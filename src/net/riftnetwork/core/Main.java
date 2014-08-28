package net.riftnetwork.core;

import me.confuser.barapi.BarAPI;
import net.riftnetwork.chatmonitor.ChatHandler;
import net.riftnetwork.healthmanager.DamageHandler;
import net.riftnetwork.healthmanager.RegenHandler;
import net.riftnetwork.login.JoinHandler;
import net.riftnetwork.login.JoinQuitMsg;
import net.riftnetwork.query.Commands;
import net.riftnetwork.serverswitch.MakeInventory;
import net.riftnetwork.serverswitch.Triggers;
import net.riftnetwork.spawner.Respawner;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

public class Main extends JavaPlugin implements PluginMessageListener {
	
	@Override
	public void onEnable() {

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		
		MakeInventory.doMake();
		
		getServer().getPluginManager().registerEvents(new Respawner(), this);
		getServer().getPluginManager().registerEvents(new DamageHandler(), this);
		getServer().getPluginManager().registerEvents(new RegenHandler(), this);
		getServer().getPluginManager().registerEvents(new ChatHandler(), this);
		getServer().getPluginManager().registerEvents(new MakeInventory(this), this);
		
		getServer().getPluginManager().registerEvents(new JoinQuitMsg(), this);
		
		getServer().getPluginManager().registerEvents(new Triggers(), this);
		getServer().getPluginManager().registerEvents(new JoinHandler(this), this);
			
		getServer().getLogger().info("Starting query server...");
		
		Commands qsc = new Commands();
		
		getCommand("verify").setExecutor(qsc);
		
	}
	
	@Override
	public void onDisable() {
		
	}

	@Override
	public void onPluginMessageReceived(String chan, Player ply, byte[] msg) {
		if (!(chan.equals("BungeeCord"))) {
			return; // Not a message for us :(
		}
		
		ByteArrayDataInput in = ByteStreams.newDataInput(msg);
		
		String subchan = in.readUTF();
		
		if (subchan.equals("GetServer")) {
			ServerDetails.setServerName(in.readUTF());
			BarAPI.setMessage("You are connected to " + ChatColor.GREEN + ServerDetails.getServerName() + ChatColor.WHITE + " on The Rift MC");
		} 
		
	}
	
}
