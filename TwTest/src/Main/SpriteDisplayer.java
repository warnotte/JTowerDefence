package Main;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SpriteDisplayer extends JPanel {

	private static final long serialVersionUID = 1L;
	JPanel jpanel_general = null;
	JPanel jpanel_tiles = null;
	private int id_selected;

	SpriteLoader spr = null;
	private JLabel jLabel_SelectedIcon = null;
	private int LayerSelected;
	private JComboBox jComboBox_Layer = null;;

	/**
	 * This method initializes jComboBox_Layer	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox_Layer() {
		if (jComboBox_Layer == null) {
			jComboBox_Layer = new JComboBox();
			jComboBox_Layer.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					LayerSelected=jComboBox_Layer.getSelectedIndex();
				}
			});
			jComboBox_Layer.insertItemAt("0", 0);
			jComboBox_Layer.insertItemAt("1", 1);
			jComboBox_Layer.insertItemAt("2", 2);
			jComboBox_Layer.insertItemAt("3", 3);
		}
		return jComboBox_Layer;
	}

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		JFrame f = new JFrame();
		SpriteDisplayer panel = new SpriteDisplayer();
		f.setContentPane(panel);
		f.setVisible(true);
	}

	/**
	 * This is the default constructor
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public SpriteDisplayer() throws IOException, InterruptedException {
		super();
		spr = new SpriteLoader("Ttiles");
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getJPanelNorth(), BorderLayout.NORTH);
		this.add(new JScrollPane(getJPanelTile()), BorderLayout.CENTER);
		
	}

	private JPanel getJPanelNorth() {
		jLabel_SelectedIcon = new JLabel();
		jLabel_SelectedIcon.setText("JLabel");
		jpanel_general = new JPanel();
		jpanel_general.add(jLabel_SelectedIcon, null);
		jpanel_general.add(getJComboBox_Layer(), null);
		return jpanel_general;
	}
	
	private JPanel getJPanelTile() {
		int nbr_cols = 7;
		int nbr_rows = spr.getNbrImages()/nbr_cols;
		jpanel_tiles = new JPanel();
		jpanel_tiles.setSize(nbr_cols*32, nbr_rows*32);
		GridLayout gd = new GridLayout();
		gd.setColumns(nbr_cols);
		gd.setRows(nbr_rows);
		jpanel_tiles.setLayout(gd);
		for (int i = 0 ; i < spr.getNbrImages(); i++)
		{
			final int ValeurId = i;
			System.err.println("Layout "+(i)+"/"+spr.getNbrImages());
			JLabel label = new JLabel();
			label.setName("Image_"+i);
			//label.setText("Image_"+i);
			
			label.addMouseListener(new java.awt.event.MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					id_selected = ValeurId;
					System.err.println("SpriteSelected=="+id_selected);
					
					BufferedImage db = spr.getImage(id_selected);
					ImageIcon ic = new ImageIcon(db);
					jLabel_SelectedIcon.setIcon(ic);
				}
			});
			BufferedImage db = spr.getImage(i);
			if (db==null)
				continue;
			ImageIcon ic = new ImageIcon(db);
	
			//	System.err.println("RE");
			label.setIcon(ic);
			jpanel_tiles.add(label);
		}
		return jpanel_tiles;
	}

	public synchronized int getId_selected() {
		return id_selected;
	}

	public synchronized void setId_selected(int id_selected) {
		this.id_selected = id_selected;
	}
	
	public int getLayerSelected() {
		return LayerSelected;
	}
	public void setLayerSelected(int l ) {
		this.LayerSelected = l;
	}
	

}
