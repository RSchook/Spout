package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityGhast;
import net.minecraft.server.World;

public class SpoutEntityGhast extends EntityGhast{

	public SpoutEntityGhast(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityGhast.class, "Ghast", 56);
	}

}
