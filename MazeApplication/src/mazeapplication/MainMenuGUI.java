package mazeapplication;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JSeparator;

public class MainMenuGUI {

	private JFrame frmMazekraze;
	private JTextField emailTextBox;
	private static MazeDesignMainGUI freeDrawMazeGUI = null;
	private static LevelMenu levelMenu = null;
	private SetImageToLabel images = new SetImageToLabel();
	private JTextField instagramField;
	private JTextField websiteAddress;
	//private UpdatesAndNews updatesAndNews = new UpdatesAndNews();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenuGUI window = new MainMenuGUI();
					window.frmMazekraze.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenuGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMazekraze = new JFrame();
		frmMazekraze.setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenuGUI.class.getResource("/images/logo.png")));
		frmMazekraze.setTitle("MazeKraze");
		frmMazekraze.getContentPane().setBackground(Color.WHITE);
		frmMazekraze.setBounds(100, 100, 730, 660);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmMazekraze.setLocation(dim.width / 2 - frmMazekraze.getSize().width / 2, dim.height / 2 - frmMazekraze.getSize().height / 2);
		frmMazekraze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMazekraze.getContentPane().setLayout(null);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 716, 623);
		frmMazekraze.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBounds(7, 196, 702, 165);
		mainPanel.add(informationPanel);
		informationPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		informationPanel.setBackground(new Color(255, 215, 0));
		informationPanel.setLayout(null);
		
		JLabel emailLabel = new JLabel("Submit Mazes Email:");
		emailLabel.setBounds(10, 10, 186, 44);
		informationPanel.add(emailLabel);
		emailLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		emailLabel.setForeground(new Color(0, 0, 0));
		
		emailTextBox = new JTextField();
		emailTextBox.setForeground(new Color(220, 20, 60));
		emailTextBox.setBounds(10, 110, 307, 35);
		informationPanel.add(emailTextBox);
		emailTextBox.setBackground(new Color(255, 215, 0));
		emailTextBox.setEditable(false);
		emailTextBox.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		emailTextBox.setText("mazekrazesubmit@gmail.com");
		emailTextBox.setColumns(10);
		
		JLabel emailInformationlabel = new JLabel("<html>Email your completed mazes here for a chance to get them on the website!</html>");
		emailInformationlabel.setBounds(10, 57, 307, 44);
		informationPanel.add(emailInformationlabel);
		emailInformationlabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
		emailInformationlabel.setForeground(new Color(0, 0, 0));
		
		JLabel instagramLabel = new JLabel("Follow our Instagram:");
		instagramLabel.setBounds(382, 10, 190, 44);
		informationPanel.add(instagramLabel);
		instagramLabel.setForeground(new Color(0, 0, 0));
		instagramLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		
		instagramField = new JTextField();
		instagramField.setForeground(new Color(220, 20, 60));
		instagramField.setBounds(382, 57, 147, 37);
		informationPanel.add(instagramField);
		instagramField.setEditable(false);
		instagramField.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		instagramField.setText("@mazekraze");
		instagramField.setBackground(new Color(255, 215, 0));
		instagramField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(10, 52, 307, 2);
		informationPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(new Color(0, 0, 0));
		separator_1.setBounds(382, 52, 310, 2);
		informationPanel.add(separator_1);
		
		JLabel lblNewLabel = new JLabel("More Info on the website:");
		lblNewLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		lblNewLabel.setBounds(382, 95, 310, 29);
		informationPanel.add(lblNewLabel);
		
		websiteAddress = new JTextField();
		websiteAddress.setEditable(false);
		websiteAddress.setForeground(new Color(220, 20, 60));
		websiteAddress.setText("www.mazekraze.com");
		websiteAddress.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		websiteAddress.setBackground(new Color(255, 215, 0));
		websiteAddress.setBounds(382, 134, 310, 25);
		informationPanel.add(websiteAddress);
		websiteAddress.setColumns(10);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(new Color(0, 0, 0));
		separator_2.setBounds(382, 127, 310, 2);
		informationPanel.add(separator_2);
		
		JPanel modeButtonPanel = new JPanel();
		modeButtonPanel.setOpaque(false);
		modeButtonPanel.setBounds(6, 441, 705, 182);
		mainPanel.add(modeButtonPanel);
		modeButtonPanel.setLayout(null);
		
		
		JButton freeDrawButton = new JButton("Maze Design");
		freeDrawButton.setFont(new Font("Papyrus", Font.BOLD, 30));
		freeDrawButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		freeDrawButton.setBounds(0, 95, 705, 82);
		modeButtonPanel.add(freeDrawButton);
		freeDrawButton.setBackground(new Color(255, 215, 0));
		
		JButton LevelPlayButton = new JButton("Level Play");
		LevelPlayButton.setFont(new Font("Papyrus", Font.BOLD, 30));
		LevelPlayButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		LevelPlayButton.setBounds(0, 10, 705, 80);
		modeButtonPanel.add(LevelPlayButton);
		LevelPlayButton.setBackground(new Color(255, 215, 0));
		
		JPanel blackSeperator2 = new JPanel();
		blackSeperator2.setBackground(new Color(0, 0, 0));
		blackSeperator2.setBounds(0, 90, 705, 5);
		modeButtonPanel.add(blackSeperator2);
		
		JPanel blackSeperator1 = new JPanel();
		blackSeperator1.setBackground(new Color(0, 0, 0));
		blackSeperator1.setBounds(0, 5, 705, 5);
		modeButtonPanel.add(blackSeperator1);
		
		LevelPlayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(levelMenu == null) {
					levelMenu = new LevelMenu();
				}
				levelMenu.setVisible(true);
			}
			
		});
		
		JPanel mazeKrazeNamePanel = new JPanel();
		mazeKrazeNamePanel.setOpaque(false);
		mazeKrazeNamePanel.setRequestFocusEnabled(false);
		mazeKrazeNamePanel.setBackground(new Color(240, 240, 240));
		mazeKrazeNamePanel.setBounds(21, 10, 685, 165);
		mainPanel.add(mazeKrazeNamePanel);
		mazeKrazeNamePanel.setLayout(null);
		
		JLabel restOfName = new JLabel("MAZE KRAZE", SwingConstants.CENTER);
		restOfName.setForeground(new Color(255, 215, 0));
		restOfName.setBounds(0, 0, 685, 157);
		mazeKrazeNamePanel.add(restOfName);
		restOfName.setFont(new Font("Viner Hand ITC", Font.ITALIC, 92));
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setBounds(0, 0, 716, 623);
		mainPanel.add(imageLabel);
		
		images.set_image_to_label(imageLabel, "/images/theGridBackgroundGlacier.jpeg");
		
		//Button Actions.
		freeDrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(freeDrawMazeGUI == null) {
					freeDrawMazeGUI = new MazeDesignMainGUI();
				}
				freeDrawMazeGUI.setVisible(true);
			}
			
		});
	}
	
	public static boolean isLevelMazeActive() {
		if(levelMenu == null) {
			return false;
		}
		else {
			if(LevelMenu.MazeActive() == true) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static boolean isMazeDesignMazeActive() {
		if(freeDrawMazeGUI == null) {
			return false;
		}
		else {
			return freeDrawMazeGUI.mazeActive();
		}
	}
}
