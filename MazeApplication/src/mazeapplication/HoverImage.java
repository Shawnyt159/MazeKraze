package mazeapplication;



import java.awt.Point;
import java.util.LinkedList;

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
	private static JLabel collectionMapHoverItem;

	public static void AddLabelToMazePanel(JPanel mazePanel, String itemName) {
		if (((MazePanel) mazePanel).getDecorationGridActive() == true) {
			Point coordinates = mazePanel.getMousePosition();
			Point leftCorner = ButtonFunctions.GetCorrectLeftCornerValue(coordinates);
			if (coordinates != null) {
				hoverLabel = new JLabel("");
				getAttributes(itemName);
				if (leftCorner == null) {
					hoverLabel.setBounds(0, 0, width, height);
				} else {
					hoverLabel.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), width, height);
				}
				images.set_image_to_label(hoverLabel, "/images/" + imageFile);
				mazePanel.add(hoverLabel);
				hoverLabel.setVisible(true);
			}
		} else {
			Point coordinates = mazePanel.getMousePosition();
			if (coordinates != null) {
				hoverLabel = new JLabel("");
				getAttributes(itemName);  
				if (height % 2 == 0) {
					z = height / 2;
				} else {
					z = height - 2;
				}
				hoverLabel.setBounds((int) coordinates.getX() - z, (int) coordinates.getY() - z, width, height);
				images.set_image_to_label(hoverLabel, "/images/" + imageFile);
				mazePanel.add(hoverLabel);
				hoverLabel.setVisible(true);
			}
		}
	}

	public static void AddLabelToMazePanel(JPanel mazePanel, int height, int width, String imageFile) {
		Point coordinates = mazePanel.getMousePosition();
		if(coordinates != null) {
			int x = (int) coordinates.getX() - (width / 2);
			int y = (int) coordinates.getY() - (height / 2);
			HoverImage.height = height;
			HoverImage.width = width;
			z = height/2;
			if (coordinates != null) {
				hoverLabel = new JLabel("");
				hoverLabel.setBounds(x, y, width, height);
				images.set_image_to_label(hoverLabel, imageFile);
				mazePanel.add(hoverLabel);
				hoverLabel.setVisible(true);
				HoverImage.imageFile = imageFile;
				mazePanel.repaint();
			}
		}
	}

	public static void AddEnemyEndLocationToMazePanelVertical(JPanel mazePanel, JLabel enemy, String itemName) {
		Point coordinates = mazePanel.getMousePosition();
		if (coordinates != null) {
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
		if (coordinates != null) {
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
		if (((MazePanel) mazePanel).getDecorationGridActive() == true) {
			Point leftCorner = ButtonFunctions.GetCorrectLeftCornerValue(mazePanel.getMousePosition());
			if (leftCorner != null && hoverLabel != null) {
				hoverLabel.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), width, height);
			}
		} else {
			Point coordinates = mazePanel.getMousePosition();
			if (coordinates != null && hoverLabel != null) {
				hoverLabel.setBounds((int) coordinates.getX() - z, (int) coordinates.getY() - z, width, height);
			}
		}
	}

	public static void MoveEnemyLabelInMazePanel(JPanel mazePanel) {
		Point coordinates = mazePanel.getMousePosition();
		if (coordinates != null && hoverLabel != null) {
			int x = (int) coordinates.getX() - (width / 2);
			int y = (int) coordinates.getY() - (height / 2);
			hoverLabel.setBounds(x, y, width, height);
		}
	}

	public static void MoveEndLocationInMazePanelVertical(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		if (coordinates != null && hoverLabel != null) {
			int x = enemy.getX();
			int y = (int) coordinates.getY();
			hoverLabel.setBounds(x, y, width, height);
		}
	}

	public static void MoveEndLocationInMazePanelHorizontal(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		if (coordinates != null && hoverLabel != null) {
			int x = (int) coordinates.getX();
			int y = enemy.getY();
			hoverLabel.setBounds(x, y, width, height);
		}
	}

	public static void RemoveLabelFromMazePanel(JPanel mazePanel) {
		if(hoverLabel != null) {
			mazePanel.remove(hoverLabel);
			mazePanel.repaint();
			hoverLabel = null;
		}
	}
	
	/*
	 * COLLECTION MAP.
	 */
	public static void AddItemToCollectionMap(JPanel collectionMap, String itemName, int size) {
		if(collectionMap.getMousePosition() == null) {
			return;
		}
		Point leftCorner = CurrentSurvivalMapData.getCorrectCollectionGridMapRectangleLeftCorner(collectionMap.getMousePosition());
		if(leftCorner == null) {
			return;
		}
		String filePath = attributesGetter.GetItemFilePath(itemName);
		collectionMapHoverItem = new JLabel();
		collectionMapHoverItem.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), size, size);
		images.set_image_to_label(collectionMapHoverItem, "/images/" +filePath);
		collectionMap.add(collectionMapHoverItem);
		collectionMap.repaint();
	}
	
	public static void MoveItemInCollectionMap(JPanel collectionMap) {
		if(collectionMap.getMousePosition() == null) {
			return;
		}
		Point leftCorner = CurrentSurvivalMapData.getCorrectCollectionGridMapRectangleLeftCorner(collectionMap.getMousePosition());
		if(leftCorner == null) {
			return;
		}
		collectionMapHoverItem.setBounds((int) leftCorner.getX(),(int) leftCorner.getY(), collectionMapHoverItem.getWidth(), collectionMapHoverItem.getHeight());
		collectionMap.repaint();
	}
	
	public static void RemoveItemFromCollectionMap(JPanel collectionMap) {
		collectionMap.remove(collectionMapHoverItem);
		collectionMap.repaint();
	}
	
	public static JLabel PlaceItemInCollectionMap(JPanel collectionMap) {
		if(collectionMap.getMousePosition() != null) {
			JLabel itemToAdd = new JLabel();
			Point leftCorner = CurrentSurvivalMapData.getCorrectCollectionGridMapRectangleLeftCorner(collectionMap.getMousePosition());
			itemToAdd.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), collectionMapHoverItem.getWidth(), collectionMapHoverItem.getHeight());
			itemToAdd.setIcon(collectionMapHoverItem.getIcon());
			itemToAdd.setVisible(true);
			LinkedList<SurvivalMapWall> wallMap = CurrentSurvivalMapData.getMapWalls();
			JLabel itemToRemove = null;
			int indexToRemove = -1;
			for(int i = 0; i < wallMap.size(); i++) {
				if(wallMap.get(i).getWall().getBounds().equals(itemToAdd.getBounds())) {
					itemToRemove = wallMap.get(i).getWall();
					indexToRemove = i;
					break;
				}
			}
			if(itemToRemove != null) {
				collectionMap.remove(itemToRemove);
				wallMap.remove(indexToRemove);
			}
			collectionMap.add(itemToAdd);
			collectionMap.repaint();
			return itemToAdd;
		}
		return null;
	}
	
	/*
	 * Used when dragging. 
	 */
	public static JLabel PlaceItemInCollectionMap(JPanel collectionMap, Point mousePosition) {
		if(mousePosition != null) {
			JLabel itemToAdd = new JLabel();
			Point leftCorner = CurrentSurvivalMapData.getCorrectCollectionGridMapRectangleLeftCorner(mousePosition);
			itemToAdd.setBounds((int) leftCorner.getX(), (int) leftCorner.getY(), collectionMapHoverItem.getWidth(), collectionMapHoverItem.getHeight());
			itemToAdd.setIcon(collectionMapHoverItem.getIcon());
			itemToAdd.setVisible(true);
			LinkedList<SurvivalMapWall> wallMap = CurrentSurvivalMapData.getMapWalls();
			JLabel itemToRemove = null;
			int indexToRemove = -1;
			for(int i = 0; i < wallMap.size(); i++) {
				if(wallMap.get(i).getWall().getBounds().equals(itemToAdd.getBounds())) {
					itemToRemove = wallMap.get(i).getWall();
					indexToRemove = i;
					break;
				}
			}
			if(itemToRemove != null) {
				collectionMap.remove(itemToRemove);
				wallMap.remove(indexToRemove);
			}
			collectionMap.add(itemToAdd);
			collectionMap.repaint();
			return itemToAdd;
		}
		return null;
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
