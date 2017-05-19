package boardgame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * マスの種類を区別るための列挙体
 */
public enum CellType {
	ROAD(true), WALL(false), BASE(false);

	private boolean isBlank;
	private Color toumei = new Color(0, 0, 0, 0);
	private Image pic;

	// 通れるか、通れないかが引数
	CellType(boolean isWall) {
		try {
			pic = ImageIO.read(new File("res\\masu.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.isBlank = isWall;
	}

	public boolean isBlank() {
		return isBlank;
	}

	public void draw(Graphics g, int tick, int x, int y, int who) {
		switch (this) {
		case ROAD:
			g.drawImage(pic, x - 15, y - 15, x + 15, y + 15, 32 * who, 0, 32 + 32 * who, 32, null);
			break;
		case WALL:
			g.setColor(toumei);
			g.fillRect(x - 15, y - 15, 30, 30);
			break;
		case BASE:
			g.drawImage(pic, x - 15, y - 15, x + 15, y + 15, 32 * 2, 0, 32 + 32 * 2, 32, null);
		}
	}
}
