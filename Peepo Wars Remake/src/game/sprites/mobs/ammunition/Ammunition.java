package game.sprites.mobs.ammunition;

import java.util.List;

import game.sprites.Sprite;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;

public abstract class Ammunition extends Sprite {

	protected AmmoType ammoType;
	protected HitBoxType hitboxType;
	protected float velocity;
	protected float damage;
	protected int cooldown;
	
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
	
	public abstract void move();

	public abstract List<HitBox> getHitBox();
	
	public abstract void damage(Boss boss, float damage);
	
	public abstract void damage(Mob mob, float damage);
}
