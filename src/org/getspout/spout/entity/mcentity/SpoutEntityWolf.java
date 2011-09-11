package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityWolf;
import net.minecraft.server.World;

public class SpoutEntityWolf extends EntityWolf{

	public SpoutEntityWolf(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityWolf.class, "Wolf", 95);
	}

}
