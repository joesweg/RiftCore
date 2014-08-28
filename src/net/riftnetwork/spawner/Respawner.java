package net.riftnetwork.spawner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Respawner implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		if (SpawnManager.getSpawn() == null) {
			// Spawn hasn't been set
			evt.getPlayer().kickPlayer("Server spawn not set! This isn't your fault, and staff have been notified.");
			
			return;
		}
		
		if (SpawnManager.getRespawn() == 0) {
			// Respawn hasn't been set
			evt.getPlayer().kickPlayer("Server respawn not set! This isn't your fault, and staff have been notified.");
			
			return;
		}
		
		
		evt.getPlayer().teleport(SpawnManager.getSpawn());
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		if (evt.getTo().getY() < SpawnManager.getRespawn()) {
			evt.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 3, 255));
			evt.getPlayer().teleport(SpawnManager.getSpawn());
		}
	}
}
