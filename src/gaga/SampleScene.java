package gaga;

import java.awt.Color;
import java.awt.Graphics;

final class SampleScene implements GScene {

	@Override
	public void draw(int tick, Graphics g) {
		g.setFont(Gaga.FONT);
		g.setColor(Color.WHITE);
		g.drawString("Gaga", 50, 100);
		g.drawString("ver." + Gaga.VERSION, 50, 130);
		g.drawString("presented by Tomohiro Oshikawa", 50, 160);
	}

	@Override
	public GScene update(int tick) {
		return this;
	}
}
