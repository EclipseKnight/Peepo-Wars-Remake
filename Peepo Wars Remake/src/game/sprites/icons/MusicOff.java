package game.sprites.icons;

import game.sprites.Sprite;

public class MusicOff extends Sprite {

	public MusicOff(float x, float y) {
		super(x, y);
		initGFX();
	}
	
	private void initGFX() {
		loadImage("icons/soundoff.png");
		getImageDimensions(3);
	}
}
