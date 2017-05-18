package gaga;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/********************************************************************
 * 特定種類のスプライトをリスト管理するクラスです。<br>
 * 利用可能なスプライトを一括で描画したり、更新したりすることができます。<br>
 * また、利用不可となったスプライトは自動的にリストから削除されます。<br>
 ********************************************************************/
public class GSpriteList<T extends GSprite> implements Iterable<T> {

	private List<T> list = new ArrayList<>();

	@Override
	public Iterator<T> iterator() {
		list.removeIf(s -> !s.isActive());
		return list.iterator();
	}

	/**
	 * 利用可能なスプライトの個数を返します。
	 *
	 * @return 利用可能なスプライトの個数
	 */
	public int size() {
		return (int) list.stream().filter(s -> s.isActive()).count();
	}

	/**
	 * スプライトを追加します。<br>
	 * すでに同じインスタンスを保持していた場合は無視されます。<br>
	 *
	 * @param sprite
	 *            追加するスプライト
	 */
	public void add(T sprite) {
		if (!list.contains(sprite)) {
			list.add(sprite);
		}
	}

	/**
	 * リストを空にします。
	 */
	public void clear() {
		list.clear();
	}

	/**
	 * 利用可能な全てのスプライトを描画します。
	 *
	 * @param g
	 *            Graphics
	 */
	public void draw(Graphics g) {
		list.removeIf(s -> !s.isActive());
		list.forEach(s -> s.draw(g));
	}

	/**
	 * 利用可能な全てのスプライトを更新します。
	 */
	public void update() {
		list.stream().filter(s -> s.isActive()).forEach(s -> s.update());
	}
}