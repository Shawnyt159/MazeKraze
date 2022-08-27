package mazeapplication;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.ScrollPane;
import javax.swing.JEditorPane;

@SuppressWarnings("serial")
public class WinMessageEditorFrame extends JFrame {

	private JPanel contentPane;
	private JEditorPane winMessageDisplayArea;

	/**
	 * Create the frame.
	 */
	public WinMessageEditorFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(WinMessageEditorFrame.class.getResource("/images/logo.png")));
		setTitle("Win Message Editor");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 432, 240);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				winMessageDisplayArea.setText(ButtonFunctions.getWinMessage().getMessage());
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
		contentPane.setBackground(new Color(120,177,199));
		
		Color buttonColor = new Color(170, 204, 0);
		JLabel currentWinMessageLabel = new JLabel("Current win message:");
		currentWinMessageLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		currentWinMessageLabel.setBounds(10, 11, 165, 24);
		contentPane.add(currentWinMessageLabel);
		
		JButton setNewWinMessageButton = new JButton("Set Message");
		setNewWinMessageButton.setBounds(283, 14, 117, 23);
		setNewWinMessageButton.setBackground(buttonColor);
		contentPane.add(setNewWinMessageButton);
		
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setBounds(10, 41, 390, 158);
		contentPane.add(scrollPane);
		
		winMessageDisplayArea = new JEditorPane();
		winMessageDisplayArea.setBounds(0, 0, 390, 158);
		setVisible(true);
		winMessageDisplayArea.setText(ButtonFunctions.getWinMessage().getMessage());
		scrollPane.add(winMessageDisplayArea);
		
		setNewWinMessageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(winMessageDisplayArea.getText().length() < 150 && !winMessageDisplayArea.getText().contains("\n")) {
					ButtonFunctions.setWinMessage(winMessageDisplayArea.getText());
					JOptionPane.showMessageDialog(null, "Message Has Been Changed!");
				}
				else {
					JOptionPane.showMessageDialog(null, "Message was not changed, messages can't exceed 150 characters or have enter characters!");
					winMessageDisplayArea.setText(ButtonFunctions.getWinMessage().getMessage());
				}
			}
		});
	}
	public JEditorPane getWinMessageDisplayArea() {
		return winMessageDisplayArea;
	}
}
