package gaga;

import java.awt.Graphics;

/********************************************************************
 * スプライトとしての基本機能を備えたクラスです。<br>
 * 自作のスプライトを作成する際には、必要に応じてこのクラスを継承してください。<br>
 * double型のフィールド x, y は座標を管理する際に役に立ちます。これらは protected です。<br>
 * x, y を int 型で得られる X メソッドと Y メソッドがあります。これらは public です。<br>
 * スプライトは new された瞬間に「利用可」の状態になり、kill メソッドで「利用不可」の状態になます。<br>
 * GSpriteList クラスで管理する場合には「利用不可」の状態にあるスプライトは対象外となり、やがて自動的に削除されます。<br>
 * GSpriteList クラスで管理しない場合には、あなたが責任をもって管理するか、あるいは無視してください。<br>
 ********************************************************************/
public abstract class GSprite {

	private boolean isActive;
	protected double x, y;
	protected int tick = 0;

	/**
	 * コンストラクタ
	 */
	protected GSprite() {
		isActive = true;
	}

	/**
	 * このスプライトが利用可能であるかを返します。<br>
	 * GSpriteList クラスが利用しますが、あなたが利用しても構いません。<br>
	 *
	 * @return trueなら利用可、falseなら利用不可
	 */
	public final boolean isActive() {
		return isActive;
	}

	/**
	 * このスプライトを利用不可の状態にします。<br>
	 */
	public final void kill() {
		isActive = false;
	}

	/**
	 * 座標のX値をint型で返します。<br>
	 *
	 * @return X値
	 */
	public final int X() {
		return (int) x;
	}

	/**
	 * 座標のY値をint型で返します。
	 *
	 * @return Y値
	 */
	public final int Y() {
		return (int) y;
	}

	/**
	 * 描画用のメソッドです。
	 *
	 * @param g
	 *            Graphics
	 */
	public abstract void draw(Graphics g);

	/**
	 * 更新のメソッドです。
	 */
	public abstract void update();
}