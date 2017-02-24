package net.kleopi.Client.Networking;

import java.io.IOException;
import java.net.Socket;

import net.kleopi.Client.GUI.Messager;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.Networking.NetReceiver;
import net.kleopi.Engine.Networking.NetSender;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;
import net.kleopi.Engine.StatusManagement.Status.NetworkStatus;

public class NetworkClient implements TKNListenerAdapter {

	protected final int port = 11833;
	protected final String serverName = "localhost"; // "178.27.72.46"; //This
														// is the IP of the
														// current Masterserver
	private Socket socket;
	private NetWorkerClient w;
	private NetSender s;
	private NetReceiver r;
	private Player player;

	public NetworkClient() {
		ClientMain.getClient().getEventManager().addListener(this);
	}

	public void disconnect() {
		Messager.info("You have been disconnected");
		ClientMain.getClient().getEventManager().fire(new DisconnectEvent());
	}

	@Override
	public void onDisconnect(DisconnectEvent e) {
		ClientMain.getClient().getStatusManager().getStatus().setNetStat(NetworkStatus.DISCONNECTED);
	}

	@Override
	public void onLogin(LoginEvent e) {

		{
			try {

				socket = new Socket(serverName, port);
				w = new NetWorkerClient();
				s = new NetSender(socket);
				r = new NetReceiver(socket);

				Messager.info("Connected to the Masterserver");
				// TODO send Login PKG
				ClientMain.getClient().getEventManager().fire(new LoggedEvent());
			} catch (IOException ex) {
				Messager.error("An error occured: " + ex.getMessage());
				Messager.error("The Server is currently down for Maintainance. Please try again later!");
				disconnect();
			}
		}

	}

	public void sendUpdate(UpdateObject object) {

		s.sendPackage(object);

	}
}