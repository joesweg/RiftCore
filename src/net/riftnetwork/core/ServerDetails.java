package net.riftnetwork.core;

public class ServerDetails {

	
	private static boolean setsvnamecalled = false;
	private static String svname = "";
	
	public static String getServerName() {	
		return ServerDetails.svname;
	}
	
	public static void setServerName(String s) throws IncorrectCallException {
		
		if (setsvnamecalled) {
			throw new IncorrectCallException();
		}
		
		ServerDetails.svname = s;
		ServerDetails.setsvnamecalled = true;
	}
}
