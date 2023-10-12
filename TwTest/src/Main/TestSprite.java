package Main;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.GridBagLayout;

public class TestSprite extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JPanel jPanel_TILES = null;

	/**
	 * This is the default constructor
	 */
	public TestSprite() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(300, 200);
		this.setContentPane(getJContentPane());
		this.setTitle("JFrame");
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new BorderLayout());
			jContentPane.add(getJPanel_TILES(), BorderLayout.CENTER);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jPanel_TILES	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanel_TILES() {
		if (jPanel_TILES == null) {
			jPanel_TILES = new JPanel();
			jPanel_TILES.setLayout(new GridBagLayout());
		}
		return jPanel_TILES;
	}

}
