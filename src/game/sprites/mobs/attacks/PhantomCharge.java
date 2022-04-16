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

public class PhantomCharge extends Attack {

	private static final float VELOCITY = 10;
	private static final float DAMAGE = 2f;
	public static final int COOLDOWN = 125;
	
	private boolean hitPlayer = false;
	private int playerToFollow;
	
	public PhantomCharge(float x, float y, int playerToFollow) {
		super(x, y, AttackType.PHANTOMCHARGE, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN, "PhantomCharge");
		this.playerToFollow = playerToFollow;
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
		var playerY = Game.level.getPlayers().get(playerToFollow).getY();
		
		if (playerY > getY() + height * .5f) {
			setY(getY() + 1.75f);
		} else if (playerY < getY() + height * .5f) {
			setY(getY() - 1.75f);
		}
		
		setX(getX() - velocity);
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
			player.damage(15);
		}
		
	}
}
