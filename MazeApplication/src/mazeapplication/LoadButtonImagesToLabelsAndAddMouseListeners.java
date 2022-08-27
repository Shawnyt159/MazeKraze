package mazeapplication;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JLabel;

public class LoadButtonImagesToLabelsAndAddMouseListeners {
	private SetImageToLabel images = new SetImageToLabel();
	
	final private int[] selectedButtons = new int[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
	public LoadButtonImagesToLabelsAndAddMouseListeners(){
		
	}
	public void Load(ArrayList<JLabel> plainActionButtons) {
		InputStream stream = getClass().getResourceAsStream("/images/ButtonNames.txt");
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream));
		String currentLine = "";
		int currentActionButton = 0; 
		
		try {
			while(streamReader.ready() && currentActionButton <= plainActionButtons.size()) {
				currentLine = streamReader.readLine();
				JLabel currentActionButtonLabel = plainActionButtons.get(currentActionButton);
				images.set_image_to_label(currentActionButtonLabel, currentLine + "Origional.png");
				String currentLineInListener = new String(currentLine);
				final int currentActionButtonIndex = currentActionButton;
				currentActionButtonLabel.addMouseListener(new MouseListener() {
					final int selectedValue = selectedButtons[currentActionButtonIndex];
					@Override
					public void mouseClicked(MouseEvent e) {
						images.set_image_to_label(currentActionButtonLabel, currentLineInListener + "Hover.png");
						MazeDesignMainGUI.SetSelected(selectedValue);
					}

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						images.set_image_to_label(currentActionButtonLabel, currentLineInListener + "Hover.png");
						
					}

					@Override
					public void mouseExited(MouseEvent e) {
						if(MazeDesignMainGUI.getSelected() != selectedValue) {
							images.set_image_to_label(currentActionButtonLabel, currentLineInListener + "Origional.png");
						}
						
					}
				});
				currentActionButton++;
			}
			stream.close();
			streamReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
