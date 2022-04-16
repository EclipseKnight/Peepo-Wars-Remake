package game.sprites.hitbox;

public class CircleHitBox extends HitBox {

	private float radius;
	
	public CircleHitBox(float x, float y, float radius) {
		super(x, y, HitBoxType.CIRCLE);
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getCenterX() {
		return (getX() + radius);
	}
	
	public float getCenterY() {
		return (getY() + radius);
	}

	@Override
	public boolean intersects(HitBox hb) {
		var type = hb.getHitBoxType();
		
		switch(type) {
		case CIRCLE:
			return intersectCircle((CircleHitBox) hb);
		case RECTANGLE:
			return intersectRect((RectangleHitBox) hb);
		default: 
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
//		switch(type) {
//		
//		case CIRCLE -> {
//			return intersectCircle((CircleHitBox) hb);
//		}
//		
//		case RECTANGLE -> {
//			return intersectRect((RectangleHitBox ) hb);
//		}
//		default -> throw new IllegalArgumentException("Unexpected value: " + type);
//		}
	}
	
	private boolean intersectCircle(CircleHitBox hb) {
		var distSq = (float) (Math.pow((this.getCenterX()) - (hb.getCenterX()), 2) 
				+ Math.pow((this.getCenterY()) - (hb.getCenterY()), 2));
		var radSq = (float) Math.pow((this.getRadius() + hb.getRadius()), 2);
		if (distSq <= radSq) {
			return true;
		}
		return false;
	}
	
	private boolean intersectRect(RectangleHitBox hb) {
		
//		float testX = this.getX();
//		float testY = this.getY();
//		
//		if (this.getCenterX() < hb.getX())//test left edge
//			testX = hb.getX();
//		else if (this.getCenterX() > hb.getX() + hb.getWidth())//test right edge
//			testX = hb.getX() + hb.getWidth();
//		if (this.getCenterY() < hb.getY())//top edge
//			testY = hb.getY();
//		else if (this.getCenterY() > hb.getY() + hb.getHeight())//bottom edge
//			testY = hb.getY() + hb.getHeight();
//			
//		float distX = this.getCenterX() - testX;
//		float distY = this.getCenterY() - testY;
//		float distance = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
//		
//		if (distance <= this.getRadius()) {
//			return true;
//		}
//		return false;
		
		var circleX = getCenterX(); //Center x of circle
		var circleY = getCenterY(); //Center y of circle
		var nearestX = Math.max(hb.getX(), Math.min(circleX, hb.getX() + hb.getWidth()));
		var nearestY = Math.max(hb.getY(), Math.min(circleY, hb.getY() + hb.getHeight()));
		var deltaX = circleX - nearestX;
		var deltaY = circleY - nearestY;
		
		return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(this.getRadius(), 2);
		
	}
}
