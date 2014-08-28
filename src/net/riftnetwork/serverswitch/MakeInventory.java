package net.riftnetwork.serverswitch;

import java.util.Arrays;
import java.util.HashMap;

import net.riftnetwork.zudohackz.rocketjump.ProxyPlayer;
import net.riftnetwork.zudohackz.utils.GetPage;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class MakeInventory implements Listener {
	
	private static Inventory serverSwitcher;
	private static String dataUrl = "http://riftnetwork.net/riftmc/servers.txt";
	private static String switcherName = "Server Switcher";
	private static HashMap<Integer, String> servers = new HashMap<Integer, String>();
	private Plugin plg;

	public MakeInventory(Plugin plg) {
		this.plg = plg;
	}
	
	public static void doMake() {
		
		Inventory inv = Bukkit.createInventory(null, 18, switcherName);
		
		String dataContents[] = GetPage.get(dataUrl);
		
		
		for (String ln : dataContents) {
			
			if (!(ln.startsWith("#"))) {
							
				String[] lineContents = ln.split(":");
			
				int slotId = Integer.parseInt(lineContents[0]);
				
				String itemName = lineContents[1].replace("&", "§");
				
				String itemDesc = lineContents[2].replace("&", "§");
				
				String itemId = lineContents[3];
				
				String serverName = lineContents[4];
				
				ItemStack item;
				
				try {
					item = new ItemStack(Material.getMaterial(itemId));
				} catch (NullPointerException ex) {
					Bukkit.getLogger().warning("Item '" + itemId + "' not found!");
					item = new ItemStack(Material.TNT, 0, (short) 1);
				}
				
				ItemMeta item_meta = item.getItemMeta();
				
				item_meta.setDisplayName(itemName);
				
				item_meta.setLore(Arrays.asList(itemDesc));
				
				item.setItemMeta(item_meta);
				
				inv.setItem(slotId, item);
				
				servers.put(slotId, serverName);
			}
		}
		
		MakeInventory.serverSwitcher = inv;
		
		
	}
	
	public static void triggerMenu(Player ply) {
		ply.openInventory(serverSwitcher);
	}
	
	@EventHandler
	public void onInventClick(InventoryClickEvent evt) {
		if (evt.getInventory().getName().equals(switcherName)) {
			evt.setCancelled(true);
			if (evt.getClick().equals(ClickType.LEFT)) {
				if (servers.containsKey(evt.getRawSlot())) {
					plg.getLogger().fine("sending to " + servers.get(evt.getRawSlot()));
					new ProxyPlayer(plg, evt.getWhoClicked().getName()).sendTo(servers.get(evt.getRawSlot()));;
				}
			}
		}
	}
}
