package pt.ipbeja.estig.eda.model;

public class Node {
	private int grayValue;
	private boolean isPartOfPath;
	private double time;

	private final int x;

	private final int y;

	public Node(final int x, final int y) {
		this.x = x;
		this.y = y;
		this.isPartOfPath = false;
	}

	public Node(final int x, final int y, final int grayValue) {
		this.x = x;
		this.y = y;
		this.grayValue = grayValue;
		this.isPartOfPath = false;
	}

	public int getGrayValue() {
		return this.grayValue;
	}

	public double getTime() {
		return this.time;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public boolean isPartOfPath() {
		return this.isPartOfPath;
	}

	public void setGrayValue(final int grayValue) {
		this.grayValue = grayValue;
	}

	public void setPartOfPath(final boolean isPartOfPath) {
		this.isPartOfPath = isPartOfPath;
	}

	public void setTime(final double time) {
		this.time = time;
	}

	@Override
	public String toString() {

		return "[X:" + this.x + "|Y:" + this.y + "|G:" + this.grayValue + "]";
	}

}
