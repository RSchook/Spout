package org.getspout.spout.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import net.minecraft.server.Entity;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.EntityLiving;
import net.minecraft.server.EntityTypes;
import net.minecraft.server.MathHelper;
import net.minecraft.server.World;

public class EntityUtil {
	
	public static void updateEntity(EntityLiving copyFrom, EntityLiving copyTo) {
		EntityUtil.copyFields(EntityLiving.class, copyFrom, copyTo);
		copyTo.setPositionRotation(copyFrom.locX, copyFrom.locY + 1, copyFrom.locZ, (float)copyFrom.pitch, (float)copyFrom.yaw);
		copyTo.uniqueId = copyFrom.uniqueId;
		copyTo.fireTicks = copyFrom.fireTicks;
		copyTo.fallDistance = copyFrom.fallDistance;
		copyTo.airTicks = copyFrom.airTicks;
		copyTo.maxAirTicks = copyFrom.maxAirTicks;
		copyTo.maxFireTicks = copyFrom.maxFireTicks;
		copyTo.noDamageTicks = copyFrom.noDamageTicks;
		copyTo.motX = copyFrom.motX;
		copyTo.motY = copyFrom.motY;
		copyTo.motZ = copyFrom.motZ;
	}
	
	public static EntityLiving replaceEntity(EntityLiving entity, Class<? extends EntityLiving> spoutClass) {
		if (!entity.getClass().equals(spoutClass)){ 
			try {
				EntityLiving replacement = (EntityLiving) spoutClass.getConstructors()[0].newInstance(entity.world);
				EntityUtil.updateEntity(entity, replacement);
				entity.world.removeEntity(entity);
				EntityUtil.addEntity(replacement);
				return replacement;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return entity;
	}
	
	public static void copyFields(Class<?> fieldClass, Entity copyFrom, Entity copyTo) {
		Field[] declaredFields = fieldClass.getDeclaredFields();
		for (Field field : declaredFields) {
			int modifier = field.getModifiers();
			if (!Modifier.isFinal(modifier) && !Modifier.isStatic(modifier)) {
				field.setAccessible(true);
				try {
					field.set(copyTo, field.get(copyFrom));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void addEntity(net.minecraft.server.Entity entity) {
		int i = MathHelper.floor(entity.locX / 16.0D);
		int j = MathHelper.floor(entity.locZ / 16.0D);

		if (entity instanceof EntityHuman || entity.world.chunkProvider.isChunkLoaded(i, j)) {
			if (entity instanceof EntityHuman) {
				EntityHuman entityhuman = (EntityHuman) entity;
				entity.world.players.add(entityhuman);
				entity.world.everyoneSleeping();
			}

			entity.world.getChunkAt(i, j).a(entity);
			entity.world.entityList.add(entity);
			@SuppressWarnings("rawtypes")
			Class[] parems = {net.minecraft.server.Entity.class};
			Object[] args = {entity};
			try {
				Method c = World.class.getDeclaredMethod("c", parems);
				c.setAccessible(true);
				c.invoke(entity.world, args);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static void addEntityType(Class toAdd, String name, int id) {
		try {
			Class[] parems = {Class.class, String.class, int.class};
			Object[] args = {toAdd, name, id};
			Method a = EntityTypes.class.getDeclaredMethod("a", parems);
			a.setAccessible(true);
			a.invoke(null, args);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
