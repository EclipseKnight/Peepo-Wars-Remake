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
	}

	private void initMobs() {
		getMobs().add(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .8f, 0, this));
		
	}
	
	
	@Override
	public void tick() {
		setTicks(getTicks() + 1);
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if (getStatus().equals("continue")) {
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
		g2d.drawString("Type your username... Press enter to toggle between typing and moving", 10, 40);
		
		if (getPlayers().get(0) != null) {
			var p = getPlayers().get(0);
			var fontSize = 25f;
			g2d.setFont(getGame().gameFont.deriveFont(fontSize));	
			
			var w = g2d.getFontMetrics().stringWidth(p.getName());
			var offset = (fontSize * p.getName().length()-1) * .45f;
			
			g2d.drawString(p.getName(), Game.GAME_MAX_WIDTH * .45f - offset, Game.GAME_MAX_BORDER * .5f);
			
			
			g2d.setFont(getGame().gameFont.deriveFont(20f));	
			if (p.canInput()) {
				g2d.drawString("Moving...", 20, 100);
				
			} else {
				g2d.drawString("Typing...", 20, 100);
				//index indicator for typing still going off other font size
				
				if (getTicks() % 30 < 15)
					g2d.drawRect((int) (Game.GAME_MAX_WIDTH * .45f - offset + w), (int) (Game.GAME_MAX_BORDER * .5f), (int) fontSize, 1);
			}
				
		}
		
		g2d.setFont(getGame().gameFont.deriveFont(12f));	
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
