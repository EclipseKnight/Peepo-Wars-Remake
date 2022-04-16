package game.sprites.mobs.attacks;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.players.Player;

public class PhantomTriCharge extends Attack {

	private static final float VELOCITY = 10;
	private static final float DAMAGE = 1f;
	public static final int COOLDOWN = 350;
	
	private boolean hitPlayer = false;
	
	public PhantomTriCharge(float x, float y) {
		super(x, y, AttackType.PHANTOMTRICHARGE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN, "PhantomTriCharge");
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAttackAnimation("PhantomCharge");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite());
		getImageDimensions(.75);
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
		hitboxes.add(new CircleHitBox(x + 40, y + height * .35f, width * .1f));
		hitboxes.add(new CircleHitBox(x + 10, y + height * .5f, width * .10f));
		hitboxes.add(new RectangleHitBox(x + 40, y + height *.45f, width * .4f, height * .15f));
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		if (hitPlayer) {
			player.damage(damage);
		} else {
			hitPlayer = true;
			player.damage(10);
		}
		
	}
}
