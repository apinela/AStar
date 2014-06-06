package pt.ipbeja.estig.eda.model;

import java.util.ArrayList;
import java.util.List;

public class AStar {

	public static final int METHOD_EUCLIDEAN = 0;
	public static final int METHOD_MANHATTAN = 1;
	private Node cNode; // Current node
	private double elapsedTime;
	private Node gNode; // Goal node
	private final PgmImage image; // Portable Gray Map loaded into memory
	private int method;
	private List<Node> nodesCL; // Nodes closed list
	private List<Node> nodesOL; // Nodes open list
	private int normalizeHSteps;
	private List<Double> scoreList; // Score list of nodes in open list
	private Node sNode; // Start node

	public AStar(final PgmImage image) {
		this.image = image;

	}

	public List<Node> findBestRoute(final Node startNode, final Node goalNode,
			final int method) {
		final long startTime = System.nanoTime();
		this.sNode = startNode;
		this.gNode = goalNode;
		this.method = method;
		this.nodesOL = new ArrayList<Node>();
		this.nodesCL = new ArrayList<Node>();
		this.normalizeHSteps = 0;

		this.cNode = this.sNode;
		this.cNode.setPartOfPath(true);
		this.cNode.setTime(System.nanoTime());
		this.nodesCL.add(this.cNode);

		while (!this.cNode.equals(this.gNode)) {

			this.nodesOL = this.getNeighbours(this.cNode);

			this.scoreList = new ArrayList<Double>();

			for (final Node n : this.nodesOL)
				this.scoreList.add(this.fScore(n));

			int index = 0;
			double score = Double.MAX_VALUE;

			for (int i = 0; i < this.scoreList.size(); i++)
				if (this.scoreList.get(i) < score) {
					score = this.scoreList.get(i);
					index = i;
				}

			this.cNode = this.nodesOL.get(index);
			this.cNode.setPartOfPath(true);
			this.cNode.setTime(System.nanoTime() - startTime);
			this.nodesCL.add(this.cNode);

		}
		this.elapsedTime = System.nanoTime() - startTime;

		return this.nodesCL;

	}

	private double fScore(final Node nodeToEvaluateScore) {
		final double g = this.gScore(nodeToEvaluateScore);
		final double h = this.hScore(nodeToEvaluateScore);
		if (this.normalizeHSteps == 0)
			this.normalizeHSteps = (int) (5 * Math.round(Math.sqrt(h) / 5));
		return g + h / this.normalizeHSteps;
	}

	public double getAvgTime() {
		return this.elapsedTime / this.nodesCL.size();

	}

	public double getElapsedTime() {
		return this.elapsedTime;
	}

	private List<Node> getNeighbours(final Node cNode) {
		final List<Node> nb = new ArrayList<Node>();
		for (int x = -1; x < 2; x++)
			for (int y = -1; y < 2; y++) {
				final Node n = this.image.getNode(cNode.getX() + x,
						cNode.getY() + y);
				if (n != null)
					if (!(x == 0 && y == 0) && !this.nodesCL.contains(n))
						nb.add(n);
			}
		return nb;
	}

	private double gScore(final Node nodeToEvaluateScore) {
		return 1 - (double) nodeToEvaluateScore.getGrayValue()
				/ this.image.getImageMaxGrayValue();
	}

	private double hScore(final Node nodeToEvaluateScore) {

		double result;
		int relativeX;
		int relativeY;

		if (this.method == 1) {
			// Manhattan distance
			relativeX = Math
					.abs(this.gNode.getX() - nodeToEvaluateScore.getX());
			relativeY = Math
					.abs(this.gNode.getY() - nodeToEvaluateScore.getY());
			result = relativeX + relativeY;
		} else {
			// Euclidean distance
			relativeX = nodeToEvaluateScore.getX() - this.gNode.getX();
			relativeY = nodeToEvaluateScore.getY() - this.gNode.getY();
			result = Math.sqrt(relativeX * relativeX + relativeY * relativeY);
		}

		return result;
	}

}
