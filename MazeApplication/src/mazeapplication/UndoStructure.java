package mazeapplication;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JPanel;

public class UndoStructure {
	private static Stack<ArrayList<Point>> undoStack = new Stack<ArrayList<Point>>();
	private static ArrayList<Point> newLine = new ArrayList<Point>();
	
	public static void AddLineUndoStack() {
		undoStack.add(newLine);
		ResetNewLine();
	}
	
	public static void Undo(JPanel mazePanel) {
		if(!undoStack.isEmpty()) {
			ArrayList<Point> newestLine = undoStack.pop();
			for(int i = 0; i < newestLine.size(); i++) {
				if(ButtonFunctions.GetCorrectQuadrantMap(newestLine.get(i)) != null) {
					ButtonFunctions.GetCorrectQuadrantMap(newestLine.get(i)).remove(newestLine.get(i));
					ButtonFunctions.GetWallColorMap().remove(newestLine.get(i));
				}
			}
			mazePanel.repaint();
		}
	}
	
	public static void clearUndoStack() {
		undoStack.clear();
	}
	
	public static void AddPointToNewLineArrayList(Point point) {
		newLine.add(point);
	}
	
	private static void ResetNewLine() {
		newLine = new ArrayList<Point>();
	}
}
