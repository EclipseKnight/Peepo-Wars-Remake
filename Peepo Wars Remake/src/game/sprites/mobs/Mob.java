package game.sprites.mobs;

import java.util.List;

import game.sprites.Sprite;
import game.sprites.hitbox.HitBox;

public abstract class Mob extends Sprite {

	protected String name;
	protected float health;
	protected float maxHealth;
	protected float speed;
	protected boolean showHealth;
	
	public Mob(float x, float y, float health, float maxHealth, float speed, String name, boolean showHealth) {
		super(x, y);
		this.health = health;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.name = name;
		this.showHealth = showHealth;
	}
	
	public float getHealth() {
		return health;
	}
	
	public float getMaxHealth() {
		return maxHealth;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean showHealth() {
		return showHealth;
	}
	
	public abstract void tick();
	
	public abstract void damage(float damage);
	
	public abstract List<HitBox> getHitBox();
}
