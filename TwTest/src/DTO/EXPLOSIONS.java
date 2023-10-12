package DTO;
import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class EXPLOSIONS {
	
	public int x;

	public int y;
	
	private int time_cpt=0;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.PANELISABLE)
	public
	EXPLOSIONS_PROPERTIES properties = null;
	private int range= 250; // En fait y'a pas besoin ... puisque properties sera une copie...
	
	public EXPLOSIONS(EXPLOSIONS_PROPERTIES explode_type, int x2, int y2)
	{
		this.x = x2;
		this.y = y2;
		setProperties(new EXPLOSIONS_PROPERTIES(explode_type));
		setRange(getProperties().EDI_Range);
	}

	public synchronized int getX() {
		return x;
	}

	public synchronized void setX(int x) {
		this.x = x;
	}

	public synchronized int getY() {
		return y;
	}

	public synchronized void setY(int y) {
		this.y = y;
	}

	public EXPLOSIONS evolue()
	{
		setTime_cpt(getTime_cpt() + 1);
		setRange(getRange() - getProperties().EDI_Decay);
		if (getRange()<=0)
		return this;
		return null;
	}

	public String getPanelisableName()
	{
		return "Explosion properties";
	}

	public void setProperties(EXPLOSIONS_PROPERTIES properties)
	{
		this.properties = properties;
	}

	public EXPLOSIONS_PROPERTIES getProperties()
	{
		return properties;
	}

	public void setTime_cpt(int time_cpt)
	{
		this.time_cpt = time_cpt;
	}

	public int getTime_cpt()
	{
		return time_cpt;
	}

	public void setRange(int range)
	{
		this.range = range;
	}

	public int getRange()
	{
		return range;
	}

}
