package game.handlers;

import game.sprites.mobs.ammunition.Ammunition;
import game.sprites.mobs.ammunition.Laser;
import game.sprites.mobs.ammunition.Missile;

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
	
	public int getLaserCooldown() {
		return laserCooldown;
	}
	
	public int getMissileCooldown() {
		return missileCooldown;
	}
	
	public void resetCooldown(Ammunition ammo) {
		
		switch(ammo.getAmmoType()) {
		case LASER: 
			laserCooldown = ammo.getCooldown();
			break;
		case MISSILE:
			missileCooldown = ammo.getCooldown();
			break;
//		switch(ammo.getAmmoType()) {
//		
//		case LASER -> laserCooldown = ammo.getCooldown();
//		case MISSILE -> missileCooldown = ammo.getCooldown();
//		
//		default -> throw new IllegalArgumentException("Unexpected value: " + ammo.getAmmoType());
		}
	}
}
