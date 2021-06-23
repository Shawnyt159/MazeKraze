package mazeapplication;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class UpdatesAndNews {
	public UpdatesAndNews() {
		
	}
	
	public String getUpdates() {
		String updatesURL = "https://docs.google.com/document/d/1cpFnaALd0uckRjS0_r0XSK_t1Z_ynq_DfkMUoG8yR-k/edit?usp=sharing";
		try {
			URL url = new URL(updatesURL);
			Scanner sc = new Scanner(url.openStream());
			StringBuffer sb = new StringBuffer();
			while(sc.hasNext()) {
				sb.append(sc.next());
			}
			String htmlUpdates = sb.toString();
			Document documentUpdates = Jsoup.parse(htmlUpdates);
			String updates = documentUpdates.body().text();
			updates = updates.substring(updates.indexOf("Updates:"));
			updates = updates.substring(updates.indexOf("Updates:"), updates.indexOf("\""));
			updates = updates.replace(",", " ");
			updates = updates.replace("\\n", ", ");
			sc.close();
			return updates;
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
