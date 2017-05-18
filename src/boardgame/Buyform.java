package boardgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gaga.GSprite;

public class Buyform extends GSprite {
	private Image pic;
	private int buyid;
	private boolean[] buy = { true, false };
	private boolean clickf;

	public Buyform(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			pic = ImageIO.read(new File("res\\buywin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void init(){
		clickf=false;
		buyid=0;
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(pic, X(), Y(), null);
		g.setColor(Color.GRAY);
		switch (buyid) {
		case 0:
			g.drawRect(X() + 40, Y() + 25, 67, 28);
			break;
		case 1:
			g.drawRect(X() + 40, Y() + 57, 67, 28);
			break;
		default:
			break;
		}
	}

	public void update(int tick) {
		int value = My.GAGA.keyboard().vertical().value();
		if (My.GAGA.keyboard().vertical().isPressClear()) {
			buyid += value;

			if (buyid < 0) {
				buyid = 0;
			} else if (buyid > 1) {
				buyid = 1;
			}
		}
		if (My.GAGA.keyboard().enter().isPressClear()) {
			clickf=true;
		}
	}
	
	public boolean onclick(){
		return clickf;
	}
	public boolean getanther(){
		return buy[buyid];
	}

	@Override
	public void update() {

	}

}
