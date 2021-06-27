package mazeapplication;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveMazeAsImage {
	public static void Load(JPanel mazePanel) {
		LookAndFeel old = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e) {
			e.printStackTrace();
			old = null;
		}
		
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "Mazes", "jpeg");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       SaveMaze(chooser.getSelectedFile().toString(), mazePanel);
	    }
	    
	    if(old != null) {
	    	 try {
	 	    	UIManager.setLookAndFeel(old);
	 	    }catch(Exception e) {
	 	    	e.printStackTrace();
	 	    }
	    }
	}
	
	
	
	private static void SaveMaze(String path, JPanel mazePanel) {
		((MazePanel) mazePanel).SaveImage(path);
	}
}
