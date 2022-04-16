package game.sprites.mobs.ammunition;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.Sprite;
import game.sprites.effects.Effect;
import game.sprites.effects.LockOn;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;

public class Missile extends Ammunition {

	private static final float VELOCITY = 1;
	private static final float DAMAGE = 65;
	public static final int COOLDOWN = 45;
	
	private float acceleration = 0;
	private Boss target = null;
	private int targetId = -1;
	private LockOn lockOn;

	public Missile(float x, float y) {
		super(x, y, AmmoType.MISSILE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		initGFX();
		initTargeting();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAmmunitionAnimation("Missile");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite(animationState.getCurrentFrame()));
		getImageDimensions(.1);
	}
	
	private void lockOn() {
		if (target == null) {
			lockOn = null;
			targetId = -1;
//			dispose();
		} else if (lockOn == null) {
			lockOn = new LockOn(target.getHitBox().get(0).getX(), target.getHitBox().get(0).getY(), 3, target, target.getId());
			Game.level.addEffect(lockOn);
		} else if (lockOn != null) {
			lockOn.setX(target.getX());
			lockOn.setY(target.getY());
		}
		
	}
	
	private void initTargeting() {
		lockOn();
		if (target == null) {
			Boss b = null;
			float delta = Game.GAME_MAX_HEIGHT + 1;
			
			for (int i = 0; i < Game.level.getBosses().size(); i++) {
				b = Game.level.getBosses().get(i);
				if (Math.abs(y - b.getY()) < delta ) {
					delta = Math.abs(y - b.getY());
					target = b;
					targetId = target.getId();
				}
			}
		}
		
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
		initTargeting();
		if (acceleration < 10) {
			acceleration += .20f;
		}
		
		x += VELOCITY + acceleration;
		
		if (target != null && acceleration >= 10) {
			if (y < target.getY()) y += 1.5;
			
			if (y > target.getY()) y -= 1.5;
		}
		

		if (x > Game.GAME_MAX_WIDTH) {
			visible = false;
		}
	}

		
	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox(x + width * .5f, y, width * .5f * Game.GAME_SCALE, height * Game.GAME_SCALE));
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
		for (int i = 0; i < Game.level.getEffects().size(); i++) {
			Effect e = Game.level.getEffects().get(i);
			
			if (e instanceof LockOn) {
				Game.level.getEffects().remove(i);
				return;
			}
		}
	}

}
