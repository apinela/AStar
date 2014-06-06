package pt.ipbeja.estig.eda.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageView extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3436022022110009197L;

	public static void RenderImage(final String title, final Image image) {
		try {
			class ImageThread implements Runnable {
				Image image;
				String title;

				ImageThread(final String title, final Image image) {
					this.image = image;
					this.title = title;
				}

				@Override
				public void run() {
					final JFrame f = new JFrame();
					f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					final ImageView iv = new ImageView(this.image);
					// f.setLayout(new BorderLayout());
					f.add(iv, BorderLayout.CENTER);
					f.pack();
					f.setTitle(this.title);
					f.setVisible(true);
					f.setResizable(false);

				}

			}
			javax.swing.SwingUtilities.invokeAndWait(new ImageThread(title,
					image));

		} catch (final Exception e) {
		}

	}

	private final Image image;

	public ImageView(final Image image) {
		this.image = image;
		this.setPreferredSize(new Dimension(image.getWidth(null), image
				.getHeight(null)));
	}

	@Override
	public void paintComponent(final Graphics g) {
		super.paintComponent(g);
		g.drawImage(this.image, 0, 0, this);
	}

}
