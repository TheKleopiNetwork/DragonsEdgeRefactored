package net.kleopi.Server;

import net.kleopi.Engine.Enums.Tilemanager;
import net.kleopi.Engine.EventManagement.EventManager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.Instances.Instancemanager;
import net.kleopi.Engine.StatusManagement.StatusManager;

public class MainServer implements TKNListenerAdapter {

	private static MainServer server;

	public static MainServer getServer() {
		return server;
	}

	public static void main(String[] args) {
		setServer(new MainServer());
		server.setEventManager(new EventManager());
		server.getEventManager().addListener(server);
		server.setNetwork(new NetworkServer());
		server.setStatusManager(new StatusManager());
		server.setTilemanager(new Tilemanager());
		server.setInstancemanager(new Instancemanager());
		server.getEventManager().fire(new StartupEvent());

	}

	public static void setServer(MainServer server) {
		MainServer.server = server;
	}

	private StatusManager statusManager;
	private NetworkServer network;

	private EventManager eventManager;
	private Tilemanager tilemanager;

	private Instancemanager instancemanager;

	public MainServer() {

	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public Instancemanager getInstancemanager() {
		return instancemanager;
	}

	public NetworkServer getNetwork() {

		return network;
	}

	public StatusManager getStatusManager() {
		return statusManager;
	}

	public Tilemanager getTilemanager() {
		return tilemanager;
	}

	@Override
	public void onStartUp(StartupEvent e) {

		tilemanager.setDatamap(Tilemanager.setDefaultMap());

	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public void setInstancemanager(Instancemanager instancemanager) {
		this.instancemanager = instancemanager;
	}

	public void setNetwork(NetworkServer network) {
		this.network = network;
	}

	public void setStatusManager(StatusManager statusManager) {
		this.statusManager = statusManager;
	}

	public void setTilemanager(Tilemanager tilemanager) {
		this.tilemanager = tilemanager;
	}

}
