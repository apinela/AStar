package pt.ipbeja.estig.eda.model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import pt.ipbeja.estig.eda.view.ImageView;

public class Main {

	public static void main(String[] args) {
		
		final boolean calcMedians = false;
		final int numOfTries = 30;

		try {
			PgmImage image = new PgmImage(System.getProperty("user.dir") + "\\img\\peppersgrad.pgm");
			AStar a = new AStar(image);
			if (calcMedians) {
				List<Double> nodesM = new ArrayList<Double>();
				List<Double> nodesE = new ArrayList<Double>();
				
				for (int m = 0; m < numOfTries; m++) {
					a.findBestRoute(image.getNode(192, 48), image.getNode(260, 508), AStar.METHOD_MANHATTAN);
					nodesM.add(a.getAvgTime());
				}
				exportGnuPlotData(nodesM, System.getProperty("user.dir") + "\\static\\plot_manhattan_data.txt");
				
				for (int m = 0; m < numOfTries; m++) {
					a.findBestRoute(image.getNode(192, 48), image.getNode(260, 508), AStar.METHOD_EUCLIDEAN);
					nodesE.add(a.getAvgTime());
				}
				exportGnuPlotData(nodesE, System.getProperty("user.dir") + "\\static\\plot_euclidean_data.txt");
			} else {
				a.findBestRoute(image.getNode(192, 48), image.getNode(260, 508), AStar.METHOD_MANHATTAN);
				image.writePgmImage(System.getProperty("user.dir") + "\\img\\peppersgrad_manhattan_test.pgm", true);
				ImageView.RenderImage("EDA1314-5338 - Manhattan - Elapsed time: " + a.getElapsedTime() + "ns | Avg time: " + a.getAvgTime() + "ns", image.exportImage(true));
				a.findBestRoute(image.getNode(192, 48), image.getNode(260, 508), AStar.METHOD_EUCLIDEAN);
				image.writePgmImage(System.getProperty("user.dir") + "\\img\\peppersgrad_euclidean_test.pgm", true);
				ImageView.RenderImage("EDA1314-5338 - Euclidean - Elapsed time: " + a.getElapsedTime() + "ns | Avg time: " + a.getAvgTime() + "ns", image.exportImage(true));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void exportGnuPlotData(List<Double> nodes, String pathToSaveFile) {

		try {
			FileWriter file = new FileWriter(new File(pathToSaveFile));
			StringBuilder sb = new StringBuilder();
			;

			for (int n = 0; n < nodes.size(); n++) {
				sb.append(n + " " + nodes.get(n) + "\n");
			}
			file.write(sb.toString());
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
