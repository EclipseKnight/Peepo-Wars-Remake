package game.handlers;

import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;

/**
 * Handles the cooldowns for the player ammos
 * @author jpaqu
 *
 */
public class CooldownHandler {
	private int laserCooldown;
	private int missileCooldown;
	
	public CooldownHandler() {
		laserCooldown = Laser.COOLDOWN;
		missileCooldown = Missile.COOLDOWN;
		
	}
	
	public void reduceCooldown() {
		if(laserCooldown > 0)
			laserCooldown--;
		if(missileCooldown > 0)
			missileCooldown--;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getLaserCooldown() {
		return laserCooldown;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getMissileCooldown() {
		return missileCooldown;
	}
	
	/**
	 * Resets the cooldown of the ammo
	 * @param ammo 
	 */
	public void resetCooldown(Ammunition ammo) {
		
		switch(ammo.getAmmoType()) {
		case LASER: 
			laserCooldown = ammo.getCooldown();
			break;
			
		case MISSILE:
			missileCooldown = ammo.getCooldown();
			break;
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + ammo.getAmmoType());
		}
	}
}
