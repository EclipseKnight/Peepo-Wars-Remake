package game.handlers;

import game.Game;

// This is still a work in progress and has not yet been implemented.
public class TimerHandler {
	
	private long currentTime = System.currentTimeMillis();
	private long timeNow;
	private long deltaTime;
	private long time;
	
	private String timeStr = "";
	private Game game;
	
	public TimerHandler(Game game) {
		this.game = game;
	}
	
	public long getTime() {
		return time;
	}
	
}
