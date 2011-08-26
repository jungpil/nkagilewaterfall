package util;

import util.Globals;

public class Debug {
	private static boolean debug = false;
	
	public static void setDebug(boolean debugOn) {
		debug = debugOn;
	}
	
	public static void print(String s) {
		if (debug) { System.out.print(s); }
	}
	
	public static void println(String s) {
		if (debug) { System.out.println(s); }
	}
}
