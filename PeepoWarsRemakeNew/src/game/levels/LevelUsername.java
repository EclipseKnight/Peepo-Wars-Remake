package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;

import game.Game;
import game.sprites.mobs.mobs.ContinueButton;
import game.sprites.mobs.players.Player;

public class LevelUsername extends Level {

	private static final int LEVELNUMBER = -2;
	
	public boolean doQuit = false;
	public LevelUsername(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {
		mobs.add(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .8f, this));
		
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if(status.equals("continue")) {
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
		g2d.drawString("Type your username... Press enter to toggle between typing and moving", 10, 40);
		
		if(players.get(0) != null) {
			var p = players.get(0);
			var fontSize = 25f;
			g2d.setFont(game.gameFont.deriveFont(fontSize));	
			
			var w = g2d.getFontMetrics().stringWidth(p.getName());
			var offset = (fontSize * p.getName().length()-1) * .45f;
			
			g2d.drawString(p.getName(), Game.GAME_MAX_WIDTH * .45f - offset, Game.GAME_MAX_BORDER * .5f);
			
			
			g2d.setFont(game.gameFont.deriveFont(20f));	
			if(p.canInput()) {
				g2d.drawString("Moving...", 20, 100);
				
			} else {
				g2d.drawString("Typing...", 20, 100);
				//index indicator for typing still going off other font size
				g2d.drawRect((int) (Game.GAME_MAX_WIDTH * .45f - offset + w), (int) (Game.GAME_MAX_BORDER * .5f), (int) fontSize, 1);
			}
				
		}
		
		g2d.setFont(game.gameFont.deriveFont(12f));	
		g2d.setColor(Color.WHITE);
		
		var offset = 20;
		g2d.drawString("Controls... ", 10, Game.GAME_MAX_BORDER - offset * 6);
		g2d.drawString("Movement: Arrow Keys ", 10, Game.GAME_MAX_BORDER - offset * 5);
		g2d.drawString("Shoot: Space ", 10, Game.GAME_MAX_BORDER - offset * 4);
		g2d.drawString("Weapons: 1, 2 ", 10, Game.GAME_MAX_BORDER - offset * 3);
		g2d.drawString("Shield Mode: S ", 10, Game.GAME_MAX_BORDER - offset * 2);
		g2d.drawString("Demon Mode: D ", 10, Game.GAME_MAX_BORDER - offset * 1);
	}

	
}
