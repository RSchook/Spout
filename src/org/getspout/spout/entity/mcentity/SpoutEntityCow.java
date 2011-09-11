package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntityCow;
import net.minecraft.server.World;

public class SpoutEntityCow extends EntityCow{

	public SpoutEntityCow(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntityCow.class, "Cow", 92);
	}

}
