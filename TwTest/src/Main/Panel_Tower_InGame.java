package Main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import DTO.EDI_TOWER;
import DTO.Game;

public class Panel_Tower_InGame extends JPanel {

	private static final long serialVersionUID = 1L;
	private JCheckBox jCheckBox_LOCKING_TARGET = null;

	EDI_TOWER tower = null;  //  @jve:decl-index=0:
	private JButton jButton_UPGRADE_DAMAGE = null;
	private JButton jButton_UPDATE_RATE = null;
	private JButton jButton_UPGRADE_RANGE = null;
	private JPanel jPanel_Informations = null;
	private JLabel jLabel_Damage = null;
	private JTextField jTextField_Damage = null;
	private JLabel jLabel_Rate = null;
	private JTextField jTextField_Rate = null;
	private JLabel jLabel_Range = null;
	private JTextField jTextField_Range = null;
	Game game = null;
	/**
	 * This method initializes jCheckBox_LOCKING_TARGET	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getJCheckBox_LOCKING_TARGET() {
		if (jCheckBox_LOCKING_TARGET == null) {
			jCheckBox_LOCKING_TARGET = new JCheckBox();
			jCheckBox_LOCKING_TARGET.setText("Vise le meme");
			jCheckBox_LOCKING_TARGET.setHorizontalAlignment(SwingConstants.CENTER);
			jCheckBox_LOCKING_TARGET.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					tower.setEDI_LockedOnSameEnnemy(jCheckBox_LOCKING_TARGET.isSelected());
				}
			});
		}
		return jCheckBox_LOCKING_TARGET;
	}

	/**
	 * This method initializes jButton_UPGRADE_DAMAGE	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_UPGRADE_DAMAGE() {
		if (jButton_UPGRADE_DAMAGE == null) {
			jButton_UPGRADE_DAMAGE = new JButton();
			jButton_UPGRADE_DAMAGE.setText("Upg Damage (1000)");
			jButton_UPGRADE_DAMAGE.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tower!=null)
					{
					game.Upgrade_Damage(tower);
					refresh();
					}
				}
			});
		}
		return jButton_UPGRADE_DAMAGE;
	}

	/**
	 * This method initializes jButton_UPDATE_RATE	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_UPDATE_RATE() {
		if (jButton_UPDATE_RATE == null) {
			jButton_UPDATE_RATE = new JButton();
			jButton_UPDATE_RATE.setText("Upg Rate (1000)");
			jButton_UPDATE_RATE.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tower!=null)
					{
					game.Upgrade_Rate(tower);
					
					
					refresh();
					}
				}
				
			});
		}
		return jButton_UPDATE_RATE;
	}

	/**
	 * This method initializes jButton_UPGRADE_RANGE	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton_UPGRADE_RANGE() {
		if (jButton_UPGRADE_RANGE == null) {
			jButton_UPGRADE_RANGE = new JButton();
			jButton_UPGRADE_RANGE.setText("Upg Range (1000)");
			jButton_UPGRADE_RANGE.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if (tower!=null)
					{
					game.Upgrade_Range(tower);
					refresh();
					}
				}
			});
			
			
		}
		
		return jButton_UPGRADE_RANGE;
	}

	/**
	 * This method initializes jPanel_Informations	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_Informations() {
		if (jPanel_Informations == null) {
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints12.gridy = 4;
			gridBagConstraints12.ipadx = 102;
			gridBagConstraints12.weightx = 1.0;
			gridBagConstraints12.gridx = 1;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints11.gridy = 4;
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridx = 0;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints10.gridy = 2;
			gridBagConstraints10.ipadx = 102;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.gridx = 1;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints9.gridy = 2;
			gridBagConstraints9.fill = GridBagConstraints.BOTH;
			gridBagConstraints9.gridx = 0;
			GridBagConstraints gridBagConstraints8 = new GridBagConstraints();
			gridBagConstraints8.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints8.gridy = 0;
			gridBagConstraints8.ipadx = 102;
			gridBagConstraints8.weightx = 1.0;
			gridBagConstraints8.gridx = 1;
			GridBagConstraints gridBagConstraints7 = new GridBagConstraints();
			gridBagConstraints7.insets = new Insets(0, 0, 0, 0);
			gridBagConstraints7.gridy = 0;
			gridBagConstraints7.fill = GridBagConstraints.BOTH;
			gridBagConstraints7.gridx = 0;
			jLabel_Range = new JLabel();
			jLabel_Range.setText("Range");
			jLabel_Range.setHorizontalAlignment(SwingConstants.RIGHT);
			jLabel_Rate = new JLabel();
			jLabel_Rate.setText("Rate");
			jLabel_Rate.setHorizontalAlignment(SwingConstants.RIGHT);
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.fill = GridBagConstraints.BOTH;
			gridBagConstraints6.gridy = -1;
			gridBagConstraints6.weightx = 1.0;
			gridBagConstraints6.gridx = -1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.fill = GridBagConstraints.BOTH;
			gridBagConstraints5.gridy = -1;
			gridBagConstraints5.gridx = -1;
			jPanel_Informations = new JPanel();
			jPanel_Informations.setLayout(new GridBagLayout());
			jPanel_Informations.add(jLabel_Damage, gridBagConstraints7);
			jPanel_Informations.add(getJTextField_Damage(), gridBagConstraints8);
			jPanel_Informations.add(jLabel_Rate, gridBagConstraints9);
			jPanel_Informations.add(getJTextField_Rate(), gridBagConstraints10);
			jPanel_Informations.add(jLabel_Range, gridBagConstraints11);
			jPanel_Informations.add(getJTextField_Range(), gridBagConstraints12);
		}
		return jPanel_Informations;
	}

	/**
	 * This method initializes jTextField_Damage	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Damage() {
		if (jTextField_Damage == null) {
			jTextField_Damage = new JTextField();
			jTextField_Damage.setEditable(false);
			jTextField_Damage.setHorizontalAlignment(JTextField.RIGHT);
		}
		return jTextField_Damage;
	}

	/**
	 * This method initializes jTextField_Rate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Rate() {
		if (jTextField_Rate == null) {
			jTextField_Rate = new JTextField();
			jTextField_Rate.setEditable(false);
			jTextField_Rate.setHorizontalAlignment(JTextField.RIGHT);
			
		}
		return jTextField_Rate;
	}

	/**
	 * This method initializes jTextField_Range	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextField_Range() {
		if (jTextField_Range == null) {
			jTextField_Range = new JTextField();
			jTextField_Range.setEditable(false);
			jTextField_Range.setHorizontalAlignment(JTextField.RIGHT);
		}
		return jTextField_Range;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * This is the default constructor
	 */
	public Panel_Tower_InGame(Game game) {
		super();
		this.game = game;
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		jLabel_Damage = new JLabel();
		jLabel_Damage.setText("Damage");
		jLabel_Damage.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
		gridBagConstraints4.gridx = 0;
		gridBagConstraints4.fill = GridBagConstraints.BOTH;
		gridBagConstraints4.gridy = 4;
		GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
		gridBagConstraints3.gridx = 0;
		gridBagConstraints3.fill = GridBagConstraints.BOTH;
		gridBagConstraints3.gridy = 3;
		GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
		gridBagConstraints2.gridx = 0;
		gridBagConstraints2.fill = GridBagConstraints.BOTH;
		gridBagConstraints2.gridy = 2;
		GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
		gridBagConstraints1.gridx = 0;
		gridBagConstraints1.fill = GridBagConstraints.BOTH;
		gridBagConstraints1.gridy = 1;
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.gridy = 0;
		this.setSize(300, 200);
		this.setLayout(new GridBagLayout());
		this.add(getJCheckBox_LOCKING_TARGET(), gridBagConstraints);
		this.add(getJButton_UPGRADE_DAMAGE(), gridBagConstraints1);
		this.add(getJButton_UPDATE_RATE(), gridBagConstraints2);
		this.add(getJButton_UPGRADE_RANGE(), gridBagConstraints3);
		this.add(getJPanel_Informations(), gridBagConstraints4);
	}

	public synchronized EDI_TOWER getTower() {
		return tower;
	}

	public synchronized void setTower(EDI_TOWER tower) {
		this.tower = tower;
		refresh();
	}

	private void refresh() {
		
		jCheckBox_LOCKING_TARGET.setSelected(false);
		jTextField_Damage.setText("");
		jTextField_Range.setText("");
		jTextField_Rate.setText("");
		jButton_UPGRADE_DAMAGE.setEnabled(false);
		jButton_UPGRADE_RANGE.setEnabled(false);
		jButton_UPDATE_RATE.setEnabled(false);
		
		if (tower!=null)
		{
			jTextField_Damage.setText(""+tower.getDamage());
			jTextField_Range.setText(""+tower.getEDI_Range());
			jTextField_Rate.setText(""+tower.getEDI_rate());
			jCheckBox_LOCKING_TARGET.setSelected(tower.isEDI_LockedOnSameEnnemy());
			jButton_UPGRADE_DAMAGE.setEnabled(true);
			jButton_UPGRADE_RANGE.setEnabled(true);
			jButton_UPDATE_RATE.setEnabled(true);
		}
	}

	public void setGame(Game game2) {
		this.game=game2;
		refresh();
	}

	
}
