package net.riftnetwork.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class ScoreboardManagement implements Listener {
	
	public static Scoreboard scb;
	public static Team afk;
	public static Team dnd;
		
	public static Objective rc;
	public static Objective so;
	public static Objective nso;
	public static Objective sl;

	private Plugin plg;
	
	private static String[] staffPerms = {"riftlobby.helper", "riftlobby.mod", "riftlobby.admin", "riftlobby.op"}; 
	
	public ScoreboardManagement(Plugin plugin) {
		plg = plugin;
	}
	
	public void start() {
		BukkitScheduler sched = Bukkit.getScheduler();
		
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		
		scb = manager.getMainScoreboard();
		
		for (Team t : scb.getTeams()) {
			t.unregister();
		}
		
		for (Objective o : scb.getObjectives()) {
			o.unregister();
		}
		
		nso = scb.registerNewObjective("no_staff_online", "dummy");
		
		nso.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "No Staff Online");

		nso.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		nso.getScore(ChatColor.YELLOW + "Support Link:").setScore(5);
		nso.getScore(ChatColor.YELLOW + "bix.im/rmchelp").setScore(4);
		nso.getScore(ChatColor.WHITE + "").setScore(3);
		nso.getScore(ChatColor.LIGHT_PURPLE + "Live chat:").setScore(2);
		nso.getScore(ChatColor.LIGHT_PURPLE + "Type /lc").setScore(1);
		
		so = scb.registerNewObjective("staff_online", "dummy");
		
		so.setDisplayName(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Staff Online Now");
		
		sl = scb.registerNewObjective("social_links", "dummy");
		
		sl.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Find us online!");
		
		sl.getScore(ChatColor.GREEN + "Website:").setScore(8);
		sl.getScore(ChatColor.GREEN + "playrift.tk").setScore(7);
		sl.getScore(ChatColor.WHITE + " ").setScore(6); // Distinguish between spacers
		sl.getScore(ChatColor.RED + "You" + ChatColor.WHITE + "Tube" + ChatColor.RED + ":").setScore(5);
		sl.getScore(ChatColor.RED + "PlayRiftMC").setScore(4);
		sl.getScore(ChatColor.BLACK + " ").setScore(3); // Distinguish between spacers
		sl.getScore(ChatColor.AQUA + "Twitter:").setScore(2);
		sl.getScore(ChatColor.AQUA + "@TheRiftMC").setScore(1);
		
		rc = scb.registerNewObjective("riftcoins", "dummy");
		
		rc.setDisplayName("RiftCoins");
		
		rc.setDisplaySlot(DisplaySlot.BELOW_NAME);
						
		afk = scb.registerNewTeam("afk");
		
		afk.setPrefix(ChatColor.RED + "" + ChatColor.BOLD + "[AFK] " + ChatColor.RESET);
		
		dnd = scb.registerNewTeam("dnd");
		
		dnd.setPrefix(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "[DND] " + ChatColor.RESET);
		
        sched.scheduleSyncRepeatingTask(plg, new Runnable() {
        	
        	boolean stonl = true;
        	boolean showStaff;
        	
            @Override
            public void run() {
            	if (stonl) {
            		ScoreboardManagement.so.setDisplaySlot(null);
            		ScoreboardManagement.nso.setDisplaySlot(null);
            		ScoreboardManagement.sl.setDisplaySlot(DisplaySlot.SIDEBAR);
            	} else {
            		ScoreboardManagement.sl.setDisplaySlot(null);
            		
            		for (Player ply : Bukkit.getOnlinePlayers()) {
            			if (ScoreboardManagement.so.getScore(ply.getName()).getScore() == 0) {
            				showStaff = false;
            			} else {
            				showStaff = true;
            				break;
            			}
            		}
            		
            		if (showStaff) {
            			ScoreboardManagement.so.setDisplaySlot(DisplaySlot.SIDEBAR);
            		} else {
            			ScoreboardManagement.nso.setDisplaySlot(DisplaySlot.SIDEBAR);
            		}
            	}
            	
            	stonl = !(stonl);
            }
            
        }, 0L, 200L);
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoin(PlayerJoinEvent evt) {
		
		Player ply = evt.getPlayer();
		
		for (String perm : staffPerms)
			if (ply.hasPermission(perm)) {
				so.getScore(ply.getName()).setScore(1);
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onLeave(PlayerQuitEvent evt) {
		scb.resetScores(evt.getPlayer().getName());
	}
	
}
