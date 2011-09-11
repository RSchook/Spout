package org.getspout.spout.entity;

import java.lang.reflect.Field;

import net.minecraft.server.*;

import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftMinecart;
import org.getspout.spout.entity.mcentity.SpoutEntityChicken;
import org.getspout.spout.entity.mcentity.SpoutEntityCow;
import org.getspout.spout.entity.mcentity.SpoutEntityCreeper;
import org.getspout.spout.entity.mcentity.SpoutEntityGhast;
import org.getspout.spout.entity.mcentity.SpoutEntityGiant;
import org.getspout.spout.entity.mcentity.SpoutEntityPig;
import org.getspout.spout.entity.mcentity.SpoutEntityPigZombie;
import org.getspout.spout.entity.mcentity.SpoutEntitySheep;
import org.getspout.spout.entity.mcentity.SpoutEntitySkeleton;
import org.getspout.spout.entity.mcentity.SpoutEntitySpider;
import org.getspout.spout.entity.mcentity.SpoutEntitySquid;
import org.getspout.spout.entity.mcentity.SpoutEntityWolf;
import org.getspout.spout.entity.mcentity.SpoutEntityZombie;
import org.getspout.spout.player.SpoutCraftPlayer;

public class SpoutCraftEntity extends CraftEntity{

	public SpoutCraftEntity(CraftServer server, Entity entity) {
		super(server, entity);
	}
	
	public static CraftEntity getUpdatedEntity(CraftServer server, Entity entity, CraftEntity previous) {
		CraftEntity updated = getEntity(server, entity, previous);
		Field bukkitEntity;
		try {
			bukkitEntity = Entity.class.getDeclaredField("bukkitEntity");
			bukkitEntity.setAccessible(true);
			bukkitEntity.set(entity, updated);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return updated;
	}
	
	private static CraftEntity getEntity(CraftServer server, Entity entity, CraftEntity previous) {
		//Order is important
		if (entity instanceof EntityLiving) {
			
			//Players, NPC's
			if (entity instanceof EntityHuman) {
				if (entity instanceof EntityPlayer) {
					if (!previous.getClass().equals(SpoutCraftPlayer.class)) {
						return new SpoutCraftPlayer(server, (EntityPlayer)entity);
					}
					return previous;
				}
				if (!previous.getClass().equals(SpoutCraftHumanEntity.class)) {
					return new SpoutCraftHumanEntity(server, (EntityHuman)entity);
				}
				return previous;
			}
			
			if (entity instanceof EntityCreature) {
				
				//Animals
				if (entity instanceof EntityAnimal) {
					if (entity instanceof EntityChicken) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityChicken.class);
						if (!previous.getClass().equals(SpoutCraftChicken.class)) {
							return new SpoutCraftChicken(server, (EntityChicken)entity);
						}
						return previous;
					}
					if (entity instanceof EntityCow) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityCow.class);
						if (!previous.getClass().equals(SpoutCraftCow.class)) {
							return new SpoutCraftCow(server, (EntityCow)entity);
						}
						return previous;
					}
					if (entity instanceof EntityPig) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityPig.class);
						if (!previous.getClass().equals(SpoutCraftPig.class)) {
							return new SpoutCraftPig(server, (EntityPig)entity);
						}
						return previous;
					}
					if (entity instanceof EntityWolf) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityWolf.class);
						if (!previous.getClass().equals(SpoutCraftWolf.class)) {
							return new SpoutCraftWolf(server, (EntityWolf)entity);
						}
						return previous;
					}
					if (entity instanceof EntitySheep) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntitySheep.class);
						if (!previous.getClass().equals(SpoutCraftSheep.class)) {
							return new SpoutCraftSheep(server, (EntitySheep)entity);
						}
						return previous;
					}
				}
				
				//Mobs
				if (entity instanceof EntityMonster) {
					if (entity instanceof EntityZombie) {
						if (entity instanceof EntityPigZombie) {
							entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityPigZombie.class);
							if (!previous.getClass().equals(SpoutCraftPigZombie.class)) {
								return new SpoutCraftPigZombie(server, (EntityPigZombie)entity);
							}
							return previous;
						}
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityZombie.class);
						if (!previous.getClass().equals(SpoutCraftZombie.class)) {
							return new SpoutCraftZombie(server, (EntityZombie)entity);
						}
						return previous;
					}
					if (entity instanceof EntityCreeper) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityCreeper.class);
						if (!previous.getClass().equals(SpoutCraftCreeper.class)) {
							return new SpoutCraftCreeper(server, (EntityCreeper)entity);
						}
						return previous;
					}
					if (entity instanceof EntityGiantZombie) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityGiant.class);
						if (!previous.getClass().equals(SpoutCraftGiant.class)) {
							return new SpoutCraftGiant(server, (EntityGiantZombie)entity);
						}
						return previous;
					}
					if (entity instanceof EntitySkeleton) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntitySkeleton.class);
						if (!previous.getClass().equals(SpoutCraftSkeleton.class)) {
							return new SpoutCraftSkeleton(server, (EntitySkeleton)entity);
						}
						return previous;
					}
					if (entity instanceof EntitySpider) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntitySpider.class);
						if (!previous.getClass().equals(SpoutCraftSpider.class)) {
							return new SpoutCraftSpider(server, (EntitySpider)entity);
						}
						return previous;
					}
				}
				
				//Water Animals
				if (entity instanceof EntityWaterAnimal) {
					if (entity instanceof EntitySquid) {
						entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntitySquid.class);
						if (!previous.getClass().equals(SpoutCraftSquid.class)) {
							return new SpoutCraftSquid(server, (EntitySquid)entity);
						}
						return previous;
					}
				}
			}
			
			//Slimes
			if (entity instanceof EntitySlime) {
				if (!previous.getClass().equals(SpoutCraftSlime.class)) {
					return new SpoutCraftSlime(server, (EntitySlime)entity);
				}
				return previous;
			}
			
			//Ghasts
			if (entity instanceof EntityGhast) {
				entity = EntityUtil.replaceEntity((EntityLiving) entity, SpoutEntityGhast.class);
				if (!previous.getClass().equals(SpoutCraftGhast.class)) {
					return new SpoutCraftGhast(server, (EntityGhast)entity);
				}
				return previous;
			}
		}
		
		if (entity instanceof EntityArrow) {
			if (!previous.getClass().equals(SpoutCraftArrow.class)) {
				return new SpoutCraftArrow(server, (EntityArrow)entity);
			}
			return previous;
		}
		if (entity instanceof EntityBoat) {
			if (!previous.getClass().equals(SpoutCraftBoat.class)) {
				return new SpoutCraftBoat(server, (EntityBoat)entity);
			}
			return previous;
		}
		if (entity instanceof EntityEgg) {
			if (!previous.getClass().equals(SpoutCraftEgg.class)) {
				return new SpoutCraftEgg(server, (EntityEgg)entity);
			}
			return previous;
		}
		if (entity instanceof EntityFallingSand) {
			if (!previous.getClass().equals(SpoutCraftFallingSand.class)) {
				return new SpoutCraftFallingSand(server, (EntityFallingSand)entity);
			}
			return previous;
		}
		if (entity instanceof EntityFireball) {
			if (!previous.getClass().equals(SpoutCraftFireball.class)) {
				return new SpoutCraftFireball(server, (EntityFireball)entity);
			}
			return previous;
		}
		if (entity instanceof EntityFish) {
			if (!previous.getClass().equals(SpoutCraftFish.class)) {
				return new SpoutCraftFish(server, (EntityFish)entity);
			}
			return previous;
		}
		if (entity instanceof EntityItem) {
			if (!previous.getClass().equals(SpoutCraftItem.class)) {
				return new SpoutCraftItem(server, (EntityItem)entity);
			}
			return previous;
		}
		if (entity instanceof EntityItem) {
			if (!previous.getClass().equals(SpoutCraftItem.class)) {
				return new SpoutCraftItem(server, (EntityItem)entity);
			}
			return previous;
		}
		
		//Weather
		if (entity instanceof EntityWeather) {
			if (entity instanceof EntityWeatherStorm) {
				if (!previous.getClass().equals(SpoutCraftLightningStrike.class)) {
					return new SpoutCraftLightningStrike(server, (EntityWeatherStorm)entity);
				}
				return previous;
			}
			if (!previous.getClass().equals(SpoutCraftWeather.class)) {
				return new SpoutCraftWeather(server, (EntityWeather)entity);
			}
			return previous;
		}
		
		//Minecart
		if (entity instanceof EntityMinecart) {
			EntityMinecart mc = (EntityMinecart) entity;
			if (mc.type == CraftMinecart.Type.Minecart.getId()) {
				if (!previous.getClass().equals(SpoutCraftMinecart.class)) {
					return new SpoutCraftMinecart(server, (EntityMinecart)entity);
				}
				return previous;
			}
			if (mc.type == CraftMinecart.Type.PoweredMinecart.getId()) {
				if (!previous.getClass().equals(SpoutCraftPoweredMinecart.class)) {
					return new SpoutCraftPoweredMinecart(server, (EntityMinecart)entity);
				}
				return previous;
			}
			if (mc.type == CraftMinecart.Type.StorageMinecart.getId()) {
				if (!previous.getClass().equals(SpoutCraftStorageMinecart.class)) {
					return new SpoutCraftStorageMinecart(server, (EntityMinecart)entity);
				}
				return previous;
			}
		}
		
		if (entity instanceof EntityPainting) {
			if (!previous.getClass().equals(SpoutCraftPainting.class)) {
				return new SpoutCraftPainting(server, (EntityPainting)entity);
			}
			return previous;
		}
		if (entity instanceof EntitySnowball) {
			if (!previous.getClass().equals(SpoutCraftSnowball.class)) {
				return new SpoutCraftSnowball(server, (EntitySnowball)entity);
			}
			return previous;
		}
		if (entity instanceof EntityTNTPrimed) {
			if (!previous.getClass().equals(SpoutCraftTNTPrimed.class)) {
				return new SpoutCraftTNTPrimed(server, (EntityTNTPrimed)entity);
			}
			return previous;
		}
		
		return CraftEntity.getEntity(server, entity);
	}
}
