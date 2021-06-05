package mazeapplication;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JSeparator;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LevelMenu implements ActionListener{

	private JFrame frmMazekrazeLevelPlay;
	private ArrayList<String> mazeImagess = new ArrayList<String>();
	private SetImageToLabel images = new SetImageToLabel();
	private HashMap<JButton, String> mazesWithImagePaths = new HashMap<JButton, String>();
	private JLabel mazePreviewImageLabel;
	private String currentSelectedMaze = null;
	private LoadSerMazeFromResources loadMaze = new LoadSerMazeFromResources();
	private MazeLevelPlayGUI mazeGUI;

	public LevelMenu(){
		initialize();
	}

	private void initialize() {
		frmMazekrazeLevelPlay = new JFrame();
		frmMazekrazeLevelPlay.setIconImage(Toolkit.getDefaultToolkit().getImage(LevelMenu.class.getResource("/images/logo.png")));
		frmMazekrazeLevelPlay.setTitle("MazeKraze Level Play");
		frmMazekrazeLevelPlay.setBounds(100, 100, 825, 600);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmMazekrazeLevelPlay.setLocation(dim.width / 2 - frmMazekrazeLevelPlay.getSize().width / 2, dim.height / 2 - frmMazekrazeLevelPlay.getSize().height / 2);
		frmMazekrazeLevelPlay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmMazekrazeLevelPlay.getContentPane().setLayout(null);
		
		// Loading the maze image paths to the mazeImages ArrayList.
		LoadMazeImagesToArray();
		
		JPanel levelPanel = new JPanel();
		levelPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		levelPanel.setBackground(Color.WHITE);
		levelPanel.setBounds(10, 49, 791, 504);
		frmMazekrazeLevelPlay.getContentPane().add(levelPanel);
		levelPanel.setLayout(null);
		
		JLabel mazePreviewLabel = new JLabel("Maze Preview:");
		mazePreviewLabel.setBounds(499, 10, 100, 13);
		levelPanel.add(mazePreviewLabel);
		
		mazePreviewImageLabel = new JLabel("");
		mazePreviewImageLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		mazePreviewImageLabel.setBounds(499, 33, 282, 253);
		levelPanel.add(mazePreviewImageLabel);
		
		JButton smileyPizzaMazeButton = new JButton("Smiley Pizza");
		smileyPizzaMazeButton.setBackground(new Color(127, 255, 0));
		smileyPizzaMazeButton.setForeground(new Color(0, 0, 0));
		smileyPizzaMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		smileyPizzaMazeButton.setBounds(10, 64, 85, 21);
		levelPanel.add(smileyPizzaMazeButton);
		
		JButton theDudeMazeButton = new JButton("The Dude");
		theDudeMazeButton.setBackground(new Color(127, 255, 0));
		theDudeMazeButton.setForeground(new Color(0, 0, 0));
		theDudeMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		theDudeMazeButton.setBounds(105, 64, 85, 21);
		levelPanel.add(theDudeMazeButton);
		
		JButton castleMazeButton = new JButton("Castle");
		castleMazeButton.setForeground(Color.BLACK);
		castleMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		castleMazeButton.setBackground(new Color(127, 255, 0));
		castleMazeButton.setBounds(200, 63, 85, 21);
		levelPanel.add(castleMazeButton);
		
		JLabel noviceMazesLabel = new JLabel("Novice Mazes:");
		noviceMazesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		noviceMazesLabel.setBounds(10, 10, 164, 32);
		levelPanel.add(noviceMazesLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 52, 479, 2);
		levelPanel.add(separator);
		
		JLabel easyMazesLabel = new JLabel("Easy Mazes:");
		easyMazesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		easyMazesLabel.setBounds(10, 126, 164, 32);
		levelPanel.add(easyMazesLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 168, 479, 2);
		levelPanel.add(separator_1);
		
		JButton beachTimesMazeButton = new JButton("Beach Times");
		beachTimesMazeButton.setBackground(new Color(64, 224, 208));
		beachTimesMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		beachTimesMazeButton.setBounds(10, 180, 85, 21);
		levelPanel.add(beachTimesMazeButton);
		
		JButton chemistryMazeButton = new JButton("Chemistry");
		chemistryMazeButton.setBackground(new Color(64, 224, 208));
		chemistryMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		chemistryMazeButton.setBounds(105, 180, 85, 21);
		levelPanel.add(chemistryMazeButton);
		
		JButton circleMazeButton = new JButton("Circle");
		circleMazeButton.setBackground(new Color(64, 224, 208));
		circleMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		circleMazeButton.setBounds(200, 180, 85, 21);
		levelPanel.add(circleMazeButton);
		
		JButton boxedLinesMazeButton = new JButton("Box Lines");
		boxedLinesMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		boxedLinesMazeButton.setBackground(new Color(64, 224, 208));
		boxedLinesMazeButton.setBounds(295, 180, 85, 21);
		levelPanel.add(boxedLinesMazeButton);
		
		JLabel mediumMazesLabel = new JLabel("Medium Mazes:");
		mediumMazesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		mediumMazesLabel.setBounds(10, 242, 164, 32);
		levelPanel.add(mediumMazesLabel);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 284, 479, 2);
		levelPanel.add(separator_2);
		
		JButton theGridMazeButton = new JButton("The Grid");
		theGridMazeButton.setBackground(new Color(255, 165, 0));
		theGridMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		theGridMazeButton.setBounds(10, 296, 85, 21);
		levelPanel.add(theGridMazeButton);
		
		JButton theGridWithEnemiesButton = new JButton("The Grid E");
		theGridWithEnemiesButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		theGridWithEnemiesButton.setBackground(new Color(255, 165, 0));
		theGridWithEnemiesButton.setBounds(105, 296, 85, 21);
		levelPanel.add(theGridWithEnemiesButton);
		
		JButton theGridWithEnemiesAndBlackout = new JButton("The Grid EB");
		theGridWithEnemiesAndBlackout.setFont(new Font("Tahoma", Font.PLAIN, 8));
		theGridWithEnemiesAndBlackout.setBackground(new Color(255, 165, 0));
		theGridWithEnemiesAndBlackout.setBounds(200, 296, 85, 21);
		levelPanel.add(theGridWithEnemiesAndBlackout);
		
		JLabel hardMazesLabel = new JLabel("Hard Mazes:");
		hardMazesLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		hardMazesLabel.setBounds(10, 358, 164, 32);
		levelPanel.add(hardMazesLabel);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(10, 400, 731, 2);
		levelPanel.add(separator_3);
		
		JButton rainbowCurvesMazeButton = new JButton("Rainbow");
		rainbowCurvesMazeButton.setBackground(new Color(255, 0, 0));
		rainbowCurvesMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		rainbowCurvesMazeButton.setBounds(10, 412, 85, 21);
		levelPanel.add(rainbowCurvesMazeButton);
		
		JButton multiMazeButton = new JButton("Multi Maze");
		multiMazeButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		multiMazeButton.setBackground(Color.RED);
		multiMazeButton.setBounds(105, 411, 85, 21);
		levelPanel.add(multiMazeButton);
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.setBounds(10, 10, 791, 39);
		frmMazekrazeLevelPlay.getContentPane().add(optionsPanel);
		optionsPanel.setLayout(null);
		
		JButton playButton = new JButton("PLAY!");
		playButton.setFont(new Font("Papyrus", Font.PLAIN, 17));
		playButton.setBackground(new Color(255, 215, 0));
		playButton.setBounds(636, 0, 155, 39);
		optionsPanel.add(playButton);
		
		JLabel backgroundImageLabel = new JLabel("");
		backgroundImageLabel.setBounds(0, 0, 811, 563);
		frmMazekrazeLevelPlay.getContentPane().add(backgroundImageLabel);
		
		int currentMazeImageIndex = 0;
		for(Component c: levelPanel.getComponents()) {
			if(c instanceof JButton) {
				mazesWithImagePaths.put((JButton) c, mazeImagess.get(currentMazeImageIndex));
				currentMazeImageIndex++;
				((JButton) c).addActionListener(this);
			}
		}
		
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Loads the maze attributes.
				if(currentSelectedMaze != null) {
					LevelPlayLoadedMazeAttributes.resetEverything();
					loadMaze.Load(currentSelectedMaze);
					if(mazeGUI == null) {
						mazeGUI = new MazeLevelPlayGUI();
					}
					mazeGUI.setVisible();
					mazeGUI.ResetMazeAttributes();
					mazeGUI.SetMazeAttributes();
					MazeLevelPlayGUI.getMazePanel().repaint();
				}
			}
		});
		SetImageToLabel images = new SetImageToLabel();
		images.set_image_to_label(backgroundImageLabel, "/images/testBackground15.jpg");
		frmMazekrazeLevelPlay.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		String imagePath = mazesWithImagePaths.get(arg0.getSource());
		images.set_image_to_label(mazePreviewImageLabel, imagePath);
		String mazeImagePath = imagePath;
		PutMazePathInCorrectFormat(mazeImagePath);
	}
	
	private void LoadMazeImagesToArray() {
		InputStream mazeImageNamesStream = getClass().getResourceAsStream("/images/mazesInOrder.txt");
		BufferedReader mazeImageNameReader = new BufferedReader(new InputStreamReader(mazeImageNamesStream));
		String currentMazeImage = "";
		try {
			while(mazeImageNameReader.ready()) {
				currentMazeImage = mazeImageNameReader.readLine();
				mazeImagess.add(currentMazeImage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setVisible(boolean visibility) {
		frmMazekrazeLevelPlay.setVisible(visibility);
	}
	
	private void PutMazePathInCorrectFormat(String mazeImagePath) {
		StringBuilder sb = new StringBuilder(mazeImagePath);
		//Get characters from 8-.
		mazeImagePath = sb.substring(8, sb.indexOf(".")).toString();
		currentSelectedMaze = "/mazes/" + mazeImagePath + ".ser";
	}
}