package mazeapplication;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class BackgroundMusic {
	private static AudioInputStream audioStream;
	private static AudioFileRetriver audioRetriver = new AudioFileRetriver();
	private static Clip clip;
	private static boolean paused = false;
	
	public static void PlayCorrectAudioContinuously(Color backgroundColor) {
		URL musicStream = audioRetriver.GetAudioFileAsResourceStream(backgroundColor);
		if(musicStream != null) {
			try {
				if(clip != null) {
					clip.stop();
					clip.close();
				}
				audioStream = AudioSystem.getAudioInputStream(musicStream);
				clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				try {
					audioStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		else {
			if(clip != null) {
				clip.stop();
			}
		}
	}
	
	public static void Pause() {
		if(clip != null && clip.isRunning()) {
			clip.stop();
		}
	}
	
	public static void Play() {
		if(clip != null) {
			Pause();
			clip.setFramePosition(0);
			clip.start();
		}
	}
	public static boolean isPaused() {
		return paused;
	}
	
	public static void setPaused(boolean isPaused) {
		paused = isPaused;
	}
}
