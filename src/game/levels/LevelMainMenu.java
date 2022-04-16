package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;
import game.sprites.mobs.mobs.ContinueButton;
import game.sprites.mobs.mobs.MultiplayerButton;
import game.sprites.mobs.mobs.PeepoWarsLogo;
import game.sprites.mobs.mobs.QuitButton;
import game.sprites.mobs.players.Player;

public class LevelMainMenu extends Level {

	private static final int LEVELNUMBER = 0;
	
	
	public boolean doQuit = false;
	public LevelMainMenu(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {

		getMobs().add(new PeepoWarsLogo(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .08f, 0));
		getMobs().add(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .49f, 1, this));
		getMobs().add(new MultiplayerButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .66f, 2, this));
		getMobs().add(new QuitButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .83f, 3, this));
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if (getStatus().equals("continue")) {
			setStatus("play");
			getGame().leveler.nextLevel();
		}
		
		if (getStatus().equals("multiplayer")) {
			setStatus("play");
			getGame().leveler.selectLevel(-3);
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
		g2d.drawString("Controls... ", 10, 40);
		g2d.drawString("Movement: Arrow Keys ", 10, 60);
		g2d.drawString("Shoot: Space ", 10, 80);
		g2d.drawString("Weapons: 1, 2 ", 10, 100);
		g2d.drawString("Shield Mode: S ", 10, 120);
		g2d.drawString("Demon Mode: D ", 10, 140);
		g2d.drawString("Shoot an option!", Game.GAME_MAX_WIDTH * .45f, 200);		
	}

	
}
