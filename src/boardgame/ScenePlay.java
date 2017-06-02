package boardgame;

import java.awt.Graphics;

import gaga.GScene;

/**
 * プレイ画面
 */
public class ScenePlay implements GScene {

	private enum State {
		MAP, DICE, BET, DAMAGE, BUY, PLAN
	}

	private State state;
	private Player mainplayer, subplayer;
	private P1 p1;
	private P2 p2;
	private Stewin sw1, sw2;
	private Betform bw;
	private Buyform bf;
	private int turncount;

	public GScene init() {
		state = State.PLAN;
		p1 = new P1(7, 7);
		p2 = new P2(22, 7);
		mainplayer = p1;
		subplayer = p2;
		sw1 = new Stewinp1(p1, 32, 128);
		sw2 = new Stewinp2(p2, 928, 128);
		bw = new Betform(562, 128);
		bf = new Buyform(562, 128);
		turncount = 0;
		My.StageSet.stage.init();
		return this;
	}

	@Override
	public void draw(int tick, Graphics g) {
		sw1.draw(g);
		sw2.draw(g);
		My.StageSet.stage.draw(g);
		My.StageSet.stage.lightCell(g, mainplayer.MX(), mainplayer.MY());
		subplayer.draw(g);
		mainplayer.draw(g);
		switch (state) {
		case MAP:
			break;
		case DICE:
			My.DiceSet.dice.draw(g);
			break;
		case BET:
			bw.draw(g);
			break;
		case DAMAGE:
			break;
		case BUY:
			bf.draw(g);
			break;
		case PLAN:
			break;
		}
	}

	@Override
	public GScene update(int tick) {
		My.StageSet.stage.update();
		if (turncount >= 3) {
			return My.SceneSet.RESULT.init(judge());
		}
		switch (state) {
		case MAP:
			updatemap(tick);
			break;
		case DICE:
			updatedice(tick);
			break;
		case BET:
			updatebet(tick);
			break;
		case DAMAGE:
			updatedamage(tick);
			break;
		case BUY:
			updatebuy(tick);
			break;
		case PLAN:
			updateplan(tick);
			break;
		default:
			break;
		}
		return this;
	}

	// 移動中の処理
	private void updatemap(int tick) {
		mainplayer.update();
	}

	// サイコロ選択時の処理
	private void updatedice(int tick) {
		My.DiceSet.dice.update();
		if (My.DiceSet.dice.ResultFlag()) {
			state = State.MAP;
		}
	}

	// マスを塗るときの処理
	private void updatebet(int tick) {
		int wcoin = bw.update(tick, mainplayer.getMoney());
		mainplayer.UseMoney(wcoin);
		My.StageSet.stage.betcoin(mainplayer.MX(), mainplayer.MY(), wcoin);
		if (wcoin > 0) {
			mainplayer.peint();
			turnChange();
		}

	}

	// 敵のマスにとまってしまった時の処理
	private void updatedamage(int tick) {
		int wcoin = My.StageSet.stage.getcoin(mainplayer.MX(), mainplayer.MY());
		mainplayer.UseMoney(wcoin);
		subplayer.AddMoney(wcoin);
		state = State.BUY;
	}

	// マスの買い取り処理
	private void updatebuy(int tick) {
		int wcoin = My.StageSet.stage.getcoin(mainplayer.MX(), mainplayer.MY());
		bf.update(tick);
		if (bf.onclick()) {
			if (bf.getanther()) {
				mainplayer.UseMoney(wcoin);
				subplayer.AddMoney(wcoin / 2);
				mainplayer.peint();
				subplayer.down();
			}
			turnChange();
		}
	}

	// 作戦中の処理
	private void updateplan(int tick) {
		if (mainplayer == p1) {
			sw1.update();
		} else {
			sw2.update();
		}
		if (My.GAGA.keyboard().enter().isPressClear()) {
			state = State.DICE;
			My.DiceSet.dice.init();
		}
	}

	// 勝ち負けを判定する
	private Player judge() {
		if (p1.gettrout() == p2.gettrout()) {
			if (p1.getMoney() == p2.getMoney()) {
				return null;
			} else {
				if (p1.getMoney() > p2.getMoney()) {
					return p1;
				} else {
					return p2;
				}
			}

		} else {
			if (p1.gettrout() > p2.gettrout()) {
				return p1;
			} else {
				return p2;
			}
		}

	}

	// プレイヤー交代
	void turnChange() {
		if (mainplayer == p2) {
			turncount++;
		}
		mainplayer = mainplayer == p1 ? p2 : p1;
		subplayer = subplayer == p2 ? p1 : p2;
		state = State.PLAN;
	}

	void bet() {
		state = State.BET;
		bw.init();
	}

	void buying() {
		state = State.DAMAGE;
		bf.init();
	}

}
