package org.getspout.server.net.codec;

import java.io.IOException;

import org.getspout.api.packet.PacketType;
import org.getspout.api.packet.SpoutPacket;
import org.getspout.server.msg.SpoutcraftMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class SpoutcraftCodec extends MessageCodec<SpoutcraftMessage> {
	
	private static final int[] nags;
	protected static final int NAG_MSG_AMT = 10;
	
	static {
		int packets = PacketType.values()[PacketType.values().length - 1].getId();
		nags = new int[packets];
		for (int i = 0; i < packets; i++) {
			nags[i] = NAG_MSG_AMT;
		}
	}

	public SpoutcraftCodec() {
		super(SpoutcraftMessage.class, 0xc3);
	}

	@Override
	public ChannelBuffer encode(SpoutcraftMessage message) throws IOException {
		byte[] data = message.getData();
		ChannelBuffer buffer = ChannelBuffers.buffer(data.length);
		buffer.writeShort(message.getId());
		buffer.writeShort(message.getVersion());
		buffer.writeInt(data.length);
		buffer.writeBytes(data);
		return buffer;
	}

	@Override
	public SpoutcraftMessage decode(ChannelBuffer buffer) throws IOException {
		int packetId = buffer.readShort();
		int version = buffer.readShort();
		int length = buffer.readInt();
		SpoutPacket packet = null;
		if (packetId > -1 && version > -1) {
			try {
				packet = PacketType.getPacketFromId(packetId).getPacketClass().newInstance();
			}
			catch (Exception e) {
				System.out.println("Failed to identify packet id: " + packetId);
			}
		}
		try {
			if (packet == null) {
				buffer.skipBytes(length);
				System.out.println("Unknown packet " + packetId + ". Skipping contents.");
				return null;
			}
			if (packet.getVersion() != version) {
				buffer.skipBytes(length);
				//Keep server admins from going insane :p
				if (nags[packetId]-- > 0) {
					System.out.println("Invalid Packet Id: " + packetId + ". Current v: " + packet.getVersion() + " Receieved v: " + version + " Skipping contents.");
				}
				return null;
			}
			byte[] data = new byte[length];
			buffer.readBytes(data, 0, length);
			return new SpoutcraftMessage(packetId, version, data);
		}
		catch (Exception e) {
			System.out.println("------------------------");
			System.out.println("Unexpected Exception: " + PacketType.getPacketFromId(packetId) + ", " + packetId);
			e.printStackTrace();
			System.out.println("------------------------");
		}
		
		return null;
	}

}
