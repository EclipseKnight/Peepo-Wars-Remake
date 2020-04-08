package game.handlers;

import java.util.Iterator;
import java.util.List;

import game.Game;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.players.Player;

/**
 * Handles all of the updating
 * @author Eclipse
 *
 */
public class UpdateHandler {

	private Game game;
	
	public UpdateHandler(Game game) {
		this.game = game;
	}
	
	/**
	 * Updates the {@linkplain Player}s.
	 */
	public void updatePlayers() {
		for(Player p : Game.level.getPlayers()) {
			if(p.getHealth() > 0) {
				p.tick();
			}
		}
	}
	
	/**
	 * Updates the {@linkplain Mobs}.
	 */
	public void updateMobs() {
		Iterator<Mob> it;
		it = Game.level.getMobs().iterator();
		Mob m = null;
		while(it.hasNext()) {
			m = it.next();
			if(m.isVisible()) {
				m.tick();
			} else {
				it.remove();
			}
		}
	}
	
	/**
	 * Updates the {@linkplain Boss}es.
	 */
	public void updateBosses() {
		Iterator<Boss> it;
		it = Game.level.getBosses().iterator();
		Boss b = null;
		while(it.hasNext()) {
			b = it.next();
			if(b.isVisible()) {
				b.tick();
			} else {
				it.remove();
			}
		}
	}
	
	/**
	 * Updates the projectliles ({@linkplain Ammunition}, {@linkplain Attack}).
	 */
	public void updateProjectiles() {
		
		Iterator<?> it1;
		List<Ammunition> ammunition = null;

		for(Player p : Game.level.getPlayers()) {
			ammunition = p.getAmmoFired();
			it1 = ammunition.iterator();
			
			while(it1.hasNext()) {
				Ammunition ammo = (Ammunition) it1.next();
				if(ammo.isVisible()) {
					ammo.tick();
				} else {
					it1.remove();
				}
			}
		}
		
		List<Attack> attacks = null;
		
		for(Boss b : Game.level.getBosses()) {
			attacks = b.getAttacks();
			it1 = attacks.iterator();
			
			while(it1.hasNext()) {
				Attack attack = (Attack) it1.next();
				if(attack.isVisible()) {
					attack.tick();
				} else {
					it1.remove();
				}
			}
		}
	}
	
	/**
	 * Checks if player has collided another {@linkplain HitBox}.
	 */
	public void checkPlayerCollision() {
		for(Player p : Game.level.getPlayers()) {
			
			//Player collides with boss.
			for(Boss b : Game.level.getBosses()) {
				for(HitBox bhb : b.getHitBox()) {
					for(HitBox phb : p.getHitBox()) {
						if(phb.intersects(bhb)) {
							b.damage(p, 1);
						}
					}
				}
			}
			
			
			//Player collides with mob.
			for(Mob m : Game.level.getMobs()) {
				for(HitBox mhb : m.getHitBox()) {
					for(HitBox phb : p.getHitBox()) {
						if(phb.intersects(mhb)) {
							m.damage(p, 1);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks if the player's projectiles ({@linkplain Ammunition}) have collided with an enemy.
	 */
	public void checkAmmunitionCollision() {
		for(Player p : Game.level.getPlayers()) {
			for(Ammunition a : p.getAmmoFired()) {
				for(HitBox ahb : a.getHitBox()) {
					
					//Player shooting boss.
					for(Boss b : Game.level.getBosses()) {
						for(HitBox bhb : b.getHitBox()) {
							if(ahb.intersects(bhb)) {
								
								var multi = 1f;
								if(p.isDemonMode())
									multi = 1.5f;
								if(p.isShieldMode())
									multi = .75f;
								
								a.damage(b, a.getDamage() * multi);
							}
						}
					}
					
					//Player shooting mobs.
					for(Mob m : Game.level.getMobs()) {
						for(HitBox mhb : m.getHitBox()) {
							if(mhb.intersects(ahb)) {

								var multi = 1f;
								if(p.isDemonMode())
									multi = 1.5f;
								if(p.isShieldMode())
									multi = .75f;
								
								a.damage(m, a.getDamage() * multi);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Checks if a boss's {@linkplain Attack} has collided with the player
	 */
	public void checkAttackCollision() {
		for(Boss b : Game.level.getBosses()) {
			for(Attack a : b.getAttacks()) {
				for(HitBox ahb : a.getHitBox()) {
					for(Player p : Game.level.getPlayers()) {
						for(HitBox phb : p.getHitBox()) {
							if(phb.intersects(ahb)) {
								a.damage(p, a.getDamage());
							}
						}
					}
				}
			}
		}
	}
}
