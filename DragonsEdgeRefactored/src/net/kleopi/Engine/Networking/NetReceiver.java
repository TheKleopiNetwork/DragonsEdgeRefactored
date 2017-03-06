package net.kleopi.Engine.Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import net.kleopi.Client.GUI.Messager;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetReceiver extends Thread {
	private ObjectInputStream in;

	/**
	 * Starts itself
	 *
	 * @param s
	 *            - Socket
	 */
	public NetReceiver(Socket s) {
		try {
			in = new ObjectInputStream(s.getInputStream());

		} catch (IOException e) {
			Messager.error("Could not get InputStream");
		}
		start();
	}

	private synchronized void receiveNext() {

		try {
			Object object = in.readObject();
			if (object instanceof UpdateObject) {
				UpdateObject update = (UpdateObject) object;
				System.out.println("Received Update");
				ClientMain.getClient().getEventManager().fire(new PackageReceivedEvent(update));
			}
		}

		catch (ClassNotFoundException | IOException e) {
			Messager.error("Error occured while reading Object: " + e.getMessage() + "[NetReceiver.ReceiveNext]");
		}
	}

	@Override
	public void run() {

		while (true) {
			receiveNext();
			try {
				sleep(10);
			} catch (InterruptedException e) {
			}

		}
	}
}
