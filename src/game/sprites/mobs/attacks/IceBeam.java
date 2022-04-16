package game.sprites.mobs.attacks;

import java.util.ArrayList;
import java.util.List;

import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
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
		super(x, y, AttackType.ICEBEAM, HitBoxType.RECTANGLE, VELOCITY, DAMAGE, COOLDOWN, "IceBeam");
		this.boss = boss;
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getAttackAnimation("IceBeam");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite());
		getImageDimensions(.5);
	}

	@Override
	public void tick() {
		tickCount++;
		animate();
		move();
	}
	
	@Override
	public void animate() {
		animationState.update();
		setImage(animation.getSprite(animationState.getCurrentFrame()));
	}
	
	@Override
	public void move() {
		
		y = boss.getY();
		
		if (tickCount >= 480) {
			setVisible(false);
			tickCount = 0;
		}
	}

	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		if (tickCount >= 220 && tickCount <= 360) {
			hitboxes.add(new RectangleHitBox(x, (y + height *.15f), (width) , (height/2)));
		}
		return hitboxes;
	}

	@Override
	public void damage(Player player, float damage) {
		player.damage(damage);
		
	}

	

}
