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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import game.audio.AudioPlayer;
import game.handlers.CrashHandler;
import game.handlers.InputHandler;
import game.handlers.LevelHandler;
import game.handlers.RenderHandler;
import game.handlers.SpriteHandler;
import game.handlers.TimerHandler;
import game.handlers.UpdateHandler;
import game.handlers.WindowHandler;
import game.levels.Level;
import game.levels.Level1;
import game.levels.Level2;
import game.levels.Level3;
import game.levels.LevelMainMenu;
import game.levels.LevelMultiplayer;
import game.levels.LevelSettings;
import game.levels.LevelUsername;
import game.sprites.mobs.Boss;
import game.sprites.mobs.Mob;
import game.sprites.mobs.players.Player;
import resources.ResourceLoader;

/**
 * The game.
 * @author Eclipse
 *
 */
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
	public static final String NAME = "Peepo Wars";
	public JFrame frame;
	public Thread thread;
	
	private BufferedImage image = new BufferedImage((int) GAME_MAX_WIDTH, (int) GAME_MAX_HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public boolean running = false;
	public int gameState = 1; // 0=Main Menu, 1=Game Running, 2=Win Level, 3=Win Game, 4=Lost Game
	public InputHandler input;
	public RenderHandler renderer;
	public UpdateHandler updater;
	public LevelHandler leveler;
	public TimerHandler timer;
	public WindowHandler windowHandler;
	public AudioPlayer audioPlayer;
	public SpriteHandler spriteHandler;
	
	/*
	public GameClient client;
	public Thread clientThread;
	public GameServer server;
	public Thread serverThread;
	*/
	public Font gameFont;
	public static boolean showhitbox = false;
	public static String fps = "0", tps = "0";
	
	/**
	 * Whether frame rate is capped or not
	 */
	public static boolean uncappedFrameRate = false;
	
	/**
	 * The tick rate of the game.
	 */
	private final double UPDATE_CAP = 1.0/60.0;
	
	/**
	 * The frame rate of the game.
	 */
	private final double RENDER_CAP = 1.0/144.0;
	
	public List<Player> players;
	public List<Boss> bosses;
	public List<Mob> mobs;
	public List<Level> levels;
	public static Level level = null;
	
	/**
	 * Starts the game thread.
	 */
	public void start() {
		running = true;
		thread = new Thread(this, NAME + "_main");
		thread.start();
	}
	
	/**
	 * Stops the game.
	 */
	public void stop() {
		running = false;
	}
	
	/**
	 * The update method (tps).
	 */
	public void tick() {
		
//		if (client.isConnected()) {
//			client.sendData(new );
//		}
		
		
		if (gameState == 1) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateMobs();
			updater.updateBosses();
			updater.updateProjectiles();
			updater.checkAmmunitionCollision();
			updater.checkAttackCollision();
			updater.checkPlayerCollision();
		}
		
		if (gameState == 2) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateProjectiles();
			updater.checkAmmunitionCollision();
			updater.updateMobs();
		}
		
		if (gameState == 3) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateProjectiles();
			updater.checkAmmunitionCollision();
			updater.updateMobs();
		}
		
		if (gameState == 4) {
			leveler.updateLevel();
			updater.updatePlayers();
			updater.updateProjectiles();
			updater.checkAmmunitionCollision();
			updater.updateMobs();
			updater.updateBosses();
		}
	}
	
	/**
	 * The render method (fps).
	 */
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g2d = (Graphics2D) bs.getDrawGraphics();
	
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		g2d.setFont(gameFont.deriveFont(11f));
		
		//If no background is being rendered the canvas will be flashing, this prevents it.
		renderer.renderBuffer(g2d, image);
		
		renderer.renderBackground(g2d);
		renderer.renderFPSAndTPSCounter(g2d);
		if (level != null) {
			renderer.renderLevel(g2d);
			renderer.renderBosses(g2d, level.getBosses());
			renderer.renderMobs(g2d, level.getMobs());
			renderer.renderPlayers(g2d, level.getPlayers());
			renderer.renderSprites(g2d, level.getSprites());
			renderer.renderEffects(g2d, level.getEffects());
			renderer.renderProjectiles(g2d);
			renderer.renderBorder(g2d);
			renderer.renderUI(g2d);
			renderer.renderWeaponBar(g2d);
			renderer.renderHealthBar(g2d);
		}
		
		if (gameState == 2) {
			renderer.renderLevelWin(g2d);
		}
		
		if (gameState == 3) {
			renderer.renderWin(g2d);
		}
		
		if (gameState == 4) {
			renderer.renderLose(g2d);
		}
		
		g2d.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		
		init();
		
		double nsConversion = 1_000_000_000D;
		
		double firstTime = 0;
		double lastTime = System.nanoTime() / nsConversion;
		double passedTime = 0;
		double unprocessedUpdateTime = 0;
		double unprocessedFrameTime = 0;
		boolean render = false;
		
		int ticks = 0;
		int frames = 0;
		long lastTimer = System.currentTimeMillis();
		
		while(running) {
			
			render = false;
			
			firstTime = System.nanoTime() / nsConversion;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			
			unprocessedUpdateTime += passedTime;
			unprocessedFrameTime += passedTime;
			
			//Every time the unprocessedTime goes over 1/60 of a second.
			while(unprocessedUpdateTime >= UPDATE_CAP) {
				unprocessedUpdateTime -= UPDATE_CAP;
				ticks++;
				tick();
			}
			
			while(unprocessedFrameTime >= RENDER_CAP) {
				unprocessedFrameTime -= RENDER_CAP;
				render = true;
			}
			
			if (render || uncappedFrameRate) {
				frames++;
				render();
			}
			else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if (System.currentTimeMillis()-lastTimer >= 1000) {
				lastTimer += 1000;
				fps = String.valueOf(frames);
				tps = String.valueOf(ticks);
				frames = 0;
				ticks = 0;
				
			}
		}
	}
	
	/**
	 * Initializes handles, levels, and resources.
	 */
	private void init() {
		
		input = new InputHandler(this);
		renderer = new RenderHandler(this);
		updater = new UpdateHandler(this);
		leveler = new LevelHandler(this);
		windowHandler = new WindowHandler(this);
		spriteHandler = new SpriteHandler();
//		client = new GameClient(this);
//		clientThread = new Thread(client);
//		
//		server = new GameServer(this);
//		serverThread = new Thread(server);
		
		initResources();
		initLevels();
		initAudioPlayer();
		
		
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
	
	/**
	 * Starts the audio player
	 */
	private void initAudioPlayer() {
		audioPlayer = new AudioPlayer();
		audioPlayer.play(); 
	}
	
	/**
	 * Initializes levels
	 */
	private void initLevels() {
		levels = new ArrayList<>();
		levels.add(new LevelMultiplayer(this));
		levels.add(new LevelUsername(this));
		levels.add(new LevelSettings(this));
		levels.add(new LevelMainMenu(this));
		levels.add(new Level1(this));
		levels.add(new Level2(this));
		levels.add(new Level3(this));
		
		//Starting level
		level = leveler.lookupLevelNumber(-2);
		
		level.addPlayer(new Player(50, Game.GAME_MAX_BORDER/2, "Peepo", input, 0));
	}
	
	/**
	 * Would be used for multiplayer to get public ip.
	 * @return ip
	 */
	public static InetAddress getIpAddress() {
		URL whatismyip = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		BufferedReader in = null;
		InetAddress ip = null;
		try {
			in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
			ip = InetAddress.getByName(in.readLine());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ip;
		
	}
}
