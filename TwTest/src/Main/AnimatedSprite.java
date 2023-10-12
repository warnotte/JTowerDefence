package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import DTO.Direction;

public class AnimatedSprite {

	
	int index = 0;
	long starttime = 0;
	
	ArrayList <BufferedImage> up = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> upright = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> right = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> downright = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> down = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> downleft = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> left = new ArrayList <BufferedImage>();
	ArrayList <BufferedImage> upleft = new ArrayList <BufferedImage>();
	
	public AnimatedSprite()
	{
		index = 0;
		starttime = System.currentTimeMillis();
	}
	public void load(String name) throws Exception
	{
		String index= ""+16;
		String dir = "Char_01_King_Arthur/"+index+"/AnimFrames/";
		up.clear();
		left.clear();
		right.clear();
		down.clear();
		upright.clear();
		downright.clear();
		downleft.clear();
		upleft.clear();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pseam = new PrintStream(out);
		
		//String ret = out.toString();
		
		ArrayList<ArrayList> list = new ArrayList<ArrayList>();
		
		
		list.add(up);
		list.add(upright);
		list.add(right);
		list.add(downright);
		list.add(down);
		list.add(downleft);
		list.add(left);
		list.add(upleft);
		
		
		
		//haut, droite, bas, gauche
		for (int j = 1 ; j < 9;j++)
		{
			ArrayList<BufferedImage> dd = list.get(j-1);
			for (int i = 1 ; i < 16;i++)
			{
				pseam.printf("%s%02d_%02d %02d.gif", dir, new Integer(""+index), j, i);
				out.flush();
				String ret = out.toString();
				System.err.println("Load sprite : "+ret);
				BufferedImage bi = loadImage(ret);
				if (bi==null)
					{System.err.println("Fuck");System.exit(-1);}
				dd.add(bi);
				out.reset();
			}
		}
		/*down.add(loadImage(name+"_bk2.gif"));
		up.add(loadImage(name+"_fr1.gif"));
		up.add(loadImage(name+"_fr2.gif"));
		left.add(loadImage(name+"_lf1.gif"));
		left.add(loadImage(name+"_lf2.gif"));
		right.add(loadImage(name+"_rt1.gif"));
		right.add(loadImage(name+"_rt2.gif"));*/
	//	index=0;
	}
	
	public static void main(String args[]) throws Exception
	{
		AnimatedSprite as = new AnimatedSprite();
		as.load("last-guardian-sprites//amg1");
		for (int i = 0;i< 50000;i++)
		{
			as.getImage(Direction.UP);
		}
	}
	public BufferedImage getImage(Direction direction)
	{
		long elapsed = System.currentTimeMillis()-starttime;
		index = (int) (elapsed/40)%15;
		
	//	System.err.println("Index "+index);
		if (direction==Direction.DOWN)
			return down.get(index);
		if (direction==Direction.DOWNLEFT)
			return downleft.get(index);
		if (direction==Direction.UPLEFT)
			return upleft.get(index);
		if (direction==Direction.UP)
			return up.get(index);
		if (direction==Direction.UPRIGHT)
			return upright.get(index);
		if (direction==Direction.RIGHTDOWN)
			return downright.get(index);
		if (direction==Direction.LEFT)
			return left.get(index);
		if (direction==Direction.RIGHT)
			return right.get(index);
		
		return null;
	}

	private BufferedImage loadImage(String name) throws Exception {
		File f = new File(name);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
		} catch (IOException e) {
			throw new Exception("Impossible de lire le fichier "+name);
		}
		
		
		Image img = makeColorTransparent(bi, Color.WHITE);
		BufferedImage bi2 = toBufferedImage(img, BufferedImage.TYPE_INT_ARGB);
		
		
		return bi2;
	}
	
	static BufferedImage toBufferedImage(Image image, int typeRGB) {
		/** On test si l'image n'est pas déja une instance de BufferedImage */
		if (image instanceof BufferedImage) {
			/** cool, rien à faire */
			return ((BufferedImage) image);
		} else {
			/** On s'assure que l'image est complètement chargée */
			image = new ImageIcon(image).getImage();

			/** On crée la nouvelle image */
			int w = image.getWidth(null);
			int h = image.getHeight(null);
			//w/=.15;
			//h/=2;
			BufferedImage bufferedImage = new BufferedImage(w,h, typeRGB);
			Graphics g = bufferedImage.createGraphics();
			g.drawImage(image, 0, 0,w,h, null);
			g.dispose();

			return (bufferedImage);
		}
	}

	public static Image makeColorTransparent(Image im, final Color color) {

		ImageFilter filter = new RGBImageFilter() {
			// Alpha bits are set to opaque, regardless of
			// what they might have been already.
			public int markerRGB = color.getRGB() | 0xFF000000;

			{
				canFilterIndexColorModel = true;
			}

			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb | 0xFF000000) == markerRGB) {
					return 0x00FFFFFF & rgb;
				} else {
					// leave the pixel untouched
					return rgb;
				}
			}
		}; // end of inner class

		// Setup to use transparency filter
		ImageProducer ip = new FilteredImageSource(im.getSource(), filter);

		return Toolkit.getDefaultToolkit().createImage(ip);
	}
}
