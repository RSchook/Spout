package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityCreeper;
import net.minecraft.server.World;

public class SpoutEntityCreeper extends EntityCreeper{

	public SpoutEntityCreeper(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityCreeper.class, "Creeper", 50);
	}

}
