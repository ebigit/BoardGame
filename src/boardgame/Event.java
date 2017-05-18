package boardgame;

import java.awt.Graphics;

interface Event {

	void draw(Graphics g, int dir, int x, int y);

	void update(int tick);

}
