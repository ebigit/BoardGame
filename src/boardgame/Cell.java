package boardgame;

import java.awt.Color;
import java.awt.Graphics;

import gaga.GSprite;

public class Cell extends GSprite {

	private CellType cellType;
	private int tick = 0;
	private int who;
	private int coin = 0;

	public Cell(int x, int y, CellType cellType) {
		this.x = x;
		this.y = y;
		this.cellType = cellType;
		who = 6;
	}

	public void init() {
		who = 6;
	}

	@Override
	public void draw(Graphics g) {
		cellType.draw(g, tick, X(), Y(), who);
	}

	public void seledraw(Graphics g) {
		g.fillRect(X() - 15, Y() - 15, 30, 30);
	}

	public void Betcoin(int coin) {
		this.coin = coin;
	}

	public int getcoin() {
		return coin;
	}

	public boolean checkbet(int coin) {
		return who == 6 && coin >= 10;
	}

	public boolean checkenemy(int who) {
		if (who == 4) {
			return this.who == 5;
		} else {
			return this.who == 4;
		}
	}

	public boolean checkmy(int who) {
		return who == this.who;
	}

	public void bonus() {
		coin *= 2.5;
	}

	public void lightdraw(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(X() - 16, Y() - 16, 31, 31);
	}

	@Override
	public void update() {
	}

	public CellType CellType() {
		return cellType;
	}

	public boolean isBlank() {
		return cellType.isBlank();
	}

	public void updateWho(int who) {
		this.who = who;
	}

	public int getWho() {
		return who;
	}
}
