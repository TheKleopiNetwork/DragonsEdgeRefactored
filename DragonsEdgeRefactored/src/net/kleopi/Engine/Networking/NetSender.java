package net.kleopi.Engine.Networking;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import net.kleopi.Client.GUI.Messager;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetSender {
	private ObjectOutputStream out;
	private Socket socket;

	// TODO: react on Events
	public NetSender(Socket s) {

		try {
			this.socket = s;
			OutputStream outStream = s.getOutputStream();
			out = new ObjectOutputStream(outStream);
			out.flush();
		} catch (IOException e) {
			Messager.error("Could not set up OutputStream");
		}
	}

	public Socket getSocket() {
		return socket;
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
