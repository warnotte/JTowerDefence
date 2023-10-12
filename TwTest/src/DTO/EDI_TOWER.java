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

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class EDI_TOWER implements Cloneable {
	
	transient static int ID_CPT = 0;
	transient int ID = -1;
	public transient Point position = null;
	transient ENNEMY target = null;
	public transient Vector<PROJECTILE> projectiles = null;
	transient int rate_cpt = 0;
	
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PROJECTILE_PROPERTIES EDI_properties = null;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	EXPLOSIONS_PROPERTIES EDI_properties_explosion = null;
	// Variable modifiables
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	private boolean EDI_LockedOnSameEnnemy = true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private String EDI_Name = "Tower_1";
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int EDI_Range = 250;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int EDI_rate = 25;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int Price = 1000;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int ImageIndex=0; // Pour le dessin.
	//transient private static Random rand = new Random();
	
	public EDI_TOWER()
	{
		ID = ++ID_CPT;
		EDI_properties = new PROJECTILE_PROPERTIES();	
		EDI_properties_explosion = new EXPLOSIONS_PROPERTIES();
	}
	public EDI_TOWER(Point position, Vector<PROJECTILE> list_projectiles, PROJECTILE.Type typep)
	{
		this();
		this.EDI_properties.EDI_type = typep;
		projectiles= list_projectiles;
		this.position = new Point(position);
		
		if (EDI_properties.EDI_type==PROJECTILE.Type.EXPLOSIVE)
			EDI_properties_explosion = new EXPLOSIONS_PROPERTIES();
		
	}
	
	public void test_change_type()
	{
		if (EDI_properties.EDI_type==PROJECTILE.Type.EXPLOSIVE)
		{
			EDI_properties.EDI_type=PROJECTILE.Type.SIMPLE;
			return;
		}
		if (EDI_properties.EDI_type==PROJECTILE.Type.SIMPLE)
		{
			EDI_properties.EDI_type=PROJECTILE.Type.EXPLOSIVE;
			EDI_properties_explosion = new EXPLOSIONS_PROPERTIES();
			return;
		}
	}

	public void evolue()
	{
		
		// Si on doit tirer parce qu'on est rechargée
			if (target!=null)
			{
				if (rate_cpt==0)
				{
				//System.err.println(this+" Je tire sur "+target);
					Point p1 = new Point(position);
					p1.x+=8;
					p1.y+=8;
				PROJECTILE p = new PROJECTILE(p1, target, EDI_properties.EDI_type, EDI_properties, EDI_properties_explosion);
				projectiles.add(p);
				rate_cpt=EDI_rate;
				}
				if (rate_cpt>0) rate_cpt--;
				
			}
	}
	
	public int getX() {
		return position.x;
	}
	public int getY() {
		return position.y;
	}

	
	public void setTarget(ENNEMY targt)
	{
		this.target=targt;
	}
	public String getPanelisableName()
	{
		return "Tower "+ID;
	}
	public Point getPosition() {
 
		return position;
	}
	
	public synchronized final PROJECTILE_PROPERTIES getEDI_properties()
	{
		return EDI_properties;
	}
	public synchronized final void setEDI_properties(
			PROJECTILE_PROPERTIES edi_properties)
	{
		EDI_properties = edi_properties;
	}
	
	public synchronized final EXPLOSIONS_PROPERTIES getEDI_properties_explosion()
	{
		return EDI_properties_explosion;
	}
	public synchronized final void setEDI_properties_explosion(
			EXPLOSIONS_PROPERTIES edi_properties_explosion)
	{
		EDI_properties_explosion = edi_properties_explosion;
	}
	public ENNEMY getTarget() {
		
		return target;
	}
	public synchronized final Vector<PROJECTILE> getProjectiles()
	{
		return projectiles;
	}
	public synchronized final void setProjectiles(
			Vector<PROJECTILE> edi_projectiles)
	{
		projectiles = edi_projectiles;
	}

	static protected void save(String filename, EDI_TOWER t) throws FileNotFoundException {
		XStream xstream = new XStream(new DomDriver());
		// Convertion du contenu de l'objet article en XML
		String xml = xstream.toXML(t);
		PrintStream ps = new PrintStream(new File(filename));
		// Affichage de la conversion XML
		ps.print(xml);
		ps.flush();
		ps.close();
		
	}

	public static EDI_TOWER load(String filename) throws IOException {
		
		XStream xstream = new XStream(new DomDriver());
  	  	File f = new File(filename);//DialogDivers.LoadDialog("xml", MainFrame.this);
  	  
          // Redirection du fichier c:/temp/article.xml vers un flux
          // d'entrée fichier
          FileInputStream fis = new FileInputStream(f);
          EDI_TOWER game = null;
          try {
              // Désérialisation du fichier c:/temp/article.xml vers un nouvel
              // objet article
        	  game =  (EDI_TOWER) xstream.fromXML(fis);
        	  
              
          } finally {
              // On s'assure de fermer le flux quoi qu'il arrive
              fis.close();
          }
        return game;
	}
	
	public static void main(String args[]) 
	{
		EDI_TOWER tower = new EDI_TOWER();
		try {
			EDI_TOWER.save("tower_1.xml", tower);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public synchronized boolean isEDI_LockedOnSameEnnemy()
	{
		return EDI_LockedOnSameEnnemy;
	}
	public synchronized String getEDI_Name()
	{
		return EDI_Name;
	}
	public synchronized int getEDI_Range()
	{
		return EDI_Range;
	}
	public synchronized int getEDI_rate()
	{
		return EDI_rate;
	}
	public synchronized void setEDI_LockedOnSameEnnemy(boolean eDILockedOnSameEnnemy)
	{
		EDI_LockedOnSameEnnemy = eDILockedOnSameEnnemy;
	}
	public synchronized void setEDI_Name(String eDIName)
	{
		EDI_Name = eDIName;
	}
	public synchronized void setEDI_Range(int eDIRange)
	{
		EDI_Range = eDIRange;
	}
	public synchronized void setEDI_rate(int eDIRate)
	{
		EDI_rate = eDIRate;
	}
	public synchronized int getPrice()
	{
		return Price;
	}
	public synchronized void setPrice(int price)
	{
		Price = price;
	}
	
	public static ArrayList load_tower_samples() throws IOException {
		ArrayList TowerSamples = new ArrayList();
		File f = new File("Towers");
		String [] filename = f.list();
		System.err.println("Adding "+filename.length+" towers sample");
		for (int i = 0; i < filename.length; i++) {
			if (filename[i].contains("CVS")==true) 
				continue;
			if (filename[i].toLowerCase().contains("xml")==false) 
					continue;
			EDI_TOWER tower = EDI_TOWER.load("Towers/"+filename[i]);
			TowerSamples.add(tower);
		}
		return TowerSamples;
	}
	
	
	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la 
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons 
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}
	public float getDamage() {
		if (EDI_properties.EDI_type==EDI_properties.EDI_type.EXPLOSIVE)
			return EDI_properties_explosion.EDI_Puissance;
		else
			return EDI_properties.EDI_Damage;
		
	}
	public void Upgrade_Range() {
		EDI_Range+=EDI_Range/5;
		
	}
	public void Upgrade_Rate() {
		EDI_rate-=EDI_rate/5;
		
	}
	public void Upgrade_Damage() {
		getEDI_properties().EDI_Damage+=getEDI_properties().EDI_Damage/5;
		getEDI_properties_explosion().EDI_Puissance+=getEDI_properties_explosion().EDI_Puissance/5;
		
	}
	public int getImageIndex() {
		return ImageIndex;
	}
	
	public void setImageIndex(int index)
	{
		ImageIndex = index;
	}
	

}
