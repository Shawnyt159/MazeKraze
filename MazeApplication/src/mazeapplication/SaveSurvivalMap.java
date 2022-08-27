package mazeapplication;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveSurvivalMap {
	public static void Load() {
		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
			old = null;
		}
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Survival", "ser");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       SaveMaze(chooser.getSelectedFile().toString()); 
	    }
	    
	    if(old != null) {
	    	 try {
	 	    	UIManager.setLookAndFeel(old);
	 	    }catch(Exception e) {
	 	    	e.printStackTrace();
	 	    }
	    }
	}
	
	private static boolean SaveMaze(String mazeFilePath) {
		File newMaze = new File(mazeFilePath + ".ser");
		try {
			if(newMaze.createNewFile()) {
				FileOutputStream fileOut = new FileOutputStream(newMaze);
				ObjectOutputStream out = new ObjectOutputStream(fileOut);
				SurvivalMap survivalMap = new SurvivalMap();
				survivalMap.setGridSize(CurrentSurvivalMapData.getGridSize());
				survivalMap.setPlayerStart(CurrentSurvivalMapData.getPlayerStart());
				survivalMap.setMapWalls(CurrentSurvivalMapData.getMapWalls());
				survivalMap.setBackground(CurrentSurvivalMapData.getBackground());
				survivalMap.setEnemySpawnerList(CurrentSurvivalMapData.getEnemySpawnerList());
				survivalMap.setLongestTime(CurrentSurvivalMapData.getLongestTime());
				out.writeObject(survivalMap);
				out.writeObject(null);
				out.close();
				fileOut.close();
				return true;
			}
			else {
				if(JOptionPane.showConfirmDialog(null, "Would you like to overwrite this file?") == JOptionPane.YES_OPTION) {
					FileOutputStream fileOut = new FileOutputStream(newMaze);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					SurvivalMap survivalMap = new SurvivalMap();
					survivalMap.setGridSize(CurrentSurvivalMapData.getGridSize());
					survivalMap.setPlayerStart(CurrentSurvivalMapData.getPlayerStart());
					survivalMap.setMapWalls(CurrentSurvivalMapData.getMapWalls());
					survivalMap.setBackground(CurrentSurvivalMapData.getBackground());
					survivalMap.setEnemySpawnerList(CurrentSurvivalMapData.getEnemySpawnerList());
					survivalMap.setLongestTime(CurrentSurvivalMapData.getLongestTime());
					out.writeObject(survivalMap);
					out.writeObject(null);
					out.close();
					fileOut.close();
					return true;
				}
				else {
					return false;
				}
			}
		} catch (HeadlessException e) {
			e.printStackTrace();
			return false;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
