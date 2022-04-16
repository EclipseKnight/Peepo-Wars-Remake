package game.sprites.mobs.attacks;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.players.Player;

public class IceWave extends Attack {

	private static final float VELOCITY = 5;
	private static final float DAMAGE = 15;
	public static final int COOLDOWN = 170;
	
	private boolean hasHitPlayer = false;
	
	public IceWave(float x, float y) {
		super(x, y, AttackType.ICEWAVE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN, "IceWave");
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAttackAnimation("IceWave");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite());
		getImageDimensions(1);
	}
	
	@Override
	public void tick() {
		animate();
		move();
		
	}
	
	@Override
	public void animate() {
		animationState.update();
		setImage(animation.getSprite(animationState.getCurrentFrame()));
	}

	@Override
	public void move() {
		x -= velocity;
		
		if (x < Game.GAME_MIN_WIDTH - width) {
			visible = false;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox((x + width * .38f), (y + height * .0f), (width * .2f) , (height * .08f)));
		hitboxes.add(new RectangleHitBox((x + width * .2f), (y + height * .088f), (width * .2f) , (height * .15f)));
		hitboxes.add(new RectangleHitBox((x + width * .1f), (y + height * .23f), (width * .4f) , (height * .5f)));
		hitboxes.add(new RectangleHitBox((x + width * .2f), (y + height * .73f), (width * .2f) , (height * .15f)));
		hitboxes.add(new RectangleHitBox((x + width * .38f), (y + height * .87f), (width * .2f) , (height * .08f)));
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		if (!hasHitPlayer) {
			player.damage(damage);
			hasHitPlayer = true;
		}
	}

	

}
