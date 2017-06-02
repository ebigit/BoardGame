package boardgame;

import java.awt.Graphics;

import gaga.GSprite;

abstract public class Items extends GSprite {

	@Override
	abstract public void draw(Graphics g);

	@Override
	abstract public void update();

}
