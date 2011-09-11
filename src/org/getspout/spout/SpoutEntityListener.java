package org.getspout.spout;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.player.SpoutPlayer;

public class SpoutEntityListener extends EntityListener {

	@Override
	public void onEntityDamage(EntityDamageEvent event) {
		if(event.getEntity() instanceof SpoutPlayer){
			event.setCancelled(event.isCancelled() || !((SpoutPlayer)event.getEntity()).isPreCachingComplete());
		}
	}

	@Override
	public void onEntityTarget(EntityTargetEvent event) {
		if(event.getTarget() instanceof SpoutPlayer){
			event.setCancelled(event.isCancelled() || !((SpoutPlayer)event.getTarget()).isPreCachingComplete());
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.isCancelled()) {
			return;
		}
		Entity spawn = event.getEntity();
		Entity updated = SpoutManager.getEntity(spawn);
	}

}
