package game.sprites.mobs.bosses;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.AttackCooldownHandler;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.attacks.IceFist;
import game.sprites.mobs.attacks.IceWave;
import game.sprites.mobs.players.Player;
/**
 * The first boss of Peepo Wars
 * @author Eclipse
 *
 */
public class StrobeKing extends Boss {

	public static final float HEALTH = 1000;
	public static final float MAXHEALTH = 1000;
	public static final float SPEED = 3f;
	public static final String NAME = "Strobe King";
	private AttackCooldownHandler cooler;
	private boolean moveUp = true;// Variable that confirms what direction to move based on the coin flip.
	private float pixelsMoved = 0; // Tracks how many pixels the boss moves to then change directions.
//	private boolean isFiringIceBeam = false;
	

	public StrobeKing(float x, float y) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, true);
		this.cooler = new AttackCooldownHandler();
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getBossAnimation("StrobeKing");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite());
		getImageDimensions(1);
	}
	
	@Override
	public void tick() {
		cooler.reduceCooldown();
		animate();
		move();
		attack();
	}
	
	@Override
	public void animate() {
		animationState.update();
		setImage(animation.getSprite(animationState.getCurrentFrame()));
	}

	private void attack() {
		var atkspeed = health <= maxHealth/2 ? 2 : 1;
		
		if(cooler.getIceFistCooldown()/atkspeed <= 0) {
			var iceFist = new IceFist(x - width, y + height / 2);
			attacks.add(iceFist);
			cooler.resetCooldown(iceFist);
		}
		if(cooler.getIceWaveCooldown()/atkspeed <= 0) {
			var iceWave = new IceWave(x - width, y + height / 2);
			attacks.add(iceWave);
			cooler.resetCooldown(iceWave);
		}
		
//		if(cooler.getIceBeamCooldown() <= 0 && !isFiringIceBeam) {
//			var iceBeam = new IceBeam(x - width * 6, y + height / 2, this);
//			//TODO animation reset for icebeam.
//			attacks.add(iceBeam);
//			cooler.resetCooldown(iceBeam);
//			isFiringIceBeam = true;
//		}
	}
	
	private void move() {
		pixelsMoved += speed;
		if (pixelsMoved > 150) {
			moveUp = flipCoin();
			pixelsMoved = 0;
		}

		if (y >= Game.GAME_MAX_BORDER - height) {
			moveUp = false;

		} else if (y < Game.GAME_MAX_BORDER - height && moveUp) {
			y += speed;
		}

		if (!moveUp && y >= 5) {
			y -= speed;
		} else if (y < 50) {
			moveUp = true;
		}

	}

	// flips a coin (50/50)
	public boolean flipCoin() {
		double x = Math.random();
		if (x >= .5)
			return true;
		else if (x < .5) {
			return false;
		}
		return false;
	}

	@Override
	public void damage(float damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
		}
	}
	
	@Override
	public void damage(Player player, float damage) {
		player.damage(damage);
	}

	public List<Attack> getAttacks() {
		return attacks;
	}
	
	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new CircleHitBox(x, y, Math.min(width, height)/2));
		return hitboxes;
	}

	

}
