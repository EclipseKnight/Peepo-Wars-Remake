package game.sprites.mobs.attacks;

import java.util.List;

import game.sprites.Animation;
import game.sprites.AnimationState;
import game.sprites.Sprite;
import game.sprites.SpriteSheet;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.mobs.players.Player;

public abstract class Attack extends Sprite {

	protected String name;
	protected AttackType attackType;
	protected HitBoxType hitboxType;
	protected float velocity;
	protected float damage;
	protected int cooldown;
	protected SpriteSheet spriteSheet;
	protected Animation animation;
	protected AnimationState animationState;
	
	public static enum AttackType {
		ICEFIST, ICEWAVE, ICEBEAM, PHANTOMCHARGE, PHANTOMTRICHARGE
	}
	
	public Attack(float x, float y, AttackType attackType, HitBoxType hitboxType, float velocity, float damage, int cooldown, String name) {
		super(x, y);
		this.attackType = attackType;
		this.hitboxType = hitboxType;
		this.velocity = velocity;
		this.damage = damage;
		this.cooldown = cooldown;
		this.name = name;
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
	
	public String getName() {
		return name;
	}
	
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	
	public Animation getAnimation() {
		return animation;
	}
	
	public abstract void animate();
	
	public abstract void tick();
	
	public abstract void move();

	public abstract List<HitBox> getHitBox();
	
	public abstract void damage(Player player, float damage);
	
}
