package mazeapplication;

import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class DecorationNode implements Serializable{
	private static final long serialVersionUID = 1L;
	private JLabel decorationNode;
	
	public DecorationNode(JLabel decorationNode) {
		this.decorationNode = decorationNode;
	}
	
	public void AddDecorationToMazePanel(JPanel mazePanel) {
		mazePanel.add(decorationNode);
		decorationNode.setVisible(true);
	}
	
	public JLabel getDecorationNode() {
		return decorationNode;
	}
	
	public void RemoveNodeFromPanel(JPanel mazePanel) {
		mazePanel.remove(decorationNode);
		mazePanel.repaint();
	}
}
