package mazeapplication;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
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
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import java.awt.ScrollPane;

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
	private PortalControlMenu portalControlMenu;
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

	// Selected Buttons.
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
	private int traceSelected = 10;
	private int rectangleSelected = 11;
	private int circleSelected = 12;
	private int startPortalSelected = 13;
	private int endPortalSelected = 14;

	private static PlayerObject player;
	// Display for end coordinates.
	private JLabel xAndyDisplayCoordinates;
	private static boolean mazeStarted = false;
	// changesMade: for exit without saving feature.
	private boolean changesMade = false;
	// Previous value of the draw line slider.
	private int previousValue = 0;
	private static PortalNode currentPortal;
	private WinMessageEditorFrame winMessageEditor;
	GenerateMazeWindow generateMazeWindow = null;
	private ImageIcon optionImageIcon;
	
	private static String playerImageLocation = "/images/O.png";
	JComboBox<String> playerImageBox;
	private static JButton clearMazeButton;
	
	private static int decorationHeight;
	private static int deocrationWidth;
	private static String decorationname;
	

	public MazeDesignMainGUI(JFrame frmMazeKraze) {
		initialize(frmMazeKraze);
	}

	private void initialize(JFrame frmMazeKraze) {
		
		/*
		 * Frame: Maze Design Frame, holds everything. 
		 */
		frame = new JFrame();
		frame.setIconImage(
				Toolkit.getDefaultToolkit().getImage(MazeDesignMainGUI.class.getResource("/images/logo.png")));
		frame.getContentPane().setFocusable(false);
		frame.setBounds(100, 100, 935, 730);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(2, 48, 71));
		frame.setTitle("MazeKraze");
		frame.setResizable(false);
		
		/*
		 * Frame Window Listener: 
		 * Actions:
		 * 1.) Displays if the maze is not saved on exiting the window. 
		 * 2.) Turns off the music if any is playing.
		 */
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				if (changesMade == true) {
					int playerChoice = JOptionPane.showConfirmDialog(null, "Exit without saving?", "Exit Program?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
					if (playerChoice == JOptionPane.YES_OPTION) {
						// End program.
						System.exit(0);
					} else {
						frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					}
				}
				else {
					// End program.
					System.exit(0);
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
			}
		});
		
//		ArrayList<JLabel> actionLabelButtons = new ArrayList<JLabel>();
//		
//		JLabel startLocationLabelButton = new JLabel();
//		startLocationLabelButton.setBounds(10, 94, 154, 21);
//		actionLabelButtons.add(startLocationLabelButton);
//		
//		JLabel endLocationLabelButton = new JLabel();
//		endLocationLabelButton.setBounds(10,116,154,21);
//		actionLabelButtons.add(endLocationLabelButton);
//		
//		JLabel traceLabelButton = new JLabel();
//		traceLabelButton.setBounds(10,138,154,21);
//		actionLabelButtons.add(traceLabelButton);
//		LoadButtonImagesToLabelsAndAddMouseListeners loadActionLabelButtons = new LoadButtonImagesToLabelsAndAddMouseListeners();
//		loadActionLabelButtons.Load(actionLabelButtons);
		
		/*
		 * General black border used in the MazePanel and other components. 
		 */
		Border blackBorder = BorderFactory.createLineBorder(Color.black, 1);
		
		/*
		 * Setting the generic win message of a maze.
		 */
		ButtonFunctions.setWinMessage("You Win!");

		/*
		 * Image Icon that is used for the menu items.
		 */
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource("/images/logo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image dimg = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		
		/*
		 * JMenuBar menuBar: holds all the menus for the maze design window. 
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new Color(120,177,199));
		menuBar.setBorder(blackBorder);
		
		/*
		 * Menus for the JMenuBar.
		 * Menus: mazeMenu, resourcesMenu, helpMenu, musicAcknowledgment
		 */
		JMenu mazeMenu = new JMenu("File");
		JMenu winMessageMenu = new JMenu("Win Message");
		JMenu portalMenu = new JMenu("Portals");
		JMenu generateMazeMenu = new JMenu("Generate Maze");
		JMenu resourcesMenu = new JMenu("Resources");
		JMenu musicAcknowledgment = new JMenu("Music Acknowledgement");
		JMenu helpMenu = new JMenu("Help");
		
		MouseListener menuBackgroundColorListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				e.getComponent().setBackground(Color.gray);
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				e.getComponent().setBackground(new Color(120,177,199));
				
			}
			
		};
		
		// Setting the menu's background and adding MouseListeners to change the background when the mouse enters them.
		mazeMenu.setOpaque(true);
		mazeMenu.setBackground(new Color(120,177,199));
		mazeMenu.addMouseListener(menuBackgroundColorListener);
		
		winMessageMenu.setOpaque(true);
		winMessageMenu.setBackground(new Color(120,177,199));
		winMessageMenu.addMouseListener(menuBackgroundColorListener);
		
		portalMenu.setOpaque(true);
		portalMenu.setBackground(new Color(120,177,199));
		portalMenu.addMouseListener(menuBackgroundColorListener);
		
		generateMazeMenu.setOpaque(true);
		generateMazeMenu.setBackground(new Color(120,177,199));
		generateMazeMenu.addMouseListener(menuBackgroundColorListener);
		
		resourcesMenu.setOpaque(true);
		resourcesMenu.setBackground(new Color(120,177,199));
		resourcesMenu.addMouseListener(menuBackgroundColorListener);
		
		musicAcknowledgment.setOpaque(true);
		musicAcknowledgment.setBackground(new Color(120,177,199));
		musicAcknowledgment.addMouseListener(menuBackgroundColorListener);
		 
		helpMenu.setOpaque(true);
		helpMenu.setBackground(new Color(120,177,199));
		helpMenu.addMouseListener(menuBackgroundColorListener);
		
		// mazeMenu: Declaring new menu items, adding them to the menu and adding the correct action listeners for them.
		JMenuItem saveMazeMenuItem = new JMenuItem("Save Maze", imageIcon);
		JMenuItem saveMazeAsImageMenuItem = new JMenuItem("Save Maze As Image", imageIcon);
		JMenuItem loadMazeMenuItem = new JMenuItem("Load Maze", imageIcon);
		JMenuItem saveMazeAsMenuItem = new JMenuItem("Save Maze As", imageIcon);
		mazeMenu.add(saveMazeMenuItem);
		mazeMenu.add(saveMazeAsMenuItem);
		mazeMenu.add(saveMazeAsImageMenuItem);
		mazeMenu.add(loadMazeMenuItem);
		saveMazeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				RemoveBordersFromMazeComponents();
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if (CheckForStartAndEndLocations() == true) {
					if (loadedMazeFileLocation == null) {
						SaveFileExplorer.Load();
					} else {
						SaveLoadedMaze();
						JOptionPane.showMessageDialog(null, "Maze Saved", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Maze needs start and end spots.","Maze Start and End Spots Warning", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
				}
			}

		});
		saveMazeAsMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RemoveBordersFromMazeComponents();
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if (CheckForStartAndEndLocations() == true) {					
					SaveFileExplorer.Load();
				} else {
					JOptionPane.showMessageDialog(null, "Maze needs start and end spots.", "Maze Start and End Spots Warning", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
				}
			}
		});
		saveMazeAsImageMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if(((MazePanel) mazePanel).getDecorationGridActive() == true) {
					((MazePanel) mazePanel).setDecorationGridActive(false);
					SaveMazeAsImage.Load(mazePanel);
					((MazePanel) mazePanel).setDecorationGridActive(true);
				}
				else {
					SaveMazeAsImage.Load(mazePanel);
				}
			}
		});
		loadMazeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					boolean mazeLoaded = LoadFileExplorer.Load();
					if(mazeLoaded == true) {
						if (BackgroundMusic.isPaused() == false) {
							BackgroundMusic.PlayCorrectAudioContinuously(mazePanel.getBackground());
						}
						UndoStructure.clearUndoStack();
						RedoErasedLineStructure.ClearRedoStack();
						RemoveBordersFromMazeComponents();
						ButtonFunctions.GetTracemap().clear();
						if(portalControlMenu != null) {
							portalControlMenu.LoadPortalsToPortalControlPanel();
						}
						LinkedList<PortalNode> portalNodeList = ButtonFunctions.getPortalNodeList();
						for(int i = 0; i < portalNodeList.size(); i++) {
							PortalNode currentNode = portalNodeList.get(i);
							if(currentNode.getLinkStart() != null) {
								mazePanel.add(currentNode.getLinkStart());
							}
							if(currentNode.getLinkEnd() != null) {
								mazePanel.add(currentNode.getLinkEnd());
							}
						}
						if(winMessageEditor != null) {
							winMessageEditor.getWinMessageDisplayArea().setText(ButtonFunctions.getWinMessage().getMessage());
						}
						mazePanel.repaint();
					}
				} catch (EOFException e) {
					e.printStackTrace();
				}
			}
		});
		
		/*
		 * Win message menu item.
		 */
		JMenuItem winMessageMenuItem = new JMenuItem("Edit Win Message", imageIcon);
		winMessageMenu.add(winMessageMenuItem);
		winMessageMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(winMessageEditor == null) {
					winMessageEditor = new WinMessageEditorFrame();
				}
				else {
					winMessageEditor.setVisible(true);
				}
			}
		});
		
		JMenuItem generateMazeMenuItem = new JMenuItem("Generate Maze", imageIcon);
		generateMazeMenu.add(generateMazeMenuItem);
		generateMazeMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(generateMazeWindow == null) {
					generateMazeWindow = new GenerateMazeWindow();
				}
				else {
					generateMazeWindow.setVisible(true);
				}
			}
			
		});
		
		// resourcesMenu: Declaring new menu items, adding them to the menu and adding the correct action listeners for them.
		JMenuItem onlineLessonsMenuItem = new JMenuItem("Online Lessons", imageIcon);
		JMenuItem downloadMazesMenuItem = new JMenuItem("Download Mazes", imageIcon);
		JMenuItem downloadTemplatesMenuItem = new JMenuItem("Download Templates", imageIcon);
		resourcesMenu.add(onlineLessonsMenuItem);
		resourcesMenu.add(downloadMazesMenuItem);
		resourcesMenu.add(downloadTemplatesMenuItem);
		onlineLessonsMenuItem.addActionListener(new ActionListener() {
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
		downloadMazesMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = new URI("https://mazekraze.com/mazekraze/novice-mazes/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {

				}
			}
		});
		downloadTemplatesMenuItem.addActionListener(new ActionListener() {
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
		
		// linkControlMenu: Declaring new menu items, adding them to the menu and adding the correct action listeners for them.
		JMenuItem controlPortalsItem = new JMenuItem("Control Portals", imageIcon);
		portalMenu.add(controlPortalsItem);
		controlPortalsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(portalControlMenu == null) {
					portalControlMenu = new PortalControlMenu();
				}
				else {
					portalControlMenu.setVisible(true);
				}
				if(enemyControlsExists == true && enemyControls.isVisible() == true) {
					EnemyControls.SetDisposed();
				}
			}
			
		});
		
		// helpMenu: Declaring new menu items, adding them to the menu and adding the correct action listeners for them.
		JMenuItem helpItem = new JMenuItem("Help", imageIcon);
		helpMenu.add(helpItem);
		helpItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = new URI("https://mazekraze.com/help/");
					java.awt.Desktop.getDesktop().browse(uri);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// musicAcknowledgment: Declaring new menu items, adding them to the menu and adding the correct action listeners for them.
		JMenuItem musicResourceItem = new JMenuItem("Music Acknowledgments", imageIcon);
		musicAcknowledgment.add(musicResourceItem);
		musicResourceItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Music: “Blue Afternoon”, from PlayOnLoop.com\n"
						+ "Licensed under Creative Commons by Attribution 4.0\n" +
						"Music for everything else done by Aneris Rivera Wagner");
			}
		});
		
		//Adding the menu's to the menuBar and then setting the menuBar to the frame.
		menuBar.add(mazeMenu);
		menuBar.add(winMessageMenu);
		menuBar.add(portalMenu);
		menuBar.add(generateMazeMenu);
		menuBar.add(resourcesMenu);
		menuBar.add(musicAcknowledgment);
		menuBar.add(helpMenu);
		frame.setJMenuBar(menuBar);
		
		/*
		 * JPanel mazePanel (MazePanel): Holds the maze users create.
		 */
		mazePanel = new MazePanel();
		mazePanel.setBackground(Color.WHITE);
		mazePanel.setBounds(10, 18, 708, 648);
		frame.getContentPane().add(mazePanel);
		mazePanel.setBorder(blackBorder);
		mazePanel.setLayout(null);
		mazePanel.addMouseListener(this);
		mazePanel.addMouseMotionListener(this);
		
		/*
		 * JPanel optionsPanel: Container for all possible options for maze design. 
		 */
		JPanel optionsPanel = new JPanel();
		optionsPanel.setBorder(null);
		optionsPanel.setBackground(new Color(120, 177, 199));
		optionsPanel.setBounds(728, 18, 180, 648);
		frame.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		/*
		 * JPanel changingOptionsPanel: Holds specific buttons, JSliders, and JRadioButtons for specific maze design.
		 */
		JPanel changingOptionsPanel = new JPanel();
		changingOptionsPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		changingOptionsPanel.setBackground(new Color(120, 177, 199));
		changingOptionsPanel.setBounds(6, 144, 168, 366);
		optionsPanel.add(changingOptionsPanel);
		changingOptionsPanel.setLayout(null);
		
		Font programButtonFont = new Font("Tahoma", Font.PLAIN, 13);
		Font labelFont = new Font("Tahoma", Font.BOLD, 13);
		Color buttonColor = new Color(170, 204, 0);
		
		/*
		 * JRadioButton startAndStopRadioButton: Radio button that when selected will change the changingOptionsPanel
		 * 										 for everything to do with start and stop.
		 */
		JRadioButton startAndStopRadioButton = new JRadioButton("Start & Stop Spots");
		startAndStopRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		startAndStopRadioButton.setBorder(null);
		startAndStopRadioButton.setFocusable(false);
		startAndStopRadioButton.setFont(programButtonFont);
		startAndStopRadioButton.setForeground(new Color(0, 0, 0));
		startAndStopRadioButton.setBackground(new Color(255, 215, 0));
		startAndStopRadioButton.setBounds(6, 6, 168, 21);
		optionsPanel.add(startAndStopRadioButton);
		
		/*
		 * Buttons for the start and stop features. 
		 */

		JButton startLocationButton = new JButton("Start Location");
		startLocationButton.setForeground(new Color(0, 0, 0));
		startLocationButton.setFocusable(false);
		startLocationButton.setBackground(buttonColor);
		startLocationButton.setToolTipText("Player start loaction.");
		startLocationButton.setBounds(10, 10, 154, 20);
		startLocationButton.setFont(programButtonFont);

		JButton endLocationButton = new JButton("End Location");
		endLocationButton.setFocusable(false);
		endLocationButton.setBackground(buttonColor);
		endLocationButton.setToolTipText("Player end location \"X marks the spot\"");
		endLocationButton.setBounds(10, 31, 154, 20);
		endLocationButton.setFont(programButtonFont);

		JButton traceButton = new JButton("Trace");
		traceButton.setFocusable(false);
		traceButton.setBackground(buttonColor);
		traceButton.setToolTipText("Trace your maze to show the solve solution");
		traceButton.setBounds(10, 52, 154, 20);
		traceButton.setFont(programButtonFont);

		JButton clearTraceButton = new JButton("Clear Trace");
		clearTraceButton.setFocusable(false);
		clearTraceButton.setBackground(buttonColor);
		clearTraceButton.setToolTipText("Clear your trace");
		clearTraceButton.setBounds(10, 73, 154, 20);
		clearTraceButton.setFont(programButtonFont);
		
		JLabel setPlayerImageLabel = new JLabel("Set Player:");
		setPlayerImageLabel.setFont(labelFont);
		setPlayerImageLabel.setBounds(10, 94, 154, 16);
		
		playerImageBox = new JComboBox<String>();
		playerImageBox.setFocusable(false);
		playerImageBox.setToolTipText("The type of start location/ player you want.");
		playerImageBox.setBounds(10,112,100, 20);
		playerImageBox.setModel(new DefaultComboBoxModel<String>(new String[] {"original"}));
		
		JButton setPlayerImageButton = new JButton("Set");
		setPlayerImageButton.setFocusable(false);
		setPlayerImageButton.setToolTipText("Set the player type.");
		setPlayerImageButton.setBackground(buttonColor);
		setPlayerImageButton.setBounds(111,112,54,20);
		/*
		 * Starting values of the changingOptionsPanel.
		 */
		startAndStopRadioButton.setSelected(true);
		changingOptionsPanel.add(startLocationButton);
		changingOptionsPanel.add(endLocationButton);
		changingOptionsPanel.add(traceButton);
		changingOptionsPanel.add(clearTraceButton);
		changingOptionsPanel.add(setPlayerImageLabel);
		changingOptionsPanel.add(playerImageBox);
		changingOptionsPanel.add(setPlayerImageButton);
		
		/*
		 * JRadioButton drawingRadioButton: When selected changes the changingOptionsPanel to contain the drawing features.
		 */
		JRadioButton drawingRadioButton = new JRadioButton("Drawing Walls");
		drawingRadioButton.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		drawingRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		drawingRadioButton.setFocusable(false);
		drawingRadioButton.setFont(programButtonFont);
		drawingRadioButton.setBackground(new Color(255, 215, 0));
		drawingRadioButton.setForeground(new Color(0, 0, 0));
		drawingRadioButton.setBounds(6, 29, 168, 21);
		optionsPanel.add(drawingRadioButton);
		
		/*
		 * Buttons, JSliders, Labels, ComboBox's for drawing features.
		 */

		JButton freeDrawButton = new JButton("Free Draw");
		freeDrawButton.setFocusable(false);
		freeDrawButton.setBackground(buttonColor);
		freeDrawButton.setBounds(10, 68, 154, 20);
		freeDrawButton.setFont(programButtonFont);

		final int minLineThickness = 2;
		final int maxLineThickness = 10;
		final int initialLineThickness = 4;
		lineThicknessSlider = new JSlider(JSlider.HORIZONTAL, minLineThickness, maxLineThickness, initialLineThickness);
		lineThicknessSlider.setFocusable(false);
		lineThicknessSlider.setBorder(new LineBorder(new Color(0, 0, 0)));
		lineThicknessSlider.setBackground(new Color(255, 255, 224));
		lineThicknessSlider.setToolTipText("Changes the thickness of the line you draw");
		lineThicknessSlider.setBounds(10, 220, 154, 54);
		lineThicknessSlider.setMajorTickSpacing(2);
		lineThicknessSlider.setPaintTicks(true);
		lineThicknessSlider.setPaintLabels(true);
		previousValue = lineThicknessSlider.getValue();
		lineThicknessSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value = lineThicknessSlider.getValue();
				if (value > previousValue) {
					if (value % 2 != 0) {
						lineThicknessSlider.setValue(value + 1);
					}
				} else if (value < previousValue) {
					if (value % 2 != 0) {
						lineThicknessSlider.setValue(value - 1);
					}
				}
				previousValue = lineThicknessSlider.getValue();
			}

		});

		JLabel lineThicknessLabel = new JLabel("Line Thickness:");
		lineThicknessLabel.setFocusable(false);
		lineThicknessLabel.setBounds(10, 206, 100, 13);

		JButton eraserButton = new JButton("Eraser");
		eraserButton.setFocusable(false);
		eraserButton.setBackground(buttonColor);
		eraserButton.setToolTipText("Erases lines you draw");
		eraserButton.setBounds(10, 467, 154, 21);
		eraserButton.setFont(programButtonFont);

		JButton horizontalLineButton = new JButton("Horizontal Line");
		horizontalLineButton.setBackground(buttonColor);
		horizontalLineButton.setToolTipText("left and right line");
		horizontalLineButton.setBounds(10, 98, 154, 21);
		horizontalLineButton.setFocusable(false);
		horizontalLineButton.setFont(programButtonFont);

		JButton verticalLineButton = new JButton("Vertical Line");
		verticalLineButton.setBackground(buttonColor);
		verticalLineButton.setToolTipText("Up and down line.");
		verticalLineButton.setBounds(10, 129, 154, 21);
		verticalLineButton.setFocusable(false);
		verticalLineButton.setFont(programButtonFont);

		JButton rectangleButton = new JButton("Rectangle");
		rectangleButton.setBackground(buttonColor);
		rectangleButton.setToolTipText("Draws a perfect rectangle");
		rectangleButton.setFocusable(false);
		rectangleButton.setFont(programButtonFont);

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
		colorComboBox.setFont(programButtonFont);

		JLabel lblLineColor = new JLabel("Line Color:");
		lblLineColor.setFocusable(false);
		lblLineColor.setFont(programButtonFont);
		lblLineColor.setVerticalAlignment(SwingConstants.BOTTOM);
		lblLineColor.setBounds(10, 160, 100, 13);
		
		JLabel currentColorLabel = new JLabel("");
		currentColorLabel.setFocusable(false);
		currentColorLabel.setBackground(new Color(0, 0, 0));
		currentColorLabel.setBounds(129, 178, 35, 20);
		currentColorLabel.setOpaque(true);
		
		JButton undoButton = new JButton("Undo Line");
		undoButton.setToolTipText("Undo the last drawn line - \"ctrl+z\"");
		undoButton.setBackground(buttonColor);
		undoButton.setFont(programButtonFont);
		undoButton.setFocusable(false);
		undoButton.setBounds(10, 399, 171, 35);

		JButton redoLineButton = new JButton("<html>Redo Erased Line</html>");
		redoLineButton.setToolTipText("Redo last erased line - \"ctrl+r\"");
		redoLineButton.setFont(programButtonFont);
		redoLineButton.setFocusable(false);
		redoLineButton.setBackground(buttonColor);
		redoLineButton.setBounds(10, 444, 171, 35);
		
		/*
		 * JRadioButton backgroundRadioButton: When selected changes the changingOptionsPanel to contain the background features.
		 */
		
		JRadioButton backgroundRadioButton = new JRadioButton("Background");
		backgroundRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		backgroundRadioButton.setFocusable(false);
		backgroundRadioButton.setFont(programButtonFont);
		backgroundRadioButton.setBackground(new Color(255, 215, 0));
		backgroundRadioButton.setForeground(new Color(0, 0, 0));
		backgroundRadioButton.setBounds(6, 75, 168, 21);
		optionsPanel.add(backgroundRadioButton);
		
		/*
		 * Buttons, JComboBox's, and Labels for background features.
		 */
		
		JComboBox<String> backgroundColorComboBox = new JComboBox<String>();
		backgroundColorComboBox.setBackground(new Color(255, 255, 255));
		backgroundColorComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "White", "Sky Blue", "Sand", "Grass", "Mud", "Glacier", "Swamp", "Storm Gray" }));
		backgroundColorComboBox.setBounds(10, 360, 85, 21);
		backgroundColorComboBox.setFocusable(false);
		backgroundColorComboBox.setFont(programButtonFont);

		JLabel backgroundColorLabel = new JLabel("Background Color:");
		backgroundColorLabel.setFocusable(false);
		backgroundColorLabel.setBounds(10, 340, 169, 13);

		JButton setBackgroundColorButton = new JButton("Set");
		setBackgroundColorButton.setBackground(buttonColor);
		setBackgroundColorButton.setBounds(101, 360, 63, 21);
		setBackgroundColorButton.setFocusable(false);
		setBackgroundColorButton.setFont(programButtonFont);
		
		/*
		 * JRadioButton decorationsRadioButton: When selected changes the changingOptionsPanel to contain the decorations features.
		 */
		
		JRadioButton decorationsRadioButton = new JRadioButton("Decorations");
		decorationsRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		decorationsRadioButton.setFocusable(false);
		decorationsRadioButton.setFont(programButtonFont);
		decorationsRadioButton.setBackground(new Color(255, 215, 0));
		decorationsRadioButton.setForeground(new Color(0, 0, 0));
		decorationsRadioButton.setBounds(6, 52, 168, 21);
		optionsPanel.add(decorationsRadioButton);
		
		/*
		 * Buttons, ComboBox's, Labels, RadioButtons, ScrollPanes, and Panels for decoration features.
		 */

		JLabel decorationsLabel = new JLabel("Decorations:");
		decorationsLabel.setFocusable(false);
		decorationsLabel.setFont(programButtonFont);
		decorationsLabel.setBounds(10, 10, 151, 22);

		JButton addDecorationButton = new JButton("Add Decoration");
		addDecorationButton.setFocusable(false);
		addDecorationButton.setBackground(buttonColor);
		addDecorationButton.setBounds(10, 131, 151, 21);
		addDecorationButton.setFont(programButtonFont);

		JComboBox<String> typeOfDecorationComboBox = new JComboBox<String>();
		typeOfDecorationComboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] { "Plain", "Sky Blue", "Sand", "Grass", "Mud", "Glacier", "Swamp", "Storm Gray" }));
		typeOfDecorationComboBox.setFocusable(false);
		typeOfDecorationComboBox.setBounds(10, 69, 151, 21);
		typeOfDecorationComboBox.setFont(programButtonFont);

		JLabel typeOfDecorationLabel = new JLabel("Biome:");
		typeOfDecorationLabel.setFocusable(false);
		typeOfDecorationLabel.setBounds(10, 46, 151, 13);

		specificDecorations = new JComboBox<String>();
		specificDecorations.setFocusable(false);
		specificDecorations.setBounds(10, 100, 151, 21);
		specificDecorations.setFont(programButtonFont);

		JButton deleteDecorationButton = new JButton("Delete Decoration");
		deleteDecorationButton.setFocusable(false);
		deleteDecorationButton.setBackground(buttonColor);
		deleteDecorationButton.setBounds(10, 162, 151, 21);
		deleteDecorationButton.setFont(programButtonFont);

		JRadioButton smallDecorationGridModeButton = new JRadioButton("Grid Mode: Small");
		smallDecorationGridModeButton.setBackground(buttonColor);
		JRadioButton mediumDecorationGridModeButton = new JRadioButton("Grid Mode: Medium");
		mediumDecorationGridModeButton.setBackground(buttonColor);
		JRadioButton largeDecorationGridModeButton = new JRadioButton("Grid Mode: Large");
		largeDecorationGridModeButton.setBackground(buttonColor);
		JRadioButton gridModeOff = new JRadioButton("Grid Off");
		gridModeOff.setBackground(buttonColor);
		
		ScrollPane scrollPane = new ScrollPane();
		JPanel imagePanel = new JPanel();
		scrollPane.setFocusable(false);
		imagePanel.setFocusable(false);
		scrollPane.setBounds(10,200,151,60);
		imagePanel.setBounds(0,0,151,60);
		scrollPane.add(imagePanel);

		ButtonGroup decorationModeButtonGroup = new ButtonGroup();
		decorationModeButtonGroup.add(smallDecorationGridModeButton);
		decorationModeButtonGroup.add(mediumDecorationGridModeButton);
		decorationModeButtonGroup.add(largeDecorationGridModeButton);
		decorationModeButtonGroup.add(gridModeOff);
		
		/*
		 * Buttons that are always visible for specific features.
		 */

		JButton pauseMusicButton = new JButton("Pause Music");
		pauseMusicButton.setFont(programButtonFont);
		pauseMusicButton.setForeground(new Color(255, 255, 255));
		pauseMusicButton.setBounds(6, 578, 168, 20);
		optionsPanel.add(pauseMusicButton);
		pauseMusicButton.setFocusable(false);
		pauseMusicButton.setBackground(new Color(0, 0, 255));

		JButton playMusicButton = new JButton("Play Music");
		playMusicButton.setFont(programButtonFont);
		playMusicButton.setForeground(new Color(255, 255, 255));
		playMusicButton.setBounds(6, 600, 168, 20);
		optionsPanel.add(playMusicButton);
		playMusicButton.setFocusable(false);
		playMusicButton.setBackground(new Color(0, 0, 255));

		JButton enemiesButton = new JButton("Enemies");
		enemiesButton.setFont(programButtonFont);
		enemiesButton.setForeground(new Color(0, 0, 0));
		enemiesButton.setBounds(6, 98, 168, 21);
		optionsPanel.add(enemiesButton);
		enemiesButton.setBackground(new Color(255, 215, 0));
		enemiesButton.setToolTipText("Add enemies to your maze.");
		enemiesButton.setFocusable(false);

		clearMazeButton = new JButton("Clear Maze");
		clearMazeButton.setFont(programButtonFont);
		clearMazeButton.setForeground(new Color(0, 0, 0));
		clearMazeButton.setBounds(6, 121, 168, 21);
		optionsPanel.add(clearMazeButton);
		clearMazeButton.setBackground(new Color(255, 215, 0));
		clearMazeButton.setToolTipText("Completely clears maze, save before!");
		clearMazeButton.setFocusable(false);

		blackoutRadioButton = new JRadioButton("Set Blackout");
		blackoutRadioButton.setHorizontalAlignment(SwingConstants.LEFT);
		blackoutRadioButton.setFont(programButtonFont);
		blackoutRadioButton.setForeground(new Color(0, 0, 0));
		blackoutRadioButton.setBackground(new Color(255, 215, 0));
		blackoutRadioButton.setBounds(6, 512, 168, 20);
		optionsPanel.add(blackoutRadioButton);
		blackoutRadioButton.setFocusable(false);

		SetOrigionalDecorationsList(specificDecorations);
		SetDecorationsToImagePanel(imagePanel, addDecorationButton,
				freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
				startLocationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);

		ButtonGroup optionsRadioButtonGroup = new ButtonGroup();
		optionsRadioButtonGroup.add(startAndStopRadioButton);
		optionsRadioButtonGroup.add(drawingRadioButton);
		optionsRadioButtonGroup.add(decorationsRadioButton);
		optionsRadioButtonGroup.add(backgroundRadioButton);
		
		startMazeButton = new JButton("Start Maze");
		startMazeButton.setFont(programButtonFont);
		startMazeButton.setForeground(new Color(255, 255, 255));
		startMazeButton.setBounds(6, 534, 168, 20);
		optionsPanel.add(startMazeButton);
		startMazeButton.setBackground(new Color(0, 128, 128));
		startMazeButton.setToolTipText("Starts the maze, makes your player moveable.");
		startMazeButton.setFocusable(false);

		stopMazeButton = new JButton("Stop Maze");
		stopMazeButton.setFont(programButtonFont);
		stopMazeButton.setForeground(new Color(255, 255, 255));
		stopMazeButton.setBounds(6, 556, 168, 20);
		optionsPanel.add(stopMazeButton);
		stopMazeButton.setBackground(new Color(0, 128, 128));
		stopMazeButton.setToolTipText("Stops the maze and resets.");
		stopMazeButton.setEnabled(false);
		stopMazeButton.setFocusable(false);
		
		JButton backToMainMenuButton = new JButton("Main Menu");
		backToMainMenuButton.setBounds(6, 622, 168, 20);
		backToMainMenuButton.setFont(programButtonFont);
		backToMainMenuButton.setBackground(new Color(0, 0, 255));
		backToMainMenuButton.setForeground(Color.white);
		optionsPanel.add(backToMainMenuButton);
		
		JLabel xAndyLabel = new JLabel("X,Y:");
		xAndyLabel.setBounds(10, 0, 27, 18);
		frame.getContentPane().add(xAndyLabel);
		xAndyLabel.setFocusable(false);
		xAndyLabel.setForeground(new Color(255, 192, 203));

		xAndyDisplayCoordinates = new JLabel("");
		xAndyDisplayCoordinates.setBounds(34, 0, 54, 18);
		frame.getContentPane().add(xAndyDisplayCoordinates);
		xAndyDisplayCoordinates.setFocusable(false);
		xAndyDisplayCoordinates.setForeground(new Color(255, 192, 203));
		
		
		/*
		 * Selected Listener which sets the selected value back to 0 so nothing can be done to the maze.
		 * Used when switching roles.
		 */
		ActionListener setSelectedListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = 0;
				SetAllButtonForegroundBlack(startLocationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		};
		controlPortalsItem.addActionListener(setSelectedListener);
		decorationsRadioButton.addActionListener(setSelectedListener);
		backgroundRadioButton.addActionListener(setSelectedListener);
		drawingRadioButton.addActionListener(setSelectedListener);
		startAndStopRadioButton.addActionListener(setSelectedListener);

		/*
		 * Button ActionListeners
		 */
		
		/*
		 * ActionListeners for the start and stop options.
		 */
		startLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				pressedKeys.clear();
				selected = startLocationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(startLocationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		});

		endLocationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = endLocationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(endLocationButton,
						startLocationButton, freeDrawButton, eraserButton, verticalLineButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}

		});

		traceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = traceSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(traceButton, endLocationButton,
						startLocationButton, freeDrawButton, eraserButton, verticalLineButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, enemiesButton, rectangleButton);
			}

		});

		clearTraceButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ButtonFunctions.GetTracemap().clear();
				mazePanel.repaint();
			}
		});
		
		setPlayerImageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String[] playerImagePaths = new String[] {"/images/O.png"};
				int index = playerImageBox.getSelectedIndex();
				playerImageLocation = playerImagePaths[index];
				ButtonFunctions.ChangePlayerImage(playerImagePaths[index]);
			}
		});
		
		
		/*
		 *  ActionListeners for the drawing feature. 
		 */
		
		freeDrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = freeDrawLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(freeDrawButton,
						startLocationButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}

		});

		eraserButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = eraserSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(eraserButton,
						startLocationButton, endLocationButton, freeDrawButton, verticalLineButton,
						horizontalLineButton, addDecorationButton, deleteDecorationButton, traceButton, enemiesButton,
						rectangleButton);
			}

		});

		horizontalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = horizontalLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(horizontalLineButton,
						startLocationButton, endLocationButton, freeDrawButton, eraserButton, verticalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}

		});

		verticalLineButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = verticalLineSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(verticalLineButton,
						startLocationButton, endLocationButton, freeDrawButton, eraserButton, horizontalLineButton,
						addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		});

		rectangleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = rectangleSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(rectangleButton,
						verticalLineButton, startLocationButton, endLocationButton, freeDrawButton, eraserButton,
						horizontalLineButton, addDecorationButton, deleteDecorationButton, traceButton, enemiesButton);
			}
		});

//		circleButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				selected = circleSelected;
//
//			}
//
//		});
		
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

		/*
		 * Background Button ActionListeners.
		 */
		setBackgroundColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int index = backgroundColorComboBox.getSelectedIndex();
				mazePanel.setBackground(backgroundColors[index]);
				if (BackgroundMusic.isPaused() == false) {
					BackgroundMusic.PlayCorrectAudioContinuously(backgroundColors[index]);
				}
				changesMade = true;
			}
		});

		colorComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentColorLabel.setBackground(GetColorFromColorComboBox());
			}

		});
		
		/*
		 * Decoration ActionListeners.
		 */

		typeOfDecorationComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				specificDecorations.removeAllItems();
				InputStream resourceStream = getClass().getResourceAsStream("/decorationsList/" + typeOfDecorationComboBox.getSelectedItem() + ".txt");
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
				SetDecorationsToImagePanel(imagePanel, addDecorationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						startLocationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		});
		
		specificDecorations.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = addDecorationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(addDecorationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						startLocationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		});

		addDecorationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				selected = addDecorationSelected;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(addDecorationButton,
						freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
						startLocationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
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
						startLocationButton, addDecorationButton, traceButton, enemiesButton, rectangleButton);
			}
		});
		
		smallDecorationGridModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MazePanel) mazePanel).setDecorationGridActive(true);
				((MazePanel) mazePanel).setSmallDecorationGridModeOn(true);
				((MazePanel) mazePanel).setMediumDecorationGridModeOn(false);
				((MazePanel) mazePanel).setLargeDecorationGridModeOn(false);
				mazePanel.repaint();
				ButtonFunctions.removeBordersFromPortals(mazePanel);
				if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
					portalControlMenu.setVisible(false);
				}
			}
		});

		mediumDecorationGridModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MazePanel) mazePanel).setDecorationGridActive(true);
				((MazePanel) mazePanel).setSmallDecorationGridModeOn(false);
				((MazePanel) mazePanel).setMediumDecorationGridModeOn(true);
				((MazePanel) mazePanel).setLargeDecorationGridModeOn(false);
				mazePanel.repaint();
				ButtonFunctions.removeBordersFromPortals(mazePanel);
				if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
					portalControlMenu.setVisible(false);
				}
			}
		});

		largeDecorationGridModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MazePanel) mazePanel).setDecorationGridActive(true);
				((MazePanel) mazePanel).setSmallDecorationGridModeOn(false);
				((MazePanel) mazePanel).setMediumDecorationGridModeOn(false);
				((MazePanel) mazePanel).setLargeDecorationGridModeOn(true);
				mazePanel.repaint();
				ButtonFunctions.removeBordersFromPortals(mazePanel);
				if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
					portalControlMenu.setVisible(false);
				}
			}
		});

		gridModeOff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((MazePanel) mazePanel).setDecorationGridActive(false);
				ButtonFunctions.removeBordersFromPortals(mazePanel);
				if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
					portalControlMenu.setVisible(false);
				}
			}

		});
		
		/*
		 * ActionListener that changes the changingOptionsPanel to fit the specific dimensions.
		 */

		ActionListener optionsRadioButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (startAndStopRadioButton.isSelected()) {
					((MazePanel) mazePanel).setDecorationGridActive(false);
					changingOptionsPanel.removeAll();
					changingOptionsPanel.add(startLocationButton);
					changingOptionsPanel.add(endLocationButton);
					changingOptionsPanel.add(traceButton);
					changingOptionsPanel.add(clearTraceButton);
					changingOptionsPanel.add(setPlayerImageLabel);
					changingOptionsPanel.add(playerImageBox);
					changingOptionsPanel.add(setPlayerImageButton);
					changingOptionsPanel.repaint();

					frame.requestFocusInWindow();
				} else if (drawingRadioButton.isSelected()) {
					((MazePanel) mazePanel).setDecorationGridActive(false);
					changingOptionsPanel.removeAll();

					freeDrawButton.setBounds(10, 5, 154, 20);
					changingOptionsPanel.add(freeDrawButton);
					horizontalLineButton.setBounds(10, 26, 154, 20);
					changingOptionsPanel.add(horizontalLineButton);
					verticalLineButton.setBounds(10, 47, 154, 20);
					changingOptionsPanel.add(verticalLineButton);
					rectangleButton.setBounds(10, 68, 154, 20);
					changingOptionsPanel.add(rectangleButton);
					lblLineColor.setBounds(10, 89, 100, 13);
					lblLineColor.setFont(labelFont);
					changingOptionsPanel.add(lblLineColor);
					colorComboBox.setBounds(10, 103, 114, 21);
					changingOptionsPanel.add(colorComboBox);
					currentColorLabel.setBounds(129, 103, 35, 20);
					currentColorLabel.setFont(labelFont);
					changingOptionsPanel.add(currentColorLabel);
					lineThicknessLabel.setBounds(10, 124, 154, 13);
					lineThicknessLabel.setFont(labelFont);
					changingOptionsPanel.add(lineThicknessLabel);
					lineThicknessSlider.setBounds(10, 138, 154, 54);
					changingOptionsPanel.add(lineThicknessSlider);
					eraserButton.setBounds(10, 193, 154, 21);
					changingOptionsPanel.add(eraserButton);
					eraserThicknessLabel.setBounds(10, 215, 100, 13);
					eraserThicknessLabel.setFont(labelFont);
					changingOptionsPanel.add(eraserThicknessLabel);
					eraserThicknessSlider.setBounds(10, 229, 154, 54);
					changingOptionsPanel.add(eraserThicknessSlider);
					undoButton.setBounds(10, 284, 154, 20);
					changingOptionsPanel.add(undoButton);
					redoLineButton.setBounds(10, 305, 154, 20);
					changingOptionsPanel.add(redoLineButton);

					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();
					frame.requestFocusInWindow();
				} else if (decorationsRadioButton.isSelected()) {
					changingOptionsPanel.removeAll();

					typeOfDecorationLabel.setBounds(10, 10, 151, 13);
					typeOfDecorationLabel.setFont(labelFont);
					changingOptionsPanel.add(typeOfDecorationLabel);
					typeOfDecorationComboBox.setBounds(10, 24, 151, 21);
					changingOptionsPanel.add(typeOfDecorationComboBox);
					specificDecorations.setBounds(10, 46, 151, 21);
					changingOptionsPanel.add(specificDecorations);
					addDecorationButton.setBounds(10, 68, 151, 21);
					changingOptionsPanel.add(addDecorationButton);
					deleteDecorationButton.setBounds(10, 90, 151, 21);
					changingOptionsPanel.add(deleteDecorationButton);
					smallDecorationGridModeButton.setBounds(10, 112, 151, 21);
					changingOptionsPanel.add(smallDecorationGridModeButton);
					mediumDecorationGridModeButton.setBounds(10, 134, 151, 21);
					changingOptionsPanel.add(mediumDecorationGridModeButton);
					largeDecorationGridModeButton.setBounds(10, 156, 151, 21);
					changingOptionsPanel.add(largeDecorationGridModeButton);
					gridModeOff.setBounds(10, 178, 151, 21);
					changingOptionsPanel.add(gridModeOff);
					gridModeOff.setSelected(true);
					changingOptionsPanel.add(scrollPane);
					
					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();

					frame.requestFocusInWindow();
				} else if (backgroundRadioButton.isSelected()) {
					((MazePanel) mazePanel).setDecorationGridActive(false);
					changingOptionsPanel.removeAll();

					backgroundColorLabel.setBounds(5, 10, 169, 13);
					backgroundColorLabel.setFont(labelFont);
					changingOptionsPanel.add(backgroundColorLabel);
					backgroundColorComboBox.setBounds(5, 28, 85, 21);
					changingOptionsPanel.add(backgroundColorComboBox);
					setBackgroundColorButton.setBounds(95, 28, 63, 21);
					changingOptionsPanel.add(setBackgroundColorButton);

					changingOptionsPanel.revalidate();
					changingOptionsPanel.repaint();
					frame.requestFocusInWindow();
				}
				ButtonFunctions.removeBordersFromPortals(mazePanel);
				if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
					portalControlMenu.setVisible(false);
				}
				if(enemyControlsExists == true) {
					enemyControls.setVisible(false);
				}
			}
		};
		startAndStopRadioButton.addActionListener(optionsRadioButtonListener);
		drawingRadioButton.addActionListener(optionsRadioButtonListener);
		decorationsRadioButton.addActionListener(optionsRadioButtonListener);
		backgroundRadioButton.addActionListener(optionsRadioButtonListener);
		
		/*
		 * ActionListeners for Buttons not in the changingOptionsPanel.
		 */

		stopMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				SetMazePanelSettingsForStopMaze(mazePanel);
				EnableButtonsForStopMaze(mazeMenu, generateMazeMenu, winMessageMenu, resourcesMenu, startLocationButton, musicAcknowledgment, portalMenu, endLocationButton,
						freeDrawButton, startMazeButton, playerImageBox, setPlayerImageButton,eraserButton, horizontalLineButton, verticalLineButton,
						clearMazeButton, colorComboBox, enemiesButton, setBackgroundColorButton,
						backgroundColorComboBox, addDecorationButton, deleteDecorationButton, undoButton,
						redoLineButton, blackoutRadioButton, traceButton, startAndStopRadioButton, drawingRadioButton,
						decorationsRadioButton, backgroundRadioButton, rectangleButton, clearTraceButton,
						typeOfDecorationComboBox,  largeDecorationGridModeButton, mediumDecorationGridModeButton,
						smallDecorationGridModeButton, gridModeOff);
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
										if (loadedMazeFileLocation == null) {
											RemoveBordersFromMazeComponents();
											SaveMazeAs();
										} else {
											RemoveBordersFromMazeComponents();
											SaveLoadedMaze();
											JOptionPane.showMessageDialog(null, "Maze Saved","", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
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
				BlackoutMaze.StopBlackout(mazePanel);
				ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
				while (iterator.hasNext()) {
					iterator.next().getDecorationNode().setVisible(true);
				}
				LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
				for(int i = 0; i < portalList.size(); i++) {
					PortalNode currentPortal = portalList.get(i);
					if(currentPortal.getLinkStart() != null) {
						currentPortal.getLinkStart().setVisible(true);
					}
					if(currentPortal.getLinkEnd() != null) {
						currentPortal.getLinkEnd().setVisible(true);
					}
					
				}
				mazePanel.repaint();
				frame.requestFocusInWindow();
				pressedKeys.clear();
			}

		});

		startMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				((MazePanel) mazePanel).setDecorationGridActive(false);
				RemoveBordersFromMazeComponents();
				ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
				if (CheckForStartAndEndLocations() == true) {
					SetMazePanelSettingsForStartOfMaze(mazePanel);
					mazePanel.repaint();
					DisableButtonsForStartMaze(mazeMenu, generateMazeMenu, winMessageMenu, resourcesMenu, portalMenu, musicAcknowledgment, startLocationButton, endLocationButton,
							freeDrawButton, startMazeButton, eraserButton, horizontalLineButton, verticalLineButton,
							clearMazeButton, playerImageBox, setPlayerImageButton, colorComboBox, enemiesButton, setBackgroundColorButton,
							backgroundColorComboBox, addDecorationButton, deleteDecorationButton, undoButton,
							redoLineButton, blackoutRadioButton, traceButton, startAndStopRadioButton,
							drawingRadioButton, decorationsRadioButton, backgroundRadioButton, rectangleButton,
							clearTraceButton, typeOfDecorationComboBox, largeDecorationGridModeButton, mediumDecorationGridModeButton,
							smallDecorationGridModeButton, gridModeOff);
					stopMazeButton.setEnabled(true);
					KeyListener[] frameListener = frame.getKeyListeners();
					for (int i = 0; i < frameListener.length; i++) {
						frame.removeKeyListener(frameListener[i]);
					}
					if (enemyControlsExists == true) {
						EnemyControls.SetDisposed();
					}
					if( portalControlMenu != null && portalControlMenu.isVisible() == true) {
						portalControlMenu.setVisible(false);
					}
					EnemyStartAndStopMazeFunctions.StartEnemies();
					if (blackoutRadioButton.isSelected()) {
						BlackoutMaze.InitiateBlackout(player, mazePanel);
						if (MainMenuGUI.isLevelMazeActive() == true) {
							LevelMenu.disposeActiveMaze();
						}
					}
					if(winMessageEditor != null) {
						winMessageEditor.setVisible(false);
						winMessageEditor.getWinMessageDisplayArea().setText(ButtonFunctions.getWinMessage().getMessage());
					}
					if(generateMazeWindow != null) {
						generateMazeWindow.setVisible(false);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Maze can only start with designated start and end locations!");
				}
			}
		});
		startMazeButton.addActionListener(setSelectedListener);
		
		clearMazeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to clear the maze?","", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon) == JOptionPane.YES_OPTION) {
					selected = 0;
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
						if(enemyControls.isVisible() == true) {
							EnemyControls.SetDisposed();
						}
					}
					
					RemoveDecorationsFromMazeAndList();
					LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
					for(int i = 0; i < portalList.size(); i++) {
						PortalNode currentNode = portalList.get(i);
						if(currentNode.getLinkStart() != null) {
							mazePanel.remove(currentNode.getLinkStart());
						}
						if(currentNode.getLinkEnd() != null) {
							mazePanel.remove(currentNode.getLinkEnd());
						}
					}
					ButtonFunctions.getPortalNodeList().clear();
					if(portalControlMenu != null) {
						portalControlMenu.LoadPortalsToPortalControlPanel();
						portalControlMenu.resetPortalControls();
						portalControlMenu.setVisible(false);
					}
					mazePanel.setBackground(Color.white);
					if (BackgroundMusic.isPaused() == false) {
						BackgroundMusic.PlayCorrectAudioContinuously(Color.white);
					}
					
					loadedMazeFileLocation = null;
					UndoStructure.clearUndoStack();
					RedoErasedLineStructure.ClearRedoStack();
					ButtonFunctions.GetTracemap().clear();
					SetAllButtonForegroundBlack(startLocationButton,
							freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton,
							addDecorationButton, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
					mazePanel.repaint();
					if(winMessageEditor != null) {
						winMessageEditor.getWinMessageDisplayArea().setText("You Win!");
					}
					ButtonFunctions.setWinMessage("You Win!");
					changesMade = false;
					if(generateMazeWindow != null) {
						generateMazeWindow.setCanGenerateMaze(true);
					}
				}
			}

		});

		enemiesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (enemyControls == null) {
					enemyControls = new EnemyControls();
				}
				enemyControls.setVisible(true);
				enemyControlsExists = true;
				selected = 7;
				SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(enemiesButton,
						verticalLineButton, startLocationButton, endLocationButton, freeDrawButton, eraserButton,
						horizontalLineButton, addDecorationButton, deleteDecorationButton, traceButton,
						rectangleButton);
			}

		});

		playMusicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BackgroundMusic.PlayCorrectAudioContinuously(mazePanel.getBackground());
				BackgroundMusic.setPaused(false);
			}

		});

		pauseMusicButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				BackgroundMusic.Pause();
				BackgroundMusic.setPaused(true);
			}
		});
		
		backToMainMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (changesMade == true) {
					int playerChoice = JOptionPane.showConfirmDialog(null, "Exit without saving?","", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
					if (playerChoice == JOptionPane.YES_OPTION) {
						frame.setVisible(false);
						BackgroundMusic.PlayCorrectAudioContinuously(Color.black);
						if(enemyControls != null) {
							enemyControls.setVisible(false);
						}
						if(portalControlMenu != null) {
							portalControlMenu.setVisible(false);
						}
						frmMazeKraze.setVisible(true);
					} else {
						//Nothing Happens.
					}
				} else {
					frame.setVisible(false);
					BackgroundMusic.PlayCorrectAudioContinuously(Color.black);
					if(enemyControls != null) {
						enemyControls.setVisible(false);
					}
					if(portalControlMenu != null) {
						portalControlMenu.setVisible(false);
					}
					frmMazeKraze.setVisible(true);
				}
			}
		});
		
		/*
		 * Frame: setting visible, and requesting focus so that it will get the ability to recognize key strokes for easy saving,
		 * 		  undoing, and redoing erased lines.
		 */
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
									RemoveBordersFromMazeComponents();
									SaveMazeAs();
								} else {
									RemoveBordersFromMazeComponents();
									SaveLoadedMaze();
									JOptionPane.showMessageDialog(null, "Maze Saved", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
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
		
		/*
		 * Image Icon that is used for the menu items.
		 */
		BufferedImage optionImage = null;
		try {
			optionImage = ImageIO.read(getClass().getResource("/images/logo.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Image optionImg = optionImage.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		optionImageIcon = new ImageIcon(optionImg);
		ButtonFunctions.setOptionImageIcon(optionImageIcon);
		EnemyButtonFunctions.setOptionImageIcon(optionImageIcon);
		/*
		 * Playing the correct music when you first open Maze Design.
		 */
		int playerChoice = JOptionPane.showConfirmDialog(frmMazeKraze, "Would you like the music to start?", "Question Choice", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
		if(playerChoice == JOptionPane.YES_OPTION) {
			BackgroundMusic.PlayCorrectAudioContinuously(Color.white);
		}
		else {
			BackgroundMusic.Pause();
			BackgroundMusic.setPaused(true);
		}
		frame.setVisible(true);
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
		player.setWon(false);
		mazeStarted = true;
	}

	private void ChangeStartLabelToPlayerLabel(JPanel mazePanel) {
		JLabel startLocation = ButtonFunctions.GetStartLabel();
		startLocation.setVisible(false);
		JLabel playerLabel = new JLabel("");
		playerLabel.setBounds(startLocation.getBounds());
		player = new PlayerObject(playerLabel);
		mazePanel.add(player.getPlayer());
		player.getPlayer().setVisible(true);
		images.set_image_to_label(player.getPlayer(), playerImageLocation);
	}

	/**
	 * Disabling the buttons so a player can't make changes while the maze is
	 * playing.
	 */

	private void DisableButtonsForStartMaze(JMenu mazeMenu, JMenu generateMazeMenu, JMenu winMessageMenu, JMenu resourcesMenu, JMenu portalMenu, JMenu musicAcknowledgment, 
			JButton startLocationButton,JButton endLocationButton, JButton freeDrawButton, JButton startMazeButton, JButton eraserButton,
			JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton, JComboBox<String> playerImageComboBox, JButton setPlayerImageButton,
			JComboBox<String> colorComboBox, JButton enemiesButton, JButton setBackgroundButton,
			JComboBox<String> backgroundColorComboBox, JButton addDecorationButton, JButton deleteDecorationButton,
			JButton undoButton, JButton redoButton, JRadioButton blackoutMazeRadioButton, JButton traceButton,
			JRadioButton startAndStopRadioButton, JRadioButton backgroundRadioButton,
			JRadioButton drawingWallsRadioButton, JRadioButton decorationsRadioButton, JButton rectangleButton,
			JButton clearTraceButton, JComboBox<String> typeOfDecorationComboBox, JRadioButton largeGridMode, JRadioButton mediumGridMode,
			JRadioButton smallGridMode, JRadioButton gridModeOff) {
		
		mazeMenu.setEnabled(false);
		generateMazeMenu.setEnabled(false);
		winMessageMenu.setEnabled(false);
		resourcesMenu.setEnabled(false);
		portalMenu.setEnabled(false);
		musicAcknowledgment.setEnabled(false);
		startLocationButton.setEnabled(false);
		endLocationButton.setEnabled(false);
		freeDrawButton.setEnabled(false);
		startMazeButton.setEnabled(false);
		this.lineThicknessSlider.setEnabled(false);
		this.eraserThicknessSlider.setEnabled(false);
		eraserButton.setEnabled(false);
		horizontalLineButton.setEnabled(false);
		verticalLineButton.setEnabled(false);
		clearMazeButton.setEnabled(false);
		playerImageComboBox.setEnabled(false);
		setPlayerImageButton.setEnabled(false);
		colorComboBox.setEnabled(false);
		enemiesButton.setEnabled(false);
		setBackgroundButton.setEnabled(false);
		backgroundColorComboBox.setEnabled(false);
		addDecorationButton.setEnabled(false);
		deleteDecorationButton.setEnabled(false);
		undoButton.setEnabled(false);
		redoButton.setEnabled(false);
		blackoutMazeRadioButton.setEnabled(false);
		traceButton.setEnabled(false);
		startAndStopRadioButton.setEnabled(false);
		backgroundRadioButton.setEnabled(false);
		drawingWallsRadioButton.setEnabled(false);
		decorationsRadioButton.setEnabled(false);
		rectangleButton.setEnabled(false);
		clearTraceButton.setEnabled(false);
		typeOfDecorationComboBox.setEnabled(false);
		specificDecorations.setEnabled(false);
		largeGridMode.setEnabled(false);
		mediumGridMode.setEnabled(false);
		smallGridMode.setEnabled(false);
		gridModeOff.setEnabled(false);
	}

	/**
	 * Enabling the buttons so a player can make changes to the maze after it is
	 * done being played.
	 */
	private void EnableButtonsForStopMaze(JMenu mazeMenu, JMenu generateMazeMenu, JMenu winMessageMenu, JMenu resourcesMenu, JButton startLocationButton, JMenu portalMenu, JMenu musicAcknowledgment,
			JButton endLocationButton, JButton freeDrawButton, JButton startMazeButton, JComboBox<String> playerImageComboBox, JButton setPlayerImageButton, JButton eraserButton,
			JButton horizontalLineButton, JButton verticalLineButton, JButton clearMazeButton,
			JComboBox<String> colorComboBox, JButton enemiesButton, JButton setBackgroundButton,
			JComboBox<String> backgroundColorComboBox, JButton addDecorationButton, JButton deleteDecorationButton,
			JButton undoButton, JButton redoButton, JRadioButton blackoutMazeRadioButton, JButton traceButton,
			JRadioButton startAndStopRadioButton, JRadioButton backgroundRadioButton,
			JRadioButton drawingWallsRadioButton, JRadioButton decorationsRadioButton, JButton rectangleButton,
			JButton clearTraceButton, JComboBox<String> typeOfDecorationComboBox, JRadioButton largeGridMode, JRadioButton mediumGridMode,
			JRadioButton smallGridMode, JRadioButton gridModeOff) {

		mazeMenu.setEnabled(true);
		generateMazeMenu.setEnabled(true);
		winMessageMenu.setEnabled(true);
		resourcesMenu.setEnabled(true);
		portalMenu.setEnabled(true);
		musicAcknowledgment.setEnabled(true);
		startLocationButton.setEnabled(true);
		endLocationButton.setEnabled(true);
		freeDrawButton.setEnabled(true);
		startMazeButton.setEnabled(true);
		playerImageComboBox.setEnabled(true);
		setPlayerImageButton.setEnabled(true);
		this.lineThicknessSlider.setEnabled(true);
		this.eraserThicknessSlider.setEnabled(true);
		eraserButton.setEnabled(true);
		horizontalLineButton.setEnabled(true);
		verticalLineButton.setEnabled(true);
		clearMazeButton.setEnabled(true);
		colorComboBox.setEnabled(true);
		enemiesButton.setEnabled(true);
		setBackgroundButton.setEnabled(true);
		backgroundColorComboBox.setEnabled(true);
		addDecorationButton.setEnabled(true);
		deleteDecorationButton.setEnabled(true);
		undoButton.setEnabled(true);
		redoButton.setEnabled(true);
		blackoutMazeRadioButton.setEnabled(true);
		traceButton.setEnabled(true);
		startAndStopRadioButton.setEnabled(true);
		backgroundRadioButton.setEnabled(true);
		drawingWallsRadioButton.setEnabled(true);
		decorationsRadioButton.setEnabled(true);
		rectangleButton.setEnabled(true);
		clearTraceButton.setEnabled(true);
		typeOfDecorationComboBox.setEnabled(true);
		specificDecorations.setEnabled(true);
		largeGridMode.setEnabled(true);
		mediumGridMode.setEnabled(true);
		smallGridMode.setEnabled(true);
		gridModeOff.setEnabled(true);
	}

	/**
	 * Setting the buttons foreground color to show which one is selected by the
	 * user.
	 */
	private void SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(JButton selectedButton,
			JButton button1, JButton button2, JButton button3, JButton button4, JButton button5, JButton button6,
			JButton button7, JButton button8, JButton enemiesButton, JButton button10) {
		selectedButton.setForeground(Color.red);
		button1.setForeground(Color.black);
		button2.setForeground(Color.black);
		button3.setForeground(Color.black);
		button4.setForeground(Color.black);
		button5.setForeground(Color.black);
		button6.setForeground(Color.black);
		button7.setForeground(Color.black);
		button8.setForeground(Color.black);
		enemiesButton.setForeground(Color.black);
		button10.setForeground(Color.black);
		if (selected != deleteDecorationSelected) {
			ButtonFunctions.RemoveBordersFromDecorations(decorationList, mazePanel);
			ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
		}
		ButtonFunctions.removeBordersFromPortals(mazePanel);
		if(portalControlMenu != null && portalControlMenu.isVisible() == true) {
			portalControlMenu.setVisible(false);
		}
		if(selected != enemiesSelected) {
			if(enemyControlsExists == true) {
				enemyControls.setVisible(false);
			}
		}
	}
	
	private void SetAllButtonForegroundBlack(JButton button0,
			JButton button1, JButton button2, JButton button3, JButton button4, JButton button5, JButton button6,
			JButton button7, JButton button8, JButton enemiesButton, JButton button10) {
		button0.setForeground(Color.black);
		button1.setForeground(Color.black);
		button2.setForeground(Color.black);
		button3.setForeground(Color.black);
		button4.setForeground(Color.black);
		button5.setForeground(Color.black);
		button6.setForeground(Color.black);
		button7.setForeground(Color.black);
		button8.setForeground(Color.black);
		enemiesButton.setForeground(Color.black);
		button10.setForeground(Color.black);
		ButtonFunctions.RemoveBordersFromDecorations(decorationList, mazePanel);
		ButtonFunctions.RemoveMouseListenersFromDecorations(decorationList, mazePanel);
	}

	/**
	 * Setting the mazePanel for editing the maze again. 
	 * 1.) removes player from the maze. 
	 * 2.) Sets the start Location visible again. 
	 * 3.) Removes the key listener so the user can't do movements. 
	 * 4.) repaints the panel to make sure changes were made.
	 */
	private void SetMazePanelSettingsForStopMaze(JPanel mazePanel) {
		mazePanel.remove(player.getPlayer());
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
			ButtonFunctions.StartLocationFunction(mazePanel, (String) playerImageBox.getSelectedItem());
			changesMade = true;
		} else if (selected == endLocationSelected) {
			JPanel mazePanel = (JPanel) arg0.getComponent();
			ButtonFunctions.EndLocationFunction(mazePanel);
			changesMade = true;
		} else if (selected == enemiesSelected) {
			int enemySelected = EnemyControls.GetEnemySelected();
			int enemyControl = EnemyButtonFunctions.GetEnemyControl();
			if (enemySelected == 1 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy1StartLocation();
				changesMade = true;
			} else if (enemySelected == 2 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy2StartLocation();
				changesMade = true;
			} else if (enemySelected == 3 && enemyControl == 1) {
				EnemyButtonFunctions.PlayerSetEnemy3StartLocation();
				changesMade = true;
			} else if (enemySelected == 1 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy1EndLocation();
				changesMade = true;
			} else if (enemySelected == 2 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy2EndLocation();
				changesMade = true;
			} else if (enemySelected == 3 && (enemyControl == 2 || enemyControl == 3)) {
				EnemyButtonFunctions.SetEnemy3EndLocation();
				changesMade = true;
			}
			if (mazeStarted == false) {
				enemyControls.setVisible(true);
			}
		} else if (selected == traceSelected) {
			JPanel mazePanel = (JPanel) arg0.getComponent();
			ButtonFunctions.DrawTraceMap(mazePanel, mazePanel.getMousePosition());
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		JPanel mazePanel = (JPanel) arg0.getComponent();
		int lineThickness = lineThicknessSlider.getValue();
		if (selected == freeDrawLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
			changesMade = true;
		} else if (selected == eraserSelected) {
			ButtonFunctions.EraseCoordinate(mazePanel, eraserThicknessSlider.getValue());
			changesMade = true;
		} else if (selected == horizontalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetHorizontialLineY((int) mouseCoordinate.getY());
			ButtonFunctions.SetHorizontalLineX((int) mouseCoordinate.getX());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
			changesMade = true;
		} else if (selected == verticalLineSelected) {
			ButtonFunctions.DrawLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			Point mouseCoordinate = new Point(mazePanel.getMousePosition());
			ButtonFunctions.SetVerticalLineX((int) mouseCoordinate.getX());
			ButtonFunctions.SetVerticalLineY((int) mouseCoordinate.getY());
			UndoStructure.AddPointToNewLineArrayList(mazePanel.getMousePosition());
			changesMade = true;
		} else if (selected == deleteDecorationSelected) {
			((MazePanel) mazePanel).SetDeleteDecorationBoxStart(mazePanel.getMousePosition());
		} else if (selected == rectangleSelected) {
			((MazePanel) mazePanel).SetRectangleStart(mazePanel.getMousePosition());
			((MazePanel) mazePanel).SetRectangleColor(GetColorFromColorComboBox());
		} else if (selected == circleSelected) {
			((MazePanel) mazePanel).setCircleStart(mazePanel.getMousePosition());
			((MazePanel) mazePanel).setCircleColor(GetColorFromColorComboBox());
		} else if (selected == addDecorationSelected) {
			if (((MazePanel) mazePanel).getDecorationGridActive() == true) {
				Point coordinates = mazePanel.getMousePosition();
				Point leftCorner = ButtonFunctions.GetCorrectLeftCornerValue(coordinates);
				if (leftCorner != null) {
					JLabel newDecoration = new JLabel("");
					String[] newDecorationAttributes = getNewDecorationAttributes(
							(String) specificDecorations.getSelectedItem());
					int height = Integer.parseInt(newDecorationAttributes[0]);
					int width = Integer.parseInt(newDecorationAttributes[1]);
					String imageURL = "/images/" + newDecorationAttributes[2];
					newDecoration.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), width, height);
					mazePanel.add(newDecoration);
					images.set_image_to_label(newDecoration, imageURL);
					DecorationNode decoration = new DecorationNode(newDecoration);
					decorationList.add(decoration);
					changesMade = true;
				}
			} else {
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
				changesMade = true;
			}
		}
		else if(selected == startPortalSelected) {
			Point mousePosition = mazePanel.getMousePosition();
			int z = 15;
			if(currentPortal.getLinkStart() == null) {
				JLabel newStartPortal = new JLabel("");
				newStartPortal.setBounds((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
				Rectangle newStartPortalBounds = newStartPortal.getBounds();
				if(ButtonFunctions.CheckStartPortalLocationLegal(newStartPortalBounds, currentPortal) == true) {
					images.set_image_to_label(newStartPortal, "/images/Storm Gray/portal.png");
					currentPortal.setLinkStart(newStartPortal);
					mazePanel.add(currentPortal.getLinkStart());
					mazePanel.repaint();
					changesMade = true;
				}
				else {
					// Nothing.
				}
			}
			else {
				int playerChoice = JOptionPane.showConfirmDialog(mazePanel, "Are you sure you want to change your protal start?", "Portal Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
				if(playerChoice == JOptionPane.YES_OPTION) {
					Rectangle newStartPortalBounds = new Rectangle((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
					if(ButtonFunctions.CheckStartPortalLocationLegal(newStartPortalBounds, currentPortal) == true) {
						currentPortal.getLinkStart().setBounds((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
						mazePanel.repaint();
						changesMade = true;
					}
					else {
						//Nothing.
					}
				}
			}
			portalControlMenu.setVisible(true);
		}
		else if(selected == endPortalSelected) {
			Point mousePosition = mazePanel.getMousePosition();
			int z = 15;
			if(currentPortal.getLinkEnd() == null) {
				JLabel newEndPortal = new JLabel("");
				newEndPortal.setBounds((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
				Rectangle newEndPortalBounds = newEndPortal.getBounds();
				if(ButtonFunctions.CheckEndPortalLocationLegal(newEndPortalBounds, currentPortal) == true) {
					images.set_image_to_label(newEndPortal, "/images/Storm Gray/portal.png");
					currentPortal.setLinkEnd(newEndPortal);
					mazePanel.add(currentPortal.getLinkEnd());
					mazePanel.repaint();
					changesMade = true;
				}
				else {
					// Nothing.
				}
			}
			else {
				int playerChoice = JOptionPane.showConfirmDialog(mazePanel, "Are you sure you want to change your protal end?", "Portal Option", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, optionImageIcon);
				if(playerChoice == JOptionPane.YES_OPTION) {
					Rectangle endPortalBounds = new Rectangle((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
					if(ButtonFunctions.CheckEndPortalLocationLegal(endPortalBounds, currentPortal) == true) {
						currentPortal.getLinkEnd().setBounds((int) mousePosition.getX()-z,(int) mousePosition.getY()-z , 30, 30);
						mazePanel.repaint();
						changesMade = true;
					}
					else {
						// Nothing.
					}
				}
			}
			portalControlMenu.setVisible(true);
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
			changesMade = true;
		} else if (selected == eraserSelected) {
			((MazePanel) mazePanel).SetEraserCoordinates(mazePanel.getMousePosition());
			ButtonFunctions.EraseCoordinate(mazePanel, eraserThicknessSlider.getValue());
			mazePanel.repaint();
			changesMade = true;
		} else if (selected == horizontalLineSelected) {
			ButtonFunctions.DrawHorizontalLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			mazePanel.repaint();
			changesMade = true;
		} else if (selected == verticalLineSelected) {
			ButtonFunctions.DrawVerticalLineFunction(mazePanel, lineThickness, GetColorFromColorComboBox());
			((MazePanel) mazePanel).SetDrawCoordinates(mazePanel.getMousePosition());
			mazePanel.repaint();
			changesMade = true;
		} else if (selected == traceSelected) {
			ButtonFunctions.DrawTraceMap(mazePanel, mazePanel.getMousePosition());
		} else if (selected == deleteDecorationSelected) {
			((MazePanel) mazePanel).SetDeleteDecorationBoxEnd(mazePanel.getMousePosition());
			((MazePanel) mazePanel).SetDeleteDecorationBoxActive(true);
			((MazePanel) mazePanel).repaint();
			Area deleteArea = ((MazePanel) mazePanel).getDeleteDecorationBoxArea();
			ButtonFunctions.CheckAreaOfDeleteBoxWithAreaOfDecorationsAndChangeBorderIfIntersecting(deleteArea);
			changesMade = true;
		} else if (selected == rectangleSelected) {
			((MazePanel) mazePanel).SetRectangleEnd(mazePanel.getMousePosition());
			((MazePanel) mazePanel).SetRectangleSize(lineThicknessSlider.getValue());
			((MazePanel) mazePanel).SetRectangleActive(true);
			changesMade = true;
			mazePanel.repaint();
		} else if (selected == circleSelected) {
			((MazePanel) mazePanel).setCircleEnd(mazePanel.getMousePosition());
			((MazePanel) mazePanel).setCircleSize(lineThicknessSlider.getValue());
			((MazePanel) mazePanel).setCircleActive(true);
			mazePanel.repaint();
		} else if (selected == addDecorationSelected) {
			if (((MazePanel) mazePanel).getDecorationGridActive() == true) {
				Point coordinates = mazePanel.getMousePosition();
				Point leftCorner = ButtonFunctions.GetCorrectLeftCornerValue(coordinates);
				if (leftCorner != null) {
					ButtonFunctions.DeleteDecorationToReplace(leftCorner);
					JLabel newDecoration = new JLabel("");
					String[] newDecorationAttributes = getNewDecorationAttributes(
							(String) specificDecorations.getSelectedItem());
					int height = Integer.parseInt(newDecorationAttributes[0]);
					int width = Integer.parseInt(newDecorationAttributes[1]);
					String imageURL = "/images/" + newDecorationAttributes[2];
					newDecoration.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), width, height);
					mazePanel.add(newDecoration);
					images.set_image_to_label(newDecoration, imageURL);
					DecorationNode decoration = new DecorationNode(newDecoration);
					decorationList.add(decoration);
					changesMade = true;
				}
			}
		}
		// ADD COORDINATES TO xAndyDisplayCoordinates
		SetCoordinatesToXAndYDisplayCoordinates(mazePanel);
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (selected == freeDrawLineSelected) {
			UndoStructure.AddLineUndoStack();
			changesMade = true;
		} else if (selected == horizontalLineSelected) {
			UndoStructure.AddLineUndoStack();
			changesMade = true;
		} else if (selected == verticalLineSelected) {
			UndoStructure.AddLineUndoStack();
			changesMade = true;
		} else if (selected == eraserSelected) {
			RedoErasedLineStructure.AddLineToRedoErasedLineStack();
			changesMade = true;
		} else if (selected == deleteDecorationSelected) {
			((MazePanel) arg0.getComponent()).SetDeleteDecorationBoxActive(false);
			ButtonFunctions.RemoveDecorationsThatAreInAreaOfDeletion();
			((MazePanel) arg0.getComponent()).repaint();
		} else if (selected == rectangleSelected) {
			if (((MazePanel) arg0.getComponent()).GetRectangleActive() == true) {
				changesMade = true;
				ButtonFunctions.AddRectangleToCoordinateMaps(((MazePanel) arg0.getComponent()).GetRectangleMap(),
						GetColorFromColorComboBox());
				UndoStructure.AddLineUndoStack();
			}
			((MazePanel) arg0.getComponent()).SetRectangleActive(false);
			arg0.getComponent().repaint();
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
					player.MoveUp();
					break;
				case KeyEvent.VK_DOWN:
					player.MoveDown();
					break;
				case KeyEvent.VK_LEFT:
					player.MoveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					player.MoveRight();
					break;
				case KeyEvent.VK_W:
					player.MoveUp();
					break;
				case KeyEvent.VK_S:
					player.MoveDown();
					break;
				case KeyEvent.VK_A:
					player.MoveLeft();
					break;
				case KeyEvent.VK_D:
					player.MoveRight();
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
		 else if (selected == freeDrawLineSelected) {
				UndoStructure.AddLineUndoStack();
				changesMade = true;
			} else if (selected == horizontalLineSelected) {
				UndoStructure.AddLineUndoStack();
				changesMade = true;
			} else if (selected == verticalLineSelected) {
				UndoStructure.AddLineUndoStack();
				changesMade = true;
			} else if (selected == deleteDecorationSelected) {
				((MazePanel) mazePanel).SetDeleteDecorationBoxActive(false);
				((MazePanel) mazePanel).repaint();
			}
			else if (selected == startPortalSelected) {
				HoverImage.RemoveLabelFromMazePanel(mazePanel);
			} else if (selected == endPortalSelected) {
				HoverImage.RemoveLabelFromMazePanel(mazePanel);
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
			String playerType = (String) playerImageBox.getSelectedItem();
			HoverImage.AddLabelToMazePanel(mazePanel, playerType);
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
		else if(selected == startPortalSelected) {
			HoverImage.AddLabelToMazePanel(mazePanel,30, 30, "/images/Storm Gray/portal.png");
		}
		else if(selected == endPortalSelected) {
			HoverImage.AddLabelToMazePanel(mazePanel,30, 30, "/images/Storm Gray/portal.png");
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
		else if(selected == startPortalSelected) {
			HoverImage.MoveLabelInMazePanel(mazePanel);
		}
		else if(selected == endPortalSelected) {
			HoverImage.MoveLabelInMazePanel(mazePanel);
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

	/*
	 * Setting the original decoration list for when the page first loads.
	 */
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
	
	/*
	 * Setting the decorations to the imagePanel in the changingOptionsPanel then adds a mouse listener to allow for users
	 * to click the images to add them. 
	 */
	private void SetDecorationsToImagePanel(JPanel imagePanel, JButton addDecorationButton, JButton startLocationButton, JButton freeDrawButton,
			JButton endLocationButton, JButton eraserButton, JButton verticalLineButton, JButton horizontalLineButton, 
			JButton deleteDecorationButton, JButton traceButton, JButton enemiesButton, JButton rectangleButton) {
		imagePanel.removeAll();
		GetFileAttributes attributeGetter = new GetFileAttributes();
		for(int i = 0; i < specificDecorations.getItemCount(); i++) {
			attributeGetter.GetAttributes(specificDecorations.getItemAt(i));
			JLabel imageLabel = new JLabel("");
			imageLabel.setBounds(0,0,deocrationWidth,decorationHeight);
			images.set_image_to_label(imageLabel, "/images/" + decorationname);
			imageLabel.setBorder(new LineBorder(Color.black, 1));
			final int currentIndex = i;
			// Mouse listener adds a border that changes color when hovered over and then sets selected to "addDecorationSelected" 
			imageLabel.addMouseListener(new MouseListener() {
				private int index = currentIndex;
				@Override
				public void mouseClicked(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
					specificDecorations.setSelectedIndex(index);
					selected = addDecorationSelected;
					SetSelectedButtonForegroundAndRemoveDecorationDeletEffectsIfNotSelected(addDecorationButton, startLocationButton,
							freeDrawButton, endLocationButton, eraserButton, verticalLineButton, horizontalLineButton
							, deleteDecorationButton, traceButton, enemiesButton, rectangleButton);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					imageLabel.setBorder(new LineBorder(Color.red, 1));
					
				}

				@Override
				public void mouseExited(MouseEvent e) {
					imageLabel.setBorder(new LineBorder(Color.black, 1));
					
				}
				
			});
			imagePanel.add(imageLabel);
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
		return player.getPlayer();
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
			changesMade = false;
		} else {
			JOptionPane.showMessageDialog(null, "Maze needs start and end spots.", "Start and End Spots Warning", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
		}
	}

	private void SaveLoadedMaze() {
		SaveLoadedFile.Save(loadedMazeFileLocation);
		changesMade = false;
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
	
	public static int getSelected() {
		return selected;
	}
	
	public static boolean mazeActive() {
		return mazeStarted;
	}

	public static void stopMaze() {
		stopMazeButton.doClick();
	}
	
	

	public static int getDecorationHeight() {
		return decorationHeight;
	}

	public static int getDeocrationWidth() {
		return deocrationWidth;
	}

	public static String getDecorationname() {
		return decorationname;
	}

	public static void setDecorationHeight(int decorationHeight) {
		MazeDesignMainGUI.decorationHeight = decorationHeight;
	}

	public static void setDeocrationWidth(int deocrationWidth) {
		MazeDesignMainGUI.deocrationWidth = deocrationWidth;
	}

	public static void setDecorationname(String decorationname) {
		MazeDesignMainGUI.decorationname = decorationname;
	}
	
	public static JPanel GetMazePanel() {
		return mazePanel;
	}

	public static JButton GetStartMazeButton() {
		return startMazeButton;
	}
	
	public static void setPlayerImageLocation(String playerImageLocation) {
		MazeDesignMainGUI.playerImageLocation = playerImageLocation; 
	}
	
	public static String getPlayerImageLocation() {
		return MazeDesignMainGUI.playerImageLocation;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	public static void ClearCurrentMaze() {
		clearMazeButton.doClick();
	}

	public static PortalNode getCurrentPortal() {
		return currentPortal;
	}

	public static void setCurrentPortal(PortalNode currentPortal) {
		MazeDesignMainGUI.currentPortal = currentPortal;
	}
}
