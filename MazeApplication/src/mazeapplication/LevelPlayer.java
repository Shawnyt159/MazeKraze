package mazeapplication;


import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LevelPlayer {
	// Global to set an image to a JLabel. 
	private static SetImageToLabel images = new SetImageToLabel();
	private static int playerZ = 5;
	private static int down = 1;
	private static int up = 2;
	private static int left = 3;
	private static int right = 4;
	public static void SetPlayerImage(JLabel player) {
		images.set_image_to_label(player, "/images/O.png");
	}
	
	public static void MoveDown(JLabel player) {
		Point user = new Point(player.getX(), player.getY());
		if(CheckValidMove(user, down, player) == true) {
			player.setBounds(player.getX(), player.getY()+1, playerZ, playerZ);
		}
		if(BlackoutMaze.isActive() == true) {
			MazeLevelPlayGUI.getMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			JOptionPane.showMessageDialog(null, "You Win!");
		}
	}
	
	public static void MoveUp(JLabel player) {
		Point user = new Point(player.getX(), player.getY());
		if(CheckValidMove(user, up, player) == true) {
			player.setBounds(player.getX(), player.getY()-1, playerZ, playerZ);
		}
		if(BlackoutMaze.isActive() == true) {
			MazeLevelPlayGUI.getMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			JOptionPane.showMessageDialog(null, "You Win!");
		}
	}
	
	public static void MoveLeft(JLabel player) {
		Point user = new Point(player.getX(), player.getY());
		if(CheckValidMove(user, left, player)) {
			player.setBounds(player.getX()-1, player.getY(), playerZ, playerZ);
		}
		if(BlackoutMaze.isActive() == true) {
			MazeLevelPlayGUI.getMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			JOptionPane.showMessageDialog(null, "You Win!");
		}
	}
	
	public static void MoveRight(JLabel player) {
		Point user = new Point(player.getX(), player.getY());
		if(CheckValidMove(user, right, player)) {
			player.setBounds(player.getX()+1, player.getY(), playerZ, playerZ);
		}
		if(BlackoutMaze.isActive() == true) {
			MazeLevelPlayGUI.getMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			JOptionPane.showMessageDialog(null, "You Win!");
		}
	}
	
	private static boolean CheckValidMove(Point user, int direction, JLabel player) {
		HashMap<Point, Integer> qmap = LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(user);
		if(CheckForOutOfBounds(user, direction) == false) {
			return false;
		}
		if(CheckQuadrantMapForCollision(qmap, user, direction) == false) {
			return false;
		}
		
		LinkedList<DecorationNode> decorationList = LevelPlayLoadedMazeAttributes.getDecorationList();
		if(decorationList != null) {
			ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
			while(iterator.hasNext()) {
				if(CollisionWithDecoration(player, iterator.next().getDecorationNode(), direction) == true) {
					return false;
				}
			}
		}
		
		if(direction == down) {
			Point testCoordinates = new Point((int) user.getX(), (int) user.getY() + (playerZ+4));
			if(qmap == LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(testCoordinates)) {
				return true;
			}
			else {
				if(CheckQuadrantMapForCollision(LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(testCoordinates), user, direction) == false) {
					return false;
				}
			}
		}
		else if(direction == right) {
			Point testCoordinates = new Point((int) user.getX() + (playerZ+4), (int) user.getY());
			if(qmap == LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(testCoordinates)) {
				return true;
			}
			else {
				if(CheckQuadrantMapForCollision(LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(testCoordinates), user, direction) == false) {
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean CheckQuadrantMapForCollision(HashMap<Point, Integer> qmap, Point user, int direction) {
		for(Map.Entry<Point, Integer> entery : qmap.entrySet()) {
			Point coordinate = entery.getKey();
			int z = entery.getValue();
			if(z == 1) {
			}
			else if(z == 2) {
				z = z-1;
			}
			else if(z == 3) {
				z = z-2;
			}
			else if(z == 4) {
				z = z-3;
			}
			else if (z == 5) {
				z = z-3;
			}
			else if(z == 6) {
				z = z-4;
			}
			else if(z == 7) {
				z = z-4;
			}
			else if(z == 8) {
				z = z-5;
			}
			else if(z == 9) {
				z = z-6;
			}
			else if(z == 10) {
				z = z-7;
			}
			else {
				z = z-5;
			}
			if(direction == down) {
				if(CheckNoCollisionDownOnPoint(coordinate, z, user) == false) {
					return false;
				}
			}
			else if(direction == up) {
				if(CheckNoCollisionUpOnPoint(coordinate, z, user) == false) {
					return false;
				}
			}
			else if(direction == left) {
				if(CheckNoCollisionLeftOnPoint(coordinate, z, user) == false) {
					return false;
				}
			}
			else if(direction == right){
				if(CheckNoCollisionRightOnPoint(coordinate, z, user) == false){
					return false;
				}
			}
		}
		return true;
	}
	
	private static boolean CheckNoCollisionDownOnPoint(Point wallCoordinate, int z, Point user) {
		Point c1 = new Point((int) wallCoordinate.getX()+z, (int) wallCoordinate.getY()-z);
		Point c3 = new Point((int) wallCoordinate.getX()-z, (int) wallCoordinate.getY()-z);
		Point uc2 = new Point((int) user.getX()+playerZ, (int) user.getY()+playerZ);
		Point uc4 = new Point((int) user.getX(), (int) user.getY()+playerZ);
		if((int) uc4.getY()+1 == (int) c1.getY()){
			if((int) uc4.getX() >= (int) c3.getX() && (int) uc4.getX() <= (int) c1.getX()) {
				return false;
			}
			else if((int) uc2.getX() >= (int) c3.getX() && (int) uc2.getX() <= (int) c1.getX()) {
				return false;
			}
			else if((int) c1.getX() >= (int) uc4.getX() && (int) c1.getX() <= uc2.getX()) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean CheckNoCollisionUpOnPoint(Point wallCoordinate, int z, Point user) {
		Point c2 = new Point((int) wallCoordinate.getX() + z, (int) wallCoordinate.getY()+z);
		Point c4 = new Point ((int) wallCoordinate.getX()-z, (int) wallCoordinate.getY()+z);
		Point uc1 = new Point((int) user.getX()+playerZ, (int) user.getY());
		Point uc3 = new Point((int) user.getX(), (int) user.getY());
		if((int) uc1.getY()-1 == (int) c2.getY()) {
			if((int) uc1.getX() >= (int) c4.getX() && (int) uc1.getX() <= (int) c2.getX()) {
				return false;
			}
			else if((int) uc3.getX() >= (int) c4.getX() && (int) uc3.getX() <= (int) c2.getX()) {
				return false;
			}
			else if((int) c2.getX() >= (int) uc3.getX() && (int) c2.getX() <= (int) uc1.getX()) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean CheckNoCollisionLeftOnPoint(Point wallCoordinate, int z, Point user) {
		Point c1 = new Point((int) wallCoordinate.getX()+z, (int) wallCoordinate.getY()-z);
		Point c2 = new Point((int) wallCoordinate.getX() + z, (int) wallCoordinate.getY()+z);
		Point uc3 = new Point((int) user.getX(), (int) user.getY());
		Point uc4 = new Point((int) user.getX(), (int) user.getY()+playerZ);
		if((int) uc3.getX()-1 == (int) c1.getX()) {
			if((int) uc3.getY() >= (int) c1.getY() && (int) uc3.getY() <= (int) c2.getY()) {
				return false;
			}
			else if((int) uc4.getY() >= (int) c1.getY() && (int) uc4.getY() <= (int) c2.getY()) {
				return false;
			}
			else if((int) c1.getY() >= (int) uc3.getY() && (int) c1.getY() <= uc4.getY()) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean CheckNoCollisionRightOnPoint(Point wallCoordinate, int z, Point user) {
		Point c3 = new Point((int) wallCoordinate.getX()-z, (int) wallCoordinate.getY()-z);
		Point c4 = new Point((int) wallCoordinate.getX()-z, (int) wallCoordinate.getY()+z);
		Point uc1 = new Point((int) user.getX()+playerZ, (int) user.getY());
		Point uc2 = new Point((int) user.getX()+playerZ, (int) user.getY()+playerZ);
		if((int) uc1.getX()+1 == (int) c3.getX()) {
			if((int) uc1.getY() >= (int) c3.getY() && (int) uc1.getY() <= c4.getY()) {
				return false;
			}
			else if((int) uc2.getY() >= (int) c3.getY() && (int) uc2.getY() <= (int) c4.getY()) {
				return false;
			}
			else if((int) c3.getY() >= (int) uc1.getY() && (int) c3.getY() <= (int) uc2.getY()) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean CollisionWithDecoration(JLabel player, JLabel decoration, int direction) {
		int height = player.getHeight();
		int width = player.getWidth();
		int x = player.getX();
		int y = player.getY();
		if(direction == up) {
			y = y-1;
		}
		else if(direction == down) {
			y = y+1;
		}
		else if(direction == left) {
			x = x-1;
		}
		else if(direction == right) {
			x = x+1;
		}
		Rectangle playerRect = new Rectangle(x,y,width,height);
		Area playerArea = new Area(playerRect);
		return CheckCollisionWithDecoration(playerArea, decoration);
	}
	
	private static boolean CheckCollisionWithDecoration(Area area1, JLabel decoration) {
		Area decorationArea = new Area(decoration.getBounds());
		return decorationArea.intersects(area1.getBounds());
	}
	
	private static boolean CheckForOutOfBounds(Point player, int direction) {
		player = new Point((int) player.getX()+2, (int) player.getY()+2);
		if(direction == down) {
			if((int) player.getY()+1 == 648) {
				return false;
			}
			return true;
		}
		else if(direction == up) {
			if((int) player.getY()-1 == 0) {
				return false;
			}
			return true;
		}
		else if(direction == left) {
			if((int) player.getX()-1 == 0) {
				return false;
			}
			return true;
		}
		else if(direction == right) {
			if((int) player.getX()+1 == 708) {
				return false;
			}
			return true;
		}
		return false;
	}
	
	private static boolean CheckForWin(JLabel player) {
		JLabel endLocation = LevelPlayLoadedMazeAttributes.getEndLocation();
		Area a = new Area(endLocation.getBounds());
		Area b = new Area(player.getBounds());
		return a.intersects(b.getBounds2D());
	}
}
