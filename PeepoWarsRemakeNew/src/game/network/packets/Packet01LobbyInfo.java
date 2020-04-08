package game.network.packets;

public class Packet01LobbyInfo extends Packet {

	private static int packetId = 01;
	
	private String username;
	
	public Packet01LobbyInfo(String username) {
		super(packetId);
		this.username = username;
	}

	/*
	@Override
	public void sendData(GameClient client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendData(GameServer server) {
		// TODO Auto-generated method stub
		
	}
	*/
	
	@Override
	public byte[] getData() {
		return (packetId + "," + username).getBytes();
	}

	public String getUsername() {
		return username;
	}
}
