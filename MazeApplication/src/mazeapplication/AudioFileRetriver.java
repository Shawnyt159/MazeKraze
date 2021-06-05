package mazeapplication;

import java.awt.Color;
import java.net.URL;

public class AudioFileRetriver {
	public AudioFileRetriver() {
		
	}
	public URL GetAudioFileAsResourceStream(Color backgroundColor) {
		if(backgroundColor.equals(Color.white)) {
			return null;
		}
		//Sky Blue
		else if(backgroundColor.equals(new Color(135,206,235))) {
			URL musicStream = getClass().getResource("/music/skyBlue.wav");
			return musicStream;
		}
		// Sand
		else if(backgroundColor.equals(new Color(194,178,128))) {
			URL musicStream = getClass().getResource("/music/Sand.wav");
			return musicStream;
		}
		//Grass
		else if(backgroundColor.equals(new Color(96,128,56))) {
			return null;
		}
		//Mud
		else if(backgroundColor.equals(new Color(112,84,62))) {
			return null;
		}
		//Glacier
		else if(backgroundColor.equals(new Color(120,177,189))) {
			return null;
		}
		//Swamp
		else if(backgroundColor.equals(new Color(160,192,144))) {
			return null;
		}
		//Storm Grey
		else if(backgroundColor.equals(new Color(211,211,211))) {
			return null;
		}
		return null;
	}
}
