package mazeapplication;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;

public class PlayerObject {
	JLabel player;
	boolean won = false;

	private static int down = 1;
	private static int up = 2;
	private static int left = 3;
	private static int right = 4;
	SetImageToLabel images = new SetImageToLabel();
	
	public PlayerObject(JLabel player) {
		this.player = player;
	}
	public void setPlayer(JLabel player) {
		this.player = player;
	}
	public JLabel getPlayer() {
		return this.player;
	}
	public void setPlayerImage(String playerImageLocation) {
		images.set_image_to_label(player, playerImageLocation);
	}
	public void MoveDown() {
		Point user = new Point(player.getX(), player.getY());
		Rectangle playerAfterMoveBounds = new Rectangle(player.getX(), player.getY()+1, player.getHeight(), player.getWidth());
		if(CheckNoCollision(playerAfterMoveBounds) == true) {
			if(CheckForOutOfBounds(user, down) == true) {
				player.setBounds(player.getX(), player.getY()+1, player.getWidth(), player.getHeight());
			}
		}
		CheckEnteringPortal(player);
		if(BlackoutMaze.isActive() == true) {
			MazeDesignMainGUI.GetMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			if(won == false) {
				ButtonFunctions.getWinMessage().DisplayMessage();
			}
			won = true;
		}
	}
	
	public void MoveUp() {
		Point user = new Point(player.getX(), player.getY());
		Rectangle playerAfterMoveBounds = new Rectangle(player.getX(), player.getY()-1, player.getHeight(), player.getWidth());
		if(CheckNoCollision(playerAfterMoveBounds) == true) {
			if(CheckForOutOfBounds(user, up) == true) {
				player.setBounds(player.getX(), player.getY()-1, player.getWidth(), player.getHeight());
			}
		}
		CheckEnteringPortal(player);
		if(BlackoutMaze.isActive() == true) {
			MazeDesignMainGUI.GetMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			if(won == false) {
				ButtonFunctions.getWinMessage().DisplayMessage();
			}
			won = true;
		}
	}
	
	public void MoveLeft() {
		Point user = new Point(player.getX(), player.getY());
		Rectangle playerAfterMoveBounds = new Rectangle(player.getX()-1, player.getY(), player.getHeight(), player.getWidth());
		if(CheckNoCollision(playerAfterMoveBounds) == true) {
			if(CheckForOutOfBounds(user, left) == true) {
				player.setBounds(player.getX()-1, player.getY(), player.getWidth(), player.getHeight());
			}
		}
		CheckEnteringPortal(player);
		if(BlackoutMaze.isActive() == true) {
			MazeDesignMainGUI.GetMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			if(won == false) {
				ButtonFunctions.getWinMessage().DisplayMessage();
			}
			won = true;
		}
		CheckEnteringPortal(player);
	}
	
	public void MoveRight() {
		Point user = new Point(player.getX(), player.getY());
		Rectangle playerAfterMoveBounds = new Rectangle(player.getX()+1, player.getY(), player.getHeight(), player.getWidth());
		if(CheckNoCollision(playerAfterMoveBounds) == true) {
			if(CheckForOutOfBounds(user, right) == true) {
				player.setBounds(player.getX()+1, player.getY(), player.getWidth(), player.getHeight());
			}
		}
		CheckEnteringPortal(player);
		if(BlackoutMaze.isActive() == true) {
			MazeDesignMainGUI.GetMazePanel().repaint();
		}
		if(CheckForWin(player)) {
			if(won == false) {
				ButtonFunctions.getWinMessage().DisplayMessage();
			}
			won = true;
		}
		CheckEnteringPortal(player);
	}
	
	/*
	 * Checking collision with possible points based on movement.
	 */
	private boolean CheckNoCollision(Rectangle playerBounds) {
		// Enlarging the player rectangle by 6 times from the center of the player rectangle.
		Rectangle enlargedPlayerBounds = new Rectangle((int)playerBounds.getCenterX() -15, (int)playerBounds.getCenterY()-15, 30, 30);
		// Every point in the enlargedPlayerBounds.
		Point[][] pointsToCheck = new Point[30][30];
		
		// Adding every point to the correct position in the pointsToCheck two dimensional array. 
		int x = (int) enlargedPlayerBounds.getX();
		int y = (int) enlargedPlayerBounds.getY();
		int maxX = x + 30;
		int maxY = y + 30;
		int xCounter = 0; 
		int yCounter = 0;
		int yIncrement = y;
		for(; x < maxX; x++) {
			for(; yIncrement < maxY; yIncrement++) {
				pointsToCheck[xCounter][yCounter] = new Point(x,yIncrement);
				yCounter++;
			}
			yIncrement = y;
			xCounter++;
			yCounter = 0;
		}
		// Making sure there is no collision between the players movement and a wall.
		boolean noCollision = CheckNoCollisionInQuadrantMaps(pointsToCheck, playerBounds);
		
		if(noCollision == false) {
			return false;
		}
		
		/*
		 * Where the player will check collision with the decorations. 
		 */
		noCollision = CheckNoCollisionWithDecorations(playerBounds);
		
		// If there is no collision. 
		if(noCollision == true) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean CheckNoCollisionInQuadrantMaps(Point[][] pointsToCheck, Rectangle playerBounds) {
		for(int x = 0; x < 30; x++) {
			for(int y = 0; y < 30; y++) {
				Point currentPoint = pointsToCheck[x][y];
				HashMap<Point, Integer> qMap = ButtonFunctions.GetCorrectQuadrantMap(currentPoint);
				if(qMap == null) {
					continue;
				}
				else if(qMap.containsKey(currentPoint)) {
					int z = qMap.get(currentPoint);
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
						z = z-4;
					}
					else if(z == 9) {
						z = z-6;
					}
					else if(z == 10) {
						z = z-5;
					}
					else {
						z = z-5;
					}
					
					Rectangle wallBounds = new Rectangle((int) currentPoint.getX()-z, (int) currentPoint.getY()-z, z*2,z*2);
					if(z == 1) {
						wallBounds = new Rectangle((int) currentPoint.getX() - z, (int) currentPoint.getY() -z, z+1, z+1);
					}
					if(wallBounds.intersects(playerBounds) == true) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/*
	 * Iterating through every decoration to see if the player intersects with them.
	 */
	private boolean CheckNoCollisionWithDecorations(Rectangle playerBounds) {
		LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
		for(int i = 0; i < decorationList.size(); i++) {
			if(playerBounds.intersects(decorationList.get(i).getDecorationNode().getBounds()) == true) {
				return false;
			}
		}
		return true;
	}
	
	/*
	 * Checking if the player will be entering a portal. 
	 */
	private void CheckEnteringPortal(JLabel player) {
		LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
		Rectangle playerBounds = player.getBounds();
		for(int i = 0; i < portalList.size(); i++) {
			PortalNode currentPortal = portalList.get(i);
			if(currentPortal.getLinkStart() != null && currentPortal.getLinkEnd() != null) {
				Rectangle portalStartRectangle = currentPortal.getLinkStart().getBounds();
				Rectangle portalEndRectangle = currentPortal.getLinkEnd().getBounds();
				if(playerBounds.intersects(portalStartRectangle) == true) {
					MovePlayerThroughPortal(portalEndRectangle, currentPortal.getLinkEndExitSide(), player);
				}
				else if(playerBounds.intersects(portalEndRectangle) == true) {
					MovePlayerThroughPortal(portalStartRectangle, currentPortal.getLinkStartExitSide(), player);
				}
			}
		}
	}
	
	
	/*
	 * Updates the player bounds when they move through the portal.
	 */
	private void MovePlayerThroughPortal(Rectangle exitPortalBounds, char exitDirection, JLabel player) {
		// North.
		if(exitDirection == 'N') {
			player.setBounds((int) exitPortalBounds.getX() + 12, (int) exitPortalBounds.getY() - 7, player.getWidth(), player.getHeight());
		}
		// South.
		else if(exitDirection == 'S') {
			player.setBounds((int) exitPortalBounds.getX() + 12, (int) exitPortalBounds.getY() + 32, player.getWidth(), player.getHeight());
		}
		// West.
		else if(exitDirection == 'W') {
			player.setBounds((int) exitPortalBounds.getX() - 7, (int) exitPortalBounds.getY() + 12, player.getWidth(), player.getHeight());
		}
		// East.
		else if(exitDirection == 'E') {
			player.setBounds((int) exitPortalBounds.getX() + 32, (int) exitPortalBounds.getY() + 12, player.getWidth(), player.getHeight());
		}
	}
	
	/*
	 * Checking if the player is out of bounds.
	 */
	private boolean CheckForOutOfBounds(Point player, int direction) {
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
	
	/*
	 * Checking if the player has won the maze.
	 */
	private boolean CheckForWin(JLabel player) {
		JLabel endLocation = ButtonFunctions.GetEndLabel();
		Area a = new Area(endLocation.getBounds());
		Area b = new Area(player.getBounds());
		return a.intersects(b.getBounds2D());
	}
	public boolean isWon() {
		return won;
	}
	public void setWon(boolean won) {
		this.won = won;
	}
}
