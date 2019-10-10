package game.sprites.mobs.players;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.CooldownHandler;
import game.handlers.InputHandler;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Mob;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;

public class Player extends Mob {

	
	private static final float SPEED = 3.5f;
	private static float HEALTH = 100;
	private static float MAXHEALTH = 100;
	private float shield = 50;
	private float maxShield = 50;
	
	private InputHandler input;
	private CooldownHandler cooler;
	private int weapon = 1;
	private List<Ammunition> ammoFired = new ArrayList<>();
	
	
	public Player(float x, float y, String name, InputHandler input) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, name, true);
		this.input = input;
		cooler = new CooldownHandler();
		initShip();
	}
	
	private void initShip() {
		loadImage("entities/ship.png");
		getImageDimensions(2);
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public int getWeapon() {
		return weapon;
	}
	
	public float getShield() {
		return shield;
	}
	
	public float getMaxShield() {
		return maxShield;
	}
	
	public List<Ammunition> getAmmoFired(){
		return ammoFired;
	}
	
	public InputHandler getInput() {
		return input;
	}
	
	public void setShield(float shield) {
		this.shield = shield;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void clearAmmoFired() {
		ammoFired.clear();
	}
	
	public void move(float dx, float dy) {
		 x += dx;
		 y += dy;
		 if(y + getHeight() > Game.GAME_MAX_BORDER) {
			 y -= (y + getHeight() - Game.GAME_MAX_BORDER);
		 }
		 if(y < Game.GAME_MIN_BORDER + 4) {
			 y = Game.GAME_MIN_BORDER + 4;
		 }
		 if(x + getWidth() > Game.DIMENSIONS.width) {
			 x -= (x + getWidth() - Game.DIMENSIONS.width);
		 }
		 if(x < 0) {
			 x = 0;
		 }
		 
	}
	
	@Override
	public void tick() {
		cooler.reduceCooldown();
		var dx = 0f;
		var dy = 0f;
		
		if(input != null) {
			
			if(input.up.isPressed()) {
				dy = -speed;
			}
			if(input.down.isPressed()) {
				dy = speed;
			}
			if(input.right.isPressed()) {
				dx = speed;
			}
			if(input.left.isPressed()) {
				dx = -speed;
			}
			if(input.space.isPressed()) {
				fire();
			}
			if(input.one.isPressed()) {
				weapon = 1;
			}
			if(input.two.isPressed()) {
				weapon = 2;
			}
		}
		
		if(dx != 0f || dy != 0f) {
			move(dx, dy);
		} 
	}
	
	public void fire() {
		Ammunition ammo = null;
	
		switch(weapon) {
		
		case 1:
			if(cooler.getLaserCooldown() <= 0) {
				ammo = new Laser(x + width, y + height / 2);
				ammoFired.add(ammo);
				cooler.resetCooldown(ammo);
			}
			break;
		case 2:
			if(cooler.getMissileCooldown() <= 0) {
				ammo = new Missile(x + width, y + height / 2);
				ammoFired.add(ammo);
				cooler.resetCooldown(ammo);
			}
			break;
		}
//		switch(weapon) {
//		
//		case 1 -> {
//			if(cooler.getLaserCooldown() <= 0) {
//				ammo = new Laser(x + width, y + height / 2);
//				ammoFired.add(ammo);
//				cooler.resetCooldown(ammo);
//			}
//		}
//		
//		case 2 -> {
//			if(cooler.getMissileCooldown() <= 0) {
//				ammo = new Missile(x + width, y + height / 2);
//				ammoFired.add(ammo);
//				cooler.resetCooldown(ammo);
//			}
//		}
//		}
	}

	public void win() {
		loadImage("entities/shipwin.gif");
		getImageDimensions(.6);
		setVisible(false);
	}
	
	public void lose() {
		loadImage("entities/shipdie.gif");
		getImageDimensions(.5);
		setVisible(false);
	}
	
	public void reset() {
		loadImage("entities/ship.png");
		getImageDimensions(2);
		setVisible(true);
		x = 30;
		y = Game.GAME_MAX_BORDER/2;
		//TODO improve x and y reset
	}
	@Override
	public void damage(float damage) {
		
		if(shield > 0) {
			if(shield - damage <= 0) {
				shield = 0;
			} else {
				shield -= damage;
			}
			
		} else if(health > 0) {
			health -= damage;
		} else {
			setVisible(false);
		}
	}
	

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new CircleHitBox((x + width/4), (y + height/4), Math.min(width, height)/3));
		return hitboxes;
	}
}
