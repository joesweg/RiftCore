package net.riftnetwork.lobbyfun;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

public class Jumping implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		evt.getPlayer().setAllowFlight(true);
		evt.getPlayer().setWalkSpeed(2f);
	}
	
	@EventHandler
	public void onDoubleJump(PlayerToggleFlightEvent evt) {
		if (evt.isFlying() && evt.getPlayer().getGameMode() != GameMode.CREATIVE) {
			evt.getPlayer().setAllowFlight(false);
			evt.setCancelled(true);

			evt.getPlayer().setVelocity(evt.getPlayer().getLocation().getDirection().multiply(new Vector(1.5,2,1.5)));
		}
	}
	
	@EventHandler
	public void onGround(PlayerMoveEvent evt) {
		if (evt.getPlayer().getWorld().getBlockAt(evt.getTo().subtract(0, 1, 0)).getType() != Material.AIR) {
			evt.getPlayer().setAllowFlight(true);
			evt.getPlayer().setFlying(false);
		}
	}
	
}
