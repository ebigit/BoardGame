package boardgame;

import java.awt.Color;
import java.awt.Graphics;

import gaga.GScene;
/**
 * タイトル画面
 */
public class SceneTitle implements GScene {

	@Override
	public void draw(int tick, Graphics g) {
		g.setFont(My.FONT);
		g.setColor(Color.white);
		g.drawString(My.TITLE, My.MX - 150, My.MY);
	}

	@Override
	public GScene update(int tick) {
		if (My.GAGA.keyboard().enter().isPressClear()) {
			return My.SceneSet.PLAY.init();
		}
		return this;
	}

	public static void main(String[] args) {
		My.GAGA.start(My.SceneSet.TITLE);
	}

}
