package game.sprites.icons;

import game.sprites.Sprite;

public class MusicOn extends Sprite {

	public MusicOn(float x, float y) {
		super(x, y);
		initGFX();
	}
	
	private void initGFX() {
		loadImage("icons/bgmon.png");
		getImageDimensions(1);
	}
}
