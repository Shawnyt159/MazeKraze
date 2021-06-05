package mazeapplication;

import java.awt.Color;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.JPanel;

public class RedoErasedLineStructure {
	private static Stack<HashMap<Point, Integer>> redoErasedLineStack = new Stack<HashMap<Point, Integer>>();
	private static Stack<HashMap<Point, Color>> currentErasedLineColorStack = new Stack<HashMap<Point, Color>>();
	private static HashMap<Point, Color> currentErasedLineColorMap = new HashMap<Point, Color>();
	private static HashMap<Point, Integer> currentErasedLine = new HashMap<Point, Integer>();
	
	public static void AddLineToRedoErasedLineStack() {
		redoErasedLineStack.add(currentErasedLine);
		currentErasedLineColorStack.add(currentErasedLineColorMap);
		ResetErasedLineStack();
	}
	
	public static void Redo(JPanel mazePanel) {
		if(!redoErasedLineStack.isEmpty()) {
			HashMap<Point, Integer> redoneLine = redoErasedLineStack.pop();
			for(Map.Entry<Point, Integer> entry: redoneLine.entrySet()) {
				HashMap<Point, Integer> qMap = ButtonFunctions.GetCorrectQuadrantMap(entry.getKey());
				Point coordinates = entry.getKey();
				Integer thickness = entry.getValue();
				qMap.put(coordinates, thickness);
			}
			HashMap<Point, Color> redoneColorMap = currentErasedLineColorStack.pop();
			for(Map.Entry<Point, Color> entry: redoneColorMap.entrySet()) {
				ButtonFunctions.GetWallColorMap().put(entry.getKey(), entry.getValue());
			}
			mazePanel.repaint();
		}
	}
	
	public static void AddPointToRedoStacks(Point coordinates, Integer thickness, Color color) {
		currentErasedLine.put(coordinates, thickness);
		currentErasedLineColorMap.put(coordinates, color);
	}
	
	public static void ClearRedoStack() {
		redoErasedLineStack.clear();
		currentErasedLineColorStack.clear();
	}
	
	private static void ResetErasedLineStack() {
		currentErasedLine = new HashMap<Point, Integer>();
		currentErasedLineColorMap = new HashMap<Point, Color>();
	}
	
}
