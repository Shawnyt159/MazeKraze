package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JLabel;

public class CurrentSurvivalMapData {
	private static  int gridSize = 0;
	private static JLabel playerStart = null;
	private static LinkedList<SurvivalMapWall> mapWalls = new LinkedList<SurvivalMapWall>();
	private static Color background = Color.white;
	private static LinkedList<SurvivalMapEnemySpawner> enemySpawnerList = new LinkedList<SurvivalMapEnemySpawner>();
	private static String longestTime = "";
	private static HashMap<Point, Rectangle> gridMap = new HashMap<Point, Rectangle>();
	private static Rectangle mapBounds = new Rectangle(0,0,720, 600);
	
	public static int getGridSize() {
		return gridSize;
	}
	public static JLabel getPlayerStart() {
		return playerStart;
	}
	public static LinkedList<SurvivalMapWall> getMapWalls() {
		return mapWalls;
	}
	public static Color getBackground() {
		return background;
	}
	public static LinkedList<SurvivalMapEnemySpawner> getEnemySpawnerList() {
		return enemySpawnerList;
	}
	public static String getLongestTime() {
		return longestTime;
	}
	public static void setGridSize(int gridSize) {
		CurrentSurvivalMapData.gridSize = gridSize;
	}
	public static void setPlayerStart(JLabel playerStart) {
		CurrentSurvivalMapData.playerStart = playerStart;
	}
	public static void setMapWalls(LinkedList<SurvivalMapWall> mapWalls) {
		CurrentSurvivalMapData.mapWalls = mapWalls;
	}
	public static void setBackground(Color background) {
		CurrentSurvivalMapData.background = background;
	}
	public static void setEnemySpawnerList(LinkedList<SurvivalMapEnemySpawner> enemySpawnerList) {
		CurrentSurvivalMapData.enemySpawnerList = enemySpawnerList;
	}
	public static void setLongestTime(String longestTime) {
		CurrentSurvivalMapData.longestTime = longestTime;
	}
	public static void ClearData() {
		gridSize = 0;
		if(playerStart != null) {
			SurvivalMapDesignWindow.getCollectionMap().remove(playerStart);
		}
		playerStart = null;
		for(int i = 0; i < mapWalls.size(); i++) {
			SurvivalMapDesignWindow.getCollectionMap().remove(mapWalls.get(i).getWall());
		}
		mapWalls.clear();
		background = Color.white;
		for(int i = 0; i < enemySpawnerList.size(); i++) {
			SurvivalMapDesignWindow.getCollectionMap().remove(enemySpawnerList.get(i).getSpawnerLabel());
		}
		enemySpawnerList.clear();
		SurvivalMapDesignWindow.getCollectionMap().repaint();
		longestTime = "";
	}
	
	public static void MakeCollectionGridMap(int startX, int startY, int endX, int endY, int space) {
		HashMap<Point, Rectangle> newGridMap = new HashMap<Point, Rectangle>();
		for(int x = startX; x < endX; x += space) {
			for(int y = startY; y < endY; y += space) {
				Point gridLeftCorner = new Point(x,y);
				Rectangle gridRectangle = new Rectangle(x,y,space,space);
				newGridMap.put(gridLeftCorner, gridRectangle);
			}
		}
		gridMap = newGridMap;
	}
	
	public static Point getCorrectCollectionGridMapRectangleLeftCorner(Point userPosition) {
		for(Map.Entry<Point, Rectangle> entry : gridMap.entrySet()) {
			if(entry.getValue().contains(userPosition)) {
				return entry.getKey();
			}
		}
		return null;
	}
	public static Rectangle getMapBounds() {
		return mapBounds;
	}
	public static void setMapBounds(Rectangle mapBounds) {
		CurrentSurvivalMapData.mapBounds = mapBounds;
	}
	
}
