package net.riftnetwork.zudohackz.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.bukkit.Bukkit;


public class GetPage {

	public static String[] get(String urltoget) {
		try {
            URL url = new URL(urltoget);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            
            ArrayList<String> output = new ArrayList<String>();
            
            String ln;
            
            while ((ln = in.readLine()) != null) {
            	output.add(ln);
            }
            
            
            return output.toArray(new String[output.size()]);

		} catch (MalformedURLException e) {
			Bukkit.getLogger().info("Malformed URL");
			return null;
		} catch (IOException e) {
			Bukkit.getLogger().info("Could not read URL");
			return null;
		}
	}
}
