package mazeapplication;

import java.awt.Point;

import javax.swing.JLabel;

public class EnemyStartAndStopMazeFunctions {

	public static void StartEnemies() {

		Enemy enemy1 = EnemyButtonFunctions.GetEnemy1();
		Enemy enemy2 = EnemyButtonFunctions.GetEnemy2();
		Enemy enemy3 = EnemyButtonFunctions.GetEnemy3();
		JLabel enemy1StartLocation = EnemyButtonFunctions.getEnemy1StartLocation();
		JLabel enemy1EndLocation = EnemyButtonFunctions.getEnemy1EndLocation();
		JLabel enemy2StartLocation = EnemyButtonFunctions.getEnemy2StartLocation();
		JLabel enemy2EndLocation = EnemyButtonFunctions.getEnemy2EndLocation();
		JLabel enemy3StartLocation = EnemyButtonFunctions.getEnemy3StartLocation();
		JLabel enemy3EndLocation = EnemyButtonFunctions.getEnemy3EndLocation();
		
		if(enemy1StartLocation != null) {
			if(enemy1EndLocation != null) {
				enemy1EndLocation.setVisible(false);
				Point endPoint = new Point(enemy1EndLocation.getX(), enemy1EndLocation.getY());
				MoveEnemy.Move(enemy1StartLocation, endPoint, enemy1.GetSpeed());
			}
		}
		if(enemy2StartLocation != null) {
			if(enemy2EndLocation != null) {
				enemy2EndLocation.setVisible(false);
				Point endPoint = new Point(enemy2EndLocation.getX(), enemy2EndLocation.getY());
				MoveEnemy.Move(enemy2StartLocation, endPoint, enemy2.GetSpeed());
			}
		}
		if(enemy3StartLocation != null) {
			if(enemy3EndLocation != null) {
				enemy3EndLocation.setVisible(false);
				Point endPoint = new Point(enemy3EndLocation.getX(), enemy3EndLocation.getY());
				MoveEnemy.Move(enemy3StartLocation, endPoint, enemy3.GetSpeed());
			}
		}
	}
	
	public static void StopEnemies() {
		MoveEnemy.SetKeepGoingFalse();
		if(EnemyButtonFunctions.GetEnemy1().getEndLocation() != null) {
			EnemyButtonFunctions.GetEnemy1().getEndLocation().setVisible(true);
		}
		if(EnemyButtonFunctions.GetEnemy2().getEndLocation() != null) {
			EnemyButtonFunctions.GetEnemy2().getEndLocation().setVisible(true);
		}
		if(EnemyButtonFunctions.GetEnemy3().getEndLocation() != null) {
			EnemyButtonFunctions.GetEnemy3().getEndLocation().setVisible(true);
		}
	}
}
