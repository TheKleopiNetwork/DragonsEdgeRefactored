package net.kleopi.Engine.Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import net.kleopi.Client.GUI.Messager;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetCommunicator extends Thread {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public NetCommunicator(Socket s) {
		try {
			// set Socket
			this.socket = s;
			// generate Streams for I/O
			out = new ObjectOutputStream(s.getOutputStream());
			out.flush();
			in = new ObjectInputStream(s.getInputStream());

			System.out.println("Successfully Set Up I/O Streams");
		} catch (IOException e) {
			Messager.error("Could not set up I/O Streams");
		}
		start();

	}

	public Socket getSocket() {
		return socket;
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

	public synchronized void sendPackage(UpdateObject updateObject) {

		try {
			out.writeObject(updateObject);
			out.flush();
		} catch (IOException e) {
			Messager.error("Could not send Package");
		}
	}

}
