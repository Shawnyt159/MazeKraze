package mazeapplication;

import java.io.Serializable;

import javax.swing.JLabel;

public class PortalNode implements Serializable{
	private static final long serialVersionUID = 1709104910131395976L;
	private String linkName;
	private JLabel linkStart;
	private JLabel linkEnd;
	private char linkStartExitSide;
	private char linkEndExitSide;
	
	public PortalNode(String linkName, char linkStartExitSide, char linkEndExitSide) {
		this.linkName = linkName;
		this.linkStartExitSide = linkStartExitSide;
		this.linkEndExitSide = linkEndExitSide;
	}
	
	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}
	

	public JLabel getLinkStart() {
		return linkStart;
	}

	public JLabel getLinkEnd() {
		return linkEnd;
	}

	public void setLinkStart(JLabel linkStart) {
		this.linkStart = linkStart;
	}

	public void setLinkEnd(JLabel linkEnd) {
		this.linkEnd = linkEnd;
	}

	public char getLinkEndExitSide() {
		return linkEndExitSide;
	}

	public char getLinkStartExitSide() {
		return linkStartExitSide;
	}

	public void setLinkEndExitSide(char linkEndExitSide) {
		this.linkEndExitSide = linkEndExitSide;
	}

	public void setLinkStartExitSide(char linkStartExitSide) {
		this.linkStartExitSide = linkStartExitSide;
	}
	
	
}
