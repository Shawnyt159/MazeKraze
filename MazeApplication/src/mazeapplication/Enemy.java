package mazeapplication;

import java.io.Serializable;

import javax.swing.JLabel;

public class Enemy implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6656273573724312125L;
	private JLabel startLabel = null;
	private JLabel endLocation = null;
	private long speed;
	private String imagePath;
	
	public Enemy(){
	}

	public JLabel getStartLabel() {
		return startLabel;
	}

	public void setStartLabel(JLabel startLabel) {
		this.startLabel = startLabel;
	}

	public JLabel getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(JLabel endLocation) {
		this.endLocation = endLocation;
	}
	
	public void SetSpeed(int userSpeed) {
		this.speed = 100/userSpeed;
	}
	
	public long GetSpeed() {
		return speed;
	}
	
	public int GetUserSpeed() {
		long userSpeed = 100/this.speed;
		return (int) userSpeed;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	
	
	
}
