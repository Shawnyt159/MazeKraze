package mazeapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveLoadedFile {
	public static boolean Save(String loadedMazeFileLocation) {
		File newMaze = new File(loadedMazeFileLocation);
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(newMaze);
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
			out.writeObject(ButtonFunctions.getPortalNodeList());
			out.writeObject(MazeDesignMainGUI.getPlayerImageLocation());
			out.writeObject(ButtonFunctions.getWinMessage().getMessage());
			out.writeObject(null);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
