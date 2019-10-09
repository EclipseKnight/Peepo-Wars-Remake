package game.sprites;

import java.awt.Image;

import game.handlers.CrashHandler;
import resources.ResourceLoader;

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
	
	protected void loadImage(String imageName) {
		if(ResourceLoader.loadImage(imageName) != null) {
			image = ResourceLoader.loadImage(imageName);
		} else {
			CrashHandler.throwError(imageName + " not found");
		}
	}
	
	protected Image setImage(String imageName) {
		Image i = null;
		i = ResourceLoader.loadImage(imageName);
		return i;
	}
	
	protected void setImage(Image image) {
		this.image = image;
	}
	
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
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
}
