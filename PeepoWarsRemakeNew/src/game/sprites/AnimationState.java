package game.sprites;

/**
 * The state of the current animation. This should always be used to have multiple asynchronous animations of the same mob.
 * @author Eclipse
 *
 */
public class AnimationState {
	
	private Animation animation;
	private int frameCount;
	private int frameDelay;
	private int currentFrame;
	private int totalFrames;
	private int animationDirection;
	private boolean loop;
	private boolean stopped;
	private int loopCount = 0;
	
	/**
	 * An {@linkplain AnimationState} constructor.
	 * @param animation An {@linkplain Animation} representing the animation being used.
	 */
	public AnimationState(Animation animation) {
		this.animation = animation;
		this.frameCount = 0;
		this.currentFrame = 0;
		this.frameDelay = animation.getFrameDelay();
		this.animationDirection = animation.getAnimationDirection();
		this.totalFrames = animation.getTotalFrames();
		this.loop = animation.getisLoop();
		stopped = false;
	}
	
	/**
	 * Gets the current frame of the {@linkplain Animation}.
	 * @return An int representing the current frame of the {@linkplain Animation}.
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}
	
	/**
	 * Starts the {@linkplain Animation}
	 */
	public void start() {
		if (!stopped || animation.getFramesSize() == 0) {
			return;
		}

		stopped = false;
	}
	
	/**
	 * Stops the {@linkplain Animation} at its current frame.
	 */
	public void stop() {
		if(animation.getFramesSize() == 0) {
			return;
		}
		
		stopped = true;
	}
	
	/**
	 * Restarts the {@linkplain Animation} to the first frame. 
	 */
	public void restart() {
		if(animation.getFramesSize() == 0) {
			return;
		}
		
		stopped = false;
		currentFrame = 0;
	}
	
	/**
	 * Resets the {@linkplain Animation} to the first frame and the frame count.
	 */
	public void reset() {
		stopped = false;
		currentFrame = 0;
		frameCount = 0;
	}
	
	/**
	 * Moves to the next frame of the {@linkplain Animation}. This should never go out of bounds.
	 */
	public void update() {
		if(loopCount > 0 && !animation.getisLoop()) {
			stopped = true;
		}
		
		if(!stopped) {
			frameCount++;
			
			if(frameCount >= frameDelay) {
				frameCount = 0;
				
				if(currentFrame + animationDirection >= totalFrames) {
					currentFrame = 0;
					loopCount++;
					
				} else if(currentFrame + animationDirection < 0) {
					currentFrame = totalFrames;
					loopCount++;
					
				} else {
					currentFrame += animationDirection;
				}
				
			}
		}
	}
}
