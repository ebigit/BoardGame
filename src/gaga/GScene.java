package gaga;

import java.awt.Graphics;

/********************************************************************
 * シーンを表現するためのインタフェースです。<br>
 * 自作のシーンを作成する際には、このインタフェースを実装する必要があります。<br>
 ********************************************************************/
public interface GScene {

	/**
	 * 描画用のメソッドです。自動的に呼び出されます。
	 *
	 * @param tick
	 *            経過時間(フレーム)
	 * @param g
	 *            Graphics
	 */
	void draw(int tick, Graphics g);

	/**
	 * 更新用のメソッドです。自動的に呼び出されます。
	 *
	 * @param tick
	 *            経過時間(フレーム)
	 * @return 次のシーン
	 */
	GScene update(int tick);
}
