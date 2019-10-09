package game.sprites.mobs.mobs;

import java.util.ArrayList;
import java.util.List;

import game.levels.Level0;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Mob;

public class QuitButton extends Mob {
	
	public static final float HEALTH = 1;
	public static final float MAXHEALTH = 1;
	public static final float SPEED = 3f;
	public static final String NAME = "Quit";
	private Level0 level;
	
	private int tickCount = 0;
	private boolean moveUp = false;
	
	public QuitButton(float x, float y, Level0 level) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, false);
		this.level = level;
		initGFX();
	}
	
	private void initGFX() {
		loadImage("icons/quit.png");
		getImageDimensions(3);
	}

	@Override
	public void tick() {
		tickCount++;
		
		if(moveUp && tickCount % 60 == 0) {
			y += speed;
			moveUp = false;
			
			tickCount = 0;
			return;
		}
		
		if(!moveUp && tickCount % 60 == 0) {
			y -= speed;
			moveUp = true;
			
			tickCount = 0;
			return;
		}
	}

	@Override
	public void damage(float damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
			level.doQuit = true;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox(x, y, width, height));
		return hitboxes;
	}

}
