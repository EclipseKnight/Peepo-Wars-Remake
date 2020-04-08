package game.handlers;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.levels.Level;
import game.levels.Level1;
import game.levels.Level2;
import game.levels.Level3;
import game.levels.LevelMainMenu;
import game.levels.LevelMultiplayer;
import game.levels.LevelSettings;
import game.levels.LevelUsername;
import game.sprites.mobs.mobs.ContinueButton;
import game.sprites.mobs.mobs.MainMenuButton;
import game.sprites.mobs.mobs.RestartButton;
import game.sprites.mobs.players.Player;
/**
 * Handles all of the level related stuff in the game.
 * @author Eclipse
 *
 */
public class LevelHandler {

	private Game game;
	
	public LevelHandler(Game game) {
		this.game = game;
	}
	
	public void updateLevel() {
		if(Game.level != null) {
			Game.level.tick();
			
			if(game.gameState == 1) {
				// TODO new parameter that checks for a levels requirements to complete.
				
				
				if(!Game.level.isBossesAlive() && !Game.level.isMobsAlive() && Game.level.isPlayersAlive()) {
					if(Game.level.isLastLevel()) {
						game.gameState = 3;
					} else {
						game.gameState = 2;
					}
				}
				
				if(!Game.level.isPlayersAlive()) {
					game.gameState = 4;
				}
				
			}
			
			// Level0 or any level that does not require all mobs be killed will never reach this gameState. 
			// Instead, nextLevel() will be triggered within the levels tick().
			// TODO change this
			if(game.gameState == 4 || game.gameState == 3 || game.gameState == 2 ) {
				
				if(!Game.level.isMobsAlive() && !Game.level.isBossesAlive()) {
					if(!Game.level.getStatus().equals("continue")) {
						Game.level.addMob(new ContinueButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .49f, Game.level));
						resetPlayers(Game.level.getPlayers());
						
					} 
					
					if(!Game.level.getStatus().equals("mainmenu")) {
						Game.level.addMob(new MainMenuButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .66f, Game.level));
						resetPlayers(Game.level.getPlayers());
					}
					
				}
				
				if(!Game.level.isPlayersAlive() && Game.level.isBossesAlive()) {
					Game.level.clearMobs();
					Game.level.clearBosses();
					
					if(!Game.level.getStatus().equals("mainmenu")) {
						Game.level.addMob(new MainMenuButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .66f, Game.level));
						resetPlayers(Game.level.getPlayers());
					}
					
					if(!Game.level.getStatus().equals("restart")) {
						Game.level.addMob(new RestartButton(Game.GAME_MAX_WIDTH-300, Game.GAME_MAX_BORDER * .49f, Game.level));
						resetPlayers(Game.level.getPlayers());
						
					}
				}
				
				if(Game.level.getStatus().equals("continue")) {
					Game.level.setStatus("play");
					game.leveler.nextLevel();
				}
				
				if(Game.level.getStatus().equals("restart")) {
					Game.level.setStatus("play");
					game.leveler.selectLevel(Game.level.getLevelNumber());
				}
				
				if(Game.level.getStatus().equals("mainmenu")) {
					Game.level.setStatus("play");
					game.leveler.selectLevel(0);
				}
			}
		}
	}
	
	public void firstLevel() {
		//Loops through levels to find the current one
		for(Level lv : game.levels) {
			if(lv.getLevelNumber() == Game.level.getLevelNumber() && lv.getLevelNumber() != 0) {
				
				//Creates a list to transfer players from one level to another.
				List<Player> players = new ArrayList<>();
				players = Game.level.getPlayers();
				
				//Resets health, shield and clears the list of currently fired ammo.
				resetPlayers(players);
				
				//sets the current level to the first level.
				Game.level = lookupLevel(0);
				
				//adds the players to the now current level.
				Game.level.addPlayers(players);
				
				//resets the previous level by creating a new object.
				resetLevel(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				

				//Continue running the game.
				game.gameState = 1;
				
				//Break if the level is found.
				break;
				
			} else if(lv.getLevelNumber() == 0) {
				//restart the current level.
				Game.level.restartLevel();
				
				game.gameState = 1;
			}
		}
	}
	
	public void nextLevel() {
		//Loops through levels to find the current one
		for(Level lv : game.levels) {
			if(lv.getLevelNumber() == Game.level.getLevelNumber() && lv.getLevelNumber() < Game.level.getLastLevel()) { 
				
				//Creates a list to transfer players from one level to another
				List<Player> players = new ArrayList<>();
				players = Game.level.getPlayers();
				
				//Resets health, shield and clears the list of currently fired ammo.
				resetPlayers(players);
				
				//sets the current level to the next one.
				Game.level = lookupLevelNumber(Game.level.getLevelNumber() + 1);
				
				//adds the players to the now current level
				Game.level.addPlayers(players);
				
				//resets the previous level by creating a new object
//				game.levels.set(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				resetLevel(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				//Continue running the game.
				game.gameState = 1;
				
				break;
				
			} else if(lv.getLevelNumber() >= Game.level.getLastLevel()) {
				//This condition is for if the player completed the last level.
				
				//Repeats the code for previous condition but sets player to first level (Menu) instead.
				List<Player> players = new ArrayList<>();
				players = Game.level.getPlayers();
				
				resetPlayers(players);
				
				Game.level = lookupLevel(0);
				
				Game.level.addPlayers(players);
				
				resetLevel(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				game.gameState = 1;
				
				
			}
		}
	}
	
	public void selectLevel(int lvlNumber) {
		//Loops through levels to find the current one
		for(Level lv : game.levels) {
			if(lv.getLevelNumber() == Game.level.getLevelNumber() && Game.level.getLevelNumber() != lvlNumber) {
				//Creates a list to transfer players from one level to another
				List<Player> players = new ArrayList<>();
				players = Game.level.getPlayers();
				
				//Resets health, shield and clears the list of currently fired ammo.
				resetPlayers(players);
				
				//sets the current level to the selected one.
				Game.level = lookupLevel(lvlNumber);
				
				//adds the players to the now current level
				Game.level.addPlayers(players);
				
				//resets the previous level by creating a new object
				resetLevel(lv.getLevelNumber(), lookupLevel(lv.getLevelNumber()));
				
				//Continue running the game.
				game.gameState = 1;
				
				break;
				
			} else {
				if(Game.level.getLevelNumber() == lvlNumber) {
					//restart the current level.
					Game.level.restartLevel();
					resetPlayers(Game.level.getPlayers());
					
					game.gameState = 1;
					
				}
			}
		}
	}
	
	private void resetPlayers(List<Player> players) {
		
		for(int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			
			player.setShield(player.getMaxShield());
			player.setHealth(player.getMaxHealth());
			player.setMode("normal");
			player.setX(50);
			player.setY(Game.GAME_MAX_BORDER/2 - (i * 40));
			player.clearAmmoFired();
			player.reset();
		}
	}
	public Level lookupLevel(int lvl) {
		Level level = null;
		
		switch(lvl) {
		case -3:
			level = new LevelMultiplayer(game);
			break;
		case -2:
			level = new LevelUsername(game);
			break;
		case -1:
			level = new LevelSettings(game);
			break;
		case 0: 
			level = new LevelMainMenu(game);
			break;
		case 1: 
			level = new Level1(game);
			break;
		case 2: 
			level = new Level2(game);
			break;
		case 3: 
			level = new Level3(game);
			break;
		default:
			CrashHandler.throwError("Unknown level attempting to be loaded.");
		}
		return level;
	}
	
	public Level lookupLevelNumber(int lv) {
		for(Level lvl : game.levels) {
			if(lvl.getLevelNumber() == lv) {
				return lvl;
			}
		}
		return null;
	}
	
	public void resetLevel(int lv, Level newLv) {
		for(int i = 0; i < game.levels.size(); i++) {
			if(game.levels.get(i).getLevelNumber() == lv) {
				game.levels.set(i, newLv);
			}
		}
	}
}
