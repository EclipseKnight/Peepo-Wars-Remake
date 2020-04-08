package game.sprites.mobs.ammunition;

import java.util.List;

import game.sprites.Animation;
import game.sprites.AnimationState;
import game.sprites.Sprite;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;

/**
 * The abstract Ammunition class that is used for creating ammo types.
 * @author Eclipse
 *
 */
public abstract class Ammunition extends Sprite {

	protected AmmoType ammoType;
	protected HitBoxType hitboxType;
	protected float velocity;
	protected float damage;
	protected int cooldown;
	protected Animation animation;
	protected AnimationState animationState;
	
	
	public static enum AmmoType {
		LASER(1), MISSILE(2);
		
		private int num;
		
		private AmmoType(int num) {
			this.num = num;
		}
		
		public int getNum() {
			return num;
		}
	}
	
	public Ammunition(float x, float y, AmmoType ammoType, HitBoxType hitboxType, float velocity, float damage, int cooldown) {
		super(x, y);
		this.ammoType = ammoType;
		this.hitboxType = hitboxType;
		this.velocity = velocity;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	public AmmoType getAmmoType() {
		return ammoType;
	}
	
	public HitBoxType getHitBoxType() {
		return hitboxType;
	}
	
	public float getVelocity() {
		return velocity;
	}
	
	public float getDamage() {
		return damage;
	}
	
	public int getCooldown() {
		return cooldown;
	}
	
	/**
	 * Designated for updating any variable that keeps in time with the ticks per second of the game. 
	 * Example: movement, attack cooldowns, animations.
	 */
	public abstract void tick();
	
	/**
	 * Designated for updating the animation of the mob. Should be called by the {@linkplain #tick()} method.
	 */
	public abstract void animate();
	
	/**
	 * Designated for designing how the ammo moves.
	 */
	public abstract void move();

	/**
	 * Gets a list containing all of the {@linkplain HitBox}es for this ammo.
	 * @return a list containing all of the hitboxes for this ammo
	 */
	public abstract List<HitBox> getHitBox();
	
	/**
	 * Designated for how the ammo inflicts damage to a boss
	 * @param boss The {@linkplain Boss} object taking damage
	 * @param damage A float representing the amount of damage
	 */
	public abstract void damage(Boss boss, float damage);
	
	/**
	 * Designated for how the ammo inflicts damage to a mob
	 * @param mob The {@linkplain Mob} object taking damage
	 * @param damage A float representing the amount of damage
	 */
	public abstract void damage(Mob mob, float damage);
}
