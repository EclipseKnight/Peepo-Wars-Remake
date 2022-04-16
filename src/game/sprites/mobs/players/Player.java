package game.sprites.mobs.players;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.handlers.CooldownHandler;
import game.handlers.InputHandler;
import game.handlers.SpriteHandler;
import game.sprites.Animation;
import game.sprites.AnimationState;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Mob;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;

public class Player extends Mob {

	
	private static float SPEED = 3.5f;
	private static float HEALTH = 100;
	private static float MAXHEALTH = 100;
	private float shield = 50;
	private float maxShield = 50;
	
	private InputHandler input;
	private CooldownHandler cooler;
	private int playerNum;
	private int weapon = 1;
	private boolean canInput = true;
	private List<Ammunition> ammoFired = new ArrayList<>();
	
	private boolean demonMode = false;
	private boolean shieldMode = false;
	
	private List<Animation> shieldAnimations = new ArrayList<>();
	private List<Animation> demonAnimations = new ArrayList<>();
	private List<BufferedImage> peepos = new ArrayList<>();
	private BufferedImage peepo;
	private Animation demonAnimation;
	private AnimationState demonAnimationState;
	private Animation shieldAnimation;
	private AnimationState shieldAnimationState;
	
	
	private int tickCount = 0;
	
	public Player(float x, float y, String name, InputHandler input, int playerNum) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, name, playerNum, true);
		this.input = input;
		this.playerNum = playerNum;
		cooler = new CooldownHandler();
		initGFX();
	}
	
	private void initGFX() {
		
		peepos.add(SpriteHandler.getPeepoImage("Normal"));
		peepos.add(SpriteHandler.getPeepoImage("Demon"));
		
		shieldAnimations.add(SpriteHandler.getShieldAnimation("ShieldBlue"));
		shieldAnimations.add(SpriteHandler.getShieldAnimation("ShieldRed"));
		shieldAnimations.add(SpriteHandler.getShieldAnimation("ShieldGreen"));
		shieldAnimations.add(SpriteHandler.getShieldAnimation("ShieldOrange"));
		
		demonAnimations.add(SpriteHandler.getDemonAnimation("DemonBlue"));
		demonAnimations.add(SpriteHandler.getDemonAnimation("DemonRed"));
		demonAnimations.add(SpriteHandler.getDemonAnimation("DemonGreen"));
		demonAnimations.add(SpriteHandler.getDemonAnimation("DemonOrange"));
		
		
		demonAnimation = demonAnimations.get(playerNum);
		shieldAnimation = shieldAnimations.get(playerNum);
		shieldAnimationState = new AnimationState(shieldAnimation);
		demonAnimationState = new AnimationState(demonAnimation);
		
		setImage(peepos.get(0));
		getImageDimensions(2);
	}
	
	@Override
	public void tick() {
		cooler.reduceCooldown();
		animate();
		tickCount++;
		
		var dx = 0f;
		var dy = 0f;
		
		if (input != null && canInput) {
			
			if (input.up.isPressed()) {
				dy = -speed;
			}
			if (input.down.isPressed()) {
				dy = speed;
			}
			if (input.right.isPressed()) {
				dx = speed;
			}
			if (input.left.isPressed()) {
				dx = -speed;
			}
			if (input.space.isPressed()) {
				fire();
			}
			if (input.one.isPressed()) {
				weapon = 1;
			}
			if (input.two.isPressed()) {
				weapon = 2;
			}
		}
		
		if (dx != 0f || dy != 0f) {
			if (demonMode) {
				dx *= 1.1;
				dy *= 1.1;
			}
			move(dx, dy);
		} 
		
		if (demonMode && tickCount % 30 == 0) {
			health -= 1f;
			if (health <= 0) {
				setVisible(false);
			}
		}
	}
	
	public void move(float dx, float dy) {
		 x += dx;
		 y += dy;
		 if (y + getHeight() > Game.GAME_MAX_BORDER) {
			 y -= (y + getHeight() - Game.GAME_MAX_BORDER);
		 }
		 if (y < Game.GAME_MIN_BORDER + 4) {
			 y = Game.GAME_MIN_BORDER + 4;
		 }
		 if (x + getWidth() > Game.DIMENSIONS.width) {
			 x -= (x + getWidth() - Game.DIMENSIONS.width);
		 }
		 if (x < 0) {
			 x = 0;
		 }
	}
	
	@Override
	public void animate() {
		if (peepo != null) {
			setImage(peepo);
		}
		if (demonAnimationState != null) {
			demonAnimationState.update();
		}
		if (shieldAnimationState != null) {
			shieldAnimationState.update();
		}
	}
	
	public void fire() {
		Ammunition ammo = null;
	
		switch(weapon) {
		
		case 1:
			if (cooler.getLaserCooldown() <= 0) {
				ammo = new Laser(x + width * .5f, y - height * .10f);
				ammoFired.add(ammo);
				cooler.resetCooldown(ammo);
			}
			break;
		case 2:
			if (cooler.getMissileCooldown() <= 0) {
				ammo = new Missile(x + width * .5f, y + height * .10f);
				ammoFired.add(ammo);
				cooler.resetCooldown(ammo);
			}
			break;
		}
	}
	
	@Override
	public void damage(float damage) {
		
		if (shieldMode) {
			if (shield > 0) {
				if (shield - damage * .5 <= 0) {
					shield = 0;
					shieldMode = false;
					
				} else {
					shield -= damage * .5;
				} 
			}
			
		} else if (demonMode) {
			
			if (health - damage * 1.5 > 0) {
				health -= damage * 1.5f;
			} else if (health > 0)
				health -= damage * 1.5;
			
		} else {
			
			if (health - damage > 0) {
				health -= damage;
			} else if (health > 0) {
				health -= damage;
			}
		}
		
		if (health <= 0) {
			setVisible(false);
		}
		
	}
	
	@Override
	public void damage(Player player, float damage) {
		player.damage(damage);
		
	}
	
	public void reset() {
		setImage(peepos.get(0));
		getImageDimensions(2);
		setVisible(true);
	}
	
	public int getPlayerNum() {
		return playerNum;
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
	
	public void setShield(float shield) {
		this.shield = shield;
	}
	
	public void setHealth(float health) {
		this.health = health;
	}
	
	public void setMode(String mode) {
		if (mode.equalsIgnoreCase("shield")) {
			shieldMode = true;
			demonMode = false;
		} else if (mode.equalsIgnoreCase("demon")) {
			demonMode = true;
			shieldMode = false;
		} else if (mode.equalsIgnoreCase("normal")) {
			demonMode = false;
			shieldMode = false;
		}
	}
	
	public Image getDemonImage() {
		demonAnimation = demonAnimations.get(playerNum);
		return demonAnimation.getSprite(demonAnimationState.getCurrentFrame());
	}
	
	public Image getShieldImage() {
		shieldAnimation = shieldAnimations.get(playerNum);
		return shieldAnimation.getSprite(shieldAnimationState.getCurrentFrame());
	}
	
	public Image getPeepoImage(int num) {
		peepo = peepos.get(num);
		return peepo;
	}
	
	public boolean isDemonMode() {
		return demonMode;
	}
	
	public boolean isShieldMode() {
		return shieldMode;
	}
	
	public boolean isNormalMode() {
		if (shieldMode || demonMode) {
			return false;
		}
		return true;
	}
	
	public boolean canInput() {
		return canInput;
	}
	
	public void setCanInput(boolean canInput) {
		this.canInput = canInput;
	}
	
	public List<Ammunition> getAmmoFired(){
		return ammoFired;
	}
	
	
	public void clearAmmoFired() {
		ammoFired.clear();
	}
	
	public InputHandler getInput() {
		return input;
	}
	
	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		if (health > 0) {
			hitboxes.add(new CircleHitBox((x + width/4), (y + height/4), Math.min(width, height)/3));
		}
		return hitboxes;
	}
}
