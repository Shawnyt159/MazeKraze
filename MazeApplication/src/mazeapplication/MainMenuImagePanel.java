package mazeapplication;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainMenuImagePanel extends JPanel{
	private BufferedImage backgroundImage;
	private Image backgroundImageScaled;
	
	public MainMenuImagePanel() {
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/images/possibleBackground1.png"));
			 backgroundImageScaled = backgroundImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
			 System.out.println(this.getWidth());
		}catch(Exception e) {
			
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImageScaled, 0, 0, this);
	}
}
