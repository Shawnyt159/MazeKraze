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
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import java.awt.Toolkit;

public class EnemyControls {

	private static JFrame frame;
	private static ArrayList<String> enemies = new ArrayList<String>();
	private static int currentNumberOfEnemies = 0;
	private static JSlider widthSlider;
	private static JSlider heightSlider;
	private static JSlider enemySpeedSlider;
	private static int enemySelected = 0;
	private static JComboBox<String> enemyComboBox;
	private static JComboBox<String> enemyImageComboBox;
	private SetImageToLabel images = new SetImageToLabel();
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
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(EnemyControls.class.getResource("/images/logo.png")));
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
		enemyStartLocationButton.setToolTipText("Set current enemy start location.");
		enemyStartLocationButton.setBounds(10, 28, 128, 21);
		particularEnemySettings.add(enemyStartLocationButton);
		
		JButton enemyEndLocationButtonHorizontal = new JButton("End Location Horizontal");
		enemyEndLocationButtonHorizontal.setToolTipText("Set current enemy end location left or right.");
		enemyEndLocationButtonHorizontal.setBounds(10, 59, 194, 21);
		particularEnemySettings.add(enemyEndLocationButtonHorizontal);
		
		final int minHeightOrWidth = 5;
		final int initialHeightOrWidth = 5;
		heightSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, 30, initialHeightOrWidth);
		heightSlider.setBounds(276, 28, 200, 52);
		heightSlider.setMinorTickSpacing(1);
		heightSlider.setMajorTickSpacing(5);
		heightSlider.setPaintTicks(true);
		heightSlider.setPaintLabels(true);
		particularEnemySettings.add(heightSlider);
		
		widthSlider = new JSlider(JSlider.HORIZONTAL, minHeightOrWidth, 30, initialHeightOrWidth);
		widthSlider.setBounds(276, 113, 200, 52);
		widthSlider.setMinorTickSpacing(1);
		widthSlider.setMajorTickSpacing(5);
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
		enemyEndLocationButtonVertical.setToolTipText("Set current enemy end location up or down");
		enemyEndLocationButtonVertical.setBounds(10, 90, 194, 21);
		particularEnemySettings.add(enemyEndLocationButtonVertical);
		
		JLabel enemySpeedLabel = new JLabel("Speed:");
		enemySpeedLabel.setBounds(276, 175, 200, 13);
		particularEnemySettings.add(enemySpeedLabel);
		
		enemySpeedSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 5);
		enemySpeedSlider.setToolTipText("Changes the speed of the current enemy.");
		enemySpeedSlider.setPaintTicks(true);
		enemySpeedSlider.setPaintLabels(true);
		enemySpeedSlider.setMinorTickSpacing(1);
		enemySpeedSlider.setMajorTickSpacing(3);
		enemySpeedSlider.setBounds(276, 190, 200, 52);
		particularEnemySettings.add(enemySpeedSlider);
		
		enemyImageComboBox = new JComboBox<String>();
		enemyImageComboBox.setToolTipText("Set enemy type");
		enemyImageComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Violet Orb", "Purple Orb", "Werewolf", "Cute Wolf"}));
		enemyImageComboBox.setBounds(10, 141, 194, 21);
		particularEnemySettings.add(enemyImageComboBox);
		
		JLabel enemyTypeLabel = new JLabel("Enemy Type:");
		enemyTypeLabel.setBounds(10, 118, 81, 13);
		particularEnemySettings.add(enemyTypeLabel);
		
		JButton updateImageButton = new JButton("Update Enemy Image");
		updateImageButton.setBounds(10, 171, 194, 21);
		particularEnemySettings.add(updateImageButton);
		
		enemyComboBox = new JComboBox<String>();
		enemyComboBox.setToolTipText("Current enemy.");
		enemyComboBox.setBounds(10, 10, 138, 21);
		controlPanel.add(enemyComboBox);
		
		JButton addEnemyButton = new JButton("Add Enemy");
		addEnemyButton.setToolTipText("Add enemy to list.");
		addEnemyButton.setBounds(158, 10, 123, 21);
		controlPanel.add(addEnemyButton);
		
		JButton deleteEnemyButton = new JButton("Delete Enemy");
		deleteEnemyButton.setToolTipText("Delete enemy from list.");
		deleteEnemyButton.setBounds(291, 10, 115, 21);
		controlPanel.add(deleteEnemyButton);
		
		/**
		 * BUTTON FUNCTIONS:
		 */
		addEnemyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveChangeListeners();
				if(currentNumberOfEnemies < 3) {
					AddEnemiesToComboBox(enemyComboBox);
				}
				else {
					JOptionPane.showMessageDialog(null, "You have the max number of enemies!");
				}
				AddChangeListeners();
			}
		});
		
		deleteEnemyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveChangeListeners();
				if(currentNumberOfEnemies > 0) {
					SetEnemyForDeletion(enemyComboBox);
					enemies.remove(enemyComboBox.getSelectedItem().toString());
					currentNumberOfEnemies--;
					enemyComboBox.removeItem(enemyComboBox.getSelectedItem());
					EnemyButtonFunctions.DeleteEnemy();
					SetEnemyForStartLocation(enemyComboBox);
				}
				AddChangeListeners();
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
		
		enemyComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveChangeListeners();
				SetBorderForSelectedEnemy((String) enemyComboBox.getSelectedItem());
				UpdateCurrentEnemyStats(enemyComboBox);
				AddChangeListeners();
			}
			
		});
		
		heightSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
		
		widthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
		
		enemySpeedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
		
		updateImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				getSelectedEnemy();
			}

			private void getSelectedEnemy() {
				if(enemyComboBox.getSelectedItem() == null) {
					
				}
				else if(enemyComboBox.getSelectedItem().equals("enemy 1")) {
					Enemy enemy1 = EnemyButtonFunctions.GetEnemy1();
					if(enemy1.getStartLabel() != null) {
						images.set_image_to_label(enemy1.getStartLabel(), GetEnemyImagePath());
					}
				}
				else if(enemyComboBox.getSelectedItem().equals("enemy 2")) {
					Enemy enemy2 = EnemyButtonFunctions.GetEnemy2();
					if(enemy2.getStartLabel() != null) {
						images.set_image_to_label(enemy2.getStartLabel(), GetEnemyImagePath());
					}
				}
				else if(enemyComboBox.getSelectedItem().equals("enemy 3")) {
					Enemy enemy3 = EnemyButtonFunctions.GetEnemy3();
					if(enemy3.getStartLabel() != null) {
						images.set_image_to_label(enemy3.getStartLabel(), GetEnemyImagePath());
					}
				}
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
		currentNumberOfEnemies = 0;
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
		if(enemy == null) {
			enemySelected = 0; 
			return;
		}
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
		if(enemy == null) {
			enemySelected = 0;
			return;
		}
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
		if(enemyComboBox.getSelectedItem() == null) {
			return;
		}
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
	
	private void RemoveChangeListeners() {
		ChangeListener[] heightListener = heightSlider.getChangeListeners();
		for(int i = 0; i < heightListener.length; i++) {
			heightSlider.removeChangeListener(heightListener[i]);
		}
		ChangeListener[] widthListener = widthSlider.getChangeListeners();
		for(int i = 0; i < widthListener.length; i++) {
			widthSlider.removeChangeListener(widthListener[i]);
		}
		ChangeListener[] speedListener = enemySpeedSlider.getChangeListeners();
		for(int i = 0; i < speedListener.length; i++) {
			enemySpeedSlider.removeChangeListener(speedListener[0]);
		}
	}
	
	private void AddChangeListeners() {
		heightSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
		
		widthSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
		
		enemySpeedSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				UpdateCurrentEnemy(enemyComboBox);
				
			}
		});
	}
	
	private void UpdateCurrentEnemyStats(JComboBox<String> enemyComboBox) {
		if(enemyComboBox.getSelectedItem() == null) {
			return;
		}
		if(enemyComboBox.getSelectedItem().equals("enemy 1")) {
			SetStats(EnemyButtonFunctions.GetEnemy1());
		}
		else if(enemyComboBox.getSelectedItem().equals("enemy 2")) {
			SetStats(EnemyButtonFunctions.GetEnemy2());
		}
		else if(enemyComboBox.getSelectedItem().equals("enemy 3")) {
			SetStats(EnemyButtonFunctions.GetEnemy3());
		}
	}
	
	private void SetBorderForSelectedEnemy(String selectedEnemy) {
		if(EnemyButtonFunctions.GetEnemy1() != null) {
			SetEnemyBorderNull(EnemyButtonFunctions.GetEnemy1());
		}
		if(EnemyButtonFunctions.GetEnemy2() != null) {
			SetEnemyBorderNull(EnemyButtonFunctions.GetEnemy2());
		}
		if(EnemyButtonFunctions.GetEnemy3() != null) {
			SetEnemyBorderNull(EnemyButtonFunctions.GetEnemy3());
		}
		if(selectedEnemy != null) {
			if(selectedEnemy.equals("enemy 1")) {
				AddBorderToEnemy(EnemyButtonFunctions.GetEnemy1());
			}
			else if(selectedEnemy.equals("enemy 2")) {
				AddBorderToEnemy(EnemyButtonFunctions.GetEnemy2());
			}
			else if(selectedEnemy.equals("enemy 3")) {
				AddBorderToEnemy(EnemyButtonFunctions.GetEnemy3());
			}
		}
	}
	
	private void SetEnemyBorderNull(Enemy currentEnemy) {
		if(currentEnemy.getStartLabel() != null) {
			currentEnemy.getStartLabel().setBorder(null);
		}
	}
	
	private void AddBorderToEnemy(Enemy currentEnemy) {
		if(currentEnemy.getStartLabel() != null) {
			currentEnemy.getStartLabel().setBorder(new LineBorder(Color.green, 2));
		}
	}
	
	private void SetStats(Enemy enemy) {
		if(enemy.getStartLabel() != null) {
			heightSlider.setValue(enemy.getStartLabel().getHeight());
			widthSlider.setValue(enemy.getStartLabel().getWidth());
			enemySpeedSlider.setValue(enemy.GetUserSpeed());
		}
	}
	
	public static String GetEnemyImagePath() {
		String[] enemyImagePaths = new String[] {"/images/violet orb.png", "/images/purple orb.png", "/images/werewolf.png", "/images/cute wolf.png"};
		int currentIndex = enemyImageComboBox.getSelectedIndex();
		return enemyImagePaths[currentIndex];
	}
	
	public static void ReduceNumberOfEnemies() {
		currentNumberOfEnemies--;
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
	
	public static JComboBox<String> getEnemyComboBox(){
		return enemyComboBox;
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public static void SetDisposed() {
		frame.setVisible(false);
	}
}
