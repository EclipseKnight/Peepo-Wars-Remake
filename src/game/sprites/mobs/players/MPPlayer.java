package game.sprites.mobs.players;

import java.net.InetAddress;

import game.handlers.InputHandler;

public class MPPlayer extends Player {

	private InetAddress ipAddress;
	private int port;
	
	public MPPlayer(float x, float y, String name, InputHandler input, int playerNum, InetAddress ipAddress, int port) {
		super(x, y, name, input, playerNum);
		this.ipAddress = ipAddress;
		this.port = port;
	}
	
	public InetAddress getIpAddress() {
		return ipAddress;
	}
	
	public int getPort() {
		return port;
	}

}
