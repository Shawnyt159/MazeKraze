package mazeapplication;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class EnemyButtonFunctions {
	private static int numberOfEnemies = 0;
	private static Enemy enemy1 = new Enemy();
	private static Enemy enemy2 = new Enemy();
	private static Enemy enemy3 = new Enemy();
	private static int enemyControl = 0;
	private static SetImageToLabel images = new SetImageToLabel();

	/**
	 * ENEMY START LOCATION FUNCTIONS.
	 */

	public static void PlayerSetEnemy1StartLocation() {
		if (enemy1.getStartLabel() == null) {
			enemy1.setStartLabel(new JLabel(""));
			PlaceEnemyStartOnPanel(MazeMainGUI.GetMazePanel(), enemy1.getStartLabel());
			enemy1.SetSpeed(EnemyControls.GetEnemySpeed());
		} else {
			JPanel mazePanel = MazeMainGUI.GetMazePanel();
			Point coordinates = mazePanel.getMousePosition();
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to change enemy 1 start location?") == JOptionPane.YES_OPTION) {
				ChangeEnemyStartLocation(mazePanel, enemy1.getStartLabel(), coordinates, enemy1);
				enemy1.SetSpeed(EnemyControls.GetEnemySpeed());
			}
		}
	}

	public static void PlayerSetEnemy2StartLocation() {
		if (enemy2.getStartLabel() == null) {
			enemy2.setStartLabel(new JLabel(""));
			PlaceEnemyStartOnPanel(MazeMainGUI.GetMazePanel(), enemy2.getStartLabel());
			enemy2.SetSpeed(EnemyControls.GetEnemySpeed());
		} else {
			JPanel mazePanel = MazeMainGUI.GetMazePanel();
			Point coordinates = mazePanel.getMousePosition();
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to change enemy 2 start location?") == JOptionPane.YES_OPTION) {
				ChangeEnemyStartLocation(mazePanel, enemy2.getStartLabel(), coordinates, enemy2);
				enemy2.SetSpeed(EnemyControls.GetEnemySpeed());
			}
		}
	}

	public static void PlayerSetEnemy3StartLocation() {
		if (enemy3.getStartLabel() == null) {
			enemy3.setStartLabel(new JLabel(""));
			PlaceEnemyStartOnPanel(MazeMainGUI.GetMazePanel(), enemy3.getStartLabel());
			enemy3.SetSpeed(EnemyControls.GetEnemySpeed());
		} else {
			JPanel mazePanel = MazeMainGUI.GetMazePanel();
			Point coordinates = mazePanel.getMousePosition();
			if (JOptionPane.showConfirmDialog(null,
					"Are you sure you want to change enemy 3 start location?") == JOptionPane.YES_OPTION) {
				ChangeEnemyStartLocation(mazePanel, enemy3.getStartLabel(), coordinates, enemy3);
				enemy3.SetSpeed(EnemyControls.GetEnemySpeed());
			}
		}
	}

	private static void PlaceEnemyStartOnPanel(JPanel mazePanel, JLabel enemy) {
		Point coordinates = mazePanel.getMousePosition();
		int x = (int) coordinates.getX() - (EnemyControls.GetEnemyWidth() / 2);
		int y = (int) coordinates.getY() - (EnemyControls.GetEnemyWidth() / 2);
		enemy.setBounds(x, y, EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
		mazePanel.add(enemy);
		images.set_image_to_label(enemy, "/images/enemy.png");
		enemy.setVisible(true);
	}

	private static void ChangeEnemyStartLocation(JPanel mazePanel, JLabel enemy, Point coordinates, Enemy enemyObject) {
		int x = (int) coordinates.getX() - (EnemyControls.GetEnemyWidth() / 2);
		int y = (int) coordinates.getY() - (EnemyControls.GetEnemyWidth() / 2);
		enemy.setBounds(x, y, EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
		images.set_image_to_label(enemy, "/images/enemy.png");
		if (enemyObject.getEndLocation() != null) {
			MazeMainGUI.GetMazePanel().remove(enemyObject.getEndLocation());
			enemyObject.setEndLocation(null);
			MazeMainGUI.GetMazePanel().repaint();
		}
	}

	private static void LoadEnemyStartLocation(JPanel mazePanel, JLabel startLocation) {
		numberOfEnemies++;
		mazePanel.add(startLocation);
		mazePanel.repaint();
	}

	/**
	 * ENEMY END LOCATION FUNCTIONS.
	 */

	public static void SetEnemy1EndLocation() {
		if (enemy1.getStartLabel() != null) {
			if (enemy1.getEndLocation() == null) {
				enemy1.setEndLocation(new JLabel(""));
				PlaceEnemyEndOnPanel(MazeMainGUI.GetMazePanel(), enemy1.getEndLocation(), enemy1.getStartLabel());
			} else {
				JPanel mazePanel = MazeMainGUI.GetMazePanel();
				Point coordinates = mazePanel.getMousePosition();
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to change enemy 1 end location?") == JOptionPane.YES_OPTION) {
					ChangeEnemyEndLocation(mazePanel, enemy1.getEndLocation(), coordinates, enemy1.getStartLabel());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "There is no start location!");
		}
	}

	public static void SetEnemy2EndLocation() {
		if (enemy2.getStartLabel() != null) {
			if (enemy2.getEndLocation() == null) {
				enemy2.setEndLocation(new JLabel(""));
				PlaceEnemyEndOnPanel(MazeMainGUI.GetMazePanel(), enemy2.getEndLocation(), enemy2.getStartLabel());
			} else {
				JPanel mazePanel = MazeMainGUI.GetMazePanel();
				Point coordinates = mazePanel.getMousePosition();
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to change enemy 2 end location?") == JOptionPane.YES_OPTION) {
					ChangeEnemyEndLocation(mazePanel, enemy2.getEndLocation(), coordinates, enemy2.getStartLabel());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "There is no start location!");
		}
	}

	public static void SetEnemy3EndLocation() {
		if (enemy3.getStartLabel() != null) {
			if (enemy3.getEndLocation() == null) {
				enemy3.setEndLocation(new JLabel(""));
				PlaceEnemyEndOnPanel(MazeMainGUI.GetMazePanel(), enemy3.getEndLocation(), enemy3.getStartLabel());
			} else {
				JPanel mazePanel = MazeMainGUI.GetMazePanel();
				Point coordinates = mazePanel.getMousePosition();
				if (JOptionPane.showConfirmDialog(null,
						"Are you sure you want to change enemy 3 end location?") == JOptionPane.YES_OPTION) {
					ChangeEnemyEndLocation(mazePanel, enemy3.getEndLocation(), coordinates, enemy3.getStartLabel());
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "There is no start location!");
		}
	}

	private static void PlaceEnemyEndOnPanel(JPanel mazePanel, JLabel enemy, JLabel enemyStart) {
		Point coordinates = mazePanel.getMousePosition();
		int x = 0;
		int y = 0;
		if (enemyControl == 2) {
			x = (int) coordinates.getX();
			y = enemyStart.getY();
		} else {
			x = enemyStart.getX();
			y = (int) coordinates.getY();
		}
		enemy.setBounds(x, y, 5, 5);
		mazePanel.add(enemy);
		images.set_image_to_label(enemy, "/images/end location.png");
		enemy.setVisible(true);
	}

	private static void ChangeEnemyEndLocation(JPanel mazePanel, JLabel enemy, Point coordinates, JLabel enemyStart) {
		int x = 0;
		int y = 0;
		if (enemyControl == 2) {
			x = (int) coordinates.getX();
			y = enemyStart.getY();
		} else {
			x = enemyStart.getX();
			y = (int) coordinates.getY();
		}
		enemy.setBounds(x, y, 5, 5);
		images.set_image_to_label(enemy, "/images/end location.png");
	}

	private static void LoadEnemyEndLocation(JPanel mazePanel, JLabel endLabel) {
		mazePanel.add(endLabel);
		mazePanel.repaint();
	}

	/**
	 * DELETE ENEMY FUNCTIONS:
	 */
	public static void DeleteEnemy() {
		int enemy1 = 1;
		int enemy2 = 2;
		int enemy3 = 3;
		int enemy = EnemyControls.GetEnemySelected();
		if (enemy == enemy1) {
			DeleteEnemy1();
		} else if (enemy == enemy2) {
			DeleteEnemy2();
		} else if (enemy == enemy3) {
			DeleteEnemy3();
		}
	}

	public static void DeleteEnemy1() {
		JPanel mazePanel = MazeMainGUI.GetMazePanel();
		if (enemy1.getStartLabel() != null) {
			mazePanel.remove(enemy1.getStartLabel());
			enemy1.setStartLabel(null);
			mazePanel.repaint();
		}
		if (enemy1.getEndLocation() != null) {
			mazePanel.remove(enemy1.getEndLocation());
			enemy1.setEndLocation(null);
			mazePanel.repaint();
		}
	}

	public static void DeleteEnemy2() {
		JPanel mazePanel = MazeMainGUI.GetMazePanel();
		if (enemy2.getStartLabel() != null) {
			mazePanel.remove(enemy2.getStartLabel());
			enemy2.setStartLabel(null);
			mazePanel.repaint();
		}
		if (enemy2.getEndLocation() != null) {
			mazePanel.remove(enemy2.getEndLocation());
			enemy2.setEndLocation(null);
			mazePanel.repaint();
		}
	}

	public static void DeleteEnemy3() {
		JPanel mazePanel = MazeMainGUI.GetMazePanel();
		if (enemy3.getStartLabel() != null) {
			mazePanel.remove(enemy3.getStartLabel());
			enemy3.setStartLabel(null);
			mazePanel.repaint();
		}
		if (enemy3.getEndLocation() != null) {
			mazePanel.remove(enemy3.getEndLocation());
			enemy3.setEndLocation(null);
			mazePanel.repaint();
		}
	}

	/**
	 * UPDATE ENEMY FUNCTIONS:
	 */
	public static void UpdateEnemy1() {
		if (enemy1.getStartLabel() != null) {
			enemy1.getStartLabel().setBounds(enemy1.getStartLabel().getX(), enemy1.getStartLabel().getY(),
					EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
			enemy1.SetSpeed(EnemyControls.GetEnemySpeed());
		}
	}

	public static void UpdateEnemy2() {
		if (enemy2.getStartLabel() != null) {
			enemy2.getStartLabel().setBounds(enemy2.getStartLabel().getX(), enemy2.getStartLabel().getY(),
					EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
			enemy2.SetSpeed(EnemyControls.GetEnemySpeed());
		}
	}

	public static void UpdateEnemy3() {
		if (enemy3.getStartLabel() != null) {
			enemy3.getStartLabel().setBounds(enemy3.getStartLabel().getX(), enemy3.getStartLabel().getY(),
					EnemyControls.GetEnemyWidth(), EnemyControls.GetEnemyHeight());
			enemy3.SetSpeed(EnemyControls.GetEnemySpeed());
		}
	}

	/**
	 * MUTUAL FUNCTIONS:
	 */

	public static int GetNumberOfEnemies() {
		return numberOfEnemies;
	}

	public static void SetNumberOfEnemies(int newNumberOfEnemies) {
		numberOfEnemies = newNumberOfEnemies;
	}

	public static int GetEnemyControl() {
		return enemyControl;
	}

	public static void SetEnemyControl(int newEnemyControl) {
		enemyControl = newEnemyControl;
	}

	public static JLabel getEnemy1StartLocation() {
		return enemy1.getStartLabel();
	}

	public static void setEnemy1StartLocation(JLabel enemy1StartLocation) {
		EnemyButtonFunctions.enemy1.setStartLabel(enemy1StartLocation);
	}

	public static JLabel getEnemy2StartLocation() {
		return enemy2.getStartLabel();
	}

	public static void setEnemy2StartLocation(JLabel enemy2StartLocation) {
		EnemyButtonFunctions.enemy2.setStartLabel(enemy2StartLocation);
	}

	public static JLabel getEnemy3StartLocation() {
		return enemy3.getStartLabel();
	}

	public static void setEnemy3StartLocation(JLabel enemy3StartLocation) {
		EnemyButtonFunctions.enemy3.setStartLabel(enemy3StartLocation);
	}

	public static JLabel getEnemy1EndLocation() {
		return enemy1.getEndLocation();
	}

	public static void setEnemy1EndLocation(JLabel enemy1EndLocation) {
		EnemyButtonFunctions.enemy1.setEndLocation(enemy1EndLocation);
	}

	public static JLabel getEnemy2EndLocation() {
		return enemy2.getEndLocation();
	}

	public static void setEnemy2EndLocation(JLabel enemy2EndLocation) {
		EnemyButtonFunctions.enemy2.setEndLocation(enemy2EndLocation);
	}

	public static JLabel getEnemy3EndLocation() {
		return enemy3.getEndLocation();
	}

	public static void setEnemy3EndLocation(JLabel enemy3EndLocation) {
		EnemyButtonFunctions.enemy3.setEndLocation(enemy3EndLocation);
	}

	public static Enemy GetEnemy1() {
		return enemy1;
	}

	public static Enemy GetEnemy2() {
		return enemy2;
	}

	public static Enemy GetEnemy3() {
		return enemy3;
	}

	public static void SetEnemy1(Enemy enemy1) {
		EnemyButtonFunctions.enemy1 = enemy1;
		if (EnemyButtonFunctions.enemy1.getStartLabel() != null) {
			LoadEnemyStartLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy1.getStartLabel());
			EnemyControls.AddEnemy1();
		}
		if (EnemyButtonFunctions.enemy1.getEndLocation() != null) {
			LoadEnemyEndLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy1.getEndLocation());
		}
	}

	public static void SetEnemy2(Enemy enemy2) {
		EnemyButtonFunctions.enemy2 = enemy2;
		if (EnemyButtonFunctions.enemy2.getStartLabel() != null) {
			LoadEnemyStartLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy2.getStartLabel());
			EnemyControls.AddEnemy2();
		}
		if (EnemyButtonFunctions.enemy2.getEndLocation() != null) {
			LoadEnemyEndLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy2.getEndLocation());
		}
	}

	public static void SetEnemy3(Enemy enemy3) {
		EnemyButtonFunctions.enemy3 = enemy3;
		if (EnemyButtonFunctions.enemy3.getStartLabel() != null) {
			LoadEnemyStartLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy3.getStartLabel());
			EnemyControls.AddEnemy3();
		}
		if (EnemyButtonFunctions.enemy3.getEndLocation() != null) {
			LoadEnemyEndLocation(MazeMainGUI.GetMazePanel(), EnemyButtonFunctions.enemy3.getEndLocation());
		}
	}

	public static int getNumberOfEnemies() {
		return numberOfEnemies;
	}

	public static int getEnemyControl() {
		return enemyControl;
	}

	public static SetImageToLabel getImages() {
		return images;
	}
}
