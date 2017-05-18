package gaga;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

final class LibImage {

	private LibImage() {
	}

	static int a(int c) {
		return c >>> 24;
	}

	static int r(int c) {
		return c >> 16 & 0xff;
	}

	static int g(int c) {
		return c >> 8 & 0xff;
	}

	static int b(int c) {
		return c & 0xff;
	}

	static int rgb(int r, int g, int b) {
		return 0xff000000 | r << 16 | g << 8 | b;
	}

	static int argb(int a, int r, int g, int b) {
		return a << 24 | r << 16 | g << 8 | b;
	}

	static BufferedImage createBI(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	}

	static BufferedImage createBI(GImage img) {
		BufferedImage bi = createBI(img.getWidth(), img.getHeight());
		bi.getGraphics().drawImage(img.getImage(), 0, 0, img.getWidth(), img.getHeight(), null);
		return bi;
	}

	static BufferedImage createBI(GImage img, int width, int height) {
		BufferedImage bi = createBI(width, height);
		bi.getGraphics().drawImage(img.getImage(), 0, 0, width, height, null);
		return bi;
	}

	static void clearBI(BufferedImage bi) {
		Graphics2D g2D = bi.createGraphics();
		g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		Rectangle2D.Double rect = new Rectangle2D.Double(0, 0, bi.getWidth(), bi.getHeight());
		g2D.fill(rect);
	}

	static void setAlphaBI(BufferedImage bi, double alpha) {
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				int c = bi.getRGB(x, y);
				int rgb = argb((int) (255 * alpha), r(c), g(c), b(c));
				bi.setRGB(x, y, rgb);
			}
		}
	}

	static void toShadowBI(BufferedImage bi, Color color) {
		int r = r(color.getRGB());
		int g = g(color.getRGB());
		int b = b(color.getRGB());
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				int c = bi.getRGB(x, y);
				if (a(c) != 0) {
					int rgb = argb(255, r, g, b);
					bi.setRGB(x, y, rgb);
				}
			}
		}
	}

	static int posX(GImage img, GPos pos, int x) {
		switch (pos) {
		case TOP:
		case CENTER:
		case BOTTOM:
			return x - img.getWidth() / 2;
		case TOP_RIGHT:
		case RIGHT:
		case BOTTOM_RIGHT:
			return x - img.getWidth();
		case TOP_LEFT:
		case LEFT:
		case BOTTOM_LEFT:
		default:
			return x;
		}
	}

	static int posY(GImage img, GPos pos, int y) {
		switch (pos) {
		case TOP:
		case TOP_LEFT:
		case TOP_RIGHT:
			return y;
		case LEFT:
		case CENTER:
		case RIGHT:
			return y - img.getHeight() / 2;
		case BOTTOM:
		case BOTTOM_LEFT:
		case BOTTOM_RIGHT:
		default:
			return y - img.getHeight();
		}
	}
}