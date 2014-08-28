package net.riftnetwork.zudohackz.rocketjump;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

@SuppressWarnings("unused")
public class ProxyPlayer implements PluginMessageListener {

	private String player;
	private Plugin plugin;
	
	public ProxyPlayer(Plugin plg, String ply) {
		player = ply;
		
		plugin = plg;
				
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
	}
	
	public void sendTo(String server) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("ConnectOther");
		out.writeUTF(player);
		out.writeUTF(server);
		
		for (Player ply : Bukkit.getOnlinePlayers()) {
			if (ply.getName().equalsIgnoreCase(player)) {
				ply.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
			}
		}
		
	}
	
	public void kick(String reason) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("KickPlayer");
		out.writeUTF(player);
		out.writeUTF(reason);
		
		for (Player ply : Bukkit.getOnlinePlayers()) {
			if (ply.getName().equalsIgnoreCase(player)) {
				ply.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
			}
		}
	}
	
	public void sendMessage(String message) {
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Message");
		out.writeUTF(player);
		out.writeUTF(message);
		
		for (Player ply : Bukkit.getOnlinePlayers()) {
			if (ply.getName().equalsIgnoreCase(player)) {
				ply.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
			}
		}
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("BungeeCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("SomeSubChannel")) {
			// TODO: something
		}
	}
	
}
