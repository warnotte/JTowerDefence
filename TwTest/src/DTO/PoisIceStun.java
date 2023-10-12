package DTO;

import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class PoisIceStun {

	enum Type {ICE, POISON, STUN};
	
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.COMBO)
	Type _Type = Type.ICE;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	long TTL;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	float Speed_divier;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	float Damage;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	int Rate;
	
	public PoisIceStun()
	{
		
	}
	//int Live = 0;
	
	public PoisIceStun(Type type, long ttl, float speed_divier, float damage, int Rate) {
		super();
		this._Type = type;
		TTL = ttl;
		this.Speed_divier = speed_divier;
		this.Damage = damage;
		this.Rate=Rate;
	}
	
	public void evolue()
	{
		if (TTL>0)
		TTL--;
	}
	public synchronized int getRate() {
		return Rate;
	}

	public synchronized void setRate(int rate) {
		Rate = rate;
	}

	public synchronized Type get_Type() {
		return _Type;
	}
	public synchronized void set_Type(Type type) {
		this._Type = type;
	}
	public synchronized long getTTL() {
		return TTL;
	}
	public synchronized void setTTL(long ttl) {
		TTL = ttl;
	}
	public synchronized float getSpeed_divier() {
		if (TTL==0)
			return 1;
		return Speed_divier;
	}
	public synchronized void setSpeed_divier(float speed_divier) {
		this.Speed_divier = speed_divier;
	}
	public synchronized float getDamage() {
		if (TTL<=0) return 0;
		if (TTL==50)
			System.err.println("toto");
		if (TTL%(int)Rate==0)
	//	if (TTL>Rate)
			return Damage;
		else
			return 0;
	}
	public synchronized void setDamage(float damage) {
		this.Damage = damage;
	}

	public void set(PoisIceStun ice) {
		this._Type = ice._Type;
		this.TTL= ice.TTL;
		this.Speed_divier= ice.Speed_divier;
		this.Damage= ice.Damage;
		
	}

	public boolean isEnable() {
		if (TTL>0) return true;
		return false;
	}
	
}
