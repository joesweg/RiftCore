package net.riftnetwork.healthmanager;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageHandler implements Listener {

	private static double multiplier = 1.0;
	
	@EventHandler
	public void onDamage(EntityDamageEvent evt) {
		if (evt.getEntityType().equals(EntityType.PLAYER)) {
			if (multiplier == 0.0) {
				evt.setCancelled(true);
			} else {
				evt.setDamage(evt.getDamage() * multiplier);
			}
		}
	}
	
	public static void setMultiplier(double d) {
		DamageHandler.multiplier = d;
	}
	
	public static double getMultiplier() {
		return multiplier;
	}
}
