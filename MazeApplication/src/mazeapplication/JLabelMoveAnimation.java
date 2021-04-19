package mazeapplication;

import java.util.ArrayList;

import javax.swing.JLabel;

public class JLabelMoveAnimation {
	public JLabelMoveAnimation() {
	}
	/*
	 * Move_Animation, Moves the array list to the correct spot. 
	 */
	public void Move_Animation(ArrayList<int[]> coordinates, JLabel selected_label, long run_time) {
		ArrayList<int[]> animated_coordinates = Make_Animated_Coordinates(coordinates, selected_label);
		
		// Thread to run through coordinates. 
		//TODO: Change the coordinates to animated_coordinates. 
		Thread thread = new Thread() {
			public void run() {
				for(int coordinate = 0; coordinate < animated_coordinates.size(); coordinate++) {
					Make_Move(animated_coordinates.get(coordinate), selected_label);
					try {
						Thread.sleep(run_time);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
	}
	
	/*
	 * Generating the coordinate map for the label to follow.
	 */
	private ArrayList<int[]> Make_Animated_Coordinates(ArrayList<int[]> coordinates, JLabel selected_label) {
		int x = selected_label.getX();
		int y = selected_label.getY();
		ArrayList<int[]> movements = new ArrayList<int[]>();
		for(int end_coordinate = 0; end_coordinate < coordinates.size(); end_coordinate++) {
			int[] current_end_coordinate = {coordinates.get(end_coordinate)[0], coordinates.get(end_coordinate)[1]};
			movements.addAll(Check_Direction_For_Movement(x,y,current_end_coordinate));
			x = current_end_coordinate[0];
			y = current_end_coordinate[1];
		}
		return movements;
	}
	
	/*
	 * Checking the direction for the label to follow.
	 */
	private ArrayList<int[]> Check_Direction_For_Movement(int x, int y, int[] current_end_coordinate) {
		if(current_end_coordinate[0] == x && current_end_coordinate[1] != y) {
			return Get_Y_Axis_Movement(x,y,current_end_coordinate);
		}
		else if(current_end_coordinate[1] == y && current_end_coordinate[0] != x){
			return Get_X_Axis_Movement(x,y,current_end_coordinate);
		}
		else {
			return Get_Non_Axis_Movements(x,y,current_end_coordinate);
		}
		
	}
	
	private ArrayList<int[]> Get_Non_Axis_Movements(int x, int y, int[] current_end_coordinate){
		return CoordinateMap.Get_Coordinates(x, y, current_end_coordinate[0], current_end_coordinate[1]);
	}
	
	
	/*
	 * generating the y axis movements. 
	 */
	private ArrayList<int[]> Get_Y_Axis_Movement(int x, int y, int[] current_end_coordinate){
		ArrayList<int[]> animated_movements = new ArrayList<int[]>();
		
		int y_movement = y;
		if(y > current_end_coordinate[1]) {
			while(y_movement > current_end_coordinate[1]) {
				y_movement -=1;
				int[] next_movement = new int[] {x, y_movement};
				animated_movements.add(next_movement);
			}
		}
		else {
			while(y_movement < current_end_coordinate[1]) {
				y_movement +=1;
				int[] next_movement = new int[] {x, y_movement};
				animated_movements.add(next_movement);
			}
		}
		
		return animated_movements;
	}
	
	/*
	 * Generating the x axis movements. 
	 */
	private ArrayList<int[]> Get_X_Axis_Movement(int x, int y, int[] current_end_coordinate){
		ArrayList<int[]> animated_movements = new ArrayList<int[]>();
		
		int x_movement = x;
		if(x > current_end_coordinate[1]) {
			while(x_movement > current_end_coordinate[1]) {
				x_movement -=1;
				int[] next_movement = new int[] {x_movement, y};
				animated_movements.add(next_movement);
			}
		}
		else {
			while(x_movement < current_end_coordinate[1]) {
				x_movement +=1;
				int[] next_movement = new int[] {x_movement, y};
				animated_movements.add(next_movement);
			}
		}
		return animated_movements;
	}
	
	private void Make_Move(int[] current_coordinate, JLabel selected_label) {
		int width = selected_label.getWidth();
		int height = selected_label.getHeight();
		int x = current_coordinate[0];
		int y = current_coordinate[1];
		selected_label.setBounds(x, y, width, height);
		System.out.println(x +"," +y);
	}
}
