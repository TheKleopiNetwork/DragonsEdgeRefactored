package net.kleopi.Server;

import net.kleopi.Engine.Enums.Tilemanager;
import net.kleopi.Engine.EventManagement.EventManager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.Instances.Instancemanager;
import net.kleopi.Engine.StatusManagement.StatusManager;

/**
 * Contains the Main server architecture with all managers
 */

public class MainServer implements TKNListenerAdapter {

	private static MainServer server;

	/**
	 *
	 * @return static Server Instance to adress all managers
	 */
	public static MainServer getServer() {
		return server;
	}

	/**
	 * This sets up the whole Server environment including all managers and the
	 * static ServerInstance
	 *
	 * @param args
	 *            - should be blank
	 */
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

	/**
	 * Sets a new static ServerInstance. Only call upon startup!
	 *
	 * @param server
	 *            - the new ServerInstance
	 */
	public static void setServer(MainServer server) {
		MainServer.server = server;
	}

	private StatusManager statusManager;
	private NetworkServer network;

	private EventManager eventManager;
	private Tilemanager tilemanager;

	private Instancemanager instancemanager;

	/**
	 *
	 * @return EventManager Instance
	 */
	public EventManager getEventManager() {
		return eventManager;
	}

	/**
	 *
	 * @return InstanceManager Instance
	 */
	public Instancemanager getInstancemanager() {
		return instancemanager;
	}

	/**
	 *
	 * @return NetworkManager Instance
	 */
	public NetworkServer getNetwork() {

		return network;
	}

	/**
	 *
	 * @return StatusManager Instance
	 */

	public StatusManager getStatusManager() {
		return statusManager;
	}

	/**
	 *
	 * @return TileManager Instance
	 */
	public Tilemanager getTilemanager() {
		return tilemanager;
	}

	@Override
	public void onStartUp(StartupEvent e) {

		tilemanager.setDatamap(Tilemanager.getDefaultMap());

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
