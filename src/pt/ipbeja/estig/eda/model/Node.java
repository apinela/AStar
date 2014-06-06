package pt.ipbeja.estig.eda.model;

public class Node {
	private int x;
	private int y;
	private double time;

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	private boolean isPartOfPath;

	public void setPartOfPath(boolean isPartOfPath) {
		this.isPartOfPath = isPartOfPath;
	}

	private int grayValue;

	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.isPartOfPath = false;
	}

	public Node(int x, int y, int grayValue) {
		this.x = x;
		this.y = y;
		this.grayValue = grayValue;
		this.isPartOfPath = false;
	}

	public int getGrayValue() {
		return this.grayValue;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setGrayValue(int grayValue) {
		this.grayValue = grayValue;
	}

	@Override
	public String toString() {

		return "[X:" + this.x + "|Y:" + this.y + "|G:" + this.grayValue + "]";
	}

	public boolean isPartOfPath() {
		return this.isPartOfPath;
	}

}
