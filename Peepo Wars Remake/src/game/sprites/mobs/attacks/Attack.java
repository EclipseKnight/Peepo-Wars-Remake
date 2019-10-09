package game.sprites.mobs.attacks;

import java.util.List;

import game.sprites.Sprite;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.mobs.Player;

public abstract class Attack extends Sprite {

	protected AttackType attackType;
	protected HitBoxType hitboxType;
	protected float velocity;
	protected float damage;
	protected int cooldown;
	
	public static enum AttackType {
		ICEFIST, ICEWAVE, ICEBEAM
	}
	
	public Attack(float x, float y, AttackType attackType, HitBoxType hitboxType, float velocity, float damage, int cooldown) {
		super(x, y);
		this.attackType = attackType;
		this.hitboxType = hitboxType;
		this.velocity = velocity;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	public AttackType getAttackType() {
		return attackType;
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
	
	public abstract void damage(Player player, float damage);
	
}
