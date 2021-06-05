package mazeapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GetFileAttributes {
	public GetFileAttributes() {
		
	}
	public void Get(String itemName) {
		InputStream stream = getClass().getResourceAsStream("/imageAttributes/Attributes.txt");
		BufferedReader streamReader = new BufferedReader(new InputStreamReader(stream));
		String currentLine = "";
		try {
			while(streamReader.ready()) {
				currentLine = streamReader.readLine();
				if(currentLine.equals(itemName)) {
					currentLine = streamReader.readLine();
					HoverImage.setHeight(Integer.parseInt(currentLine));
					currentLine = streamReader.readLine();
					HoverImage.setWidth(Integer.parseInt(currentLine));
					currentLine = streamReader.readLine();
					HoverImage.setImageFile(currentLine);
					break;
				}
			}
			stream.close();
			streamReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
