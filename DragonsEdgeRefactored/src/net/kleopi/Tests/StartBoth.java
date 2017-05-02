package net.kleopi.Tests;

import com.esotericsoftware.minlog.Log;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.Instances.Character;
import net.kleopi.Server.ServerMain;

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
		ServerMain.main(null);

		ServerMain.getServer().getInstancemanager()
				.registerInstance(new Character().withData(0, 0, 180, 20, null, 0, Sprite.EARTH_CHARACTER_RIGHT));

	}

}
