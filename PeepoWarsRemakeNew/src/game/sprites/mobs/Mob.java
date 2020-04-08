package game.sprites.mobs;

import java.util.List;

import game.sprites.Animation;
import game.sprites.Sprite;
import game.sprites.SpriteSheet;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.players.Player;

/**
 * The abstract Mob class used to create bosses.
 * @author Eclipse
 *
 */
public abstract class Mob extends Sprite {

	protected String name;
	protected float health;
	protected float maxHealth;
	protected float speed;
	protected boolean showHealth;
	protected SpriteSheet spriteSheet;
	protected Animation animation;
	
	public Mob(float x, float y, float health, float maxHealth, float speed, String name, boolean showHealth) {
		super(x, y);
		this.health = health;
		this.maxHealth = maxHealth;
		this.speed = speed;
		this.name = name;
		this.showHealth = showHealth;
	}
	
	/**
	 * Gets the health of this mob.
	 * @return A float representing the health of the mob
	 */
	public float getHealth() {
		return health;
	}
	
	/**
	 * Gets the max health the mob can have
	 * @return A float representing the max health the mob can have
	 */
	public float getMaxHealth() {
		return maxHealth;
	}
	
	/**
	 * Gets the name of the mob.
	 * @return A String representing the name of the mob
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the sprite sheet for this mob.
	 * @return A {@linkplain SpriteSheet} representing the sprite sheet for this mob
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
	 * Determines if the health bar of the mob is shown.
	 * @return true if the health bar is displayed
	 */
	public boolean showHealth() {
		return showHealth;
	}
	
	/**
	 * Sets the name of this mob.
	 * @param name A String representing the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * Gets a list containing all of the {@linkplain HitBox}es for this mob.
	 * @return a list containing all of the hitboxes for this mob
	 */
	public abstract List<HitBox> getHitBox();
}
