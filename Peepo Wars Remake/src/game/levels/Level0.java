package game.levels;

import game.Game;
import game.sprites.mobs.Player;
import game.sprites.mobs.mobs.ContinueButton;
import game.sprites.mobs.mobs.PeepoWarsLogo;
import game.sprites.mobs.mobs.QuitButton;

public class Level0 extends Level {

	private static final int LEVELNUMBER = 0;
	
	
	public boolean doContinue = false;
	public boolean doQuit = false;
	public Level0(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {

		mobs.add(new PeepoWarsLogo(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER/10f));
		mobs.add(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER/1.75f, this));
		mobs.add(new QuitButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER/1.25f, this));
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		if(doContinue) {
			doContinue = false;
			game.leveler.nextLevel();
		}
		
		if(doQuit) {
			doQuit = false;
			System.exit(0);
		}
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
