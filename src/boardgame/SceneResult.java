package boardgame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import gaga.GScene;

/**
 * 勝敗判定画面
 */
public class SceneResult implements GScene {
	private Player winner;

	public SceneResult init(Player winner) {
		this.winner = winner;
		return this;
	}

	@Override
	public void draw(int tick, Graphics g) {
		g.setFont(new Font(null, Font.BOLD, 60));
		g.setColor(Color.MAGENTA);
		if (winner != null) {
			g.drawString("勝者" + winner.name, 500, 200);
		} else {
			g.drawString("引き分け", 500, 200);
		}
	}

	@Override
	public GScene update(int tick) {
		tick++;
		if (tick % 100 == 0) {
			return My.SceneSet.TITLE;
		}
		return this;
	}

}
