package mazeapplication;

import java.awt.Point;

import javax.swing.JLabel;

public class LevelPlayEnemyStartAndStopMazeFunctions {
	public static void StartEnemies() {

		Enemy enemy1 = LevelPlayLoadedMazeAttributes.getEnemy1();
		Enemy enemy2 = LevelPlayLoadedMazeAttributes.getEnemy2();
		Enemy enemy3 = LevelPlayLoadedMazeAttributes.getEnemy3();
		JLabel enemy1StartLocation = null;
		JLabel enemy1EndLocation = null;
		if(enemy1 != null) {
			enemy1StartLocation = enemy1.getStartLabel();
			enemy1EndLocation = enemy1.getEndLocation();
		}
		JLabel enemy2StartLocation = null;
		JLabel enemy2EndLocation = null;
		if(enemy2 != null) {
			enemy2StartLocation = enemy2.getStartLabel();
			enemy2EndLocation = enemy2.getEndLocation();
		}
		JLabel enemy3StartLocation = null;
		JLabel enemy3EndLocation = null;
		if(enemy3 != null) {
			enemy3StartLocation = enemy3.getStartLabel();
			enemy3EndLocation = enemy3.getEndLocation();
		}
		
		if(enemy1StartLocation != null) {
			if(enemy1EndLocation != null) {
				enemy1EndLocation.setVisible(false);
				Point endPoint = new Point(enemy1EndLocation.getX(), enemy1EndLocation.getY());
				LevelPlayMoveEnemy.Move(enemy1StartLocation, endPoint, enemy1.GetSpeed());
			}
		}
		if(enemy2StartLocation != null) {
			if(enemy2EndLocation != null) {
				enemy2EndLocation.setVisible(false);
				Point endPoint = new Point(enemy2EndLocation.getX(), enemy2EndLocation.getY());
				LevelPlayMoveEnemy.Move(enemy2StartLocation, endPoint, enemy2.GetSpeed());
			}
		}
		if(enemy3StartLocation != null) {
			if(enemy3EndLocation != null) {
				enemy3EndLocation.setVisible(false);
				Point endPoint = new Point(enemy3EndLocation.getX(), enemy3EndLocation.getY());
				LevelPlayMoveEnemy.Move(enemy3StartLocation, endPoint, enemy3.GetSpeed());
			}
		}
	}
	
	public static void StopEnemies() {
		LevelPlayMoveEnemy.SetKeepGoingFalse();
		Enemy enemy1 = LevelPlayLoadedMazeAttributes.getEnemy1();
		Enemy enemy2 = LevelPlayLoadedMazeAttributes.getEnemy2();
		Enemy enemy3 = LevelPlayLoadedMazeAttributes.getEnemy3();
		if(enemy1 != null) {
			if(enemy1.getEndLocation() != null) {
				enemy1.getEndLocation().setVisible(true);
			}
		}
		if(enemy2 != null) {
			if(enemy2.getEndLocation() != null) {
				enemy2.getEndLocation().setVisible(true);
			}
		}
		if(enemy3 != null) {
			if(enemy3.getEndLocation() != null) {
				enemy3.getEndLocation().setVisible(true);
			}
		}
	}
}
