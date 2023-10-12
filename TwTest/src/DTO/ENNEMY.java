package DTO;
import java.awt.Point;
import java.util.Random;
import java.util.Vector;

import javax.vecmath.Vector2d;




public class ENNEMY {
	
	transient double position_x = 0;
	transient double position_y = 0;
	transient private int step = 0; // Position dans la liste des point a parcourir
	transient private Vector<Point> path = null;
	transient double speed = ((double)(new Random().nextInt(2)+1));
	transient float life = 100;
	
	private String image_name = "";
	
	//int cpt_speed = 0;
	
	// Pour les dommage style ICE, POISON, ETC...
	// La valeur est la valeur de dommage la plus grosse et le temps updaté.
	DammagerModif damage_modifiers = new DammagerModif();
	Direction _direction; 
	
	public synchronized DammagerModif getDamage_modifiers() {
		return damage_modifiers;
	}



	public synchronized void setDamage_modifiers(DammagerModif damage_modifiers) {
		this.damage_modifiers = damage_modifiers;
	}


	static Random rand = new Random();
	
	public ENNEMY(Point position, Vector<Point> path, int life, double speed, String image_name)
	{
		this.path = path;
		this.position_x = position.x;
		this.position_y = position.y;
		this.life=life;
		this.speed = speed;
		this.setImage_name(image_name);
	}
	
	
	
	public synchronized double getPosition_x() {
		return position_x;
	}



	public synchronized void setPosition_x(double position_x) {
		this.position_x = position_x;
	}



	public synchronized double getPosition_y() {
		return position_y;
	}



	public synchronized void setPosition_y(double position_y) {
		this.position_y = position_y;
	}


	public ENNEMY evolue()
	{
		float actual_speed = (float) speed;
		// Fait evolue les dammages.
		boolean isDammage = damage_modifiers.evolue();
		
		if (isDammage==true)
		{
			// A chaque cycle ? mais ca craind ca...
			// Applique les damage ?!
			float Damage = damage_modifiers.getDamageModifier();
			float Speed = damage_modifiers.getSpeedModifier();
			
			actual_speed /= Speed;
		//	System.err.println("Ennemy affected by speed "+Speed);
			if (Damage!=0)
			{
				System.err.println("Ennemy affected by "+Damage+" damage");
				doDamage((int)Damage);
			}
		}
		// Target point
		//Point orig_pt  = path.elementAt(step);
		Point cible_pt = path.elementAt((step+1)%path.size());
		Point position = new Point((int)this.position_x, (int)this.position_y);
		
		// On regarde si on est pas arrivé au point de destination
		if (position.distance(cible_pt)<=actual_speed*2)
		{
			if (step<path.size()-2)
			{
				step++;
			}
			else
			{
				// On recommence est arrivé au bout, alors l'ennemi meurt
				return this;
			}
		}
		
		
		double dx = (cible_pt.x)-position.x;
		double dy = (cible_pt.y)-position.y;
		
		Vector2d v1 = new Vector2d(dx,dy);
		Vector2d v2 = new Vector2d(1,0);
		v1.normalize();
		//v2.normalize();
		float angle = (int)Math.toDegrees(Math.acos(v2.dot(v1)));
		if (dy>0) 
			angle = 180+(180-angle);
		angle = (int)(angle * 8 / 360); 
		
	//	if (angle>=5)
	//	System.err.println("Angle : "+v1);
		
		if (angle==0) _direction=Direction.RIGHT;
		if (angle==1) _direction=Direction.UPRIGHT;
		if (angle==2) _direction=Direction.UP;
		if (angle==3) _direction=Direction.UPLEFT;
		if (angle==4) _direction=Direction.LEFT;
		if (angle==5) _direction=Direction.DOWNLEFT;
		if (angle==6) _direction=Direction.DOWN;
		if (angle==7) _direction=Direction.RIGHTDOWN;
		if (angle==8) _direction=Direction.RIGHT;
		
		
		
		/*if (Math.abs(dx)>Math.abs(dy))
		{
			// Gauche droite
			if (dx<0)
				_direction=Direction.LEFT;
				else
				_direction=Direction.RIGHT;
		}
		else
		{
			if ((dx>-0.5) && (dx<0.5))
			{
				if (dy<0)
					_direction=Direction.DOWN;
				else
					_direction=Direction.UP;
			}
		}*/
		
		double max = Math.abs(dx);
		if (Math.abs(dy)>max)
			max = Math.abs(dy);
		
		dx /= max;
		dy /= max;

		dx =  (dx*actual_speed);
		dy =  (dy*actual_speed);
		
		position_x+=dx/4;
		position_y+=dy/4;
		
		
		
		// Doitpas arriver a terme.
		if (position_x>4000) return this;
		if (position_x<0) return this;
		if (position_y>4000) return this;
		if (position_y<0) return this;
		
		if (this.life<=0) return this;
		return null;
		
	}



	public Point getPosition() {
		return new Point((int)this.position_x, (int)this.position_y);
	}



	public synchronized float getLife() {
		return life;
	}



	public synchronized void setLife(int life) {
		this.life = life;
	}



	public void doDamage(float damage) {
		life -= damage;
		if (life<=0) life = 0;
	}


	public void doMaladies(DammagerModif damager) {
		this.damage_modifiers.set(damager);
		
	}



	public Direction getDirection() {
		
		return _direction;
	}



	public void setImage_name(String image_name) {
		this.image_name = image_name;
	}



	public String getImage_name() {
		return image_name;
	}
}
