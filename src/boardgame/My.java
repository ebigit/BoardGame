package boardgame;

import java.awt.Font;
import java.util.Random;

import gaga.Gaga;
import gaga.Gaga.GKeyboard;

public interface My {
	Random RAND = new Random();
	Font FONT = new Font(null, Font.PLAIN, 40);

	Gaga GAGA = new Gaga.Builder("ボードゲーム").setSize(1282, 642).build();

	int W = GAGA.system().width();
	int H = GAGA.system().height();
	int MX = GAGA.system().midX();
	int MY = GAGA.system().midY();

	String TITLE = GAGA.system().title();
	GKeyboard KEY = GAGA.keyboard();

	public interface SceneSet {
		SceneTitle TITLE = new SceneTitle();
		ScenePlay PLAY = new ScenePlay();
		SceneResult RESULT = new SceneResult();
	}

	public interface DiceSet {
		Dice dice = new Dice();
	}

	public interface StageSet {
		Stage stage = new Stage(177, 177);
	}

	int sx = StageSet.stage.X();
	int sy = StageSet.stage.Y();

}