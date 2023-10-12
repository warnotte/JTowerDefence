package DTO;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

import DTO.PROJECTILE.Type;
import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;
import SpeechEngine.Speaker;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class Game {
	
	
	public static boolean EditorMode = false;
	public enum Etats {STOP, PAUSE, PLAYING, NEXTLEVEL,WAITNEXTLEVEL, WIN, LOST};
	public Etats etat = Etats.PLAYING;
	private MAP map = null;
	private int GRIDSIZE_TOWER = 16; 
	private int GRIDSIZE_PATH = 8; 
	private int GRIDSIZE_BGDSPRITES = 32; 
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	private boolean EDI_isLaunchEnnemy = true;
	
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.LISTLIKE)
	ArrayList<WaveSettings> WaveSettingsReference = new ArrayList<WaveSettings>();
	
	public transient WaveSettings WaveSetting = null;
	
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int WaveInterval = 10000;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int StartingMoney = 5000;
	
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int Life = 10;

	transient int CurrentWaveIndex = 0; // Numero de la vague courante
	private transient int ennemy_count=0;
	transient long start_time=System.currentTimeMillis();
	transient private Vector<ENNEMY> ennemis = new Vector<ENNEMY>();
	transient private Vector<PROJECTILE> projectiles = new Vector<PROJECTILE>();
	transient private Vector<EXPLOSIONS> explosions = new Vector<EXPLOSIONS>();
	transient private Vector<EDI_TOWER> EDI_TOWERS = new Vector<EDI_TOWER>();
	transient private ArrayList TowerSamples = new ArrayList<EDI_TOWER>();
	
	public Game(int map_width, int map_heigth) throws IOException
	{
		Speaker.say("I'm loading the game ...");
		for (int i = 0 ; i < 5 ; i ++);
		WaveSettingsReference.add(new WaveSettings());

		TowerSamples = EDI_TOWER.load_tower_samples();
		map = new MAP(map_width,map_heigth,GRIDSIZE_PATH);

		New();
	/*	for (int i = 0 ; i < 1 ; i++)
			add_ennemy();*/
	}
	
	

	public void add_ennemy()
	{
		ENNEMY e = new ENNEMY(map.getPath_a_parcourir().elementAt(0), map.getPath_a_parcourir(), WaveSetting.getEnnemy_Vie_initiale(), WaveSetting.getEnnemy_Speed(), WaveSetting.getEnnemy_Image_Name());
		ennemis.add(e);
		setEnnemy_count(getEnnemy_count() + 1);
	}
	
	public synchronized Vector<ENNEMY> getEnnemis() {
		return ennemis;
	}

	public synchronized void setEnnemis(Vector<ENNEMY> ennemis) {
		this.ennemis = ennemis;
	}	
	
	private void evolue_PLAYING(long now, long elapsed)
	{
		if (CurrentWaveIndex>=WaveSettingsReference.size())
			if (getEnnemis().size()==0)
			{
				etat=Etats.WIN;
					return;
		}
		
		
		if (EditorMode==true)
		{
		if (elapsed>=WaveSetting.getTempsIntervalEnnemis())
			{
			//	System.err.println("Elapsed ="+elapsed);
				add_ennemy();
				start_time=now;
			}
		}
		else
		{
		
		// Lancer tout les ennemis
		if (getEnnemy_count()<=WaveSetting.getNombreEnnemy())
		{
			if ((getLaunchEnnemy()==true) && (Life>=0))
				if (elapsed>=WaveSetting.getTempsIntervalEnnemis())
				{
				//	System.err.println("Elapsed ="+elapsed);
					add_ennemy();
					start_time=now;
				}
		}
		else
			// On a fini de lancer les ennemis en theorie, on doitattendre puis passer ala vague suivante.
		{
			if (etat!=Etats.WAITNEXTLEVEL)
			{
				etat = Etats.NEXTLEVEL;
				Speaker.say("Waiting the next level.");
				
			}
			
		}
		}
		
	
		// Deplace les ennemis
		int cpt = ennemis.size();
		Vector<ENNEMY> to_kill = new Vector<ENNEMY>();
		for (int i = 0 ; i < cpt;i++)
		{
			ENNEMY e = ennemis.elementAt(i);
			if (e.evolue()!=null) // Je suis au bout du circuit
			{
				// On a tué nous meme l'ennemy ou il est au bout?
				if (e.getLife()==0)
				{
					StartingMoney+=WaveSetting.getEnnemy_Value();
					Speaker.say("Ennemy killed.");
				}else
				{
				Life--;
				Speaker.say("You have lost a life.");
				}
				if ((Life==0) && (EditorMode==false))
				{
					etat=Etats.LOST;
					Speaker.say("You have lost the game.");
				}
				to_kill.add(e);
			}
			//Point pos = e.getPosition();
		}
		for (int i = 0 ; i < to_kill.size();i++)
		{
			//System.err.println("Je tue une ennemy "+to_kill.elementAt(i));
			ennemis.remove(to_kill.elementAt(i));
		}
		
		
		if (this.getEnnemis().size()!=0)
		// Regarde si y'a pas un monstre dans l'endroit de chaque tour
		for (int k = 0 ;k<EDI_TOWERS.size();k++)
			{
				EDI_TOWER t = EDI_TOWERS.elementAt(k);
				if (t!=null)
				{
					Point pd = t.getPosition();
					Point p = new Point(pd);
					
					p.x+=16;
					p.y+=16;
					ENNEMY near_ennemy = null;
					float dst_min = 50000;
					
					// si la tour a une target
					// si la target est tjrs dans le range
					
					ENNEMY e =null ;
					
					float dst=9999999;
					
					if (ennemis.contains(t.getTarget())==false)
						t.setTarget(null);
					
					if (t.getTarget()!=null)
					dst =(float) p.distance(t.getTarget().getPosition());
					
					//System.err.println("Distance is "+dst);
					// Si le bonhomme est dans la zone alors on le garde comme cible 
			/*		
			 		// TODO : Y'a un bug qd l'ennemy est bcp trop loin;
			  		if ((t.getTarget()!=null) && (t.isEDI_LockedOnSameEnnemy()==true))
					{
					if (dst<=t.getEDI_Range()/2)
					{
						System.err.println("1)Dst == " +dst);
						near_ennemy = t.getTarget();
					}
					}
					else*/
					// Parcours la liste des ennemis pour rechercher le plus proche
					for (int i = 0;i<ennemis.size();i++)
					{
						e = ennemis.elementAt(i);
						dst =(float) e.getPosition().distance(p);
						//System.err.println("Distance is "+dst);
						// Si le bonhomme est dans la zone alors on le cible
						if (dst<=t.getEDI_Range()/2)
						{
							if (dst_min>dst)
							{
							//	System.err.println("2)Dst == " +dst);
								near_ennemy=e;
								dst_min = dst;
							}
							
						}
					}
					
					// Si l'ennemy a perdu sa vie alors il faut supprimer la cible de la tourelle
					if (near_ennemy!=null)
						if (near_ennemy.getLife()<=0)
						near_ennemy = null;
					// Met la target la plus proche sur la tour
					if (near_ennemy!=null)
						t.setTarget(near_ennemy);
					t.evolue();
				}
			}
		
		
		// Regarde si y'a pas un monstre dans des explosions
		for (int i = 0;i<this.getExplosions().size();i++)
		{
			EXPLOSIONS t = this.getExplosions().elementAt(i);
			Point p = new Point(t.getX(), t.getY());
			if (t.getTime_cpt()<=0)
				continue;
			//p.x+=16;
			//p.y+=16;
		//	ENNEMY near_ennemy = null;
		//	float dst_min = 50000;
			// Parcours la liste des ennemis
			for (int j = 0;j<ennemis.size();j++)
			{
				ENNEMY e = ennemis.elementAt(j);
				float dst =(float) e.getPosition().distance(p);
				// Si le bonhomme est dans la zone alors on le cible
				if (dst<=t.getProperties().getEDI_Range())
					e.doDamage((int) (dst*t.getProperties().getEDI_Puissance()));
				
			}
		}
		
		
		
		Vector<PROJECTILE> to_kill2 = new Vector<PROJECTILE>();
		
		// Fait evoluer les projectiles
		for (int i = 0 ; i <projectiles.size();i++)
		{
			PROJECTILE p = projectiles.elementAt(i);
			if (p.evolue()!=null)
			{
				to_kill2.add(p);
				if (p.getType()==Type.EXPLOSIVE)
				{
					EXPLOSIONS expl =  p.getExplosion();
					expl.setX(expl.getX() + GRIDSIZE_TOWER/2);
					expl.setY(expl.getY() + GRIDSIZE_TOWER/2);
					getExplosions().add(expl);
				}
				if (p.getType()==Type.DIRECT)
				{
					EXPLOSIONS expl =  p.getExplosion();
					expl.setRange(5);
					expl.setX(expl.getX() + GRIDSIZE_TOWER/2);
					expl.setY(expl.getY() + GRIDSIZE_TOWER/2);
					getExplosions().add(expl);
				}
				
				// Il faut 
			}
		}
		for (int i = 0 ; i <to_kill2.size();i++)
		{
			projectiles.remove(to_kill2.elementAt(i));
		}
		
		Vector<EXPLOSIONS> to_kill3 = new Vector<EXPLOSIONS>();
		
		// Fait evoluer les explosions
		for (int i = 0 ; i < explosions.size();i++)
		{
			EXPLOSIONS e = explosions.elementAt(i);
			if (e.evolue()!=null)
				to_kill3.add(e);
		}
		for (int i = 0 ; i <to_kill3.size();i++)
			explosions.remove(to_kill3.elementAt(i));
	}
	
	public synchronized void evolue()
	{
		long now = System.currentTimeMillis();
		long elapsed = now - start_time;
		
		if ((etat==Etats.PLAYING) || (etat==Etats.WAITNEXTLEVEL))
		{
			evolue_PLAYING(now, elapsed);
		}
		else
			if (etat==Etats.NEXTLEVEL)
		{
				
			Thread t = new Thread()
			{
				public void run()
				{
					etat=Etats.WAITNEXTLEVEL;
					NextLevel();
					Speaker.say("Level is starting");
				}
			};
			t.setName("Change level");
			t.start();
			
			
		}
		else
		{
			
		}
	}

	
	private void NextLevel() {
		
		
		CurrentWaveIndex++;
		if (CurrentWaveIndex>=WaveSettingsReference.size())
			etat=Etats.WIN;
		else
		WaveSetting=WaveSettingsReference.get(CurrentWaveIndex);
		StartingMoney+=WaveSetting.getIncome();
		try {
			Thread.sleep(WaveSetting.getDelay_Before_Start());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		setEnnemy_count(0);
		if ((etat!=Etats.LOST) && (etat!=Etats.WIN))
		etat = Etats.PLAYING;
	}



	/**
	 * Qd un projectile touche un ennemy il faut lui transmettre ses maladies ...
	 * @param p
	 * @param e
	 */
	/*private void contamineEnnemy(PROJECTILE p, ENNEMY e)
	{
		
	}*/
	
	public synchronized boolean getLaunchEnnemy() {
		return EDI_isLaunchEnnemy;
	}

	public synchronized MAP getMap() {
		return map;
	}

	public synchronized void setMap(MAP map) {
		this.map = map;
	}

	public int getMapWidth() {
		return map.getWidth();
	}
	public int getMapHeigth() {
		return map.getHeigth();
	}

	public synchronized int getGRIDSIZE_TOWER() {
		return GRIDSIZE_TOWER;
	}

	public synchronized void setGRIDSIZE_TOWER(int tile_size_bck) {
		GRIDSIZE_TOWER = tile_size_bck;
	}

	public synchronized Vector<PROJECTILE> getProjectiles() {
		return projectiles;
	}

	public synchronized void setProjectiles(Vector<PROJECTILE> projectiles) {
		this.projectiles = projectiles;
	}

	public Vector<EXPLOSIONS> getExplosions() {
		return explosions;
	}
	
	public EDI_TOWER addTower(Point tiled, EDI_TOWER et)
	{
		Point position = new Point(tiled);
		//System.err.println("Add tower in "+position);
		EDI_TOWER m = getTower(tiled);
		if (m!=null)
		{
			EDI_TOWERS.remove(m);
			return null;
		}
		
		EDI_TOWER t = null;// new EDI_TOWER(position, projectiles, PROJECTILE.Type.SIMPLE);
		t = (EDI_TOWER) et.clone();
		t.position=position;
		t.projectiles=projectiles;
		this.EDI_TOWERS.add(t);
		return t;
	}
	
	public EDI_TOWER getTower(Point p)
	{
		//EDI_TOWER sec = null;
	//System.err.println("Checking ...");
		for (int i=0;i<EDI_TOWERS.size();i++)
		{
			EDI_TOWER t = EDI_TOWERS.elementAt(i);
		//	System.err.println(i+") "+t.getPosition());
			if (t.getPosition().equals(p))
				return t;
		}
		//System.err.println("Not Found");
		return null;
	}

	public synchronized final int getGRIDSIZE_BGDSPRITES()
	{
		return GRIDSIZE_BGDSPRITES;
	}

	public synchronized final void setGRIDSIZE_BGDSPRITES(int gridsize_gnagna)
	{
		GRIDSIZE_BGDSPRITES = gridsize_gnagna;
	}

	public synchronized final int getGRIDSIZE_PATH()
	{
		return GRIDSIZE_PATH;
	}

	public synchronized final void setGRIDSIZE_PATH(int gridsize_path)
	{
		GRIDSIZE_PATH = gridsize_path;
	}
	public synchronized final Vector<EDI_TOWER> getEDI_TOWERS()
	{
		return EDI_TOWERS;
	}

	public synchronized final void setEDI_TOWERS(Vector<EDI_TOWER> towers)
	{
		EDI_TOWERS = towers;
	}

	public void setIsLaunchEnnemy(boolean t) {
		this.EDI_isLaunchEnnemy=t;
		
	}

	

	public synchronized boolean isEDI_isLaunchEnnemy()
	{
		return EDI_isLaunchEnnemy;
	}

	public synchronized final void setEDI_isLaunchEnnemy(boolean isLaunchEnnemy)
	{
		this.EDI_isLaunchEnnemy = isLaunchEnnemy;
	}
	
	public void save(String filename) throws FileNotFoundException {
		XStream xstream = new XStream(new DomDriver());
		// Convertion du contenu de l'objet article en XML
		String xml = xstream.toXML(this);
		PrintStream ps = new PrintStream(new File(filename));
		// Affichage de la conversion XML
		ps.print(xml);
		ps.flush();
		ps.close();
		
	}

	public static Game load(String filename) throws IOException {
		
		XStream xstream = new XStream(new DomDriver());
  	  	File f = new File(filename);//DialogDivers.LoadDialog("xml", MainFrame.this);
  	  
          // Redirection du fichier c:/temp/article.xml vers un flux
          // d'entrée fichier
          FileInputStream fis = new FileInputStream(f);
          Game game = null;
          try {
              // Désérialisation du fichier c:/temp/article.xml vers un nouvel
              // objet article
        	  game =  (Game) xstream.fromXML(fis);
        	  game.CurrentWaveIndex=0;
        	  game.WaveSetting=game.WaveSettingsReference.get(game.CurrentWaveIndex);
        	  game.ennemis = new Vector<ENNEMY>();
        	  game.etat = Etats.PLAYING;
        	  
        	  game.projectiles = new Vector<PROJECTILE>();
        	  game.explosions = new Vector<EXPLOSIONS>();
        	  game.EDI_TOWERS = new Vector<EDI_TOWER>();
        	  long start_time=System.currentTimeMillis();
        	  game.start_time=start_time;
              
          } finally {
              // On s'assure de fermer le flux quoi qu'il arrive
              fis.close();
          }
        return game;
	}

	

	public void New() {
		
		CurrentWaveIndex=0;
		WaveSettingsReference.clear();
		for (int i = 0 ; i < 5 ; i ++)
		WaveSettingsReference.add(new WaveSettings());
		WaveSetting=WaveSettingsReference.get(CurrentWaveIndex);

		setEnnemy_count(0);
		ennemis.clear();
		explosions.clear();
		projectiles.clear();
		EDI_TOWERS.clear();
		
		map.clear(GRIDSIZE_PATH);
		StartingMoney = 5000;
		Life = 10;
	}

	

	public synchronized int getStartingMoney()
	{
		return StartingMoney;
	}

	public synchronized void setStartingMoney(int startingMoney)
	{
		StartingMoney = startingMoney;
	}

	public synchronized int getLife()
	{
		return Life;
	}

	public synchronized void setLife(int life)
	{
		Life = life;
	}

	public synchronized ArrayList getTowerSamples() {
		return TowerSamples;
	}

	public synchronized void setTowerSamples(ArrayList towerSamples) {
		TowerSamples = towerSamples;
	}

	public synchronized WaveSettings getWaveSetting() {
		return WaveSetting;
	}

	/*public synchronized void setWaveSetting(WaveSettings waveSetting) {
		this.WaveSetting = waveSetting;
	}*/
	

	public synchronized ArrayList<WaveSettings> getWaveSettingsReference() {
		return WaveSettingsReference;
	}



	public synchronized void setWaveSettingsReference(ArrayList<WaveSettings> waveSettingsReference) {
		WaveSettingsReference = waveSettingsReference;
	}



	public boolean Upgrade_Damage(EDI_TOWER tower) {
		System.err.println("Uprading Upgrade_Damage ...");
		if (StartingMoney>=1000)
		{
			System.err.println("OK ...");tower.Upgrade_Damage();StartingMoney-=1000;return true;}
		return false;
	}
	public boolean Upgrade_Rate(EDI_TOWER tower) {
		System.err.println("Uprading Upgrade_Rate ...");
		if (StartingMoney>=1000){System.err.println("OK ...");tower.Upgrade_Rate();StartingMoney-=1000;return true;}
		return false;
	}
	public boolean Upgrade_Range(EDI_TOWER tower) {
		System.err.println("Uprading Upgrade_Range ...");
		if (StartingMoney>=1000){System.err.println("OK ...");tower.Upgrade_Range();StartingMoney-=1000;return true;}
		return false;
	}



	public int getCurrentWaveIndex() {
		return CurrentWaveIndex;
	}



	public int getMaxVagueIndex() {
		return WaveSettingsReference.size();
	}



	public synchronized int getWaveInterval() {
		return WaveInterval;
	}



	public synchronized void setWaveInterval(int waveInterval) {
		WaveInterval = waveInterval;
	}



	public void setEnnemy_count(int ennemy_count) {
		this.ennemy_count = ennemy_count;
	}



	public int getEnnemy_count() {
		return ennemy_count;
	}
	
	

}
