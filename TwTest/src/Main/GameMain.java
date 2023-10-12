package Main;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import DTO.EDI_TOWER;
import DTO.ENNEMY;
import DTO.EXPLOSIONS;
import DTO.Game;
import DTO.PROJECTILE;
import DTO.Game.Etats;
import WaxLibrary.Dialog.DialogDivers;
import java.awt.Dimension;
import java.awt.GridBagConstraints;

public class GameMain extends JFrame implements WindowListener, BufferDraw {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JButton jButton = null;
	private DoubleBufferedPanel screen = null;
	
	private Thread_Affichage thread_affichage;  //  @jve:decl-index=0:
	private Thread_Evolution thread_evolution;  //  @jve:decl-index=0:
	private Random rand = new Random();  //  @jve:decl-index=0:
	
	DefaultListModel ListModelTower;
	
	Configuration configuration = new Configuration();  //  @jve:decl-index=0:
	protected double ZOOM_FACTOR=0.125f;
	private EDI_TOWER selected_tower = null;
	private int MOUSE_X;
	private int MOUSE_Y;
	Drawing drawer = null;
	
	SpriteLoader spr = null;
	
	Game game = null;
	
	private BufferedImage IMG_Fond;  //  @jve:decl-index=0:

	float dash[] = {15.0f, 25.0f};
	BasicStroke strk = new BasicStroke (4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1f, dash, 20.0f);  //  @jve:decl-index=0:
	private JPanel jPanel = null;
//	private JCheckBox jCheckBox_ToggleLancementBonhommes = null;
//	private SpriteDisplayer jPanel_Sprites = null;
	private JButton jButton_REDRAW_FOND = null;
//	private JPanel jPanel_Conf = null;
	private JMenuBar jJMenuBar = null;
	private JMenu jMenu_File = null;
	private JMenuItem jMenuItem_Load = null;
	private JMenuItem jMenuItem_Save = null;
	private JMenuItem jMenuItem_New = null;
	private JMenu jMenu_Window = null;
	private JMenuItem jMenuItem_Windows_Configuration = null;
	
	Font font_display = null;
	private JList jPanel_Towers = null;
	private Panel_Tower_InGame jPanel_INF = null;
	private JPanel jPanel1 = null;
	
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 * @throws Exception 
	 */
	private JPanel getJPanel2() throws Exception {
		if (jPanel == null) {
			jPanel = new JPanel();
			jPanel.setLayout(new BoxLayout(getJPanel2(), BoxLayout.X_AXIS));
		//	jPanel.add(getJCheckBox_ToggleLancementBonhommes(), null);
			jPanel.add(getJButton_REDRAW_FOND(), null);
	//		jPanel.add(getJPanel_Conf(), null);
		//	jPanel.add(getConfigurationPanel(), null);
		}
		return jPanel;
	}

	
	
	/**
	 * This method initializes jPanel_Sprites	
	 * 	
	 * @return javax.swing.JPanel	
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	/*private SpriteDisplayer getJPanel_Sprites() throws IOException, InterruptedException {
		if (jPanel_Sprites == null) {
			jPanel_Sprites = new SpriteDisplayer();
		}
		return jPanel_Sprites;
	}*/
	/**
	 * This method initializes jButton_REDRAW_FOND	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_REDRAW_FOND()
	{
		if (jButton_REDRAW_FOND == null)
		{
			jButton_REDRAW_FOND = new JButton();
			jButton_REDRAW_FOND.setMnemonic(KeyEvent.VK_UNDEFINED);
			jButton_REDRAW_FOND.setText("Redraw_Fond");
			jButton_REDRAW_FOND.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					prepare_image_fond();
				}
			});
		}
		return jButton_REDRAW_FOND;
	}
	/**
	 * This method initializes jPanel_Conf	
	 * 	
	 * @return javax.swing.JPanel	
	 * @throws Exception 
	 */
	/*private JPanel getJPanel_Conf() throws Exception
	{
		if (jPanel_Conf == null)
		{
			jPanel_Conf =  ParseurAnnotations.CreatePanelFromObject("Panel Configuration", game);
		}
		return jPanel_Conf;
	}*/
	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJMenu_File());
			jJMenuBar.add(getJMenu_Window());
			
	//		jJMenuBar.add(WAXUIConfigurator.getJMenu_Options(getContentPane()));
		}
		return jJMenuBar;
	}
	/**
	 * This method initializes jMenu_File	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu_File() {
		if (jMenu_File == null) {
			jMenu_File = new JMenu();
			jMenu_File.setText("File");
			jMenu_File.add(getJMenuItem_Load());
			//jMenu_File.add(getJMenuItem_Save());
			jMenu_File.add(getJMenuItem_New());
		}
		return jMenu_File;
	}
	/**
	 * This method initializes jMenuItem_Load	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem_Load() {
		if (jMenuItem_Load == null) {
			jMenuItem_Load = new JMenuItem();
			jMenuItem_Load.setText("Load");
			jMenuItem_Load.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					
					try {
						File f = DialogDivers.LoadDialog(GameMain.this,"xml");
						load(f.getAbsolutePath());
					} catch (IOException e1) {
						DialogDivers.Show_dialog(e1, "Error loading");
						e1.printStackTrace();
					} catch (Exception e1)
					{
						DialogDivers.Show_dialog(e1, "Error loading");
						e1.printStackTrace();
					}
				}
			});
		}
		return jMenuItem_Load;
	}
	protected void load(String absolutePath) throws IOException {
		Game game = Game.load(absolutePath);
		synchronized(this)
		{
			this.game=game;
			drawer.setGame(game);
			jPanel_INF.setGame(game);
			thread_evolution.setGame(game);
			prepare_image_fond();
		}
	}
	/**
	 * This method initializes jMenuItem_Save	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem_Save() {
		if (jMenuItem_Save == null) {
			jMenuItem_Save = new JMenuItem();
			jMenuItem_Save.setText("Save");
			jMenuItem_Save.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						save();
					} catch (FileNotFoundException e1) {
						
						e1.printStackTrace();
					}
				}
			});
		}
		return jMenuItem_Save;
	}
	protected void save() throws FileNotFoundException {
		String file = DialogDivers.SaveDialog(this, "xml");
		if (file!=null)
			
		game.save(file);
		
	}
	/**
	 * This method initializes jMenuItem_New	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem_New() {
		if (jMenuItem_New == null) {
			jMenuItem_New = new JMenuItem();
			jMenuItem_New.setText("New");
			jMenuItem_New.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					New();
				}
			});
		}
		return jMenuItem_New;
	}
	protected void New() {
		game.New();
		drawer.setGame(game);
		prepare_image_fond();
		redraw_buffer_screen();
		
	}
	/**
	 * This method initializes jMenu_Window	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenu_Window() {
		if (jMenu_Window == null) {
			jMenu_Window = new JMenu();
			jMenu_Window.setText("Windows");
			jMenu_Window.add(getJMenuItem_Windows_Configuration());
		}
		return jMenu_Window;
	}
	/**
	 * This method initializes jMenuItem_Windows_Configuration	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItem_Windows_Configuration() {
		if (jMenuItem_Windows_Configuration == null) {
			jMenuItem_Windows_Configuration = new JMenuItem();
			jMenuItem_Windows_Configuration.setText("Voir/Cacher Editeur");
			jMenuItem_Windows_Configuration
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jPanel.setVisible(invert(jPanel.isVisible()));
					///		jPanel_PROPERTIES.setVisible(invert(jPanel_PROPERTIES.isVisible()));
					//		jPanel_Sprites.setVisible(invert(jPanel_Sprites.isVisible()));
							
							
						}
					});
		}
		return jMenuItem_Windows_Configuration;
	}
	protected boolean invert(boolean v) {
		if (v==true) return false;
		return true;
	}
	/**
	 * This method initializes jPanel_Towers	
	 * 	
	 * @return javax.swing.JPanel	
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private JList getJPanel_Towers() throws IOException, InterruptedException {
		if (jPanel_Towers == null) {
			jPanel_Towers = new JList();
			jPanel_Towers.setPreferredSize(new Dimension(200, 400));
			ComplexCellRenderer cp = new ComplexCellRenderer();
			jPanel_Towers.setCellRenderer(cp);
			ListModelTower = new DefaultListModel ();
			for (int i = 0; i < game.getTowerSamples().size(); i++) {
				EDI_TOWER et = (EDI_TOWER)game.getTowerSamples().get(i);
				ListModelTower.add(i, et);
			}
		
			
			jPanel_Towers.setModel(ListModelTower);			
			
		}
		return jPanel_Towers;
	}



	/**
	 * This method initializes jPanel_INF	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_INF() {
		if (jPanel_INF == null) {
			jPanel_INF = new Panel_Tower_InGame(game);
			
			
		}
		return jPanel_INF;
	}



	/**
	 * This method initializes jPanel1	
	 * 	
	 * @return javax.swing.JPanel	
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	private JPanel getJPanel1() throws IOException, InterruptedException {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			jPanel1.setLayout(new BoxLayout(getJPanel1(), BoxLayout.Y_AXIS));
			jPanel1.add(getJPanel_Towers(), null);
			jPanel1.add(getJPanel_INF(), null);
		}
		return jPanel1;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				GameMain thisClass;
				try {
					//UIManager.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
					//SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.RavenGraphiteGlassSkin");
					
					thisClass = new GameMain();
					thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					thisClass.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * This is the default constructor
	 * @throws Exception 
	 */
	public GameMain() throws Exception {
		super();
		game = new Game(32,32);
		spr = new SpriteLoader("Ttiles");
		drawer = new Drawing(game, configuration, spr);
		configuration.setEDI_Enable_DrawPath(false);
		initialize();
		init_buffer_fond();
		
		prepare_image_fond();
		redraw_buffer_screen();
	}
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					game.add_ennemy();
				}
			});
		}
		return jButton;
	}
	/**
	 * This method initializes jPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private DoubleBufferedPanel getJPanel() {
		if (screen == null) {
			try {
				screen = new DoubleBufferedPanel(game.getMapWidth()*game.getGRIDSIZE_BGDSPRITES(),game.getMapHeigth()*game.getGRIDSIZE_BGDSPRITES(), 1)
				{
					public void paint(Graphics g)
					{
						super.paint(g);
						g.setFont(font_display);
						g.setColor(Color.white);
						g.drawString("T : ajoute tour", 0, 30);
						g.drawString("Money : "+game.getStartingMoney(), 0, 60);
						g.drawString("Life : "+game.getLife(), 0, 90);
						g.drawString("Vague : "+game.getCurrentWaveIndex()+"/"+game.getMaxVagueIndex(), 0, 120);
						g.drawString("Ennemy : "+game.getEnnemy_count()+"/"+game.WaveSetting.getNombreEnnemy(), 0, 150);
						g.drawString("State = "+game.etat, 0, 180);
						
						if (game.etat==Etats.LOST)
						{
							g.drawString("Perdu !!!", screen.getWidth()/2,screen.getHeight()/2);
						}
						if (game.etat==Etats.WIN)
						{
							g.drawString("Victoire !!!", screen.getWidth()/2,screen.getHeight()/2);
						}
						
					}
				};
				screen.setFocusable(true);
				screen.addMouseMotionListener(new java.awt.event.MouseMotionAdapter()
				{
					public void mouseMoved(java.awt.event.MouseEvent e)
					{
						Point p = screen.convertViewXYtoRealXY(e.getX(), e.getY());
						MOUSE_X=p.x;
						MOUSE_Y=p.y;
					}
					public void mouseDragged(java.awt.event.MouseEvent e)
					{
						Point p = screen.convertViewXYtoRealXY(e.getX(), e.getY());
					    MOUSE_X=p.x;
						MOUSE_Y=p.y;
					
					if (screen.MB_MIDDLE_CLICKED == 1)
					{
						screen.scrollMouse(e.getX(), e.getY());
						//repaint();
					}
					if (screen.MB_RIGHT_CLICKED == 1)
					{
					/*	Point r = convertXYToGriddedXY(MOUSE_X,MOUSE_Y, game.getGRIDSIZE_PATH());
						if (selected_path_node!=null)
						{
							selected_path_node.x = r.x;
							selected_path_node.y = r.y;
						}*/
					}
				}
			});
				
			screen.addMouseListener(new java.awt.event.MouseAdapter()
			{   
				public void mouseEntered(java.awt.event.MouseEvent e) {    
					screen.requestFocus();
				}
				public void mouseClicked(MouseEvent e)
				{
					if (e.getClickCount()>=2)
					{
						//doubleClick();
					}
				}
					
				public void mousePressed(java.awt.event.MouseEvent e)
				{
					int x = e.getX();
					int y = e.getY();
					Point p = screen.convertViewXYtoRealXY(e.getX(), e.getY());
					//x=p.x;
					//y=p.y;
					if (e.getButton()==1)
					{
						screen.Leftmouseclick(x,y);
					}
					if (e.getButton()==2)
					{
						screen.Middlemouseclick(x,y);
					}
					if (e.getButton()==3)
					{
						screen.Rightmouseclick(p.x,p.y);
					//	selected_path_node = getPathNodeSelected(p.x,p.y);
						
					}
					
				}
				public void mouseReleased(java.awt.event.MouseEvent e)
				{
					if (e.getButton()==1)
					{
						screen.Leftmouseunclick(-1, -1);
						Point p= convertXYToGriddedXY(MOUSE_X,MOUSE_Y, game.getGRIDSIZE_TOWER());
						selected_tower=game.getTower(p);
						try
						{
							jPanel_INF.setTower(selected_tower);
							
								
						} catch (Exception e1)
						{
							e1.printStackTrace();
						}
							
						}
						if (e.getButton()==2)
						{
							screen.Middlemouseunclick(-1,-1);
							repaint();
						}
						if (e.getButton()==3)
						{
							screen.Rightmouseunclick(-1, -1);
						//	selected_path_node =  null;
						}
					}
				});
			screen.addKeyListener(new java.awt.event.KeyAdapter() {
				public void keyReleased(java.awt.event.KeyEvent e) {
					System.err.println("KeyReleased == "+e.getKeyCode()+ " "+e.getKeyText(e.getKeyCode()));
					

					
					// Delete - le noeud selectionné
					if (e.getKeyCode()==127)
					{
					/*	if (selected_path_node!=null)
						{
							game.getMap().getPath_a_parcourir().remove(selected_path_node);
							selected_path_node= game.getMap().getPath_a_parcourir().lastElement();
						}
						else
							System.err.println("No path point selected");*/
					}
					// Ajoute une tower ou la supprime
					if (e.getKeyCode()==KeyEvent.VK_T)
					{
						EDI_TOWER et = (EDI_TOWER)jPanel_Towers.getSelectedValue();
						if (et!=null) // Faut avoir choisi une tour
						if (game.getStartingMoney()>=et.getPrice()) // Avoir assez de pognon.
						{
							game.setStartingMoney(game.getStartingMoney()-et.getPrice());
							Point position = convertXYToGriddedXY(MOUSE_X,MOUSE_Y,game.getGRIDSIZE_TOWER());
							selected_tower = game.addTower(position, et);
							try
							{
					//			ParseurAnnotations.Refresh_PanelEditor_For_Object("Game Configuration", jPanel_Conf, game, null, false);
					//			CreateEditionPanelFromObject.Refresh_PanelEditor_For_Object("Game Configuration",jPanel_Conf,game);
							} catch (Exception e1)
							{
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						
					}
					// Change le _Type de tower
					if (e.getKeyCode()==KeyEvent.VK_C)
					{
						if (selected_tower!=null)
							selected_tower.test_change_type();
						else
							System.err.println("No tower selected");
					}
			
				}
			});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			screen.addMouseWheelListener(new MouseWheelListener()
			{
			public void mouseWheelMoved(MouseWheelEvent e)
			{
				if (screen.MB_MIDDLE_CLICKED==0)
				{
					int   x = e.getX();
					int   y = e.getY();
					int   v = -e.getWheelRotation();
					Point p2 = screen.convertViewXYtoRealXY(x, y);
					double z = screen.getZOOM_X();
					z += (z*0.05*v);
					screen.setZOOM(z);
					Point p = screen.convertViewXYtoRealXY(x, y);
					double dx=(p2.x-p.x);
					double dy=(p2.y-p.y);
					screen.scroll_notrelative(screen.getTransX()-dx, screen.getTransY()-dy);
					repaint();
				}
			}
			});
			
			screen.setLayout(new GridBagLayout());
		}
		return screen;
	}
	/**
	 * This method initializes jPanel_PROPERTIES	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	/*private JWPanel getJPanel_PROPERTIES()
	{
		if (jPanel_PROPERTIES == null)
		{
			try
			{
			//	jPanel_PROPERTIES = CreateEditionPanelFromObject.Create_PanelEditor_For_Object("Fake Tower", new EDI_TOWER());
				//jPanel_PROPERTIES = ParseurAnnotations.CreatePanelFromObject("Fake Tower", new EDI_TOWER(), 0, null, false);
				jPanel_PROPERTIES = (JWPanel)ParseurAnnotations.CreatePanelFromObject("Fake Tower", new EDI_TOWER());

			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jPanel_PROPERTIES;
	}*/
	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		this.setSize(1024, 800);
		
		font_display = new Font("IMPACT", Font.BOLD, 32);
		
		this.setJMenuBar(getJJMenuBar());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		this.addWindowListener(this);
		thread_evolution = new Thread_Evolution(game);
		thread_evolution.start();
		thread_affichage = new Thread_Affichage(this);	     
	    thread_affichage.start();
	    
	      
	   
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 * @throws Exception 
	 */
	private JPanel getJContentPane() throws Exception {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel2(), BorderLayout.NORTH);
			jContentPane.add(getJButton(), BorderLayout.SOUTH);
		//	jContentPane.add(getJPanel_PROPERTIES(), BorderLayout.EAST);
			jContentPane.add(getJPanel(), BorderLayout.CENTER);
			jContentPane.add(getJPanel1(), BorderLayout.WEST);
			
	//		jContentPane.add(getJPanel_Sprites(), BorderLayout.WEST);
			
		}
		return jContentPane;
	}
	
/////////////// GRAPHIQUE ////////////////////////////////
	public void init_buffer_fond() // Le force ne me plait pas non plus
	{
		int w=game.getMapWidth()*game.getGRIDSIZE_BGDSPRITES();
		int h=game.getMapHeigth()*game.getGRIDSIZE_BGDSPRITES();
		// Si on resize la windows comme un porc ca px mettre w ou h a < 0
		if ((w>0) && (h>0))
		{
			System.err.println("Set buffer ["+w+" , "+h+"]");
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			IMG_Fond = null; // Prions pour qu'il desinitialise la mémoire ??!
			IMG_Fond = gc.createCompatibleImage(w, h);
			//gc.createCompatibleVolatileImage(arg0, arg1)
			prepare_image_fond();
			repaint();
		}
	}
	
	private void prepare_image_fond()
	{
		thread_affichage.setPause(true);
	//	int xx,yy;
		System.err.println("prepare_image_fond");
		Graphics2D g2 = (Graphics2D) IMG_Fond.getGraphics();
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_OFF);
	//	g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(10,45,10));
		g2.fillRect(0, 0, screen.BUFF_WIDTH, screen.BUFF_HEIGHT);
		
		if (configuration.isEDI_Enable_BACKGROUND_LAYER1()==true)
			draw_map_layer(g2, 0);
		if (configuration.isEDI_Enable_BACKGROUND_LAYER2()==true)
			draw_map_layer(g2, 1);
		if (configuration.isEDI_Enable_GRID_BACKGROUND()==true)
			draw_grid(g2, game.getGRIDSIZE_TOWER(),new Color(0,0,255,127));
		if (configuration.isEDI_Enable_GRID_TOWER()==true)
			draw_grid(g2, game.getGRIDSIZE_BGDSPRITES(),new Color(255,0,0,127));
		
		thread_affichage.setPause(false);
	}
	private void draw_grid(Graphics2D g2, int gdsize, Color col)
	{
		Color back = g2.getColor();
		g2.setColor(col);
		int w = screen.getWidth();
		int h = screen.getHeight();
				
		for (int x=0;x<w;x+=gdsize)
			g2.drawLine(x,0,x,h);	
		for (int y=0;y<h;y+=gdsize)
			g2.drawLine(0,y,w,y);
		
		g2.setColor(back);
	}
	/**
	 * Redessine tout le buffer graphique
	 *
	 */
	
	private void draw_map_layer(Graphics2D g2, int klayer)
	{
		int MAP_W =  game.getMapHeigth();
		int MAP_H =  game.getMapWidth();
		int size = game.getGRIDSIZE_BGDSPRITES();
		for (int j = 0; j < MAP_H;j++)
			for (int i = 0; i < MAP_W;i++)
			{
				int xx = i * size;
				int yy = j * size;
				int id = game.getMap().getBackgroundTile(i, j, klayer);
				if (id!=-1)
				{
					g2.drawImage(spr.getImage(id), xx,yy, this);
				}
			}
	}
	
	public void redraw_buffer_screen() {
	//ystem.err.println("Redraw Buffer Screen");
		Graphics2D g2 = (Graphics2D) screen.m_imgOffScr.getGraphics();
		
		g2.setRenderingHint (RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
		g2.drawImage(IMG_Fond, 0,0, screen);
		
		synchronized (game)
		{
			if (configuration.isEDI_Enable_DrawPath()==true)
			{
				Vector<Point> chemin = game.getMap().getPath_a_parcourir();
				
				// Dessine le chemin
				Point old_pt = chemin.elementAt(0);
				g2.setColor(new Color(127,80,0,127));
				g2.setStroke (new BasicStroke (8));
				for (int j = 1 ; j < chemin.size();j++)
				{
					Point p = chemin.elementAt(j);
					g2.drawLine(old_pt.x, old_pt.y, p.x, p.y);
					g2.drawLine(old_pt.x, old_pt.y+1, p.x, p.y+1);
					g2.drawLine(old_pt.x+1, old_pt.y+1, p.x+1, p.y+1);
					g2.drawLine(old_pt.x+1, old_pt.y, p.x+1, p.y);
					g2.drawLine(old_pt.x, old_pt.y+1, p.x, p.y+1);
					old_pt = p;
				}
				g2.setStroke (new BasicStroke ());
					
		
				// Dessine les noeuds du chemin
				for (int j = 0 ; j < chemin.size();j++)
				{
					Point p = chemin.elementAt(j);
				//	if (p==selected_path_node)
				//		g2.setColor(Color.BLUE);
				//	else
						g2.setColor(Color.RED);
					g2.fillOval(p.x-8, p.y-8, 16, 16);
				}
			}
		
		// Illumine la tour selectionnée
		if (selected_tower!=null)
		{
		g2.setColor(new Color(255,0,255,127));
		float modifier = 0.25f+0.05f*(float) Math.cos((double)(thread_evolution.getTime())/(double)25);
		g2.setStroke (strk);
	    g2.drawOval(
					(int)(selected_tower.getX()+game.getGRIDSIZE_TOWER()/2-selected_tower.getEDI_Range()/2*modifier), 
					(int)(selected_tower.getY()+game.getGRIDSIZE_TOWER()/2-selected_tower.getEDI_Range()/2*modifier), 
					(int)(selected_tower.getEDI_Range()*modifier), 
					(int)(selected_tower.getEDI_Range()*modifier)
					);
	    g2.setStroke (strk);
	 	g2.setColor(new Color(255,255,255,32));
		g2.drawOval((int)(selected_tower.getX()+game.getGRIDSIZE_TOWER()/2-selected_tower.getEDI_Range()/2), (int)(selected_tower.getY()+game.getGRIDSIZE_TOWER()/2-selected_tower.getEDI_Range()/2), selected_tower.getEDI_Range(), selected_tower.getEDI_Range());
		
		g2.setColor(Color.ORANGE);
		}
		
		// Dessine les towers
		Vector<EDI_TOWER> towers = game.getEDI_TOWERS();
		g2.setColor(Color.ORANGE);
		float dash[] = {15.0f, 25.0f};
	//	BasicStroke strk = new BasicStroke (2.0f, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER, 1f, dash, 0.0f);
		for (int i =0;i<towers.size();i++)
			{
				EDI_TOWER e = towers.elementAt(i);
				if (e!=null)
				{
					drawer.Draw_EDI_TOWER(g2, e);
					
					
				}
	    	}

		// Dessine les ennemis
		Vector<ENNEMY> ennemis = game.getEnnemis();
		g2.setColor(Color.GREEN);
		for (int j = 0 ; j < ennemis.size();j++)
		{
			ENNEMY e = ennemis.elementAt(j);
			drawer.Draw_ENNEMY(g2, e);
		}
		
		// Dessine les projectiles
		Vector<PROJECTILE> projectiles = game.getProjectiles();
		g2.setColor(Color.LIGHT_GRAY);
		for (int j = 0 ; j < projectiles.size();j++)
		{
			PROJECTILE e = projectiles.elementAt(j);
			g2.fillOval((int)(e.getX()-3), (int)(e.getY()-3), 6,6);
		}
		
		// Dessine les explosions splash
		Vector<EXPLOSIONS> explodes = game.getExplosions();
		g2.setColor(Color.MAGENTA);
		
		for (int j = 0 ; j < explodes.size();j++)
		{
			EXPLOSIONS e = explodes.elementAt(j);
			int range = e.getRange()*2;
			if (range<=1) continue;
			
			Point2D center = new Point2D.Float(e.getX(), e.getY()-16);
		    float radius = range/2;
		    float[] dist = {0.0f, 0.2f, 1.0f};
		    Color[] colors = {Color.YELLOW, Color.RED,new Color(0,0,40,0)};
		    RadialGradientPaint p = new RadialGradientPaint(center, radius, dist, colors);
		    g2.setPaint(p);
			g2.fillOval((int)(e.getX()-range/2), (int)(e.getY()-range/2)-16, range,range);
		}
		g2.setPaint(null);
	
		if (configuration.isEDI_Enable_BACKGROUND_LAYER3()==true)
			draw_map_layer(g2, 2);
		if (configuration.isEDI_Enable_BACKGROUND_LAYER4()==true)
			draw_map_layer(g2, 3);
		}
		
		
		
		screen.repaint();
		
	}
	
//////////////////////////////////////////////////	
	
	/**
	 * Convertit une valeur de postion réelle (transformé par DoubleBufferedPanel avant) en la 
	 * valeur réel subissant un facteur de grille de taille paramtrée
	 * @param mouse_x2 position réelle X
	 * @param mouse_y2 position réelle Y
	 * @param GRID_SIZE taille de la grille
	 * @return
	 */
	protected Point convertXYToGriddedXY(int mouse_x2, int mouse_y2, int GRID_SIZE)
	{
		mouse_y2 = mouse_y2/GRID_SIZE;
		mouse_x2 = mouse_x2/GRID_SIZE;
		Point pos = new Point(mouse_x2*GRID_SIZE,mouse_y2*GRID_SIZE);
		return pos;
	}
	/**
	 * Cherche un noeud du chemin proche (en position réelle pour game
	 * @param x
	 * @param y
	 * @return le Point
	 */
	protected Point getPathNodeSelected(int x, int y) {
		Vector<Point> chem = game.getMap().getPath_a_parcourir();
		for (int i = 0;i<chem.size();i++)
		{
			Point p = chem.elementAt(i);
			if (p.distance(x,y)<10)
				return p;
		}
		return null;
	}
	public void windowActivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowClosed(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		try
		{
			quit();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void windowClosing(WindowEvent arg0)
	{
		try
		{
			quit();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void quit() throws IOException
	{
		//this.game.getMap().save();
	}
	public void windowDeactivated(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowDeiconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowIconified(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	public void windowOpened(WindowEvent arg0)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	
}
