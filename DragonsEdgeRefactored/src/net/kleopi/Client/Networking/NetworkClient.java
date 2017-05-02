package net.kleopi.Client.Networking;

import java.io.IOException;

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
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.Networking.KryoRegisterer;
import net.kleopi.Engine.Networking.UpdateObjects.LoginUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetworkClient implements TKNListenerAdapter {

	private static final int buffersize = 1024 * 1024 * 16;
	protected final int port = 11833;
	protected final String serverName = "localhost"; // "178.27.72.46"; //This

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
	}

	@Override
	public void onLogin(LoginEvent e) {

		{
			Client client = new Client(buffersize, buffersize);
			client.start();
			try {
				client.connect(5000, "localhost", 11833, 11880);
			} catch (IOException g) {
				Messager.error("Server not up yet. Trying again...");
				client.close();
				client.stop();
				ClientMain.getClient().getEventManager().fire(e);
				return;
			}
			Kryo kryo = client.getKryo();
			KryoRegisterer.registerClasses(kryo);
			client.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object object) {
					if (object instanceof UpdateObject) {
						ClientMain.getClient().getEventManager().fire(new PackageReceivedEvent(object));
					}
				}
			});
			client.sendTCP(new LoginUpdate("zero", "fucks given"));
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onPackageReceived(
	 * net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent)
	 */
	@Override
	public void onPackageReceived(PackageReceivedEvent e) {
		if (e.getUpdateObject() instanceof TileMapUpdate) {
			ClientMain.getClient().getEventManager().fire(new LoggedEvent());
		}
	}

	public void sendUpdateToServer(UpdateObject object) {
		// TODO: send Object
	}
}