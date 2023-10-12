package DTO;


import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import OBJ2GUI.Annotations.GUI_CLASS;



public class MAP implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7296865244048232288L;

	Vector<Point> path_a_parcourir = null; // Chemin que doivent prendre les enemmis
	
	int BACKGROUND_ID[][][] = null;
	
	int width, heigth;
	static Random rand = new Random();
	
	public MAP(int width, int height, int GRIDSIZE_PATH)
	{
		this.width= width;
		this.heigth=height;
		BACKGROUND_ID = new int [4][width][heigth];
		clear(GRIDSIZE_PATH);
	}
	public void save() throws IOException
	{
		File f = new File("map.dat");
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream dos = new ObjectOutputStream(fos);
		dos.writeObject(this);
		dos.flush();
		dos.close();
		fos.flush();
		fos.close();
	}
	public static MAP load() throws IOException, ClassNotFoundException
	{
		File f = new File("map.dat");
		FileInputStream fos = new FileInputStream(f);
		ObjectInputStream dos = new ObjectInputStream(fos);
		return (MAP) dos.readObject();
		//ObjectInputStream
	}

	public synchronized void setBackgroundTile(int x, int y, int layer, int id)
	{
		BACKGROUND_ID[layer][x][y]=id;
	}
	public synchronized int getBackgroundTile(int x, int y, int layer)
	{
		return BACKGROUND_ID[layer][x][y];
	}
	
	public synchronized Vector<Point> getPath_a_parcourir() {
		return path_a_parcourir;
	}

	public synchronized void setPath_a_parcourir(Vector<Point> path_a_parcourir) {
		this.path_a_parcourir = path_a_parcourir;
	}

	public synchronized int getWidth() {
		return width;
	}

	public synchronized void setWidth(int width) {
		this.width = width;
	}

	public synchronized int getHeigth() {
		return heigth;
	}

	public synchronized void setHeigth(int heigth) {
		this.heigth = heigth;
	}
	public void clear(int GRIDSIZE_PATH) {
		for (int i = 0 ;i< width;i++)
			for (int j = 0 ;j< heigth;j++)
			{
				BACKGROUND_ID[0][i][j]= rand.nextInt(4)+1;
				BACKGROUND_ID[1][i][j]= -1;
				BACKGROUND_ID[2][i][j]= -1;
				BACKGROUND_ID[3][i][j]= -1;
			}
		if (path_a_parcourir!=null)
		path_a_parcourir.clear();
		else
		 path_a_parcourir = new Vector<Point>();
		Point start_pt = new Point(2*GRIDSIZE_PATH,2*GRIDSIZE_PATH);
		path_a_parcourir.add(start_pt);
		path_a_parcourir.add(new Point((width-2)*GRIDSIZE_PATH,(heigth-2)*GRIDSIZE_PATH));
		
	}

}

