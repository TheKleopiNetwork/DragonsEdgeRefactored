package net.kleopi.Tests;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Server.MainServer;

public class StartBoth {
	/**
	 * starts both, client and server
	 *
	 * @param args
	 *            - Just leave this blank :P
	 */
	public static void main(String args[]) {

		ClientMain.main(null);
		MainServer.main(null);
	}
}
