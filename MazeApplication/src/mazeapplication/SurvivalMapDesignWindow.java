package mazeapplication;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import java.awt.ScrollPane;
import java.awt.Font;

public class SurvivalMapDesignWindow implements MouseListener, MouseMotionListener, KeyListener{

	private JFrame survivalMapFrm;
	private static JPanel collectionMapPanel;
	private JRadioButton previousGridSizeButton = null;
	private static int selected = 0;
	private int playerStartSelected = 1;
	private int addWallSelected = 2;
	private int enemySpawnerSelected = 3;
	private int removeWallsSelected = 4;
	private int removeEnemySpawnerSelected = 5;
	private static JButton stopMapButton;
	private int addWallIndex = -1;
	private int smallSize = 20;
	private int mediumSize = 30;
	private int largeSize = 40;
	private int currentSize;
	private ArrayList<String> wallNameList = new ArrayList<String>();
	private final HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private boolean keepTimerGoing = false;

	/**
	 * Create the application.
	 */
	public SurvivalMapDesignWindow(JFrame mainMenu) {
		initialize(mainMenu);
		survivalMapFrm.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(JFrame mainMenu) {
		survivalMapFrm = new JFrame();
		survivalMapFrm.setIconImage(Toolkit.getDefaultToolkit().getImage(SurvivalMapDesignWindow.class.getResource("/images/logo.png")));
		survivalMapFrm.setTitle("Survival Map Editor");
		survivalMapFrm.setBounds(100, 100, 931, 674);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		survivalMapFrm.setLocation(dim.width / 2 - survivalMapFrm.getSize().width / 2, dim.height / 2 - survivalMapFrm.getSize().height / 2);
		survivalMapFrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		survivalMapFrm.getContentPane().setBackground(new Color(2, 48, 71));
		survivalMapFrm.getContentPane().setLayout(null);
		
		collectionMapPanel = new SurvivalMapPanel();
		collectionMapPanel.setBackground(new Color(255, 255, 255));
		collectionMapPanel.setBorder(new LineBorder(Color.black, 1));
		collectionMapPanel.setBounds(10, 31, 720, 600);
		collectionMapPanel.setBackground(Color.white);
		survivalMapFrm.getContentPane().add(collectionMapPanel);
		collectionMapPanel.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBounds(0, 0, 919, 22);
		menuBar.setBackground(new Color(120,177,199));
		survivalMapFrm.getContentPane().add(menuBar);
		
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		JMenuItem saveFileMenuItem = new JMenuItem("Save");
		fileMenu.add(saveFileMenuItem);
		saveFileMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SaveSurvivalMap.Load();
			}
		});
		
		JMenuItem saveAsFileMenuItem = new JMenuItem("Save As");
		fileMenu.add(saveAsFileMenuItem);
		
		JMenuItem loadFileMenuItem = new JMenuItem("Load");
		fileMenu.add(loadFileMenuItem);
		
		JPanel controlsPanel = new JPanel();
		controlsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		controlsPanel.setBackground(new Color(120,177,199));
		controlsPanel.setBounds(740, 31, 169, 441);
		survivalMapFrm.getContentPane().add(controlsPanel);
		controlsPanel.setLayout(null);
		
		Color buttonColor = new Color(170, 204, 0);
		
		JButton playerStartButton = new JButton("Player Start");
		playerStartButton.setBounds(2, 2, 165, 23);
		playerStartButton.setBackground(buttonColor);
		controlsPanel.add(playerStartButton);
		
		JButton addEnemySpawnPointButton = new JButton("Add Enemy Spawn");
		addEnemySpawnPointButton.setBounds(2, 52, 165, 23);
		addEnemySpawnPointButton.setBackground(buttonColor);
		controlsPanel.add(addEnemySpawnPointButton);
		
		JButton removeEnemySpawnButton = new JButton("Remove Enemy Spawn");
		removeEnemySpawnButton.setBounds(2, 77, 165, 23);
		removeEnemySpawnButton.setBackground(buttonColor);
		controlsPanel.add(removeEnemySpawnButton);
		
		JButton removeWallsButton = new JButton("Remove Walls");
		removeWallsButton.setBounds(2, 27, 165, 23);
		removeWallsButton.setBackground(buttonColor);
		controlsPanel.add(removeWallsButton);
		
		JRadioButton smallGridModeRadioButton = new JRadioButton("Small Grid");
		smallGridModeRadioButton.setBounds(2, 102, 165, 23);
		controlsPanel.add(smallGridModeRadioButton);
		
		JRadioButton mediumGridModeRadioButton = new JRadioButton("Medium Grid");
		mediumGridModeRadioButton.setBounds(2, 127, 165, 23);
		controlsPanel.add(mediumGridModeRadioButton);
		
		JRadioButton largeGridModeRadioButton = new JRadioButton("Large Grid");
		largeGridModeRadioButton.setBounds(2, 152, 165, 23);
		controlsPanel.add(largeGridModeRadioButton);
		
		ButtonGroup gridModeGroup = new ButtonGroup();
		gridModeGroup.add(smallGridModeRadioButton);
		gridModeGroup.add(mediumGridModeRadioButton);
		gridModeGroup.add(largeGridModeRadioButton);
		
		JButton clearMapButton = new JButton("Clear");
		clearMapButton.setBounds(2, 409, 165, 23);
		clearMapButton.setBackground(buttonColor);
		controlsPanel.add(clearMapButton);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		scorePanel.setBackground(new Color(120,177,199));
		scorePanel.setBounds(740, 483, 169, 145);
		survivalMapFrm.getContentPane().add(scorePanel);
		scorePanel.setLayout(null);
		
		
		JButton mainMenuButton = new JButton("Main Menu");
		mainMenuButton.setBackground(new Color(0, 0, 255));
		mainMenuButton.setForeground(Color.white);
		mainMenuButton.setBounds(10, 111, 149, 23);
		scorePanel.add(mainMenuButton);
		
		JLabel timeLabel = new JLabel("Time:");
		timeLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		timeLabel.setBounds(10, 11, 48, 29);
		scorePanel.add(timeLabel);
		
		JLabel elapsedTimeDisplay = new JLabel("0:0");
		elapsedTimeDisplay.setOpaque(true);
		elapsedTimeDisplay.setBackground(Color.WHITE);
		elapsedTimeDisplay.setBorder(new LineBorder(Color.BLACK));
		elapsedTimeDisplay.setFont(new Font("Tahoma", Font.ITALIC, 13));
		elapsedTimeDisplay.setBounds(52, 16, 88, 20);
		scorePanel.add(elapsedTimeDisplay);
		mainMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				survivalMapFrm.setVisible(false);
				mainMenu.setVisible(true);
			}
		});
		
		stopMapButton = new JButton("Stop Map");
		stopMapButton.setBounds(2, 384, 165, 23);
		stopMapButton.setBackground(buttonColor);
		controlsPanel.add(stopMapButton);
		
		JButton startMapButton = new JButton("Start Map");
		startMapButton.setBounds(2, 360, 165, 23);
		startMapButton.setBackground(buttonColor);
		controlsPanel.add(startMapButton);
		
		JButton[] controlButtons = new JButton[] {playerStartButton, addEnemySpawnPointButton, removeEnemySpawnButton,
				removeWallsButton, startMapButton, clearMapButton};
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(2, 177, 157, 60);
		controlsPanel.add(scrollPane);
		
		JPanel wallImagePanel = new JPanel();
		wallImagePanel.setBounds(scrollPane.getBounds());
		scrollPane.add(wallImagePanel);
		AddWallsToWallImagePanel(wallImagePanel);
		/*
		 * Button Actions.
		 */
		ActionListener changeGridListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(previousGridSizeButton == null) {
					if(smallGridModeRadioButton.isSelected()) {
						((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(true);
						((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
						((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
						CurrentSurvivalMapData.MakeCollectionGridMap(0, 0, collectionMapPanel.getWidth(), collectionMapPanel.getHeight(), smallSize);
						currentSize = smallSize;
						CurrentSurvivalMapData.setGridSize(1);
					}
					else if(mediumGridModeRadioButton.isSelected()) {
						((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
						((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(true);
						((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
						currentSize = mediumSize;
						CurrentSurvivalMapData.MakeCollectionGridMap(0, 0, collectionMapPanel.getWidth(), collectionMapPanel.getHeight(), mediumSize);
						CurrentSurvivalMapData.setGridSize(2);
					}
					else if(largeGridModeRadioButton.isSelected()) {
						((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
						((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
						((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(true);
						CurrentSurvivalMapData.MakeCollectionGridMap(0, 0, collectionMapPanel.getWidth(), collectionMapPanel.getHeight(), largeSize);
						currentSize = largeSize;
						CurrentSurvivalMapData.setGridSize(3);
					}
					previousGridSizeButton = (JRadioButton) e.getSource();
					EnableControlButtons(controlButtons, scrollPane);
					collectionMapPanel.repaint();
				}
				else {
					int playerChoice = JOptionPane.showConfirmDialog(null, "Performing this action will clear your current map! Do you wish to continue?");
					if(playerChoice == JOptionPane.YES_OPTION) {
						CurrentSurvivalMapData.ClearData();
						if(smallGridModeRadioButton.isSelected()) {
							((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(true);
							((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
							((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
							CurrentSurvivalMapData.MakeCollectionGridMap(0, 0,(int) collectionMapPanel.getSize().getWidth(), (int) collectionMapPanel.getSize().getHeight(), smallSize);
							currentSize = smallSize;
							CurrentSurvivalMapData.setGridSize(1);
						}
						else if(mediumGridModeRadioButton.isSelected()) {
							((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
							((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(true);
							((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
							CurrentSurvivalMapData.MakeCollectionGridMap(0, 0, collectionMapPanel.getSize().width, collectionMapPanel.getSize().height, mediumSize);
							currentSize = mediumSize;
							CurrentSurvivalMapData.setGridSize(2);
						}
						else if(largeGridModeRadioButton.isSelected()) {
							((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
							((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
							((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(true);
							CurrentSurvivalMapData.MakeCollectionGridMap(0, 0, collectionMapPanel.getSize().width, collectionMapPanel.getSize().height, largeSize);
							currentSize = largeSize;
							CurrentSurvivalMapData.setGridSize(3);
						}
						previousGridSizeButton = (JRadioButton) e.getSource();
						collectionMapPanel.repaint();
					}
					else {
						if(previousGridSizeButton != null) {
							previousGridSizeButton.setSelected(true);
						}
					}
				}
			}
		};
		
		ActionListener clearListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int playerChoice = JOptionPane.showConfirmDialog(null, "Are you sure you want to clear the map?");
				if(playerChoice == JOptionPane.YES_OPTION) {
					previousGridSizeButton = null;
					gridModeGroup.clearSelection();
					((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
					((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
					((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
					DisableControlButtons(controlButtons, scrollPane);
					CurrentSurvivalMapData.ClearData();
					selected = 0;
					collectionMapPanel.repaint();
				}
			}
		};
		clearMapButton.addActionListener(clearListener);
		
		ActionListener playerStartListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = playerStartSelected;
			}
		};
		playerStartButton.addActionListener(playerStartListener);
		
		ActionListener enemySpawnerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = enemySpawnerSelected;
			}
		};
		addEnemySpawnPointButton.addActionListener(enemySpawnerListener);
		
		ActionListener startMapListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(CurrentSurvivalMapData.getPlayerStart() != null) {
					LinkedList<SurvivalMapEnemySpawner> enemySpawnerList = CurrentSurvivalMapData.getEnemySpawnerList();
					for(int i = 0; i < enemySpawnerList.size(); i++) {
						enemySpawnerList.get(i).SpawnEnemies(true, collectionMapPanel, currentSize);
					}
					if(currentSize == smallSize) {
						((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(false);
					}
					else if(currentSize == mediumSize) {
						((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(false);
					}
					else if(currentSize == largeSize) {
						((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(false);
					}
					collectionMapPanel.repaint();
					selected = 0;
					SurvivalMapPlayer.InitializePlayerStart();
					AddKeyListener();
					collectionMapPanel.requestFocus();
					stopMapButton.setEnabled(true);
					DisableControlButtons(controlButtons, scrollPane);
					smallGridModeRadioButton.setEnabled(false);
					mediumGridModeRadioButton.setEnabled(false);
					largeGridModeRadioButton.setEnabled(false);
					mainMenuButton.setEnabled(false);
					keepTimerGoing = true;
					StartNewTimerThread(elapsedTimeDisplay);
					BackgroundMusic.PlayCorrectAudioContinuously(new Color(194,178,128));
				}
				else {
					JOptionPane.showMessageDialog(null, "Map requires a player to start!");
				}
			}
		};
		startMapButton.addActionListener(startMapListener);
		
		ActionListener stopMapListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<SurvivalMapEnemySpawner> enemySpawnerList = CurrentSurvivalMapData.getEnemySpawnerList();
				for(int i = 0; i < enemySpawnerList.size(); i++) {
					enemySpawnerList.get(i).SpawnEnemies(false, collectionMapPanel, currentSize);
				}
				if(currentSize == smallSize) {
					((SurvivalMapPanel) collectionMapPanel).setSmallGridModeActive(true);
				}
				else if(currentSize == mediumSize) {
					((SurvivalMapPanel) collectionMapPanel).setMediumGridModeActive(true);
				}
				else if(currentSize == largeSize) {
					((SurvivalMapPanel) collectionMapPanel).setLargeGridModeActive(true);
				}
				collectionMapPanel.repaint();
				RemoveKeyListener();
				SurvivalMapPlayer.ResetPlayer();
				EnableControlButtons(controlButtons, scrollPane);
				stopMapButton.setEnabled(false);
				smallGridModeRadioButton.setEnabled(true);
				mediumGridModeRadioButton.setEnabled(true);
				largeGridModeRadioButton.setEnabled(true);
				mainMenuButton.setEnabled(true);
				keepTimerGoing = false;
				pressedKeys.clear();
				BackgroundMusic.Pause();
			}
		};
		stopMapButton.addActionListener(stopMapListener);
		
		ActionListener removeWallsListener = new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				selected = removeWallsSelected;
			}
		};
		removeWallsButton.addActionListener(removeWallsListener);
		
		ActionListener removeEnemySpawnerListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selected = removeEnemySpawnerSelected;
			}
		};
		removeEnemySpawnButton.addActionListener(removeEnemySpawnerListener);
		
		smallGridModeRadioButton.addActionListener(changeGridListener);
		mediumGridModeRadioButton.addActionListener(changeGridListener);
		largeGridModeRadioButton.addActionListener(changeGridListener);
		
		collectionMapPanel.addMouseListener(this);
		collectionMapPanel.addMouseMotionListener(this);
		
		for(int i = 0 ; i < controlButtons.length; i++) {
			controlButtons[i].setEnabled(false);
		}
		stopMapButton.setEnabled(false);
		scrollPane.setEnabled(false);
		
	}
	
	private void DisableControlButtons(JButton[] buttons, ScrollPane scrollPane) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setEnabled(false);
		}
		scrollPane.setEnabled(false);
	}
	
	private void EnableControlButtons(JButton[] buttons, ScrollPane scrollPane) {
		for(int i = 0; i < buttons.length; i++) {
			buttons[i].setEnabled(true);
		}
		scrollPane.setEnabled(true);
	}
	
	private void AddWallsToWallImagePanel(JPanel wallImagePanel) {
		GetFileAttributes attributesGetter = new GetFileAttributes();
		SetImageToLabel images = new SetImageToLabel();
		InputStream resourceStream = getClass().getResourceAsStream("/collectionMapWallList/wallList");
		BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resourceStream));
		int i = 0;
		try {
			while(resourceReader.ready()) {
				String currentWallName = resourceReader.readLine();
				wallNameList.add(currentWallName);
				String filePath = "/images/" + attributesGetter.GetItemFilePath(currentWallName);
				JLabel newWallImage = new JLabel();
				newWallImage.setBounds(0,0,30,30);
				images.set_image_to_label(newWallImage, filePath);
				newWallImage.setVisible(true);
				wallImagePanel.add(newWallImage);
				final int currentIndex = i;
				MouseListener wallImageListener = new MouseListener() {
					private int index = currentIndex;
					@Override
					public void mouseClicked(MouseEvent e) {
						selected = addWallSelected;
						addWallIndex = index;
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
						((JLabel) e.getComponent()).setBorder(new LineBorder(Color.red));
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						((JLabel) e.getComponent()).setBorder(new LineBorder(Color.black));						
					}
				};
				newWallImage.setBorder(new LineBorder(Color.black));
				newWallImage.addMouseListener(wallImageListener);
				i++;
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private void AddKeyListener() {
		collectionMapPanel.addKeyListener(this);
	}
	
	private void RemoveKeyListener() {
		collectionMapPanel.removeKeyListener(this);
	}
	
	public static void EndMap() {
		stopMapButton.doClick();
	}
	
	private void StartNewTimerThread(JLabel elapsedTimeDisplay) {
		SurvivalMapTimer.StartTimer();
		Thread timerThread = new Thread() {
			public void run() {
				while(keepTimerGoing) {
					try {
						elapsedTimeDisplay.setText(SurvivalMapTimer.GetTime());
						Thread.sleep(500);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		timerThread.start();
	}
	
	public static int getSelected() {
		return selected;
	}
	
	public static JPanel getCollectionMap() {
		return collectionMapPanel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(selected == playerStartSelected) {
			if(CurrentSurvivalMapData.getPlayerStart() == null) {
				CurrentSurvivalMapData.setPlayerStart(HoverImage.PlaceItemInCollectionMap(collectionMapPanel));
			}
			else {
				Point mousePosition = collectionMapPanel.getMousePosition();
				int playerChoice = JOptionPane.showConfirmDialog(null, "Are you sure you want to change your player start?");
				if(playerChoice == JOptionPane.YES_OPTION) {
					collectionMapPanel.remove(CurrentSurvivalMapData.getPlayerStart());
					CurrentSurvivalMapData.setPlayerStart(HoverImage.PlaceItemInCollectionMap(collectionMapPanel, mousePosition));
				}
			}
		}
		else if(selected == enemySpawnerSelected) {
			JLabel spawnerlabel = HoverImage.PlaceItemInCollectionMap(collectionMapPanel);
			SurvivalMapEnemySpawner newSpawner = new SurvivalMapEnemySpawner(spawnerlabel);
			CurrentSurvivalMapData.getEnemySpawnerList().add(newSpawner);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(selected == addWallSelected) {
			CurrentSurvivalMapData.getMapWalls().add(new SurvivalMapWall(HoverImage.PlaceItemInCollectionMap(collectionMapPanel)));
		}
		else if(selected == removeWallsSelected) {
			Point mousePosition = collectionMapPanel.getMousePosition();
			LinkedList<SurvivalMapWall> mapWallList = CurrentSurvivalMapData.getMapWalls();
			for(int i = 0; i < mapWallList.size(); i++) {
				if(mapWallList.get(i).getWall().getBounds().contains(mousePosition)) {
					collectionMapPanel.remove(mapWallList.get(i).getWall());
					collectionMapPanel.repaint();
					CurrentSurvivalMapData.getMapWalls().remove(mapWallList.get(i));
					break;
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(selected == playerStartSelected) {
			HoverImage.AddItemToCollectionMap(collectionMapPanel, "original", currentSize);
		}
		else if(selected == addWallSelected) {
			HoverImage.AddItemToCollectionMap(collectionMapPanel, wallNameList.get(addWallIndex), currentSize);
		}
		else if(selected == enemySpawnerSelected) {
			HoverImage.AddItemToCollectionMap(collectionMapPanel, "abyssal stair S", currentSize);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(selected == playerStartSelected) {
			HoverImage.RemoveItemFromCollectionMap(collectionMapPanel);
		}
		else if(selected == addWallSelected) {
			HoverImage.RemoveItemFromCollectionMap(collectionMapPanel);
		}
		else if(selected == enemySpawnerSelected) {
			HoverImage.RemoveItemFromCollectionMap(collectionMapPanel);
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(selected == addWallSelected) {
			JLabel wallPlaced = HoverImage.PlaceItemInCollectionMap(collectionMapPanel);
			if(wallPlaced != null) {
				CurrentSurvivalMapData.getMapWalls().add(new SurvivalMapWall(wallPlaced));
			}
		}
		else if(selected == removeWallsSelected) {
			Point mousePosition = collectionMapPanel.getMousePosition();
			LinkedList<SurvivalMapWall> mapWallList = CurrentSurvivalMapData.getMapWalls();
			for(int i = 0; i < mapWallList.size(); i++) {
				if(mapWallList.get(i).getWall().getBounds().contains(mousePosition)) {
					collectionMapPanel.remove(mapWallList.get(i).getWall());
					collectionMapPanel.repaint();
					CurrentSurvivalMapData.getMapWalls().remove(mapWallList.get(i));
					break;
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(selected == playerStartSelected) {
			HoverImage.MoveItemInCollectionMap(collectionMapPanel);
		}
		else if(selected == addWallSelected) {
			HoverImage.MoveItemInCollectionMap(collectionMapPanel);
		}
		else if(selected == enemySpawnerSelected) {
			HoverImage.MoveItemInCollectionMap(collectionMapPanel);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		pressedKeys.add(keyCode);
		/*
		 * PLAYER MOVEMENTS: Can go in multiple directions.
		 */
		if (!pressedKeys.isEmpty()) {
			for (Iterator<Integer> key = pressedKeys.iterator(); key.hasNext();) {
				switch (key.next()) {
				case KeyEvent.VK_UP:
					SurvivalMapPlayer.MoveUp();
					break;
				case KeyEvent.VK_DOWN:
					SurvivalMapPlayer.MoveDown();
					break;
				case KeyEvent.VK_LEFT:
					SurvivalMapPlayer.MoveLeft();
					break;
				case KeyEvent.VK_RIGHT:
					SurvivalMapPlayer.MoveRight();
					break;
				case KeyEvent.VK_W:
					SurvivalMapPlayer.MoveUp();
					break;
				case KeyEvent.VK_S:
					SurvivalMapPlayer.MoveDown();
					break;
				case KeyEvent.VK_A:
					SurvivalMapPlayer.MoveLeft();
					break;
				case KeyEvent.VK_D:
					SurvivalMapPlayer.MoveRight();
					break;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		
	}
	
	public void setVisible(boolean visible) {
		survivalMapFrm.setVisible(visible);
	}
}
