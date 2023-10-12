package Main;
import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class Configuration
{
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_GRID_BACKGROUND=false;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_GRID_TOWER=false;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_BACKGROUND_LAYER1=true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_BACKGROUND_LAYER2=true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_BACKGROUND_LAYER3=true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_BACKGROUND_LAYER4=true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_DrawSpriteEnnemy = true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_DrawPath = true;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.CHECKBOX)
	boolean EDI_Enable_DrawEnnemyLife = true;
	public synchronized boolean isEDI_Enable_GRID_BACKGROUND()
	{
		return EDI_Enable_GRID_BACKGROUND;
	}
	public synchronized boolean isEDI_Enable_GRID_TOWER()
	{
		return EDI_Enable_GRID_TOWER;
	}
	public synchronized boolean isEDI_Enable_BACKGROUND_LAYER1()
	{
		return EDI_Enable_BACKGROUND_LAYER1;
	}
	public synchronized boolean isEDI_Enable_BACKGROUND_LAYER2()
	{
		return EDI_Enable_BACKGROUND_LAYER2;
	}
	public synchronized boolean isEDI_Enable_BACKGROUND_LAYER3()
	{
		return EDI_Enable_BACKGROUND_LAYER3;
	}
	public synchronized boolean isEDI_Enable_BACKGROUND_LAYER4()
	{
		return EDI_Enable_BACKGROUND_LAYER4;
	}
	public synchronized boolean isEDI_Enable_DrawSpriteEnnemy()
	{
		return EDI_Enable_DrawSpriteEnnemy;
	}
	public synchronized boolean isEDI_Enable_DrawPath()
	{
		return EDI_Enable_DrawPath;
	}
	public synchronized boolean isEDI_Enable_DrawEnnemyLife()
	{
		return EDI_Enable_DrawEnnemyLife;
	}
	public synchronized void setEDI_Enable_GRID_BACKGROUND(boolean eDIEnableGRIDBACKGROUND)
	{
		EDI_Enable_GRID_BACKGROUND = eDIEnableGRIDBACKGROUND;
	}
	public synchronized void setEDI_Enable_GRID_TOWER(boolean eDIEnableGRIDTOWER)
	{
		EDI_Enable_GRID_TOWER = eDIEnableGRIDTOWER;
	}
	public synchronized void setEDI_Enable_BACKGROUND_LAYER1(boolean eDIEnableBACKGROUNDLAYER1)
	{
		EDI_Enable_BACKGROUND_LAYER1 = eDIEnableBACKGROUNDLAYER1;
	}
	public synchronized void setEDI_Enable_BACKGROUND_LAYER2(boolean eDIEnableBACKGROUNDLAYER2)
	{
		EDI_Enable_BACKGROUND_LAYER2 = eDIEnableBACKGROUNDLAYER2;
	}
	public synchronized void setEDI_Enable_BACKGROUND_LAYER3(boolean eDIEnableBACKGROUNDLAYER3)
	{
		EDI_Enable_BACKGROUND_LAYER3 = eDIEnableBACKGROUNDLAYER3;
	}
	public synchronized void setEDI_Enable_BACKGROUND_LAYER4(boolean eDIEnableBACKGROUNDLAYER4)
	{
		EDI_Enable_BACKGROUND_LAYER4 = eDIEnableBACKGROUNDLAYER4;
	}
	public synchronized void setEDI_Enable_DrawSpriteEnnemy(boolean eDIEnableDrawSpriteEnnemy)
	{
		EDI_Enable_DrawSpriteEnnemy = eDIEnableDrawSpriteEnnemy;
	}
	public synchronized void setEDI_Enable_DrawPath(boolean eDIEnableDrawPath)
	{
		EDI_Enable_DrawPath = eDIEnableDrawPath;
	}
	public synchronized void setEDI_Enable_DrawEnnemyLife(boolean eDIEnableDrawEnnemyLife)
	{
		EDI_Enable_DrawEnnemyLife = eDIEnableDrawEnnemyLife;
	}
	

}
