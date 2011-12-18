package org.getspout.server.msg;

import java.io.IOException;

import org.getspout.api.io.SpoutInputStream;
import org.getspout.api.io.SpoutOutputStream;
import org.getspout.api.packet.PacketType;
import org.getspout.api.packet.SpoutPacket;

public class SpoutcraftMessage extends Message{
	private int id, version;
	private byte[] data;
	public SpoutcraftMessage(int id, int version, byte[] data) {
		this.id = id;
		this.version = version;
		this.data = data;
	}
	
	public SpoutcraftMessage(SpoutPacket packet) {
		SpoutOutputStream output = new SpoutOutputStream();
		try {
			packet.writeData(output);
			data = output.getRawBuffer().array();
			id = packet.getPacketType().getId();
			version = packet.getVersion();
		}
		catch (IOException e) {
			System.out.println("Failed to write Spoutcraft Packet " + (packet.getPacketType() != null ? packet.getPacketType().name() : "Unknown Packet"));
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public SpoutPacket toPacket() {
		SpoutInputStream input = new SpoutInputStream(data);
		SpoutPacket packet = null;
		PacketType type = PacketType.getPacketFromId(id);
		try {
			packet = type.getPacketClass().newInstance();
			packet.readData(input);
		}
		catch (IOException e) {
			System.out.println("Failed to read Spoutcraft Packet " + (type != null ? type.name() : "Unknown Packet"));
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return packet;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
