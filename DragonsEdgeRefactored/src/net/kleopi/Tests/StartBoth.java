package net.kleopi.Tests;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Server.MainServer;

public class StartBoth {
	// comment it back
	public static void main(String args[]) {

		ClientMain.main(null);
		MainServer.main(null);
	}
}
