package net.kleopi.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.Networking.NetSender;
import net.kleopi.Engine.Networking.Player;
import net.kleopi.Engine.Networking.UpdateObjects.DataMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetworkServer extends Thread implements TKNListenerAdapter {
	private final static int port = 11833;

	List<Player> players = new ArrayList<>();
	private int idcounter = 1;

	NetWorkerServer w;

	private ArrayList<NetSender> senders = new ArrayList<>();

	public NetworkServer() {
		MainServer.getServer().getEventManager().addListener(this);
		start();
	}

	public void addPlayer(Socket socket) {

		Player p = new Player(getNextId());
		players.add(p);
		System.out.println("Player added [Server.addPlayer]");
		sendUpdate(new DataMapUpdate(MainServer.getServer().getTilemanager().getDatamap()), socket);
		System.out.println("Sent Datamap");
	}

	private int getNextId() {

		int nid = idcounter;
		idcounter++;
		return nid;
	}

	public Player getPlayerfromId(int clientid) {

		for (Player p : players) {
			if (p.id == clientid) {
				return p;
			}
		}
		System.out.println("Player does not exist!");
		return null;
	}

	public void removePlayer(Player player) {

		players.remove(player);
	}

	@SuppressWarnings("resource")
	@Override
	public void run() {

		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		NetWorkerServer w = new NetWorkerServer();
		// workloop
		while (true) {
			try {
				Socket client = serverSocket.accept();
				System.out
						.println("Client connected from " + client.getRemoteSocketAddress() + "[Server.run(workloop)]");
				addPlayer(client);
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out! [Server.run(workloop)]");

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	public void sendUpdate(UpdateObject object, Socket socket) {

		for (NetSender s : senders = new ArrayList<>()) {
			if (s.getSocket() == socket) {
				s.sendPackage(object);
			}
		}

	}

	public void updateAll(UpdateObject object)

	{

		// TODO: sendPackage to Everyone
	}
}
