package net.riftnetwork.query;

import net.riftnetwork.zudohackz.utils.ChatPrefixes;
import net.riftnetwork.zudohackz.utils.GetPage;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	@Override
	public boolean onCommand(CommandSender sdr, Command cmd, String lbl,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("verify")) {
			
			if (!(sdr instanceof Player)) {
				return true;
			}
						
			if (args.length == 0) {
				String[] stat = GetPage.get("http://riftnetwork.net/riftmc/verify_account.php?u=" + sdr.getName());
				if (stat.length == 0) {
					sdr.sendMessage(ChatPrefixes.getServerMessageText("Registration", "Your account is not linked! Type /verify <your email>"));			
				} else {
					sdr.sendMessage(ChatPrefixes.getServerMessageText("Registration", "Your account is linked to " + ChatColor.GREEN + stat[0] + ChatColor.WHITE + "."));
				}
				return true;
			}
			
			if (args.length != 1) {
				return false;
			}

			
			String email = args[0];
			
			if (!isValidEmailAddress(email)) {
				sdr.sendMessage(ChatPrefixes.getServerMessageText("Registration", "Are you sure that's your email address?"));
				return true;
			}
			
			String stat = GetPage.get("http://riftnetwork.net/riftmc/verify_account.php?e=" + email + "&u=" + sdr.getName())[0];
			
			if (stat.startsWith("locked:")) {
				sdr.sendMessage(ChatPrefixes.getServerMessageText("Registration", "You can't change the email address on this account, as it is locked to " + ChatColor.GREEN + stat.substring(stat.indexOf(":")+1) + ChatColor.WHITE + ". Log in to the control panel and disable it."));
			} else {
				sdr.sendMessage(ChatPrefixes.getServerMessageText("Registration", "An email has been sent to your address containing your new password."));
			}
			
			return true;
		}
		
		return false;
	}

}
