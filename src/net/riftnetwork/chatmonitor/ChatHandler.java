package net.riftnetwork.chatmonitor;

import net.riftnetwork.scoreboards.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.Note.Tone;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent evt) {
		
		for (Player ply : Bukkit.getOnlinePlayers()) {
			if (evt.getMessage().contains(ply.getName())) {
				StringBuilder b = new StringBuilder(evt.getMessage());
				b.insert(evt.getMessage().indexOf(ply.getName()), ChatColor.RED);
				b.insert(evt.getMessage().indexOf(ply.getName())+ply.getName().length()+2, ChatColor.GRAY);
				evt.setMessage(new String(b));
			}
		}
		
		evt.setMessage(evt.getMessage().replace("%", "%%"));
		
		if (evt.getPlayer().getName().equalsIgnoreCase("z_hackz")) {
			evt.setFormat(ChatColor.WHITE + "" + ChatColor.BOLD + "DEV " + ChatColor.WHITE + "" + ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "OP " + ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		} else if (evt.getPlayer().hasPermission("riftlobby.op")) {
			evt.setFormat(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "OP " + ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		} else if (evt.getPlayer().hasPermission("riftlobby.admin")) {
			evt.setFormat(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "ADMIN " + ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		} else if (evt.getPlayer().hasPermission("riftlobby.mod")) {
			evt.setFormat(ChatColor.RED + "" + ChatColor.BOLD + "MOD " + ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		} else if (evt.getPlayer().hasPermission("riftlobby.helper")) {
			evt.setFormat(ChatColor.GREEN + "" + ChatColor.BOLD + "HELPER " + ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		} else {
			evt.setFormat(ChatColor.WHITE + evt.getPlayer().getDisplayName() + " " + ChatColor.GRAY + evt.getMessage());
		}
		
		
		for (Player ply : Bukkit.getOnlinePlayers()) {
			if (evt.getMessage().contains(ply.getName()) && !(ScoreboardManagement.scb.getTeam("dnd").hasPlayer(ply))) {
				ply.playNote(ply.getLocation(), Instrument.PIANO, Note.sharp(2, Tone.F));
			}
		}
		
	}
	
}
