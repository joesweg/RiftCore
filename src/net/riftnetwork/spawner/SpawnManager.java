package net.riftnetwork.spawner;

import org.bukkit.Location;

public class SpawnManager {

	private static Location spawn;
	private static double respawn;
	
	public static void setSpawn(Location l) {
		SpawnManager.spawn = l;
	}
	
	public static Location getSpawn() {
		return spawn;
	}

	public static void setRespawn(double l) {
		SpawnManager.respawn = l;
	}
	
	public static double getRespawn() {
		return respawn;
	}

}
