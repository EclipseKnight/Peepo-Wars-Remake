package game.sprites.mobs.ammunition;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;

public class Laser extends Ammunition {

	private static final float VELOCITY = 7;
	private static final float DAMAGE = 25;
	public static final int COOLDOWN = 15;
	
	public Laser(float x, float y) {
		super(x, y, AmmoType.LASER, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAmmunitionAnimation("Laser");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite(animationState.getCurrentFrame()));
		getImageDimensions(.025);
	}
	
	@Override
	public void tick() {
		move();
		animate();
	}

	@Override
	public void animate() {
		animationState.update();
		setImage(animation.getSprite(animationState.getCurrentFrame()));
	}
	
	@Override
	public void move() {
		x += VELOCITY;
		
		if (x > Game.GAME_MAX_WIDTH) {
			visible = false;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox(x + width * .5f, y + height * .25f , width * .5f * Game.GAME_SCALE, height * .5f * Game.GAME_SCALE));
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

	@Override
	public void dispose() {
		
	}

	

	
}
