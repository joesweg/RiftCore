package net.riftnetwork.login;

import me.confuser.barapi.BarAPI;
import net.riftnetwork.core.ServerDetails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class JoinHandler implements Listener {
	
	private Plugin plg;
	
	public JoinHandler(Plugin plg) {
		this.plg = plg;
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent evt) {
		
		if (ServerDetails.getServerName().equals("")) {
			BarAPI.setMessage("Waiting for Bungee...");
			Bukkit.getScheduler().scheduleSyncDelayedTask(plg, new ServerName(plg), 5);
		} else {
			BarAPI.setMessage("You are connected to " + ChatColor.GREEN + ServerDetails.getServerName() + ChatColor.WHITE + " on The Rift MC");
		}
	}
}

class ServerName implements Runnable {

	private Plugin plg;
	
	public ServerName(Plugin plg) {
		this.plg = plg;
	}
	
	@Override
	public void run() {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		
		out.writeUTF("GetServer");
		
		try {
			Bukkit.getOnlinePlayers()[0].sendPluginMessage(plg, "BungeeCord", out.toByteArray());
			plg.getLogger().info("Server name acquired.");
		} catch (ArrayIndexOutOfBoundsException ex) {
			// Player left?
			plg.getLogger().warning("Player left while waiting for Bungee.");
		}
		
		ByteArrayDataOutput out1 = ByteStreams.newDataOutput();
		
		out1.writeUTF("PlayerCount");
		out1.writeUTF("sg");
		Bukkit.getOnlinePlayers()[0].sendPluginMessage(plg, "BungeeCord", out1.toByteArray());
		
	}
	
}