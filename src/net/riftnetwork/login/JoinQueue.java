package net.riftnetwork.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.riftnetwork.zudohackz.utils.GetPage;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class JoinQueue implements Runnable {
	
	public static List<UUID> queue = new ArrayList<UUID>();

	@Override
	public void run() {
		
		Iterator<UUID> it = queue.iterator();
		
		while (it.hasNext()) {
			
			@SuppressWarnings("unused")
			Player ply = Bukkit.getPlayer(it.next());
			
			// Query
			
			GetPage.get("http://google.com/search?q=rtere");
						
			// Dequeue the player
			
			it.remove();
		
		}
	}

}
