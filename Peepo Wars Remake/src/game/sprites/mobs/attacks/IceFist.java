package game.sprites.mobs.attacks;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.mobs.players.Player;
import game.sprites.hitbox.RectangleHitBox;

public class IceFist extends Attack {

	private static final float VELOCITY = 8;
	private static final float DAMAGE = 25;
	public static final int COOLDOWN = 40;
	
	private boolean hasHitPlayer = false;
	
	public IceFist(float x, float y) {
		super(x, y, AttackType.ICEFIST, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		initGFX();
	}
	
	private void initGFX() {
		loadImage("animations/attacks/icefist.gif");
		getImageDimensions(.6);
	}
	
	@Override
	public void move() {
		
		x -= velocity;
		
		if(x < Game.GAME_MIN_WIDTH - width) {
			visible = false;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new RectangleHitBox((x + width * .05f), (y + height * .05f), (width * .7f) , (height * .85f)));
		
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		if(!hasHitPlayer) {
			player.damage(damage);
			hasHitPlayer = true;
			setVisible(false);
		}
		
		
	}

}
