package mazeapplication;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class LoadSerMazeFromResources {
	private int startLocation = 0;
	private int q1 = 1;
	private int q2 = 2;
	private int q3 = 3;
	private int q4 = 4;
	private int endLocation = 5;
	private int wallColorMap = 6;
	private int enemy1 = 7;
	private int enemy2 = 8;
	private int enemy3 = 9;
	private int backgroundColor = 10;
	private int decorationList = 11;
	private int blackoutSetting = 12;
	
	public LoadSerMazeFromResources() {
		
	}
	
	public void Load(String mazePath) {
		try {
			ObjectInputStream objectIn = new ObjectInputStream(getClass().getResourceAsStream(mazePath));
			
			Object object = objectIn.readObject();
			int currentObject = 0;
			while(object != null) {
				LoadObjects(object, currentObject);
				object = objectIn.readObject();
				currentObject++;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Couldn't open maze!");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Couldn't load certain object!");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void LoadObjects(Object object, int currentObject) {
		if(currentObject == startLocation) {
			LevelPlayLoadedMazeAttributes.setPlayerStart((JLabel) object);
		}
		else if(currentObject == q1) {
			LevelPlayLoadedMazeAttributes.setQ1((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q2) {
			LevelPlayLoadedMazeAttributes.setQ2((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q3) {
			LevelPlayLoadedMazeAttributes.setQ3((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q4) {
			LevelPlayLoadedMazeAttributes.setQ4((HashMap<Point, Integer>) object);
		}
		else if(currentObject == endLocation) {
			LevelPlayLoadedMazeAttributes.setEndLocation((JLabel) object);
		}
		else if(currentObject == wallColorMap) {
			LevelPlayLoadedMazeAttributes.setColorMap((HashMap<Point, Color>) object);
		}
		else if(currentObject == enemy1) {
			LevelPlayLoadedMazeAttributes.setEnemy1((Enemy) object);
		}
		else if(currentObject == enemy2) {
			LevelPlayLoadedMazeAttributes.setEnemy2((Enemy) object);
		}
		else if(currentObject == enemy3) {
			LevelPlayLoadedMazeAttributes.setEnemy3((Enemy) object);
		}
		else if(currentObject == backgroundColor) {
			LevelPlayLoadedMazeAttributes.setBackgroundColor((Color) object);
		}
		else if(currentObject == decorationList) {
			LevelPlayLoadedMazeAttributes.setDecorationList((LinkedList<DecorationNode>) object);
		}
		else if(currentObject == blackoutSetting) {
			LevelPlayLoadedMazeAttributes.setBlackoutSetting((boolean) object);
		}
	}
}
