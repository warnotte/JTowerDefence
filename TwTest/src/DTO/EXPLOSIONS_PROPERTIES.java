package DTO;
import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;


@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class EXPLOSIONS_PROPERTIES 
{
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	int EDI_Range = 50; // ? range ?
 	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	float EDI_Puissance = 0.0125f; // ? range ? 
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	int EDI_Decay = 1;

	public EXPLOSIONS_PROPERTIES()
	{
		
	}
	
	public EXPLOSIONS_PROPERTIES(int range, int puissance)
	{
		this.EDI_Range=range;
		this.EDI_Puissance=puissance;
	}
	

	public EXPLOSIONS_PROPERTIES(EXPLOSIONS_PROPERTIES explode_type)
	{
		this.EDI_Decay = explode_type.EDI_Decay;
		this.EDI_Puissance = explode_type.EDI_Puissance;
		this.EDI_Range = explode_type.EDI_Range;
	}

	public synchronized final float getEDI_Puissance()
	{
		return EDI_Puissance;
	}

	public synchronized final void setEDI_Puissance(float puissance)
	{
		EDI_Puissance = puissance;
	}

	public synchronized final int getEDI_Range()
	{
		return EDI_Range;
	}

	public synchronized final void setEDI_Range(int range)
	{
		EDI_Range = range;
	}
	public synchronized final int getEDI_Decay()
	{
		return EDI_Decay;
	}

	public synchronized final void setEDI_Decay(int decay)
	{
		EDI_Decay = decay;
	}

	
	public String getPanelisableName()
	{
		return "Explosion properties";
	}

}
