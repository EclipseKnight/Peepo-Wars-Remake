package game.handlers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game.Game;
import game.audio.AudioPlayer;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.icons.MusicOff;
import game.sprites.icons.MusicOn;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.players.Player;
import resources.ResourceLoader;

/**
 * Handles all of the rendering.
 * @author Eclipse
 *
 */
public class RenderHandler {

	private Game game;

	public RenderHandler(Game game) {
		this.game = game;
	}

	/**
	 * Renders the FPS and TPS counter,
	 * @param g2d
	 */
	public void renderFPSAndTPSCounter(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("FPS:" + Game.fps, 0, Game.GAME_MIN_BORDER - 5);
		g2d.drawString("TPS:" + Game.tps, 100, Game.GAME_MIN_BORDER - 5);
		
	}

	/**
	 * Renders the game borders.
	 * @param g2d
	 */
	public void renderBorder(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawRect(0, Game.GAME_MIN_BORDER, (int) Game.GAME_MAX_WIDTH, 2);
		g2d.drawRect(0, Game.GAME_MAX_BORDER, (int) Game.GAME_MAX_WIDTH, 2);
	}

	/**
	 * Renders the background buffer.
	 * @param g2d
	 */
	public void renderBuffer(Graphics2D g2d, BufferedImage image) {
		g2d.drawImage(image, 0, 0, game.frame.getWidth(), game.frame.getHeight(), null);
	}
	
	/**
	 * Renders any {@linkplain Level} specific rendering (text, etc).
	 * @param g2d
	 */
	public void renderLevel(Graphics2D g2d) {
		Game.level.render(g2d);
	}

	/**
	 * Renders the {@linkplain Player}s.
	 * @param g2d
	 */
	public void renderPlayers(Graphics2D g2d, List<Player> players) {
		for(int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			
			if(p.isVisible()) {
				
				if(p.isShieldMode()) {
					var shield = p.getShieldImage();
					var shielddim = new Dimension((int) (shield.getWidth(null) * .3), (int) (shield.getHeight(null) * .3));
					g2d.drawImage(p.getPeepoImage(0), (int) p.getX(), (int) p.getY(), p.getWidth(), p.getHeight(), null);
					g2d.drawImage(shield, (int) (p.getX() - shielddim.width/2.7), (int) (p.getY() - shielddim.height/2.8), shielddim.width, shielddim.height, null);
					
				}
				
				if(p.isDemonMode()) {
					var demon = p.getDemonImage();
					var demondim = new Dimension((int) (demon.getWidth(null) * .075), (int) (demon.getHeight(null) * .075));
					g2d.drawImage(demon, (int) (p.getX() - demondim.width * .35), (int) (p.getY() - demondim.height * .6), demondim.width, demondim.height, null);
					g2d.drawImage(p.getPeepoImage(1), (int) p.getX(), (int) p.getY(), p.getWidth(), p.getHeight(), null);
				}
				
				if(p.isNormalMode()) {
					g2d.drawImage(p.getPeepoImage(0), (int) p.getX(), (int) p.getY(), p.getWidth(), p.getHeight(), null);
				}
				
				
				if(Game.showhitbox) {
					renderHitBox(g2d, p.getHitBox());
				}
			}
		}
	}
	
	/**
	 * Renders the {@linkplain Mob}s.
	 * @param g2d
	 */
	public void renderMobs(Graphics2D g2d, List<Mob> mobs) {
		for(Mob m : mobs) {
			g2d.drawImage(m.getImage(), (int) m.getX(), (int) m.getY(), m.getWidth(), m.getHeight(), null);
			if(Game.showhitbox) {
				renderHitBox(g2d, m.getHitBox());
			}
		}
	}
	
	/**
	 * Renders the {@linkplain Boss}es. 
	 * @param g2d
	 */
	public void renderBosses(Graphics2D g2d, List<Boss> bosses) {
		for(Boss b : bosses) {
			g2d.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), b.getWidth(), b.getHeight(), null);
			if(Game.showhitbox) {
				renderHitBox(g2d, b.getHitBox());
			}
		}
	}

	/**
	 * Renders the background.
	 * @param g2d
	 */
	public void renderBackground(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, game.frame.getWidth(), game.frame.getHeight());
		g2d.drawImage(ResourceLoader.loadImage("animations/stage/starbackground.gif"), 0, 0, (int) Game.GAME_MAX_WIDTH, (int) Game.GAME_MAX_HEIGHT, null);
	}

	/**
	 * Renders the UI
	 * @param g2d
	 */
	public void renderUI(Graphics2D g2d) {
		Image music = null;

		if (AudioPlayer.status.equals("play")) {
			music = new MusicOn(0, 0).getImage();
		} else if (AudioPlayer.status.equals("paused")) {
			music = new MusicOff(0, 0).getImage();
		}

		g2d.drawImage(music, (int) Game.GAME_MAX_WIDTH / 2 - 35, (int) Game.GAME_MAX_HEIGHT - 45,
				music.getWidth(null) * 2, music.getHeight(null) * 2, null);
	}
	
	/**
	 * Renders weapon bar
	 * @param g2d
	 */
	public void renderWeaponBar(Graphics2D g2d) {
		
		if(Game.level.getPlayers().size() > 0) {
			var images = new ArrayList<Ammunition>();
			
			var laser = new Laser(0,0);
			var missile = new Missile(0,0);
			
			images.add(laser);
			images.add(missile);
			
			Rectangle2D slot;
			for(int i = 0; i < images.size(); i++) {
				var image = images.get(i);
				slot = new Rectangle2D.Double((40 * i)+10, Game.GAME_MAX_BORDER+20, 30, 30);
				
				g2d.drawImage(image.getImage(), (int) (slot.getX() - image.getWidth()/3 ), (int) (slot.getY() + slot.getHeight()/3), (int) (image.getWidth() * Game.GAME_SCALE), 
						(int) (image.getHeight() * Game.GAME_SCALE), null);
				
				if(Game.level.getPlayers().get(0).getWeapon() == i+1) {
					g2d.setColor(Color.white);
				} else
					g2d.setColor(Color.DARK_GRAY);
				g2d.draw(slot);
			}
		}
		
	}

	/**
	 * Renders the projectiles visible ({@linkplain Ammunition}, {@linkplain Attack}).
	 * @param g2d
	 */
	public void renderProjectiles(Graphics2D g2d) {
//		Iterator<?> it2 = null; 
		Iterator<?> it1 = null;
		for(Player p : Game.level.getPlayers()) {
			it1 = p.getAmmoFired().iterator();
			
			while(it1.hasNext()){
				Ammunition ammo = (Ammunition) it1.next();
				g2d.drawImage(ammo.getImage(), (int) ammo.getX(), (int) ammo.getY(), (int) ((ammo.getWidth() * (Game.GAME_SCALE))), (int) ((ammo.getHeight() * (Game.GAME_SCALE))), null);
				if(Game.showhitbox) {
					
					renderHitBox(g2d, ammo.getHitBox());
				}
			}
		}
		
		for(Boss b : Game.level.getBosses()) {
			it1 = b.getAttacks().iterator();
			
			while(it1.hasNext()){
				Attack attack = (Attack) it1.next();
				g2d.drawImage(attack.getImage(), (int) attack.getX(), (int) attack.getY(), (int) ((attack.getWidth() * (Game.GAME_SCALE))), (int) ((attack.getHeight() * (Game.GAME_SCALE))), null);
				if(Game.showhitbox) {
					
					renderHitBox(g2d, attack.getHitBox());
				}
			}
		}
	}
	
	/**
	 * Renders the {@linkplain HitBox}es.
	 * @param g2d
	 */
	private void renderHitBox(Graphics2D g2d, List<HitBox> hbs) {
		g2d.setColor(Color.green);
		
		for(HitBox hb : hbs) {
			switch(hb.getHitBoxType()) {
			case CIRCLE:
				CircleHitBox hitbox = (CircleHitBox) hb;
				g2d.drawOval((int) hitbox.getX(), (int) hitbox.getY(), 
						(int)(hitbox.getRadius()*2), (int)(hitbox.getRadius()*2));
				break;
				
			case RECTANGLE:
				RectangleHitBox hitbox1 = (RectangleHitBox) hb;
				g2d.drawRect((int) hitbox1.getX(), (int) hitbox1.getY(), 
						(int) (hitbox1.getWidth() * Game.GAME_SCALE), (int) (hitbox1.getHeight() * Game.GAME_SCALE));
				break;
				
			default:
				throw new IllegalArgumentException("Unexpected value: " + hb.getHitBoxType());
			}
		}
	}
	
	/**
	 * Renders the health bars.
	 * @param g2d
	 */
	public void renderHealthBar(Graphics2D g2d) {
		
		Rectangle bar;
		Rectangle health;
		Rectangle shield;
		
		
		
		for(int i = 0; i < Game.level.getBosses().size(); i++) {
			var b = Game.level.getBosses().get(i);
			
			bar = new Rectangle( (int) (Game.GAME_MAX_WIDTH - 440), (int) Game.GAME_MAX_HEIGHT - 170 + (i*45), 400, 20); 
			health = new Rectangle( bar.x+1, bar.y+1, barCalculateBoss(b, bar.width)-1, bar.height-1);
			
			g2d.setFont(game.gameFont.deriveFont(10f));
			g2d.setColor(Color.white);
			g2d.draw(bar);
			g2d.setColor(Color.red);
			g2d.fill(health);
			g2d.setColor(Color.white);
			
			g2d.drawString("HP: " + b.getHealth() + "/" + b.getMaxHealth(), (int)(bar.x*1.01), bar.y+14);
			g2d.setFont(game.gameFont.deriveFont(12f));	
			g2d.drawString(b.getName(), (int)(bar.x*1.01), bar.y-10);
		}
		
		for(int i = 0; i < Game.level.getPlayers().size(); i++) {
			var p = Game.level.getPlayers().get(i);
			
			bar = new Rectangle( 10, (int) Game.GAME_MAX_HEIGHT - 170 + (i*45), 400, 20);
			health = new Rectangle( bar.x+1, bar.y+1, barCalculateShip(p, bar.width/2, 0)-1, bar.height-1);
			shield = new Rectangle( bar.x+1, bar.y+1, barCalculateShip(p, bar.width, 1)-1, bar.height-1);
			shield.x = (bar.x + bar.width) - shield.width;
			
			g2d.setFont(game.gameFont.deriveFont(10f));
			g2d.setColor(Color.white);
			g2d.draw(bar);
			g2d.setColor(Color.red);
			g2d.fill(health);
			g2d.setColor(Color.blue);
			g2d.fill(shield);
			g2d.setColor(Color.white);
			
			g2d.drawString("HP: " + p.getHealth() + "/" + p.getMaxHealth(), (int)(bar.x*1.5), bar.y+14);
			g2d.drawString("Shield: " + p.getShield() + "/" + p.getMaxShield(), (int)(bar.x*22), bar.y+14);
			g2d.setFont(game.gameFont.deriveFont(12f));	
			g2d.drawString(p.getName(), (int)(bar.x*1.01), bar.y-10);
		}
			
		
		for(Mob m : Game.level.getMobs()) {
			
			if(m.showHealth()) {
				bar = new Rectangle( (int) m.getX(), (int) m.getY() + m.getHeight()+10, m.getWidth(), 10);
				health = new Rectangle( bar.x+1, bar.y+1, (int)(barCalculate(m))-1 , 9);
				
				g2d.setFont(game.gameFont.deriveFont(10f));
				g2d.setColor(Color.white);
				g2d.draw(bar);
				g2d.setColor(Color.red);
				g2d.fill(health);
				g2d.setColor(Color.white);
				
				g2d.drawString(m.getHealth() + " " /*+ "/" + m.getMaxHealth() */, (int)(bar.x*1.01), bar.y+10);
			}
		}
	}
	
	//Calculates Enemy HP bar size
	private int barCalculate(Mob m) {
		return (int)(m.getHealth()/m.getMaxHealth() * m.getWidth());
	}
		
	//Calculates Boss HP bar size
	private int barCalculateBoss(Boss b, int width) {
		return (int)(b.getHealth()/b.getMaxHealth() * width);
	}
		
	//Calculates Ship HP and Shield bar size
	private int barCalculateShip(Player p, int width, int type) {
		if(type == 0) 
			return (int)(p.getHealth()/p.getMaxHealth() * width);
		
		if(type == 1) 
			return (int)(p.getShield()/p.getMaxHealth() * width);
		
		return 0;
		
	}

	/**
	 * Renders the win state.
	 * @param g2d
	 */
	public void renderWin(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.cyan);
		g2d.drawString("You Win!", 270, 120);
	}
	
	/**
	 * Renders the lose state.
	 * @param g2d
	 */
	public void renderLose(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.red);
		g2d.drawString("You Lose!", 270, 120);
	}

	/**
	 * Renders level completion state.
	 * @param g2d
	 */
	public void renderLevelWin(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.cyan);
		g2d.drawString("Level Completed!", 120, 120);
	}
	
}
