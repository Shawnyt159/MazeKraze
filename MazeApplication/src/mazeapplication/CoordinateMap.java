package mazeapplication;

import java.util.ArrayList;

public class CoordinateMap {

	public static ArrayList<int[]> Get_Coordinates(int x, int y, int end_x, int end_y){
		ArrayList<int[]> coordinates = new ArrayList<int[]>();
		int[] slope = Get_Slope(x,y,end_x,end_y);
		// reducing slope and returning coordinates.
		if(slope[0] % 4 == 0 && slope[1] % 4 == 0) {
			return Reduce_Slope_Get_Coordinates_No_Remainder(x, y, end_x, coordinates, slope);
		}
		else {
			// reducing slope and returning coordinates.
			if(slope[0] % 4 != 0 && slope[1] % 4 != 0) {
				return Reduce_Slope_Get_Coordinates_Both_Remainder(x, y, end_x, end_y, coordinates, slope);
			}
			// reducing slope and returning coordinates.
			else if(slope[0] % 4 == 0 && slope[1] != 0) {
				return Reduce_Slope_Get_Coordinates_Y_Remainder(x, y, end_x, end_y, coordinates, slope);
			}
			// reducing slope and returning coordinates.
			else {
				return Reduce_Slope_Get_Coordinates_X_Remainder(x, y, end_x, end_y, coordinates, slope);
			}
		}
	}
	
	private static ArrayList<int[]> Reduce_Slope_Get_Coordinates_No_Remainder(int x, int y, int end_x, ArrayList<int[]> coordinates, int[] slope){
		Reduce_Slope(slope);
		while(x != end_x) {
			x += slope[0];
			y += slope[1];
			int[] coordinate = {x,y};
			coordinates.add(coordinate);
		}
		return coordinates;
	}
	
	private static ArrayList<int[]> Reduce_Slope_Get_Coordinates_Both_Remainder(int x, int y, int end_x, int end_y, ArrayList<int[]> coordinates, int[] slope){
		Reduce_Slope(slope);
		if(slope[0] < 0) {
			while(x > end_x) {
				x += slope[0];
				y += slope[1];
				int[] coordinate = {x,y};
				coordinates.add(coordinate);
			}
		}
		else {
			while(x < end_x) {
				x += slope[0];
				y += slope[1];
				int[] coordinate = {x,y};
				coordinates.add(coordinate);
			}
		}
		coordinates.add(new int[] {end_x,end_y});
		return coordinates;
	}
	
	private static ArrayList<int[]> Reduce_Slope_Get_Coordinates_Y_Remainder(int x, int y, int end_x, int end_y, ArrayList<int[]> coordinates, int[] slope){
		Reduce_Slope(slope);
		while(x != end_x) {
			x += slope[0];
			y += slope[1];
			int[] coordinate = {x,y};
			coordinates.add(coordinate);
		}
		int[] coordinate = {x, end_y};
		coordinates.add(coordinate);
		return coordinates;
	}
	
	private static ArrayList<int[]> Reduce_Slope_Get_Coordinates_X_Remainder(int x, int y, int end_x, int end_y, ArrayList<int[]> coordinates, int[] slope){
		Reduce_Slope(slope);
		while(y != end_y) {
			x += slope[0];
			y += slope[1];
			int[] coordinate = {x,y};
			coordinates.add(coordinate);
		}
		int[] coordinate = {end_x, y};
		coordinates.add(coordinate);
		return coordinates;
	}
	
	private static int[] Get_Slope(int x, int y, int end_x, int end_y) {
		int x_slope = end_x - x;
		int y_slope = end_y - y;
		return Reduce(x_slope, y_slope);
	}
	
	private static int[] Reduce(int numerator, int denominator) {
		int gcd = CalculateGCD(numerator, denominator);
		
		numerator /= Math.abs(gcd);
		denominator /= Math.abs(gcd);
		
		return new int[] {numerator,denominator};
	}
	
	private static int CalculateGCD(int numerator, int denominator) {
		if (numerator % denominator == 0) {
	             return denominator;
	      	}
		return CalculateGCD(denominator, numerator % denominator);
	}
	
	private static void Reduce_Slope(int[] slope) {
		if(Math.abs(slope[0]) > 20 || Math.abs(slope[1]) > 20) {
			slope[0] = slope[0]/4;
			slope[1] = slope[1]/4;
		}
	}
}
