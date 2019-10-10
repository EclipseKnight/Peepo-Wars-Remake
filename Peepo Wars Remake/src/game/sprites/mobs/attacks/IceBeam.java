package game.sprites.mobs.attacks;

import java.util.ArrayList;
import java.util.List;

import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.HitBox.HitBoxType;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.players.Player;

public class IceBeam extends Attack {

	private static final float VELOCITY = 0;
	private static final float DAMAGE = 10;
	public static final int COOLDOWN = 1000;
	private Boss boss;
	
	private int tickCount = 0;
	
	public IceBeam(float x, float y, Boss boss) {
		super(x, y, AttackType.ICEBEAM, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN);
		this.boss = boss;
		initGFX();
	}
	
	private void initGFX() {
		loadImage("animations/attacks/icebeamfix2.gif");
		getImageDimensions(.5);
	}

	@Override
	public void move() {
		tickCount++;
		y = boss.getY();
		
		if(tickCount >= 480) {
			setVisible(false);
			tickCount = 0;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		if(tickCount >= 220 && tickCount <= 360) {
			hitboxes.add(new RectangleHitBox(x, (y + height *.15f), (width) , (height/2)));
		}
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		player.damage(damage);
		
	}

}
