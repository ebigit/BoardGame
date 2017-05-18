package gaga;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/********************************************************************
 * 画像を管理するクラスです。(※注意事項があります)<br>
 * 画像を様々な方法で加工したり、画像を様々な方法で描画することができます。<br>
 * 画像の横幅や縦幅の情報を得ることができます。<br>
 * 画像から Image オブジェクトや、ImageIcon オブジェクトを抽出することができます。<br>
 * 得られる画像はイミュータブル（不変）であることが保証されます。<br>
 * 画像が編集された際には、編集後の新たな画像が返されます。ただし、キャッシュはされません。<br>
 * <br>
 * [注意]<br>
 * 一部の画像は編集すると透明になってしまうことがあります。原因は不明です。<br>
 * 編集すると透明になる場合は、別の画像で試してください。<br>
 ********************************************************************/
public final class GImage {

	private ImageIcon imageIcon;

	GImage(BufferedImage bi) {
		this((Image) bi);
	}

	GImage(Image image) {
		this(new ImageIcon(image));
	}

	GImage(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	// ***********************************************************************************
	// 属性
	// ***********************************************************************************

	/**
	 * この画像の横幅を返します。<br>
	 *
	 * @return ピクセル単位の横幅
	 */
	public int getWidth() {
		return imageIcon.getIconWidth();
	}

	/**
	 * この画像の縦幅を返します。<br>
	 *
	 * @return ピクセル単位の縦幅
	 */
	public int getHeight() {
		return imageIcon.getIconHeight();
	}

	/**
	 * この画像のImageを返します。<br>
	 *
	 * @return Imageオブジェクト
	 */
	public Image getImage() {
		return imageIcon.getImage();
	}

	/**
	 * この画像のImageIconを返します。<br>
	 *
	 * @return ImageIconオブジェクト
	 */
	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	// ***********************************************************************************
	// コピー
	// ***********************************************************************************

	/**
	 * この画像のコピーを返します。<br>
	 *
	 * @return 画像
	 */
	public GImage copy() {
		return new GImage(imageIcon);
	}

	// ***********************************************************************************
	// 反転・回転
	// ***********************************************************************************

	/**
	 * この画像を反転した新たな画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param isHorizontal
	 *            水平方向へ反転
	 * @param isVertical
	 *            垂直方向へ反転
	 * @return 画像
	 */
	public GImage reversal(boolean isHorizontal, boolean isVertical) {
		if (!isHorizontal && !isVertical) {
			return this;
		}
		BufferedImage bi = LibImage.createBI(getWidth(), getHeight());
		if (isHorizontal && isVertical) {
			bi.getGraphics().drawImage(getImage(), getWidth(), getHeight(), -getWidth(), -getHeight(), null);
		} else if (isHorizontal) {
			bi.getGraphics().drawImage(getImage(), getWidth(), 0, -getWidth(), getHeight(), null);
		} else {
			bi.getGraphics().drawImage(getImage(), 0, getHeight(), getWidth(), -getHeight(), null);
		}
		return new GImage(bi);
	}

	/**
	 * この画像を反時計回り（左回転）に90度回転させた画像を返します。<br>
	 * 結果的に画像の横幅と縦幅が逆転します。<br>
	 *
	 * @return 画像
	 */
	public GImage turnL90() {
		BufferedImage bi = LibImage.createBI(getHeight(), getWidth());
		draw(bi.getGraphics(), getHeight() / 2, getWidth() / 2, 90);
		return new GImage(bi);
	}

	/**
	 * この画像を時計回り（右回転）に90度回転させた画像を返します。<br>
	 * 結果的に画像の横幅と縦幅が逆転します。<br>
	 *
	 * @return 画像
	 */
	public GImage turnR90() {
		BufferedImage bi = LibImage.createBI(getHeight(), getWidth());
		draw(bi.getGraphics(), getHeight() / 2, getWidth() / 2, -90);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// リサイズ
	// ***********************************************************************************

	/**
	 * この画像をリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param width
	 *            横幅
	 * @param height
	 *            高さ
	 * @return 画像
	 */
	public GImage setSize(int width, int height) {
		width = (width <= 0) ? 1 : width;
		height = (height <= 0) ? 1 : height;
		if (width == getWidth() && height == getHeight()) {
			return this;
		}
		return new GImage(LibImage.createBI(this, width, height));
	}

	/**
	 * この画像をリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param rate
	 *            倍率
	 * @return 画像
	 */
	public GImage setSize(double rate) {
		return setSize((int) (getWidth() * rate), (int) (getHeight() * rate));
	}

	/**
	 * この画像をリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param width
	 *            横幅
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setWidth(int width, boolean isKeepAspect) {
		if (isKeepAspect) {
			int h = (int) (getHeight() * ((double) width / getWidth()));
			return setSize(width, h);
		}
		return setSize(width, getHeight());
	}

	/**
	 * この画像をリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param height
	 *            高さ
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setHeight(int height, boolean isKeepAspect) {
		if (isKeepAspect) {
			int h = (int) (getHeight() * ((double) height / getHeight()));
			return setSize(height, h);
		}
		return setSize(getWidth(), height);
	}

	/**
	 * この画像の横幅が最大値を超えないようにリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param maxWidth
	 *            横幅の最大値
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setMaxWidth(int maxWidth, boolean isKeepAspect) {
		int w = (getWidth() < maxWidth) ? getWidth() : maxWidth;
		return setWidth(w, isKeepAspect);
	}

	/**
	 * この画像の高さが最大値を超えないようにリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param maxHeight
	 *            高さの最大値
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setMaxHeight(int maxHeight, boolean isKeepAspect) {
		int h = (getHeight() < maxHeight) ? getHeight() : maxHeight;
		return setWidth(h, isKeepAspect);
	}

	/**
	 * この画像の横幅が最小値を下回らないようにリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param minWidth
	 *            横幅の最小値
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setMinWidth(int minWidth, boolean isKeepAspect) {
		int w = (getWidth() > minWidth) ? getWidth() : minWidth;
		return setWidth(w, isKeepAspect);
	}

	/**
	 * この画像の高さが最小値を下回らないようにリサイズした新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param minHeight
	 *            高さの最小値
	 * @param isKeepAspect
	 *            アスペクト比を維持する
	 * @return 画像
	 */
	public GImage setMinHeight(int minHeight, boolean isKeepAspect) {
		int h = (getHeight() < minHeight) ? getHeight() : minHeight;
		return setWidth(h, isKeepAspect);
	}

	// ***********************************************************************************
	// 切り出し
	// ***********************************************************************************

	/**
	 * この画像の一部を切り出した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param width
	 *            横幅
	 * @param height
	 *            縦幅
	 * @return 画像
	 */
	public GImage clip(int x, int y, int width, int height) {
		if (x == 0 && y == 0 && width == getWidth() && height == getHeight()) {
			return this;
		}
		BufferedImage bi = LibImage.createBI(width, height);
		bi.getGraphics().drawImage(getImage(), 0, 0, width, height, x, y, x + width, y + height, null);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// 透明度
	// ***********************************************************************************

	/**
	 * この画像に透明度を設定した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param alpha
	 *            透明度
	 * @return 画像
	 */
	public GImage setAlpha(double alpha) {
		if (alpha >= 1.0) {
			return this;
		}
		alpha = (alpha < 0.0) ? 0.0 : alpha;
		BufferedImage bi = LibImage.createBI(this);
		LibImage.setAlphaBI(bi, alpha);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// シャドウ
	// ***********************************************************************************

	/**
	 * この画像のシャドウを新しい画像として返します。<br>
	 *
	 * @param color
	 *            塗り潰し色
	 * @return 画像
	 */
	public GImage toShadow(Color color) {
		BufferedImage bi = LibImage.createBI(this);
		LibImage.toShadowBI(bi, color);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// 余白
	// ***********************************************************************************

	/**
	 * この画像に余白を追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param width
	 *            上下左右の余白幅
	 * @return 画像
	 */
	public GImage addMargin(int width) {
		return addMargin(width, width, width, width);
	}

	/**
	 * この画像に余白を追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param top_bottom
	 *            上下の余白幅
	 * @param left_right
	 *            左右の余白幅
	 * @return 画像
	 */
	public GImage addMargin(int top_bottom, int left_right) {
		return addMargin(top_bottom, left_right, top_bottom, left_right);
	}

	/**
	 * この画像に余白を追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param top
	 *            上の余白幅
	 * @param right
	 *            右の余白幅
	 * @param bottom
	 *            下の余白幅
	 * @param left
	 *            左の余白幅
	 * @return 画像
	 */
	public GImage addMargin(int top, int right, int bottom, int left) {
		if (top == 0 && right == 0 && bottom == 0 && left == 0) {
			return this;
		}
		int w = right + getWidth() + right;
		int h = top + getHeight() + bottom;
		BufferedImage bi = LibImage.createBI(w, h);
		bi.getGraphics().drawImage(getImage(), right, top, getWidth(), getHeight(), null);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// ボーダー
	// ***********************************************************************************

	/**
	 * この画像にボーダーを追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param color
	 *            ボーダー色
	 * @param width
	 *            上下左右のボーダー幅
	 * @return 画像
	 */
	public GImage addBorder(Color color, int width) {
		return addBorder(color, width, width, width, width);
	}

	/**
	 * この画像にボーダーを追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param color
	 *            ボーダー色
	 * @param top_bottom
	 *            上下のボーダー幅
	 * @param left_right
	 *            左右のボーダー幅
	 * @return 画像
	 */
	public GImage addBorder(Color color, int top_bottom, int left_right) {
		return addBorder(color, top_bottom, left_right, top_bottom, left_right);
	}

	/**
	 * この画像にボーダーを追加した新しい画像を返します。<br>
	 * 画像に変更がなかった場合のみ、この画像をそのまま返します。<br>
	 *
	 * @param color
	 *            ボーダー色
	 * @param top
	 *            上のボーダー幅
	 * @param right
	 *            右のボーダー幅
	 * @param bottom
	 *            下のボーダー幅
	 * @param left
	 *            左のボーダー幅
	 * @return 画像
	 */
	public GImage addBorder(Color color, int top, int right, int bottom, int left) {
		if (top == 0 && right == 0 && bottom == 0 && left == 0) {
			return this;
		}
		int w = right + getWidth() + right;
		int h = top + getHeight() + bottom;
		BufferedImage bi = LibImage.createBI(w, h);
		Graphics g = bi.getGraphics();
		g.setColor(color);
		g.fillRect(0, 0, w, top);
		g.fillRect(0, h - bottom, w, bottom);
		g.fillRect(0, 0, left, h);
		g.fillRect(w - right, 0, right, h);
		g.drawImage(getImage(), left, top, null);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// 画像合成
	// ***********************************************************************************
	/**
	 * この画像に別の画像を埋め込んだ新しい画像を返します。<br>
	 * 画像のサイズは拡張されません。<br>
	 *
	 * @param img
	 *            埋込画像
	 * @param x
	 *            埋込画像のX座標
	 * @param y
	 *            埋込画像のY座標
	 * @return 画像
	 */
	public GImage putImage(GImage img, int x, int y) {
		return putImage(img, x, y, GPos.CENTER);
	}

	/**
	 * この画像に別の画像を埋め込んだ新しい画像を返します。<br>
	 * 画像のサイズは拡張されません。<br>
	 *
	 * @param img
	 *            埋込画像
	 * @param x
	 *            埋込画像のX座標
	 * @param y
	 *            埋込画像のY座標
	 * @param pos
	 *            埋込画像のポジション
	 * @return 画像
	 */
	public GImage putImage(GImage img, int x, int y, GPos pos) {
		int dx = LibImage.posX(img, pos, x);
		int dy = LibImage.posY(img, pos, y);
		BufferedImage bi = LibImage.createBI(this);
		bi.getGraphics().drawImage(img.getImage(), dx, dy, img.getWidth(), img.getHeight(), null);
		return new GImage(bi);
	}

	/**
	 * この画像に別の画像を合成した新しい画像を返します。<br>
	 * 画像のサイズは自動的に拡張されます。<br>
	 *
	 * @param img
	 *            上書画像
	 * @param x
	 *            上書画像のX座標
	 * @param y
	 *            上書画像のY座標
	 * @return 画像
	 */
	public GImage addImage(GImage img, int x, int y) {
		return addImage(img, x, y, GPos.CENTER);
	}

	/**
	 * この画像に別の画像を合成した新しい画像を返します。<br>
	 * 画像のサイズは自動的に拡張されます。<br>
	 *
	 * @param img
	 *            上書画像
	 * @param x
	 *            上書画像のX座標
	 * @param y
	 *            上書画像のY座標
	 * @param pos
	 *            上書画像のポジション
	 * @return 画像
	 */
	public GImage addImage(GImage img, int x, int y, GPos pos) {
		int dx = LibImage.posX(img, pos, x);
		int dy = LibImage.posY(img, pos, y);
		int minX = (dx < 0) ? dx : 0;
		int minY = (dy < 0) ? dy : 0;
		int maxX = (dx + img.getWidth() > getWidth()) ? (dx + img.getWidth()) : getWidth();
		int maxY = (dy + img.getHeight() > getHeight()) ? (dy + img.getHeight()) : getHeight();
		int w = maxX - minX;
		int h = maxY - minY;
		BufferedImage bi = LibImage.createBI(w, h);
		Graphics g = bi.getGraphics();
		int x1 = (dx < 0) ? -dx : 0;
		int y1 = (dy < 0) ? -dy : 0;
		int x2 = x1 + dx;
		int y2 = y1 + dy;
		g.drawImage(getImage(), x1, y1, getWidth(), getHeight(), null);
		g.drawImage(img.getImage(), x2, y2, img.getWidth(), img.getHeight(), null);
		return new GImage(bi);
	}

	// ***********************************************************************************
	// 描画
	// ***********************************************************************************
	/**
	 * この画像を描画します。<br>
	 *
	 * @param g
	 *            Graphics
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 */
	public void draw(Graphics g, int x, int y) {
		draw(g, x, y, GPos.CENTER);
	}

	/**
	 * この画像を描画します。<br>
	 *
	 * @param g
	 *            Graphics
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param pos
	 *            ポジション
	 */
	public void draw(Graphics g, int x, int y, GPos pos) {
		int dx = LibImage.posX(this, pos, x);
		int dy = LibImage.posY(this, pos, y);
		g.drawImage(getImage(), dx, dy, null);
	}

	/**
	 * この画像を描画します。<br>
	 *
	 * @param g
	 *            Graphics
	 * @param x
	 *            X座標
	 * @param y
	 *            Y座標
	 * @param degree
	 *            回転角度
	 */
	public void draw(Graphics g, int x, int y, double degree) {
		AffineTransform af = new AffineTransform();
		af.translate(x - getWidth() / 2, y - getHeight() / 2);
		af.rotate(Math.toRadians(-1.0 * degree), getWidth() / 2, getHeight() / 2);
		((Graphics2D) g).drawImage(getImage(), af, null);
	}
}
