package game.handlers;

import java.util.Iterator;
import java.util.List;

import game.Game;
import game.sprites.hitbox.HitBox;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.Player;
import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.attacks.Attack;

public class UpdateHandler {

	private Game game;
	
	public UpdateHandler(Game game) {
		this.game = game;
	}
	
	public void updatePlayers() {
		for(Player p : game.level.getPlayers()) {
			p.tick();
		}
	}
	
	public void updateMobs() {
		Iterator<Mob> it;
		it = game.level.getMobs().iterator();
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
	
	public void updateBosses() {
		Iterator<Boss> it;
		it = game.level.getBosses().iterator();
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
	
	public void updateProjectiles() {
		
		Iterator<?> it1;
		List<Ammunition> ammunition = null;

		for(Player p : game.level.getPlayers()) {
			ammunition = p.getAmmoFired();
			it1 = ammunition.iterator();
			
			while(it1.hasNext()) {
				Ammunition ammo = (Ammunition) it1.next();
				if(ammo.isVisible()) {
					ammo.move();
				} else {
					it1.remove();
				}
			}
		}
		
		List<Attack> attacks = null;
		
		for(Boss b : game.level.getBosses()) {
			attacks = b.getAttacks();
			it1 = attacks.iterator();
			
			while(it1.hasNext()) {
				Attack attack = (Attack) it1.next();
				if(attack.isVisible()) {
					attack.move();
				} else {
					it1.remove();
				}
			}
		}
	}
	
	public void win() {
		game.gameState = 2;
	}
	
	public void lose() {
		game.gameState = 3;
	}
	
	public void checkPlayerCollision() {
		for(Player p : game.level.getPlayers()) {
			for(Boss b : game.level.getBosses()) {
				for(HitBox bhb : b.getHitBox()) {
					if(p.getHitBox().get(0).intersects(bhb)) {
						p.damage(1);
					}
				}
			}
			
			for(Mob m : game.level.getMobs()) {
				for(HitBox mhb : m.getHitBox()) {
					if(p.getHitBox().get(0).intersects(mhb)) {
						p.damage(1);
					}
				}
			}
		}
	}
	
	public void checkAmmunitionCollision() {
		for(Player p : game.level.getPlayers()) {
			for(Ammunition a : p.getAmmoFired()) {
				for(HitBox ahb : a.getHitBox()) {
					
					//Player shooting boss.
					for(Boss b : game.level.getBosses()) {
						for(HitBox bhb : b.getHitBox()) {
							if(ahb.intersects(bhb)) {
								a.damage(b, a.getDamage());
							}
						}
					}
					
					//Player shooting mobs.
					for(Mob m : game.level.getMobs()) {
						for(HitBox mhb : m.getHitBox()) {
							if(mhb.intersects(ahb)) {
								a.damage(m, a.getDamage());
							}
						}
					}
				}
			}
		}
	}
	
	public void checkAttackCollision() {
		for(Boss b : game.level.getBosses()) {
			for(Attack a : b.getAttacks()) {
				for(HitBox ahb : a.getHitBox()) {
					for(Player p : game.level.getPlayers()) {
						if(ahb.intersects(p.getHitBox().get(0))) {
							a.damage(p, a.getDamage());
						}
					}
				}
			}
		}
	}
}
