package game.sprites.icons;

import game.handlers.SpriteHandler;
import game.sprites.Sprite;

public class MusicOff extends Sprite {

	public MusicOff(float x, float y) {
		super(x, y);
		initGFX();
	}
	
	private void initGFX() {
		setImage(SpriteHandler.getIconImage("MusicOff"));
		getImageDimensions(3);
	}
}
