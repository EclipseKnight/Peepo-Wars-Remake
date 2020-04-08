package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;
import game.sprites.mobs.mobs.MainMenuButton;
import game.sprites.mobs.players.Player;

public class LevelSettings extends Level {

	private static final int LEVELNUMBER = -1;
	
	public boolean doQuit = false;
	public LevelSettings(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {
		mobs.add(new MainMenuButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .10f, this));

		
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if(status.equals("mainmenu")) {
			System.out.println("test");
			status = "play";
			game.leveler.selectLevel(0);
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
			p.setShield(p.getMaxShield());
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
		g2d.setFont(game.gameFont.deriveFont(14f));	
		g2d.setColor(Color.WHITE);
		g2d.drawString("Settings... ", 10, 40);
		
	}

	
}
