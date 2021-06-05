package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFileExplorer {
	private static int startLocation = 0;
	private static int q1 = 1;
	private static int q2 = 2;
	private static int q3 = 3;
	private static int q4 = 4;
	private static int endLocation = 5;
	private static int wallColorMap = 6;
	private static int enemy1 = 7;
	private static int enemy2 = 8;
	private static int enemy3 = 9;
	private static int backgroundColor = 10;
	private static int decorationsList = 11;
	private static int blackoutSetting = 12;
	
	
	
	public static void Load() throws EOFException {
		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
			old = null;
		}
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Mazes", "ser");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       LoadMaze(chooser.getSelectedFile().toString());
	    }
	    
	    if(old != null) {
	    	 try {
	 	    	UIManager.setLookAndFeel(old);
	 	    }catch(Exception e) {
	 	    	e.printStackTrace();
	 	    }
	    }
	}
	
	private static void LoadMaze(String filePath) throws EOFException {
		try {
			ButtonFunctions.GetWallColorMap().clear();
			EnemyButtonFunctions.DeleteEnemy1();
			EnemyButtonFunctions.DeleteEnemy2();
			EnemyButtonFunctions.DeleteEnemy3();
			MazeDesignMainGUI.GetMazePanel().setBackground(Color.white);
			MazeDesignMainGUI.RemoveDecorationsFromMazeAndList();
			if(MazeDesignMainGUI.GetEnemyControls() == null) {
				MazeDesignMainGUI.InitializeEnemyControls();
				EnemyControls.SetDisposed();
			}
			EnemyControls.ResetEnemies();
			FileInputStream in = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(in);
			JPanel mazePanel = MazeDesignMainGUI.GetMazePanel();
			Object object = objectIn.readObject();
			int currentObject = 0;
			while(object != null) {
				LoadObjects(object, mazePanel, currentObject);
				object = objectIn.readObject();
				currentObject++;
			}
			mazePanel.repaint();
			in.close();
			objectIn.close();
			MazeDesignMainGUI.SetLoadedMazeFileLocation(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private static void LoadObjects(Object object, JPanel mazePanel, int currentObject) {
		if(currentObject == startLocation) {
			ButtonFunctions.SetStartLocationCoordinatesLoad(mazePanel, (JLabel) object);
		}
		else if(currentObject == q1) {
			ButtonFunctions.SetQ1((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q2) {
			ButtonFunctions.SetQ2((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q3) {
			ButtonFunctions.SetQ3((HashMap<Point, Integer>) object);
		}
		else if(currentObject == q4) {
			ButtonFunctions.SetQ4((HashMap<Point, Integer>) object);
		}
		else if(currentObject == endLocation) {
			ButtonFunctions.SetEndLocationCoordinatesLoad(mazePanel, (JLabel) object);
		}
		else if(currentObject == wallColorMap) {
			ButtonFunctions.SetWallColorMap((HashMap<Point, Color>) object);
		}
		else if(currentObject == enemy1) {
			EnemyButtonFunctions.SetEnemy1((Enemy) object);
		}
		else if(currentObject == enemy2) {
			EnemyButtonFunctions.SetEnemy2((Enemy) object);
		}
		else if(currentObject == enemy3) {
			EnemyButtonFunctions.SetEnemy3((Enemy) object);
		}
		else if(currentObject == backgroundColor) {
			MazeDesignMainGUI.GetMazePanel().setBackground((Color) object);
		}
		else if(currentObject == decorationsList) {
			MazeDesignMainGUI.AddDecorationsToMazeAndList((LinkedList<DecorationNode>) object);
		}
		else if(currentObject == blackoutSetting) {
			MazeDesignMainGUI.SetBlackoutMazeSetting((boolean) object);
		}
	}
}
