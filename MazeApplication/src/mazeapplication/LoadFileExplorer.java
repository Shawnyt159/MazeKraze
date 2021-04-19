package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class LoadFileExplorer {
	private static int startLocation = 0;
	private static int q1 = 1;
	private static int q2 = 2;
	private static int q3 = 3;
	private static int q4 = 4;
	private static int endLocation = 5;
	private static int wallColorMap = 6;
	
	
	
	public static void Load() throws EOFException {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Mazes", "ser");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(chooser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       LoadMaze(chooser.getSelectedFile().toString());
	    }
	}
	
	private static void LoadMaze(String filePath) throws EOFException {
		try {
			ButtonFunctions.GetWallColorMap().clear();
			FileInputStream in = new FileInputStream(filePath);
			ObjectInputStream objectIn = new ObjectInputStream(in);
			JPanel mazePanel = MazeMainGUI.GetMazePanel();
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
	}
}
