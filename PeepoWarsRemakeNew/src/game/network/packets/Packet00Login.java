package game.network.packets;

public class Packet00Login extends Packet {

	private static String packetId = "00";
	
	private String username;
	
	//Constructor for sending packet
	public Packet00Login(String username) {
		super(00);
		this.username = username;
	}

	public Packet00Login(byte[] data) {
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
	}
	/*
	//sends packet data to the host
	@Override
	public void sendData(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void sendData(GameServer server) {
		server.sendDataToOtherClients(getData(), Game.getIpAddress());
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
