package game.network.packets;

public abstract class Packet {
	
	public static enum PacketType {
		INVALID(-1), LOGIN(00), LOBBYINFO(01), MOVE(02);
		
		private int id;
		
		private PacketType(int id) {
			this.id = id;
		}
		
		public int getId() {
			return id;
		}
	}

	public byte packetId;
	
	public Packet(int packetId) {
		this.packetId = (byte) packetId;
	}
	
	public String readData(byte[] data) {
		String message = new String(data).trim();
		return message.substring(3);
	}
	/*
	//Client sends a packet to the server
	public abstract void sendData(GameClient client);
	
	//Host sends a packet to the clients
	public abstract void sendData(GameServer server);
	*/
	//returns the data of the packet including the packet id and its fields.
	public abstract byte[] getData();
	
	public static PacketType lookupPacket(String packetId) {
		try {
			return lookupPacket(Integer.parseInt(packetId));
		} catch (NumberFormatException e) {
			return PacketType.INVALID;
		}
		
		
	}
	
	public static PacketType lookupPacket(int packetId) {
		for(PacketType p : PacketType.values()) {
			if(p.getId() == packetId) {
				return p;
			}
		}
		
		return PacketType.INVALID;
	}
}
