package game.levels;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.Sprite;
import game.sprites.effects.Effect;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;

/**
 * The abstract Level class for creating levels.
 * @author jpaqu
 *
 */
public abstract class Level {

	private Game game;
	private int levelNumber;
	private List<Mob> mobs;
	private List<Player> players;
	private List<Boss> bosses;
	private List<Sprite> sprites;
	private List<Effect> effects;
	private String status;
	private boolean canContinue;
	private long ticks;

	public Level(Game game, int levelNumber) {
		this.game = game;
		this.levelNumber = levelNumber;
		this.mobs = new ArrayList<>();
		this.players = new ArrayList<>();
		this.bosses = new ArrayList<>();
		this.sprites = new ArrayList<>();
		this.effects = new ArrayList<>();
		this.status = "play";
		this.canContinue = false;
		this.ticks = 0;
	}

	/**
	 * Gets the {@linkplain Game} instance passed to the level.
	 * @return a {@linkplain Game} instance passed to the level
	 */
	public Game getGame() {
		return game;
	}
	
	/**
	 * Gets the ticks manually being counted for the level.
	 * @return a long representing ticks
	 */
	public long getTicks() {
		return ticks;
	}
	
	/**
	 * Sets the ticks.
	 * @param ticks
	 */
	public void setTicks(long ticks) {
		this.ticks = ticks;
	}
	
	/**
	 * Gets a list containing the {@linkplain Mob}s in the level.
	 * @return a List containing the {@linkplain Mob}s in the level
	 */
	public List<Mob> getMobs() {
		return mobs;
	}

	/**
	 * Gets a list containing the {@linkplain Boss}es in the level.
	 * @return a List containing the {@linkplain Boss}es in the level
	 */
	public List<Boss> getBosses() {
		return bosses;
	}
	
	/**
	 * Gets a list containing the {@linkplain Player}s in the level.
	 * @return a List containing the {@linkplain Player}s in the level
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Gets a list containing the {@linkplain Sprite}s in the level.
	 * @return a List containing the {@linkplain Sprite}s in the level
	 */
	public List<Sprite> getSprites() {
		return sprites;
	}
	
	public List<Effect> getEffects() {
		return effects;
	}
	
	/**
	 * Checks if all of the {@linkplain Mob}s are alive in the level.
	 * @return true if at least one mob is alive.
	 */
	public boolean isMobsAlive() {
		if (mobs.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if all of the {@linkplain Boss}es are alive in the level.
	 * @return true if at least one boss is alive.
	 */
	public boolean isBossesAlive() {
		if (bosses.isEmpty()) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if all of the {@linkplain Player}s are alive in the level.
	 * @return true if at least one player is alive.
	 */
	public boolean isPlayersAlive() {
		var isAlive = false;
		for (Player p : players) {
			if (p.getHealth() > 0) {
				isAlive = true;
			}
		}
		return isAlive;
	}
	
	/**
	 * Gets the {@linkplain level}s number.
	 * @return an int representing the level number
	 */
	public int getLevelNumber() {
		return levelNumber;
	}
	
	/**
	 * Checks if the {@linkplain level} is the last in the game.
	 * @return true if last
	 */
	public boolean isLastLevel() {
		if (levelNumber == game.levels.get(game.levels.size()-1).getLevelNumber()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the last {@linkplain level} in the game.
	 * @return A {@linkplain level} representing the last in the game.
	 */
	public int getLastLevel() {
		return game.levels.get(game.levels.size()-1).getLevelNumber();
	}
	
	/**
	 * The status which correlates to when the level should update.
	 * @return A String noting the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Adds a {@linkplain Player} to the level.
	 * @param player
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
	}
	
	/**
	 * Adds {@linkplain Player}s to the level.
	 * @param player
	 */
	public void addPlayers(List<Player> players) {
		this.players.addAll(players);
	}
	
	/**
	 * Adds a {@linkplain Mob} to the level.
	 * @param mob
	 */
	public void addMob(Mob mob) {
		this.mobs.add(mob);
	}
	
	/**
	 * Adds {@linkplain Mob}s to the level.
	 * @param mob
	 */
	public void addMobs(List<Mob> mobs) {
		this.mobs.addAll(mobs);
	}
	
	/**
	 * Adds a {@linkplain Boss} to the level.
	 * @param boss
	 */
	public void addBoss(Boss boss) {
		bosses.add(boss);
	}
	
	/**
	 * Adds {@linkplain Boss}es to the level.
	 * @param boss
	 */
	public void addBosses(List<Boss> bosses) {
		this.bosses.addAll(bosses);
	}
	
	/**
	 * Adds {@linkplain Sprite} to the level.
	 * @param sprite
	 */
	public void addSprite(Sprite sprite) {
		this.sprites.add(sprite);
	}
	
	/**
	 * Adds {@linkplain Sprite}s to the level.
	 * @param sprites
	 */
	public void addSprites(List<Sprite> sprites) {
		this.sprites.addAll(sprites);
	}
	
	/**
	 * Adds {@linkplain Effect} to the level.
	 * @param effect
	 */
	public void addEffect(Effect effect) {
		this.effects.add(effect);
	}
	
	/**
	 * Adds {@linkplain Effect}s to the level.
	 * @param effects
	 */
	public void addEffect(List<Effect> effects) {
		this.effects.addAll(effects);
	}
	
	/**
	 * Clears the {@linkplain Mob}s in the level.
	 */
	public void clearMobs() {
		this.mobs.clear();
	}
	
	/**
	 * Clears the {@linkplain Boss}es in the level.
	 */
	public void clearBosses() {
		this.bosses.clear();
	}
	
	/**
	 * Clears the {@linkplain Player}s in the level.
	 */
	public void clearPlayers() {
		this.players.clear();
	}
	
	/**
	 * Clears the {@linkplain Sprite}s in the level.
	 */
	public void clearSprites() {
		this.sprites.clear();
	}
	
	/**
	 * Clears the {@linkplain Effect}s in the level.
	 */
	public void clearEffect() {
		this.effects.clear();
	}
	
	/**
	 * Moves all of the players to their new position.
	 * @param playerNum
	 * @param x
	 * @param y
	 */
	public synchronized void movePlayer(int playerNum, float x, float y) {
		for (Player p : this.players) {
			if (p.getPlayerNum() == playerNum) {
				p.setX(x);
				p.setY(y);
			}
		}
	}
	
//	public abstract boolean metRequirements();
	
	/**
	 * Designated for restarting the level.
	 */
	public abstract void restartLevel();
	
	/**
	 * Designated for clearing the level.
	 */
	public abstract void clearLevel();

	/**
	 * Designated for updating any variable that keeps in time with the ticks per second of the game. 
	 * Example: movement, attack cooldowns, animations.
	 */
	public abstract void tick();
	
	/**
	 * Designated for any level specific rendering.
	 * @param g2d
	 */
	public abstract void render(Graphics2D g2d);

	
	
}
