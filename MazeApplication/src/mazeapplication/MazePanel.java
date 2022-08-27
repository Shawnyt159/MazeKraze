package mazeapplication;

import java.awt.AWTException;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.geom.Area;
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
	
	private boolean deleteDecorationBoxActive;
	private Point deleteDecorationBoxStart;
	private Point deleteDecorationBoxEnd;
	private Area deleteDecorationBoxArea;
	
	private Point rectangleStart;
	private Point rectangleEnd;
	private boolean rectangleActive;
	private Color rectangleColor;
	private int rectangleSize;
	
	private boolean circleActive;
	private Point circleStart;
	private Point circleEnd;
	private int circleSize;
	private Color circleColor;
	
	private boolean decorationGridActive = false;
	private boolean smallDecorationGridModeOn = false;
	private boolean mediumDecorationGridModeOn = false;
	private boolean largeDecorationGridModeOn = false;
	private HashMap<Point, Rectangle> gridMap = new HashMap<Point, Rectangle>();
	
	private boolean portalConnectionsActive = false;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// IF THE MAZE IS A BLACKOUT MAZE THEN SHOWING EVEYRTHING AROUND THE PLAYER 
		if(BlackoutMaze.isActive() == true) {
			Point playerCenter = new Point(BlackoutMaze.getBlackoutPlayer().getX()+2, BlackoutMaze.getBlackoutPlayer().getY()+2);
			HashSet<Point> visibleAreaMap = new HashSet<Point>();
			
			for(int x = -44; x < 45; x++) {
				for(int y = -44; y < 45; y++) {
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
			
			Rectangle visibleAreaRectangle = new Rectangle((int) playerCenter.getX()-45, (int) playerCenter.getY()-45, 90, 90);
			LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
			ListIterator<DecorationNode> iterator = (ListIterator<DecorationNode>) decorationList.iterator();
			while(iterator.hasNext()) {
				JLabel decorationLabel = iterator.next().getDecorationNode();
				Rectangle decorationBounds = decorationLabel.getBounds();
				if(decorationBounds.intersects(visibleAreaRectangle) == true) {
					decorationLabel.setVisible(true);
				}
				else {
					decorationLabel.setVisible(false);
				}
			}
			
			LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
			for(int i = 0; i < portalList.size(); i++) {
				PortalNode currentPortal = portalList.get(i);
				if(currentPortal.getLinkStart() != null) {
					Rectangle currentPortalStartBounds = currentPortal.getLinkStart().getBounds();
					if(currentPortalStartBounds.intersects(visibleAreaRectangle) == true) {
						currentPortal.getLinkStart().setVisible(true);
					}
					else {
						currentPortal.getLinkStart().setVisible(false);
					}
				}
				if(currentPortal.getLinkEnd() != null) {
					Rectangle currentPortalEndBounds = currentPortal.getLinkEnd().getBounds();
					if(currentPortalEndBounds.intersects(visibleAreaRectangle) == true) {
						currentPortal.getLinkEnd().setVisible(true);
					}
					else {
						currentPortal.getLinkEnd().setVisible(false);
					}
				}
			}
			
			
		}
		
		// IF THE MAZE IS NOT A BLACK OUT MAZE.
		else {
			// DRAWING THE WALLS IN THE COORDINATE MAPS
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
			// DRAWING THE TRACE MAP IF THE MAZE IS NOT ACTIVE
			if(MazeDesignMainGUI.mazeActive() == false) {
				for(Map.Entry<Point, Integer> entry : ButtonFunctions.GetTracemap().entrySet()) {
					((Graphics2D) g).setStroke(new BasicStroke(entry.getValue()));
					Point coordinates = entry.getKey();
					g.setColor(new Color(255, 0, 255));
					g.drawLine((int) coordinates.getX(), (int) coordinates.getY(), (int) coordinates.getX(), (int) coordinates.getY());
					g.setColor(Color.black);
				}
			}
			
			// DRAWING THE ERASER SIZE AROUND THE CURSOR
			if(eraserActive == true) {
				if(eraserCoordinates != null) {
					g.setColor(Color.red);
					((Graphics2D) g).setStroke(new BasicStroke(1));
					g.drawRect((int) eraserCoordinates.getX()-((eraserThickness*2)/2), (int) eraserCoordinates.getY()-((eraserThickness*2)/2), eraserThickness*2, eraserThickness*2);
				}
			}
			
			// DRAWING THE BLUE DRAW SIZE ON THE CURSOR
			if(drawActive == true) {
				if(drawCoordinates != null) {
					g.setColor(Color.blue);
					((Graphics2D) g).setStroke(new BasicStroke(1));
					int rectangleX = (int) (drawCoordinates.getX()-((drawThickness/2)));
					int rectangleY = (int) (drawCoordinates.getY()-((drawThickness/2)));
					g.drawRect(rectangleX, rectangleY, drawThickness, drawThickness);
				}
			}
			
			// DRAWING DELETE DECORATION BOX
			if(deleteDecorationBoxActive == true && deleteDecorationBoxEnd != null) {
				((Graphics2D) g).setStroke(new BasicStroke(1)); 
				g.setColor(new Color(175,234,220));
				if((int) deleteDecorationBoxStart.getX() < (int) deleteDecorationBoxEnd.getX() && (int) deleteDecorationBoxStart.getY() < (int) deleteDecorationBoxEnd.getY()) {
					int deleteDecorationBoxX = (int) deleteDecorationBoxStart.getX();
					int deleteDecorationBoxY = (int) deleteDecorationBoxStart.getY();
					int deleteDecorationBoxWidth = (int) (deleteDecorationBoxEnd.getX() - deleteDecorationBoxX);
					int deleteDecorationBoxHeight = (int) (deleteDecorationBoxEnd.getY() - deleteDecorationBoxY);
					g.drawRect(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					Rectangle rect = new Rectangle(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					deleteDecorationBoxArea = new Area(rect);
				}
				else if((int) deleteDecorationBoxStart.getX() > (int) deleteDecorationBoxEnd.getX() && (int) deleteDecorationBoxStart.getY() > (int) deleteDecorationBoxEnd.getY()){
					int deleteDecorationBoxX = (int) deleteDecorationBoxEnd.getX();
					int deleteDecorationBoxY = (int) deleteDecorationBoxEnd.getY();
					int deleteDecorationBoxWidth = (int) (deleteDecorationBoxStart.getX() - deleteDecorationBoxX);
					int deleteDecorationBoxHeight = (int) (deleteDecorationBoxStart.getY() - deleteDecorationBoxY);
					g.drawRect(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					Rectangle rect = new Rectangle(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					deleteDecorationBoxArea = new Area(rect);
				}
				else if((int) deleteDecorationBoxStart.getX() > (int) deleteDecorationBoxEnd.getX() && (int) deleteDecorationBoxStart.getY() < (int) deleteDecorationBoxEnd.getY()) {
					int deleteDecorationBoxX = (int) deleteDecorationBoxEnd.getX();
					int deleteDecorationBoxY = (int) deleteDecorationBoxStart.getY();
					int deleteDecorationBoxWidth = (int) (deleteDecorationBoxStart.getX() - deleteDecorationBoxX);
					int deleteDecorationBoxHeight = (int) (deleteDecorationBoxEnd.getY() - deleteDecorationBoxY);
					g.drawRect(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					Rectangle rect = new Rectangle(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					deleteDecorationBoxArea = new Area(rect);
				}
				else if((int) deleteDecorationBoxStart.getX() < (int) deleteDecorationBoxEnd.getX() && (int) deleteDecorationBoxStart.getY() > (int) deleteDecorationBoxEnd.getY()) {
					int deleteDecorationBoxX = (int) deleteDecorationBoxStart.getX();
					int deleteDecorationBoxY = (int) deleteDecorationBoxEnd.getY();
					int deleteDecorationBoxWidth = (int) (deleteDecorationBoxEnd.getX() - deleteDecorationBoxX);
					int deleteDecorationBoxHeight = (int) (deleteDecorationBoxStart.getY() - deleteDecorationBoxY);
					g.drawRect(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					Rectangle rect = new Rectangle(deleteDecorationBoxX, deleteDecorationBoxY, deleteDecorationBoxWidth, deleteDecorationBoxHeight);
					deleteDecorationBoxArea = new Area(rect);
				}
			}
			
			//DRAWING A RECTANGLE
			if(rectangleActive == true && rectangleEnd != null) {
				g.setColor(rectangleColor);
				((Graphics2D) g).setStroke(new BasicStroke(rectangleSize));
				if((int) rectangleStart.getX() < (int) rectangleEnd.getX() && (int) rectangleStart.getY() < (int) rectangleEnd.getY()) {
					int rectangleX = (int) rectangleStart.getX();
					int rectangleY = (int) rectangleStart.getY();
					int rectangleWidth = (int) (rectangleEnd.getX() - rectangleX);
					int rectangleHeight = (int) (rectangleEnd.getY() - rectangleY);
					g.drawRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
				}
				else if((int) rectangleStart.getX() > (int) rectangleEnd.getX() && (int) rectangleStart.getY() > (int) rectangleEnd.getY()) {
					int rectangleX = (int) rectangleEnd.getX();
					int rectangleY = (int) rectangleEnd.getY();
					int rectangleWidth = (int) (rectangleStart.getX() - rectangleX);
					int rectangleHeight = (int) (rectangleStart.getY() - rectangleY);
					g.drawRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
				}
				else if((int) rectangleStart.getX() > (int) rectangleEnd.getX() && (int) rectangleStart.getY() < (int) rectangleEnd.getY()) {
					int rectangleX = (int) rectangleEnd.getX();
					int rectangleY = (int) rectangleStart.getY();
					int rectangleWidth = (int) (rectangleStart.getX() - rectangleX);
					int rectangleHeight = (int) (rectangleEnd.getY() - rectangleY);
					g.drawRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
				}
				else if((int) rectangleStart.getX() < (int) rectangleEnd.getX() && (int) rectangleStart.getY() > (int) rectangleEnd.getY()) {
					int rectangleX = (int) rectangleStart.getX();
					int rectangleY = (int) rectangleEnd.getY();
					int rectangleWidth = (int) (rectangleEnd.getX() - rectangleX);
					int rectangleHeight = (int) (rectangleStart.getY() - rectangleY);
					g.drawRect(rectangleX, rectangleY, rectangleWidth, rectangleHeight);
				}
			}
			// DRAWING A CIRCLE.
			if(circleActive == true) {
				g.setColor(circleColor);
				((Graphics2D) g).setStroke(new BasicStroke(circleSize));
				int radius = (int) (circleEnd.getX() - circleStart.getX());
				int circleX = (int) circleStart.getX();
				int circleY = (int) circleStart.getY();
				g.drawOval(circleX, circleY, radius, radius);
			}
			
			// DRAWING DECORATIONS GRID IF ACTIVE.
			if(decorationGridActive == true) {
				((Graphics2D) g).setStroke(new BasicStroke(1));
				g.setColor(new Color(255, 0, 255));
				if(smallDecorationGridModeOn == true) {
					int startX = 4;
					int startY = 4;
					int endX = 704;
					int endY = 644;
					int space = 10;
					for(int x = startX; x <= endX; x += space) {
						g.drawLine(x, startY, x, endY);
					}
					for(int y = startY; y <= endY; y += space) {
						g.drawLine(startX, y, endX, y);
					}
					MakeDecorationGridMap(startX, startY, endX, endY, space);
					ButtonFunctions.setDecorationMap(gridMap);
				}
				else if(mediumDecorationGridModeOn == true) {
					int startX = 4;
					int startY = 4;
					int endX = 704;
					int endY = 644;
					int space = 20;
					for(int x = startX; x <= endX; x += space) {
						g.drawLine(x, startY, x, endY);
					}
					for(int y = startY; y <= endY; y += space) {
						g.drawLine(startX, y, endX, y);
					}
					MakeDecorationGridMap(startX, startY, endX, endY, space);
					ButtonFunctions.setDecorationMap(gridMap);
				} 
				else if(largeDecorationGridModeOn == true) {
					int startX = 9;
					int startY = 9;
					int endX = 699;
					int endY = 639;
					int space = 30;
					for(int x = startX; x <= endX; x += space) {
						g.drawLine(x, startY, x, endY);
					}
					for(int y = startY; y <= endY; y += space) {
						g.drawLine(startX, y, endX, y);
					}
					MakeDecorationGridMap(startX, startY, endX, endY, space);
					ButtonFunctions.setDecorationMap(gridMap);
				}
				g.setColor(Color.black);
			}
			
			// DRAWING LINES BETWEEN PORTALS TO INDICATE CONNECTIONS.
			if(portalConnectionsActive == true && MazeDesignMainGUI.mazeActive() == false) {
				LinkedList<PortalNode> portalList = ButtonFunctions.getPortalNodeList();
				((Graphics2D) g).setStroke(new BasicStroke(1));
				g.setColor(new Color(84,255,159));
				for(int i = 0; i < portalList.size(); i++) {
					PortalNode currentPortalNode = portalList.get(i);
					if(currentPortalNode.getLinkStart() != null && currentPortalNode.getLinkEnd() != null) {
						JLabel startPortal = currentPortalNode.getLinkStart();
						JLabel endPortal = currentPortalNode.getLinkEnd();
						int startPortalX = startPortal.getX() + 15;
						int startPortalY = startPortal.getY() + 15;
						int endPortalX = endPortal.getX() + 15;
						int endPortalY = endPortal.getY() + 15;
						g.drawLine(startPortalX, startPortalY, endPortalX, endPortalY);
					}
				}
				g.setColor(Color.black);
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
	public void SetDeleteDecorationBoxStart(Point mouseCoordinates) {
		deleteDecorationBoxStart = mouseCoordinates;
	}
	public void SetDeleteDecorationBoxEnd(Point mouseCoordinates) {
		deleteDecorationBoxEnd = mouseCoordinates;
	}
	public void SetDeleteDecorationBoxActive(boolean active) {
		deleteDecorationBoxActive = active;
	}
	public Area getDeleteDecorationBoxArea() {
		return deleteDecorationBoxArea;
	}
	public void SetRectangleActive(boolean active) {
		rectangleActive = active;
	}
	public void SetRectangleStart(Point rectangleStart) {
		this.rectangleStart = rectangleStart;
	}
	public void SetRectangleEnd(Point rectangleEnd) {
		this.rectangleEnd = rectangleEnd;
	}
	public void SetRectangleColor(Color rectangleColor) {
		this.rectangleColor = rectangleColor;
	}
	public void SetRectangleSize(int rectangleSize) {
		this.rectangleSize = rectangleSize;
	}
	public boolean GetRectangleActive() {
		return rectangleActive;
	}
	public HashMap<Point, Integer> GetRectangleMap(){
		HashMap<Point, Integer> rectangleMap = new HashMap<Point, Integer>();
		if(rectangleEnd == null) {
			return rectangleMap;
		}
		if((int) rectangleStart.getX() < (int) rectangleEnd.getX() && (int) rectangleStart.getY() < (int) rectangleEnd.getY()) {
			int rectangleX = (int) rectangleStart.getX();
			int rectangleY = (int) rectangleStart.getY();
			int rectangleWidth = (int) (rectangleEnd.getX() - rectangleX);
			int rectangleHeight = (int) (rectangleEnd.getY() - rectangleY);
			for(int x = rectangleX; x < rectangleWidth+rectangleX; x++) {
				Point currentPoint = new Point(x, rectangleY);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleHeight+rectangleY; y++) {
				Point currentPoint = new Point(rectangleX+rectangleWidth, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int x = rectangleX; x < rectangleX+rectangleWidth; x++) {
				Point currentPoint = new Point(x, rectangleY+rectangleHeight);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleY+rectangleHeight; y++) {
				Point currentPoint = new Point(rectangleX, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			return rectangleMap;
		}
		else if((int) rectangleStart.getX() > (int) rectangleEnd.getX() && (int) rectangleStart.getY() > (int) rectangleEnd.getY()) {
			int rectangleX = (int) rectangleEnd.getX();
			int rectangleY = (int) rectangleEnd.getY();
			int rectangleWidth = (int) (rectangleStart.getX() - rectangleX);
			int rectangleHeight = (int) (rectangleStart.getY() - rectangleY);
			for(int x = rectangleX; x < rectangleWidth+rectangleX; x++) {
				Point currentPoint = new Point(x, rectangleY);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleHeight+rectangleY; y++) {
				Point currentPoint = new Point(rectangleX+rectangleWidth, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int x = rectangleX; x < rectangleX+rectangleWidth; x++) {
				Point currentPoint = new Point(x, rectangleY+rectangleHeight);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleY+rectangleHeight; y++) {
				Point currentPoint = new Point(rectangleX, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			return rectangleMap;
		}
		else if((int) rectangleStart.getX() > (int) rectangleEnd.getX() && (int) rectangleStart.getY() < (int) rectangleEnd.getY()) {
			int rectangleX = (int) rectangleEnd.getX();
			int rectangleY = (int) rectangleStart.getY();
			int rectangleWidth = (int) (rectangleStart.getX() - rectangleX);
			int rectangleHeight = (int) (rectangleEnd.getY() - rectangleY);
			for(int x = rectangleX; x < rectangleWidth+rectangleX; x++) {
				Point currentPoint = new Point(x, rectangleY);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleHeight+rectangleY; y++) {
				Point currentPoint = new Point(rectangleX+rectangleWidth, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int x = rectangleX; x < rectangleX+rectangleWidth; x++) {
				Point currentPoint = new Point(x, rectangleY+rectangleHeight);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleY+rectangleHeight; y++) {
				Point currentPoint = new Point(rectangleX, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			return rectangleMap;
		}
		else if((int) rectangleStart.getX() < (int) rectangleEnd.getX() && (int) rectangleStart.getY() > (int) rectangleEnd.getY()) {
			int rectangleX = (int) rectangleStart.getX();
			int rectangleY = (int) rectangleEnd.getY();
			int rectangleWidth = (int) (rectangleEnd.getX() - rectangleX);
			int rectangleHeight = (int) (rectangleStart.getY() - rectangleY);
			for(int x = rectangleX; x < rectangleWidth+rectangleX; x++) {
				Point currentPoint = new Point(x, rectangleY);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleHeight+rectangleY; y++) {
				Point currentPoint = new Point(rectangleX+rectangleWidth, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int x = rectangleX; x < rectangleX+rectangleWidth; x++) {
				Point currentPoint = new Point(x, rectangleY+rectangleHeight);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			for(int y = rectangleY; y < rectangleY+rectangleHeight; y++) {
				Point currentPoint = new Point(rectangleX, y);
				rectangleMap.put(currentPoint, rectangleSize);
			}
			return rectangleMap;
		}
		return null;
	}
	
	private void MakeDecorationGridMap(int startX, int startY, int endX, int endY, int space) {
		HashMap<Point, Rectangle> gridMap = new HashMap<Point, Rectangle>();
		for(int x = startX; x < endX; x += space) {
			for(int y = startY; y < endY; y += space) {
				Point gridLeftCorner = new Point(x,y);
				Rectangle gridRectangle = new Rectangle(x,y,space,space);
				gridMap.put(gridLeftCorner, gridRectangle);
			}
		}
		this.gridMap = gridMap;
	}

	public void setCircleActive(boolean circleActive) {
		this.circleActive = circleActive;
	}

	public void setCircleStart(Point circleStart) {
		this.circleStart = circleStart;
	}

	public void setCircleEnd(Point circleEnd) {
		this.circleEnd = circleEnd;
	}
	
	public void setCircleSize(int circleSize) {
		this.circleSize = circleSize;
	}

	public void setCircleColor(Color circleColor) {
		this.circleColor = circleColor;
	}
	
	public boolean isSmallDecorationGridModeOn() {
		return smallDecorationGridModeOn;
	}

	public boolean isMediumDecorationGridModeOn() {
		return mediumDecorationGridModeOn;
	}

	public boolean isLargeDecorationGridModeOn() {
		return largeDecorationGridModeOn;
	}
	
	public boolean getDecorationGridActive() {
		return this.decorationGridActive;
	}

	public void setSmallDecorationGridModeOn(boolean smallDecorationGridModeOn) {
		this.smallDecorationGridModeOn = smallDecorationGridModeOn;
	}

	public void setMediumDecorationGridModeOn(boolean mediumDecorationGridModeOn) {
		this.mediumDecorationGridModeOn = mediumDecorationGridModeOn;
	}

	public void setLargeDecorationGridModeOn(boolean largeDecorationGridModeOn) {
		this.largeDecorationGridModeOn = largeDecorationGridModeOn;
	}
	
	public void setDecorationGridActive(boolean decorationGridActive) {
		this.decorationGridActive = decorationGridActive;
		this.repaint();
	}
	
	public void setPortalConnectionsActive(boolean portalConnectionsActive) {
		this.portalConnectionsActive = portalConnectionsActive;
	}
	
	public boolean getPortalConnectionsActive() {
		return portalConnectionsActive;
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
