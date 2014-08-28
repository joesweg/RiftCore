package net.riftnetwork.serverswitch;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Triggers implements Listener {

	@EventHandler
	public void onClick(PlayerInteractEvent evt) {
		if (evt.getPlayer().isSneaking() && (evt.getAction().equals(Action.LEFT_CLICK_AIR) || evt.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
			MakeInventory.triggerMenu(evt.getPlayer());
		}
	}
}
