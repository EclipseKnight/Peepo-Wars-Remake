package game.sprites.effects;

import java.awt.Graphics2D;

import game.sprites.Animation;
import game.sprites.AnimationState;
import game.sprites.Sprite;

public abstract class Effect extends Sprite {

	protected Animation animation;
	protected AnimationState animationState;
	
	public Effect(float x, float y) {
		super(x, y);
	}
	
	public abstract void render(Graphics2D g2d);

}
