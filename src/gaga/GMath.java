package gaga;

/********************************************************************
 * 数学的な演算を行うクラスです。
 ********************************************************************/
public final class GMath {

	private GMath() {
	}

	/**
	 * ２つのスプライトが衝突したかを判定します。
	 *
	 * @param s1
	 *            １つ目のスプライト
	 * @param s2
	 *            ２つ目のスプライト
	 * @param radius
	 *            衝突したとみなす距離
	 * @return trueなら衝突している、falseなら衝突していない
	 */
	public static boolean isCollision(GSprite s1, GSprite s2, int radius) {
		if (!s1.isActive() || !s2.isActive()) {
			return false;
		}
		int dx = (int) Math.pow(s1.X() - s2.X(), 2);
		int dy = (int) Math.pow(s1.Y() - s2.Y(), 2);
		return (dx + dy) < (radius * radius);
	}
}
