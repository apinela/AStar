package pt.ipbeja.estig.eda.model;

import java.util.ArrayList;
import java.util.List;

public class AStar {

	public static final int METHOD_EUCLIDEAN = 0;
	public static final int METHOD_MANHATTAN = 1;
	private PgmImage image; // Portable Gray Map loaded into memory
	private Node cNode; // Current node
	private Node sNode; // Start node
	private Node gNode; // Goal node
	private List<Node> nodesOL; // Nodes open list
	private List<Node> nodesCL; // Nodes closed list
	private List<Double> scoreList; // Score list of nodes in open list
	private int normalizeHSteps;
	private int method;
	private double elapsedTime;

	public AStar(PgmImage image) {
		this.image = image;

	}

	public List<Node> findBestRoute(Node startNode, Node goalNode, int method) {
		long startTime = System.nanoTime();
		this.sNode = startNode;
		this.gNode = goalNode;
		this.method = method;
		this.nodesOL = new ArrayList<Node>();
		this.nodesCL = new ArrayList<Node>();
		this.normalizeHSteps = 0;

		cNode = sNode;
		cNode.setPartOfPath(true);
		cNode.setTime(System.nanoTime());
		nodesCL.add(cNode);

		while (!cNode.equals(gNode)) {
			
			nodesOL = getNeighbours(cNode);

			scoreList = new ArrayList<Double>();

			for (Node n : nodesOL)
				scoreList.add(fScore(n));

			int index = 0;
			double score = Double.MAX_VALUE;

			for (int i = 0; i < scoreList.size(); i++) {
				if (scoreList.get(i) < score) {
					score = scoreList.get(i);
					index = i;
				}
			}

			cNode = nodesOL.get(index);
			cNode.setPartOfPath(true);
			cNode.setTime(System.nanoTime()-startTime);
			nodesCL.add(cNode);

		}
		this.elapsedTime = System.nanoTime() - startTime;
		
		return nodesCL;

	}

	public double getElapsedTime() {
		return elapsedTime;
	}
	
	public double getAvgTime() {		
		return elapsedTime / (nodesCL.size());
		
	}

	private double fScore(Node nodeToEvaluateScore) {
		double g = gScore(nodeToEvaluateScore);
		double h = hScore(nodeToEvaluateScore);
		if (normalizeHSteps==0)
			normalizeHSteps = (int) (5 * Math.round(Math.sqrt(h) / 5));
		return g + (h / normalizeHSteps);
	}

	private double hScore(Node nodeToEvaluateScore) {

		double result;
		int relativeX;
		int relativeY;

		if (this.method == 1) {
			// Manhattan distance
			relativeX = Math.abs(gNode.getX() - nodeToEvaluateScore.getX());
			relativeY = Math.abs(gNode.getY() - nodeToEvaluateScore.getY());
			result = relativeX + relativeY;
		} else {
			// Euclidean distance
			relativeX = nodeToEvaluateScore.getX() - gNode.getX();
			relativeY = nodeToEvaluateScore.getY() - gNode.getY();
			result = (double) Math.sqrt((relativeX * relativeX) + (relativeY * relativeY));
		}

		return result;
	}

	private double gScore(Node nodeToEvaluateScore) {
		return 1 - ( (double) (nodeToEvaluateScore.getGrayValue()) / (this.image.getImageMaxGrayValue()));
	}

	private List<Node> getNeighbours(Node cNode) {
		List<Node> nb = new ArrayList<Node>();
		for (int x = -1; x < 2; x++) {
			for (int y = -1; y < 2; y++) {
				Node n = this.image.getNode(cNode.getX() + x, cNode.getY() + y);
				if (n != null)
					if (!(x == 0 && y == 0) && !nodesCL.contains(n)) {
						nb.add(n);
					}
			}
		}
		return nb;
	}

}
