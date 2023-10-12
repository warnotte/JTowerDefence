package Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import DTO.EDI_TOWER;

class ComplexCellRenderer implements ListCellRenderer {

  protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
  SpriteLoader sp = null;
  
  public ComplexCellRenderer() throws IOException, InterruptedException
  {
	  sp = new SpriteLoader("Towers", BufferedImage.TYPE_INT_RGB);
  }  
  
  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    Font theFont = null;
	    Color theForeground = null;
	    Icon theIcon = null;
	    String theText = null;
	    
	    final EDI_TOWER tower = ((EDI_TOWER)value);

	    JPanel panel = new JPanel()
	    {
	    	public void paint(Graphics g)
	    	{
	    		super.paint(g);
	    		if (tower.getEDI_properties().getDamager().is_Ice_Enabled()==true)
	    		{
	    		g.setColor(Color.BLUE);
	    		g.fillRect(5,46-10-5,10,10);
	    		}
	    		if (tower.getEDI_properties().getDamager().is_Poison_Enabled()==true)
	    		{
	    		g.setColor(Color.GREEN);
	    		g.fillRect(16,46-10-5,10,10);
	    		}
	    		if (tower.getEDI_properties().getDamager().is_Stun_Enabled()==true)
	    		{
	    		g.setColor(Color.RED);
	    		g.fillRect(27,46-10-5,10,10);
	    		}
	    	}
	    };
	    
	    JLabel text = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    JLabel icon = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    panel.add(text);
	    panel.add(icon);
	    panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	    
	      theIcon = new ImageIcon(sp.getImage(tower.getImageIndex()));
	    	
	      theFont = list.getFont();
	      theForeground = list.getForeground();
	      theText = ""+tower.getEDI_Name()+" - "+tower.getEDI_properties().getEDI_type().toString()+" - "+tower.getPrice();
	    
	 //   if (!isSelected) {
	    	//panel.setBackground(theForeground);
	    icon.setIcon(theIcon);
	    text.setText(theText);
	    text.setFont(theFont);
	    
	    return panel;
	  }
  /*
  public Component getListCellRendererComponent(JList list, Object value, int index,
	      boolean isSelected, boolean cellHasFocus) {
	    Font theFont = null;
	    Color theForeground = null;
	    Icon theIcon = null;
	    String theText = null;
	    
	    EDI_TOWER tower = ((EDI_TOWER)value);

	    JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
	    
	    if (value instanceof Object[]) {
	      Object values[] = (Object[]) value;
	      theFont = (Font) values[0];
	      theForeground = (Color) values[1];
	      theIcon = (Icon) values[2];
	      theText = (String) values[3];
	    } else {
	    	
	      theIcon = new ImageIcon(sp.getImage(tower.getImageIndex()));
	    	
	      theFont = list.getFont();
	      theForeground = list.getForeground();
	      theText = ""+tower.getEDI_Name()+" - "+tower.getEDI_properties().getEDI_type().toString()+" - "+tower.getPrice();
	    }
	    if (!isSelected) {
	      renderer.setForeground(theForeground);
	    }
	    if (theIcon != null) {
	      renderer.setIcon(theIcon);
	    }
	    renderer.setText(theText);
	    renderer.setFont(theFont);
	    
	    return renderer;
	  }*/
}