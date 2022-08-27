package mazeapplication;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

import javax.swing.JLabel;

public class SurvivalMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5044232525423130048L;
	private int gridSize;
	private JLabel playerStart;
	private LinkedList<SurvivalMapWall> mapWalls;
	private Color background;
	private LinkedList<SurvivalMapEnemySpawner> enemySpawnerList;
	private String longestTime;
	
	public SurvivalMap() {
		
	}

	public int getGridSize() {
		return gridSize;
	}

	public JLabel getPlayerStart() {
		return playerStart;
	}

	public LinkedList<SurvivalMapWall> getMapWalls() {
		return mapWalls;
	}

	public Color getBackground() {
		return background;
	}

	public LinkedList<SurvivalMapEnemySpawner> getEnemySpawnerList() {
		return enemySpawnerList;
	}

	public String getLongestTime() {
		return longestTime;
	}

	public void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	public void setPlayerStart(JLabel playerStart) {
		this.playerStart = playerStart;
	}

	public void setMapWalls(LinkedList<SurvivalMapWall> mapWalls) {
		this.mapWalls = mapWalls;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public void setEnemySpawnerList(LinkedList<SurvivalMapEnemySpawner> enemySpawnerList) {
		this.enemySpawnerList = enemySpawnerList;
	}

	public void setLongestTime(String longestTime) {
		this.longestTime = longestTime;
	}
	
}
