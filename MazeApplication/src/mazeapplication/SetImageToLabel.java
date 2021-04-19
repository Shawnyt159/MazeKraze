package mazeapplication;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class SetImageToLabel {
	
	public SetImageToLabel() {
		
	}
	/**
	 * Sets an image to a JLabel.
	 */
	public void set_image_to_label(JLabel image_label, String file_name) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(getClass().getResource(file_name));
			Image dimg = image.getScaledInstance(image_label.getWidth(), image_label.getHeight(), Image.SCALE_SMOOTH);
			ImageIcon imageIcon = new ImageIcon(dimg);
			image_label.setIcon(imageIcon);
		}catch(Exception e) {
			System.out.println("error: "+ e );
		}
	}
}
