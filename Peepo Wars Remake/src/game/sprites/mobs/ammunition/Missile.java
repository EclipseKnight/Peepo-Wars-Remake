package game.sprites.mobs.ammunition;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;

public class Missile extends Ammunition {

	private static final float VELOCITY = 1;
	private static final float DAMAGE = 65;
	public static final int COOLDOWN = 60;
	
	private float acceleration = 0;

	public Missile(float x, float y) {
		super(x, y, AmmoType.MISSILE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		initGFX();
	}
	
	private void initGFX() {
		loadImage("ammunition/missile.png");
		getImageDimensions(2);
	}
	
	@Override
	public void move() {
		if(acceleration < 10) {
			acceleration += .20f;
		}
		
		x += VELOCITY + acceleration;

		if (x > Game.GAME_MAX_WIDTH) {
			visible = false;
		}

	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox(x, y, width * Game.GAME_SCALE, height * Game.GAME_SCALE));
		return hitboxes;
	}

	@Override
	public void damage(Boss boss, float damage) {
		boss.damage(damage);
		setVisible(false);
	}

	@Override
	public void damage(Mob mob, float damage) {
		mob.damage(damage);
		setVisible(false);
	}

}
