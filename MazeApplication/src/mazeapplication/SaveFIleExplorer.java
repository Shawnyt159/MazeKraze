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

public class SaveFileExplorer {
	public static void Load() {
		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
			old = null;
		}
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Mazes", "ser");
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
				out.writeObject(ButtonFunctions.GetStartLabel());
				out.writeObject(ButtonFunctions.GetQ1());
				out.writeObject(ButtonFunctions.GetQ2());
				out.writeObject(ButtonFunctions.GetQ3());
				out.writeObject(ButtonFunctions.GetQ4());
				out.writeObject(ButtonFunctions.GetEndLabel());
				out.writeObject(ButtonFunctions.GetWallColorMap());
				out.writeObject(EnemyButtonFunctions.GetEnemy1());
				out.writeObject(EnemyButtonFunctions.GetEnemy2());
				out.writeObject(EnemyButtonFunctions.GetEnemy3());
				out.writeObject(MazeDesignMainGUI.GetMazePanel().getBackground());
				out.writeObject(MazeDesignMainGUI.getDecorationList());
				out.writeObject(MazeDesignMainGUI.GetBlackoutMazeSetting());
				out.writeObject(null);
				out.close();
				fileOut.close();
				MazeDesignMainGUI.SetLoadedMazeFileLocation(mazeFilePath + ".ser");
				return true;
			}
			else {
				if(JOptionPane.showConfirmDialog(null, "Would you like to overwrite this file?") == JOptionPane.YES_OPTION) {
					FileOutputStream fileOut = new FileOutputStream(newMaze);
					ObjectOutputStream out = new ObjectOutputStream(fileOut);
					out.writeObject(ButtonFunctions.GetStartLabel());
					out.writeObject(ButtonFunctions.GetQ1());
					out.writeObject(ButtonFunctions.GetQ2());
					out.writeObject(ButtonFunctions.GetQ3());
					out.writeObject(ButtonFunctions.GetQ4());
					out.writeObject(ButtonFunctions.GetEndLabel());
					out.writeObject(ButtonFunctions.GetWallColorMap());
					out.writeObject(EnemyButtonFunctions.GetEnemy1());
					out.writeObject(EnemyButtonFunctions.GetEnemy2());
					out.writeObject(EnemyButtonFunctions.GetEnemy3());
					out.writeObject(MazeDesignMainGUI.GetMazePanel().getBackground());
					out.writeObject(MazeDesignMainGUI.getDecorationList());
					out.writeObject(MazeDesignMainGUI.GetBlackoutMazeSetting());
					out.writeObject(null);
					out.close();
					fileOut.close();
					MazeDesignMainGUI.SetLoadedMazeFileLocation(mazeFilePath + ".ser");
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
