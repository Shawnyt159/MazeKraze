package mazeapplication;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class levelPlayMazePanel extends JPanel{

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
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
				HashMap<Point, Integer> correctMap = LevelPlayLoadedMazeAttributes.GetCorrectQuadrantMap(nextPoint);
				if(correctMap.containsKey(nextPoint)) {
					DrawCurrentCoordinate(g, nextPoint, correctMap.get(nextPoint));
				}
			}
			
			Rectangle visibleAreaRectangle = new Rectangle((int) playerCenter.getX()-45, (int) playerCenter.getY()-45, 90, 90);
			LinkedList<DecorationNode> decorationList = LevelPlayLoadedMazeAttributes.getDecorationList();
			if(decorationList != null) {
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
			}
		}
		else {
			for(Map.Entry<Point, Integer> entery : LevelPlayLoadedMazeAttributes.getQ1().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : LevelPlayLoadedMazeAttributes.getQ2().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : LevelPlayLoadedMazeAttributes.getQ3().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
			for(Map.Entry<Point, Integer> entery : LevelPlayLoadedMazeAttributes.getQ4().entrySet()) {
				DrawCurrentCoordinate(g, entery);
			}
		}
	}
	
	// Drawing on the coordinate with the color and the thickness.
		private void DrawCurrentCoordinate(Graphics g, Map.Entry<Point, Integer> entery) {
			Point coordinates = entery.getKey();
			int lineThickness = entery.getValue();
			((Graphics2D) g).setStroke(new BasicStroke(lineThickness));
			if(LevelPlayLoadedMazeAttributes.getColorMap() != null) {
				g.setColor(LevelPlayLoadedMazeAttributes.getColorMap().get(coordinates));
			}
			g.drawLine((int) coordinates.getX(), (int) coordinates.getY(), (int) coordinates.getX(), (int) coordinates.getY());
			g.setColor(Color.black);
		}
		
		private void DrawCurrentCoordinate(Graphics g, Point coordinates, int lineThickness) {
			((Graphics2D) g).setStroke(new BasicStroke(lineThickness));
			g.setColor(LevelPlayLoadedMazeAttributes.getColorMap().get(coordinates));
			g.drawLine((int) coordinates.getX(), (int) coordinates.getY(), (int) coordinates.getX(), (int) coordinates.getY());
			g.setColor(Color.black);
		}
}
