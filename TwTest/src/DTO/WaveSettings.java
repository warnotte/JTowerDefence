package DTO;

import OBJ2GUI.Annotations.GUI_CLASS;
import OBJ2GUI.Annotations.GUI_FIELD_TYPE;

@GUI_CLASS(type=GUI_CLASS.Type.BoxLayout, BoxLayout_property=GUI_CLASS.Type_BoxLayout.Y)
public class WaveSettings {

	public String toString()
	{
		return "Wave Setting";
	}
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int TempsIntervalEnnemis = 2000;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int Ennemy_Vie_initiale = 100;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private double Ennemy_Speed = 2.0d;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private double Ennemy_Value = 2.0d;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int NombreEnnemy = 20;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private String Ennemy_Image_Name = "amg1";
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int Delay_Before_Start = 5000;
	@GUI_FIELD_TYPE(type=GUI_FIELD_TYPE.Type.TEXTFIELD)
	private int Income = 500;
	
	
	public synchronized double getEnnemy_Value() {
		return Ennemy_Value;
	}

	public synchronized void setEnnemy_Value(double ennemy_Value) {
		Ennemy_Value = ennemy_Value;
	}

	public synchronized int getDelay_Before_Start() {
		return Delay_Before_Start;
	}

	public synchronized void setDelay_Before_Start(int delay_Before_Start) {
		Delay_Before_Start = delay_Before_Start;
	}

	public synchronized String getEnnemy_Image_Name() {
		return Ennemy_Image_Name;
	}

	public synchronized void setEnnemy_Image_Name(String ennemy_Image_Name) {
		Ennemy_Image_Name = ennemy_Image_Name;
	}

	public int getEnnemy_Vie_initiale() {
		return Ennemy_Vie_initiale;
	}

	public void setEnnemy_Vie_initiale(int edi_ennemy_life) {
		Ennemy_Vie_initiale = edi_ennemy_life;
	}

	public double getEnnemy_Speed() {
		return Ennemy_Speed;
	}

	public void setEnnemy_Speed(double edi_ennemy_speed) {
		Ennemy_Speed = edi_ennemy_speed;
	}
	public synchronized int getTempsIntervalEnnemis()
	{
		return TempsIntervalEnnemis;
	}

	public synchronized void setTempsIntervalEnnemis(int eDIENNEMYLAUNCH)
	{
		TempsIntervalEnnemis = eDIENNEMYLAUNCH;
	}

	public synchronized int getNombreEnnemy() {
		return NombreEnnemy;
	}

	public synchronized void setNombreEnnemy(int nombreEnnemy) {
		NombreEnnemy = nombreEnnemy;
	}

	public void setIncome(int income) {
		Income = income;
	}

	public int getIncome() {
		return Income;
	}
}
