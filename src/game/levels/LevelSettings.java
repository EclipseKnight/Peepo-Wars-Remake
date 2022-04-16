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
		getMobs().add(new MainMenuButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .10f, 0, this));

		
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if (getStatus().equals("mainmenu")) {
			System.out.println("test");
			setStatus("play");
			getGame().leveler.selectLevel(0);
		}
		if (doQuit) {
			doQuit = false;
			System.exit(0);
		}
	}

	@Override
	public void restartLevel() {
		getMobs().clear();
		getBosses().clear();
		initMobs();
		initBosses();
		
		for (Player p : getPlayers()) {
			p.setHealth(p.getMaxHealth());
			p.setShield(p.getMaxShield());
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
		g2d.setFont(getGame().gameFont.deriveFont(14f));	
		g2d.setColor(Color.WHITE);
		g2d.drawString("Settings... ", 10, 40);
		
	}

	
}
