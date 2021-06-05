package mazeapplication;

import java.awt.Point;
import java.awt.geom.Area;

import javax.swing.JLabel;

public class LevelPlayMoveEnemy {
	private static boolean keepGoing = false;

	public static void Move(JLabel enemyLabel, Point endPoint, long speed) {
		keepGoing = true;
		Thread moveThread = new Thread() {
			public void run() {
				Point startPoint = new Point(enemyLabel.getX(), enemyLabel.getY());
				// Vertical Movement
				if ((int) startPoint.getX() == endPoint.getX()) {
					// Upward start movement
					if ((int) startPoint.getY() > (int) endPoint.getY()) {
						while (keepGoing == true) {
							while (enemyLabel.getY() != (int) endPoint.getY() && keepGoing == true) {
								MakeMoveUp(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							while (enemyLabel.getY() != startPoint.getY() && keepGoing == true) {
								MakeMoveDown(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
					// Downward start movement
					else {
						while (keepGoing == true) {
							while (enemyLabel.getY() != (int) endPoint.getY() && keepGoing == true) {
								MakeMoveDown(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							while (enemyLabel.getY() != (int) startPoint.getY() && keepGoing == true) {
								MakeMoveUp(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				// Horizontal Movement
				else {
					// Left start movement
					if ((int) startPoint.getX() > (int) endPoint.getX()) {
						while(keepGoing == true) {
							while (enemyLabel.getX() != (int) endPoint.getX() && keepGoing == true) {
								MakeMoveLeft(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							while (enemyLabel.getX() != (int) startPoint.getX() && keepGoing == true) {
								MakeMoveRight(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
					// Right Start movement
					else {
						while(keepGoing == true) {
							while (enemyLabel.getX() != (int) endPoint.getX() && keepGoing == true) {
								MakeMoveRight(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							while(enemyLabel.getX() != (int) startPoint.getX() && keepGoing == true) {
								MakeMoveLeft(enemyLabel);
								try {
									Thread.sleep(speed);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
				//Reset enemyLabel
				enemyLabel.setBounds((int) startPoint.getX(), (int) startPoint.getY(), enemyLabel.getHeight(), enemyLabel.getWidth());
			}
		};
		moveThread.start();
	}

	private static void MakeMoveUp(JLabel enemyLabel) {
		enemyLabel.setBounds(enemyLabel.getX(), enemyLabel.getY() - 1, enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckCollisionWithPlayer(enemyLabel);
		
	}

	private static void MakeMoveDown(JLabel enemyLabel) {
		enemyLabel.setBounds(enemyLabel.getX(), enemyLabel.getY() + 1, enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckCollisionWithPlayer(enemyLabel);
	}

	private static void MakeMoveLeft(JLabel enemyLabel) {
		enemyLabel.setBounds(enemyLabel.getX() - 1, enemyLabel.getY(), enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckCollisionWithPlayer(enemyLabel);
	}

	private static void MakeMoveRight(JLabel enemyLabel) {
		enemyLabel.setBounds(enemyLabel.getX() + 1, enemyLabel.getY(), enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckCollisionWithPlayer(enemyLabel);
	}
	
	private static void CheckCollisionWithPlayer(JLabel enemyLabel) {
		Area a = new Area(enemyLabel.getBounds());
		Area b = new Area(MazeLevelPlayGUI.GetPlayerLabel().getBounds());
		if(a.intersects(b.getBounds2D()) == true) {
			MazeLevelPlayGUI.GetPlayerLabel().setBounds(LevelPlayLoadedMazeAttributes.getPlayerStart().getBounds());
		}
	}
	
	public static void SetKeepGoingFalse() {
		keepGoing = false;
	}
}
