package gaga;

/********************************************************************
 * キー入力状態を表現するためのインタフェースです。<br>
 * 主に、キーの押下状態を調べるために使用します。<br>
 * STGゲームでの自機のショットなど「押し続けている間の処理」をする場合には、isPress メソッドを使用します。<br>
 * アイテムの使用など「押した瞬間だけの処理」をする場合には、isPressClear メソッドを使用します。<br>
 * STGゲームでの矢印キーでの自機の移動などには value  メソッドが役に立ちます。<br>
 * パズルゲームでの矢印キーでのカーソルの移動などには valueClear  メソッドが役に立ちます。<br>
 ********************************************************************/
public interface GKey {

	/**
	 * キーの押下状態を初期化します。<br>
	 */
	void clear();

	/**
	 * キーの押下状態を返します。<br>
	 * 登録されたキーのうち１つ以上が押下された場合を押下と判定します。<br>
	 * 水平キーの場合は[←]か[→]のどちらか一方だけが押下された場合を押下と判定します。<br>
	 * 垂直キーの場合は[↑]か[↓]のどちらか一方だけが押下された場合を押下と判定します。<br>
	 * @return 押下状態
	 */
	boolean isPress();

	/**
	 * キーの押下状態を返します。直後、キーの押下状態は初期化されます。<br>
	 * 登録されたキーのうち１つ以上が押下された場合を押下と判定します。<br>
	 * 水平キーの場合は[←]か[→]のどちらか一方だけが押下された場合を押下と判定します。<br>
	 * 垂直キーの場合は[↑]か[↓]のどちらか一方だけが押下された場合を押下と判定します。<br>
	 * @return 押下状態
	 */
	default boolean isPressClear() {
		boolean ret = isPress();
		if (ret) {
			clear();
		}
		return ret;
	}

	/**
	 * キーの押下状態を値として返します。<br>
	 * 登録されたキーのうち、いずれかが押下状態なら1、そうでなければ0を返します。<br>
	 * 水平キーの場合は[←]なら-1、[→]なら1、いずれでもなければ0を返します。同時押しは0を返します。<br>
	 * 垂直キーの場合は[↑]なら-1、[↓]なら1、いずれでもなければ0を返します。同時押しは0を返します。<br>
	 * @return 値
	 */
	int value();

	/**
	 * キーの押下状態を値として返します。直後、キーの押下状態は初期化されます。<br>
	 * 登録されたキーのうち、いずれかが押下状態なら1、そうでなければ0を返します。<br>
	 * 水平キーの場合は[←]なら-1、[→]なら1、いずれでもなければ0を返します。同時押しは0を返します。<br>
	 * 垂直キーの場合は[↑]なら-1、[↓]なら1、いずれでもなければ0を返します。同時押しは0を返します。<br>
	 * @return 値
	 */
	default int valueClear() {
		int ret = value();
		if (ret != 0) {
			clear();
		}
		return ret;
	}
}