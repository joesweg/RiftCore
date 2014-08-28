package net.riftnetwork.zudohackz.utils;

import org.bukkit.ChatColor;

public class ChatPrefixes {
	
	public static String getServerMessageText(String fake_player, String msg) {
		return ChatColor.DARK_RED + fake_player + "> " + ChatColor.WHITE + msg;
	}
	
	public static String getEconMessageText(String msg) {
		return ChatColor.GREEN + "Econ" + "> " + ChatColor.WHITE + msg;
	}
}
