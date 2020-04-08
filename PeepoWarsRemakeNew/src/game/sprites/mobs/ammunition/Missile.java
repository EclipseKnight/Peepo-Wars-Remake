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

public class Missile extends Ammunition {

	private static final float VELOCITY = 1;
	private static final float DAMAGE = 65;
	public static final int COOLDOWN = 45;
	
	private float acceleration = 0;
//	private int targetBoss;

	public Missile(float x, float y) {
		super(x, y, AmmoType.MISSILE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		initGFX();
//		initTargeting();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAmmunitionAnimation("Missile");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite(animationState.getCurrentFrame()));
		getImageDimensions(.1);
	}
	
/*
	private void initTargeting() {
		Boss boss =  null;
		for(int i = 0; i < Game.level.getBosses().size()-1; i++) {
			boss = Game.level.getBosses().get(i);
			
		}
	}
*/
	
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
		if(acceleration < 10) {
			acceleration += .20f;
		}
		
		x += VELOCITY + acceleration;
/*		
		var bossY = 0f;
		Boss boss = Game.level.getBosses().get(0);
		for(int i = 0; i < Game.level.getBosses().size()-1; i++) {		
			boss = Game.level.getBosses().get(i);
			if(boss.getY() - y < bossY - y)
				bossY = boss.getY();
		}
		
		
		
		
		
		if (x > Game.GAME_MAX_WIDTH) {
			visible = false;
		}
*/
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

}
