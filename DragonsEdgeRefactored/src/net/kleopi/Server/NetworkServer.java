package net.kleopi.Server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Engine.Networking.UpdateObjects.LoginUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetworkServer extends Thread implements TKNListenerAdapter {
	private final static int port = 11833; // the Port we will use
	private final static int udpport = 11880;
	private static final int buffersize = 1024 * 1024 * 16;
	List<Player> players = new ArrayList<>(); // saving all connected players

	/**
	 * Starts itself! Also registers itself!
	 */
	public NetworkServer() {
		MainServer.getServer().getEventManager().addListener(this);
		start();
	}

	/**
	 * Add a Player to the list.
	 *
	 * @param socket
	 *            - The socket which should be saved for that player
	 */

	public void addPlayer(String username, Connection c) {

		Player p = new Player(c);
		players.add(p);
		Messager.info("Player added [Server.addPlayer]");
		// TODO: maybe dont send the Datapack here already?
		Messager.info("Packing TileMap for new Client");
		TileMapUpdate tmu = new TileMapUpdate()
				.withCompressedTilemap(MainServer.getServer().getTilemanager().getCompressedDataMap());
		sendUpdate(p, tmu);
		Messager.info("Sent Tilemap");
	}

	/**
	 * Removes a player from the List
	 *
	 * @param player
	 *            - Player to remove
	 */
	public void removePlayer(Player player) {

		players.remove(player);
	}

	@Override
	public void run() {

		// Updated Code:
		System.out.println("Waiting for Map to finish generating...");
		while (!MainServer.getServer().getTilemanager().hasMap) {
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Map retrieved");

		Server server = new Server(buffersize, buffersize);

		Kryo kryo = server.getKryo();
		kryo.register(LoginUpdate.class);
		kryo.register(TileMapUpdate.class);
		kryo.register(char[][].class);
		kryo.register(char[].class);
		server.addListener(new Listener() {
			@Override
			public void received(Connection connection, Object object) {
				if (object instanceof LoginUpdate) {
					addPlayer(((LoginUpdate) object).username, connection);
					Messager.info("received object");

				}
			}
		});
		server.start();
		try {
			server.bind(port, udpport);
		} catch (IOException e1) {
			Messager.error("IOException has occured" + e1);
		}
	}

	/**
	 * Sends a datapackage to the specific client using a socket
	 *
	 * @param object
	 *            - Datapackage to send
	 * @param player
	 *            - The Player-Object which should be adressed
	 * 
	 */
	public void sendUpdate(Player player, UpdateObject object) {

		player.getConnection().sendTCP(object);

	}

	/**
	 * Send a package to all connected Players
	 *
	 * @param object
	 *            - Datapackage to send
	 */
	public void updateAll(UpdateObject object)

	{

		// TODO: sendPackage to Everyone
	}
}
