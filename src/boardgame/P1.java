package boardgame;

import java.awt.Color;
import java.awt.Graphics;

public class P1 extends Player {

	public P1(int x, int y) {
		super(x, y);
		move_dir = 1;
		mycolor = 4;
		name="P1";
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(X() - 10, Y() - 10, 20, 20);
	}
}
