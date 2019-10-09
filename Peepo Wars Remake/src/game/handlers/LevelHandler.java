package game.handlers;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.levels.Level;
import game.levels.Level0;
import game.levels.Level1;
import game.levels.Level2;
import game.sprites.mobs.Player;

public class LevelHandler {

	private Game game;
	
	public LevelHandler(Game game) {
		this.game = game;
	}
	
	public void updateLevel() {
		if(game.level != null) {
			game.level.tick();
			
			if(game.gameState == 1) {
				if(game.level.isBossesAlive() && game.level.isMobsAlive() && game.level.isPlayersAlive()) {
					game.gameState = 4;
				}
				
				if(!game.level.isPlayersAlive()) {
					game.gameState = 3;
				}
			}
			
			if(game.gameState == 4) {
				if(game.level.getLevelNumber() == game.levels.size()-1) {
					game.gameState = 2;
				}
			}
			//if gameState == 4 look @InputHandler for continue function
		}
	}
	
	public void firstLevel() {
		for(Level lv : game.levels) {
			if(lv.getLevelNumber() == game.level.getLevelNumber() && lv.getLevelNumber() != 0) {
				
				//Creates a list to transfer players from one level to another.
				List<Player> players = new ArrayList<>();
				players = game.level.getPlayers();
				
				//Resets health and clears the list of currently fired ammo.
				resetPlayers(players);
				
				//sets the current level to the first level.
				game.level = game.levels.get(0);
				
				//adds the players to the now current level.
				game.level.addPlayers(players);
				
				//resets the previous level by creating a new object.
				game.levels.set(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				//Continue running the game.
				game.gameState = 1;
				
				break;
			} else if(lv.getLevelNumber() == 0) {
				//restart the current level.
				game.level.restartLevel();
				
				game.gameState = 1;
			}
		}
	}
	
	public void nextLevel() {
		for(Level lv : game.levels) {
			if(lv.getLevelNumber() == game.level.getLevelNumber() && lv.getLevelNumber() < game.levels.size()-1) {
				
				//Creates a list to transfer players from one level to another
				List<Player> players = new ArrayList<>();
				players = game.level.getPlayers();
				
				//Resets health and clears the list of currently fired ammo.
				resetPlayers(players);
				
				//sets the current level to the next one.
				game.level = game.levels.get(game.level.getLevelNumber() + 1);
				
				//adds the players to the now current level
				game.level.addPlayers(players);
				
				//resets the previous level by creating a new object
				game.levels.set(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				//Continue running the game.
				game.gameState = 1;
				
				break;
			} else if(lv.getLevelNumber() >= game.levels.size()-1) {
				//This condition is for if the player completed the last level.
				
				//Repeats the code for previous condition but sets player to first level (Menu) instead.
				List<Player> players = new ArrayList<>();
				players = game.level.getPlayers();
				
				resetPlayers(players);
				
				game.level = game.levels.get(0);
				
				game.level.addPlayers(players);
				
				game.levels.set(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				game.gameState = 1;
				
			}
		}
	}
	
	
	private void resetPlayers(List<Player> players) {
		players.forEach(player -> {
			player.setHealth(player.getMaxHealth());
			player.clearAmmoFired();
			player.reset();
		});
	}
	private Level lookupLevel(int lvl) {
		Level level = null;
		
		switch(lvl) {
		case 0: 
			level = new Level0(game);
			break;
		case 1: 
			level = new Level1(game);
			break;
		case 2: 
			level = new Level2(game);
			break;
		default:
			CrashHandler.throwError("Unknown level attempting to be loaded.");
		}
		return level;
	}
}
