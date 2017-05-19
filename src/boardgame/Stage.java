package boardgame;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import gaga.GSprite;
/**
 * ステージクラス
 */
public class Stage extends GSprite {
	private Cell[][] cell = new Cell[15][30];
	private Cell[][] mcell = new Cell[15][30];
	private MapData mapdata = new MapData();

	public Stage(int x, int y) {
		mapdata.mapload();
		this.x = x;
		this.y = y;
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				mcell[i][j] = new Cell(x + j * 32, y + i * 32, mapdata.MAP[i][j]);
			}
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				cell[i][j] = mcell[i][j];
			}
		}
	}

	public void init() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				cell[i][j] = mcell[i][j];
			}
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				cell[i][j].init();
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 30; j++) {
				cell[i][j].draw(g);
			}
		}
	}

	public void seledraw(int x, int y, int dir) {

	}

	@Override
	public void update() {

	}

	public void lightCell(Graphics g, int x, int y) {
		cell[y][x].lightdraw(g);
	}

	public boolean isBlank(int x, int y) {
		return cell[y][x].isBlank();
	}

	public void betcoin(int x, int y, int coin) {
		cell[y][x].Betcoin(coin);
	}

	public int getcoin(int x, int y) {
		return cell[y][x].getcoin();
	}

	public void Fill(int x, int y, int who) {
		cell[y][x].updateWho(who);
	}

	public boolean checkbet(int x, int y, int coin) {
		return cell[y][x].checkbet(coin);
	}

	public boolean checkenemy(int x, int y, int who) {
		return cell[y][x].checkenemy(who);
	}

	public boolean checkmy(int x, int y, int who) {
		return cell[y][x].checkmy(who);
	}

	public void bonus(int x, int y) {
		cell[y][x].bonus();
	}

	// private void checkdir(int dir) {
	// int x, y;
	// switch (dir) {
	// case 0:
	// x = 0;
	// y = -1;
	// break;
	// case 1:
	// x = 1;
	// y = 0;
	// break;
	// case 2:
	// x = 0;
	// y = 1;
	// break;
	// case 3:
	// x = -1;
	// y = 0;
	// break;
	// default:
	// x = 0;
	// y = 0;
	// break;
	// }
	// if (isBlank(mx + x, my + y)) {
	// if (dir != redir(dir)) {
	// }
	// }
	//
	// if (dir >= 3) {
	// return;
	// }
	// checkdir(dir + 1);
	// }
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
	 * マップのデータを外部から読み込み
	 */
	public class MapData {
		public final CellType[][] MAP = new CellType[15][30];

		public void mapload() {
			try (BufferedReader br = new BufferedReader(new FileReader("res\\MapData1.csv"));) {

				String line;
				StringTokenizer token;
				for (int i = 0; (line = br.readLine()) != null; i++) {

					token = new StringTokenizer(line, ",");
					//テキストの文字に応じてそれぞれのマスに変換
					for (int j = 0; token.hasMoreTokens(); j++) {
						switch (token.nextToken()) {
						case "0":
							MAP[i][j] = CellType.ROAD;
							break;
						case "1":
							MAP[i][j] = CellType.WALL;
							break;
						case "3":
							MAP[i][j] = CellType.BASE;
							break;
						}
					}
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}
