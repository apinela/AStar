package pt.ipbeja.estig.eda.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageView extends JPanel {
	public static void RenderImage(String title, Image image) {
		try {
			class ImageThread implements Runnable {
				Image image;
				String title;

				ImageThread(String title, Image image) {
					this.image = image;
					this.title = title;
				}

				@Override
				public void run() {
					JFrame f = new JFrame();
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					ImageView iv = new ImageView(image);
					//f.setLayout(new BorderLayout());
					f.add(iv, BorderLayout.CENTER);
					f.pack();
					f.setTitle(title);
					f.setVisible(true);
					f.setResizable(false);

				}

			}
			javax.swing.SwingUtilities.invokeAndWait(new ImageThread(title, image));

		} catch (Exception e) {
		}

	}

	private Image image;

	public ImageView(Image image) {
		this.image = image;
		this.setPreferredSize(new Dimension(image.getWidth(null), image
				.getHeight(null)));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, this);
	}

}
