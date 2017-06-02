package boardgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ステータスウィンドウ
 *
 * プレイヤーの所持コインと 自分のマスの数を表示
 *
 */
public class Stewin {

	private Player player;
	private Image pic;
	private int x, y;
	private Icon icon;

	public Stewin(Player p, int x, int y) {
		this.x = x;
		this.y = y;
		this.player = p;
		icon=new Icon(x + 57, y + 280);
		try {
			pic = ImageIO.read(new File("res\\stewin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {

		g.drawImage(pic, x, y, null);
		g.setColor(Color.BLACK);
		g.drawString(player.getMoneyStr(), x + 50, y + 64);
		g.drawString(player.getTroutStr(), x + 50, y + 128);
		// g.setFont(new Font(null, Font.BOLD, 30));
		g.drawString("ダブル", x + 70, y + 300);
		g.drawString("強化", x + 70, y + 350);
		g.drawString("弱化", x + 70, y + 400);
		icon.draw(g);
	}

	public void update() {
		icon.update();
	}

	class Icon {
		int item = 0;
		int x, y;

		public Icon(int x, int y) {
			this.x = x;
			this.y = y;
		}

		void draw(Graphics g) {
			g.setColor(Color.YELLOW);
			g.fillOval(x - 5, y + (50 * item) - 5, 10, 10);
		}

		void update() {
			int value = My.GAGA.keyboard().vertical().value();
			if (My.GAGA.keyboard().vertical().isPressClear()) {
				item += value;
				if (item < 0) {
					item = 0;
				} else if (item > 2) {
					item = 2;
				}
			}
		}
	}
}