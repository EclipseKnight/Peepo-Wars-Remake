package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import game.audio.AudioPlayer;
import game.handlers.CrashHandler;
import game.handlers.InputHandler;
import game.handlers.LevelHandler;
import game.handlers.RenderHandler;
import game.handlers.UpdateHandler;
import game.handlers.WindowHandler;
import game.levels.Level;
import game.levels.Level0;
import game.levels.Level1;
import game.levels.Level2;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;
import resources.ResourceLoader;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 5106334414276794990L;
	
	//Window 
	public static final float GAME_MIN_WIDTH = 0;
	public static final float GAME_MAX_WIDTH = 1000;
	public static final float GAME_MIN_HEIGHT = 0;
	public static final float GAME_MAX_HEIGHT = 700;
	//In game borders.
	public static final int GAME_MIN_BORDER = 20;
	public static final int GAME_MAX_BORDER = 450;
	
	public static final float GAME_SCALE = 1;
	public static final Dimension DIMENSIONS = new Dimension((int) (GAME_MAX_WIDTH * GAME_SCALE), (int) (GAME_MAX_HEIGHT * GAME_SCALE));
	public static final String NAME = "PeepoWars";
	public JFrame frame;
	public Thread thread;
	
	private BufferedImage image = new BufferedImage((int) GAME_MAX_WIDTH, (int) GAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public boolean running = false;
	public int tickCount;
	public int gameState = 1; // 0=Main Menu 1=Game 2=Win 3=Lose 4=Won Level
	public InputHandler input;
	public RenderHandler renderer;
	public UpdateHandler updater;
	public LevelHandler leveler;
	public WindowHandler windowHandler;
	public AudioPlayer audioPlayer;
	public Font gameFont;
	public static boolean showhitbox = false;
	public static String fps = "0", tps = "0";
	
	public List<Player> players;
	public List<Boss> bosses;
	public List<Mob> mobs;
	public List<Level> levels;
	public Level level = null;
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, NAME + "_main");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
	}
	
	
	public void tick() {
		
	
		
		if(gameState == 1) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateMobs();
			updater.updateBosses();
			updater.updateProjectiles();
			updater.checkAmmunitionCollision();
			updater.checkAttackCollision();
			updater.checkPlayerCollision();
		}
		
		if(gameState == 2) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateProjectiles();
		}
		
		if(gameState == 3) {
			leveler.updateLevel();
			updater.updateMobs();
			updater.updateBosses();
			updater.updateProjectiles();
		}
		
		if(gameState == 4) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateProjectiles();
		}
		
		
		
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
	
//		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setFont(gameFont.deriveFont(11f));
		
		//If no background is being rendered the canvas will be flashing, this prevents it.
		renderer.renderBuffer(g2d, image);
		renderer.renderBackground(g2d);
		renderer.renderFPSAndTPSCounter(g2d);
		if(level != null) {
			renderer.renderMobs(g2d, level.getMobs());
			renderer.renderBosses(g2d, level.getBosses());
			renderer.renderPlayers(g2d, level.getPlayers());
			renderer.renderProjectiles(g2d);
			renderer.renderBorder(g2d);
			renderer.renderUI(g2d);
			renderer.renderWeaponBar(g2d);
			renderer.renderHealthBar(g2d);
		}
		
		if(gameState == 2) {
			renderer.renderWin(g2d);
		}
		
		if(gameState == 3) {
			renderer.renderLose(g2d);
		}
		
		if(gameState == 4) {
			renderer.renderLevelWin(g2d);
		}
		g2d.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		
		init();
		long lastTime = System.nanoTime();
		//how many nano-seconds are in one tick.
		double nsPerTick = 1_000_000_000D/60;
		
		int ticks = 0;
		int frames = 0;
		
		//will track a second for fps and tps
		long lastTimer = System.currentTimeMillis();
		double delta = 0;
		
		while(running) {
			
			
			long now = System.nanoTime();
			delta += (now - lastTime)/nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			while(delta >= 1) {
				ticks++;
				tick();
				frames++;
				render();
				delta -= 1;
				shouldRender = true;
			}
//			try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			
			//uncap fps, else place render() in delta loop.
			if(shouldRender) {

				
				
			}
			
			if(System.currentTimeMillis()-lastTimer >= 1000) {
				lastTimer += 1000;
				fps = String.valueOf(frames);
				tps = String.valueOf(ticks);
				frames = 0;
				ticks = 0;
				
			}
		}
	}
	
	private void init() {
		initResources();
		initAudioPlayer();
		input = new InputHandler(this);
		renderer = new RenderHandler(this);
		updater = new UpdateHandler(this);
		leveler = new LevelHandler(this);
		windowHandler = new WindowHandler(this);
		initLevels();
		
	}
	
	private void initResources() {
		try {
			gameFont = Font.createFont(Font.TRUETYPE_FONT, ResourceLoader.loadStream("fonts/prstartk.ttf")).deriveFont(30f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			CrashHandler.throwError(e.toString());
		}
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(gameFont);
	}
	
	private void initAudioPlayer() {
		audioPlayer = new AudioPlayer();
		audioPlayer.play();
	}
	
	private void initLevels() {
		levels = new ArrayList<>();
		levels.add(new Level0(this));
		levels.add(new Level1(this));
		levels.add(new Level2(this));
		
		//Starting level
		level = levels.get(0);
		level.addPlayer(new Player(30, Game.GAME_MAX_BORDER/2, "Eclipse", input));
	}

}
