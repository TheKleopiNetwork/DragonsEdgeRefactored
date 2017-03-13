package net.kleopi.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.Networking.NetCommunicator;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Engine.Networking.UpdateObjects.DataMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetworkServer extends Thread implements TKNListenerAdapter {
	private final static int port = 11833; // the Port we will use

	List<Player> players = new ArrayList<>(); // saving all connected players
												// TODO: change?
	private int idcounter = 1; // needed to give clients Ids

	private ArrayList<NetCommunicator> communicators = new ArrayList<>();

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

	public void addPlayer(Socket socket) {

		Player p = new Player(getNextId());
		players.add(p);
		communicators.add(new NetCommunicator(socket));
		Messager.info("Player added [Server.addPlayer]");
		// TODO: maybe dont send the Datapack here already?
		Messager.info("Packing TileMap for new Client");
		sendUpdate(new DataMapUpdate(MainServer.getServer().getTilemanager().getDatamap()), socket);
		Messager.info("Sent Tilemap");
	}

	private int getNextId() {

		int nid = idcounter;
		idcounter++;
		return nid;
	}

	/**
	 *
	 * @param clientid
	 *            - id to search with
	 * @return Player with the given ID
	 */
	public Player getPlayerfromId(int clientid) {

		for (Player p : players) {
			if (p.id == clientid) {
				return p;
			}
		}
		System.out.println("Player does not exist!");
		return null;
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

	@SuppressWarnings("resource")
	@Override
	public void run() {

		// Preparations
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		// workloop
		while (true) {
			try {
				Socket clientSocket = serverSocket.accept();
				Messager.info(
						"Client connected from " + clientSocket.getRemoteSocketAddress() + "[Server.run(workloop)]");
				addPlayer(clientSocket);
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Sends a datapackage to the specific client using a socket
	 *
	 * @param object
	 *            - Datapackage to send
	 * @param socket
	 *            - Socket to send a package to TODO: Add methods using username
	 *            or ID; Overloading maybe?
	 */
	public void sendUpdate(UpdateObject object, Socket socket) {

		for (NetCommunicator s : communicators) {
			if (s.getSocket() == socket) {
				s.sendPackage(object);
			}
		}

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
