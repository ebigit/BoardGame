package boardgame;

import gaga.GSprite;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Dice extends GSprite {
	private Random rand = new Random();
	private DiceNum diceNum;
	private boolean stopFlag = false, resultFlag = false;

	public Dice() {
		this.x = My.MX - 25;
		this.y = My.MY - 300;
		diceNum = null;
	}

	public void init() {
		stopFlag = false;
		resultFlag = false;
		diceNum = null;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.CYAN);
		g.drawRect(X(), Y(), 50, 50);
		if (diceNum != null) {
			diceNum.draw(g, tick, X() + 25, Y() + 25);
		}
	}

	@Override
	public void update() {
		if (!stopFlag) {
			switch (rand.nextInt(6)) {
			case 0:
				diceNum = DiceNum.one;
				break;
			case 1:
				diceNum = DiceNum.two;
				break;
			case 2:
				diceNum = DiceNum.three;
				break;
			case 3:
				diceNum = DiceNum.four;
				break;
			case 4:
				diceNum = DiceNum.five;
				break;
			case 5:
				diceNum = DiceNum.six;
				break;
			}
			if (My.GAGA.keyboard().enter().isPressClear()) {
				stopFlag = true;
			}
		} else {
			tick++;
			if (tick % 50 == 0) {
				resultFlag = true;
			}
		}

	}

	public boolean ResultFlag() {
		return resultFlag;
	}

	public int getNumber() {
		if (diceNum == null) {
			return 0;
		}
		return diceNum.ordinal() + 1;
	}
}
