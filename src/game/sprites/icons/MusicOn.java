package game.sprites.icons;

import game.handlers.SpriteHandler;
import game.sprites.Sprite;

public class MusicOn extends Sprite {

	public MusicOn(float x, float y) {
		super(x, y);
		initGFX();
	}
	
	private void initGFX() {
		setImage(SpriteHandler.getIconImage("MusicOn"));
		getImageDimensions(3);
	}
}
