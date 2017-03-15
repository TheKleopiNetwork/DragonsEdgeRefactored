package net.kleopi.Tests;

import com.esotericsoftware.minlog.Log;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Server.MainServer;

public class StartBoth {
	/**
	 * starts both, client and server
	 *
	 * @param args
	 *            - Just leave this blank :P
	 */
	public static void main(String args[]) {

		Messager.info("BUILD STARTED - Testing Server and Client:");
		Log.setLogger(new Messager());
		ClientMain.main(null);
		MainServer.main(null);
	}

}
