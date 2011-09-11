package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityGiantZombie;
import net.minecraft.server.World;

public class SpoutEntityGiant extends EntityGiantZombie{

	public SpoutEntityGiant(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityGiant.class, "Giant", 53);
	}

}
