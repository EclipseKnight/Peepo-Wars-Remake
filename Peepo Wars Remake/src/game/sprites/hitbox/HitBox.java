package game.sprites.hitbox;

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
	
	public HitBoxType getHitBoxType() {
		return type;
	}
	
	
	//Checks if the two HitBoxes intersect.
	public abstract boolean intersects(HitBox hb1);
}
