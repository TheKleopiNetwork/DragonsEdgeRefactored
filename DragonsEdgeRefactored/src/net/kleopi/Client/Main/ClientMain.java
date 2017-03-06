package net.kleopi.Client.Main;

import net.kleopi.Client.GUI.GUI;
import net.kleopi.Client.GUI.PreLoaded;
import net.kleopi.Client.Networking.NetworkClient;
import net.kleopi.Engine.Enums.Tilemanager;
import net.kleopi.Engine.EventManagement.EventManager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.Instances.Instancemanager;
import net.kleopi.Engine.StatusManagement.StatusManager;

public class ClientMain implements TKNListenerAdapter {

	private static ClientMain client;

	public static ClientMain getClient() {
		return client;
	}

	/**
	 * Set up the Client environment with all managers
	 * 
	 * @param args
	 *            - Leave blank!
	 */
	public static void main(String[] args) {
		setClient(new ClientMain());

		client.setEventManager(new EventManager());
		client.getEventManager().addListener(client);
		client.setStatusManager(new StatusManager());
		client.setNetwork(new NetworkClient());
		client.setPreloaded(new PreLoaded());
		client.setTilemanager(new Tilemanager());
		client.setInstancemanager(new Instancemanager());
		client.setGui(new GUI());
		client.getEventManager().fire(new StartupEvent());

	}

	public static void setClient(ClientMain client) {
		ClientMain.client = client;
	}

	private GUI gui;

	private StatusManager statusManager;
	private NetworkClient network;

	private EventManager eventManager;
	private PreLoaded preloaded;

	private Tilemanager tilemanager;

	private Instancemanager instancemanager;

	public EventManager getEventManager() {
		return eventManager;
	}

	public GUI getGui() {
		return gui;
	}

	public Instancemanager getInstancemanager() {
		return instancemanager;
	}

	public NetworkClient getNetwork() {
		return network;
	}

	public PreLoaded getPreloaded() {
		return preloaded;
	}

	public StatusManager getStatusManager() {
		return statusManager;
	}

	public Tilemanager getTilemanager() {
		return tilemanager;
	}

	public void setEventManager(EventManager man) {
		eventManager = man;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public void setInstancemanager(Instancemanager instancemanager) {
		this.instancemanager = instancemanager;
	}

	public void setNetwork(NetworkClient network) {
		this.network = network;
	}

	public void setPreloaded(PreLoaded preloaded) {
		this.preloaded = preloaded;
	}

	public void setStatusManager(StatusManager statusManager) {
		this.statusManager = statusManager;
	}

	public void setTilemanager(Tilemanager tilemanager) {
		this.tilemanager = tilemanager;
	}

}
