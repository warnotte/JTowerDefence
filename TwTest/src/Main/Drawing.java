package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import DTO.DammagerModif;
import DTO.Direction;
import DTO.EDI_TOWER;
import DTO.ENNEMY;
import DTO.Game;

public class Drawing {

	Game game = null;
	Configuration configuration = null;
	Random rand = new Random();
	SpriteLoader sprites = null;
	SpriteLoader Towers = null;
	
	//AnimatedSprite as = new AnimatedSprite();
	HashMap<String, AnimatedSprite> ennemies_sprites = new HashMap<String, AnimatedSprite>();
	
	
	
	public Drawing(Game g, Configuration c, SpriteLoader sp) throws Exception
	{
		Towers = new SpriteLoader("Towers", BufferedImage.TYPE_INT_RGB);
		this.game = g;
		this.configuration = c;
		this.sprites = sp;
		
		File f = new File("last-guardian-sprites");
		String list[] = f.list();
		// Faut filtrer.
		for (int i = 0; i < list.length; i++) {
			String s = list[i];
			if (s.contains("CVS")==true)continue;
			if (s.contains(".")==false) continue;
			// Si le nom fini par _lf1. c good extract
			System.err.println("ee "+s);
			if (s.contains("_lf1"))
			{
				s=s.substring(0, s.indexOf("_"));
				System.err.println("Load "+s);
				AnimatedSprite asM = new AnimatedSprite();
				asM.load("last-guardian-sprites//"+s);
				ennemies_sprites.put(s,asM);
			}
		}
	//	as.load("last-guardian-sprites//amg1");
		
		
	}
	public void setGame(Game game)
	{
		this.game=game;
	}

	public void Draw_EDI_TOWER(Graphics2D g2, EDI_TOWER e) {
	/*	g2.setStroke(new BasicStroke ());
		g2.setColor(Color.ORANGE);
		g2.fillOval((int)(e.getX()), (int)(e.getY()), game.getGRIDSIZE_TOWER(), game.getGRIDSIZE_TOWER());
	*/	
		BufferedImage bi = Towers.getImage(e.getImageIndex());
		g2.drawImage(bi, (int)(e.getX()), (int)(e.getY()), game.getGRIDSIZE_TOWER(), game.getGRIDSIZE_TOWER(), null);
		
	//	g2.fillOval((int)(e.getX()), (int)(e.getY()), game.getGRIDSIZE_TOWER(), game.getGRIDSIZE_TOWER());
	}
	public void Draw_ENNEMY(Graphics2D g2, ENNEMY e) {
		if (configuration.isEDI_Enable_DrawSpriteEnnemy()==true)
		{
			Direction direction = e.getDirection();
			
			
			AnimatedSprite as = ennemies_sprites.get(e.getImage_name());
			if (as!=null)
			{
				BufferedImage img = as.getImage(direction);//as.getImage(direction);
				g2.drawImage(img, (int)(e.getPosition_x()-16), (int)(e.getPosition_y()-32), null);
			}
			else
			{
				g2.drawRect((int)(e.getPosition_x()-16), (int)(e.getPosition_x()-16), 32, 32);
			}
			/*if (rand.nextBoolean()==true)
				g2.drawImage(sprites.getImage(314), (int)(e.getPosition_x()-16), (int)(e.getPosition_y()-32), null);
			else
			g2.drawImage(sprites.getImage(313),  (int)(e.getPosition_x()-16), (int)(e.getPosition_y()-32), null);
		*/}
		else
		{
			g2.setColor(new Color(0,(int)( e.getLife()*2.5),0));
			g2.fillOval((int)(e.getPosition_x()-3), (int)(e.getPosition_y()-3), 6,6);
		}
		if (configuration.isEDI_Enable_DrawEnnemyLife())
		{
			int h =8;
			int w = 32;
			g2.translate(-16, 2);
			g2.setColor(Color.white);
			g2.drawRect((int)(e.getPosition_x()),(int)( e.getPosition_y()+0), w, h);
			g2.setColor(Color.red);
			g2.fillRect((int)(e.getPosition_x())+2,(int)(e.getPosition_y()+0)+2, (int)((w-3)*((float)e.getLife()/100.0f)), h-3);
			g2.translate(16,-2);
		}
		
		DammagerModif dm = e.getDamage_modifiers();
		if (dm.is_Ice_Enabled()==true)
		{
			g2.setColor(Color.BLUE);
			g2.fillRect((int)(e.getPosition_x()+18), (int)(e.getPosition_y())+10+0, 4, 4);
		}
		if (dm.is_Poison_Enabled()==true)
		{
			g2.setColor(Color.GREEN);
			g2.fillRect((int)(e.getPosition_x()+18), (int)(e.getPosition_y())+10+4, 4, 4);
		}
		if (dm.is_Stun_Enabled()==true)
		{
			g2.setColor(Color.RED);
			g2.fillRect((int)(e.getPosition_x()+18), (int)(e.getPosition_y())+10+8, 4, 4);
		}
	}
	
}
