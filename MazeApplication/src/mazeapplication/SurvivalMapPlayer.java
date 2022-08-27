package mazeapplication;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;

public class SurvivalMapPlayer {
	private static Rectangle startBounds = null;
	private static JLabel playerLabel = null;

	public static void InitializePlayerStart() {
		SurvivalMapPlayer.playerLabel = CurrentSurvivalMapData.getPlayerStart();
		playerLabel.setBorder(new LineBorder(Color.black));
		startBounds = playerLabel.getBounds();
	}
	
	public static void MoveUp() {
		Rectangle possibleMove = new Rectangle(playerLabel.getX(), playerLabel.getY()-1, playerLabel.getWidth(), playerLabel.getHeight());
		if(DoesntHitWallOrExitMapArea(possibleMove)) {
			playerLabel.setBounds(playerLabel.getX(), playerLabel.getY()-1, playerLabel.getWidth(), playerLabel.getHeight());
		}
	}
	
	public static void MoveDown() {
		Rectangle possibleMove = new Rectangle(playerLabel.getX(), playerLabel.getY()+1, playerLabel.getWidth(), playerLabel.getHeight());
		if(DoesntHitWallOrExitMapArea(possibleMove)) {
			playerLabel.setBounds(playerLabel.getX(), playerLabel.getY()+1, playerLabel.getWidth(), playerLabel.getHeight());
		}
	}
	
	public static void MoveRight() {
		Rectangle possibleMove = new Rectangle(playerLabel.getX()+1, playerLabel.getY(), playerLabel.getWidth(), playerLabel.getHeight());
		if(DoesntHitWallOrExitMapArea(possibleMove)) {
			playerLabel.setBounds(playerLabel.getX()+1, playerLabel.getY(), playerLabel.getWidth(), playerLabel.getHeight());
		}
	}
	
	public static void MoveLeft() {
		Rectangle possibleMove = new Rectangle(playerLabel.getX()-1, playerLabel.getY(), playerLabel.getWidth(), playerLabel.getHeight());
		if(DoesntHitWallOrExitMapArea(possibleMove)) {
			playerLabel.setBounds(playerLabel.getX()-1, playerLabel.getY(), playerLabel.getWidth(), playerLabel.getHeight());
		}
	}
	
	private static boolean DoesntHitWallOrExitMapArea(Rectangle possibleMove) {
		LinkedList<SurvivalMapWall> wallList = CurrentSurvivalMapData.getMapWalls();
		for(int i = 0; i < wallList.size(); i++) {
			if(wallList.get(i).getWall().getBounds().intersects(possibleMove)){
				return false;
			}
		}
		if(!CurrentSurvivalMapData.getMapBounds().contains(possibleMove)) {
			return false;
		}
		return true;
	}
	
	public static JLabel getPlayerLabel() {
		return playerLabel;
	}

	public static void setPlayerLabel(JLabel playerLabel) {
		SurvivalMapPlayer.playerLabel = playerLabel;
	}
	
	public static void ResetPlayer() {
		CurrentSurvivalMapData.getPlayerStart().setBounds(startBounds);
		CurrentSurvivalMapData.getPlayerStart().setBorder(null);
	}
}
