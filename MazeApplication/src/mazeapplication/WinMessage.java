package mazeapplication;

import javax.swing.JOptionPane;

public class WinMessage implements Message{
	private String message;
	
	public WinMessage() {
		
	}
	
	public void DisplayMessage() {
		JOptionPane.showMessageDialog(null, message);
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public void setMessage(String message) {
		this.message = message;
	}
	
}
