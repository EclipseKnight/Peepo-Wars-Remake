package game.levels;

import java.awt.Color;
import java.awt.Graphics2D;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import game.Game;
import game.sprites.mobs.mobs.HostButton;
import game.sprites.mobs.mobs.JoinButton;
import game.sprites.mobs.mobs.MainMenuButton;
import game.sprites.mobs.players.Player;

public class LevelMultiplayer extends Level {

	private static final int LEVELNUMBER = -3;
	
	public boolean doQuit = false;
	
	public static String credentials = "ip:port";
	
	public LevelMultiplayer(Game game) {
		super(game, LEVELNUMBER);
		initMobs();
		initBosses();
	}

	private void initMobs() {
		
		mobs.add(new JoinButton(Game.GAME_MAX_WIDTH-200, Game.GAME_MAX_BORDER * .43f, this));
		mobs.add(new HostButton(Game.GAME_MAX_WIDTH-200, Game.GAME_MAX_BORDER * .59f, this));
		mobs.add(new MainMenuButton(Game.GAME_MAX_WIDTH-200, Game.GAME_MAX_BORDER * .75f, this));
	}
	
	private void initBosses() {
		
	}
	
	@Override
	public void tick() {
		
		// This level needs to continue here because there are still other mobs. Otherwise, the @LevelHandler deals with continuing.
		if(status.equals("mainmenu")) {
			status = "play";
			game.leveler.selectLevel(0);
		}
		
		/*
		if(status.equals("join")) {
			status = "play";
			var creds = credentials.split(":");
			if(creds.length > 1) {
				game.client.setIpAddress(creds[0]);
				game.client.setServerPort(Integer.parseInt(creds[1]));
				game.client.setConnection(true);
				game.clientThread.start();
				
				var loginPacket = new Packet00Login(game.level.getPlayers().get(0).getName());
				loginPacket.sendData(game.client);
			}
		}
		*/
		/*
		if(status.equals("host")) {
			status = "play";
			var creds = credentials.split(":");
			if(creds.length > 1) {
			
				game.server.setSocketPort(Integer.parseInt(creds[1]));
				game.server.setHosting(true);
				game.serverThread.start();
				
				game.client.setIpAddress(creds[0]);
				game.client.setServerPort(Integer.parseInt(creds[1]));
				game.client.setConnection(true);
				game.clientThread.start();
				
				
			}
		}
		*/
	}

	@Override
	public void restartLevel() {
		mobs.clear();
		bosses.clear();
		initMobs();
		initBosses();
		
		for(Player p : players) {
			p.setHealth(p.getMaxHealth());
			p.setShield(p.getMaxShield());
			p.reset();
		}
	}

	@Override
	public void clearLevel() {
		mobs.clear();
		bosses.clear();
		players.clear();
		
	}

	@Override
	public void render(Graphics2D g2d) {
		g2d.setFont(game.gameFont.deriveFont(14f));	
		g2d.setColor(Color.WHITE);
		g2d.drawString("Enter a valid address and port to either host or join a game", 10, 40);
		g2d.drawString("Leave the port empty to use UPnP", 10, 60);
		
		if(players.get(0) != null) {
			var p = players.get(0);
			var fontSize = 25f;
			g2d.setFont(game.gameFont.deriveFont(fontSize));	
			
			var w = g2d.getFontMetrics().stringWidth(credentials);
			var offset = (fontSize * credentials.length()-1) * .45f;
			
			g2d.drawString(credentials, Game.GAME_MAX_WIDTH * .45f - offset, Game.GAME_MAX_BORDER * .5f);
			
			
			g2d.setFont(game.gameFont.deriveFont(20f));	
			if(p.canInput()) {
				g2d.drawString("Moving...", 20, 100);
				
			} else {
				g2d.drawString("Typing...", 20, 100);
				//index indicator for typing still going off other font size
				g2d.drawRect((int) (Game.GAME_MAX_WIDTH * .45f - offset + w), (int) (Game.GAME_MAX_BORDER * .5f), (int) fontSize, 1);
			}
				
		}
	}

	
	public static boolean isValidInet4Address(String ip) {
		try {
			return Inet4Address.getByName(ip).getHostAddress().equals(ip);
		} catch (UnknownHostException e) {
			return false;
		}
	}
	
}
