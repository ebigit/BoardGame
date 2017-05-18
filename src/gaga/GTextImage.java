package gaga;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/********************************************************************
 * テキスト画像を生成するクラスです。<br>
 * 文字周りにボーダーを加えたのちに、画像に変換することができます。<br>
 * テキストを直接描画するよりも、画像に変換してから描画するほうが高速になります。<br>
 * とくにフォントサイズが大きい場合は、この傾向が顕著になります。<br>
 ********************************************************************/
public final class GTextImage {

	private List<BiFunction<GImage, String, GImage>> list = new ArrayList<>();
	private Font font;
	private Color mainColor;
	private FontMetrics fm;
	private int fontHeight, fontAscent;

	GTextImage(Gaga game, Color color, Font font) {
		this.font = font;
		this.mainColor = color;
		fm = game.g().getFontMetrics(this.font);
		fontHeight = (int) ((fm.getAscent() + fm.getDescent()) * 0.8);
		fontAscent = (int) (fontHeight - fm.getDescent() * 0.5);
	}

	/**
	 * 画像に変換します。
	 *
	 * @param str
	 *            文字列
	 * @return 画像
	 */
	public GImage toImage(String str) {
		GImage image = toImage(mainColor, str, 0);
		for (BiFunction<GImage, String, GImage> order : list) {
			image = order.apply(image, str);
		}
		return image;
	}

	/**
	 * ボーダーを追加します。
	 *
	 * @param color
	 *            ボーダーカラー
	 * @param width
	 *            ボーダーの幅
	 * @return テキスト画像管理オブジェクト
	 */
	public GTextImage addBorder(Color color, int width) {
		list.add((i, s) -> {
			int offset = (i.getWidth() - fm.stringWidth(s)) / 2;
			GImage image = toImage(color, s, width + offset);
			BufferedImage bi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics g = bi.getGraphics();
			g.drawImage(image.getImage(), 0, 0, null);
			g.drawImage(i.getImage(), width, width, null);
			return new GImage(bi);
		});
		return this;
	}

	private GImage toImage(Color color, String str, int width) {
		int x = width;
		int y = fontAscent + width;
		int w = fm.stringWidth(str) + width * 2;
		int h = fontHeight + width * 2;
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.getGraphics();
		g.setFont(font);
		g.setColor(color);
		for (int i = -width; i <= width; i++) {
			for (int j = -width; j <= width; j++) {
				g.drawString(str, x + i, y + j);
			}
		}
		return new GImage(bi);
	}
}
