package mazeapplication;

public class MazeWall {
	private boolean exists = true;
	private MazeCell mazeCell;
	public MazeWall(MazeCell mazeCell) {
		this.setMazeCell(mazeCell);
	}
	
	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
	public boolean getExists() {
		return exists;
	}

	public MazeCell getMazeCell() {
		return mazeCell;
	}

	public void setMazeCell(MazeCell mazeCell) {
		this.mazeCell = mazeCell;
	}
}
