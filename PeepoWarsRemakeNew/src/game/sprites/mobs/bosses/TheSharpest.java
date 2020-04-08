package game.sprites.mobs.bosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.Game;
import game.handlers.AttackCooldownHandler;
import game.handlers.SpriteHandler;
import game.sprites.AnimationState;
import game.sprites.hitbox.CircleHitBox;
import game.sprites.hitbox.HitBox;
import game.sprites.hitbox.RectangleHitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.attacks.PhantomCharge;
import game.sprites.mobs.attacks.PhantomTriCharge;
import game.sprites.mobs.players.Player;
/**
 * The second boss of Peepo Wars.
 * @author Eclipse
 *
 */
public class TheSharpest extends Boss {

	public static final float HEALTH = 1500;
	public static final float MAXHEALTH = 1500;
	public static final float SPEED = 2.5f;
	public static final String NAME = "The Sharpest";
	private AttackCooldownHandler cooler;
	private int tickCount = 0;
	private int playerToFollow = 0;
	
	public TheSharpest(float x, float y) {
		super(x, y, HEALTH, MAXHEALTH, SPEED, NAME, true);
		this.cooler = new AttackCooldownHandler();
		initGFX();
	}
	
	private void initGFX() {
		animation = SpriteHandler.getBossAnimation("TheSharpestIdle");
		animationState = new AnimationState(animation);
		setImage(animation.getSprite());
		getImageDimensions(.75);
	}
	
	@Override
	public void tick() {
		cooler.reduceCooldown();
		animate();
		move();
		attack();
	}
	
	@Override
	public void animate() {
		animationState.update();
		setImage(animation.getSprite(animationState.getCurrentFrame()));
	}

	private void attack() {
		
		
		var atkSpeed = health <= maxHealth/2 ? 2 : 1;
		
		if(cooler.getPhantomChargeCooldown() / atkSpeed <= 0) {
			var attack = new PhantomCharge(x, y, playerToFollow);
			attacks.add(attack);
			cooler.resetCooldown(attack);
		}
		
		if(cooler.getPhantomTriChargeCooldown() <= 0) {
			
			var attack2 = new PhantomTriCharge(x, Game.GAME_MAX_BORDER * .5f - 150);
			var attack1 = new PhantomTriCharge(x, attack2.getY() - 150);
			var attack3 = new PhantomTriCharge(x, attack2.getY() + 150);
			attacks.add(attack1);
			attacks.add(attack2);
			attacks.add(attack3);
			cooler.resetCooldown(attack1);
			cooler.setCooldown(30, "PhantomCharge");
		}
	}
	
	private void move() {
		tickCount++;
		
		var playerY = Game.level.getPlayers().get(playerToFollow).getY();
		var rand = new Random();
		
		if(tickCount > 240) {
			tickCount = 0;
			
			playerToFollow = rand.nextInt(Game.level.getPlayers().size());
			playerY = Game.level.getPlayers().get(playerToFollow).getY();
		}
		
		if(playerY > y + height * .5f && playerY - (y + height * .5f) > speed) {
			y = y + speed;
		} else if(playerY < y + height * .5f && (y + height * .5f) - playerY > speed) {
			y = y - speed;
		}
	}

	@Override
	public void damage(float damage) {
		health -= damage;
		if(health <= 0) {
			setVisible(false);
		}
	}
	
	@Override
	public void damage(Player player, float damage) {
		player.damage(damage);
	}

	public List<Attack> getAttacks() {
		return attacks;
	}
	
	@Override
	public List<HitBox> getHitBox() {
		var hitboxes = new ArrayList<HitBox>();
		hitboxes.add(new CircleHitBox(x + 40, y + height * .35f, width * .1f));
		hitboxes.add(new CircleHitBox(x + 10, y + height * .5f, width * .10f));
		hitboxes.add(new RectangleHitBox(x + 40, y + height *.45f, width * .4f, height * .15f));
		return hitboxes;
	}

	

}
