package mazeapplication;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BlackoutMaze {
	private static JLabel player;
	private static boolean active = false;
	
	public static void InitiateBlackout(JLabel player, JPanel mazePanel) {
		BlackoutMaze.player = player;
		active = true;
		mazePanel.repaint();
	}
	
	public static void StopBlackout(JPanel mazePanel) {
		active = false;
		mazePanel.repaint();
	}
	
	public static JLabel getBlackoutPlayer() {
		return player;
	}
	
	public static boolean isActive() {
		return active;
	}
}
