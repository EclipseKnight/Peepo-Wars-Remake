package game.sprites;

import java.awt.Image;

import game.handlers.CrashHandler;
import resources.ResourceLoader;

/**
 * The root of all images in the game. 
 * @author Eclipse
 *
 */
public class Sprite {

	protected float x;
	protected float y;
	protected int width;
	protected int height;
	protected boolean visible; //Crucial for removing sprites from lists
	protected Image image;
	
	public Sprite(float x, float y) {
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	/**
	 * Loads an image from resources.
	 * @param imageName
	 */
	protected void loadImage(String imageName) {
		if(ResourceLoader.loadImage(imageName) != null) {
			image = ResourceLoader.loadImage(imageName);
		} else {
			CrashHandler.throwError(imageName + " not found");
		}
	}
	
	/**
	 * Set the image of the sprite.
	 * @param image
	 */
	protected void setImage(Image image) {
		this.image = image;
	}
	
	/**
	 * Set the dimensions of the sprite.
	 * @param scale
	 */
	protected void getImageDimensions(double scale) {
		width = (int) (image.getWidth(null) * scale);
		height = (int) (image.getHeight(null) * scale);
	}
	
	public Image getImage() {
		return image;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Gets the x position of the sprite.
	 * @param x A float representing the x position of the sprite.
	 */
	public void setX(float x) {
		this.x = x;
	}
	
	/**
	 * Gets the y position of the sprite.
	 * @param y A float representing the y position of the sprite.
	 */
	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Sets whether the sprite is visible or not. 
	 * @param visible true if the sprite is visible
	 */
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
