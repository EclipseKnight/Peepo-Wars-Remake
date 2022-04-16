package game.handlers;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import game.sprites.Animation;
import game.sprites.SpriteSheet;
import resources.ResourceLoader;

/**
 * Handles preloading the sprites and caching them to be used later.
 * @author Eclipse
 *
 */
public class SpriteHandler {

	// Mobs
	private static final HashMap<String, BufferedImage> mobs = new HashMap<>();
	
	// Bosses
	private static final HashMap<String, Animation> bossAnimations = new HashMap<>();

	// Players
	private static final HashMap<String, BufferedImage> peepos = new HashMap<>();
	private static final HashMap<String, Animation> demonAnimations = new HashMap<>();
	private static final HashMap<String, Animation> shieldAnimations = new HashMap<>();
	
	// Attacks
	private static final HashMap<String, Animation> attackAnimations = new HashMap<>();

	// Ammunition
	private static final HashMap<String, Animation> ammunitionAnimations = new HashMap<>();
	
	// Icons
	private static final HashMap<String, BufferedImage> icons = new HashMap<>();

	public SpriteHandler() {
		preload();
	}

	private void preload() {
		loadMobs();
		loadBosses();
		loadPlayers();
		loadAttacks();
		loadAmmunition();
		loadIcons();
	}

	private void loadMobs() {
		mobs.put("ContinueButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/continue.png"), 1, 1, 1, 50, 15).getSprite(1));
		mobs.put("RestartButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/restart.png"), 1, 1, 1, 38, 15).getSprite(1));
		mobs.put("MainMenuButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/mainmenu.png"), 1, 1, 1, 52, 15).getSprite(1));
		mobs.put("SettingsButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/settingscog.png"), 1, 1, 1, 52, 15).getSprite(1));
		mobs.put("QuitButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/quit.png"), 1, 1, 1, 35, 15).getSprite(1));
		mobs.put("PeepoWarsLogo", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/peepo_wars_logo_big.png"), 1, 1, 1, 185, 165).getSprite(1));
		mobs.put("JoinButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/join.png"), 1, 1, 1, 27, 15).getSprite(1));
		mobs.put("HostButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/host.png"), 1, 1, 1, 29, 15).getSprite(1));
		mobs.put("MultiplayerButton", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/multiplayer.png"), 1, 1, 1, 52, 15).getSprite(1));
	}
	
	private void loadBosses() {
		bossAnimations.put("StrobeKing", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/mobs/strobekingsheet.png"), 9, 4, 3, 112, 112).getSprites(), 3, true));
		
		bossAnimations.put("TheSharpestIdle", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/mobs/thesharpestidlesheet.png"), 30, 5, 6, 400, 400).getSprites(), 3, true));
		
	}
	
	private void loadPlayers() {
		demonAnimations.put("DemonBlue", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/demonblue.png"), 6, 4, 2, 458, 715).getSprites(), 3, true));
		demonAnimations.put("DemonRed", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/demonred.png"), 6, 4, 2, 458, 715).getSprites(), 3, true));
		demonAnimations.put("DemonGreen", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/demongreen.png"), 6, 4, 2, 458, 715).getSprites(), 3, true));
		demonAnimations.put("DemonOrange", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/demonorange.png"), 6, 4, 2, 458, 715).getSprites(), 3, true));
		
		shieldAnimations.put("ShieldBlue", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/shieldbluesheet.png"), 12, 4, 3, 222, 194).getSprites(), 3, true));
		shieldAnimations.put("ShieldRed", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/shieldredsheet.png"), 12, 4, 3, 222, 194).getSprites(), 3, true));
		shieldAnimations.put("ShieldGreen", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/shieldgreensheet.png"), 12, 4, 3, 222, 194).getSprites(), 3, true));
		shieldAnimations.put("ShieldOrange", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/shieldorangesheet.png"), 12, 4, 3, 222, 194).getSprites(), 3, true));
		
		peepos.put("Normal", new SpriteSheet(ResourceLoader.loadBufferedImage("entities/peeponormal.png"), 1, 1, 1, 9, 7).getSprite(1));
		peepos.put("Demon", new SpriteSheet(ResourceLoader.loadBufferedImage("entities/peepodemon.png"), 1, 1, 1, 9, 7).getSprite(1));
		
	}
	
	private void loadAttacks() {
		attackAnimations.put("IceFist", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("animations/attacks/icefist.png"), 4, 4, 1, 314, 120).getSprites(), 3, true));
		attackAnimations.put("IceWave", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("animations/attacks/icewave.png"), 4, 4, 1, 126, 193).getSprites(), 3, true));
		//TODO IceBeam has padding
		attackAnimations.put("IceBeam", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("animations/attacks/icebeam.png"), 94, 4, 23, 1200, 653).getSprites(), 3, false));
		attackAnimations.put("PhantomCharge", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("entities/mobs/thesharpestchargesheet2.png"), 30, 5, 6, 400, 400).getSprites(), 3, true));
	}
	
	private void loadAmmunition() {
		ammunitionAnimations.put("Laser", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("ammunition/lasersheet.png"), 3, 2, 2, 1701, 618).getSprites(), 3, true));
		ammunitionAnimations.put("Missile", new Animation(new SpriteSheet(ResourceLoader.loadBufferedImage("ammunition/missilesheet.png"), 3, 1, 3, 563, 139).getSprites(), 3, true));
	}
	
	private void loadIcons() {
		icons.put("MusicOn", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/soundon.png"), 1, 1, 1, 18, 15).getSprite(1));
		icons.put("MusicOff", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/soundoff.png"), 1, 1, 1, 18, 15).getSprite(1));
		icons.put("LockOn", new SpriteSheet(ResourceLoader.loadBufferedImage("icons/lockon.png"), 1, 1, 1, 37, 37).getSprite(1));
	}

	public static Animation getShieldAnimation(String name) {
		for(Map.Entry<String, Animation> e : shieldAnimations.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static Animation getDemonAnimation(String name) {
		for(Map.Entry<String, Animation> e : demonAnimations.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static BufferedImage getPeepoImage(String name) {
		for(Map.Entry<String, BufferedImage> e : peepos.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static Animation getBossAnimation(String name) {
		for(Map.Entry<String, Animation> e : bossAnimations.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static BufferedImage getMobImage(String name) {
		for(Map.Entry<String, BufferedImage> e : mobs.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static Animation getAttackAnimation(String name) {
		for(Map.Entry<String, Animation> e : attackAnimations.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static Animation getAmmunitionAnimation(String name) {
		for(Map.Entry<String, Animation> e : ammunitionAnimations.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}
	
	public static BufferedImage getIconImage(String name) {
		for(Map.Entry<String, BufferedImage> e : icons.entrySet()) {
			if(e.getKey().equalsIgnoreCase(name)) {
				return e.getValue();
			}
		}
		System.err.println("No key called " + name + " was found.");
		return null;
	}







	

}
