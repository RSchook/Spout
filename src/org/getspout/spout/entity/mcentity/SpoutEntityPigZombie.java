package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.World;

public class SpoutEntityPigZombie extends EntityPigZombie{

	public SpoutEntityPigZombie(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityPigZombie.class, "PigZombie", 57);
	}

}
