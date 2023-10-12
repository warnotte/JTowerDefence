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
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class SpriteLoader {

	Vector<BufferedImage> images = null;
	/**
	 * @param args
	 * @throws IOException 
	 * @throws IOException 
	 */
	
	public int getNbrImages()
	{
		return images.size();
	}
	SpriteLoader(String directory, int TypeRGB) throws IOException, InterruptedException
	{
		load_file(directory, TypeRGB);
	}
	SpriteLoader(String directory) throws IOException, InterruptedException
	{
		load_file(directory);
	}
	
	public  void main(String[] args) throws IOException, InterruptedException {
	//	load_file("tiles");
	}
	
	
	void load_file(final String directory) throws IOException, InterruptedException
	{
		load_file(directory,BufferedImage.TYPE_INT_ARGB);
	}
	
	void load_file(final String directory, final int TypeRGB) throws IOException, InterruptedException
	{
		images = new Vector<BufferedImage>();
		File dir = new File(directory);
		final String[] children = dir.list();
		
		Thread t = new Thread()
		{
			public void run()
			{
				try {
					rtr(directory, children, 0,children.length, TypeRGB);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t.start();
	/*	Thread t2 = new Thread()
		{
			public void run()
			{
				try {
					rtr(directory, children, children.length/2,children.length);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		t2.start();*/
		t.join();
		//t2.join();
		//rtr(directory, children, children.length/2,children.length);
	}
	private void rtr(String directory, String[] children, int min, int max, int typeRGB) throws IOException {
		for (int i = min ;i<max;i++)
		{
			if (children[i].toLowerCase().contains("xml"))
				continue;
			if (children[i].contains("CVS"))
				continue;
			System.err.println("Load image "+(i+1)+"/"+children.length);
			
			File f = new File(directory+"/"+children[i]);
			System.err.println("Read "+f.getAbsolutePath());
			BufferedImage bi = ImageIO.read(f);
			
if (bi==null)continue;
			
			Image img = makeColorTransparent(bi, Color.BLACK);
			BufferedImage bi2 = toBufferedImage(img, typeRGB);
			
			images.add(bi2);
		}
	}
	public BufferedImage getImage(int i) {
		return images.elementAt(i);
	}

	
	static BufferedImage toBufferedImage(Image image, int typeRGB) {
        /** On test si l'image n'est pas déja une instance de BufferedImage */
        if( image instanceof BufferedImage ) {
                /** cool, rien à faire */
                return( (BufferedImage)image );
        } else {
                /** On s'assure que l'image est complètement chargée */
                image = new ImageIcon(image).getImage();
                
                /** On crée la nouvelle image */
                BufferedImage bufferedImage = new BufferedImage(
                                                      image.getWidth(null),
                                                      image.getHeight(null),
                                                      typeRGB );
                Graphics g = bufferedImage.createGraphics();
                g.drawImage(image,0,0,null);
                g.dispose();
                
                return( bufferedImage );
        } 
}
	
	
	
	    public static Image makeColorTransparent(
	            Image im, 
	            final Color color
	        ) {

	        ImageFilter filter = 
	            new RGBImageFilter() {
	                // Alpha bits are set to opaque, regardless of 
	                // what they might have been already.
	                public int markerRGB = color.getRGB() | 0xFF000000;

	                {
	                    canFilterIndexColorModel = true;
	                }

	                public final int filterRGB(int x, int y, int rgb) {
	                    if ( ( rgb | 0xFF000000 ) == markerRGB ) {
	                        return 0x00FFFFFF & rgb;
	                    }
	                    else {
	                        // leave the pixel untouched
	                        return rgb;
	                    }
	                }
	            }; // end of inner class

	        // Setup to use transparency filter
	        ImageProducer ip = 
	            new FilteredImageSource(im.getSource(), filter);

	        return Toolkit.getDefaultToolkit().createImage(ip);
	    }

	   
	
}
