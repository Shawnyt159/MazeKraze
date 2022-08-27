package mazeapplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultEditorKit;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.*;

public class MainMenuGUI {

	private JFrame frmMazekraze;
	private static MazeDesignMainGUI freeDrawMazeGUI = null;
	private static LevelMenu levelMenu = null;
	private static SurvivalMapDesignWindow collectionMapWindow = null;
	private SetImageToLabel images = new SetImageToLabel();
	private UpdatesAndNews updatesAndNews = new UpdatesAndNews();
	private JTextField emailTextField;
	private JTextField instagramTextField;
	private JTextField websiteTextField;

	/**
	 * Create the application.
	 */
	public MainMenuGUI() {
		String updates = updatesAndNews.getUpdates();
		initialize(updates);
		if(updates != null && updates.contains("Update Available")) {
			JOptionPane.showMessageDialog(null, "An update for MazeKraze is availible, new maze features won't load on your current version! Head to 'www.mazekraze.com' to learn how to update your version!");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String updates) {
		frmMazekraze = new JFrame();
		frmMazekraze.setIconImage(Toolkit.getDefaultToolkit().getImage(MainMenuGUI.class.getResource("/images/logo.png")));
		frmMazekraze.setTitle("MazeKraze");
		frmMazekraze.getContentPane().setBackground(Color.WHITE);
		frmMazekraze.setBounds(100, 100, 730, 660);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmMazekraze.setLocation(dim.width / 2 - frmMazekraze.getSize().width / 2, dim.height / 2 - frmMazekraze.getSize().height / 2);
		frmMazekraze.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMazekraze.getContentPane().setLayout(null);
		frmMazekraze.setResizable(false);
		
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 716, 623);
		frmMazekraze.getContentPane().add(mainPanel);
		mainPanel.setLayout(null);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setBounds(7, 196, 702, 206);
		mainPanel.add(informationPanel);
		informationPanel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		informationPanel.setBackground(new Color(255, 215, 0));
		informationPanel.setLayout(null);
		
		JLabel emailLabel = new JLabel("Email");
		emailLabel.setHorizontalAlignment(SwingConstants.CENTER);
		emailLabel.setBackground(new Color(255, 225, 0));
		emailLabel.setOpaque(true);
		emailLabel.setBounds(10, 10, 307, 41);
		informationPanel.add(emailLabel);
		emailLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		emailLabel.setForeground(new Color(0, 0, 0));
		emailLabel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JLabel instagramLabel = new JLabel("Instagram");
		instagramLabel.setHorizontalAlignment(SwingConstants.CENTER);
		instagramLabel.setOpaque(true);
		instagramLabel.setBackground(new Color(255, 215, 0));
		instagramLabel.setBounds(6, 57, 311, 42);
		informationPanel.add(instagramLabel);
		instagramLabel.setForeground(new Color(0, 0, 0));
		instagramLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(0, 0, 0));
		separator.setBounds(10, 52, 307, 2);
		informationPanel.add(separator);
		
		JLabel websiteLabel = new JLabel("Website");
		websiteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		websiteLabel.setOpaque(true);
		websiteLabel.setBackground(new Color(255, 215, 0));
		websiteLabel.setFont(new Font("Rockwell", Font.PLAIN, 18));
		websiteLabel.setBounds(10, 104, 307, 42);
		informationPanel.add(websiteLabel);
		
		JLabel updatesLabel = new JLabel("");
		updatesLabel.setForeground(new Color(220, 20, 60));
		updatesLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		updatesLabel.setBounds(10, 160, 682, 35);
		if(updates != null) {
			updatesLabel.setText(updates);
		}else {
			updatesLabel.setText("Need Internet for Information.");
		}
		informationPanel.add(updatesLabel);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBackground(Color.BLACK);
		separator_1.setBounds(10, 99, 307, 2);
		informationPanel.add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBackground(Color.BLACK);
		separator_2.setBounds(10, 147, 307, 2);
		informationPanel.add(separator_2);
		
		JPanel specificInformationPanel = new JPanel();
		specificInformationPanel.setOpaque(false);
		specificInformationPanel.setBounds(327, 10, 365, 139);
		informationPanel.add(specificInformationPanel);
		specificInformationPanel.setLayout(null);
		
		JLabel websiteInformationLabel = new JLabel("<html> The website has everything MazeKraze on it. Check it<br/>out to download any maze drawn by designers all over<br/> the world. All mazes are printable and if you submit one<br/> it could end up on the website!</html>");
		websiteInformationLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		websiteInformationLabel.setVerticalAlignment(SwingConstants.TOP);
		websiteInformationLabel.setBounds(10, 11, 350, 86);
		
		JPopupMenu menu = new JPopupMenu();
		Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.NAME, "Copy");
        copy.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
        menu.add(copy);
		
		websiteTextField = new JTextField();
		websiteTextField.setFont(new Font("Tahoma", Font.BOLD, 17));
		websiteTextField.setText("www.mazekraze.com");
		websiteTextField.setOpaque(false);
		websiteTextField.setEditable(false);
		websiteTextField.setBounds(10, 98, 345, 30);
		websiteTextField.setColumns(10);
		websiteTextField.setComponentPopupMenu(menu);
		
		JLabel instagramInformationLabel = new JLabel("<html>Follow our Instagram! If you get your maze on the<br/>website then your maze will be posted to our<br/>instagram with your credit if you want!</html>");
		instagramInformationLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		instagramInformationLabel.setVerticalAlignment(SwingConstants.TOP);
		instagramInformationLabel.setBounds(10, 11, 345, 65);
		
		instagramTextField = new JTextField();
		instagramTextField.setEditable(false);
		instagramTextField.setOpaque(false);
		instagramTextField.setFont(new Font("Tahoma", Font.BOLD, 17));
		instagramTextField.setText("@mazekraze");
		instagramTextField.setBounds(10, 87, 345, 30);
		instagramTextField.setColumns(10);
		instagramTextField.setComponentPopupMenu(menu);
		
		JLabel emailInformationLabel = new JLabel("<html>Email all your completed mazes here for a chance<br/>to get them on the wbsite! Or email us here if you <br/>have anyquestions and our software engineer will get <br/>in touch with you as quickly as possible!</html>");
		emailInformationLabel.setFont(new Font("Dialog", Font.PLAIN, 14));
		emailInformationLabel.setVerticalAlignment(SwingConstants.TOP);
		emailInformationLabel.setBounds(10, 11, 345, 76);
		specificInformationPanel.add(emailInformationLabel);
		
		emailTextField = new JTextField();
		emailTextField.setEditable(false);
		emailTextField.setOpaque(false);
		emailTextField.setText("mazekrazesubmit@gmail.com");
		emailTextField.setFont(new Font("Tahoma", Font.BOLD, 17));
		emailTextField.setBounds(10, 98, 345, 30);
		emailTextField.setColumns(10);
		emailTextField.setComponentPopupMenu(menu);
		specificInformationPanel.add(emailTextField);
		
		JPanel modeButtonPanel = new JPanel();
		modeButtonPanel.setOpaque(false);
		modeButtonPanel.setBounds(6, 441, 705, 182);
		mainPanel.add(modeButtonPanel);
		modeButtonPanel.setLayout(null);
		
		JButton freeDrawButton = new JButton("Maze Design");
		freeDrawButton.setFont(new Font("Dialog", Font.BOLD, 25));
		freeDrawButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		freeDrawButton.setBounds(0, 67, 705, 57);
		modeButtonPanel.add(freeDrawButton);
		freeDrawButton.setBackground(new Color(255, 215, 0));
		
		JButton LevelPlayButton = new JButton("Level Play");
		LevelPlayButton.setFont(new Font("Dialog", Font.BOLD, 25));
		LevelPlayButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		LevelPlayButton.setBounds(0, 10, 705, 57);
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
		
		JButton collectionModeButton = new JButton("Survival Map Design");
		collectionModeButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		collectionModeButton.setBackground(new Color(255, 215, 0));
		collectionModeButton.setFont(new Font("Dialog", Font.BOLD, 25));
		collectionModeButton.setBounds(0, 124, 705, 57);
		modeButtonPanel.add(collectionModeButton);
		
		LevelPlayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(levelMenu == null) {
					levelMenu = new LevelMenu(frmMazekraze);
					frmMazekraze.setVisible(false);
				}
				levelMenu.setVisible(true);
				frmMazekraze.setVisible(false);
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
		
		JLabel[] optionsArray = new JLabel[] {emailLabel, instagramLabel, websiteLabel};
		
		//Button Actions.
		freeDrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(freeDrawMazeGUI == null) {
					freeDrawMazeGUI = new MazeDesignMainGUI(frmMazekraze);
					frmMazekraze.setVisible(false);
				}
				freeDrawMazeGUI.setVisible(true);
				frmMazekraze.setVisible(false);
			}
		});
		
		collectionModeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(collectionMapWindow == null) {
					collectionMapWindow = new SurvivalMapDesignWindow(frmMazekraze);
					frmMazekraze.setVisible(false);
				}
				else {
					collectionMapWindow.setVisible(true);
					frmMazekraze.setVisible(false);
				}
			}
		});
		
		MouseListener mainMenuOptionsListener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				for(int i = 0; i < optionsArray.length; i++) {
					if(e.getComponent().equals(optionsArray[i])) {
						optionsArray[i].setFont(new Font("Rockwell", Font.BOLD, 18));
						optionsArray[i].setBackground(new Color(255,225,0));
						optionsArray[i].setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
					}
					else {
						optionsArray[i].setFont(new Font("Rockwell", Font.PLAIN, 18));
						optionsArray[i].setBackground(new Color(255,215,0));
						optionsArray[i].setBorder(null);
					}
				}
				if(e.getComponent().equals(emailLabel)) {
					specificInformationPanel.removeAll();
					
					specificInformationPanel.add(emailInformationLabel);
					specificInformationPanel.add(emailTextField);
					
					specificInformationPanel.revalidate();
					specificInformationPanel.repaint();
				}
				else if(e.getComponent().equals(instagramLabel)) {
					specificInformationPanel.removeAll();
					
					specificInformationPanel.add(instagramInformationLabel);
					specificInformationPanel.add(instagramTextField);
					
					specificInformationPanel.revalidate();
					specificInformationPanel.repaint();
				}
				else if(e.getComponent().equals(websiteLabel)) {
					specificInformationPanel.removeAll();
					
					specificInformationPanel.add(websiteInformationLabel);
					specificInformationPanel.add(websiteTextField);
					
					specificInformationPanel.revalidate();
					specificInformationPanel.repaint();
				}
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
		};
		for(int i = 0; i < optionsArray.length; i++) {
			optionsArray[i].addMouseListener(mainMenuOptionsListener);
		}
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
			return MazeDesignMainGUI.mazeActive();
		}
	}
	
	public void setVisible(boolean visible) {
		frmMazekraze.setVisible(visible);
	}
}
