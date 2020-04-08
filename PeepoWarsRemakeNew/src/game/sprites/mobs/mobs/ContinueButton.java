package game.sprites.mobs.mobs;

import java.util.ArrayList;
import java.util.List;

import game.handlers.SpriteHandler;
import game.levels.Level;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;

public class ContinueButton extends Mob {
	
	public static final float HEALTH = 100;
	public static final float MAXHEALTH = 100;
	public static final float SPEED = 3f;
	public static final String NAME = "Continue";
	private Level level;
	
	private boolean moveUp = false;
	private int tickCount = 0;
	public ContinueButton(float x, float y, Level level) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, true);
		this.level = level;
		initGFX();
	}
	
	private void initGFX() {
		setImage(SpriteHandler.getMobImage("ContinueButton"));
		getImageDimensions(3);
	}

	@Override
	public void tick() {
	tickCount++;
		
		if(moveUp && tickCount % 40 == 0) {
			y += speed;
			moveUp = false;
			
			tickCount = 0;
			return;
		}
		
		if(!moveUp && tickCount % 40 == 0) {
			y -= speed;
			moveUp = true;
			
			tickCount = 0;
			return;
		}
	}
	
	@Override
	public void animate() {
		
	}

	@Override
	public void damage(float damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
			level.setStatus("continue");
		}
	}
	
	@Override
	public void damage(Player player, float damage) {
		player.damage(0);
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox(x, y, width, height));
		return hitboxes;
	}

}
