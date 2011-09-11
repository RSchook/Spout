package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntitySheep;
import net.minecraft.server.World;

public class SpoutEntitySheep extends EntitySheep{

	public SpoutEntitySheep(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntitySheep.class, "Sheep", 91);
	}

}
