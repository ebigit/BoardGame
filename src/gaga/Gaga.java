package gaga;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/********************************************************************
 * ゲーム全体を管理するクラスです。すべての基盤となるクラスです。<br>
 * キーボード機能や画像機能など、ゲームシステム全般に関わる機能を提供します。<br>
 * 直接 new することはできませんので、ビルダー経由で new してください。<br>
 ********************************************************************/
public final class Gaga {

	static final Font FONT = new Font(null, Font.PLAIN, 20);
	static final String VERSION = "1.01";

	/********************************************************************
	 * ゲームを生成するためのビルダーです。<br>
	 * ゲームのタイトル、画面のサイズ、フレームレート、画像フォルダへのパスなどを設定したのちに、ゲームを生成することができます。<br>
	 * タイトルは必須ですが、その他は省略可能です。省略した場合はデフォルト値が使用されます。<br>
	 ********************************************************************/
	public static final class Builder {

		private String title;
		private int width = 800;
		private int height = 500;
		private int fps = 30;
		private String imagePath = "img";

		/**
		 * ビルダーを起動します。
		 *
		 * @param title
		 *            ゲームのタイトル
		 */
		public Builder(String title) {
			this.title = title;
		}

		/**
		 * 画面の大きさを設定します。
		 *
		 * @param width
		 *            画面の横幅（ピクセル）
		 * @param height
		 *            画面の縦幅（ピクセル）
		 * @return ビルダー
		 */
		public Builder setSize(int width, int height) {
			this.width = width;
			this.height = height;
			return this;
		}

		/**
		 * フレームレートを設定します。
		 *
		 * @param fps
		 *            フレームレート（1秒間に何回更新するか）
		 * @return ビルダー
		 */
		public Builder setFps(int fps) {
			this.fps = fps;
			return this;
		}

		/**
		 * 画像フォルダのパスを設定します。
		 *
		 * @param imagePath
		 *            画像フォルダのパス
		 * @return ビルダー
		 */
		public Builder setImagePath(String imagePath) {
			this.imagePath = imagePath;
			return this;
		}

		/**
		 * ゲームを生成します。
		 *
		 * @return ゲーム
		 */
		public Gaga build() {
			return new Gaga(title, width, height, fps, imagePath);
		}
	}

	private GFrame frame;
	private Graphics graphics;
	private GKeyboard keyboard;
	private GSystem system;
	private GImageMap imageMap;

	private Gaga(String title, int width, int height, int fps, String imagePath) {
		this.frame = new GFrame(title, width, height, fps);
		this.keyboard = new GKeyboard();
		this.system = new GSystem(title, width, height, fps);
		this.imageMap = new GImageMap(imagePath);
		this.frame.setVisible(true);
	}

	/**
	 * ゲームを開始します。ゲームは指定されたシーンが起点となります。<br>
	 * 引数に null が渡されると、デフォルトのシーンが使用されます。<br>
	 *
	 * @param scene
	 *            最初のシーン
	 */
	public void start(GScene scene) {
		if (scene == null) {
			frame.scene = new SampleScene();
		} else {
			frame.scene = scene;
		}
		frame.setVisible(true);
		new Thread(frame).start();
	}

	/**
	 * システム情報を返します。
	 *
	 * @return システム情報
	 */
	public GSystem system() {
		return system;
	}

	/**
	 * キーボードを返します。
	 *
	 * @return キーボード
	 */
	public GKeyboard keyboard() {
		return keyboard;
	}

	/**
	 * 画像を返します。画像はビルド時に指定された画像フォルダから探します。<br>
	 * 画像が見つからなかった場合はエラーになります。<br>
	 * 一度読み込んだ画像はキャッシュされるため、２度目以降の読み出し時に利用されます。<br>
	 *
	 * @param name
	 *            画像ファイル名
	 * @return 画像
	 */
	public GImage getImage(String name) {
		return imageMap.get(name);
	}

	/**
	 * テキスト画像を返します。ここから編集を加えたり、画像に変換したりできます。<br>
	 *
	 * @param color
	 *            メインカラー
	 * @param font
	 *            フォント
	 * @return テキスト画像
	 */
	public GTextImage getTextImage(Color color, Font font) {
		return new GTextImage(this, color, font);
	}

	Graphics g() {
		if (graphics == null) {
			return frame.getGraphics();
		}
		return graphics;
	}

	/********************************************************************
	 * GFrame
	 ********************************************************************/
	private class GFrame extends JFrame implements Runnable {

		private int width, height, fps;
		private GKeyboardListener keyboardListener;
		private Image screen;
		private GScene scene;
		private boolean isPause;

		private GFrame(String title, int width, int height, int fps) {
			setTitle(title);
			this.width = width;
			this.height = height;
			this.fps = fps;
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			getContentPane().setPreferredSize(new Dimension(width, height));
			pack();
			setLocationRelativeTo(null);
			JPanel panel = new JPanel() {
				@Override
				public void paint(Graphics g) {
					g.drawImage(screen, 0, 0, this);
				}

				@Override
				public void update(Graphics g) {
					paint(g);
				}
			};
			panel.addKeyListener(keyboardListener = new GKeyboardListener());
			panel.setFocusable(true);
			add(panel);
			screen = createImage(width, height);
		}

		@Override
		public void run() {
			Graphics g = screen.getGraphics();
			Gaga.this.graphics = g;
			int tick = 0;
			int tickPause = 0;
			while (true) {
				long processTime = System.currentTimeMillis();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, width, height);
				scene.draw(tick, g);
				g.drawImage(screen, 0, 0, this);

				if (isPause && tickPause % 60 < 20) {
					g.setFont(FONT);
					g.setColor(Color.BLUE);
					g.drawString("Gaga ver." + VERSION , 21, 21);
					g.setColor(Color.WHITE);
					g.drawString("Gaga ver." + VERSION , 20, 20);
				}

				repaint();
				if (isPause) {
					tickPause++;
				} else {
					GScene newScene = scene.update(tick);
					if (scene == newScene) {
						tick++;
					} else {
						tick = 0;
					}
					scene = newScene;
				}

				if (Gaga.this.keyboard.esc().isPressClear()) {
					isPause = !isPause;
					tickPause = 0;
				}

				processTime = System.currentTimeMillis() - processTime;
				if (1000 / fps - processTime > 0) {
					try {
						Thread.sleep(1000 / fps - processTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	/********************************************************************
	 * GKeyboardListener
	 ********************************************************************/
	private class GKeyboardListener implements KeyListener {

		private boolean[] keys = new boolean[KeyEvent.KEY_LAST];
		private GKey any, horizontal, vertical, space, enter, esc, c, x, z;

		private GKeyboardListener() {
			any = new GKey() {
				@Override
				public void clear() {
					for (int i = 0; i < keys.length; i++) {
						if (i != KeyEvent.VK_ESCAPE) {
							keys[i] = false;
						}
					}
				}

				@Override
				public boolean isPress() {
					for (int i = 0; i < keys.length; i++) {
						if (i != KeyEvent.VK_ESCAPE) {
							if (keys[i]) {
								return true;
							}
						}
					}
					return false;
				}

				@Override
				public int value() {
					return isPress() ? 1 : 0;
				}
			};

			horizontal = new GKey() {
				@Override
				public void clear() {
					keys[KeyEvent.VK_RIGHT] = false;
					keys[KeyEvent.VK_LEFT] = false;
				}

				@Override
				public boolean isPress() {
					if (keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_LEFT]) {
						if (keys[KeyEvent.VK_RIGHT] != keys[KeyEvent.VK_LEFT]) {
							return true;
						}
					}
					return false;
				}

				@Override
				public int value() {
					if (keys[KeyEvent.VK_RIGHT] == keys[KeyEvent.VK_LEFT]) {
						return 0;
					}
					return keys[KeyEvent.VK_RIGHT] ? 1 : -1;
				}
			};

			vertical = new GKey() {
				@Override
				public void clear() {
					keys[KeyEvent.VK_UP] = false;
					keys[KeyEvent.VK_DOWN] = false;
				}

				@Override
				public boolean isPress() {
					if (keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_DOWN]) {
						if (keys[KeyEvent.VK_UP] != keys[KeyEvent.VK_DOWN]) {
							return true;
						}
					}
					return false;
				}

				@Override
				public int value() {
					if (keys[KeyEvent.VK_UP] == keys[KeyEvent.VK_DOWN]) {
						return 0;
					}
					return keys[KeyEvent.VK_DOWN] ? 1 : -1;
				}
			};

			space = createKey(KeyEvent.VK_SPACE);
			enter = createKey(KeyEvent.VK_ENTER);
			esc = createKey(KeyEvent.VK_ESCAPE);
			c = createKey(KeyEvent.VK_C);
			x = createKey(KeyEvent.VK_X);
			z = createKey(KeyEvent.VK_Z);
		}

		private void clear() {
			for (int i = 0; i < keys.length; i++) {
				keys[i] = false;
			}
		}

		private GKey createKey(int... keyCode) {
			return new GKey() {
				private List<Integer> list = new ArrayList<Integer>() {
					{
						for (int keyCode : keyCode) {
							add(keyCode);
						}
					}
				};

				@Override
				public void clear() {
					for (int keyCode : list) {
						keys[keyCode] = false;
					}
				}

				@Override
				public boolean isPress() {
					for (int keyCode : list) {
						if (keys[keyCode]) {
							return true;
						}
					}
					return false;
				}

				@Override
				public int value() {
					return isPress() ? 1 : 0;
				}
			};
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() < keys.length) {
				keys[e.getKeyCode()] = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() < keys.length) {
				keys[e.getKeyCode()] = false;
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	/********************************************************************
	 * キーボードを管理するクラスです。(※注意事項があります)<br>
	 * キーの入力状態を返したり、キーの入力状態を変更することができます。<br>
	 * よく使われるキーについては標準で用意されています。<br>
	 * キーコードを指定することで、任意のキーを管理することができます。<br>
	 * 複数のキーコードを指定することで、複数のキーにマップされたキーを管理することができます。<br>
	 * ただし、エスケープキーについては、すでにポーズ機能として割り当ててあるため、使用しないでください。<br>
	 * <br>
	 * [注意]<br>
	 * スペースキー押下と矢印キー[←][↑]同時押しを併用すると、矢印キーの判定がおかしくなる場合があります。原因は不明です。<br>
	 ********************************************************************/
	public final class GKeyboard {

		private GKeyboard() {
		}

		/**
		 * すべてのキーの押下状態を初期化します。<br>
		 */
		public void clear() {
			Gaga.this.frame.keyboardListener.clear();
		}

		/**
		 * ANYキーを返します。ANYキーは全てのキーが登録されたキーです。ただしエスケープキーは除外します。<br>
		 *
		 * @return キー
		 */
		public GKey any() {
			return Gaga.this.frame.keyboardListener.any;
		}

		/**
		 * 水平キーを返します。水平キーは[←][→]の入力判定にカスタムされたキーです。<br>
		 *
		 * @return キー
		 */
		public GKey horizontal() {
			return Gaga.this.frame.keyboardListener.horizontal;
		}

		/**
		 * 垂直キーを返します。垂直キーは[↑][↓]の入力判定にカスタムされたキーです。<br>
		 *
		 * @return キー
		 */
		public GKey vertical() {
			return Gaga.this.frame.keyboardListener.vertical;
		}

		/**
		 * スペースキーを返します。(※注意事項があります)<br>
		 * <br>
		 * [注意] <br>
		 * スペースキー押下と矢印キー[←][↑]同時押しを併用すると、矢印キーの判定がおかしくなる場合があります。原因は不明です。<br>
		 * STGゲームのショットキーなどには割り当てないほうがいいです。<br>
		 *
		 * @return キー
		 */
		public GKey space() {
			return Gaga.this.frame.keyboardListener.space;
		}

		/**
		 * エンターキーを返します。<br>
		 *
		 * @return キー
		 */
		public GKey enter() {
			return Gaga.this.frame.keyboardListener.enter;
		}

		/**
		 * [C]キーを返します。<br>
		 *
		 * @return キー
		 */
		public GKey shift() {
			return Gaga.this.frame.keyboardListener.c;
		}

		/**
		 * [X]キーを返します。<br>
		 *
		 * @return キー
		 */
		public GKey X() {
			return Gaga.this.frame.keyboardListener.x;
		}

		/**
		 * [Z]キーを返します。<br>
		 *
		 * @return キー
		 */
		public GKey Z() {
			return Gaga.this.frame.keyboardListener.z;
		}

		/**
		 * キーを生成して返します。複数のキーを登録できます。<br>
		 *
		 * @param keyCode
		 *            キーコード
		 * @return キー
		 */
		public GKey create(int... keyCode) {
			return frame.keyboardListener.createKey(keyCode);
		}

		// ESCキーはポーズボタンとして使用する
		GKey esc() {
			return Gaga.this.frame.keyboardListener.esc;
		}
	}

	/********************************************************************
	 * GImageMap
	 ********************************************************************/
	private class GImageMap {

		private String path;
		private Map<String, GImage> map = new TreeMap<>();

		private GImageMap(String path) {
			this.path = path;
		}

		private GImage get(String name) {
			if (!map.containsKey(name)) {
				URL url = getClass().getClassLoader().getResource(path + "/" + name);
				map.put(name, new GImage(new ImageIcon(url)));
			}
			return map.get(name);
		}
	}
}