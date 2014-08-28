package net.riftnetwork.login;

import net.riftnetwork.zudohackz.utils.ChatPrefixes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitMsg implements Listener {
	
	private static String joinMsg = ChatPrefixes.getServerMessageText("Join", "%PLAYERNAME%");
	private static String quitMsg = ChatPrefixes.getServerMessageText("Quit", "%PLAYERNAME%");
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onJoin(PlayerJoinEvent evt) {
		evt.setJoinMessage(joinMsg.replace("%PLAYERNAME%", evt.getPlayer().getName()));
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent evt) {
		evt.setQuitMessage(quitMsg.replace("%PLAYERNAME%", evt.getPlayer().getName()));
	}

	public static String getJoinMsg() {
		return joinMsg;
	}

	public static void setJoinMsg(String joinmsg) {
		JoinQuitMsg.joinMsg = joinmsg;
	}

	public static String getQuitMsg() {
		return quitMsg;
	}

	public static void setQuitMsg(String quitMsg) {
		JoinQuitMsg.quitMsg = quitMsg;
	}

}
