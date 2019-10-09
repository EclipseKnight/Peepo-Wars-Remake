package game.sprites.hitbox;

import java.awt.Rectangle;

public class RectangleHitBox extends HitBox {

	private float width;
	private float height;
	
	public RectangleHitBox(float x, float y, float width, float height) {
		super(x, y, HitBoxType.RECTANGLE);
		this.width = width;
		this.height = height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
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
//			return intersectRect((RectangleHitBox) hb);
//		}
//		
//		default -> throw new IllegalArgumentException("Unexpected value: " + type);
//		}
	}

//	private boolean intersectRect(RectangleHitBox hb) {
//		var l1 = new Point((int) this.getX(), (int) this.getY()); //top left of rectangle 1.
//		var r1 = new Point((int) (this.getX() + this.getWidth()), (int) (this.getY() + this.getHeight())); //bottom right of rectangle 1.
//		var l2 = new Point((int) hb.getX(), (int) hb.getY()); //top left of rectangle 2.
//		var r2 = new Point((int) (hb.getX() + hb.getWidth()), (int) (hb.getY() + hb.getHeight())); //bottom right of rectangle 2.
//		//If one rectangle is on left side of other.
//		if(l1.x >= r2.x || l2.x >= r1.x) {
//			System.out.println("1");
//			return false;
//		}
//		//If one rectangle is above other
//		if(l1.y <= r2.y || l2.y <= r1.y) {
//			System.out.println("2");
//			return false;
//		}
//		return true;
//	}

	private boolean intersectRect(RectangleHitBox hb) {
		var rect1 = new Rectangle((int) this.getX(), (int) this.getY(), (int) this.getWidth(), (int) this.getHeight());
		var rect2 = new Rectangle((int) hb.getX(), (int) hb.getY(), (int) hb.getWidth(), (int) hb.getHeight());
		
		return rect1.intersects(rect2);
	}
	private boolean intersectCircle(CircleHitBox hb) {
		
//		float testX = this.getX();
//		float testY = this.getY();
//		
//		if(hb.getCenterX() < this.getX())//test left edge
//			testX = this.getX();
//		else if(hb.getCenterX() > hb.getX() + this.getWidth())//test right edge
//			testX = this.getX() + this.getWidth();
//		if(hb.getCenterY() < this.getY())//top edge
//			testY = this.getY();
//		else if(hb.getCenterY() > this.getY() + this.getHeight())//bottom edge
//			testY = this.getY() + this.getHeight();
//			
//		float distX = hb.getCenterX() - testX;
//		float distY = hb.getCenterY() - testY;
//		float distance = (float) Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2));
//		
//		if(distance <= hb.getRadius()) {
//			return true;
//		}
//		return false;
		
		var circleX = hb.getCenterX(); //Center x of circle
		var circleY = hb.getCenterY(); //Center y of circle
		var nearestX = Math.max(this.getX(), Math.min(circleX, this.getX() + this.getWidth()));
		var nearestY = Math.max(this.getY(), Math.min(circleY, this.getY() + this.getHeight()));
		var deltaX = circleX - nearestX;//distance between the two points
		var deltaY = circleY - nearestY;// distance between the two points
		
		return (Math.pow(deltaX, 2) + Math.pow(deltaY, 2)) < Math.pow(hb.getRadius(), 2);
	}


}
