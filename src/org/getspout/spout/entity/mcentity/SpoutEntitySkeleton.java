package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntitySkeleton;
import net.minecraft.server.World;

public class SpoutEntitySkeleton extends EntitySkeleton{

	public SpoutEntitySkeleton(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntitySkeleton.class, "Skeleton", 51);
	}

}
