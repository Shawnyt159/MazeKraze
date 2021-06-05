package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;

public class LevelPlayLoadedMazeAttributes {
	private static JLabel playerStart = null;
	private static HashMap<Point,Integer> q1 = new HashMap<Point,Integer>();
	private static HashMap<Point,Integer> q2 = new HashMap<Point,Integer>();
	private static HashMap<Point,Integer> q3 = new HashMap<Point,Integer>();
	private static HashMap<Point,Integer> q4 = new HashMap<Point,Integer>();
	private static JLabel endLocation = null;
	private static HashMap<Point,Color> colorMap = new HashMap<Point,Color>();
	private static Enemy enemy1 = null;
	private static Enemy enemy2 = null;
	private static Enemy enemy3 = null;
	private static Color backgroundColor = null;
	private static LinkedList<DecorationNode> decorationList = new LinkedList<DecorationNode>();
	private static boolean blackoutSetting = false;
	
	public static JLabel getPlayerStart() {
		return playerStart;
	}
	public static void setPlayerStart(JLabel playerStart) {
		LevelPlayLoadedMazeAttributes.playerStart = playerStart;
	}
	public static HashMap<Point, Integer> getQ1() {
		return q1;
	}
	public static void setQ1(HashMap<Point, Integer> q1) {
		LevelPlayLoadedMazeAttributes.q1 = q1;
	}
	public static HashMap<Point, Integer> getQ2() {
		return q2;
	}
	public static void setQ2(HashMap<Point, Integer> q2) {
		LevelPlayLoadedMazeAttributes.q2 = q2;
	}
	public static HashMap<Point, Integer> getQ3() {
		return q3;
	}
	public static void setQ3(HashMap<Point, Integer> q3) {
		LevelPlayLoadedMazeAttributes.q3 = q3;
	}
	public static HashMap<Point, Integer> getQ4() {
		return q4;
	}
	public static void setQ4(HashMap<Point, Integer> q4) {
		LevelPlayLoadedMazeAttributes.q4 = q4;
	}
	public static JLabel getEndLocation() {
		return endLocation;
	}
	public static LinkedList<DecorationNode> getDecorationList(){
		return decorationList;
	}
	public static void setDecorationList(LinkedList<DecorationNode> decorationList) {
		LevelPlayLoadedMazeAttributes.decorationList = decorationList;
	}
	public static void setEndLocation(JLabel endLocation) {
		LevelPlayLoadedMazeAttributes.endLocation = endLocation;
	}
	public static HashMap<Point, Color> getColorMap() {
		return colorMap;
	}
	public static void setColorMap(HashMap<Point, Color> colorMap) {
		LevelPlayLoadedMazeAttributes.colorMap = colorMap;
	}
	public static Enemy getEnemy1() {
		return enemy1;
	}
	public static boolean getBlackoutSetting() {
		return blackoutSetting;
	}
	
	public static void setEnemy1(Enemy enemy1) {
		LevelPlayLoadedMazeAttributes.enemy1 = enemy1;
	}
	public static Enemy getEnemy2() {
		return enemy2;
	}
	public static void setEnemy2(Enemy enemy2) {
		LevelPlayLoadedMazeAttributes.enemy2 = enemy2;
	}
	public static Enemy getEnemy3() {
		return enemy3;
	}
	public static void setEnemy3(Enemy enemy3) {
		LevelPlayLoadedMazeAttributes.enemy3 = enemy3;
	}
	public static Color getBackgroundColor() {
		return backgroundColor;
	}
	public static void setBackgroundColor(Color backgroundColor) {
		LevelPlayLoadedMazeAttributes.backgroundColor = backgroundColor;
	}
	public static void setBlackoutSetting(boolean blackoutSetting) {
		LevelPlayLoadedMazeAttributes.blackoutSetting = blackoutSetting;
	}
	
	public static void resetEverything() {
		playerStart = null;
		q1 = null;
		q2 = null;
		q3 = null;
		q4 = null;
		endLocation = null;
		colorMap = null;
		enemy1 = null;
		enemy2 = null;
		enemy3 = null;
		backgroundColor = null;
		decorationList = null;
		blackoutSetting = false;
	}
	/*
	 * Getting the correct quadrant map based on the users current location.
	 */
	public static HashMap<Point, Integer> GetCorrectQuadrantMap(Point coordinates){
		if(coordinates != null) {
			int x = (int) coordinates.getX();
			int y = (int) coordinates.getY();
			if(x <= 354) {
				if(y <= 324) {
					return q1;
				}
				else {
					return q3;
				}
			}
			else {
				if(y <= 324) {
					return q2;
				}
				else {
					return q4;
				}
			}
		}
		return null;
	}
	
}
