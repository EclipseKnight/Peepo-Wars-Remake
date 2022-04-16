package game.handlers;

import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.attacks.IceBeam;
import game.sprites.mobs.attacks.IceFist;
import game.sprites.mobs.attacks.IceWave;
import game.sprites.mobs.attacks.PhantomCharge;
import game.sprites.mobs.attacks.PhantomTriCharge;

/**
 * Handles the cooldowns of Boss {@linkplain Attack}s.
 * @author Eclipse
 *
 */
public class AttackCooldownHandler {

	//StrobeKing
	private int iceFistCooldown;
	private int iceWaveCooldown;
	private int iceBeamCooldown;
	
	//TheSharpest
	private int phantomChargeCooldown;
	private int phantomTriChargeCooldown;
	
	public AttackCooldownHandler() {
		iceFistCooldown = IceFist.COOLDOWN;
		iceWaveCooldown = IceWave.COOLDOWN;
		iceBeamCooldown = IceBeam.COOLDOWN;
		phantomChargeCooldown = PhantomCharge.COOLDOWN;
		phantomTriChargeCooldown = PhantomTriCharge.COOLDOWN;
	}
	
	/**
	 * Reduce the cooldown of all attacks
	 */
	public void reduceCooldown() {
		
		//StrobeKing
		if (iceFistCooldown > 0)
			iceFistCooldown--;
		if (iceWaveCooldown > 0)
			iceWaveCooldown--;
		if (iceBeamCooldown > 0)
			iceBeamCooldown--;
		
		//TheSharpest
		if (phantomChargeCooldown > 0)
			phantomChargeCooldown--;
		if (phantomTriChargeCooldown > 0) 
			phantomTriChargeCooldown--;
		
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getIceFistCooldown() {
		return iceFistCooldown;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getIceWaveCooldown() {
		return iceWaveCooldown;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getIceBeamCooldown() {
		return iceBeamCooldown;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getPhantomChargeCooldown() {
		return phantomChargeCooldown;
	}
	
	/**
	 * Gets the remaining ticks of the cooldown
	 * @return An int representing the remaining ticks of the cooldown
	 */
	public int getPhantomTriChargeCooldown() {
		return phantomTriChargeCooldown;
	}
	
	/**
	 * Resets the cooldown of the attack
	 * @param attack
	 */
	public void resetCooldown(Attack attack) {
		
		switch(attack.getAttackType()) {
		case ICEFIST:
			iceFistCooldown = attack.getCooldown();
			break;
		case ICEWAVE:
			iceWaveCooldown = attack.getCooldown();
			break;
		case ICEBEAM:
			iceBeamCooldown = attack.getCooldown();
			break;
		case PHANTOMCHARGE:
			phantomChargeCooldown = attack.getCooldown();
			break;
		case PHANTOMTRICHARGE:
			phantomTriChargeCooldown = attack.getCooldown();
			break;
			
		default:
			CrashHandler.throwError("Failed to reset " + attack.getName() + " cooldown");
		}
	}
	
	/**
	 * sets the cooldown of the attack
	 * @param cooldown
	 * @param attack
	 */
	public void setCooldown(int cooldown, String attack) {
		
		switch(attack) {
		case "IceFist":
			iceFistCooldown = cooldown;
			break;
		case "IceWave":
			iceWaveCooldown = cooldown;
			break;
		case "IceBeam":
			iceBeamCooldown = cooldown;
			break;
		case "PhantomCharge":
			phantomChargeCooldown = cooldown;
			break;
		case "PhantomTriCharge":
			phantomTriChargeCooldown = cooldown;
			break;
			
		default:
			CrashHandler.throwError("Failed to set " + attack + " cooldown");
		}
	}
}
