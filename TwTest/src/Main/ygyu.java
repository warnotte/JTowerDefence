package Main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

class ygyu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JLabel jLabel = null;
	Vector<BufferedImage> images = null;  //  @jve:decl-index=0:
	int w = 0;
	int h = 0;
	int Tw = w/32;
	int Th = h/32;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				ygyu thisClass;
				try {
					thisClass = new ygyu();
				
				thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				thisClass.setVisible(true);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * This is the default constructor
	 * @throws IOException 
	 */
	public ygyu() throws IOException {
		super();
		initialize();
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		System.err.println("EP");
		if (images!=null)
		//if (images.size()>30)
			for (int j = 0;j<8;j++)
				for (int i = 0;i<Tw;i++)
		{
			g2.drawImage(images.elementAt(i), j*32, i * 32, this);
			System.err.println("I"+i);
		}
	//	Vector<Image> images = load_file(new JPanel(), "F:\\Projets\\TW\\Tiles.bmp");	
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
		System.err.println("Loading");
		images = load_file(new JPanel(), "F:\\Projets\\TW\\Tiles.bmp");
		System.err.println("Ok");
		 /*for (int i = 0 ; i < images.size();i++)
		{
			JLabel label = new JLabel();
			Image img = images.elementAt(i);
			ImageIcon img2 = new ImageIcon(img);
			label.setIcon(img2);
			System.err.println(i+" of "+images.size());
			
		}*/
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.insets = new Insets(0, 0, 149, 0);
			gridBagConstraints.gridy = 0;
			gridBagConstraints.ipadx = 292;
			gridBagConstraints.ipady = 24;
			gridBagConstraints.gridx = 0;
			jLabel = new JLabel();
			jLabel.setText("JLabel");
			jLabel.setIcon(new ImageIcon("Tiles.bmp"));
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			jContentPane.add(jLabel, gridBagConstraints);
		}
		return jContentPane;
	}
	
	Vector<BufferedImage> load_file(JPanel jpanel, String filename) throws IOException
	{
		Vector<BufferedImage> retour = new Vector<BufferedImage>();
		BufferedImage img = ImageIO.read(new File(filename));
		
		 w = img.getWidth(null);
		 h = img.getHeight(null);
		 Tw = w/32;
		 Th = h/32;
		int cpt =0;
		for (int iy = 0;iy<Th;iy++)
			for (int ix = 0;ix<Tw;ix++)
			{
				cpt++;
				System.err.println(iy+"_"+ix);
				ImageFilter extractFilter=new CropImageFilter(ix*32, iy*32,32,32);
				Image image2 =  Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(img.getSource(),extractFilter));
				BufferedImage bufferedImage = new BufferedImage(
						image2.getWidth(null),
						image2.getHeight(null),
                        BufferedImage.TYPE_INT_RGB );
				Graphics g = bufferedImage.createGraphics();
				g.drawImage(image2,0,0,null);
				g.dispose();
			
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				PrintStream pseam = new PrintStream(out);
				pseam.printf("tiles\\"+"image_%06d.bmp", cpt);
				String ret = out.toString();
				
				File name = new File(ret);
				ImageIO.write(bufferedImage, "BMP", name);
				
				retour.add(bufferedImage);
			}
		return retour;
	}

}
