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

		mobs.add(new PeepoWarsLogo(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .08f));
		mobs.add(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .49f, this));
		mobs.add(new MultiplayerButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .66f, this));
		mobs.add(new QuitButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .83f, this));
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if(status.equals("continue")) {
			status = "play";
			game.leveler.nextLevel();
		}
		
		if(status.equals("multiplayer")) {
			status = "play";
			game.leveler.selectLevel(-3);
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
		g2d.drawString("Controls... ", 10, 40);
		g2d.drawString("Movement: Arrow Keys ", 10, 60);
		g2d.drawString("Shoot: Space ", 10, 80);
		g2d.drawString("Weapons: 1, 2 ", 10, 100);
		g2d.drawString("Shield Mode: S ", 10, 120);
		g2d.drawString("Demon Mode: D ", 10, 140);
		g2d.drawString("Shoot an option!", Game.GAME_MAX_WIDTH * .45f, 200);		
	}

	
}
