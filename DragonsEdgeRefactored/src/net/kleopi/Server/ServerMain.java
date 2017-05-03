package net.kleopi.Server;

import net.kleopi.Engine.EventManagement.EventManager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;

/**
 * Contains the Main server architecture with all managers
 */

public class ServerMain implements TKNListenerAdapter {

	private static ServerMain server;

	/**
	 *
	 * @return static Server Instance to adress all managers
	 */
	public static ServerMain getServer() {
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
		setServer(new ServerMain());
		server.setEventManager(new EventManager());
		server.getEventManager().addListener(server);
		server.setTilemanager(new TileManagerServer());
		server.setInstancemanager(new InstancemanagerServer());
		server.getEventManager().fire(new StartupEvent());
		server.setNetwork(new NetworkServer());
	}

	/**
	 * Sets a new static ServerInstance. Only call upon startup!
	 *
	 * @param server
	 *            - the new ServerInstance
	 */
	public static void setServer(ServerMain server) {
		ServerMain.server = server;
	}

	private NetworkServer network;

	private EventManager eventManager;
	private TileManagerServer tilemanager;

	private InstancemanagerServer instancemanager;

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
	public InstancemanagerServer getInstancemanager() {
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
	 * @return TileManager Instance
	 */
	public TileManagerServer getTilemanager() {
		return tilemanager;
	}

	@Override
	public void onStartUp(StartupEvent e) {

		tilemanager.setDatamap(TileManagerServer.getDefaultMap());

	}

	public void setEventManager(EventManager eventManager) {
		this.eventManager = eventManager;
	}

	public void setInstancemanager(InstancemanagerServer instancemanager) {
		this.instancemanager = instancemanager;
	}

	public void setNetwork(NetworkServer network) {
		this.network = network;
	}

	public void setTilemanager(TileManagerServer tilemanager) {
		this.tilemanager = tilemanager;
	}

}
