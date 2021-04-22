package mazeapplication;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.EOFException;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class MazeMainGUI implements MouseMotionListener, MouseListener, KeyListener {

	/**
	 * RULES: 1.) New Button: Disable focusable Add button to start maze function
	 * for it to be disabled. Add button to stop maze function for it to be enabled.
	 * 
	 */
	
	private JFrame frame;
	private JSlider lineThicknessSlider;
	private JSlider eraserThicknessSlider;
	private static JPanel mazePanel;
	private static JButton startMazeButton;
	private JComboBox<String> colorComboBox;
	private static EnemyControls enemyControls;
	private final HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private static boolean enemyControlsExists = false;

	/*
	 * selected: 0-7 
	 * 1: start location 
	 * 2: end location 
	 * 3: free draw line 
	 * 4: eraser
	 * 5: horizontal line 
	 * 6: vertical line 
	 * 7: Enemies Option Panel
	 */

	private static int selected = 0;
	private int startLocationSelected = 1;
	private int endLocationSelected = 2;
	private int freeDrawLineSelected = 3;
	private int eraserSelected = 4;
	private int horizontalLineSelected = 5;
	private int verticalLineSelected = 6;
	private int enemiesSelected = 7;

	private static JLabel player;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MazeMainGUI window = new MazeMainGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MazeMainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 928, 712);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(119, 136, 153));

		Border blackBorder = BorderFactory.createLineBorder(Color.black);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setBackground(new Color(176, 196, 222));
		optionsPanel.setBounds(726, 10, 172, 471);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		optionsPanel.setBorder(blackBorder);

		JButton startLocationButton = new JButton("Start Location");
		startLocationButton.setBounds(10, 10, 152, 20);
		optionsPanel.add(startLocationButton);

		JButton endLocationButton = new JButton("End Location");
		endLocationButton.setBounds(10, 38, 152, 20);
		optionsPanel.add(endLocationButton);

		JButton freeDrawButton = new JButton("Free Draw");
		freeDrawButton.setBounds(10, 68, 152, 20);
		optionsPanel.add(freeDrawButton);

		final int minLineThickness = 1;
		final int maxLineThickness = 10;
		final int initialLineThickness = 5;
		lineThicknessSlider = new JSlider(JSlider.HORIZONTAL, minLineThickness, maxLineThickness, initialLineThickness);
		lineThicknessSlider.setBounds(10, 231, 152, 43);
		lineThicknessSlider.setMajorTickSpacing(3);
		lineThicknessSlider.setMinorTickSpacing(1);
		lineThicknessSlider.setPaintTicks(true);
		lineThicknessSlider.setPaintLabels(true);
		optionsPanel.add(lineThicknessSlider);

		JLabel lineThicknessLabel = new JLabel("Line Thickness:");
		lineThicknessLabel.setBounds(10, 208, 100, 13);
		optionsPanel.add(lineThicknessLabel);

		JButton eraserButton = new JButton("Eraser");
		eraserButton.setBounds(10, 284, 152, 21);
		optionsPanel.add(eraserButton);

		JButton horizontalLineButton = new JButton("Horizontal Line");
		horizontalLineButton.setBounds(10, 98, 152, 21);
		optionsPanel.add(horizontalLineButton);
		horizontalLineButton.setFocusable(false);

		JButton verticalLineButton = new JButton("Vertical Line");
		verticalLineButton.setBounds(10, 129, 152, 21);
		optionsPanel.add(verticalLineButton);
		verticalLineButton.setFocusable(false);

		JButton clearMazeButton = new JButton("Clear Maze");
		clearMazeButton.setBounds(10, 396, 152, 21);
		optionsPanel.add(clearMazeButton);
		clearMazeButton.setFocusable(false);

		JLabel eraserThicknessLabel = new JLabel("Eraser Size:");
		eraserThicknessLabel.setBounds(10, 315, 100, 13);
		optionsPanel.add(eraserThicknessLabel);

		final int minEraserThickness = 1;
		final int maxEraserThickness = 10;
		final int initialEraserThickness = 5;
		eraserThicknessSlider = new JSlider(JSlider.HORIZONTAL, minEraserThickness, maxEraserThickness,
				initialEraserThickness);
		eraserThicknessSlider.setBounds(10, 338, 152, 48);
		eraserThicknessSlider.setMajorTickSpacing(3);
		eraserThicknessSlider.setMinorTickSpacing(1);
		eraserThicknessSlider.setPaintTicks(true);
		eraserThicknessSlider.setPaintLabels(true);
		optionsPanel.add(eraserThicknessSlider);

		colorComboBox = new JComboBox<String>();
		colorComboBox.setBounds(10, 177, 152, 21);
		optionsPanel.add(colorComboBox);
		colorComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Black", "Red", "Yellow", "Orange", "Pink", "Purple", "Blue", "Gray", "Green", "Brown",
						"Lavender", "Navy Blue", "Army Green", "Plum", "Turquoise", "Maroon", "Storm", "Salmon" }));

		JLabel lblLineColor = new JLabel("Line Color:");
		lblLineColor.setBounds(10, 160, 100, 13);
		optionsPanel.add(lblLineColor);

		JButton enemiesButton = new JButton("Enemies");
		enemiesButton.setBounds(10, 427, 152, 21);
		optionsPanel.add(enemiesButton);
		enemiesButton.setFocusable(false);

		mazePanel = new MazePanel();
		mazePanel.setBackground(Color.WHITE);
		mazePanel.setBounds(10, 10, 708, 648);
		frame.getContentPane().add(mazePanel);
		mazePanel.setBorder(blackBorder);
		mazePanel.setLayout(null);

		startMazeButton = new JButton("Start Maze");
		startMazeButton.setBounds(726, 491, 172, 21);
		frame.getContentPane().add(startMazeButton);
		startMazeButton.setFocusable(false);

		JButton stopMazeButton = new JButton("Stop Maze");
		stopMazeButton.setEnabled(false);
		stopMazeButton.setBounds(726, 522, 172, 21);
		frame.getContentPane().add(stopMazeButton);
		stopMazeButton.setFocusable(false);

		JButton loadMazeButton = new JButton("Load Maze");
		loadMazeButton.setBounds(726, 637, 172, 21);
		frame.getContentPane().add(loadMazeButton);
		loadMazeButton.setFocusable(false);

		JButton saveMazeButton = new JButton("Save Maze");
		saveMazeButton.setBounds(726, 606, 172, 21);
		frame.getContentPane().add(saveMazeButton);
		saveMazeButton.setFocusable(false);

		JButton saveMazeAsImageButton = new JButton("Save Maze As Image");
		saveMazeAsImageButton.setBounds(726, 575, 172, 21);
		saveMazeAsImageButton.setFocusable(false);
		frame.getContentPane().add(saveMazeAsImageButton);

		// Button Actions
		startLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = startLocationSelected;
				startLocationButton.setForeground(new Color(255, 69, 0));
				endLocationButton.setForeground(Color.black);
				freeDrawButton.setForeground(Color.black);
				eraserButton.setForeground(Color.black);
				horizontalLineButton.setForeground(Color.black);
				verticalLineButton.setForeground(Color.black);
			}
		});

		endLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = endLocationSelected;
				endLocationButton.setForeground(new Color(255, 69, 0));
				startLocationButton.setForeground(Color.black);
				freeDrawButton.setForeground(Color.black);
				eraserButton.setForeground(Color.black);
				horizontalLineButton.setForeground(Color.black);
				verticalLineButton.setForeground(Color.black);
			}

		});

		freeDrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = freeDrawLineSelected;
				freeDrawButton.setForeground(new Color(255, 69, 0));
				startLocationButton.setForeground(Color.black);
				endLocationButton.setForeground(Color.black);
				eraserButton.setForeground(Color.black);
				horizontalLineButton.setForeground(Color.black);
				verticalLineButton.setForeground(Color.black);
			}

		});

		eraserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = eraserSelected;
				eraserButton.setForeground(new Color(255, 69, 0));
				startLocationButton.setForeground(Color.black);
				endLocationButton.setForeground(Color.black);
				freeDrawButton.setForeground(Color.black);
				horizontalLineButton.setForeground(Color.black);
				verticalLineButton.setForeground(Color.black);
			}

		});

		horizontalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = horizontalLineSelected;
				horizontalLineButton.setForeground(new Color(255, 69, 0));
				startLocationButton.setForeground(Color.black);
				endLocationButton.setForeground(Color.black);
				freeDrawButton.setForeground(Color.black);
				verticalLineButton.setForeground(Color.black);
				eraserButton.setForeground(Color.black);
			}

		});

		verticalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = verticalLineSelected;
				verticalLineButton.setForeground(new Color(255, 69, 0));
				startLocationButton.setForeground(Color.black);
				endLocationButton.setForeground(Color.black);
				freeDrawButton.setForeground(Color.black);
				eraserButton.setForeground(Color.black);
				horizontalLineButton.setForeground(Color.black);
			}
		});

		startMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (CheckForStartAndEndLocations() == true) {
					SetMazePanelSettingsForStartOfMaze(mazePanel);
					DisableButtonsForStartMaze(startLocationButton, endLocationButton, freeDrawButton, startMazeButton,
							eraserButton, loadMazeButton, saveMazeButton, horizontalLineButton, verticalLineButton,
							clearMazeButton, saveMazeAsImageButton, colorComboBox, enemiesButton);
					stopMazeButton.setEnabled(true);
					if(enemyControlsExists == true) {
						EnemyControls.SetDisposed();
					}
					EnemyStartAndStopMazeFunctions.StartEnemies();
				} else {
					JOptionPane.showMessageDialog(null, "Maze can only start with designated start and end locations!");
				}
			}
		});

		stopMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetMazePanelSettingsForStopMaze(mazePanel);
				EnableButtonsForStopMaze(startLocationButton, endLocationButton, freeDrawButton, startMazeButton,
						eraserButton, loadMazeButton, saveMazeButton, horizontalLineButton, verticalLineButton,
						clearMazeButton, saveMazeAsImageButton, colorComboBox, enemiesButton);
				EnemyStartAndStopMazeFunctions.StopEnemies();
				stopMazeButton.setEnabled(false);
			}

		});

		clearMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to clear the maze?") == JOptionPane.YES_OPTION) {
					if (ButtonFunctions.StartLocationExists() == true) {
						mazePanel.remove(ButtonFunctions.GetStartLabel());
						ButtonFunctions.SetStartLocationExistsFalse();
					}
					if (ButtonFunctions.EndLocationExists() == true) {
						mazePanel.remove(ButtonFunctions.GetEndLabel());
						ButtonFunctions.SetEndLocationExistsFalse();
					}
					ButtonFunctions.GetQ1().clear();
					ButtonFunctions.GetQ2().clear();
					ButtonFunctions.GetQ3().clear();
					ButtonFunctions.GetQ4().clear();
					ButtonFunctions.GetWallColorMap().clear();
					EnemyButtonFunctions.DeleteEnemy1();
					EnemyButtonFunctions.DeleteEnemy2();
					EnemyButtonFunctions.DeleteEnemy3();
					mazePanel.repaint();
				}
			}

		});

		saveMazeAsImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SaveMazeAsImage.Load(mazePanel);
			}
		});

		saveMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (CheckForStartAndEndLocations() == true) {
					SaveFIleExplorer.Load();
				} else {
					JOptionPane.showMessageDialog(null, "Maze needs start and end spots.");
				}
			}

		});

		loadMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoadFileExplorer.Load();
				} catch (EOFException e) {
					e.printStackTrace();
				}
			}

		});

		enemiesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (enemyControls == null) {
					enemyControls = new EnemyControls();
				}
				enemyControls.setVisible();
				enemyControlsExists = true;
				selected = 7;
			}

		});

		mazePanel.addMouseListener(this);
		mazePanel.addMouseMotionListener(this);
	}

	private boolean CheckForStartAndEndLocations() {
		if (ButtonFunctions.StartLocationExists() == true && ButtonFunctions.EndLocationExists() == true) {
			return true;
		} else {
			return false;
		}
	}

	private void SetMazePanelSettingsForStartOfMaze(JPanel mazePanel) {
		selected = 0;
		AddKeyListenerToMaze(mazePanel);
		mazePanel.setFocusable(true);
		ChangeStartLabelToPlayerLabel(mazePanel);
	}

	private void ChangeStartLabelToPlayerLabel(JPanel mazePanel) {
		JLabel startLocation = ButtonFunctions.GetStartLabel();
		startLocation.setVisible(false);
		player = new JLabel("");
		player.setBounds(startLocation.getBounds());
		mazePanel.add(player);
		player.setVisible(true);
		Player.SetPlayerImage(player);
	}

	private void DisableButtonsForStartMaze(JButton startLocationButton, JButton endLocationButton,
			JButton freeDrawButton, JButton startMazeButton, JButton eraserButton, JButton loadMazeButton,
			JButton saveMazeButton, JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton,
			JButton saveMazeAsImageButton, JComboBox<String> colorComboBox, JButton enemiesButton) {
		
		startLocationButton.setEnabled(false);
		endLocationButton.setEnabled(false);
		freeDrawButton.setEnabled(false);
		startMazeButton.setEnabled(false);
		this.lineThicknessSlider.setEnabled(false);
		this.eraserThicknessSlider.setEnabled(false);
		eraserButton.setEnabled(false);
		loadMazeButton.setEnabled(false);
		saveMazeButton.setEnabled(false);
		horizontalLineButton.setEnabled(false);
		verticalLineButton.setEnabled(false);
		clearMazeButton.setEnabled(false);
		saveMazeAsImageButton.setEnabled(false);
		colorComboBox.setEnabled(false);
		enemiesButton.setEnabled(false);
	}

	private void EnableButtonsForStopMaze(JButton startLocationButton, JButton endLocationButton,
			JButton freeDrawButton, JButton startMazeButton, JButton eraserButton, JButton loadMazeButton,
			JButton saveMazeButton, JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton,
			JButton saveMazeAsImageButton, JComboBox<String> colorComboBox, JButton enemiesButton) {
		
		startLocationButton.setEnabled(true);
		endLocationButton.setEnabled(true);
		freeDrawButton.setEnabled(true);
		startMazeButton.setEnabled(true);
		this.lineThicknessSlider.setEnabled(true);
		this.eraserThicknessSlider.setEnabled(true);
		eraserButton.setEnabled(true);
		loadMazeButton.setEnabled(true);
		saveMazeButton.setEnabled(true);
		horizontalLineButton.setEnabled(true);
		verticalLineButton.setEnabled(true);
		clearMazeButton.setEnabled(true);
		saveMazeAsImageButton.setEnabled(true);
		colorComboBox.setEnabled(true);
		enemiesButton.setEnabled(true);
	}

	private void SetMazePanelSettingsForStopMaze(JPanel mazePanel) {
		mazePanel.remove(player);
		ButtonFunctions.GetStartLabel().setVisible(true);
		RemoveKeyListenerToMaze(mazePanel);
		mazePanel.repaint();
	}

	private void AddKeyListenerToMaze(JPanel mazePanel) {
		mazePanel.addKeyListener(this);
	}

	private void RemoveKeyListenerToMaze(JPanel mazePanel) {
		mazePanel.removeKeyListener(this);
	}

	private Color GetColorFromColorComboBox() {
		String color = (String) colorComboBox.getSelectedItem();
		if (color.equals("Black")) {
			return Color.black;
		} else if (color.equals("Red")) {
			return Color.red;
		} else if (color.equals("Yellow")) {
			return Color.yellow;
		} else if (color.equals("Orange")) {
			return Color.orange;
		} else if (color.equals("Pink")) {
			return Color.pink;
		} else if (color.equals("Purple")) {
			return new Color(128, 0, 128);
		} else if (color.equals("Blue")) {
			return Color.blue;
		} else if (color.equals("Gray")) {
			return Color.gray;
		} else if (color.equals("Green")) {
			return Color.green;
		} else if (color.equals("Brown")) {
			return new Color(139, 69, 19);
		} else if (color.equals("Lavender")) {
			return new Color(204, 204, 255);
		} else if (color.equals("Navy Blue")) {
			return new Color(0, 0, 153);
		} else if (color.equals("Army Green")) {
			return new Color(0, 102, 0);
		} else if (color.equals("Plum")) {
			return new Color(153, 51, 153);
		} else if (color.equals("Turquoise")) {
			return new Color(102, 255, 255);
		} else if (color.equals("Maroon")) {
			return new Color(153, 0, 51);
		} else if (color.equals("Storm")) {
			return new Color(102, 102, 153);
		} else if (color.equals("Salmon")) {
			return new Color(250, 128, 114);
		} else {
			return Color.black;
		}
	}

	public static JPanel GetMazePanel() {
		return mazePanel;
	}

	public static JButton GetStartMazeButton() {
		return startMazeButton;
	}

	public void mouseClicked(MouseEvent arg0) {
		if (selected == 0) {
		} else if (selected == startLocationSelected) {
			JPanel mazePanel = (JPanel) arg0.getComponent();
			ButtonFunctions.StartLocationFunction(mazePanel);
		} else if (selected == endLocationSelected) {
			JPanel mazePanel = (JPanel) arg0.getComponent();
			ButtonFunctions.EndLocationFunction(mazePanel);
		} else if (selected == enemiesSelected) {
			int enemySelected = EnemyControls.GetEnemySelected();
			int enemyControl = EnemyButtonFunctions.GetEnemyControl();
			if (enemySelected == 1 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy1StartLocation();
			} else if (enemySelected == 2 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy2StartLocation();
			} else if (enemySelected == 3 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy3StartLocation();
			} else if (enemySelected == 1 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy1EndLocation();
			} else if (enemySelected == 2 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy2EndLocation();
			} else if (enemySelected == 3 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy3EndLocation();
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JPanel mazePanel = (JPanel) arg0.getComponent();
		int lineThickness = lineThicknessSlider.getValue();
		if (selected == freeDrawLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
		} else if (selected == eraserSelected) {
			ButtonFunctions.EraseCoordinate(mazePanel, eraserThicknessSlider.getValue());
		} else if (selected == horizontalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetHorizontialLineY((int) mouseCoordinate.getY());
		} else if (selected == verticalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetVerticalLineX((int) mouseCoordinate.getX());
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		JPanel mazePanel = (JPanel) arg0.getComponent();
		int lineThickness = lineThicknessSlider.getValue();
		if (selected == freeDrawLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			mazePanel.repaint();
		} else if (selected == eraserSelected) {
			((MazePanel) mazePanel).SetEraserCoordinates(mazePanel.getMousePosition());
			ButtonFunctions.EraseCoordinate(mazePanel, eraserThicknessSlider.getValue());
			mazePanel.repaint();
		} else if (selected == horizontalLineSelected) {
			ButtonFunctions.DrawHorizontalLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			mazePanel.repaint();
		} else if (selected == verticalLineSelected) {
			ButtonFunctions.DrawVerticalLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			mazePanel.repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		pressedKeys.add(keyCode);
		/*
		 * PLAYER MOVEMENTS: Can go in multiple directions.
		 */
		if (!pressedKeys.isEmpty()) {
			for (Iterator<Integer> key = pressedKeys.iterator(); key.hasNext();) {
				switch (key.next()) {
				case KeyEvent.VK_UP:
					Player.MoveUp(player);
					break;
				case KeyEvent.VK_DOWN:
					Player.MoveDown(player);
					break;
				case KeyEvent.VK_LEFT:
					Player.MoveLeft(player);
					break;
				case KeyEvent.VK_RIGHT:
					Player.MoveRight(player);
					break;
				case KeyEvent.VK_W:
					Player.MoveUp(player);
					break;
				case KeyEvent.VK_S:
					Player.MoveDown(player);
					break;
				case KeyEvent.VK_A:
					Player.MoveLeft(player);
					break;
				case KeyEvent.VK_D:
					Player.MoveRight(player);
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		pressedKeys.remove(arg0.getKeyCode());
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (selected == eraserSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetEraserOff();
			mazePanel.repaint();
		}
		if(selected == freeDrawLineSelected || selected == horizontalLineSelected || selected == verticalLineSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetDrawOff();
			mazePanel.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if (selected == eraserSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetEraserOn();
			((MazePanel) mazePanel).SetEraserThickness(eraserThicknessSlider.getValue());
			Point eraserCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetEraserCoordinates(eraserCoordinates);
			mazePanel.repaint();
		}
		else if(selected == freeDrawLineSelected || selected == horizontalLineSelected || selected == verticalLineSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetDrawOn();
			((MazePanel) mazePanel).SetDrawThickness(this.lineThicknessSlider.getValue());
			Point drawCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetDrawCoordinates(drawCoordinates);
			mazePanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if (selected == eraserSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetEraserOn();
			((MazePanel) mazePanel).SetEraserThickness(eraserThicknessSlider.getValue());
			Point eraserCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetEraserCoordinates(eraserCoordinates);
			mazePanel.repaint();
		}
		else if(selected == freeDrawLineSelected || selected == horizontalLineSelected || selected == verticalLineSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetDrawOn();
			((MazePanel) mazePanel).SetDrawThickness(this.lineThicknessSlider.getValue());
			Point drawCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetDrawCoordinates(drawCoordinates);
			mazePanel.repaint();
		}
	}
	
	public static JLabel GetPlayerLabel() {
		return player;
	}
	
	public static EnemyControls GetEnemyControls() {
		return enemyControls;
	}
	
	public static void SetEnemyControls() {
		enemyControls = new EnemyControls();
		enemyControlsExists = true;
	}
	
	public static void SetSelected(int selected) {
		MazeMainGUI.selected = selected;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
