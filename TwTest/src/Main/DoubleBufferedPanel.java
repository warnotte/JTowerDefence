package Main;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * Classe buffer graphique pour contenir des pixels a afficher sur l'ecran selon un zoom, une translation
 * Convertit les données visuelles en données souris réelle.
 * @author : Warnotte Renaud 2006
 */

public class DoubleBufferedPanel extends JPanel implements FocusListener
{
	private static final long serialVersionUID = 1L;
	//private int				  VERBOSE = 1; // Permet d'avoir l'affichage des appel de fonctions (pour voir ce qu'il se passe si ca paint ou repaint)
	private double			  FACTEUR_PRECISION = 1; // 1 PIXEL = 1/10 metres;
	double			  		  ZOOM_X = 1; // Facteur de zoom entre le buffer et ce qu'il y'a a l'écran
	double			  		  ZOOM_Y = 1; // Facteur de zoom entre le buffer et ce qu'il y'a a l'écran
	private double			  TransX = 0; // Pour l'offset de dessin
	private double			  TransY = 0; // Pour l'offset de dessin
	public int				  BUFF_WIDTH = 150; // Taille du buffer W (a multiplier ensuite par le FACTEUR DE PRECISION)
	public int				  BUFF_HEIGHT = 150; // Taille du buffer H (a multiplier ensuite par le FACTEUR DE PRECISION)
	public BufferedImage	  m_imgOffScr = null; //  Image for off-screen drawing -- "back buffer"
	public Graphics			  m_gOffScr = null; //  Graphics drawing interface to offscr image
	public int				  drag_OFFSX; // Permet de savoir si on DRAG la souris pour scroller (bt millieu)
	public boolean			  SCROLLABLE_X = true;
	public boolean			  SCROLLABLE_Y = true;
	private boolean			  BYPASS_ZOOMY = false;
	private boolean			  BYPASS_ZOOMX = false;
//	private Random rand = new Random();

	// Variable pour la gestion de la souris :p
	@SuppressWarnings("unused")
	private int left_clicked_x=0;
	@SuppressWarnings("unused")
	private int left_clicked_y=0;
	@SuppressWarnings("unused")
	private int right_clicked_x=0;
	@SuppressWarnings("unused")
	private int right_clicked_y=0;
	private int middle_clicked_x=0;
	private int middle_clicked_y=0;
	public int MB_LEFT_CLICKED;
	public int MB_RIGHT_CLICKED;
	public int MB_MIDDLE_CLICKED;
	
	public void Leftmouseclick(int x, int y)
	{
		MB_LEFT_CLICKED = 1;
		left_clicked_x = x;
		left_clicked_y = y;
	}
	public void Middlemouseclick(int x, int y)
	{
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		MB_MIDDLE_CLICKED = 1;
		middle_clicked_x = x;
		middle_clicked_y = y;
	}
	public void Rightmouseclick(int x, int y)
	{
		MB_RIGHT_CLICKED = 1;
		right_clicked_x = x;
		right_clicked_y = y;
	}
	
	public void Leftmouseunclick(int x, int y)
	{
		MB_LEFT_CLICKED = 0;
		left_clicked_x = x;
		left_clicked_y = y;
	}
	public void Middlemouseunclick(int x, int y)
	{
		MB_MIDDLE_CLICKED = 0;
		left_clicked_x = x;
		left_clicked_y = y;
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	}
	public void Rightmouseunclick(int x, int y)
	{
		MB_RIGHT_CLICKED = 0;
		right_clicked_x = x;
		right_clicked_y = y;
	}
	
	
	//  I like to set up the double-buffer first.  Then
	//  if other errors occur later, we at least have our
	//  display system up & running and may use it to display
	//  messages or other info.
	//
	public DoubleBufferedPanel(int width, int height, double facteur_precision) throws Exception
	{
		this.setFocusable(true);
		this.FACTEUR_PRECISION = facteur_precision;
		this.BUFF_WIDTH		   = (int)(width*FACTEUR_PRECISION);
		this.BUFF_HEIGHT	   = (int)(height*FACTEUR_PRECISION);
		
		
		init();
		TransX = 0;//(double)(BUFF_WIDTH/1); // Position au milieu
		TransY = 0;//(double)(BUFF_HEIGHT/1); // Position au milieu
		this.setName("DoublebufferedPanel("+this.BUFF_WIDTH+")");
	}
	

	/*
	 * Crée l'image et peint en noir l'image et assigne le buffer a la screen (jpanel ou autre)
	 */
	public synchronized void init() throws Exception
	{
		
		
				
		
	
		
		setBackground(new Color(0, 0, 0)); //  Set the background color
		try
		{
			if (m_imgOffScr!=null)
				m_imgOffScr.flush();
			m_imgOffScr = null;
			System.gc();
			m_imgOffScr = new BufferedImage(BUFF_WIDTH, BUFF_HEIGHT, BufferedImage.TYPE_INT_RGB);
			m_gOffScr   = m_imgOffScr.getGraphics();
			
		}
		catch (Exception e)
		{
			
			System.err.println("Failed to set up double-buffered graphics");
			m_imgOffScr = null;
			m_gOffScr   = null;
			throw e;
		}
		System.gc();
	    m_gOffScr.setColor(new Color(0, 0, 0));
		m_gOffScr.fillRect(0, 0, BUFF_WIDTH, BUFF_HEIGHT);
	}
	
	
	public Object mainframe = null;
	public Method method = null;
	public BufferedImage	img_logo;
	
	
	
	
	/*
	 * Crée le buffer (lent)
	 */
	@Override
	public synchronized void paint(Graphics g)
	{
	
		
		/*if (VERBOSE == 1)
			System.err.println(this.getName()+"::Paint()");
*/
		//System.err.println(this.getWidth()+","+ this.getHeight());
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Allez :) on n'est plus en < 1995 qd meme ; Par contre ca bouffe :)
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF); // Allez :) on n'est plus en < 1995 qd meme ; Par contre ca bouffe :)
	//	 g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		synchronized(this)
		{
			if (m_imgOffScr != null)
		
		{
				
				
				//	g2.drawImage(img_logo, 0,0, null);
				
			//	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			//	AffineTransform transBack = g2.getTransform( );
				AffineTransform trans = g2.getTransform();
			//	 AffineTransformOp shear_op = new AffineTransformOp (trans, AffineTransformOp.TYPE_BICUBIC);
				
				trans.scale(ZOOM_X,ZOOM_Y);
				trans.translate(TransX,TransY);
				
				g2.setTransform(trans);
			
			
				if (m_imgOffScr != null)
				{
					try
					{	
						g2.drawImage(m_imgOffScr, 0, 0, this); //  Just display the "back" buffer
					}
					catch(NullPointerException e)
					{
						e.printStackTrace();
					}
				}
				trans.translate(-TransX,-TransY);
					trans.scale(1/ZOOM_X,1/ZOOM_Y);
				
					g2.setTransform(trans);
		
		}
			
			/*Point p = new Point(50,50);
			p= convertRealXYToViewXY(p.x, p.y);
			g2.drawString("Salut", p.x,p.y);*/
			
			
			Object [] objs = new Object [] { this, g2};;
			try {
				if (method!=null)
				method.invoke(mainframe, objs);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//XY_DrawText
		}
	}
	
	

	/*
	 * Update le buffer a l'ecran (rapide) 
	 * Cette methode semble etre nickel (et surtout jamais appellée :D)
	 */
	@Override
	public synchronized void update(Graphics g)
	{
	/*	if (VERBOSE == 1)
			System.err.println(this.getName()+"::Update()");
	*/	paint(g);
	}

	/*
	 * Transforme la coordonée en bonne coordonnée (avec zoom et translation)
	 */
	public Point convertViewXYtoRealXY(double X, double Y)
	{
		double x = X;
		double y = Y;

		
		if (BYPASS_ZOOMX == false)			x /= this.ZOOM_X;
		if (BYPASS_ZOOMY == false)			y /= this.ZOOM_Y;
		if (BYPASS_ZOOMX == false)			x /= this.FACTEUR_PRECISION;
		if (BYPASS_ZOOMY == false)			y /= this.FACTEUR_PRECISION;
		
		x -= this.TransX;
		y -= this.TransY;
		
		Point p = new Point((int)x, (int)y);

		return p;
	}
	
	/*
	 * Transforme la coordonée en bonne coordonnée (avec zoom et translation)
	 */
	
	/*
	 * Transforme la coordonée en bonne coordonnée (avec zoom et translation)
	 */
	public Point convertRealXYToViewXY(double X, double Y)
	{
		double x = X;
		double y = Y;

		// Facteur pour mettre le dessin centré 
		x += this.TransX;
		y += this.TransY;
	
		if (BYPASS_ZOOMX == false)			x *= this.FACTEUR_PRECISION;
		if (BYPASS_ZOOMY == false)			y *= this.FACTEUR_PRECISION;
		if (BYPASS_ZOOMX == false)			x *= this.ZOOM_X;
		if (BYPASS_ZOOMY == false)			y *= this.ZOOM_Y;
			
		Point p = new Point((int)x, (int)y);
		return p;
	}

	
	public synchronized boolean isBYPASS_ZOOMX()
	{
		return BYPASS_ZOOMX;
	}

	public synchronized void setBYPASS_ZOOMX(boolean bypass_zoomx)
	{
		BYPASS_ZOOMX = bypass_zoomx;
	}

	public synchronized boolean isBYPASS_ZOOMY()
	{
		return BYPASS_ZOOMY;
	}

	public synchronized void setBYPASS_ZOOMY(boolean bypass_zoomy)
	{
		BYPASS_ZOOMY = bypass_zoomy;
	}

	public synchronized int getDrag_OFFSX()
	{
		return drag_OFFSX;
	}

	public synchronized void setDrag_OFFSX(int drag_OFFSX)
	{
		this.drag_OFFSX = drag_OFFSX;
	}

	public synchronized double getTransX()
	{
		return TransX;
	}

	public synchronized void setTransX(double transX)
	{
	//	System.err.println("setTransX = "+transX);
		TransX = transX;
	}

	public synchronized double getTransY()
	{
		return TransY;
	}

	public synchronized void setTransY(double transY)
	{
	//	System.err.println("setTransY = "+transY);
		TransY = transY;
	}

	public synchronized double getZOOM_X()
	{
		return ZOOM_X;
	}
	public synchronized double getZOOM_Y()
	{
		return ZOOM_Y;
	}

	public synchronized void setZOOM(double zoom)
	{
		ZOOM_X = zoom;
		ZOOM_Y = zoom;
	}

	public synchronized int getBUFF_HEIGHT()
	{
		return BUFF_HEIGHT;
	}

	public synchronized void setBUFF_HEIGHT(int appheight)
	{
		BUFF_HEIGHT = appheight;
	}

	public synchronized int getBUFF_WIDTH()
	{
		return BUFF_WIDTH;
	}

	public synchronized void setBUFF_WIDTH(int appwidth)
	{
		BUFF_WIDTH = appwidth;
	}

	public synchronized double getFACTEUR_PRECISION()
	{
		return FACTEUR_PRECISION;
	}

	public synchronized void setFACTEUR_PRECISION(double facteur_precision)
	{
		FACTEUR_PRECISION = facteur_precision;
	}

	public synchronized int getMB_LEFT_CLICKED() {
		return MB_LEFT_CLICKED;
	}

	public synchronized void resizeBuffer(int i, int j) throws Exception {
		this.BUFF_WIDTH		   = (int)(i*FACTEUR_PRECISION);
		this.BUFF_HEIGHT	   = (int)(j*FACTEUR_PRECISION);
		init();
		TransX =0; 
		TransY =0;
	}
	
	  public void focusGained(FocusEvent e) {
		  System.err.println("Focus Gained");
	    }

	    public void focusLost(FocusEvent e) {
	    	System.err.println("Focus Not Gained");
	    }

	    public void setZOOM_Y(double val) {
			this.ZOOM_Y=val;
			
		}
	    public void setZOOM_X(double val) {
			this.ZOOM_X=val;
			
		}

	    public void scroll_notrelative(double f, double g) {
			this.setTransX(f);
			this.setTransY(g);
		}
	    public void scroll_relative(double f, double g) {
	    	this.setTransX(this.getTransX()+f);
	    	this.setTransY(this.getTransY()+g);
		}
	    /*
		 * In : X,Y nouvelle position de la souris
		 */
	    public void scrollMouse(int x, int y, boolean forcepaint)
		{
			if (this.MB_MIDDLE_CLICKED == 1)
			{
				if (this.SCROLLABLE_X==true)
					this.setTransX(this.getTransX()+(x-middle_clicked_x)/ZOOM_X);
				if (this.SCROLLABLE_Y==true)
					this.setTransY(this.getTransY()+(y-middle_clicked_y)/ZOOM_Y);
				middle_clicked_x = x;
				middle_clicked_y = y;
				if (forcepaint==true)
				     this.repaint();
			}
		}
	    
	    public void scrollMouse(int x, int y)
	    {
	    	scrollMouse(x, y, true);
	    }
	    
		public Rectangle Recupere_Rectangle_Vue() {
//			 Recupere la view actuelle du buffer (x,y,w,h)
			int X = -(int) this.getTransX();
			int Y = -(int) this.getTransY();
			int ww = this.getWidth();
			int hh = this.getHeight();
			Point p = this.convertViewXYtoRealXY(ww, hh);
			Rectangle Vision = new Rectangle(X,Y, p.x+1-X, p.y+1-Y);
			return Vision;
		}
		
		public void upperleftise() {
			this.setTransX(0);
			this.setTransY(0);
			this.repaint();
		}
		
		public void centerview() {
			double oldx = this.getZOOM_X();
			double oldy = this.getZOOM_Y();
			this.scroll_relative(super.getWidth()/2/oldx, super.getHeight()/2/oldy);
		}

		
		
		
		// Permet la sauvegarde du buffer actuel dans un fichier .BMP
		public synchronized BufferedImage writeBufferToFile(String filename) {
			// Write generated image to a file
			try {		        // Save as PNG
		        File file = new File(filename+".png");
		        synchronized(this)
				{
		        ImageIO.write(m_imgOffScr, "png", file);
		        // 	Save as JPEG
		        //file = new File(filename+".jpg");
		        //ImageIO.write(m_imgOffScr, "jpg", file);
				}
		    	} catch (IOException e) {
		    	
		    	}
			return m_imgOffScr;
		}
		
		public synchronized BufferedImage writeDisplayToFile(String filename)  {
			BufferedImage tamponSauvegarde = new BufferedImage(
					this.getSize().width,
					this.getSize().height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tamponSauvegarde.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getSize().width,
					this.getSize().height);
			this.paint(g);
			try
			{
				ImageIO.setUseCache(false);
				ImageIO.write(tamponSauvegarde, "PNG", new File(filename));
				return tamponSauvegarde;
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return tamponSauvegarde;
		}
	/*	public int getHeight()
		{
			return this.BUFF_HEIGHT;
		}
		public int getWidth()
		{
			return this.BUFF_WIDTH;
		}*/
		public void setXY(int XX, int YY) {
			double oldx = this.getZOOM_X();
			double oldy = this.getZOOM_Y();
			this.setZOOM(1);
			// Recupere la nef ainsi que les infos du panneau pour calculer la coordonée X,Y finale (donc par rapport a la nef)
			this.setTransX(-XX*this.getZOOM_X()*this.getFACTEUR_PRECISION());
			this.setTransY(-YY*this.getZOOM_Y()*this.getFACTEUR_PRECISION());
			this.setZOOM_X(oldx);
			this.setZOOM_Y(oldy);
			this.repaint();
		}

}