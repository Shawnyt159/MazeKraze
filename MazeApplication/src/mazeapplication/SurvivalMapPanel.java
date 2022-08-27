package mazeapplication;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SurvivalMapPanel extends JPanel{
	/**
	 * Serialize ID
	 */
	private static final long serialVersionUID = 692346055067391493L;
	
	private boolean smallGridModeActive = false;
	private boolean mediumGridModeActive = false;
	private boolean largeGridModeActive = false;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(smallGridModeActive == true) {
			int smallSpace = 20;
			PaintGrid(smallSpace, g);
		}
		else if(mediumGridModeActive == true) {
			int mediumSpace = 30;
			PaintGrid(mediumSpace, g);
		}
		else if(largeGridModeActive == true) {
			int largeSpace = 40;
			PaintGrid(largeSpace, g);
		}
	}
	
	private void PaintGrid(int space, Graphics g) {
		g.setColor(Color.MAGENTA);
		int startX = 0; 
		int startY = 0;
		int endX = 720;
		int endY = 600;
		
		// Drawing vertically.
		for(int x = startX; x <= endX; x += space) {
			g.drawLine(x, startY, x, endY);
		}
		// Drawing horizontally. 
		for(int y = startY; y <= endY; y += space) {
			g.drawLine(startX, y, endX, y);
		}
	}

	public boolean isSmallGridModeActive() {
		return smallGridModeActive;
	}

	public boolean isMediumGridModeActive() {
		return mediumGridModeActive;
	}

	public boolean isLargeGridModeActive() {
		return largeGridModeActive;
	}

	public void setSmallGridModeActive(boolean smallGridModeActive) {
		this.smallGridModeActive = smallGridModeActive;
	}

	public void setMediumGridModeActive(boolean mediumGridModeActive) {
		this.mediumGridModeActive = mediumGridModeActive;
	}

	public void setLargeGridModeActive(boolean largeGridModeActive) {
		this.largeGridModeActive = largeGridModeActive;
	}
	
	
	
}
