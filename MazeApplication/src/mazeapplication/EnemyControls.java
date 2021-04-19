package mazeapplication;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.JComboBox;
import javax.swing.JSlider;

public class EnemyControls {

	private JFrame frame;
	private ArrayList<String> enemies = new ArrayList<String>();
	private int currentNumberOfEnemies = 0;
	private static JSlider widthSlider;
	private static JSlider heightSlider;
	
	private static int enemySelected = 0;
	/**
	 * Create the application.
	 */
	public EnemyControls() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(UIManager.getColor("InternalFrame.inactiveTitleGradient"));
		frame.setBounds(100, 100, 450, 366);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setBackground(new Color(119,136,153));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(new Color(119,136,153));
		controlPanel.setBounds(10, 10, 416, 309);
		frame.getContentPane().add(controlPanel);
		controlPanel.setLayout(null);
		
		Border blackBorder = BorderFactory.createLineBorder(Color.black);
		
		JPanel particularEnemySettings = new JPanel();
		particularEnemySettings.setBackground(new Color(176,192,222));
		particularEnemySettings.setBounds(10, 47, 396, 252);
		particularEnemySettings.setBorder(blackBorder);
		controlPanel.add(particularEnemySettings);
		particularEnemySettings.setLayout(null);
		
		JLabel currentEnemyControlLabel = new JLabel("Enemy Settings:");
		currentEnemyControlLabel.setBounds(10, 5, 223, 13);
		particularEnemySettings.add(currentEnemyControlLabel);
		
		JButton enemyStartLocationButton = new JButton("Start Location");
		enemyStartLocationButton.setBounds(10, 28, 128, 21);
		particularEnemySettings.add(enemyStartLocationButton);
		
		JButton enemyEndLocationButton = new JButton("End Location");
		enemyEndLocationButton.setBounds(10, 59, 128, 21);
		particularEnemySettings.add(enemyEndLocationButton);
		
		final int minHeightOrWidth = 5;
		final int maxHeightOrWidth = 20;
		final int initialHeightOrWidth = 5;
		heightSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, maxHeightOrWidth, initialHeightOrWidth);
		heightSlider.setBounds(162, 28, 200, 52);
		heightSlider.setMinorTickSpacing(1);
		heightSlider.setMajorTickSpacing(3);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		particularEnemySettings.add(heightSlider);
		
		widthSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, maxHeightOrWidth, initialHeightOrWidth);
		widthSlider.setBounds(162, 158, 200, 52);
		widthSlider.setMinorTickSpacing(1);
		widthSlider.setMajorTickSpacing(3);
		widthSlider.setPaintLabels(true);
		widthSlider.setPaintTicks(true);
		particularEnemySettings.add(widthSlider);
		
		JLabel enemyHeightLabel = new JLabel("Height:");
		enemyHeightLabel.setBounds(162, 5, 146, 13);
		particularEnemySettings.add(enemyHeightLabel);
		
		JLabel enemyWidthLabel = new JLabel("Width:");
		enemyWidthLabel.setBounds(162, 135, 200, 13);
		particularEnemySettings.add(enemyWidthLabel);
		
		JComboBox<String> enemyComboBox = new JComboBox<String>();
		enemyComboBox.setBounds(10, 10, 138, 21);
		controlPanel.add(enemyComboBox);
		
		JButton addEnemyButton = new JButton("Add Enemy");
		addEnemyButton.setBounds(158, 10, 123, 21);
		controlPanel.add(addEnemyButton);
		
		/**
		 * BUTTON FUNCTIONS:
		 */
		addEnemyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentNumberOfEnemies < 3) {
					AddEnemiesToComboBox(enemyComboBox);
				}
				else {
					JOptionPane.showMessageDialog(null, "You have the max number of enemies!");
				}
			}
			
		});
		
		enemyStartLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetEnemyStartLocation(enemyComboBox);
			}
			
		});
		
		currentNumberOfEnemies = EnemyButtonFunctions.GetNumberOfEnemies();
		frame.setVisible(true);
	}
	
	private void AddEnemiesToComboBox(JComboBox<String> enemyComboBox) {
		currentNumberOfEnemies++;
		String enemyNumber = Integer.toString(currentNumberOfEnemies);
		enemies.add("enemy " + enemyNumber);
		enemyComboBox.removeAllItems();
		for(int i = 0; i < currentNumberOfEnemies; i++) {
			enemyComboBox.addItem(enemies.get(i));
		}
	}
	
	private void SetEnemyStartLocation(JComboBox<String> enemyComboBox) {
		String enemy = (String) enemyComboBox.getSelectedItem();
		if(enemy.equals("enemy 1")) {
			enemySelected = 1;
		}
		else if(enemy.equals("enemy 2")) {
			enemySelected = 2;
		}
		else if(enemy.equals("enemy 3")) {
			enemySelected = 3;
		}
	}
	
	public static int GetEnemyWidth() {
		return widthSlider.getValue();
	}
	
	public static int GetEnemyHeight() {
		return heightSlider.getValue();
	}
	
	public static int GetEnemySelected() {
		return enemySelected;
	}
	
	public void setVisible() {
		this.frame.setVisible(true);
	}
	
	
}
