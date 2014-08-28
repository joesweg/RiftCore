package net.riftnetwork.healthmanager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegenHandler implements Listener {

	private static boolean doRegen = false;
	private static boolean doRegenFood = false;

	public static boolean isDoRegen() {
		return doRegen;
	}

	public static void setDoRegen(boolean doRegen) {
		RegenHandler.doRegen = doRegen;
	}
	
	public static boolean isDoRegenFood() {
		return doRegenFood;
	}

	public static void setDoRegenFood(boolean doRegenFood) {
		RegenHandler.doRegenFood = doRegenFood;
	}

	@EventHandler
	public void onMove(PlayerMoveEvent evt) {
		if (doRegen) {
			evt.getPlayer().setHealth(20d);
		}
		
		if (doRegenFood) {
			evt.getPlayer().setFoodLevel(20);
		}
	}
	
}
