package mazeapplication;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveFIleExplorer {
	public static void Load() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Mazes", "ser");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       SaveMaze(chooser.getSelectedFile().toString());
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
				out.writeObject(null);
				out.close();
				fileOut.close();
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
