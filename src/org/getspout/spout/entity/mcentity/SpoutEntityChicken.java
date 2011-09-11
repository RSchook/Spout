package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityChicken;
import net.minecraft.server.World;

public class SpoutEntityChicken extends EntityChicken{

	public SpoutEntityChicken(World world) {
		super(world);
	}


	static {
		EntityUtil.addEntityType(SpoutEntityChicken.class, "Chicken", 93);
	}
}
