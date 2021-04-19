package mazeapplication;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveMazeAsImage {
	public static void Load(JPanel mazePanel) {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Mazes", "jpeg");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       SaveMaze(chooser.getSelectedFile().toString(), mazePanel);
	    }
	}
	
	private static void SaveMaze(String path, JPanel mazePanel) {
		((MazePanel) mazePanel).SaveImage(path);
	}
}
