package game.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * An animation created from the frames of a sprite sheet. This should be used with {@linkplain AnimationState}, therefore the methods for this remain undocumented.
 * @author Eclipse
 *
 */
public class Animation {

	private int frameCount; // counts update ticks
	private int frameDelay; // update delay
	private int currentFrame; // current frame
	private int animationDirection; // the direction the animation is updating (forwards/backwards)
	private int totalFrames; // total frames in the animation

	private boolean stopped;
	private boolean loop;
	private List<Frame> frames = new ArrayList<>();

	/**
	 * 
	 * @param frames An array representing the frames of a SpriteSheet
	 * @param frameDelay An int representing the tick delay between changing frames.
	 * @param loop A boolean representing if the animation should loop or not.
	 */
	public Animation(BufferedImage[] frames, int frameDelay, boolean loop) {
		this.frameDelay = frameDelay;
		this.stopped = false;
		this.loop = loop;
		for (int i = 0; i < frames.length; i++) {
			addFrame(frames[i], frameDelay);
		}

		this.frameCount = 0;
		this.frameDelay = frameDelay;
		this.currentFrame = 0;
		this.animationDirection = 1;
		this.totalFrames = this.frames.size();
	}

	public BufferedImage getSprite() {
		
		return frames.get(currentFrame).getFrame();
	}
	
	public BufferedImage getSprite(int frame) {
		if (frame < frames.size()) {
			return frames.get(frame).getFrame();
		} 
		System.err.println("Invalid frame selected: " + frame);
		return null;
	}
	
	public boolean getisLoop() {
		return loop;
	}
	
	public int getFrameDelay() {
		return frameDelay;
	}
	
	public int getTotalFrames() {
		return totalFrames;
	}
	
	public int getFramesSize() {
		return frames.size();
	}
	public int getAnimationDirection() {
		return animationDirection;
	}

	private void addFrame(BufferedImage frame, int duration) {
		if (duration <= 0) {
			System.err.println("Invalid duration: " + duration);
			throw new RuntimeException("Invalid duration: " + duration);
		}

		frames.add(new Frame(frame, duration));
		currentFrame = 0;
	}
}
