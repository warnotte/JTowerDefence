package DTO;

import java.awt.Point;

import OBJ2GUI.Annotations.GUI_FIELD_TYPE;


public class PROJECTILE 
{
	
	public enum Type {SIMPLE, EXPLOSIVE, DIRECT, LASER};
	Type type = Type.SIMPLE;
	float x, y;
	ENNEMY target = null;
	PROJECTILE_PROPERTIES properties=null;
	EXPLOSIONS_PROPERTIES explode_properties=null;
	
	public void setType(Type type) {
		this.type = type;
	}

	PROJECTILE(Point origine, ENNEMY target, Type type, PROJECTILE_PROPERTIES properties, EXPLOSIONS_PROPERTIES explode_properties)
	{
		x= origine.x;
		y= origine.y;
		this.target = target;
		this.type=type;
		this.properties = properties;
		this.explode_properties = explode_properties;
	}
	
	public PROJECTILE evolue()
	{ 
		// Si c un projectile directe alors il ne vit pas longtemps ;)
		if (type==Type.DIRECT)
		{
			// Deplace le projectile directement sur l'ennemi
			Point cible_pt = target.getPosition();
			this.x=cible_pt.x;
			this.y=cible_pt.y;
			target.doDamage(properties.EDI_Damage);
			target.doMaladies(properties.Damager);
			return this;
		}
		// Tente de se rapprocher de la cible
		Point orig_pt  = new Point((int)x,(int)y);
		Point cible_pt = target.getPosition();
		
		// On regarde si on est pas arrivé au point de destination
		if (orig_pt.distance(cible_pt)<=6.0f)
		{
			// On est pres du monstre le truc doit le tuer
			//System.err.println(this+ " j'attends ma cible");
			target.doDamage(properties.EDI_Damage);
			if (properties.Damager==null)
				System.err.println("tototototopjopj");
			target.doMaladies(properties.Damager);
			return this;
		}
		
		double dx = (cible_pt.x)-orig_pt.x;
		double dy = (cible_pt.y)-orig_pt.y;
		
		double max = Math.abs(dx);
		if (Math.abs(dy)>max)
			max = Math.abs(dy);
		
		dx /= max;
		dy /= max;

		dx =  (dx*properties.EDI_Speed);
		dy =  (dy*properties.EDI_Speed);
		
		x+=dx/10;
		y+=dy/10;
		
		// Doitpas arriver a terme.
		if (x>4000) return this;
		if (x<0) return this;
		if (y>4000) return this;
		if (y<0) return this;
		
		return null;
	}

	public int getX() {
		return (int)x;
	}
	public int getY() {
		return (int)y;
	}

	public synchronized float getDamage() {
		return properties.EDI_Damage;
	}

	public synchronized void setDamage(float damage) {
		this.properties.EDI_Damage = damage;
	}

	public Type getType()
	{
		return type;
	}

	public EXPLOSIONS getExplosion()
	{
		if (explode_properties==null)
		{
			//EXPLOSIONS_PROPERTIES explode_properties=new EXPLOSIONS_PROPERTIES();
			System.err.println("Y'a un probleme il ne devrait pas etre nulle (qd on change le _Type en explosif)");
		}
		return new EXPLOSIONS(explode_properties,getX(),getY());
	}
}
