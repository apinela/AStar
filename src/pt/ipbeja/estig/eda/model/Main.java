package pt.ipbeja.estig.eda.model;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import pt.ipbeja.estig.eda.view.ImageView;

public class Main {

	public static void main(String[] args) {

		try {
			PgmImage image = new PgmImage(System.getProperty("user.dir") + "\\img\\peppersgrad.pgm");
			AStar a = new AStar(image);
			long startTime = System.currentTimeMillis();
			List<Node> nodes = a.findBestRoute(image.getNode(192, 48), image.getNode(260, 508), AStar.METHOD_EUCLIDEAN);
			long endTime = System.currentTimeMillis() - startTime;
			exportGnuPlotData(nodes, System.getProperty("user.dir") + "\\static\\plot_data.txt");
			image.writePgmImage(System.getProperty("user.dir") + "\\img\\peppersgrad_test.pgm", true);
			ImageView.RenderImage("EDA1314-5338 - Elapsed time: " + endTime + "ms", image.exportImage(true));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void exportGnuPlotData(List<Node> nodes, String pathToSaveFile) {

		try {
			FileWriter file = new FileWriter(new File(pathToSaveFile));
			StringBuilder sb = new StringBuilder();
			;

			for (int n = 0; n < nodes.size(); n++) {
				sb.append(n + " " + nodes.get(n).getTime() + "\n");
			}
			file.write(sb.toString());
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
