package pt.ipbeja.estig.eda.model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PgmImage {

	private int imageWidth;
	private int imageHeight;
	private int imageMaxGrayValue;
	private Node[][] imageNodes;

	public PgmImage(String imagePath) throws FileNotFoundException, IOException {

		/*
		 * Parse file header (width, height and max gray value of pgm matrix)
		 */
		FileInputStream is = new FileInputStream(imagePath);

		Scanner isScanner = new Scanner(is);

		isScanner.nextLine();
		isScanner.nextLine();

		imageWidth = isScanner.nextInt();
		imageHeight = isScanner.nextInt();
		imageMaxGrayValue = isScanner.nextInt();

		imageNodes = new Node[imageHeight][imageWidth];

		/*
		 * Parse pgm matrix it self into a List of Nodes
		 */

		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				imageNodes[y][x] = new Node(x, y, isScanner.nextInt());
			}
		}

		/*
		 * Close stream
		 */
		is.close();

	}

	public BufferedImage exportImage(boolean showMarkedPath) {
		BufferedImage image = new BufferedImage(this.imageWidth,
				this.imageHeight, BufferedImage.TYPE_BYTE_GRAY);
		int gv = 0;
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {

				if (imageNodes[y][x].isPartOfPath())
					gv = 255;
				else
					gv = imageNodes[y][x].getGrayValue();
				image.setRGB(x, y, new Color(gv, gv, gv).getRGB());

			}
		}

		return image;
	}

	public int getImageHeight() {
		return imageHeight;
	}

	public int getImageMaxGrayValue() {
		return imageMaxGrayValue;
	}

	public Node[][] getImageNodes() {
		return imageNodes;
	}

	public int getImageWidth() {
		return imageWidth;
	}

	public Node getNode(int x, int y) {

		try {
			return imageNodes[y][x];
		} catch (Exception e) {
			return null;
		}

	}

	public void writePgmImage(String path, boolean showMarkedPath)
			throws IOException {
		File file = new File(path);
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		StringBuilder sb = new StringBuilder();

		sb.append("P2\n");
		sb.append("# CREATOR: EDA1314-5338\n");
		sb.append(this.imageWidth + " " + this.imageHeight + "\n");
		sb.append(this.imageMaxGrayValue + "\n");
		for (int y = 0; y < imageHeight; y++) {
			for (int x = 0; x < imageWidth; x++) {
				sb.append((showMarkedPath && imageNodes[y][x].isPartOfPath()) ? "255"
						: imageNodes[y][x].getGrayValue() + "\n");
			}
		}

		try {

			writer.write(sb.toString());
		} finally {
			if (writer != null)
				writer.close();
		}

	}

}
