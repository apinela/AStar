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

	private final int imageHeight;
	private final int imageMaxGrayValue;
	private final Node[][] imageNodes;
	private final int imageWidth;

	public PgmImage(final String imagePath) throws FileNotFoundException,
			IOException {

		/*
		 * Parse file header (width, height and max gray value of pgm matrix)
		 */
		final FileInputStream is = new FileInputStream(imagePath);

		final Scanner isScanner = new Scanner(is);

		isScanner.nextLine();
		isScanner.nextLine();

		this.imageWidth = isScanner.nextInt();
		this.imageHeight = isScanner.nextInt();
		this.imageMaxGrayValue = isScanner.nextInt();

		this.imageNodes = new Node[this.imageHeight][this.imageWidth];

		/*
		 * Parse pgm matrix it self into a List of Nodes
		 */

		for (int y = 0; y < this.imageHeight; y++)
			for (int x = 0; x < this.imageWidth; x++)
				this.imageNodes[y][x] = new Node(x, y, isScanner.nextInt());

		/*
		 * Close stream
		 */
		is.close();

	}

	public BufferedImage exportImage(final boolean showMarkedPath) {
		final BufferedImage image = new BufferedImage(this.imageWidth,
				this.imageHeight, BufferedImage.TYPE_INT_RGB);
		int gv = 0;
		for (int y = 0; y < this.imageHeight; y++)
			for (int x = 0; x < this.imageWidth; x++)
				if (this.imageNodes[y][x].isPartOfPath()) {
					gv = 255;
					image.setRGB(x, y, new Color(0, gv, 0).getRGB());
				} else {
					gv = this.imageNodes[y][x].getGrayValue();
					image.setRGB(x, y, new Color(gv, gv, gv).getRGB());
				}

		return image;
	}

	public int getImageHeight() {
		return this.imageHeight;
	}

	public int getImageMaxGrayValue() {
		return this.imageMaxGrayValue;
	}

	public Node[][] getImageNodes() {
		return this.imageNodes;
	}

	public int getImageWidth() {
		return this.imageWidth;
	}

	public Node getNode(final int x, final int y) {

		try {
			return this.imageNodes[y][x];
		} catch (final Exception e) {
			return null;
		}

	}

	public void writePgmImage(final String path, final boolean showMarkedPath)
			throws IOException {
		final File file = new File(path);
		final BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		final StringBuilder sb = new StringBuilder();

		sb.append("P2\n");
		sb.append("# CREATOR: EDA1314-5338\n");
		sb.append(this.imageWidth + " " + this.imageHeight + "\n");
		sb.append(this.imageMaxGrayValue + "\n");
		for (int y = 0; y < this.imageHeight; y++)
			for (int x = 0; x < this.imageWidth; x++)
				sb.append(showMarkedPath
						&& this.imageNodes[y][x].isPartOfPath() ? "255"
						: this.imageNodes[y][x].getGrayValue() + "\n");

		try {

			writer.write(sb.toString());
		} finally {
			if (writer != null)
				writer.close();
		}

	}

}
