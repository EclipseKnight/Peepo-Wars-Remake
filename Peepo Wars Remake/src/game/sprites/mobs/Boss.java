package game.sprites.mobs;

import java.util.ArrayList;
import java.util.List;

import game.sprites.Sprite;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.attacks.Attack;

public abstract class Boss extends Sprite {

	protected String name;
	protected float health;
	protected float maxHealth;
	protected float speed;
	protected boolean isBoss;
	protected List<Attack> attacks;
	
	public Boss(float x, float y, float health, float maxHealth, float speed, String name, boolean isBoss) {
		super(x, y);
		this.health = health;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.name = name;
		this.isBoss = isBoss;
		this.attacks = new ArrayList<>();
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
	
	public List<Attack> getAttacks() {
		return attacks;
	}
	public abstract void tick();
	
	public abstract void damage(float damage);
	
	public abstract List<HitBox> getHitBox();
}
