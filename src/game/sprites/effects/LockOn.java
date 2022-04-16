package game.sprites.effects;

import java.awt.Graphics2D;

import game.handlers.SpriteHandler;
import game.sprites.mobs.Boss;

public class LockOn extends Effect {

	private Boss target;
	private int targetId;
	public LockOn(float x, float y, double scale, Boss target, int targetId) {
		super(x, y);
		this.target = target;
		this.targetId = targetId;
		initGFX(scale);
	}
	
	private void initGFX(double scale) {
		setImage(SpriteHandler.getIconImage("LockOn"));
		getImageDimensions(scale);
	}

	@Override
	public void render(Graphics2D g2d) {
		x = target.getX();
		y = target.getY();
		g2d.drawImage(getImage(), (int) x, (int) y, getWidth(), getHeight(), null);
	}
	
	public Boss getTarget() {
		return target;
	}
}
