package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenerateMaze {
	private static HashMap<Point, Integer> hardMazeMap = new HashMap<Point, Integer>();
	private static HashMap<Point, Integer> mediumMazeMap = new HashMap<Point, Integer>();
	private static HashMap<Point, Integer> easyMazeMap = new HashMap<Point, Integer>();
	private static HashMap<Point, Color> colorMap = new HashMap<Point, Color>();

	public static void GenerateHardMaze(Color lineColor) {
		hardMazeMap.clear();
		colorMap.clear();
		
		GenerateMazeWithValues(70, 64, hardMazeMap, 4, 4, 10, 4, 2);

		for(Map.Entry<Point, Integer> entry : hardMazeMap.entrySet()) {
			HashMap<Point, Integer> qMap = ButtonFunctions.GetCorrectQuadrantMap(entry.getKey());
			qMap.put(entry.getKey(), entry.getValue());
			colorMap.put(entry.getKey(), lineColor);
		}
		ButtonFunctions.SetWallColorMap(colorMap);
		MazeDesignMainGUI.GetMazePanel().repaint();
	}
	
	public static void GenerateMediumMaze(Color lineColor) {
		mediumMazeMap.clear();
		colorMap.clear();
		
		GenerateMazeWithValues(35, 32, mediumMazeMap, 4, 4, 20, 4, 4);
		
		for(Map.Entry<Point, Integer> entry : mediumMazeMap.entrySet()) {
			HashMap<Point, Integer> qMap = ButtonFunctions.GetCorrectQuadrantMap(entry.getKey());
			qMap.put(entry.getKey(), entry.getValue());
			colorMap.put(entry.getKey(), lineColor);
		}
		ButtonFunctions.SetWallColorMap(colorMap);
		MazeDesignMainGUI.GetMazePanel().repaint();
	}
	
	public static void GenerateEasyMaze(Color lineColor) {
		easyMazeMap.clear();
		colorMap.clear();
		
		GenerateMazeWithValues(23, 21, easyMazeMap, 9, 9, 30, 9, 6);
		for(Map.Entry<Point, Integer> entry : easyMazeMap.entrySet()) {
			HashMap<Point, Integer> qMap = ButtonFunctions.GetCorrectQuadrantMap(entry.getKey());
			qMap.put(entry.getKey(), entry.getValue());
			colorMap.put(entry.getKey(), lineColor);
		}
		ButtonFunctions.SetWallColorMap(colorMap);
		MazeDesignMainGUI.GetMazePanel().repaint();
	}
	
	private static void GenerateMazeWithValues(int width, int height, HashMap<Point, Integer> mazeMap, int currentCellXCoordinate, int currentCellYCoordinate,
			int space, int startCellYCoordinate, int lineSize) {
		int x = 0;
		int y = 0;

		// Populating the two dimensional array of maze cells.
		MazeCell[][] mazeCells = new MazeCell[width][height];
		for (x = 0; x < width; x++) {
			for (y = 0; y < height; y++) {
				MazeCell newCell = new MazeCell(false);
				mazeCells[x][y] = newCell;
			}
		}
		
		// Setting wall neighbors.
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(y-1 >= 0) {
					mazeCells[x][y].setUpNeighbor(mazeCells[x][y-1]);
				}
				if(y+1 <= height-1) {
					mazeCells[x][y].setDownNeighbor(mazeCells[x][y+1]);
				}
				if(x-1 >= 0) {
					mazeCells[x][y].setLeftNeightbor(mazeCells[x-1][y]);
				}
				if(x+1 <= width-1) {
					mazeCells[x][y].setRightNeighbor(mazeCells[x+1][y]);
				}
			}
		}

		// Generating a random path to the final cell.
		MazeCell startCell = mazeCells[0][0];
		startCell.setVisited(true);

		ArrayList<MazeWall> availableWalls = new ArrayList<MazeWall>();
		availableWalls.add(startCell.getDownWall());
		availableWalls.add(startCell.getRightWall());
		Random randomPathGenerator = new Random();
		
		
		boolean mazeGenerated = false;
		while(mazeGenerated == false) {
			
			int nextWall = randomPathGenerator.nextInt(availableWalls.size());
			MazeWall currentWall = availableWalls.get(nextWall);
			MazeCell mazeCell = currentWall.getMazeCell();
			
			// If the wall is the upward wall of the cell.
			if(currentWall.equals(mazeCell.getUpWall())) {
				if(mazeCell.getUpNeighbor() == null) {
					availableWalls.remove(currentWall);
				}
				else {
					if(mazeCell.getUpNeighbor().getVisited() == false) {
						currentWall.setExists(false);
						MazeCell upNeighbor = mazeCell.getUpNeighbor();
						upNeighbor.setVisited(true);
						upNeighbor.getDownWall().setExists(false);
						availableWalls.add(upNeighbor.getUpWall());
						availableWalls.add(upNeighbor.getRightWall());
						availableWalls.add(upNeighbor.getLeftWall());
					}
					else {
						availableWalls.remove(currentWall);
					}
				}
			}
			
			//If the wall is the downward facing wall of the cell.
			else if(currentWall.equals(mazeCell.getDownWall())) {
				if(mazeCell.getDownNeighbor() == null) {
					availableWalls.remove(currentWall);
				}
				else {
					if(mazeCell.getDownNeighbor().getVisited() == false) {
						currentWall.setExists(false);
						MazeCell downNeighbor = mazeCell.getDownNeighbor();
						downNeighbor.setVisited(true);
						downNeighbor.getUpWall().setExists(false);
						availableWalls.add(downNeighbor.getDownWall());
						availableWalls.add(downNeighbor.getRightWall());
						availableWalls.add(downNeighbor.getLeftWall());
					}
					else {
						availableWalls.remove(currentWall);
					}
				}
			}
			
			// If the wall is the leftward facing wall of the cell.
			else if(currentWall.equals(mazeCell.getLeftWall())) {
				if(mazeCell.getLeftNeightbor() == null) {
					availableWalls.remove(currentWall);
				}
				else {
					if(mazeCell.getLeftNeightbor().getVisited() == false) {
						currentWall.setExists(false);
						MazeCell leftNeighbor = mazeCell.getLeftNeightbor();
						leftNeighbor.setVisited(true);
						leftNeighbor.getRightWall().setExists(false);
						availableWalls.add(leftNeighbor.getUpWall());
						availableWalls.add(leftNeighbor.getDownWall());
						availableWalls.add(leftNeighbor.getRightWall());
					}
					else {
						availableWalls.remove(currentWall);
					}
				}
			}
			
			// If the wall if the rightward facing wall of the cell.
			else if(currentWall.equals(mazeCell.getRightWall())) {
				if(mazeCell.getRightNeighbor() == null) {
					availableWalls.remove(currentWall);
				}
				else {
					if(mazeCell.getRightNeighbor().getVisited() == false) {
						currentWall.setExists(false);
						MazeCell rightNeighbor = mazeCell.getRightNeighbor();
						rightNeighbor.setVisited(true);
						rightNeighbor.getLeftWall().setExists(false);
						availableWalls.add(rightNeighbor.getUpWall());
						availableWalls.add(rightNeighbor.getDownWall());
						availableWalls.add(rightNeighbor.getLeftWall());
					}
					else {
						availableWalls.remove(currentWall);
					}
				}
			}
			
			// If there are no more walls available then end loop.
			if(availableWalls.isEmpty() == true) {
				mazeGenerated = true;
			}
			
		}
		
		for(x = 0; x < width; x++) {
			for(y = 0; y < height; y++) {
				if(mazeCells[x][y].getUpWall().getExists() == true) {
					for(int i = 0; i < space+1; i++) {
						Point newPoint = new Point(currentCellXCoordinate+i, currentCellYCoordinate);
						mazeMap.put(newPoint, lineSize);
					}
				}
				if(mazeCells[x][y].getDownWall().getExists() == true) {
					for(int i = 0; i < space+1; i++) {
						Point newPoint = new Point(currentCellXCoordinate+i, currentCellYCoordinate+space);
						mazeMap.put(newPoint, lineSize);
					}
				}
				if(mazeCells[x][y].getLeftWall().getExists() == true) {
					for(int i = 0; i < space+1; i++) {
						Point newPoint = new Point(currentCellXCoordinate, currentCellYCoordinate+i);
						mazeMap.put(newPoint, lineSize);
					}
				}
				if(mazeCells[x][y].getRightWall().getExists() == true) {
					for(int i = 0; i < space+1; i++) {
						Point newPoint = new Point(currentCellXCoordinate+space, currentCellYCoordinate+i);
						mazeMap.put(newPoint, lineSize);
					}
				}
				currentCellYCoordinate += space;
			}
			currentCellYCoordinate = startCellYCoordinate;
			currentCellXCoordinate += space;
		}
	}
}
