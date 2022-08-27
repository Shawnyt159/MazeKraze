package mazeapplication;

public class MazeCell {
	private boolean visited;
	private MazeCell upNeighbor = null;
	private MazeCell downNeighbor = null;
	private MazeCell leftNeightbor = null;
	private MazeCell rightNeighbor = null;
	private MazeWall upWall = new MazeWall(this);
	private MazeWall downWall = new MazeWall(this);
	private MazeWall leftWall = new MazeWall(this);
	private MazeWall rightWall = new MazeWall(this);
	
	public MazeCell(boolean visited) {
		this.visited = visited;
	}
	
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	
	public boolean getVisited() {
		return this.visited;
	}

	public MazeCell getUpNeighbor() {
		return upNeighbor;
	}

	public MazeCell getDownNeighbor() {
		return downNeighbor;
	}

	public MazeCell getLeftNeightbor() {
		return leftNeightbor;
	}

	public MazeCell getRightNeighbor() {
		return rightNeighbor;
	}

	public void setUpNeighbor(MazeCell upNeighbor) {
		this.upNeighbor = upNeighbor;
	}

	public void setDownNeighbor(MazeCell downNeighbor) {
		this.downNeighbor = downNeighbor;
	}

	public void setLeftNeightbor(MazeCell leftNeightbor) {
		this.leftNeightbor = leftNeightbor;
	}

	public void setRightNeighbor(MazeCell rightNeighbor) {
		this.rightNeighbor = rightNeighbor;
	}

	public MazeWall getUpWall() {
		return upWall;
	}

	public MazeWall getDownWall() {
		return downWall;
	}

	public MazeWall getLeftWall() {
		return leftWall;
	}

	public MazeWall getRightWall() {
		return rightWall;
	}

	public void setUpWall(MazeWall upWall) {
		this.upWall = upWall;
	}

	public void setDownWall(MazeWall downWall) {
		this.downWall = downWall;
	}

	public void setLeftWall(MazeWall leftWall) {
		this.leftWall = leftWall;
	}

	public void setRightWall(MazeWall rightWall) {
		this.rightWall = rightWall;
	}
	
	
}
