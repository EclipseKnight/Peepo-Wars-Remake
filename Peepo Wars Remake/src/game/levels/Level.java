package game.levels;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;

public abstract class Level {

	protected Game game;
	protected int levelNumber;
	protected List<Mob> mobs;
	protected List<Player> players;
	protected List<Boss> bosses;

	public Level(Game game, int levelNumber) {
		this.game = game;
		this.levelNumber = levelNumber;
		this.mobs = new ArrayList<>();
		this.players = new ArrayList<>();
		this.bosses = new ArrayList<>();

	}

	public List<Mob> getMobs() {
		return mobs;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public List<Boss> getBosses() {
		return bosses;
	}
	
	public boolean isMobsAlive() {
		if(mobs.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean isBossesAlive() {
		if(bosses.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public boolean isPlayersAlive() {
		var isAlive = false;
		for(Player p : players) {
			if(p.getHealth() > 0) {
				isAlive = true;
			}
		}
		return isAlive;
	}
	
	public int getLevelNumber() {
		return levelNumber;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void addPlayers(List<Player> players) {
		this.players.addAll(players);
	}
	
	public abstract void restartLevel();
	
	public abstract void clearLevel();

	public abstract void tick();
	
	public abstract boolean isCurrentLevel();
}
