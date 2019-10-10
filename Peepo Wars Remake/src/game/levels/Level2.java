package game.levels;

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
		bosses.add(new StrobeKing(Game.GAME_MAX_WIDTH - 150, Game.GAME_MAX_HEIGHT/3 ));
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
	public boolean isCurrentLevel() {
		if(game.level instanceof Level0) {
			return true;
		}
		return false;
	}

}
