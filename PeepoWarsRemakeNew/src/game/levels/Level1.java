package game.levels;

import java.awt.Graphics2D;

import game.Game;
import game.sprites.mobs.bosses.StrobeKing;
import game.sprites.mobs.players.Player;

public class Level1 extends Level {

	private static final int LEVELNUMBER = 1;
	
	public Level1(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {
		
	}
	
	private void initBosses() {
		bosses.add(new StrobeKing(Game.GAME_MAX_WIDTH - 150, Game.GAME_MAX_HEIGHT/3 ));
	}
	
	@Override
	public void tick() {
		
	}

	@Override
	public void restartLevel() {
		mobs.clear();
		bosses.clear();
		initMobs();
		initBosses();
		
		for(Player p : players) {
			p.setHealth(p.getMaxHealth());
			p.reset();
		}
	}

	@Override
	public void clearLevel() {
		mobs.clear();
		bosses.clear();
		players.clear();
		
	}

	@Override
	public void render(Graphics2D g2d) {
		
	}

}
