package game.handlers;

import game.sprites.mobs.attacks.Attack;
import game.sprites.mobs.attacks.IceBeam;
import game.sprites.mobs.attacks.IceFist;
import game.sprites.mobs.attacks.IceWave;

public class AttackCooldownHandler {

	private int iceFistCooldown;
	private int iceWaveCooldown;
	private int iceBeamCooldown;
	public AttackCooldownHandler() {
		iceFistCooldown = IceFist.COOLDOWN;
		iceWaveCooldown = IceWave.COOLDOWN;
		iceBeamCooldown = IceBeam.COOLDOWN;
	}
	
	public void reduceCooldown() {
		if(iceFistCooldown > 0)
			iceFistCooldown--;
		if(iceWaveCooldown > 0)
			iceWaveCooldown--;
		if(iceBeamCooldown > 0) {
			iceBeamCooldown--;
		}
	}
	
	public int getIceFistCooldown() {
		return iceFistCooldown;
	}
	
	public int getIceWaveCooldown() {
		return iceWaveCooldown;
	}
	
	public int getIceBeamCooldown() {
		return iceBeamCooldown;
	}
	
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
		}
	}
}
