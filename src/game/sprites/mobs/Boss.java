package game.sprites.mobs;

import java.util.ArrayList;
import java.util.List;

import game.sprites.Animation;
import game.sprites.AnimationState;
import game.sprites.Sprite;
import game.sprites.SpriteSheet;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.players.Player;

/**
 * The abstract Boss class used to create bosses.
 * @author Eclipse
 *
 */
public abstract class Boss extends Sprite {

	protected String name;
	protected int id;
	protected float health;
	protected float maxHealth;
	protected float speed;
	protected boolean isBoss;
	protected List<Attack> attacks;
	protected SpriteSheet spriteSheet;
	protected Animation animation;
	protected AnimationState animationState;
	
	public Boss(float x, float y, float health, float maxHealth, float speed, String name, int id, boolean isBoss) {
		super(x, y);
		this.health = health;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.name = name;
		this.id = id;
		this.isBoss = isBoss;
		this.attacks = new ArrayList<>();
	}
	
	/**
	 * Gets the health of this boss.
	 * @return A float representing the health of the boss
	 */
	public float getHealth() {
		return health;
	}
	
	/**
	 * Gets the max health the boss can have
	 * @return A float representing the max health the boss can have
	 */
	public float getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Gets the name of the boss.
	 * @return A String representing the name of the boss
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the id of the boss.
	 * @return A int representing the id of the boss
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets a list of the attacks currently being used by this boss.
	 * @return A List of the {@linkplain Attack}s currently being used by this boss.
	 */
	public List<Attack> getAttacks() {
		return attacks;
	}
	
	/**
	 * Gets the sprite sheet for this boss.
	 * @return A {@linkplain SpriteSheet} representing the sprite sheet for this boss
	 */
	public SpriteSheet getSpriteSheet() {
		return spriteSheet;
	}
	
	/**
	 * Gets the animation of this mob.
	 * @return An {@linkplain Animation} representing the animation of the mob
	 */
	public Animation getAnimation() {
		return animation;
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
	 * Designated for calculating how the mob takes damage.
	 * @param damage A float representing the amount of damage
	 */
	public abstract void damage(float damage);
	
	/**
	 * Designated for how the mob inflicts damage to a player
	 * @param player The {@linkplain Player} object taking damage
	 * @param damage A float representing the amount of damage
	 */
	public abstract void damage(Player player, float damage);
	
	/**
	 * Gets a list containing all of the {@linkplain HitBox}es for this boss.
	 * @return a list containing all of the hitboxes for this boss
	 */
	public abstract List<HitBox> getHitBox();
}
