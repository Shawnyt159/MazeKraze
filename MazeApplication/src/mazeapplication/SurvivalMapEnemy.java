package mazeapplication;

import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SurvivalMapEnemy {
	private JLabel enemyLabel;
	private int moveCount = 0;
	private int direction = 0;
	private int left = 1;
	private int right = 2;
	private int up = 3;
	private int down = 4;
	
	public SurvivalMapEnemy(JLabel enemyLabel) {
		this.enemyLabel = enemyLabel;
	}
	
	public void MoveEnemy() {
		Random rand = new Random();
		if(direction == 0) {
			direction = rand.nextInt(4)+1;
			moveCount = rand.nextInt(100) + 25;
		}
		boolean moved = false;
		while(moved == false) {
			if(direction == left) {
				if(moveCount != 0 && CheckIfMovementHitsAWall(EnemyBoundsIfLeft()) == false) {
					moveCount--;
					MoveEnemyLeft();
					moved = true;
				}
				else {
					direction = rand.nextInt(4)+1;
					moveCount = rand.nextInt(50) + 1;
				}
			}
			else if(direction == right) {
				if(moveCount != 0 && CheckIfMovementHitsAWall(EnemyBoundsIfRight()) == false) {
					moveCount--;
					MoveEnemyRight();
					moved = true;
				}
				else {
					direction = rand.nextInt(4)+1;
					moveCount = rand.nextInt(50) + 1;
				}
			}
			else if(direction == up) {
				if(moveCount != 0 && CheckIfMovementHitsAWall(EnemyBoundsIfUp()) == false) {
					moveCount--;
					MoveEnemyUp();
					moved = true;
				}
				else {
					direction = rand.nextInt(4)+1;
					moveCount = rand.nextInt(50) + 1;
				}
			}
			else if(direction == down) {
				if(moveCount != 0 && CheckIfMovementHitsAWall(EnemyBoundsIfDown()) == false) {
					moveCount--;
					MoveEnemyDown();
					moved = true;
				}
				else {
					direction = rand.nextInt(4)+1;
					moveCount = rand.nextInt(50) + 1;
				}
			}
		}
	}
	
	public boolean CheckIfMovementHitsAWall(Rectangle bounds) {
		LinkedList<SurvivalMapWall> wallList = CurrentSurvivalMapData.getMapWalls();
		for(int i = 0; i < wallList.size(); i++) {
			if(bounds.intersects(wallList.get(i).getWall().getBounds())) {
				return true;
			}
		}
		if(!CurrentSurvivalMapData.getMapBounds().contains(bounds)) {
			return true;
		}
		return false;
	}
	
	public Rectangle EnemyBoundsIfLeft() {
		Rectangle bounds = enemyLabel.getBounds();
		bounds.setBounds((int) bounds.getX()-1, (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
		return bounds;
	}
	
	public Rectangle EnemyBoundsIfRight() {
		Rectangle bounds = enemyLabel.getBounds();
		bounds.setBounds((int) bounds.getX()+1, (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight());
		return bounds;
	}
	
	public Rectangle EnemyBoundsIfUp() {
		Rectangle bounds = enemyLabel.getBounds();
		bounds.setBounds((int) bounds.getX(), (int) bounds.getY()-1, (int) bounds.getWidth(), (int) bounds.getHeight());
		return bounds;
	}
	
	public Rectangle EnemyBoundsIfDown() {
		Rectangle bounds = enemyLabel.getBounds();
		bounds.setBounds((int) bounds.getX(), (int) bounds.getY()+1, (int) bounds.getWidth(), (int) bounds.getHeight());
		return bounds;
	}
	
	public void MoveEnemyLeft() {
		enemyLabel.setBounds(enemyLabel.getX()-1, enemyLabel.getY(), enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckHitsPlayer(enemyLabel.getBounds());
	}
	public void MoveEnemyRight() {
		enemyLabel.setBounds(enemyLabel.getX()+1, enemyLabel.getY(), enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckHitsPlayer(enemyLabel.getBounds());
	}
	public void MoveEnemyUp() {
		enemyLabel.setBounds(enemyLabel.getX(), enemyLabel.getY()-1, enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckHitsPlayer(enemyLabel.getBounds());
	}
	public void MoveEnemyDown() {
		enemyLabel.setBounds(enemyLabel.getX(), enemyLabel.getY()+1, enemyLabel.getWidth(), enemyLabel.getHeight());
		CheckHitsPlayer(enemyLabel.getBounds());
	}
	
	private void CheckHitsPlayer(Rectangle bounds) {
		if(bounds.intersects(SurvivalMapPlayer.getPlayerLabel().getBounds())) {
			SurvivalMapDesignWindow.EndMap();
			JOptionPane.showMessageDialog(null, "You died");
		}
	}
	
	public JLabel getEnemyLabel() {
		return enemyLabel;
	}

	public void setEnemyLabel(JLabel enemyLabel) {
		this.enemyLabel = enemyLabel;
	}
	
}