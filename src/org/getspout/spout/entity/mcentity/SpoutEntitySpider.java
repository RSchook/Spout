package org.getspout.spout.entity.mcentity;

import org.getspout.spout.entity.EntityUtil;

import net.minecraft.server.EntitySpider;
import net.minecraft.server.World;

public class SpoutEntitySpider extends EntitySpider{

	public SpoutEntitySpider(World world) {
		super(world);
	}
	
	static {
		EntityUtil.addEntityType(SpoutEntitySpider.class, "Spider", 52);
	}

}
