package net.riftnetwork.minigames;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class Minigame implements Runnable, CommandExecutor {

	private String gamename;
	private int maxplayers;
	private Location lobby;
	private Location game;
	
	private Location[] spawns;
	
	private ArrayList<UUID> currentplayers;
	
	private boolean isPlaying = false;
	
	/**
	 * Constructor for games with single spawn.
	 * 
	 * @param maximum_players The maxium amount of players that can play.
	 * @param game_name The name of the game.
	 * @param lobby_location The location where a player will spawn on joining the server.
	 * @param game_spawn A single spawn point for the actual game.
	 */
	public Minigame(int maximum_players, String game_name, Location lobby_location, Location game_spawn) {
		maxplayers = maximum_players;
		gamename = game_name;
		lobby = lobby_location;
		game = game_spawn;
	}
	
	/**
	 * Constructor for games with multiple spawns.
	 * 
	 * @param maximum_players The maxium amount of players that can play.
	 * @param game_name The name of the game.
	 * @param lobby_location The location where a player will spawn on joining the server.
	 * @param game_spawns An array of spawn points.
	 */
	public Minigame(int maximum_players, String game_name, Location lobby_location, Location[] game_spawns) {
		maxplayers = maximum_players;
		gamename = game_name;
		lobby = lobby_location;
		spawns = game_spawns;
	}
	
	/**
	 * Gets the name of the minigame.
	 * 
	 * @return The name of the game.
	 */
	public final String getName() {
		return gamename;
	}
	
	/**
	 * Gets the max players.
	 * 
	 * @return The maximum players.
	 */
	public final int getMaxPlayers() {
		return maxplayers;
	}
	
	public final boolean isPlaying() {
		return isPlaying;
	}
	
	/**
	 * Forces the game to start, even with less than max players.
	 */
	public void forceStart() {
		
		for (UUID id : currentplayers) {
			Player ply = Bukkit.getPlayer(id);
			
			if (spawns == null) {
				ply.teleport(game);
			} else {
				int i = 0;
				
				ply.teleport(spawns[i]);
				
				i++;
			}
		}
		
		this.isPlaying = true;
	}
	
	/**
	 * Forces the game to stop 
	 */
	public void forceStop() {
		
		for (UUID id : currentplayers) {
			Bukkit.getPlayer(id).teleport(lobby);
		}
		
		this.isPlaying = false;
	}
	/**
	 * Runs while players are in the lobby, perhaps filling chests in SG?
	 */
	public abstract void init();
	
	// False if can't start.
	public final boolean tryStart() {
		if (currentplayers.size() >= maxplayers) {
			forceStart();
			return true;
		}
		
		return false;
	}
	
	/**
	 * The main game loop.
	 */
	@Override
	public abstract void run();
	
	@Override
	public boolean onCommand(CommandSender sdr, Command cmd, String lbl, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("forcestart")) {
			this.forceStart();
			return true;
		}
		
		return false;
	}
	
	/**
	 *  Add player to lobby.
	 *  
	 * @param id Player's UUID
	 */
	public final void addPlayer(UUID id) {
		currentplayers.add(id);
	}
	
	/**
	 * Remove player from lobby.
	 * 
	 * @param id Player's UUID
	 */
	public final void removePlayer(UUID id) {
		currentplayers.remove(id);
		Bukkit.getPlayer(id).kickPlayer("Removed by software.");
	}
	
}
