package boardgame;

import java.awt.Color;
import java.awt.Graphics;

public class P2 extends Player {

	public P2(int x, int y) {
		super(x, y);
		move_dir = 3;
		mycolor = 5;
		name="P2";
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(X() - 10, Y() - 10, 20, 20);
	}
}
