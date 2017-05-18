package gaga;

/********************************************************************
 * ゲームの設定情報を管理するクラスです。<br>
 * ゲームのタイトル、画面のサイズ、画面の中央値、フレームレートなどを管理します。<br>
 ********************************************************************/
public final class GSystem {

	private String title;
	private int width, height, midX, midY, fps;

	GSystem(String title, int width, int height, int fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.midX = width / 2;
		this.midY = height / 2;
		this.fps = fps;
	}

	/**
	 * タイトルを返します。
	 *
	 * @return タイトル
	 */
	public String title() {
		return title;
	}

	/**
	 * 画面の横幅を返します。
	 *
	 * @return 横幅
	 */
	public int width() {
		return width;
	}

	/**
	 * 画面の縦幅を返します。
	 *
	 * @return 縦幅
	 */
	public int height() {
		return height;
	}

	/**
	 * 画面中のX値を返します。
	 *
	 * @return 画面中のX値
	 */
	public int midX() {
		return midX;
	}

	/**
	 * 画面中のY値を返します。
	 *
	 * @return 画面中のY値
	 */
	public int midY() {
		return midY;
	}

	/**
	 * FPSを返します。
	 *
	 * @return FPS
	 */
	public int fps() {
		return fps;
	}
}
