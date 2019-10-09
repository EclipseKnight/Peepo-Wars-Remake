package game.handlers;

import java.awt.Color;
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
import game.sprites.mobs.Player;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;
import game.sprites.mobs.attacks.Attack;
import resources.ResourceLoader;

public class RenderHandler {

	private Game game;

	public RenderHandler(Game game) {
		this.game = game;
	}

	public void renderFPSAndTPSCounter(Graphics2D g2d) {
		g2d.setColor(Color.DARK_GRAY);
		g2d.drawString("FPS:" + Game.fps, 0, Game.GAME_MIN_BORDER - 5);
		g2d.drawString("TPS:" + Game.tps, 100, Game.GAME_MIN_BORDER - 5);
//		g2d.drawString("TPS:" + game.gameState, 200, Game.GAME_MIN_BORDER - 5);
	}

	public void renderBorder(Graphics2D g2d) {
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.drawRect(0, Game.GAME_MIN_BORDER, (int) Game.GAME_MAX_WIDTH, 2);
		g2d.drawRect(0, Game.GAME_MAX_BORDER, (int) Game.GAME_MAX_WIDTH, 2);
	}

	public void renderBuffer(Graphics2D g2d, BufferedImage image) {
		g2d.drawImage(image, 0, 0, game.frame.getWidth(), game.frame.getHeight(), null);
	}

	public void renderPlayers(Graphics2D g2d, List<Player> players) {
		for (Player p : players) {
			g2d.drawImage(p.getImage(), (int) p.getX(), (int) p.getY(), p.getWidth(), p.getHeight(), null);
			if(Game.showhitbox) {
				renderHitBox(g2d, p.getHitBox());
			}
		}
	}
	
	public void renderMobs(Graphics2D g2d, List<Mob> mobs) {
		for(Mob m : mobs) {
			g2d.drawImage(m.getImage(), (int) m.getX(), (int) m.getY(), m.getWidth(), m.getHeight(), null);
			if(Game.showhitbox) {
				renderHitBox(g2d, m.getHitBox());
			}
		}
	}
	
	public void renderBosses(Graphics2D g2d, List<Boss> bosses) {
		for(Boss b : bosses) {
			g2d.drawImage(b.getImage(), (int) b.getX(), (int) b.getY(), b.getWidth(), b.getHeight(), null);
			if(Game.showhitbox) {
				renderHitBox(g2d, b.getHitBox());
			}
		}
	}

	public void renderBackground(Graphics2D g2d) {
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, game.frame.getWidth(), game.frame.getHeight());
		g2d.drawImage(ResourceLoader.loadImage("animations/stage/starbackgroundslow.gif"), 0, 0, null);
	}

	public void renderUI(Graphics2D g2d) {
		Image music = null;

		g2d.setColor(Color.lightGray);
		g2d.fillRoundRect((int) Game.GAME_MAX_WIDTH / 2 - 40, (int) Game.GAME_MAX_HEIGHT - 50, 80, 40, 10, 10);

		if (AudioPlayer.status.equals("play")) {
			music = new MusicOn(0, 0).getImage();
		} else if (AudioPlayer.status.equals("paused")) {
			music = new MusicOff(0, 0).getImage();
		}

		g2d.drawImage(music, (int) Game.GAME_MAX_WIDTH / 2 - 35, (int) Game.GAME_MAX_HEIGHT - 45,
				music.getWidth(null) * 2, music.getHeight(null) * 2, null);
	}
	
	public void renderWeaponBar(Graphics2D g2d) {
		
		if(game.level.getPlayers().size() > 0) {
			var images = new ArrayList<Image>();
			
			var laser = new Laser(0,0).getImage();
			var missile = new Missile(0,0).getImage();
			
			images.add(laser);
			images.add(missile);
			
			Rectangle2D slot;
			for(int i = 0; i < images.size(); i++) {
				Image image = images.get(i);
				slot = new Rectangle2D.Double((40 * i)+10, Game.GAME_MAX_BORDER+20, 30, 30);
				g2d.drawImage(image, (int) (slot.getX() + slot.getWidth()/5), (int) (slot.getY() + slot.getHeight()/3), (int) ((image.getWidth(null) * 2) * Game.GAME_SCALE), 
						(int) ((image.getHeight(null) * 2) * Game.GAME_SCALE), null);
				if(game.level.getPlayers().get(0).getWeapon() == i+1) {
					g2d.setColor(Color.blue);
				} else
					g2d.setColor(Color.DARK_GRAY);
				g2d.draw(slot);
			}
		}
		
	}

	public void renderProjectiles(Graphics2D g2d) {
//		Iterator<?> it2 = null; 
		Iterator<?> it1 = null;
		for(Player p : game.level.getPlayers()) {
			it1 = p.getAmmoFired().iterator();
			
			while(it1.hasNext()){
				Ammunition ammo = (Ammunition) it1.next();
				g2d.drawImage(ammo.getImage(), (int) ammo.getX(), (int) ammo.getY(), (int) ((ammo.getWidth() * (Game.GAME_SCALE))), (int) ((ammo.getHeight() * (Game.GAME_SCALE))), null);
				if(Game.showhitbox) {
					
					renderHitBox(g2d, ammo.getHitBox());
				}
			}
		}
		
		for(Boss b : game.level.getBosses()) {
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
		
//		switch(hb.getHitBoxType()) {
//		
//		case CIRCLE -> {
//			CircleHitBox hitbox = (CircleHitBox) hb;
//			g2d.drawOval((int) hitbox.getX(), (int) hitbox.getY(), 
//					(int)(hitbox.getRadius() * Game.GAME_SCALE), (int)(hitbox.getRadius() * Game.GAME_SCALE));
//		}
//		
//		case RECTANGLE -> {
//			RectangleHitBox hitbox = (RectangleHitBox) hb;
//			g2d.drawRect((int) hitbox.getX(), (int) hitbox.getY(), 
//					(int) (hitbox.getWidth() * Game.GAME_SCALE), (int) (hitbox.getHeight() * Game.GAME_SCALE));
//		}
//		default -> throw new IllegalArgumentException("Unexpected value: " + hb.getHitBoxType());
//		}
		
	}
	
	public void renderHealthBar(Graphics2D g2d) {
		
		Rectangle bar;
		Rectangle health;
		g2d.setFont(game.gameFont.deriveFont(10f));
		
		
		for(int i = 0; i < game.level.getBosses().size(); i++) {
			var b = game.level.getBosses().get(i);
			
			bar = new Rectangle( (int) (Game.GAME_MAX_WIDTH - 370), (int) Game.GAME_MAX_HEIGHT - 90 / (i+1), 350, 20); 
			health = new Rectangle( bar.x+1, bar.y+1, barCalculateBoss(b, bar.width)-1, bar.height-1);
			
			g2d.setColor(Color.gray);
			g2d.draw(bar);
			g2d.setColor(Color.red);
			g2d.fill(health);
			g2d.setColor(Color.white);
			g2d.drawString(b.getHealth() + "/" + b.getMaxHealth(), (int)(bar.x*1.01), bar.y+14);
			g2d.drawString(b.getName(), (int)(bar.x*1.01), bar.y-10);
		}
		
		for(int i = 0; i < game.level.getPlayers().size(); i++) {
			var p = game.level.getPlayers().get(i);
			bar = new Rectangle( 10, (int) Game.GAME_MAX_HEIGHT - 90 / (i+1), 350, 20);
			health = new Rectangle( bar.x+1, bar.y+1, barCalculateShip(p, bar.width)-1, bar.height-1);
			
			g2d.setColor(Color.gray);
			g2d.draw(bar);
			g2d.setColor(Color.red);
			g2d.fill(health);
			g2d.setColor(Color.white);
			g2d.drawString(p.getHealth() + "/" + p.getMaxHealth(), (int)(bar.x*1.01), bar.y+14);
			g2d.drawString(p.getName(), (int)(bar.x*1.01), bar.y-10);
		}
			
		
		for(Mob m : game.level.getMobs()) {
			
			if(m.showHealth()) {
				bar = new Rectangle( (int) m.getX(), (int) m.getY() + m.getHeight()+10, m.getWidth(), 10);
				health = new Rectangle( bar.x+1, bar.y+1, (int)(barCalculate(m))-1 , 10);
				
				g2d.setColor(Color.gray);
				g2d.draw(bar);
				g2d.setColor(Color.red);
				g2d.fill(health);
				g2d.setColor(Color.white);
				g2d.drawString(m.getHealth() + "/" + m.getMaxHealth(), (int)(bar.x*1.01), bar.y+10);
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
		
	//Calculates Ship HP bar size
	private int barCalculateShip(Player p, int width) {
		return (int)(p.getHealth()/p.getMaxHealth() * width);
	}

	public void renderWin(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.cyan);
		g2d.drawString("You Win!", 270, 120);
		g2d.setFont(game.gameFont);
		g2d.drawString("Press R To Restart...", 200, 390);
	}
	
	public void renderLose(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.red);
		g2d.drawString("You Lose!", 270, 120);
		g2d.setFont(game.gameFont);
		g2d.drawString("Press R To Restart...", 200, 390);
	}

	public void renderLevelWin(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(50f));
		g2d.setColor(Color.cyan);
		g2d.drawString("Level Completed!", 120, 120);
		g2d.setFont(game.gameFont);
		g2d.drawString("Press R To Continue...", 200, 390);
	}
	
}
