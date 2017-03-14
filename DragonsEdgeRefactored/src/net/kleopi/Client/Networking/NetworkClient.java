package net.kleopi.Client.Networking;

import java.io.IOException;
import java.net.Socket;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.Networking.NetCommunicator;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Engine.Networking.UpdateObjects.DataMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.LoginUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;
import net.kleopi.Engine.StatusManagement.Status.NetworkStatus;

public class NetworkClient implements TKNListenerAdapter {

	protected final int port = 11833;
	protected final String serverName = "localhost"; // "178.27.72.46"; //This
														// is the IP of the
														// current Masterserver
	private Socket socket;
	private Player player;
	private NetCommunicator netcommunicator;

	public NetworkClient() {
		ClientMain.getClient().getEventManager().addListener(this);
	}

	/**
	 * Called whenever a disconnect happens
	 */
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

				Client client = new Client();
				client.start();
				try {
					client.connect(5000, "localhost", 11833, 11880);
				} catch (IOException g) {
					g.printStackTrace();
				}
				Kryo kryo = client.getKryo();
				kryo.register(LoginUpdate.class);
				kryo.register(DataMapUpdate.class);
				client.addListener(new Listener() {
					@Override
					public void received(Connection connection, Object object) {
						if (object instanceof UpdateObject) {
							System.out.println("Also received Object");
						}
					}
				});

				// old Code

				client.sendTCP(new LoginUpdate("zero", "fucks given"));
				Messager.info("Trying to connect to server...");
				socket = new Socket(serverName, port);
				netcommunicator = new NetCommunicator(socket);

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

		// TODO: replace by event
		netcommunicator.sendPackage(object);

	}
}