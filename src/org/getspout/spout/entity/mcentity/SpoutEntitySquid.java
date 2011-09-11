package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntitySquid;
import net.minecraft.server.World;

public class SpoutEntitySquid extends EntitySquid{

	public SpoutEntitySquid(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntitySquid.class, "Squid", 94);
	}

}
