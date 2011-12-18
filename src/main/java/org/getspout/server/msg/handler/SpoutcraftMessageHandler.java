package org.getspout.server.msg.handler;

import org.getspout.api.packet.SpoutPacket;
import org.getspout.server.entity.SpoutPlayer;
import org.getspout.server.msg.SpoutcraftMessage;
import org.getspout.server.net.Session;

public final class SpoutcraftMessageHandler extends MessageHandler<SpoutcraftMessage>{

	@Override
	public void handle(Session session, SpoutPlayer player, SpoutcraftMessage message) {
		SpoutPacket packet = message.toPacket();
		if (packet != null) {
			packet.run(player.getEntityId());
		}
	}

}
