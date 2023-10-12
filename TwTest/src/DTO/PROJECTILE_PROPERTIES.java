package DTO;
import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class PROJECTILE_PROPERTIES 
{
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	float EDI_Damage = 1;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	float EDI_Speed = 15f;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.COMBO)
	PROJECTILE.Type EDI_type = PROJECTILE.Type.EXPLOSIVE;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	DammagerModif Damager = new DammagerModif();
	
	public synchronized final PROJECTILE.Type getEDI_type()
	{
		return EDI_type;
	}
	public synchronized final void setEDI_type(PROJECTILE.Type edi_type)
	{
		EDI_type = edi_type;
	}
	public synchronized final float getEDI_Damage()
	{
		return EDI_Damage;
	}
	public synchronized final void setEDI_Damage(int damage)
	{
		EDI_Damage = damage;
	}
	public synchronized final float getEDI_Speed()
	{
		return EDI_Speed;
	}
	public synchronized final void setEDI_Speed(float speed)
	{
		EDI_Speed = speed;
	}
	public synchronized DammagerModif getDamager() {
		return Damager;
	}
	public synchronized void setDamager(DammagerModif damager) {
		Damager = damager;
	}
	
	
}
