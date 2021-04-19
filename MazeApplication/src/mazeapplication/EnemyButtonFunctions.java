package mazeapplication;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class EnemyButtonFunctions {
	private static int numberOfEnemies = 0;
	private static JLabel enemy1 = null;
	private static JLabel enemy2 = null;
	private static JLabel enemy3 = null;
	private static SetImageToLabel images = new SetImageToLabel();
	
	public static void PlayerSetEnemy1StartLocation() {
		if(enemy1 == null) {
			enemy1 = new JLabel("");
			PlaceEnemyOnPanel(MazeMainGUI.GetMazePanel(), enemy1);
		}
	}
	
	public static void PlayerSetEnemy2StartLocation() {
		if(enemy2 == null) {
			enemy2 = new JLabel("");
			PlaceEnemyOnPanel(MazeMainGUI.GetMazePanel(), enemy2);
		}
	}
	
	public static void PlayerSetEnemy3StartLocation() {
		if(enemy3 == null) {
			enemy3 = new JLabel("");
			PlaceEnemyOnPanel(MazeMainGUI.GetMazePanel(), enemy3);
		}
	}
	
	private static void PlaceEnemyOnPanel(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		int x = (int) coordinates.getX() - (EnemyControls.GetEnemyWidth()/2);
		int y = (int) coordinates.getY() - (EnemyControls.GetEnemyWidth()/2);
		enemy.setBounds(x, y, EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
		mazePanel.add(enemy);
		images.set_image_to_label(enemy, "/images/enemy.png");
		enemy.setVisible(true);
	}
	
	public static int GetNumberOfEnemies() {
		return numberOfEnemies;
	}
	public static void SetNumberOfEnemies(int newNumberOfEnemies) {
		numberOfEnemies = newNumberOfEnemies;
	}
}
