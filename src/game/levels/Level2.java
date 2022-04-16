package game.levels;

import java.awt.Graphics2D;

import game.Game;
import game.sprites.mobs.bosses.StrobeKing;
import game.sprites.mobs.players.Player;

public class Level2 extends Level {

	private static final int LEVELNUMBER = 2;
	
	public Level2(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {
		
	}
	
	private void initBosses() {
		getBosses().add(new StrobeKing(Game.GAME_MAX_WIDTH - 150, Game.GAME_MAX_HEIGHT/3, 0));
		getBosses().add(new StrobeKing(Game.GAME_MAX_WIDTH - 150, Game.GAME_MAX_HEIGHT/3, 1));
	}
	@Override
	public void tick() {
		
	}

	@Override
	public void restartLevel() {
		getMobs().clear();
		getBosses().clear();
		initMobs();
		initBosses();
		
		for (Player p : getPlayers()) {
			p.setHealth(p.getMaxHealth());
			p.reset();
		}
		
	}

	@Override
	public void clearLevel() {
		getMobs().clear();
		getBosses().clear();
		getPlayers().clear();
		getEffects().clear();
		
	}

	@Override
	public void render(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
