package mazeapplication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.JRadioButton;

public class MazeDesignMainGUI implements MouseMotionListener, MouseListener, KeyListener {

	/**
	 * RULES: 1.) New Button: Disable focusable Add button to start maze function
	 * for it to be disabled. Add button to stop maze function for it to be enabled.
	 */

	private JFrame frame;
	private JSlider lineThicknessSlider;
	private JSlider eraserThicknessSlider;
	private static JPanel mazePanel;
	private static JButton startMazeButton;
	private JComboBox<String> colorComboBox;
	private static EnemyControls enemyControls;
	private final HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private final HashSet<Integer> mazeFramePressedKeys = new HashSet<Integer>();
	private static boolean enemyControlsExists = false;
	private Color[] backgroundColors = new Color[] { Color.white, new Color(135, 206, 235), new Color(194, 178, 128),
			new Color(96, 128, 56), new Color(112, 84, 62), new Color(120, 177, 199), new Color(160, 192, 144),
			new Color(211, 211, 211) };
	private JComboBox<String> specificDecorations;
	private SetImageToLabel images = new SetImageToLabel();
	private static LinkedList<DecorationNode> decorationList = new LinkedList<DecorationNode>();
	private static String loadedMazeFileLocation = null;
	private static JRadioButton blackoutRadioButton;
	private static JButton stopMazeButton = null;

	/*
	 * selected: 0-9 1: start location 2: end location 3: free draw line 4: eraser
	 * 5: horizontal line 6: vertical line 7: Enemies Option Panel 8: Add
	 * Decorations 9: Delete Decorations
	 */

	private static int selected = 0;
	private int startLocationSelected = 1;
	private int endLocationSelected = 2;
	private int freeDrawLineSelected = 3;
	private int eraserSelected = 4;
	private int horizontalLineSelected = 5;
	private int verticalLineSelected = 6;
	private int enemiesSelected = 7;
	private int addDecorationSelected = 8;
	private int deleteDecorationSelected = 9;

	// End of selected.
	private static JLabel player;
	// Display for end coordinates.
	private JLabel xAndyDisplayCoordinates;
	private boolean mazeStarted = false;

	public MazeDesignMainGUI() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(MazeDesignMainGUI.class.getResource("/images/logo.png")));
		frame.getContentPane().setFocusable(false);
		frame.setBounds(100, 100, 935, 730);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(0, 0, 128));
		frame.setTitle("MazeKraze");

		Border blackBorder = BorderFactory.createLineBorder(Color.black);

		JButton startLocationButton = new JButton("Start Location");
		startLocationButton.setFocusable(false);
		startLocationButton.setBackground(new Color(255, 255, 224));
		startLocationButton.setToolTipText("Player start loaction.");
		startLocationButton.setBounds(10, 10, 154, 20);
		startLocationButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton endLocationButton = new JButton("End Location");
		endLocationButton.setFocusable(false);
		endLocationButton.setBackground(new Color(255, 255, 224));
		endLocationButton.setToolTipText("Player end location \"X marks the spot\"");
		endLocationButton.setBounds(10, 38, 154, 20);
		endLocationButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton freeDrawButton = new JButton("Free Draw");
		freeDrawButton.setFocusable(false);
		freeDrawButton.setBackground(new Color(255, 255, 224));
		freeDrawButton.setBounds(10, 68, 154, 20);
		freeDrawButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		final int minLineThickness = 1;
		final int maxLineThickness = 10;
		final int initialLineThickness = 5;
		lineThicknessSlider = new JSlider(JSlider.HORIZONTAL, minLineThickness, maxLineThickness, initialLineThickness);
		lineThicknessSlider.setFocusable(false);
		lineThicknessSlider.setBorder(new LineBorder(new Color(0, 0, 0)));
		lineThicknessSlider.setBackground(new Color(255, 255, 224));
		lineThicknessSlider.setToolTipText("Changes the thickness of the line you draw");
		lineThicknessSlider.setBounds(10, 220, 154, 54);
		lineThicknessSlider.setMajorTickSpacing(3);
		lineThicknessSlider.setMinorTickSpacing(1);
		lineThicknessSlider.setPaintTicks(true);
		lineThicknessSlider.setPaintLabels(true);

		JLabel lineThicknessLabel = new JLabel("Line Thickness:");
		lineThicknessLabel.setFocusable(false);
		lineThicknessLabel.setBounds(10, 206, 100, 13);

		JButton eraserButton = new JButton("Eraser");
		eraserButton.setFocusable(false);
		eraserButton.setBackground(new Color(245, 222, 179));
		eraserButton.setToolTipText("Erases lines you draw");
		eraserButton.setBounds(10, 467, 154, 21);
		eraserButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton horizontalLineButton = new JButton("Horizontal Line");
		horizontalLineButton.setBackground(new Color(255, 255, 224));
		horizontalLineButton.setToolTipText("left and right line");
		horizontalLineButton.setBounds(10, 98, 154, 21);
		horizontalLineButton.setFocusable(false);
		horizontalLineButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton verticalLineButton = new JButton("Vertical Line");
		verticalLineButton.setBackground(new Color(255, 255, 224));
		verticalLineButton.setToolTipText("Up and down line.");
		verticalLineButton.setBounds(10, 129, 154, 21);
		verticalLineButton.setFocusable(false);
		verticalLineButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JLabel eraserThicknessLabel = new JLabel("Eraser Size:");
		eraserThicknessLabel.setFocusable(false);
		eraserThicknessLabel.setBounds(10, 384, 100, 13);

		final int minEraserThickness = 1;
		final int maxEraserThickness = 10;
		final int initialEraserThickness = 5;
		eraserThicknessSlider = new JSlider(JSlider.HORIZONTAL, minEraserThickness, maxEraserThickness,
				initialEraserThickness);
		eraserThicknessSlider.setFocusable(false);
		eraserThicknessSlider.setBorder(new LineBorder(new Color(0, 0, 0)));
		eraserThicknessSlider.setBackground(new Color(245, 222, 179));
		eraserThicknessSlider.setToolTipText("Changes the size of your eraser");
		eraserThicknessSlider.setBounds(10, 403, 154, 54);
		eraserThicknessSlider.setMajorTickSpacing(3);
		eraserThicknessSlider.setMinorTickSpacing(1);
		eraserThicknessSlider.setPaintTicks(true);
		eraserThicknessSlider.setPaintLabels(true);

		colorComboBox = new JComboBox<String>();
		colorComboBox.setFocusable(false);
		colorComboBox.setBackground(new Color(255, 255, 255));
		colorComboBox.setToolTipText("Changes the color you draw with.");
		colorComboBox.setBounds(10, 177, 114, 21);
		colorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Black", "Red", "Yellow", "Orange",
				"Pink", "Purple", "Blue", "Gray", "Dark Gray", "Green", "Brown", "Lavender", "Navy Blue", "Army Green",
				"Plum", "Turquoise", "Maroon", "Storm", "Salmon" }));
		colorComboBox.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JLabel lblLineColor = new JLabel("Line Color:");
		lblLineColor.setFocusable(false);
		lblLineColor.setBounds(10, 160, 100, 13);

		JComboBox<String> backgroundColorComboBox = new JComboBox<String>();
		backgroundColorComboBox.setBackground(new Color(255, 255, 255));
		backgroundColorComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "White", "Sky Blue", "Sand", "Grass", "Mud", "Glacier", "Swamp", "Storm Gray" }));
		backgroundColorComboBox.setBounds(10, 360, 85, 21);
		backgroundColorComboBox.setFocusable(false);
		backgroundColorComboBox.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JLabel backgroundColorLabel = new JLabel("Background Color:");
		backgroundColorLabel.setFocusable(false);
		backgroundColorLabel.setBounds(10, 340, 169, 13);

		JButton setBackgroundColorButton = new JButton("Set");
		setBackgroundColorButton.setBackground(new Color(255, 255, 224));
		setBackgroundColorButton.setBounds(101, 360, 63, 21);
		setBackgroundColorButton.setFocusable(false);
		setBackgroundColorButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JLabel currentColorLabel = new JLabel("");
		currentColorLabel.setFocusable(false);
		currentColorLabel.setBackground(new Color(0, 0, 0));
		currentColorLabel.setBounds(129, 178, 35, 20);
		currentColorLabel.setOpaque(true);

		mazePanel = new MazePanel();
		mazePanel.setBackground(Color.WHITE);
		mazePanel.setBounds(10, 35, 708, 648);
		frame.getContentPane().add(mazePanel);
		mazePanel.setBorder(blackBorder);
		mazePanel.setLayout(null);

		JLabel decorationsLabel = new JLabel("Decorations:");
		decorationsLabel.setFocusable(false);
		decorationsLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		decorationsLabel.setBounds(10, 10, 151, 22);

		JButton addDecorationButton = new JButton("Add Decoration");
		addDecorationButton.setFocusable(false);
		addDecorationButton.setBackground(Color.PINK);
		addDecorationButton.setBounds(10, 131, 151, 21);
		addDecorationButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JComboBox<String> typeOfDecorationComboBox = new JComboBox<String>();
		typeOfDecorationComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Plain", "Sky Blue", "Sand", "Grass", "Mud", "Glacier", "Swamp", "Storm Gray" }));
		typeOfDecorationComboBox.setFocusable(false);
		typeOfDecorationComboBox.setBounds(10, 69, 151, 21);
		typeOfDecorationComboBox.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JLabel typeOfDecorationLabel = new JLabel("Type of Decoration:");
		typeOfDecorationLabel.setFocusable(false);
		typeOfDecorationLabel.setBounds(10, 46, 151, 13);

		specificDecorations = new JComboBox<String>();
		specificDecorations.setFocusable(false);
		specificDecorations.setBounds(10, 100, 151, 21);
		specificDecorations.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton deleteDecorationButton = new JButton("Delete Decoration");
		deleteDecorationButton.setFocusable(false);
		deleteDecorationButton.setBackground(new Color(250, 128, 114));
		deleteDecorationButton.setBounds(10, 162, 151, 21);
		deleteDecorationButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton undoButton = new JButton("Undo Line");
		undoButton.setToolTipText("Undo the last drawn line - \"ctrl+z\"");
		undoButton.setBackground(new Color(255, 127, 80));
		undoButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		undoButton.setFocusable(false);
		undoButton.setBounds(10, 399, 171, 35);
		undoButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton redoLineButton = new JButton("<html>Redo Erased Line</html>");
		redoLineButton.setToolTipText("Redo last erased line - \"ctrl+r\"");
		redoLineButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
		redoLineButton.setFocusable(false);
		redoLineButton.setBackground(new Color(244, 164, 96));
		redoLineButton.setBounds(10, 444, 171, 35);
		redoLineButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JPanel optionsPanelTest = new JPanel();
		optionsPanelTest.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		optionsPanelTest.setBackground(new Color(255, 215, 0));
		optionsPanelTest.setBounds(728, 2, 180, 688);
		frame.getContentPane().add(optionsPanelTest);
		optionsPanelTest.setLayout(null);

		JPanel changingOptionsPanel = new JPanel();
		changingOptionsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		changingOptionsPanel.setBackground(new Color(255, 215, 0));
		changingOptionsPanel.setBounds(5, 102, 170, 335);
		optionsPanelTest.add(changingOptionsPanel);
		changingOptionsPanel.setLayout(null);

		JButton pauseMusicButton = new JButton("Pause Music");
		pauseMusicButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		pauseMusicButton.setForeground(new Color(255, 255, 255));
		pauseMusicButton.setBounds(10, 563, 158, 20);
		optionsPanelTest.add(pauseMusicButton);
		pauseMusicButton.setFocusable(false);
		pauseMusicButton.setBackground(new Color(0, 0, 255));

		JButton playMusicButton = new JButton("Play Music");
		playMusicButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		playMusicButton.setForeground(new Color(255, 255, 255));
		playMusicButton.setBounds(10, 588, 158, 20);
		optionsPanelTest.add(playMusicButton);
		playMusicButton.setFocusable(false);
		playMusicButton.setBackground(new Color(0, 0, 255));

		JButton enemiesButton = new JButton("Enemies");
		enemiesButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		enemiesButton.setForeground(new Color(255, 255, 255));
		enemiesButton.setBounds(10, 439, 158, 20);
		optionsPanelTest.add(enemiesButton);
		enemiesButton.setBackground(new Color(0, 191, 255));
		enemiesButton.setToolTipText("Add enemies to your maze.");
		enemiesButton.setFocusable(false);

		JButton clearMazeButton = new JButton("Clear Maze");
		clearMazeButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		clearMazeButton.setForeground(new Color(255, 255, 255));
		clearMazeButton.setBounds(10, 463, 158, 20);
		optionsPanelTest.add(clearMazeButton);
		clearMazeButton.setBackground(new Color(0, 191, 255));
		clearMazeButton.setToolTipText("Completely clears maze, save before!");
		clearMazeButton.setFocusable(false);

		blackoutRadioButton = new JRadioButton("Set Blackout");
		blackoutRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		blackoutRadioButton.setForeground(new Color(255, 255, 255));
		blackoutRadioButton.setBackground(new Color(0, 191, 255));
		blackoutRadioButton.setBounds(10, 488, 158, 20);
		optionsPanelTest.add(blackoutRadioButton);
		blackoutRadioButton.setFocusable(false);

		// Button Actions
		startLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = startLocationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(startLocationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						enemiesButton, addDecorationButton, deleteDecorationButton);
			}
		});

		endLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = endLocationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(endLocationButton,
						startLocationButton, freeDrawButton, eraserButton, verticalLineButton, horizontalLineButton,
						enemiesButton, addDecorationButton, deleteDecorationButton);
			}

		});

		freeDrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = freeDrawLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(freeDrawButton,
						startLocationButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						enemiesButton, addDecorationButton, deleteDecorationButton);
			}

		});

		eraserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = eraserSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(eraserButton,
						startLocationButton, endLocationButton, freeDrawButton, verticalLineButton,
						horizontalLineButton, enemiesButton, addDecorationButton, deleteDecorationButton);
			}

		});

		horizontalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = horizontalLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(horizontalLineButton,
						startLocationButton, endLocationButton, freeDrawButton, eraserButton, verticalLineButton,
						enemiesButton, addDecorationButton, deleteDecorationButton);
			}

		});

		verticalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = verticalLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(verticalLineButton,
						startLocationButton, endLocationButton, freeDrawButton, eraserButton, horizontalLineButton,
						enemiesButton, addDecorationButton, deleteDecorationButton);
			}
		});

		setBackgroundColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = backgroundColorComboBox.getSelectedIndex();
				mazePanel.setBackground(backgroundColors[index]);
				BackgroundMusic.PlayCorrectAudioContinuously(backgroundColors[index]);
			}

		});

		colorComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentColorLabel.setBackground(GetColorFromColorComboBox());
			}

		});

		typeOfDecorationComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				specificDecorations.removeAllItems();
				InputStream resourceStream = getClass()
						.getResourceAsStream("/decorationsList/" + typeOfDecorationComboBox.getSelectedItem() + ".txt");
				BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
				try {
					while (resourceReader.ready()) {
						String currentItem = resourceReader.readLine();
						specificDecorations.addItem(currentItem);
					}
					resourceStream.close();
					resourceReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		addDecorationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = addDecorationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(addDecorationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						enemiesButton, startLocationButton, deleteDecorationButton);
			}

		});

		deleteDecorationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = deleteDecorationSelected;
				ButtonFunctions.AddBordersToDecorations(decorationList, mazePanel);
				ButtonFunctions.AddMouseListenerToDecorations(decorationList, mazePanel);
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(deleteDecorationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						enemiesButton, startLocationButton, addDecorationButton);
			}

		});

		undoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UndoStructure.Undo(mazePanel);
			}
		});

		redoLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RedoErasedLineStructure.Redo(mazePanel);

			}

		});

		frame.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				mazeFramePressedKeys.add(arg0.getKeyCode());
				for (Iterator<Integer> iterator = mazeFramePressedKeys.iterator(); iterator.hasNext();) {
					switch (iterator.next()) {
					case 17:
						if (iterator.hasNext()) {
							int nextKey = iterator.next();
							if (nextKey == KeyEvent.VK_Z) {
								UndoStructure.Undo(mazePanel);
								mazeFramePressedKeys.remove(KeyEvent.VK_Z);
							} else if (nextKey == KeyEvent.VK_S) {
								if (loadedMazeFileLocation == null) {
									SaveMazeAs();
								} else {
									SaveLoadedMaze();
									JOptionPane.showMessageDialog(null, "Maze Saved");
								}
								mazeFramePressedKeys.remove(KeyEvent.VK_S);
							} else if (nextKey == KeyEvent.VK_R) {
								RedoErasedLineStructure.Redo(mazePanel);
								mazeFramePressedKeys.remove(KeyEvent.VK_R);
							}
						}
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				mazeFramePressedKeys.remove(arg0.getKeyCode());
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

		});

		mazePanel.addMouseListener(this);
		mazePanel.addMouseMotionListener(this);
		frame.setVisible(true);
		SetOrigionalDecorationsList(specificDecorations);

		JRadioButton startAndStopRadioButton = new JRadioButton("Start & Stop Spots");
		startAndStopRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		startAndStopRadioButton.setForeground(new Color(255, 255, 255));
		startAndStopRadioButton.setBackground(new Color(0, 191, 255));
		startAndStopRadioButton.setBounds(6, 6, 168, 21);
		optionsPanelTest.add(startAndStopRadioButton);

		JRadioButton drawingRadioButton = new JRadioButton("Drawing Walls");
		drawingRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		drawingRadioButton.setBackground(new Color(0, 191, 255));
		drawingRadioButton.setForeground(new Color(255, 255, 255));
		drawingRadioButton.setBounds(6, 29, 168, 21);
		optionsPanelTest.add(drawingRadioButton);

		JRadioButton decorationsRadioButton = new JRadioButton("Decorations");
		decorationsRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		decorationsRadioButton.setBackground(new Color(0, 191, 255));
		decorationsRadioButton.setForeground(new Color(255, 255, 255));
		decorationsRadioButton.setBounds(6, 52, 168, 21);
		optionsPanelTest.add(decorationsRadioButton);

		JRadioButton backgroundRadioButton = new JRadioButton("Background");
		backgroundRadioButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		backgroundRadioButton.setBackground(new Color(0, 191, 255));
		backgroundRadioButton.setForeground(new Color(255, 255, 255));
		backgroundRadioButton.setBounds(6, 75, 168, 21);
		optionsPanelTest.add(backgroundRadioButton);

		ButtonGroup optionsRadioButtonGroup = new ButtonGroup();
		optionsRadioButtonGroup.add(startAndStopRadioButton);
		optionsRadioButtonGroup.add(drawingRadioButton);
		optionsRadioButtonGroup.add(decorationsRadioButton);
		optionsRadioButtonGroup.add(backgroundRadioButton);
		startMazeButton = new JButton("Start Maze");
		startMazeButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		startMazeButton.setForeground(new Color(255, 255, 255));
		startMazeButton.setBounds(10, 513, 158, 20);
		optionsPanelTest.add(startMazeButton);
		startMazeButton.setBackground(new Color(0, 128, 128));
		startMazeButton.setToolTipText("Starts the maze, makes your player moveable.");
		startMazeButton.setFocusable(false);

		stopMazeButton = new JButton("Stop Maze");
		stopMazeButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		stopMazeButton.setForeground(new Color(255, 255, 255));
		stopMazeButton.setBounds(10, 538, 158, 20);
		optionsPanelTest.add(stopMazeButton);
		stopMazeButton.setBackground(new Color(0, 128, 128));
		stopMazeButton.setToolTipText("Stops the maze and resets.");
		stopMazeButton.setEnabled(false);
		stopMazeButton.setFocusable(false);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(10, 6, 708, 24);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		JButton loadMazeButton = new JButton("Load Maze");
		loadMazeButton.setBounds(143, 2, 179, 20);
		panel.add(loadMazeButton);
		loadMazeButton.setBackground(new Color(255, 215, 0));
		loadMazeButton.setToolTipText("Load a previously saved maze.");
		loadMazeButton.setFocusable(false);
		loadMazeButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton saveMazeAsImageButton = new JButton("Save Maze As Image");
		saveMazeAsImageButton.setBounds(329, 2, 179, 20);
		panel.add(saveMazeAsImageButton);
		saveMazeAsImageButton.setBackground(new Color(255, 215, 0));
		saveMazeAsImageButton.setToolTipText("Save the maze as a printable image");
		saveMazeAsImageButton.setFocusable(false);
		saveMazeAsImageButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		JButton saveMazeButton = new JButton("Save Maze");
		saveMazeButton.setBounds(518, 2, 179, 20);
		panel.add(saveMazeButton);
		saveMazeButton.setBackground(new Color(255, 215, 0));
		saveMazeButton.setToolTipText("Save maze to send to your friends or for later use");
		saveMazeButton.setFocusable(false);
		saveMazeButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));

		stopMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetMazePanelSettingsForStopMaze(mazePanel);
				EnableButtonsForStopMaze(startLocationButton, endLocationButton, freeDrawButton, startMazeButton,
						eraserButton, loadMazeButton, saveMazeButton, horizontalLineButton, verticalLineButton,
						clearMazeButton, saveMazeAsImageButton, colorComboBox, enemiesButton, setBackgroundColorButton,
						backgroundColorComboBox, addDecorationButton, deleteDecorationButton, undoButton, redoLineButton
						, blackoutRadioButton);
				EnemyStartAndStopMazeFunctions.StopEnemies();
				stopMazeButton.setEnabled(false);
				frame.addKeyListener(new KeyListener() {
					@Override
					public void keyPressed(KeyEvent arg0) {
						mazeFramePressedKeys.add(arg0.getKeyCode());
						for (Iterator<Integer> iterator = mazeFramePressedKeys.iterator(); iterator.hasNext();) {
							switch (iterator.next()) {
							case 17:
								if (iterator.hasNext()) {
									int nextKey = iterator.next();
									if (nextKey == KeyEvent.VK_Z) {
										UndoStructure.Undo(mazePanel);
										mazeFramePressedKeys.remove(KeyEvent.VK_Z);
									} else if (nextKey == KeyEvent.VK_S) {
										System.out.println(loadedMazeFileLocation);
										if (loadedMazeFileLocation == null) {
											SaveMazeAs();
										} else {
											SaveLoadedMaze();
											JOptionPane.showMessageDialog(null, "Maze Saved");
										}
										mazeFramePressedKeys.remove(KeyEvent.VK_S);
									}
								}
							}
						}
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						mazeFramePressedKeys.remove(arg0.getKeyCode());
					}

					@Override
					public void keyTyped(KeyEvent arg0) {

					}
				});
				BlackoutMaze.StopBlackout(mazePanel);
				ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
				while (iterator.hasNext()) {
					iterator.next().getDecorationNode().setVisible(true);
				}
				mazePanel.repaint();
				frame.requestFocusInWindow();
			}

		});

		startMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveBordersFromMazeComponents();
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if (CheckForStartAndEndLocations() == true) {
					SetMazePanelSettingsForStartOfMaze(mazePanel);
					DisableButtonsForStartMaze(startLocationButton, endLocationButton, freeDrawButton, startMazeButton,
							eraserButton, loadMazeButton, saveMazeButton, horizontalLineButton, verticalLineButton,
							clearMazeButton, saveMazeAsImageButton, colorComboBox, enemiesButton,
							setBackgroundColorButton, backgroundColorComboBox, addDecorationButton,
							deleteDecorationButton, undoButton, redoLineButton, blackoutRadioButton);
					stopMazeButton.setEnabled(true);
					KeyListener[] frameListener = frame.getKeyListeners();
					for (int i = 0; i < frameListener.length; i++) {
						frame.removeKeyListener(frameListener[i]);
					}
					if (enemyControlsExists == true) {
						EnemyControls.SetDisposed();
					}
					EnemyStartAndStopMazeFunctions.StartEnemies();
					if (blackoutRadioButton.isSelected()) {
						BlackoutMaze.InitiateBlackout(player, mazePanel);
						if(MainMenuGUI.isLevelMazeActive() == true) {
							LevelMenu.disposeActiveMaze();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Maze can only start with designated start and end locations!");
				}
			}
		});

		ActionListener optionsRadioButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (startAndStopRadioButton.isSelected()) {
					changingOptionsPanel.removeAll();
					changingOptionsPanel.add(startLocationButton);
					changingOptionsPanel.add(endLocationButton);
					changingOptionsPanel.repaint();
					
					frame.requestFocusInWindow();
				} else if (drawingRadioButton.isSelected()) {
					changingOptionsPanel.removeAll();

					freeDrawButton.setBounds(10, 10, 154, 20);
					changingOptionsPanel.add(freeDrawButton);
					horizontalLineButton.setBounds(10, 34, 154, 20);
					changingOptionsPanel.add(horizontalLineButton);
					verticalLineButton.setBounds(10, 58, 154, 20);
					changingOptionsPanel.add(verticalLineButton);
					lblLineColor.setBounds(10, 82, 100, 13);
					changingOptionsPanel.add(lblLineColor);
					colorComboBox.setBounds(10, 95, 114, 21);
					changingOptionsPanel.add(colorComboBox);
					currentColorLabel.setBounds(129, 95, 35, 20);
					changingOptionsPanel.add(currentColorLabel);
					lineThicknessLabel.setBounds(10, 118, 100, 13);
					changingOptionsPanel.add(lineThicknessLabel);
					lineThicknessSlider.setBounds(10, 133, 154, 54);
					changingOptionsPanel.add(lineThicknessSlider);
					eraserButton.setBounds(10, 190, 154, 21);
					changingOptionsPanel.add(eraserButton);
					eraserThicknessLabel.setBounds(10, 213, 100, 13);
					changingOptionsPanel.add(eraserThicknessLabel);
					eraserThicknessSlider.setBounds(10, 230, 154, 54);
					changingOptionsPanel.add(eraserThicknessSlider);
					undoButton.setBounds(10, 288, 154, 20);
					changingOptionsPanel.add(undoButton);
					redoLineButton.setBounds(10, 312, 154, 20);
					changingOptionsPanel.add(redoLineButton);

					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();
					frame.requestFocusInWindow();
				} else if (decorationsRadioButton.isSelected()) {
					changingOptionsPanel.removeAll();

					typeOfDecorationLabel.setBounds(10, 10, 151, 13);
					changingOptionsPanel.add(typeOfDecorationLabel);
					typeOfDecorationComboBox.setBounds(10, 33, 151, 21);
					changingOptionsPanel.add(typeOfDecorationComboBox);
					specificDecorations.setBounds(10, 64, 151, 21);
					changingOptionsPanel.add(specificDecorations);
					addDecorationButton.setBounds(10, 95, 151, 21);
					changingOptionsPanel.add(addDecorationButton);
					deleteDecorationButton.setBounds(10, 126, 151, 21);
					changingOptionsPanel.add(deleteDecorationButton);

					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();
					
					frame.requestFocusInWindow();
				} else if (backgroundRadioButton.isSelected()) {
					changingOptionsPanel.removeAll();

					backgroundColorLabel.setBounds(5, 10, 169, 13);
					changingOptionsPanel.add(backgroundColorLabel);
					backgroundColorComboBox.setBounds(5, 28, 85, 21);
					changingOptionsPanel.add(backgroundColorComboBox);
					setBackgroundColorButton.setBounds(95, 28, 63, 21);
					changingOptionsPanel.add(setBackgroundColorButton);

					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();
					frame.requestFocusInWindow();
				}
			}
		};
		startAndStopRadioButton.addActionListener(optionsRadioButtonListener);
		drawingRadioButton.addActionListener(optionsRadioButtonListener);
		decorationsRadioButton.addActionListener(optionsRadioButtonListener);
		backgroundRadioButton.addActionListener(optionsRadioButtonListener);

		startAndStopRadioButton.setSelected(true);
		changingOptionsPanel.add(startLocationButton);
		changingOptionsPanel.add(endLocationButton);

		JButton lessonsButton = new JButton("Online Lessons");
		lessonsButton.setForeground(new Color(255, 255, 255));
		lessonsButton.setBounds(10, 613, 158, 20);
		optionsPanelTest.add(lessonsButton);
		lessonsButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		lessonsButton.setFocusable(false);
		lessonsButton.setBackground(new Color(0, 0, 139));

		JButton downloadTemplatesButton = new JButton("Download Templates");
		downloadTemplatesButton.setForeground(new Color(255, 255, 255));
		downloadTemplatesButton.setBounds(10, 638, 158, 20);
		optionsPanelTest.add(downloadTemplatesButton);
		downloadTemplatesButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		downloadTemplatesButton.setFocusable(false);
		downloadTemplatesButton.setBackground(new Color(0, 0, 139));

		JButton downloadMazesButton = new JButton("Download Mazes");
		downloadMazesButton.setForeground(new Color(255, 255, 255));
		downloadMazesButton.setBounds(10, 663, 158, 20);
		optionsPanelTest.add(downloadMazesButton);
		downloadMazesButton.setFont(new Font("Berlin Sans FB", Font.PLAIN, 13));
		downloadMazesButton.setFocusable(false);
		downloadMazesButton.setBackground(new Color(0, 0, 139));

		JLabel xAndyLabel = new JLabel("X,Y:");
		xAndyLabel.setBounds(0, 4, 27, 18);
		panel.add(xAndyLabel);
		xAndyLabel.setFocusable(false);
		xAndyLabel.setForeground(Color.PINK);

		xAndyDisplayCoordinates = new JLabel("");
		xAndyDisplayCoordinates.setBounds(37, 4, 54, 18);
		panel.add(xAndyDisplayCoordinates);
		xAndyDisplayCoordinates.setFocusable(false);
		xAndyDisplayCoordinates.setForeground(Color.PINK);

		JLabel backgroundImageLabel = new JLabel("");
		backgroundImageLabel.setBounds(0, 2, 921, 688);
		frame.getContentPane().add(backgroundImageLabel);

		saveMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveBordersFromMazeComponents();
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if (CheckForStartAndEndLocations() == true) {
					if (loadedMazeFileLocation == null) {
						SaveFileExplorer.Load();
					} else {
						SaveLoadedMaze();
						JOptionPane.showMessageDialog(null, "Maze Saved");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Maze needs start and end spots.");
				}
			}

		});

		saveMazeAsImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				SaveMazeAsImage.Load(mazePanel);
			}
		});

		loadMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					LoadFileExplorer.Load();
					BackgroundMusic.PlayCorrectAudioContinuously(mazePanel.getBackground());
					UndoStructure.clearUndoStack();
					RedoErasedLineStructure.ClearRedoStack();
					RemoveBordersFromMazeComponents();
				} catch (EOFException e) {
					e.printStackTrace();
				}
			}

		});

		downloadMazesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = new URI("https://mazekraze.com/mazekraze/novice-mazes/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {

				}
			}
		});

		downloadTemplatesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = new URI("https://mazekraze.com/mazekraze/templates/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});

		lessonsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// https://mazekraze.com/mazekraze/lessons/
				try {
					URI uri = new URI("https://mazekraze.com/mazekraze/lessons/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
					if (enemyControlsExists) {
						EnemyControls.ResetEnemies();
					}
					RemoveDecorationsFromMazeAndList();
					mazePanel.setBackground(Color.white);
					mazePanel.repaint();
					loadedMazeFileLocation = null;
					UndoStructure.clearUndoStack();
					RedoErasedLineStructure.ClearRedoStack();
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
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(enemiesButton,
						verticalLineButton, startLocationButton, endLocationButton, freeDrawButton, eraserButton,
						horizontalLineButton, addDecorationButton, deleteDecorationButton);
			}

		});

		playMusicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BackgroundMusic.Play();
			}

		});

		pauseMusicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BackgroundMusic.Pause();
			}
		});
		
		SetImageToLabel images = new SetImageToLabel();
		images.set_image_to_label(backgroundImageLabel, "/images/testBackground15.jpg");

		frame.requestFocus();
	}

	public void setVisible(boolean visible) {
		frame.setVisible(visible);
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
		mazePanel.requestFocusInWindow();
		ChangeStartLabelToPlayerLabel(mazePanel);
		mazeStarted = true;
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

	/**
	 * Disabling the buttons so a player can't make changes while the maze is
	 * playing.
	 * 
	 * @param startLocationButton
	 * @param endLocationButton
	 * @param freeDrawButton
	 * @param startMazeButton
	 * @param eraserButton
	 * @param loadMazeButton
	 * @param saveMazeButton
	 * @param horizontalLineButton
	 * @param verticalLineButton
	 * @param clearMazeButton
	 * @param saveMazeAsImageButton
	 * @param colorComboBox
	 * @param enemiesButton
	 * @param setBackgroundButton
	 * @param backgroundColorComboBox
	 */

	private void DisableButtonsForStartMaze(JButton startLocationButton, JButton endLocationButton,
			JButton freeDrawButton, JButton startMazeButton, JButton eraserButton, JButton loadMazeButton,
			JButton saveMazeButton, JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton,
			JButton saveMazeAsImageButton, JComboBox<String> colorComboBox, JButton enemiesButton,
			JButton setBackgroundButton, JComboBox<String> backgroundColorComboBox, JButton addDecorationButton,
			JButton deleteDecorationButton, JButton undoButton, JButton redoButton, JRadioButton blackoutMazeRadioButton) {

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
		setBackgroundButton.setEnabled(false);
		backgroundColorComboBox.setEnabled(false);
		addDecorationButton.setEnabled(false);
		deleteDecorationButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		blackoutMazeRadioButton.setEnabled(false);
	}

	/**
	 * Enabling the buttons so a player can make changes to the maze after it is
	 * done being played.
	 * 
	 * @param startLocationButton
	 * @param endLocationButton
	 * @param freeDrawButton
	 * @param startMazeButton
	 * @param eraserButton
	 * @param loadMazeButton
	 * @param saveMazeButton
	 * @param horizontalLineButton
	 * @param verticalLineButton
	 * @param clearMazeButton
	 * @param saveMazeAsImageButton
	 * @param colorComboBox
	 * @param enemiesButton
	 * @param setBackgroundButton
	 * @param backgroundColorComboBox
	 */
	private void EnableButtonsForStopMaze(JButton startLocationButton, JButton endLocationButton,
			JButton freeDrawButton, JButton startMazeButton, JButton eraserButton, JButton loadMazeButton,
			JButton saveMazeButton, JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton,
			JButton saveMazeAsImageButton, JComboBox<String> colorComboBox, JButton enemiesButton,
			JButton setBackgroundButton, JComboBox<String> backgroundColorComboBox, JButton addDecorationButton,
			JButton deleteDecorationButton, JButton undoButton, JButton redoButton, JRadioButton blackoutMazeRadioButton) {

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
		setBackgroundButton.setEnabled(true);
		backgroundColorComboBox.setEnabled(true);
		addDecorationButton.setEnabled(true);
		deleteDecorationButton.setEnabled(true);
		undoButton.setEnabled(true);
		redoButton.setEnabled(true);
		blackoutMazeRadioButton.setEnabled(true);
	}

	/**
	 * Setting the buttons foreground color to show which one is selected by the
	 * user.
	 */
	private void SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(JButton selectedButton,
			JButton button1, JButton button2, JButton button3, JButton button4, JButton button5, JButton button6,
			JButton button7, JButton button8) {
		selectedButton.setForeground(new Color(255, 69, 0));
		button1.setForeground(Color.black);
		button2.setForeground(Color.black);
		button3.setForeground(Color.black);
		button4.setForeground(Color.black);
		button5.setForeground(Color.black);
		button6.setForeground(Color.black);
		button7.setForeground(Color.black);
		button8.setForeground(Color.black);
		if (selected != deleteDecorationSelected) {
			ButtonFunctions.RemoveBordersFromDecorations(decorationList, mazePanel);
			ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
		}
	}

	/**
	 * Setting the mazePanel for editing the maze again. 1.) removes player from the
	 * maze. 2.) Sets the start Location visible again. 3.) Removes the key listener
	 * so the user can't do movements. 4.) repaints the panel to make sure changes
	 * were made.
	 * 
	 * @param mazePanel
	 */
	private void SetMazePanelSettingsForStopMaze(JPanel mazePanel) {
		mazePanel.remove(player);
		ButtonFunctions.GetStartLabel().setVisible(true);
		RemoveKeyListenerToMaze(mazePanel);
		mazePanel.repaint();
		mazeStarted = false;
	}

	private void AddKeyListenerToMaze(JPanel mazePanel) {
		mazePanel.addKeyListener(this);
	}

	private void RemoveBordersFromMazeComponents() {
		for (Component c : mazePanel.getComponents()) {
			if (c instanceof JLabel) {
				((JLabel) c).setBorder(null);
			}
		}
	}

	private void RemoveKeyListenerToMaze(JPanel mazePanel) {
		mazePanel.removeKeyListener(this);
	}

	/**
	 * Get's a string from the color combo box and returns the color designated to
	 * that string name.
	 * 
	 * @return Color
	 */
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
		} else if (color.equals("Dark Gray")) {
			return Color.darkGray;
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

	/**
	 * Mouse Clicked event Selected = startLocation: changing or setting the start
	 * location Selected = endLocation: changing or setting the end location
	 * Selected = enemies: Enemies tab is open and they are setting the enemy start
	 * or end location.
	 */
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
			if (mazeStarted == false) {
				enemyControls.setVisible();
			}
		} else if (selected == addDecorationSelected) {
			JLabel newDecoration = new JLabel("");
			String[] newDecorationAttributes = getNewDecorationAttributes(
					(String) specificDecorations.getSelectedItem());
			int height = Integer.parseInt(newDecorationAttributes[0]);
			int width = Integer.parseInt(newDecorationAttributes[1]);
			String imageURL = "/images/" + newDecorationAttributes[2];
			Point coordinates = mazePanel.getMousePosition();
			newDecoration.setBounds((int) coordinates.getX() - (height / 2), (int) coordinates.getY() - (width / 2),
					width, height);
			mazePanel.add(newDecoration);
			images.set_image_to_label(newDecoration, imageURL);
			DecorationNode decoration = new DecorationNode(newDecoration);
			decorationList.add(decoration);
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JPanel mazePanel = (JPanel) arg0.getComponent();
		int lineThickness = lineThicknessSlider.getValue();
		if (selected == freeDrawLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
		} else if (selected == eraserSelected) {
			ButtonFunctions.EraseCoordinate(mazePanel, eraserThicknessSlider.getValue());
		} else if (selected == horizontalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetHorizontialLineY((int) mouseCoordinate.getY());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
		} else if (selected == verticalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetVerticalLineX((int) mouseCoordinate.getX());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		JPanel mazePanel = (JPanel) arg0.getComponent();
		int lineThickness = lineThicknessSlider.getValue();
		if (selected == freeDrawLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
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
		// ADD COORDINATES TO xAndyDisplayCoordinates
		SetCoordinatesToXAndYDisplayCoordinates(mazePanel);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (selected == freeDrawLineSelected) {
			UndoStructure.AddLineUndoStack();
		} else if (selected == horizontalLineSelected) {
			UndoStructure.AddLineUndoStack();
		} else if (selected == verticalLineSelected) {
			UndoStructure.AddLineUndoStack();
		} else if (selected == eraserSelected) {
			RedoErasedLineStructure.AddLineToRedoErasedLineStack();
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
		} else if (selected == freeDrawLineSelected || selected == horizontalLineSelected
				|| selected == verticalLineSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetDrawOff();
			mazePanel.repaint();
		} else if (selected == startLocationSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			HoverImage.RemoveLabelFromMazePanel(mazePanel);
		} else if (selected == endLocationSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			HoverImage.RemoveLabelFromMazePanel(mazePanel);
		} else if (selected == addDecorationSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			HoverImage.RemoveLabelFromMazePanel(mazePanel);
		} else if (selected == enemiesSelected) {
			if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 1) {
				HoverImage.RemoveLabelFromMazePanel(mazePanel);
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 3) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.RemoveLabelFromMazePanel(mazePanel);
				}
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 2) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.RemoveLabelFromMazePanel(mazePanel);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		JPanel mazePanel = (MazePanel) arg0.getComponent();
		if (selected == eraserSelected) {
			((MazePanel) mazePanel).SetEraserOn();
			((MazePanel) mazePanel).SetEraserThickness(eraserThicknessSlider.getValue());
			Point eraserCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetEraserCoordinates(eraserCoordinates);
			mazePanel.repaint();
		} else if (selected == freeDrawLineSelected || selected == horizontalLineSelected
				|| selected == verticalLineSelected) {
			((MazePanel) mazePanel).SetDrawOn();
			((MazePanel) mazePanel).SetDrawThickness(this.lineThicknessSlider.getValue());
			Point drawCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetDrawCoordinates(drawCoordinates);
			mazePanel.repaint();
		} else if (selected == startLocationSelected) {
			HoverImage.AddLabelToMazePanel(mazePanel, "player");
		} else if (selected == endLocationSelected) {
			HoverImage.AddLabelToMazePanel(mazePanel, "endLocation");
		} else if (selected == addDecorationSelected) {
			HoverImage.AddLabelToMazePanel(mazePanel, (String) specificDecorations.getSelectedItem());
		} else if (selected == enemiesSelected) {
			if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 1) {
				HoverImage.AddLabelToMazePanel(mazePanel, EnemyControls.GetEnemyHeight(), EnemyControls.GetEnemyWidth(),
						EnemyControls.GetEnemyImagePath());
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 3) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.AddEnemyEndLocationToMazePanelVertical(mazePanel, enemy, "enemyEndLocation");
				}
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 2) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.AddEnemyEndLocationToMazePanelHorizontal(mazePanel, enemy, "enemyEndLocation");
				}
			}
		}
		// ADD COORDINATES TO xAndyDisplayCoordinates
		SetCoordinatesToXAndYDisplayCoordinates(mazePanel);
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
		} else if (selected == freeDrawLineSelected || selected == horizontalLineSelected
				|| selected == verticalLineSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			((MazePanel) mazePanel).SetDrawOn();
			((MazePanel) mazePanel).SetDrawThickness(this.lineThicknessSlider.getValue());
			Point drawCoordinates = ((MazePanel) mazePanel).getMousePosition();
			((MazePanel) mazePanel).SetDrawCoordinates(drawCoordinates);
			mazePanel.repaint();
		} else if (selected == startLocationSelected) {
			JPanel mazePanel = (MazePanel) arg0.getComponent();
			HoverImage.MoveLabelInMazePanel(mazePanel);
		} else if (selected == endLocationSelected) {
			HoverImage.MoveLabelInMazePanel(mazePanel);
		} else if (selected == addDecorationSelected) {
			HoverImage.MoveLabelInMazePanel(mazePanel);
		} else if (selected == enemiesSelected) {
			if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 1) {
				HoverImage.MoveEnemyLabelInMazePanel(mazePanel);
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 3) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.MoveEndLocationInMazePanelVertical(mazePanel, enemy);
				}
			} else if (EnemyControls.getEnemyComboBox().getSelectedItem() != null
					&& EnemyButtonFunctions.getEnemyControl() == 2) {
				int enemySelected = EnemyControls.GetEnemySelected();
				JLabel enemy = null;
				if (enemySelected == 1) {
					enemy = EnemyButtonFunctions.getEnemy1StartLocation();
				} else if (enemySelected == 2) {
					enemy = EnemyButtonFunctions.getEnemy2StartLocation();
				} else if (enemySelected == 3) {
					enemy = EnemyButtonFunctions.getEnemy3StartLocation();
				}
				if (enemy != null) {
					HoverImage.MoveEndLocationInMazePanelHorizontal(mazePanel, enemy);
				}
			}

		}
		// ADD COORDINATES TO xAndyDisplayCoordinates
		SetCoordinatesToXAndYDisplayCoordinates(mazePanel);
	}

	private void SetCoordinatesToXAndYDisplayCoordinates(JPanel mazePanel) {
		Point coordinates = mazePanel.getMousePosition();
		if (coordinates != null) {
			int x = (int) coordinates.getX();
			int y = (int) coordinates.getY();
			String coordinatesAsString = x + "," + y;
			xAndyDisplayCoordinates.setText(coordinatesAsString);
		}
	}

	private void SetOrigionalDecorationsList(JComboBox<String> specificDecorations) {
		InputStream resourceStream = getClass().getResourceAsStream("/decorationsList/Plain.txt");
		BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
		try {
			while (resourceReader.ready()) {
				String currentItem = resourceReader.readLine();
				specificDecorations.addItem(currentItem);
			}
			resourceStream.close();
			resourceReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String[] getNewDecorationAttributes(String itemName) {
		InputStream resourceStream = getClass().getResourceAsStream("/imageAttributes/Attributes.txt");
		BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
		String[] attributes = new String[3];
		try {
			while (resourceReader.ready()) {
				String currentLine = resourceReader.readLine();
				if (currentLine.equals(itemName)) {
					currentLine = resourceReader.readLine();
					attributes[0] = currentLine;
					currentLine = resourceReader.readLine();
					attributes[1] = currentLine;
					currentLine = resourceReader.readLine();
					attributes[2] = currentLine;
				}
			}
			resourceStream.close();
			resourceReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return attributes;
	}

	public static JLabel GetPlayerLabel() {
		return player;
	}

	public static EnemyControls GetEnemyControls() {
		return enemyControls;
	}

	public static void InitializeEnemyControls() {
		enemyControls = new EnemyControls();
		enemyControlsExists = true;
	}

	public static void SetSelected(int selected) {
		MazeDesignMainGUI.selected = selected;
	}

	public static LinkedList<DecorationNode> getDecorationList() {
		return decorationList;
	}

	public static void AddDecorationsToMazeAndList(LinkedList<DecorationNode> newDecorationList) {
		decorationList = newDecorationList;
		ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
		while (iterator.hasNext()) {
			iterator.next().AddDecorationToMazePanel(mazePanel);
		}
	}

	public static void RemoveDecorationsFromMazeAndList() {
		ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
		while (iterator.hasNext()) {
			mazePanel.remove(iterator.next().getDecorationNode());
		}
		mazePanel.repaint();
		decorationList.clear();
	}

	private void SaveMazeAs() {
		RemoveBordersFromMazeComponents();
		ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
		if (CheckForStartAndEndLocations() == true) {
			SaveFileExplorer.Load();
		} else {
			JOptionPane.showMessageDialog(null, "Maze needs start and end spots.");
		}
	}

	private void SaveLoadedMaze() {
		SaveLoadedFile.Save(loadedMazeFileLocation);
	}

	public static void SetLoadedMazeFileLocation(String loadedMazeFileLocation) {
		MazeDesignMainGUI.loadedMazeFileLocation = loadedMazeFileLocation;
	}

	public static boolean GetBlackoutMazeSetting() {
		return blackoutRadioButton.isSelected();
	}

	public static void SetBlackoutMazeSetting(boolean setting) {
		blackoutRadioButton.setSelected(setting);
	}
	
	public boolean mazeActive() {
		return mazeStarted;
	}
	
	public static void stopMaze() {
		stopMazeButton.doClick();
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}
