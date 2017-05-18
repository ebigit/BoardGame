package boardgame;

import gaga.GSprite;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Betform extends GSprite {
	private Image pic;
	private int[] betcoin = { 10, 100, 1000 };
	public int betid = 0;

	public Betform(int x, int y) {
		this.x = x;
		this.y = y;
		try {
			pic = ImageIO.read(new File("res\\betwin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init(){
		betid=0;
	}

	public void draw(Graphics g) {
		g.drawImage(pic, X(), Y(), null);
		g.setColor(Color.GRAY);
		switch (betid) {
		case 0:
			g.drawRect(X() + 40, Y() + 15, 40, 23);
			break;
		case 1:
			g.drawRect(X() + 40, Y() + 36, 55, 23);
			break;
		case 2:
			g.drawRect(X() + 40, Y() + 57, 73, 23);
			break;
		}

	}

	public int update(int tick, int wcoin) {
		int value = My.GAGA.keyboard().vertical().value();
		if (My.GAGA.keyboard().vertical().isPressClear()) {

			betid += value;

			if (betid < 0) {
				betid = 0;
			} else if (betid > 2) {
				betid = 2;
			}
			if (wcoin < betcoin[betid]) {
				betid -= value;
			}
		}
		if (My.GAGA.keyboard().enter().isPressClear()) {
			return betcoin[betid];
		}
		return 0;
	}

	public int BetCoin() {
		return betcoin[betid];
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

}
