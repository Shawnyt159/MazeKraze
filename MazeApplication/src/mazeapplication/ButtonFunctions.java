package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ButtonFunctions {
	// Global to set an image to a JLabel. 
	private static SetImageToLabel images = new SetImageToLabel();
	
	//Start Location variables.
	private static JLabel startLocation = new JLabel("");
	private static boolean startLocationExists = false;
	private static int startLocationZ = 5;
	
	//End Location Variables. 
	private static JLabel endLocation = new JLabel("");
	private static boolean endLocationExists = false;
	private static int endLocationZ = 10;
	
	// Quadrant maps for walls. 
	private static HashMap<Point, Integer> q1WallCoordinates = new HashMap<Point, Integer>(); 
	private static HashMap<Point, Integer> q2WallCoordinates = new HashMap<Point, Integer>();
	private static HashMap<Point, Integer> q3WallCoordinates = new HashMap<Point, Integer>();
	private static HashMap<Point, Integer> q4WallCoordinates = new HashMap<Point, Integer>();
	
	//Trace Map for maze.
	private static HashMap<Point, Integer> traceMap = new HashMap<Point, Integer>();
	
	//Wall Color Map.
	private static HashMap<Point, Color> wallColorMap = new HashMap<Point, Color>();
	
	private static HashMap<Point, Rectangle> decorationMap = new HashMap<Point, Rectangle>();
	private static LineBorder deleteBorder = new LineBorder(Color.red, 2);
	
	private static LinkedList<PortalNode> portalNodeList = new LinkedList<PortalNode>();
	
	private static int horizontalLineY;
	private static int horizontalLineX;
	private static int verticalLineX;
	private static int verticalLineY;
	
	private static ImageIcon optionImageIcon;
	private static GetFileAttributes fileAttributesGetter = new GetFileAttributes();
	
	private static WinMessage winMessage = new WinMessage();
	
	/**
	 * START LOCATION FUNCTIONS:
	 */
	
	/*
	 * Logic for startLocation of maze.
	 */
	public static void StartLocationFunction(JPanel mazePanel, String playerType) {
		String playerPath = fileAttributesGetter.GetItemFilePath(playerType);
		if(startLocationExists == false) {
			SetStartLocationCoordinates(mazePanel);
			mazePanel.add(startLocation);
			images.set_image_to_label(startLocation, "/images/" + playerPath);
		}
		else {
			Point coordinates = GetMouseCoordinates(mazePanel);
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to change the start location?", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, optionImageIcon) == JOptionPane.YES_OPTION) {
				images.set_image_to_label(startLocation, "/images/" + playerPath);
				MazeDesignMainGUI.setPlayerImageLocation("/images/" + playerPath);
				ChangeStartLocationCoordinates(coordinates);
			} 
			else {
			   // Do nothing.
			}
		}
	}
	
	/*
	 * Setting the new location for the startLocation.
	 */
	private static void SetStartLocationCoordinates(JPanel mazePanel) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		startLocation.setBounds((int) coordinates.getX()-2, (int) coordinates.getY()-2, startLocationZ, startLocationZ);
		startLocationExists = true;
	}
	
	/*
	 * Setting the start location from a loaded maze. 
	 */
	public static void SetStartLocationCoordinatesLoad(JPanel mazePanel, JLabel newStartLocation) {
		if(startLocationExists == false) {
			startLocation = newStartLocation;
			mazePanel.add(startLocation);
			startLocationExists = true;
		}
		else {
			mazePanel.remove(startLocation);
			startLocation = newStartLocation;
			mazePanel.add(startLocation);
		}
	}
	
	/*
	 * Changing the start location.
	 */
	private static void ChangeStartLocationCoordinates(Point coordinates) {
		startLocation.setBounds((int) coordinates.getX()-2, (int) coordinates.getY()-2, startLocationZ, startLocationZ);
	}
	
	public static void ChangePlayerImage(String newImagePath) {
		if(startLocationExists == true) {
			images.set_image_to_label(startLocation, newImagePath);
		}
	}
	
	/**
	 * END LOCATION FUNCTIONS: 
	 */
	
	/*
	 * Setting the end location for the maze.
	 */
	public static void EndLocationFunction(JPanel mazePanel) {
		if(endLocationExists == false) {
			SetEndLocationCoordinates(mazePanel);
			mazePanel.add(endLocation);
			images.set_image_to_label(endLocation, "/images/X.png");
		}
		else {
			Point coordinates = GetMouseCoordinates(mazePanel);
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to change the end location?", "WARNING", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, optionImageIcon) == JOptionPane.YES_OPTION) {
				ChangeEndLocationCoordinates(coordinates);
			} 
			else {
			   // Do nothing.
			}
		}
		
	}
	
	/*
	 * Setting the original location of the endLocation.
	 */
	private static void SetEndLocationCoordinates(JPanel mazePanel) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		endLocation.setBounds((int) coordinates.getX()-5, (int) coordinates.getY()-5, endLocationZ, endLocationZ);
		endLocationExists = true;
	}
	
	/*
	 * Setting the end location from a loaded maze.
	 */
	public static void SetEndLocationCoordinatesLoad(JPanel mazePanel, JLabel newEndLocation) {
		if(endLocationExists == false) {
			endLocation = newEndLocation;
			mazePanel.add(endLocation);
			endLocationExists = true;
		}
		else {
			mazePanel.remove(endLocation);
			endLocation = newEndLocation;
			mazePanel.add(endLocation);
		}
	}
	
	/*
	 * Changing the location of the endLocation.
	 */
	private static void ChangeEndLocationCoordinates(Point coordinates) {
		endLocation.setBounds((int) coordinates.getX()-5, (int) coordinates.getY()-5, endLocationZ, endLocationZ);
	}
	
	
	/**
	 * DRAW LINE FUNCTIONS:
	 */
	public static void DrawLineFunction(JPanel mazePanel, int lineThickness, Color lineColor) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(coordinates);
		if(qmap == null) {
			return;
		}
		DrawCoordinate(mazePanel, lineThickness, coordinates, qmap, lineColor);
	}
	
	public static void DrawHorizontalLineFunction(JPanel mazePanel, int lineThickness, Color lineColor) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		if(coordinates == null) {
			return;
		}
		Point horizontalPoint = new Point((int) coordinates.getX(), horizontalLineY);
		//Right to left drawing (User is drawing left to right).
		if((int) horizontalPoint.getX() < horizontalLineX) {
			int coordinateX = (int) horizontalPoint.getX();
			while(coordinateX != horizontalLineX) {
				Point wallAddition = new Point(coordinateX, horizontalLineY);
				HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(wallAddition);
				DrawCoordinate(mazePanel, lineThickness, wallAddition, qmap, lineColor);
				coordinateX++;
			}
		}
		//Left to right drawing (User is drawing right to left).
		else {
			int coordinateX = (int) horizontalPoint.getX();
			while(coordinateX != horizontalLineX) {
				Point wallAddition = new Point(coordinateX, horizontalLineY);
				HashMap<Point,Integer> qmap = GetCorrectQuadrantMap(wallAddition);
				DrawCoordinate(mazePanel, lineThickness, wallAddition, qmap, lineColor);
				coordinateX--;
			}
		}
	}
	
	public static void DrawVerticalLineFunction(JPanel mazePanel, int lineThickness, Color lineColor) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		if(coordinates == null) {
			return;
		}
		Point verticalPoint = new Point(verticalLineX, (int) coordinates.getY());
		//Up to down drawing (User is drawing down to up).
		if((int) verticalPoint.getY() < verticalLineY) {
			int coordinateY = (int) verticalPoint.getY();
			while(coordinateY != verticalLineY) {
				Point wallAddition = new Point(verticalLineX, coordinateY);
				HashMap<Point,Integer> qmap = GetCorrectQuadrantMap(wallAddition);
				DrawCoordinate(mazePanel, lineThickness, wallAddition, qmap, lineColor);
				coordinateY++;
			}
		}
		//Down to up drawing (User is drawing up to down).
		else {
			int coordinateY = (int) verticalPoint.getY();
			while(coordinateY != verticalLineY) {
				Point wallAddition = new Point(verticalLineX, coordinateY);
				HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(wallAddition);
				DrawCoordinate(mazePanel, lineThickness, wallAddition, qmap, lineColor);
				coordinateY--;
			}
		}
	}
	
	public static void AddRectangleToCoordinateMaps(HashMap<Point, Integer> rectangle, Color rectangleColor) {
		if(rectangle == null) {
			return;
		}
		for(Map.Entry<Point, Integer> entery : rectangle.entrySet()) {
			Point coordinates = entery.getKey();
			int lineThickness = entery.getValue();
			GetCorrectQuadrantMap(coordinates).put(coordinates, lineThickness);
			wallColorMap.put(coordinates, rectangleColor);
			UndoStructure.AddPointToNewLineArrayList(coordinates);
		}
	}
	
	/*
	 * Drawing on the current coordinate with the specific thickness.
	 */
	private static void DrawCoordinate(JPanel mazePanel, int lineThickness, Point coordinates, HashMap<Point, Integer> qmap, Color color) {
		qmap.put(coordinates, lineThickness);
		AddCoordinateAndColorToColorMap(coordinates, color);
		UndoStructure.AddPointToNewLineArrayList(coordinates);
		mazePanel.repaint();
	}
	
	public static void DrawTraceMap(JPanel mazePanel, Point coordinates) {
		if(coordinates != null) {
			traceMap.put(coordinates, 3);
			mazePanel.repaint();
		}
	}
	
	private static void AddCoordinateAndColorToColorMap(Point coordinates, Color color) {
		wallColorMap.put(coordinates, color);
	}
	
	/**
	 * ERASE LINE FUNCTIONS.
	 */
	public static void EraseCoordinate(JPanel mazePanel, int thickness) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		if(coordinates == null) {
			return;
		}
		HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(coordinates);
		thickness = thickness * 2;
		int negativeThickness = thickness * -1;
		for(int x = negativeThickness; x < thickness; x++) {
			for(int y = negativeThickness; y < thickness; y++) {
				Point newCoordinate = new Point((int) coordinates.getX() + x, (int) coordinates.getY() + y);
				if(qmap.containsKey(newCoordinate)) {
					Color lineColor = Color.black;
					if(wallColorMap.containsKey(newCoordinate)) {
						lineColor = wallColorMap.get(newCoordinate);
					}
					RedoErasedLineStructure.AddPointToRedoStacks(newCoordinate, qmap.get(newCoordinate), lineColor);
					qmap.remove(newCoordinate);
					wallColorMap.remove(newCoordinate);
					mazePanel.repaint();
				}
			}
		}
	}
	
	/**
	 * DELETE DECORATION FUNCTIONS.
	 */
	public static void AddBordersToDecorations(LinkedList<DecorationNode> decorationList, JPanel mazePanel) {
		Iterator<DecorationNode> iterator = decorationList.iterator();
		Border border = new LineBorder(Color.green, 2);
		while(iterator.hasNext()) {
			iterator.next().getDecorationNode().setBorder(border);
		}
	}
	
	public static void CheckAreaOfDeleteBoxWithAreaOfDecorationsAndChangeBorderIfIntersecting(Area deleteArea) {
		if(deleteArea == null) {
			return;
		}
		LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
		for(int i = 0; i < decorationList.size(); i++) {
			JLabel decorationLabel = decorationList.get(i).getDecorationNode();
			Rectangle2D decorationRectangle = decorationLabel.getBounds();
			if(deleteArea.intersects(decorationRectangle)) {
				decorationLabel.setBorder(deleteBorder);
			}
			if(!deleteArea.intersects(decorationRectangle) && decorationLabel.getBorder().equals(deleteBorder)) {
				decorationLabel.setBorder(new LineBorder(Color.green, 2));
			}
		}
	}
	
	public static void RemoveDecorationsThatAreInAreaOfDeletion() {
		LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
		LinkedList<DecorationNode> decorationListToDelete = new LinkedList<DecorationNode>();
		for(int i = 0; i < decorationList.size(); i++) {
			if(decorationList.get(i).getDecorationNode().getBorder().equals(deleteBorder)) {
				decorationListToDelete.add(decorationList.get(i));
			}
		}
		decorationList.removeAll(decorationListToDelete);
		for(int i = 0; i < decorationListToDelete.size(); i++) {
			MazeDesignMainGUI.GetMazePanel().remove(decorationListToDelete.get(i).getDecorationNode());
		}
	}
	
	public static void RemoveBordersFromDecorations(LinkedList<DecorationNode> decorationList, JPanel mazePanel) {
		Iterator<DecorationNode> iterator = decorationList.iterator();
		while(iterator.hasNext()) {
			iterator.next().getDecorationNode().setBorder(null);
		}
	}
	
	public static void AddMouseListenerToDecorations(LinkedList<DecorationNode> decorationList, JPanel mazePanel) {
		Iterator<DecorationNode> iterator = decorationList.iterator();
		while(iterator.hasNext()) {
			iterator.next().getDecorationNode().addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					JLabel currentDecoration = (JLabel) arg0.getComponent();
					mazePanel.remove(currentDecoration);
					for(int i = 0; i < decorationList.size(); i++) {
						DecorationNode currentNode = decorationList.get(i);
						if(currentNode.getDecorationNode() == currentDecoration) {
							decorationList.remove(currentNode);
							break;
						}
					}
					mazePanel.repaint();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					JLabel currentDecoration = (JLabel) arg0.getComponent();
					currentDecoration.setBorder(new LineBorder(Color.red, 2));
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					JLabel currentDecoration = (JLabel) arg0.getComponent();
					currentDecoration.setBorder(new LineBorder(Color.green, 2));
					
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}
				@Override
				public void mouseReleased(MouseEvent arg0) {
				}
			});
		}
	}
	
	public static void RemoveMouseListenersFromDecorations(LinkedList<DecorationNode> decorationList, JPanel mazePanel) {
		Iterator<DecorationNode> iterator = decorationList.iterator();
		while(iterator.hasNext()) {
			JLabel currentDecoration = iterator.next().getDecorationNode();
			MouseListener[] listeners = currentDecoration.getMouseListeners();
			for(int i = 0; i < listeners.length; i++) {
				currentDecoration.removeMouseListener(listeners[i]);
			}
		}
	}
	
	/**
	 * MUTUAL FUNCTIONS:
	 */
	
	/*
	 * Returning the mouse coordinates in the mazePanel. 
	 */
	private static Point GetMouseCoordinates(JPanel mazePanel) {
		Point mouseCoordinates = mazePanel.getMousePosition();
		return mouseCoordinates;
	}
	
	/*
	 * Getting the correct quadrant map based on the users current location.
	 */
	public static HashMap<Point, Integer> GetCorrectQuadrantMap(Point coordinates){
		if(coordinates != null) {
			int x = (int) coordinates.getX();
			int y = (int) coordinates.getY();
			if(x <= 354) {
				if(y <= 324) {
					return q1WallCoordinates;
				}
				else {
					return q3WallCoordinates;
				}
			}
			else {
				if(y <= 324) {
					return q2WallCoordinates;
				}
				else {
					return q4WallCoordinates;
				}
			}
		}
		return null;
	}
	
	public static Point GetCorrectLeftCornerValue(Point mousePosition) {
		if(mousePosition != null) {
			for(Map.Entry<Point, Rectangle> entry : decorationMap.entrySet()) {
				if(entry.getValue().contains(mousePosition)) {
					return entry.getKey();
				}
			}
			return null;
		}
		return null;
	}
	
	public static void DeleteDecorationToReplace(Point leftCorner) {
		LinkedList<DecorationNode> decorationList = MazeDesignMainGUI.getDecorationList();
		DecorationNode nodeToDelete = null;
		for(int i = 0; i < decorationList.size(); i++) {
			if(decorationList.get(i).getDecorationNode().getX() == (int) leftCorner.getX() && decorationList.get(i).getDecorationNode().getY() == (int) leftCorner.getY()) {
				nodeToDelete = decorationList.get(i);
			}
		}
		if(nodeToDelete != null) {
			decorationList.remove(nodeToDelete);
			MazeDesignMainGUI.GetMazePanel().remove(nodeToDelete.getDecorationNode());
		}
	}
	
	/*
	 * Portal Functions.
	 */
	
	public static boolean CheckStartPortalLocationLegal(Rectangle newStartPortalBounds, PortalNode currentPortal) {
		for(int i = 0; i < portalNodeList.size(); i++) {
			PortalNode currentPortalNode = portalNodeList.get(i);
			if(currentPortalNode.equals(currentPortal)) {
				boolean isValid = CheckIfPortalValidLocation(newStartPortalBounds, currentPortalNode.getLinkEnd());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to the end portal.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
			}
			else {
				boolean isValid = CheckIfPortalValidLocation(newStartPortalBounds, currentPortalNode.getLinkStart());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to another portal.","", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
				isValid = CheckIfPortalValidLocation(newStartPortalBounds, currentPortalNode.getLinkEnd());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to another portal.","", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
			}
		}
		boolean isValid = CheckIfInsidePortalLegalArea(newStartPortalBounds);
		if(isValid == false) {
			JOptionPane.showMessageDialog(null, "You can't put a portal that close to the edge of the map.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
			return false;
		}
		return true;
	}
	
	public static boolean CheckEndPortalLocationLegal(Rectangle newEndPortalBounds, PortalNode currentPortal) {
		for(int i = 0; i < portalNodeList.size(); i++) {
			PortalNode currentPortalNode = portalNodeList.get(i);
			if(currentPortalNode.equals(currentPortal)) {
				boolean isValid = CheckIfPortalValidLocation(newEndPortalBounds, currentPortalNode.getLinkStart());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to the start portal.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
			}
			else {
				boolean isValid = CheckIfPortalValidLocation(newEndPortalBounds, currentPortalNode.getLinkStart());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to another portal.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
				isValid = CheckIfPortalValidLocation(newEndPortalBounds, currentPortalNode.getLinkEnd());
				if(isValid == false) {
					JOptionPane.showMessageDialog(null, "You can't put a portal so close to another portal.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
					return false;
				}
			}
		}
		boolean isValid = CheckIfInsidePortalLegalArea(newEndPortalBounds);
		if(isValid == false) {
			JOptionPane.showMessageDialog(null, "You can't put a portal that close to the edge of the map.", "", JOptionPane.INFORMATION_MESSAGE, optionImageIcon);
			return false;
		}
		return true;
	}
	
	private static boolean CheckIfPortalValidLocation(Rectangle newPortalBounds, JLabel portalToCheck) {
		if(portalToCheck == null) {
			return true;
		}
		Rectangle portalToCheckBounds = new Rectangle(portalToCheck.getX()-10, portalToCheck.getY()-10, portalToCheck.getHeight()+20, portalToCheck.getWidth()+20);
		if(newPortalBounds.intersects(portalToCheckBounds)) {
			return false;
		}
		return true;
	}
	
	private static boolean CheckIfInsidePortalLegalArea(Rectangle newPortalBounds) {
		Rectangle portalLegalArea = new Rectangle(10,10,688,628);
		if(portalLegalArea.contains(newPortalBounds)) {
			return true;
		}
		return false;
	}
	
	public static void removeBordersFromPortals(JPanel mazePanel) {
		
		for(int i = 0; i < portalNodeList.size(); i++) {
			PortalNode currentPortal = portalNodeList.get(i);
			if(currentPortal.getLinkStart() != null) {
				currentPortal.getLinkStart().setBorder(null);
			}
			if(currentPortal.getLinkEnd() != null) {
				currentPortal.getLinkEnd().setBorder(null);
			}
		}
		
	}
	
	public static void setDecorationMap(HashMap<Point, Rectangle> gridMap) {
		decorationMap = gridMap;
	}
	
	public static void SetQ1(HashMap<Point, Integer> newq1WallCoordinates) {
		q1WallCoordinates = newq1WallCoordinates;
	}
	
	public static void SetQ2(HashMap<Point, Integer> newq2WallCoordinates) {
		q2WallCoordinates = newq2WallCoordinates;
	}
	
	public static void SetQ3(HashMap<Point, Integer> newq3WallCoordinates) {
		q3WallCoordinates = newq3WallCoordinates;
	}

	public static void SetQ4(HashMap<Point, Integer> newq4WallCoordinates) {
		q4WallCoordinates = newq4WallCoordinates;
	}
	
	public static void SetWallColorMap(HashMap<Point, Color> newWallColorMap) {
		wallColorMap = newWallColorMap;
	}
	
	public static boolean StartLocationExists() {
		return startLocationExists;
	}
	
	public static boolean EndLocationExists() {
		return endLocationExists;
	}
	
	public static JLabel GetStartLabel() {
		return startLocation;
	}
	
	public static JLabel GetEndLabel() {
		return endLocation;
	}
	
	public static HashMap<Point, Integer> GetQ1() {
		return q1WallCoordinates;
	}
	public static HashMap<Point, Integer> GetQ2() {
		return q2WallCoordinates;
	}
	public static HashMap<Point, Integer> GetQ3() {
		return q3WallCoordinates;
	}
	public static HashMap<Point, Integer> GetQ4() {
		return q4WallCoordinates;
	}
	public static HashMap<Point, Color> GetWallColorMap(){
		return wallColorMap;
	}
	public static HashMap<Point, Integer> GetTracemap(){
		return traceMap;
	}
	public static void setTraceMap(HashMap<Point, Integer> traceMap) {
		ButtonFunctions.traceMap = traceMap;
	}
	public static void SetHorizontialLineY(int y) {
		horizontalLineY = y;
	}
	public static void SetHorizontalLineX(int x) {
		horizontalLineX = x;
	}
	public static void SetVerticalLineX(int x) {
		verticalLineX = x;
	}
	public static void SetVerticalLineY(int y) {
		verticalLineY = y;
	}
	
	public static void SetStartLocationExistsFalse() {
		startLocationExists = false;
	}
	
	public static void SetEndLocationExistsFalse() {
		endLocationExists = false;
	}

	public static LinkedList<PortalNode> getPortalNodeList() {
		return portalNodeList;
	}

	public static void setPortalNodeList(LinkedList<PortalNode> linkNodeList) {
		ButtonFunctions.portalNodeList = linkNodeList;
	}

	public static ImageIcon getOptionImageIcon() {
		return optionImageIcon;
	}

	public static void setOptionImageIcon(ImageIcon optionImageIcon) {
		ButtonFunctions.optionImageIcon = optionImageIcon;
	}
	
	public static WinMessage getWinMessage() {
		return ButtonFunctions.winMessage;
	}
	
	public static void setWinMessage(String winMessage) {
		ButtonFunctions.winMessage.setMessage(winMessage);
	}
}
