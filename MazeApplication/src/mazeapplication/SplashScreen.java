package mazeapplication;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.SwingConstants;

public class SplashScreen {

	private JFrame frmMazekrazeLauncher;
	private boolean started = false;
	private MainMenuGUI startScreen = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					@SuppressWarnings("unused")
					SplashScreen window = new SplashScreen();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SplashScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmMazekrazeLauncher = new JFrame();
		frmMazekrazeLauncher.setTitle("MazeKraze Launcher");
		frmMazekrazeLauncher.setIconImage(Toolkit.getDefaultToolkit().getImage(SplashScreen.class.getResource("/images/logo.png")));
		frmMazekrazeLauncher.getContentPane().setBackground(new Color(120,177,199));
		frmMazekrazeLauncher.setBounds(100, 100, 450, 292);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmMazekrazeLauncher.setLocation(dim.width / 2 - frmMazekrazeLauncher.getSize().width / 2, dim.height / 2 - frmMazekrazeLauncher.getSize().height / 2);
		frmMazekrazeLauncher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmMazekrazeLauncher.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBounds(10, 10, 416, 235);
		frmMazekrazeLauncher.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel mazekrazeLoadingLabel = new JLabel("MAZEKRAZE");
		mazekrazeLoadingLabel.setForeground(new Color(255, 215, 0));
		mazekrazeLoadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		mazekrazeLoadingLabel.setFont(new Font("Papyrus", Font.BOLD, 40));
		mazekrazeLoadingLabel.setBounds(10, 10, 396, 99);
		panel.add(mazekrazeLoadingLabel);

		JLabel loadingLabel = new JLabel("LOADING...");
		loadingLabel.setForeground(new Color(255, 215, 0));
		loadingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		loadingLabel.setFont(new Font("Papyrus", Font.BOLD, 40));
		loadingLabel.setBounds(10, 97, 396, 99);
		panel.add(loadingLabel);

		frmMazekrazeLauncher.setVisible(true);
		Thread loadingThread = new Thread() {
			public void run() {
				try {
					while(started == false) {
						if(loadingLabel.getText().equals("LOADING.")) {
							loadingLabel.setText("LOADING..");
						}
						else if(loadingLabel.getText().equals("LOADING..")) {
							loadingLabel.setText("LOADING...");
						}
						else if(loadingLabel.getText().equals("LOADING...")) {
							loadingLabel.setText("LOADING.");
						}
						sleep(500);
					}
					frmMazekrazeLauncher.setVisible(false);
					startScreen.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		loadingThread.start();
		
		Thread startScreenThread = new Thread() {
			public void run() {
				try {
					startScreen = new MainMenuGUI();
					started = true;
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		};
		startScreenThread.start();
	}
}
