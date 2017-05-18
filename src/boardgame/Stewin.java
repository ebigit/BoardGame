package boardgame;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Stewin {
	private Player player;
	private Image pic;
	private int x, y;

	public Stewin(Player p, int x, int y) {
		this.x = x;
		this.y = y;
		this.player = p;
		try {
			pic = ImageIO.read(new File("res\\stewin.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {
		g.drawImage(pic, x, y, null);
		g.drawString(player.getMoneyStr(), x + 32, y + 64);
		g.drawString(player.getTroutStr(), x + 32, y + 128);
	}
}
