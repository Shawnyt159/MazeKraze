package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SurvivalMapEnemySpawner implements MouseListener, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6668805133478166940L;
	private JLabel spawnerLabel;
	private LinkedList<SurvivalMapEnemy> enemyList = new LinkedList<SurvivalMapEnemy>();
	SetImageToLabel images = new SetImageToLabel();
	private boolean spawnEnemies = false;
	private int left = 1;
	private int right = 2;
	private int up = 3;
	private int down = 4;
	private int removeEnemySpawnSelected = 5;
	private ArrayList<Integer> unavailableDirections = new ArrayList<Integer>();
	
	
	public SurvivalMapEnemySpawner(JLabel spawnerLabel) {
		this.spawnerLabel = spawnerLabel;
		spawnerLabel.addMouseListener(this);
	}

	public JLabel getSpawnerLabel() {
		return spawnerLabel;
	}

	public void setSpawnerLabel(JLabel spawnerLabel) {
		this.spawnerLabel = spawnerLabel;
	}
	
	public void SpawnEnemies(boolean spawnEnemies, JPanel collectionMapPanel, int currentSize) {
		this.spawnEnemies = spawnEnemies;
		if(this.spawnEnemies == true) {
			CheckDirectionsForSpawning();
			BeginSpawningEnemies(collectionMapPanel, currentSize);
		}
		else if(this.spawnEnemies == false) {
			RemoveEnemiesFromMap(collectionMapPanel);
			ResetEnemyList();
			ResetEnemySpawnerDirections();
		}
	}
	
	private void CheckDirectionsForSpawning() {
		//Checking left.
		if(!CurrentSurvivalMapData.getMapBounds().contains(new Point(spawnerLabel.getX()-1, spawnerLabel.getY()))) {
			if(!unavailableDirections.contains(left)) {
				unavailableDirections.add(left);
			}
		}
		for(int i = 0; i < CurrentSurvivalMapData.getMapWalls().size(); i++) {
			if(CurrentSurvivalMapData.getMapWalls().get(i).getWall().getBounds().contains(new Point(spawnerLabel.getX()-1, spawnerLabel.getY()))) {
				if(!unavailableDirections.contains(left)) {
					unavailableDirections.add(left);
				}
			}
		}
		//Checking right.
		if(!CurrentSurvivalMapData.getMapBounds().contains(new Point(spawnerLabel.getX()+1+spawnerLabel.getWidth(), spawnerLabel.getY()))) {
			if(!unavailableDirections.contains(right)) {
				unavailableDirections.add(right);
			}
		}
		for(int i = 0; i < CurrentSurvivalMapData.getMapWalls().size(); i++) {
			if(CurrentSurvivalMapData.getMapWalls().get(i).getWall().getBounds().contains(new Point(spawnerLabel.getX()+1+spawnerLabel.getWidth(), spawnerLabel.getY()))) {
				if(!unavailableDirections.contains(right)) {
					unavailableDirections.add(right);
				}
			}
		}
		//Checking up.
		if(!CurrentSurvivalMapData.getMapBounds().contains(new Point(spawnerLabel.getX(), spawnerLabel.getY()-1))) {
			if(!unavailableDirections.contains(up)) {
				unavailableDirections.add(up);
			}
		}
		for(int i = 0; i < CurrentSurvivalMapData.getMapWalls().size(); i++) {
			if(CurrentSurvivalMapData.getMapWalls().get(i).getWall().getBounds().contains(new Point(spawnerLabel.getX(), spawnerLabel.getY()-1))) {
				if(!unavailableDirections.contains(up)) {
					unavailableDirections.add(up);
				}
			}
		}
		//Checking down.
		if(!CurrentSurvivalMapData.getMapBounds().contains(new Point(spawnerLabel.getX(), spawnerLabel.getY()+1+spawnerLabel.getHeight()))) {
			if(!unavailableDirections.contains(down)) {
				unavailableDirections.add(down);
			}
		}
		for(int i = 0; i < CurrentSurvivalMapData.getMapWalls().size(); i++) {
			if(CurrentSurvivalMapData.getMapWalls().get(i).getWall().getBounds().contains(new Point(spawnerLabel.getX(), spawnerLabel.getY()+1+spawnerLabel.getHeight()))) {
				if(!unavailableDirections.contains(down)) {
					unavailableDirections.add(down);
				}
			}
		}
	}
	
	private void BeginSpawningEnemies(JPanel collectionMapPanel, int currentSize) {
		//TODO: When spawnEnemies is true, start thread when it is false kill thread.
		Thread enemySpawnerThread = new Thread() {
			public void run() {
				Random rand = new Random();
				while(true) {
					if(spawnEnemies == true) {
						if(unavailableDirections.size() != 4) {
							JLabel enemyLabel = new JLabel();
							int direction = rand.nextInt(4) +1;
							while(unavailableDirections.contains(direction)) {
								direction = rand.nextInt(4) +1;
							}
							if(direction == left) {
								enemyLabel.setBounds(spawnerLabel.getX()-currentSize, spawnerLabel.getY(), currentSize, currentSize);
							}
							else if(direction == right) {
								enemyLabel.setBounds(spawnerLabel.getX()+currentSize, spawnerLabel.getY(), currentSize, currentSize);
							}
							else if(direction == up) {
								enemyLabel.setBounds(spawnerLabel.getX(), spawnerLabel.getY()-currentSize, currentSize, currentSize);
							}
							else if(direction == down) {
								enemyLabel.setBounds(spawnerLabel.getX(), spawnerLabel.getY()+currentSize, currentSize, currentSize);
							}
							enemyLabel.setVisible(true);
							images.set_image_to_label(enemyLabel, "/images/Swamp/deep troll.png");
							SurvivalMapEnemy newEnemy = new SurvivalMapEnemy(enemyLabel);
							enemyList.add(newEnemy);
							collectionMapPanel.add(enemyLabel);
							try {
								sleep(8000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else {
						break;
					}
				}
			}
		};
		enemySpawnerThread.start();
		StartMovingEnemiesFromThisSpawner();
	}
	
	public void StartMovingEnemiesFromThisSpawner() {
		// TODO: Make a thread and move all the enemies that were spawned by this spawner.
		Thread moveEnemiesThread = new Thread() {
			public void run() {
				while(true) {
					if(spawnEnemies == false) {
						break;
					}
					try {
						for(int i = 0; i < enemyList.size(); i++) {
							enemyList.get(i).MoveEnemy();
						}
						sleep(30);
					} catch(InterruptedException e) {
						
					}
				}
			}
		};
		moveEnemiesThread.start();
	}
	
	private void RemoveEnemiesFromMap(JPanel collectionMapPanel) {
		for(int i = 0; i < enemyList.size(); i++) {
			collectionMapPanel.remove(enemyList.get(i).getEnemyLabel());
		}
		collectionMapPanel.repaint();
	}
	
	private void ResetEnemyList() {
		this.enemyList.clear();
	}
	
	private void ResetEnemySpawnerDirections() {
		this.unavailableDirections.clear();
	}
	
	public boolean isSpawnEnemies() {
		return spawnEnemies;
	}

	public void setSpawnEnemies(boolean spawnEnemies) {
		this.spawnEnemies = spawnEnemies;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SurvivalMapDesignWindow.getSelected() == removeEnemySpawnSelected) {
			SurvivalMapDesignWindow.getCollectionMap().remove(spawnerLabel);
			SurvivalMapDesignWindow.getCollectionMap().repaint();
			CurrentSurvivalMapData.getEnemySpawnerList().remove(this);
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(SurvivalMapDesignWindow.getSelected() == removeEnemySpawnSelected) {
			spawnerLabel.setBorder(new LineBorder(Color.red, 2));
		}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(SurvivalMapDesignWindow.getSelected() == removeEnemySpawnSelected) {
			spawnerLabel.setBorder(null);
		}
		
	}
}
