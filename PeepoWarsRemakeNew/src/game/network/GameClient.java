package game.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import game.Game;
import game.network.packets.Packet;
import game.network.packets.Packet00Login;
import game.network.packets.Packet02Move;
import game.sprites.mobs.players.MPPlayer;
import game.sprites.mobs.players.Player;

public class GameClient implements Runnable {

	private InetAddress ipAddress;
	private int port;
	private DatagramSocket socket;
	private Game game;
	
	
	private boolean isConnected = false;
	
	//address of the host
	public GameClient(Game game) {
		this.game = game;
		
		try {
			this.socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setIpAddress(String address) {
		try {
			this.ipAddress = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	public void setServerPort(int port) {
		this.port = port;
	}
	public int getSocketPort() {
		return socket.getLocalPort();
	}
	
	public void setConnection(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	//While connected to the host it will receive packets
	@Override
	public void run() {
		while(isConnected) {
			System.out.println("Client is listening on " + ipAddress + ":" + socket.getLocalPort());
			byte[] data = new byte[1024];
			var packet = new DatagramPacket(data, data.length);
			
			try {
				socket.receive(packet);
				System.out.println("Packet received on client from" + packet.getAddress() + ":" + packet.getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	public void parsePacket(byte[] data, InetAddress address, int port) {
		var message = new String(data).trim();
		var type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet = null;
		
		switch(type) {
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet, address, port);
			break;
			
		case LOBBYINFO:
			break;
			
		case MOVE:
			packet = new Packet02Move(data);
			handleMove((Packet02Move) packet);
			break;
			
		case INVALID:
			break;
		}
		
	}
	
	//Sends data to the host
	public void sendData(byte[] data) {
		var packet = new DatagramPacket(data, data.length, ipAddress, port);
		
		try {
			socket.send(packet);
			System.out.println("Packet sent to " + ipAddress + ":" + port + " from " + socket.getLocalPort());
		} catch (IOException e) {
			System.out.println("packet failed to send to " + ipAddress + ":" + port);
		}
	}
	
	public void handleLogin(Packet00Login packet, InetAddress address, int port) {
		var playerNum = getViablePlayerNum();
		
		if(playerNum != -1) {
			game.level.addPlayer(new MPPlayer(50, Game.GAME_MAX_BORDER/playerNum, packet.getUsername(), null, playerNum, address, port));
			System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has connected to the lobby...");
		} else {
			System.out.println("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " failed to connect. Lobby is full...");
		}
	}
	
	public void handleMove(Packet02Move packet) {
		game.level.movePlayer(packet.getPlayerNum(), packet.getX(), packet.getY());
	}
	
	public int getViablePlayerNum() {
		var num = 0;
		for(Player p : game.level.getPlayers()) {
			if(p.getPlayerNum() == num) {
				num++;
			} else {
				return num;
			}
		}
		return -1;
	}
	
}
