package net.kleopi.Tests;

import javax.swing.JOptionPane;

import com.esotericsoftware.minlog.Log;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Instances.Character;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Server.ServerMain;

public class StartBoth implements TKNListenerAdapter {
	private static StartBoth starter;
	private static Character c;

	/**
	 * starts both, client and server
	 *
	 * @param args
	 *            - Just leave this blank :P
	 */
	public static void main(String args[]) {

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			Messager.error("Crash Report:");
			Messager.error(t + " crashed due to " + e);
			Messager.error("The Process is being closed...");
			JOptionPane.showMessageDialog(null,
					"This is a crash D: Please report this crash to the Game creator and include the todays logfile which you can find in the /logs directory!");
			System.exit(1);
		});
		starter = new StartBoth();
		Messager.info("BUILD STARTED - Testing Server and Client:");
		Log.setLogger(new Messager());
		ClientMain.main(null);
		ServerMain.main(null);
		c = (Character) new Character().withData(200, 200, 0, 1, Player.getNeutralPlayer(), 0,
				Sprite.EARTH_CHARACTER_RIGHT);
		ServerMain.getServer().getInstancemanager().registerInstance(c);
		ServerMain.getServer().getEventManager().addListener(starter);
	}

	@Override
	public void onTick(TickEvent e) {
		c.setVelocity(Math.random() * 360, Math.random() * 4);
	}

}
