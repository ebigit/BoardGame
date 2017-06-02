package boardgame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * サイコロは画像ではないため
 * 目によって丸の位置を変える
 */
public enum DiceNum {
	one, two, three, four, five, six;
	public void draw(Graphics g, int tick, int x, int y) {
		g.setColor(Color.GRAY);
		switch (this) {
		case one:
			g.setColor(Color.RED);
			g.fillOval(x - 9, y - 9, 18, 18);
			break;
		case two:
			g.fillOval(x - 20, y - 20, 10, 10);
			g.fillOval(x + 10, y + 10, 10, 10);
			break;
		case three:
			g.fillOval(x - 20, y - 20, 10, 10);
			g.fillOval(x + 10, y + 10, 10, 10);
			g.fillOval(x - 5, y - 5, 10, 10);
			break;
		case four:
			g.fillOval(x - 20, y - 20, 10, 10);
			g.fillOval(x + 10, y + 10, 10, 10);
			g.fillOval(x + 10, y - 20, 10, 10);
			g.fillOval(x - 20, y + 10, 10, 10);
			break;
		case five:
			g.fillOval(x - 20, y - 20, 10, 10);
			g.fillOval(x + 10, y + 10, 10, 10);
			g.fillOval(x + 10, y - 20, 10, 10);
			g.fillOval(x - 20, y + 10, 10, 10);
			g.fillOval(x - 5, y - 5, 10, 10);
			break;
		case six:
			g.fillOval(x - 20, y - 20, 10, 10);
			g.fillOval(x + 10, y + 10, 10, 10);
			g.fillOval(x + 10, y - 20, 10, 10);
			g.fillOval(x - 20, y + 10, 10, 10);
			g.fillOval(x - 20, y - 5, 10, 10);
			g.fillOval(x + 10, y - 5, 10, 10);
			break;
		}
	}
}
