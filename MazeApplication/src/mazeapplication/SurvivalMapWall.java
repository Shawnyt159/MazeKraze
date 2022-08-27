package mazeapplication;

import javax.swing.JLabel;

public class SurvivalMapWall{
	JLabel wall;
	public SurvivalMapWall(JLabel wall) {
		this.wall = wall;
	}
	public JLabel getWall() {
		return wall;
	}
	public void setWall(JLabel wall) {
		this.wall = wall;
	}
}
