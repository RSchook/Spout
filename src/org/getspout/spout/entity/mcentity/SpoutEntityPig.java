package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityPig;
import net.minecraft.server.World;

public class SpoutEntityPig extends EntityPig{
	public SpoutEntityPig(World world) {
		super(world);
	}

	static {
		EntityUtil.addEntityType(SpoutEntityPig.class, "Pig", 90);
	}
}
