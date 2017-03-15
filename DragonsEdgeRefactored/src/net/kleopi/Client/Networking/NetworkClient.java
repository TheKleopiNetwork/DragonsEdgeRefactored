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
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.LoginUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;
import net.kleopi.Engine.StatusManagement.Status.NetworkStatus;

public class NetworkClient implements TKNListenerAdapter {

	private static final int buffersize = 1024*1024*16;
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
		ClientMain.getClient().getStatusManager().getStatus().setNetStat(NetworkStatus.DISCONNECTED);
	}

	@Override
	public void onLogin(LoginEvent e) {

		{
			Client client = new Client(buffersize,buffersize);
			client.start();
			try {
				client.connect(5000, "localhost", 11833, 11880);
			} catch (IOException g) {
				g.printStackTrace();
			}
			Kryo kryo = client.getKryo();
			kryo.register(LoginUpdate.class);
			kryo.register(TileMapUpdate.class);
			kryo.register(char[][].class);
			kryo.register(char[].class);
			client.addListener(new Listener() {
				@Override
				public void received(Connection connection, Object object) {
					if (object instanceof UpdateObject) {
					}
				}
			});
			client.sendTCP(new LoginUpdate("zero", "fucks given"));
			}

	}

	public void sendUpdateToServer(UpdateObject object) {
		//TODO: send Object
	}
}