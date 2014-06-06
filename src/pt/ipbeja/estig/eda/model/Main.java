package pt.ipbeja.estig.eda.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.estig.eda.view.ImageView;

public class Main {

	public static void exportGnuPlotData(final List<Double> nodes,
			final String pathToSaveFile) {

		try {
			final FileWriter file = new FileWriter(new File(pathToSaveFile));
			final StringBuilder sb = new StringBuilder();
			;

			for (int n = 0; n < nodes.size(); n++)
				sb.append((n+1) + " " + nodes.get(n) + "\n");
			file.write(sb.toString());
			file.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {

		final boolean calcMedians = false;
		final int numOfTries = 30;

		try {
			final PgmImage image = new PgmImage(System.getProperty("user.dir")
					+ "\\img\\peppersgrad.pgm");
			final AStar a = new AStar(image);
			final Node startNode = image.getNode(192, 48);
			final Node finalNode = image.getNode(260, 508);

			if (calcMedians) {
				final List<Double> nodesM = new ArrayList<Double>();
				final List<Double> nodesE = new ArrayList<Double>();

				for (int m = 0; m < numOfTries; m++) {
					a.findBestRoute(startNode, finalNode,
							AStar.METHOD_MANHATTAN);
					nodesM.add(a.getAvgTime());
				}
				Main.exportGnuPlotData(nodesM, System.getProperty("user.dir")
						+ "\\static\\plot_manhattan_data.txt");

				for (int m = 0; m < numOfTries; m++) {
					a.findBestRoute(startNode, finalNode,
							AStar.METHOD_EUCLIDEAN);
					nodesE.add(a.getAvgTime());
				}
				Main.exportGnuPlotData(nodesE, System.getProperty("user.dir")
						+ "\\static\\plot_euclidean_data.txt");
			} else {
				a.findBestRoute(startNode, finalNode, AStar.METHOD_MANHATTAN);
				image.writePgmImage(System.getProperty("user.dir")
						+ "\\img\\peppersgrad_manhattan_test.pgm", true);
				ImageView.RenderImage(
						"Manhattan - Elapsed time: " + a.getElapsedTime()
								+ "ns | Avg time: " + a.getAvgTime() + "ns",
						image.exportImage(true));
				a.findBestRoute(startNode, finalNode, AStar.METHOD_EUCLIDEAN);
				image.writePgmImage(System.getProperty("user.dir")
						+ "\\img\\peppersgrad_euclidean_test.pgm", true);
				ImageView.RenderImage(
						"Euclidean - Elapsed time: " + a.getElapsedTime()
								+ "ns | Avg time: " + a.getAvgTime() + "ns",
						image.exportImage(true));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

}
