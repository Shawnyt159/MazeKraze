package mazeapplication;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;
import javax.swing.JButton;


public class MazeLevelPlayGUI implements KeyListener{

	private JFrame frame;
	private static JLabel player;
	
	private JLabel elapsedTimeDisplay;
	private JLabel startLocation = null;
	private JLabel endLocation = null;
	private Enemy enemy1 = null;
	private Enemy enemy2 = null;
	private Enemy enemy3 = null;
	private final HashSet<Integer> pressedKeys = new HashSet<Integer>();
	private static JPanel mazePanel;
	private Thread timerThread;
	private boolean keepTimerGoing = true;
	
	/**
	 * Create the application.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	public MazeLevelPlayGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MazeLevelPlayGUI.class.getResource("/images/logo.png")));
		frame.setBounds(100, 100, 910, 701);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				BackgroundMusic.PlayCorrectAudioContinuously(Color.black);
				keepTimerGoing = false;
				while(timerThread.isAlive()) {
					
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mazePanel = new levelPlayMazePanel();
		mazePanel.setBounds(10, 10, 708, 648);
		mazePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		mazePanel.setFocusable(true);
		mazePanel.addKeyListener(this);
		frame.getContentPane().add(mazePanel);
		mazePanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 215, 0));
		panel.setBounds(728, 10, 150, 456);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel movementLabel = new JLabel("Movement:");
		movementLabel.setBounds(10, 0, 140, 21);
		panel.add(movementLabel);
		movementLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 19, 140, 2);
		panel.add(separator);
		
		JLabel upMovementLabel = new JLabel("<html>Up Arrow: Move up<br/>W: Move up<br/>W + Up Arrow : Move up fast</html>");
		upMovementLabel.setBounds(10, 25, 140, 64);
		panel.add(upMovementLabel);
		upMovementLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		upMovementLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 87, 140, 2);
		panel.add(separator_1);
		
		JLabel downMovementLabel = new JLabel("<html>Down Arrow: Move Down<br/>S: Move Down<br/>S + Down Arrow : Move Down fast</html>");
		downMovementLabel.setBounds(10, 99, 140, 64);
		panel.add(downMovementLabel);
		downMovementLabel.setVerticalAlignment(SwingConstants.TOP);
		downMovementLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(10, 162, 140, 2);
		panel.add(separator_1_1);
		
		JLabel rightMovementLabel = new JLabel("<html>Right Arrow: Move Right<br/>\r\nD: Move Right<br/>\r\nD + Right Arrow : Move Right fast</html>");
		rightMovementLabel.setBounds(10, 173, 140, 64);
		panel.add(rightMovementLabel);
		rightMovementLabel.setVerticalAlignment(SwingConstants.TOP);
		rightMovementLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JSeparator separator_1_1_1 = new JSeparator();
		separator_1_1_1.setBounds(10, 247, 140, 2);
		panel.add(separator_1_1_1);
		
		JLabel leftMovementLabel = new JLabel("<html>Left Arrow: Move Left<br/>\r\nA: Move Left<br/>\r\nA + Left Arrow : Move Left fast</html>");
		leftMovementLabel.setBounds(10, 259, 140, 64);
		panel.add(leftMovementLabel);
		leftMovementLabel.setVerticalAlignment(SwingConstants.TOP);
		leftMovementLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JButton pauseMusicButton = new JButton("Pause Music");
		pauseMusicButton.setBounds(10, 333, 132, 21);
		panel.add(pauseMusicButton);
		pauseMusicButton.setFocusable(false);
		pauseMusicButton.setBackground(new Color(238, 232, 170));
		
		JButton playMusicButton = new JButton("Play Music");
		playMusicButton.setBounds(10, 364, 132, 21);
		panel.add(playMusicButton);
		playMusicButton.setFocusable(false);
		playMusicButton.setBackground(new Color(238, 232, 170));
		
		JLabel elapsedTimeLabel = new JLabel("Elapsed Time:");
		elapsedTimeLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		elapsedTimeLabel.setBounds(10, 395, 130, 27);
		panel.add(elapsedTimeLabel);
		
		elapsedTimeDisplay = new JLabel("");
		elapsedTimeDisplay.setBounds(10, 421, 132, 13);
		panel.add(elapsedTimeDisplay);
		
		JLabel backgroundImageLabel = new JLabel("");
		backgroundImageLabel.setBounds(0, 0, 896, 664);
		frame.getContentPane().add(backgroundImageLabel);
		
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
		
		SetImageToLabel images = new SetImageToLabel();
		images.set_image_to_label(backgroundImageLabel, "/images/testBackground15.jpg");
		frame.setVisible(true);
	}
	
	private void StartNewTimerThread() {
		timerThread = new Thread() {
			public void run() {
				while(keepTimerGoing) {
					try {
						elapsedTimeDisplay.setText(LevelPlayTimer.GetTime());
						Thread.sleep(500);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		timerThread.start();
	}
	
	public void ChangeStartLabelToPlayerLabel(JPanel mazePanel) {
		JLabel startLocation = LevelPlayLoadedMazeAttributes.getPlayerStart();
		startLocation.setVisible(false);
		player = new JLabel("");
		player.setBounds(startLocation.getBounds());
		mazePanel.add(player);
		player.setVisible(true);
		LevelPlayer.SetPlayerImage(player);
	}
	
	public void SetMazeAttributes() {
		keepTimerGoing = true;
		LevelPlayTimer.StartTimer();
		StartNewTimerThread();
		mazePanel.requestFocusInWindow();
		startLocation = LevelPlayLoadedMazeAttributes.getPlayerStart();
		endLocation = LevelPlayLoadedMazeAttributes.getEndLocation();
		enemy1 = LevelPlayLoadedMazeAttributes.getEnemy1();
		enemy2 = LevelPlayLoadedMazeAttributes.getEnemy2();
		enemy3 = LevelPlayLoadedMazeAttributes.getEnemy3();
		mazePanel.add(startLocation);
		mazePanel.add(endLocation);
		if(enemy1 != null) {
			if(enemy1.getStartLabel() != null) {
				mazePanel.add(enemy1.getStartLabel());
				if(enemy1.getEndLocation() != null) {
					mazePanel.add(enemy1.getEndLocation());
				}
			}
		}
		if(enemy2 != null) {
			if(enemy2.getStartLabel() != null) {
				mazePanel.add(enemy2.getStartLabel());
				if(enemy2.getEndLocation() != null) {
					mazePanel.add(enemy2.getEndLocation());
				}
			}
		}
		if(enemy3 != null) {
			if(enemy3.getStartLabel() != null) {
				mazePanel.add(enemy3.getStartLabel());
				if(enemy3.getEndLocation() != null) {
					mazePanel.add(enemy3.getEndLocation());
				}
			}
		}
		Color backgroundColor = LevelPlayLoadedMazeAttributes.getBackgroundColor();
		if(backgroundColor == null) {
			mazePanel.setBackground(Color.white);
			if(BackgroundMusic.isPaused() == false) {
				BackgroundMusic.PlayCorrectAudioContinuously(Color.white);
			}
		}
		else {
			mazePanel.setBackground(backgroundColor);
			if(BackgroundMusic.isPaused() == false) {
				BackgroundMusic.PlayCorrectAudioContinuously(backgroundColor);
			}
		}
		if(LevelPlayLoadedMazeAttributes.getDecorationList() != null) {
			Iterator<DecorationNode> iterator = LevelPlayLoadedMazeAttributes.getDecorationList().iterator();
			while(iterator.hasNext()) {
				mazePanel.add(iterator.next().getDecorationNode());
			}
		}
		ChangeStartLabelToPlayerLabel(mazePanel);
		LevelPlayEnemyStartAndStopMazeFunctions.StartEnemies();
		if(LevelPlayLoadedMazeAttributes.getBlackoutSetting() == true) {
			BlackoutMaze.InitiateBlackout(player, mazePanel);
		}
		else {
			BlackoutMaze.StopBlackout(mazePanel);
		}
	}
	
	public void ResetMazeAttributes() {
		if(startLocation != null) {
			mazePanel.remove(startLocation);
		}
		if(endLocation != null) {
			mazePanel.remove(endLocation);
		}
		if(player != null) {
			mazePanel.remove(player);
		}
		if(enemy1 != null) {
			if(enemy1.getStartLabel() != null) {
				mazePanel.remove(enemy1.getStartLabel());
			}
			if(enemy1.getEndLocation() != null) {
				mazePanel.remove(enemy1.getEndLocation());
			}
		}
		if(enemy2 != null) {
			if(enemy2.getStartLabel() != null) {
				mazePanel.remove(enemy2.getStartLabel());
			}
			if(enemy2.getEndLocation() != null) {
				mazePanel.remove(enemy2.getEndLocation());
			}
		}
		if(enemy3 != null) {
			if(enemy3.getStartLabel()!= null) {
				mazePanel.remove(enemy3.getStartLabel());
			}
			if(enemy3.getEndLocation() != null) {
				mazePanel.remove(enemy3.getEndLocation());
			}
		}
		LevelPlayEnemyStartAndStopMazeFunctions.StopEnemies();
		pressedKeys.clear();
		startLocation = null;
		endLocation = null;
		enemy1 = null;
		enemy2 = null;
		enemy3 = null;
		player = null;
		mazePanel.removeAll();
		mazePanel.repaint();
	}
	
	public static JPanel getMazePanel() {
		return mazePanel;
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public static JLabel GetPlayerLabel() {
		return player;
	}
	
	public boolean isActive() {
		return mazePanel.isVisible();
	}
	
	public void dispose() {
		frame.setVisible(false);
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
					LevelPlayer.MoveUp(player);
					break;
				case KeyEvent.VK_DOWN:
					LevelPlayer.MoveDown(player);
					break;
				case KeyEvent.VK_LEFT:
					LevelPlayer.MoveLeft(player);
					break;
				case KeyEvent.VK_RIGHT:
					LevelPlayer.MoveRight(player);
					break;
				case KeyEvent.VK_W:
					LevelPlayer.MoveUp(player);
					break;
				case KeyEvent.VK_S:
					LevelPlayer.MoveDown(player);
					break;
				case KeyEvent.VK_A:
					LevelPlayer.MoveLeft(player);
					break;
				case KeyEvent.VK_D:
					LevelPlayer.MoveRight(player);
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
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
