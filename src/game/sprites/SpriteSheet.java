package game.sprites;

import java.awt.image.BufferedImage;
/**
 * A sprite sheet created from an image. The sheets must have 0 padding.
 * @author Eclipse
 *
 */
public class SpriteSheet {

	private BufferedImage spriteSheet;
	private int numOfSprites;
	private int sheetWidth;
	private int sheetHeight;
	private int sheetCols;
	private int sheetRows;
	private int spriteWidth;
	private int spriteHeight;
	
	/**
	 * The SpriteSheet constructor.
	 * @param spriteSheet A BufferedImage representing the image used for the sprite sheet
	 * @param numOfSprites An int representing how many sprites are in the sheet
	 * @param sheetCols An int representing how many columns are in the sheet
	 * @param sheetRows An int representing how many rows are in the sheet
	 * @param spriteWidth An int representing the width of a single frame
	 * @param spriteHeight An int representing the height of a single frame 
	 */
	public SpriteSheet(BufferedImage spriteSheet, int numOfSprites, int sheetCols, int sheetRows, int spriteWidth, int spriteHeight) {
		this.spriteSheet = spriteSheet;
		this.numOfSprites = numOfSprites;
		this.sheetWidth = spriteSheet.getWidth(null);
		this.sheetHeight = spriteSheet.getHeight(null);
		this.sheetCols = sheetCols;
		this.sheetRows = sheetRows;
		this.spriteWidth = sheetWidth / sheetCols;
		this.spriteHeight = sheetHeight / sheetRows;
//		System.out.println(this.spriteHeight + " " + spriteHeight);
//		System.out.println(this.spriteWidth + " " + spriteWidth);
		//TODO Calculate width and height of each frame based off rows and cols. Assume there is no padding, else add variable to account for it.
	}
	
	/**
	 * Gets a sprite based on the rows and columns of the sheet.
	 * @param gridX the row
	 * @param gridY the column
	 * @return A BufferedImage representing the sprite
	 */
	public BufferedImage getSprite(int gridX, int gridY) {
		if (spriteSheet == null) {
			return null;
		}
		
		return spriteSheet.getSubimage(gridX * spriteWidth, gridY * spriteHeight, spriteWidth, spriteHeight);
	}
	
	/**
	 * Gets a sprite based on what number it is in the sheet
	 * @param num
	 * @return
	 */
	public BufferedImage getSprite(int num) {
		
		int count = 0;
		for (int i = 0; i < sheetRows; i++) {
			for (int j = 0; j < sheetCols; j++) {
				if (count == num-1) {
					return spriteSheet.getSubimage(j * spriteWidth, i * spriteHeight, spriteWidth, spriteHeight);
				}
				count++;
			}
		}
		return spriteSheet;
	}
	
	/**
	 * Gets an array containing all of the sprites in the sheet.
	 * @return an array containing all of the sprites in the sheet
	 */
	public BufferedImage[] getSprites() {
		BufferedImage[] sprites = new BufferedImage[numOfSprites];
		for (int i = 0; i < numOfSprites; i++) {
			sprites[i] = getSprite(i+1);
			
		}
		return sprites;
	}
}
