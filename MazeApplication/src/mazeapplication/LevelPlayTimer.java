package mazeapplication;

public class LevelPlayTimer {
	private static long startTime;
	private static long elapsedTime;
	private static long elapsedSeconds;
	private static long secondsDisplay;
	private static long elapsedMinutes;
	public static void StartTimer() {
		startTime = System.currentTimeMillis();
	}
	public static String GetTime() {
		elapsedTime = System.currentTimeMillis() - startTime;
		elapsedSeconds = elapsedTime / 1000;
		secondsDisplay = elapsedSeconds % 60;
		elapsedMinutes = elapsedSeconds / 60;
		String time = elapsedMinutes + ":" + secondsDisplay;
		return time;
	}
}
