package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityZombie;
import net.minecraft.server.World;

public class SpoutEntityZombie extends EntityZombie{

	public SpoutEntityZombie(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityZombie.class, "Zombie", 54);
	}

}
