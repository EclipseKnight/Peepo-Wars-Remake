package game.network.packets;

public class Packet02Move extends Packet {

	private static String packetId = "02";
	
	private int playerNum;
	private float x;
	private float y;
	
	public Packet02Move(int playerNum, float x, float y) {
		super(02);
		this.playerNum = playerNum;
		this.x = x;
		this.y = y;
	}
	
	public Packet02Move(byte[] data) {
		super(02);
		String[] dataArray = readData(data).split(",");
		this.playerNum = Integer.parseInt(dataArray[0]);
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}

	/*
	//Sends the packet data to the host
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
		return (packetId + "," + playerNum + "," + x + "," + y).getBytes();
	}

	public int getPlayerNum() {
		return playerNum;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
