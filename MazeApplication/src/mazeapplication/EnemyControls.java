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
import javax.swing.SwingConstants;

public class EnemyControls {

	private static JFrame frame;
	private static ArrayList<String> enemies = new ArrayList<String>();
	private static int currentNumberOfEnemies = 0;
	private static JSlider widthSlider;
	private static JSlider heightSlider;
	private static JSlider enemySpeedSlider;
	private static int enemySelected = 0;
	private static JComboBox<String> enemyComboBox;
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
		frame.setBounds(100, 100, 540, 366);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setBackground(new Color(119,136,153));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setBackground(new Color(119,136,153));
		controlPanel.setBounds(10, 10, 506, 309);
		frame.getContentPane().add(controlPanel);
		controlPanel.setLayout(null);
		
		Border blackBorder = BorderFactory.createLineBorder(Color.black);
		
		JPanel particularEnemySettings = new JPanel();
		particularEnemySettings.setBackground(new Color(176,192,222));
		particularEnemySettings.setBounds(10, 47, 486, 252);
		particularEnemySettings.setBorder(blackBorder);
		controlPanel.add(particularEnemySettings);
		particularEnemySettings.setLayout(null);
		
		JLabel currentEnemyControlLabel = new JLabel("Enemy Settings:");
		currentEnemyControlLabel.setBounds(10, 5, 128, 13);
		particularEnemySettings.add(currentEnemyControlLabel);
		
		JButton enemyStartLocationButton = new JButton("Start Location");
		enemyStartLocationButton.setBounds(10, 28, 128, 21);
		particularEnemySettings.add(enemyStartLocationButton);
		
		JButton enemyEndLocationButtonHorizontal = new JButton("End Location Horizontal");
		enemyEndLocationButtonHorizontal.setBounds(10, 59, 194, 21);
		particularEnemySettings.add(enemyEndLocationButtonHorizontal);
		
		final int minHeightOrWidth = 5;
		final int maxHeightOrWidth = 20;
		final int initialHeightOrWidth = 5;
		heightSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, maxHeightOrWidth, initialHeightOrWidth);
		heightSlider.setBounds(276, 28, 200, 52);
		heightSlider.setMinorTickSpacing(1);
		heightSlider.setMajorTickSpacing(3);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		particularEnemySettings.add(heightSlider);
		
		widthSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, maxHeightOrWidth, initialHeightOrWidth);
		widthSlider.setBounds(276, 113, 200, 52);
		widthSlider.setMinorTickSpacing(1);
		widthSlider.setMajorTickSpacing(3);
		widthSlider.setPaintLabels(true);
		widthSlider.setPaintTicks(true);
		particularEnemySettings.add(widthSlider);
		
		JLabel enemyHeightLabel = new JLabel("Height:");
		enemyHeightLabel.setBounds(276, 5, 146, 13);
		particularEnemySettings.add(enemyHeightLabel);
		
		JLabel enemyWidthLabel = new JLabel("Width:");
		enemyWidthLabel.setBounds(276, 90, 200, 13);
		particularEnemySettings.add(enemyWidthLabel);
		
		JButton enemyEndLocationButtonVertical = new JButton("End Location Vertical");
		enemyEndLocationButtonVertical.setBounds(10, 90, 194, 21);
		particularEnemySettings.add(enemyEndLocationButtonVertical);
		
		JLabel enemySpeedLabel = new JLabel("Speed:");
		enemySpeedLabel.setBounds(276, 175, 200, 13);
		particularEnemySettings.add(enemySpeedLabel);
		
		enemySpeedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 5);
		enemySpeedSlider.setPaintTicks(true);
		enemySpeedSlider.setPaintLabels(true);
		enemySpeedSlider.setMinorTickSpacing(1);
		enemySpeedSlider.setMajorTickSpacing(3);
		enemySpeedSlider.setBounds(276, 190, 200, 52);
		particularEnemySettings.add(enemySpeedSlider);
		
		JButton updateEnemyButton = new JButton("Add Changes To Enemy");
		updateEnemyButton.setBounds(10, 218, 194, 21);
		particularEnemySettings.add(updateEnemyButton);
		
		enemyComboBox = new JComboBox<String>();
		enemyComboBox.setBounds(10, 10, 138, 21);
		controlPanel.add(enemyComboBox);
		
		JButton addEnemyButton = new JButton("Add Enemy");
		addEnemyButton.setBounds(158, 10, 123, 21);
		controlPanel.add(addEnemyButton);
		
		JButton deleteEnemyButton = new JButton("Delete Enemy");
		deleteEnemyButton.setBounds(291, 10, 115, 21);
		controlPanel.add(deleteEnemyButton);
		
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
		
		deleteEnemyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(currentNumberOfEnemies > 0) {
					SetEnemyForDeletion(enemyComboBox);
					enemies.remove(enemyComboBox.getSelectedItem().toString());
					currentNumberOfEnemies--;
					enemyComboBox.removeItem(enemyComboBox.getSelectedItem());
					EnemyButtonFunctions.DeleteEnemy();
				}
			}
			
		});
		
		enemyStartLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetEnemyForStartLocation(enemyComboBox);
				EnemyButtonFunctions.SetEnemyControl(1);
				enemyStartLocationButton.setForeground(new Color(255, 69, 0));
				enemyEndLocationButtonHorizontal.setForeground(Color.black);
				enemyEndLocationButtonVertical.setForeground(Color.black);
			}
			
		});
		
		enemyEndLocationButtonHorizontal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetEnemyForEndLocation(enemyComboBox);
				EnemyButtonFunctions.SetEnemyControl(2);
				enemyEndLocationButtonHorizontal.setForeground(new Color(255, 69, 0));
				enemyEndLocationButtonVertical.setForeground(Color.black);
				enemyStartLocationButton.setForeground(Color.black);
			}
			
		});
		
		enemyEndLocationButtonVertical.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetEnemyForEndLocation(enemyComboBox);
				EnemyButtonFunctions.SetEnemyControl(3);
				enemyEndLocationButtonVertical.setForeground(new Color(255, 69, 0));
				enemyEndLocationButtonHorizontal.setForeground(Color.black);
				enemyStartLocationButton.setForeground(Color.black);
			}
			
		});
		
		updateEnemyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
			}
			
		});
		frame.setVisible(true);
	}
	
	private void AddEnemiesToComboBox(JComboBox<String> enemyComboBox) {
		currentNumberOfEnemies++;
		if(enemies.isEmpty()) {
			enemies.add("enemy 1");
		}
		else if(enemies.contains("enemy 1") && !enemies.contains("enemy 2")) {
			enemies.add("enemy 2");
		}
		else if(enemies.contains("enemy 1") && enemies.contains("enemy 2")) {
			enemies.add("enemy 3");
		}
		else if(enemies.contains("enemy 2") && !enemies.contains("enemy 1")) {
			enemies.add("enemy 1");
		}
		else if(enemies.contains("enemy 2") && enemies.contains("enemy 3")) {
			enemies.add("enemy 1");
		}
		else if(enemies.contains("enemy 3") && enemies.contains("enemy 1")) {
			enemies.add("enemy 2");
		}
		else if(enemies.contains("enemy 3") && !enemies.contains("enemy 2")) {
			enemies.add("enemy 1");
		}
		enemyComboBox.removeAllItems();
		for(int i = 0; i < currentNumberOfEnemies; i++) {
			enemyComboBox.addItem(enemies.get(i));
		}
	}
	
	public static void ResetEnemies() {
		enemies.clear();
		enemyComboBox.removeAllItems();
	}
	
	public static void AddEnemy1() {
		enemies.add("enemy 1");
		enemyComboBox.addItem("enemy 1");
		currentNumberOfEnemies++;
	}
	
	public static void AddEnemy2() {
		enemies.add("enemy 2");
		enemyComboBox.addItem("enemy 2");
		currentNumberOfEnemies++;
	}
	
	public static void AddEnemy3() {
		enemies.add("emeny 3");
		enemyComboBox.addItem("enemy 3");
		currentNumberOfEnemies++;
	}
	
	private void SetEnemyForStartLocation(JComboBox<String> enemyComboBox) {
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
	
	private void SetEnemyForEndLocation(JComboBox<String> enemyComboBox) {
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
	
	private void SetEnemyForDeletion(JComboBox<String> enemyComboBox) {
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
	
	private void UpdateCurrentEnemy(JComboBox<String> enemyComboBox) {
		if(enemyComboBox.getSelectedItem().equals("enemy 1")) {
			EnemyButtonFunctions.UpdateEnemy1();
		}
		else if(enemyComboBox.getSelectedItem().equals("enemy 2")) {
			EnemyButtonFunctions.UpdateEnemy2();
		}
		else if(enemyComboBox.getSelectedItem().equals("enemy 3")) {
			EnemyButtonFunctions.UpdateEnemy3();
		}
		else {
			
		}
	}
	
	public static int GetEnemyWidth() {
		return widthSlider.getValue();
	}
	
	public static int GetEnemyHeight() {
		return heightSlider.getValue();
	}
	
	public static int GetEnemySpeed() {
		return enemySpeedSlider.getValue();
	}
	
	public static int GetEnemySelected() {
		return enemySelected;
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public static void SetDisposed() {
		frame.setVisible(false);
	}
}
