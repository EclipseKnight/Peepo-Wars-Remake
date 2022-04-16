package game.sprites.hitbox;

/**
 * The abstract HitBox class that is used to create hitboxes.
 * @author jpaqu
 *
 */
public abstract class HitBox {
	
	private float x;
	private float y;
	private HitBoxType type;
	
	public static enum HitBoxType {
		CIRCLE, RECTANGLE;
	}
	
	public HitBox(float x, float y, HitBoxType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	/**
	 * Gets the type of hitbox.
	 * @return A {@linkplain HitBoxType} of this hitbox.
	 */
	public HitBoxType getHitBoxType() {
		return type;
	}
	
	
	/**
	 * Checks if the hitbox intersects with another hitbox.
	 * @param hb1 The hitbox to test against.
	 * @return true if the hitboxes intersect
	 */
	public abstract boolean intersects(HitBox hb1);
}
