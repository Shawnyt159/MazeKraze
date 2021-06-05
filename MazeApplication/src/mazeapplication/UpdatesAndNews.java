package mazeapplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class UpdatesAndNews {
	public UpdatesAndNews() {
		
	}
	
	public String getUpdates() {
		String updatesURL = "https://www.mazekraze.com/Updates/";
		try {
			URL url = new URL(updatesURL);
			Scanner sc = new Scanner(url.openStream());
			StringBuffer sb = new StringBuffer();
			while(sc.hasNext()) {
				sb.append(sc.next());
			}
			String updates = sb.toString();
			System.out.println(updates);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getNews() {
		return null;
	}
}
