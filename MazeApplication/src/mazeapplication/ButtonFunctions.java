package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	
	//Wall Color Map.
	private static HashMap<Point, Color> wallColorMap = new HashMap<Point, Color>();
	
	private static int horizontialLineY; 
	private static int verticalLineX;
	
	
	/**
	 * START LOCATION FUNCTIONS:
	 */
	
	/*
	 * Logic for startLocation of maze.
	 */
	public static void StartLocationFunction(JPanel mazePanel) {
		if(startLocationExists == false) {
			SetStartLocationCoordinates(mazePanel);
			mazePanel.add(startLocation);
			images.set_image_to_label(startLocation, "/images/O.png");
		}
		else {
			Point coordinates = GetMouseCoordinates(mazePanel);
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to change the start location?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
			if (JOptionPane.showConfirmDialog(null, "Are you sure you want to change the end location?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
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
		// New point to draw.
		if(!qmap.containsKey(coordinates)) {
			DrawCoordinate(mazePanel, lineThickness, coordinates, qmap, lineColor);
		}
		// Replace the point.
		else {
			int currentLineThickness = qmap.get(coordinates);
			if(currentLineThickness != lineThickness) {
				DrawCoordinate(mazePanel, lineThickness, coordinates, qmap, lineColor);
			}
			else {
				// Do nothing.
			}
		}
	}
	
	public static void DrawHorizontalLineFunction(JPanel mazePanel, int lineThickness, Color lineColor) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		Point horizontialPoint = new Point((int) coordinates.getX(), horizontialLineY);
		HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(horizontialPoint);
		// New point to draw.
		if(!qmap.containsKey(horizontialPoint)) {
			DrawCoordinate(mazePanel, lineThickness, horizontialPoint, qmap, lineColor);
		}
		// Replace the point.
		else {
			int currentLineThickness = qmap.get(horizontialPoint);
			if(currentLineThickness != lineThickness) {
				DrawCoordinate(mazePanel, lineThickness, horizontialPoint, qmap, lineColor);
			}
			else {
				// Do nothing.
			}
		}
	}
	
	public static void DrawVerticalLineFunction(JPanel mazePanel, int lineThickness, Color lineColor) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		Point verticalPoint = new Point(verticalLineX, (int) coordinates.getY());
		HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(verticalPoint);
		// New point to draw.
		if(!qmap.containsKey(verticalPoint)) {
			DrawCoordinate(mazePanel, lineThickness, verticalPoint, qmap, lineColor);
		}
		// Replace the point.
		else {
			int currentLineThickness = qmap.get(verticalPoint);
			if(currentLineThickness != lineThickness) {
				DrawCoordinate(mazePanel, lineThickness, verticalPoint, qmap, lineColor);
			}
			else {
				// Do nothing.
			}
		}
	}
	
	/*
	 * Drawing on the current coordinate with the specific thickness.
	 */
	private static void DrawCoordinate(JPanel mazePanel, int lineThickness, Point coordinates, HashMap<Point, Integer> qmap, Color color) {
		qmap.put(coordinates, lineThickness);
		AddCoordinateAndColorToColorMap(coordinates, color);
		mazePanel.repaint();
	}
	
	private static void AddCoordinateAndColorToColorMap(Point coordinates, Color color) {
		wallColorMap.put(coordinates, color);
	}
	
	/**
	 * ERASE LINE FUNCTIONS.
	 */
	public static void EraseCoordinate(JPanel mazePanel, int thickness) {
		Point coordinates = GetMouseCoordinates(mazePanel);
		HashMap<Point, Integer> qmap = GetCorrectQuadrantMap(coordinates);
		thickness = thickness * 2;
		int negativeThickness = thickness * -1;
		for(int x = negativeThickness; x < thickness; x++) {
			for(int y = negativeThickness; y < thickness; y++) {
				Point newCoordinate = new Point((int) coordinates.getX() + x, (int) coordinates.getY() + y);
				if(qmap.containsKey(newCoordinate)) {
					qmap.remove(newCoordinate);
					wallColorMap.remove(newCoordinate);
					mazePanel.repaint();
				}
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
	
	public static void SetHorizontialLineY(int y) {
		horizontialLineY = y;
	}
	
	public static void SetVerticalLineX(int x) {
		verticalLineX = x;
	}
	
	public static void SetStartLocationExistsFalse() {
		startLocationExists = false;
	}
	
	public static void SetEndLocationExistsFalse() {
		endLocationExists = false;
	}
}