package mazeapplication;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MazePanel extends JPanel{
	private boolean eraserActive = false;
	private int eraserThickness;
	private Point eraserCoordinates;
	
	private boolean drawActive = false;
	private int drawThickness;
	private Point drawCoordinates;
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(BlackoutMaze.isActive() == true) {
			Point playerCenter = new Point(BlackoutMaze.getBlackoutPlayer().getX()+2, BlackoutMaze.getBlackoutPlayer().getY()+2);
			HashSet<Point> visibleAreaMap = new HashSet<Point>();
			
			for(int x = -30; x < 30; x++) {
				for(int y = -30; y < 30; y++) {
					Point coordinates = new Point((int) playerCenter.getX()+x, (int) playerCenter.getY()+y);
					visibleAreaMap.add(coordinates);
				}
			}
			
			Iterator<Point> visibleAreaIterator = visibleAreaMap.iterator();
			while(visibleAreaIterator.hasNext()) {
				Point nextPoint = visibleAreaIterator.next();
				HashMap<Point, Integer> correctMap = ButtonFunctions.GetCorrectQuadrantMap(nextPoint);
				if(correctMap.containsKey(nextPoint)) {
					DrawCurrentCoordinate(g, nextPoint, correctMap.get(nextPoint));
				}
			}
			
			LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
			ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
			while(iterator.hasNext()) {
				JLabel decorationLabel = iterator.next().getDecorationNode();
				Point decorationLocation = decorationLabel.getLocation();
				if(visibleAreaMap.contains(decorationLocation)) {
					decorationLabel.setVisible(true);
				}
				else {
					decorationLabel.setVisible(false);
				}
			}
		}
		else {
			for(Map.Entry<Point, Integer> entery : ButtonFunctions.GetQ1().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : ButtonFunctions.GetQ2().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : ButtonFunctions.GetQ3().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : ButtonFunctions.GetQ4().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			if(eraserActive == true) {
				if(eraserCoordinates != null) {
					g.setColor(Color.red);
					((Graphics2D) g).setStroke(new BasicStroke(2));
					g.drawRect((int) eraserCoordinates.getX()-((eraserThickness*2)/2), (int) eraserCoordinates.getY()-((eraserThickness*2)/2), eraserThickness*2, eraserThickness*2);
				}
			}
			if(drawActive == true) {
				if(drawCoordinates != null) {
					g.setColor(Color.blue);
					((Graphics2D) g).setStroke(new BasicStroke(2));
					g.drawRect((int) drawCoordinates.getX()-(drawThickness/2), (int) drawCoordinates.getY()-(drawThickness/2), drawThickness, drawThickness);
				}
			}
		}
	}
	
	// Drawing on the coordinate with the color and the thickness.
	private void DrawCurrentCoordinate(Graphics g, Map.Entry<Point, Integer> entery) {
		Point coordinates = entery.getKey();
		int lineThickness = entery.getValue();
		((Graphics2D) g).setStroke(new BasicStroke(lineThickness));
		g.setColor(ButtonFunctions.GetWallColorMap().get(coordinates));
		g.drawLine((int) coordinates.getX(), (int) coordinates.getY(), (int) coordinates.getX(), (int) coordinates.getY());
		g.setColor(Color.black);
	}
	
	private void DrawCurrentCoordinate(Graphics g, Point coordinates, int lineThickness) {
		((Graphics2D) g).setStroke(new BasicStroke(lineThickness));
		g.setColor(ButtonFunctions.GetWallColorMap().get(coordinates));
		g.drawLine((int) coordinates.getX(), (int) coordinates.getY(), (int) coordinates.getX(), (int) coordinates.getY());
		g.setColor(Color.black);
	}
	
	public void SetEraserOn() {
		this.eraserActive = true;
	}
	public void SetEraserOff() {
		this.eraserActive = false;
	}
	
	public void SetEraserThickness(int eraserThickness) {
		this.eraserThickness = eraserThickness *2;
	}
	
	public void SetEraserCoordinates(Point eraserCoordinates) {
		this.eraserCoordinates = eraserCoordinates;
	}
	
	public void SetDrawOn() {
		this.drawActive = true;
	}
	public void SetDrawOff() {
		this.drawActive = false;
	}
	public void SetDrawThickness(int drawThickness) {
		this.drawThickness = drawThickness;
	}
	public void SetDrawCoordinates(Point drawCoordinates) {
		this.drawCoordinates = drawCoordinates;
	}
	// Saving the current panel as an image.
	public void SaveImage(String path) {
		BufferedImage image = null;
		try {
	        image = new Robot().createScreenCapture(this.getBounds());
	    } catch (AWTException e1) {
	        e1.printStackTrace();
	    }  
		Graphics2D graphics2D = image.createGraphics();
		this.printAll(graphics2D);
		try {
			File newMazeImage = new File(path + ".jpeg");
			if(newMazeImage.createNewFile()) {
				ImageIO.write(image,"jpeg", newMazeImage);
			}
			else {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to overwrite this file?") == JOptionPane.YES_OPTION) {
					ImageIO.write(image, "jpeg", newMazeImage);
				}
			}
			JOptionPane.showMessageDialog(null, "Maze Saved As Image!");
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        System.out.println("error");
	    }
	}
	
}
