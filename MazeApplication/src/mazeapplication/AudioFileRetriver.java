package mazeapplication;

import java.awt.Color;
import java.net.URL;

public class AudioFileRetriver {
	public AudioFileRetriver() {
		
	}
	public URL GetAudioFileAsResourceStream(Color backgroundColor) {
		if(backgroundColor.equals(Color.white)) {
			URL musicStream = getClass().getResource("/music/Plain.wav");
			return musicStream;
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
			URL musicStream = getClass().getResource("/music/Grass.wav");
			return musicStream;
		}
		//Mud
		else if(backgroundColor.equals(new Color(112,84,62))) {
			URL musicStream = getClass().getResource("/music/Mud.wav");
			return musicStream;
		}
		//Glacier
		else if(backgroundColor.equals(new Color(120,177,199))) {
			URL musicStream = getClass().getResource("/music/Glacier.wav");
			return musicStream;
		}
		//Swamp
		else if(backgroundColor.equals(new Color(160,192,144))) {
			URL musicStream = getClass().getResource("/music/Swamp.wav");
			return musicStream;
		}
		//Storm Gray
		else if(backgroundColor.equals(new Color(211,211,211))) {
			URL musicStream = getClass().getResource("/music/Storm Gray.wav");
			return musicStream;
		}
		return null;
	}
}
