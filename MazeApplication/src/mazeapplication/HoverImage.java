package mazeapplication;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HoverImage {
	private static int height;
	private static int width;
	private static int z;
	private static String imageFile;
	private static JLabel hoverLabel;
	private static GetFileAttributes attributesGetter = new GetFileAttributes();
	private static SetImageToLabel images = new SetImageToLabel();
	
	public static void AddLabelToMazePanel(JPanel mazePanel, String itemName) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null) {
			hoverLabel = new JLabel("");
			getAttributes(itemName);
			if(height%2 == 0) {
				z = height/2;
			}
			else {
				z = height-2;
			}
			hoverLabel.setBounds((int) coordinates.getX()-z, (int) coordinates.getY()-z, width, height);
			images.set_image_to_label(hoverLabel, "/images/" + imageFile);
			mazePanel.add(hoverLabel);
			hoverLabel.setVisible(true);
		}
	}
	
	public static void AddLabelToMazePanel(JPanel mazePanel, int height, int width, String imageFile) {
		Point coordinates = mazePanel.getMousePosition();
		int x = (int) coordinates.getX() - (width / 2);
		int y = (int) coordinates.getY() - (height / 2);
		HoverImage.height = height;
		HoverImage.width = width;
		if(coordinates != null) {
			hoverLabel = new JLabel("");
			hoverLabel.setBounds(x, y, width, height);
			images.set_image_to_label(hoverLabel, imageFile);
			mazePanel.add(hoverLabel);
			hoverLabel.setVisible(true);
			HoverImage.imageFile = imageFile;
		}
	}
	
	public static void AddEnemyEndLocationToMazePanelVertical(JPanel mazePanel, JLabel enemy, String itemName) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null) {
			hoverLabel = new JLabel("");
			getAttributes(itemName);
			int x = enemy.getX();
			int y = (int) coordinates.getY();
			hoverLabel.setBounds(x, y, width, height);
			images.set_image_to_label(hoverLabel, "/images/" + imageFile);
			mazePanel.add(hoverLabel);
			hoverLabel.setVisible(true);
		}
	}
	
	public static void AddEnemyEndLocationToMazePanelHorizontal(JPanel mazePanel, JLabel enemy, String itemName) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null) {
			hoverLabel = new JLabel("");
			getAttributes(itemName);
			int x = (int) coordinates.getX();
			int y = enemy.getY();
			hoverLabel.setBounds(x, y, width, height);
			images.set_image_to_label(hoverLabel, "/images/" + imageFile);
			mazePanel.add(hoverLabel);
			hoverLabel.setVisible(true);
		}
	}
	
	public static void MoveLabelInMazePanel(JPanel mazePanel) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null && hoverLabel != null) {
			hoverLabel.setBounds((int) coordinates.getX()-z, (int) coordinates.getY()-z, width, height);
		}
	}
	
	public static void MoveEnemyLabelInMazePanel(JPanel mazePanel) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null && hoverLabel != null) {
			int x = (int) coordinates.getX() - (width / 2);
			int y = (int) coordinates.getY() - (height / 2);
			hoverLabel.setBounds(x, y, width, height);
		}
	}
	
	public static void MoveEndLocationInMazePanelVertical(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null && hoverLabel != null) {
			int x = enemy.getX();
			int y = (int) coordinates.getY();
			hoverLabel.setBounds(x, y, width, height);
		}
	}
	
	public static void MoveEndLocationInMazePanelHorizontal(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null && hoverLabel != null) {
			int x = (int) coordinates.getX();
			int y = enemy.getY();
			hoverLabel.setBounds(x, y, width, height);
		}
	}
	
	public static void RemoveLabelFromMazePanel(JPanel mazePanel) {
		mazePanel.remove(hoverLabel);
		mazePanel.repaint();
		hoverLabel = null;
	}
	
	private static void getAttributes(String name) {
		attributesGetter.Get(name);
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		HoverImage.height = height;
	}

	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		HoverImage.width = width;
	}

	public static String getImageFile() {
		return imageFile;
	}

	public static void setImageFile(String imageFile) {
		HoverImage.imageFile = imageFile;
	}
	
	
}
