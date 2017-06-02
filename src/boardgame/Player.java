package boardgame;

import java.awt.Graphics;

import gaga.GSprite;

/**
 * プレイヤース－パークラス
 */
abstract public class Player extends GSprite {
	private final int MAXLENGTH = 32;
	private final int SPEED = 4;

	private int valueX = 0, valueY = 0;
	private int beforex, beforey;
	private boolean moveFlag;
	private int samlength = 0;
	private int mx, my;
	private int movetrout = 0;
	private boolean seleFlag;
	private int dircount = 0;
	private int ifdir = 1;

	protected int move_dir;
	protected int mycolor;
	protected String name;

	public boolean eveFlag;
	// スコアパラメータ
	private int money;
	private int trout_cnt;

	public Player(int x, int y) {
		mx = x;
		my = y;
		this.x = mx * MAXLENGTH + My.sx;
		this.y = my * MAXLENGTH + My.sy;
		money = 500;
		trout_cnt = 0;
	}

	abstract public void draw(Graphics g);

	@Override
	public void update() {
		if (movetrout < My.DiceSet.dice.getNumber()) {// 出た目の数だけマスを進める
			if (seleFlag) {
				selecdir();
			} else {
				move();
			}

		} else {
			// マスを塗れるか、買い取れるかなどの分岐
			movetrout = 0;
			if (My.StageSet.stage.checkbet(mx, my, money)) {
				bet();
			} else if (My.StageSet.stage.checkenemy(mx, my, mycolor)) {
				buying();
			} else if (My.StageSet.stage.checkmy(mx, my, mycolor)) {
				AddMoney(My.StageSet.stage.getcoin(mx, my));
				My.StageSet.stage.bonus(mx, my);
				My.SceneSet.PLAY.turnChange();
			} else {
				My.SceneSet.PLAY.turnChange();
			}

		}

	}

	// 塗りつぶす、マスの増減-----------
	public void peint() {
		My.StageSet.stage.Fill(mx, my, mycolor);
		trout_cnt++;

	}

	public void down() {
		trout_cnt--;
	}

	public int gettrout() {
		return trout_cnt;
	}
	// ---------------------------------

	// マスにBET
	public void bet() {
		My.SceneSet.PLAY.bet();
	}

	// マスの買い取り
	public void buying() {
		My.SceneSet.PLAY.buying();
	}

	// お金のやり取り------------------
	public void UseMoney(int coin) {
		money -= coin;
	}

	public void AddMoney(int coin) {
		money += coin;
	}

	// --------------------------------

	/**
	 * マスの移動（自動）
	 */
	public void move() {
		if (moveFlag) {
			if (tick % 2 == 0) {
				x += valueX * SPEED;
				y += valueY * SPEED;
				samlength += SPEED;
			}
			if (samlength >= MAXLENGTH) {
				moveFlag = false;
				samlength = 0;
				mx += valueX;
				my += valueY;
				movetrout++;
			}



			tick++;
			return;
		}
		dircount = 0;
		checkdir(0);
		if (dircount != 1) {
			seleFlag = true;
		} else if (dircount == 1) {
			move_dir = ifdir;
		}

		switch (move_dir) {
		case 0:
			valueX = 0;
			valueY = -1;
			break;
		case 1:
			valueX = 1;
			valueY = 0;
			break;
		case 2:
			valueX = 0;
			valueY = 1;
			break;
		case 3:
			valueX = -1;
			valueY = 0;
			break;
		default:
			valueX = 0;
			valueY = 0;
			break;
		}
		beforex = -valueX;
		beforey = -valueY;
		if (!My.StageSet.stage.isBlank(mx + valueX, my + valueY)) {
			return;
		}

		moveFlag = true;
		tick = 0;
	}

	//上、右、下、左のどの方向に分岐しているかチェック
	//来た道に戻ることはできない
	private void checkdir(int dir) {
		int x, y;
		switch (dir) {
		case 0:
			x = 0;
			y = -1;
			break;
		case 1:
			x = 1;
			y = 0;
			break;
		case 2:
			x = 0;
			y = 1;
			break;
		case 3:
			x = -1;
			y = 0;
			break;
		default:
			x = 0;
			y = 0;
			break;
		}
		if (My.StageSet.stage.isBlank(mx + x, my + y)) {
			if (dir != redir(move_dir)) {
				dircount++;
				ifdir = dir;
			}
		}

		if (dir >= 3) {
			return;
		}
		checkdir(dir + 1);
	}

	private int redir(int dir) {
		switch (dir) {
		case 0:
			return 2;
		case 1:
			return 3;
		case 2:
			return 0;
		case 3:
			return 1;
		default:
			return dir;
		}
	}

	/**
	 * 分岐点での方向選択（来た方向へは戻れない）
	 */
	public void selecdir() {
		if (My.KEY.horizontal().isPress() || My.KEY.vertical().isPress()) {
			seleFlag = false;
			valueX = My.KEY.horizontal().value();
			valueY = My.KEY.vertical().value();
			if (mx + beforex == mx + valueX && my + beforey == my + valueY) {
				seleFlag = true;
			}
			switch (valueX) {
			case 1:
				move_dir = 1;
				break;
			case -1:
				move_dir = 3;
				break;
			default:
				switch (valueY) {
				case 1:
					move_dir = 2;
					break;
				case -1:
					move_dir = 0;
					break;
				default:
					break;
				}
				break;
			}

		}
	}

	public int MX() {
		return mx;
	}

	public int MY() {
		return my;
	}

	public String getMoneyStr() {
		String str = "";
		return str += money;
	}

	public String getTroutStr() {
		String str = "";
		return str + trout_cnt;
	}

	public int getMoney() {
		return money;
	}
}
