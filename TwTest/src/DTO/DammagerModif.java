package DTO;

import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class DammagerModif
{
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Ice = new PoisIceStun(PoisIceStun.Type.ICE, 0, 2, 0.1f, 50);
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Poison = new PoisIceStun(PoisIceStun.Type.POISON, 0, 1.5f, 0.1f, 50);
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Stun = new PoisIceStun(PoisIceStun.Type.STUN, 0, 0, 0.1f, 50);

	/*@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Ice = new PoisIceStun(PoisIceStun.Type.ICE, 5000, 2, 0.1f);
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Poison = new PoisIceStun(PoisIceStun.Type.POISON, 5000, 1.5f, 0.1f);
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	PoisIceStun PIS_Stun = new PoisIceStun(PoisIceStun.Type.STUN, 2000, 0, 0.1f);
*/
	/**
	 * return : False = 0 dommage
	 */
	public boolean evolue()
	{
		boolean check = true;
		PIS_Ice.evolue();
		PIS_Poison.evolue();
		PIS_Stun.evolue();
		
		if (
				(PIS_Ice.TTL==0) &&
				(PIS_Poison.TTL==0) &&
				(PIS_Stun.TTL==0)
			)
			return false;
		return true;
	}
	
	public boolean is_Ice_Enabled(){return PIS_Ice.isEnable();}
	public boolean is_Poison_Enabled(){return PIS_Poison.isEnable();}
	public boolean is_Stun_Enabled(){return PIS_Stun.isEnable();}
	
	public float getSpeedModifier()
	{
		return PIS_Ice.getSpeed_divier()*
		PIS_Poison.getSpeed_divier()*
		PIS_Stun.getSpeed_divier();
	}
	
	public float getDamageModifier()
	{
		return PIS_Ice.getDamage()+
		PIS_Poison.getDamage()+
		PIS_Stun.getDamage();
	}
	
	public synchronized PoisIceStun getPIS_Ice() {
		return PIS_Ice;
	}
	public synchronized void setPIS_Ice(PoisIceStun ice) {
		PIS_Ice = ice;
	}
	public synchronized PoisIceStun getPIS_Poison() {
		return PIS_Poison;
	}
	public synchronized void setPIS_Poison(PoisIceStun poison) {
		PIS_Poison = poison;
	}
	public synchronized PoisIceStun getPIS_Stun() {
		return PIS_Stun;
	}
	public synchronized void setPIS_Stun(PoisIceStun stun) {
		PIS_Stun = stun;
	}

	public void set(DammagerModif damager) {
		PIS_Ice.set(damager.getPIS_Ice());
		PIS_Poison.set(damager.getPIS_Poison());
		PIS_Stun.set(damager.getPIS_Stun());
		
	}
	
}
