package game.sprites.mobs.bosses;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.AttackCooldownHandler;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.attacks.IceFist;
import game.sprites.mobs.attacks.IceWave;

public class StrobeKing extends Boss {

	public static final float HEALTH = 1000;
	public static final float MAXHEALTH = 1000;
	public static final float SPEED = 3f;
	public static final String NAME = "Strobe King";
	private AttackCooldownHandler cooler;
	private boolean moveUp = true;// Variable that confirms what direction to move based on the coin flip.
	private int pixelsMoved = 0; // Tracks how many pixels the boss moves to then change directions.
//	private boolean isFiringIceBeam = false;

	public StrobeKing(float x, float y) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, true);
		this.cooler = new AttackCooldownHandler();
		initGFX();
	}
	
	private void initGFX() {
		loadImage("entities/mobs/strobeking.gif");
		getImageDimensions(1);
	}
	
	@Override
	public void tick() {
		cooler.reduceCooldown();
		move();
		attack();
	}

	private void attack() {
		var speed = health <= maxHealth/2 ? 2 : 1;
		if(cooler.getIceFistCooldown()/speed <= 0) {
			var iceFist = new IceFist(x - width, y + height / 2);
			attacks.add(iceFist);
			cooler.resetCooldown(iceFist);
		}
		if(cooler.getIceWaveCooldown()/speed <= 0) {
			var iceWave = new IceWave(x - width, y + height / 2);
			attacks.add(iceWave);
			cooler.resetCooldown(iceWave);
		}
//		if(cooler.getIceBeamCooldown() <= 0 && !isFiringIceBeam) {
//			var iceBeam = new IceBeam(x - width * 6, y + height / 2, this);
//			attacks.add(iceBeam);
//			cooler.resetCooldown(iceBeam);
//			isFiringIceBeam = true;
//			
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
