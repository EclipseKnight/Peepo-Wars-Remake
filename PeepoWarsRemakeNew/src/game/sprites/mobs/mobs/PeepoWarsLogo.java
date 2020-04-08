package game.sprites.mobs.mobs;

import java.util.ArrayList;
import java.util.List;

import game.handlers.SpriteHandler;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;

public class PeepoWarsLogo extends Mob {
	
	public static final float HEALTH = 10;
	public static final float MAXHEALTH = 10;
	public static final float SPEED = 1f;
	public static final String NAME = "Peepo Wars";
	
	private boolean moveUp = false;
	private int tickCount = 0;
	public PeepoWarsLogo(float x, float y) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, false);
		initGFX();
	}
	
	private void initGFX() {
		setImage(SpriteHandler.getMobImage("PeepoWarsLogo"));
		getImageDimensions(1);
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
		
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		
	}

	

}
