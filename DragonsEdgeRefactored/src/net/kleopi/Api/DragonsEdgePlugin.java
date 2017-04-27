package net.kleopi.Api;
public interface DragonsEdgePlugin {
	/**
	 *
	 * @return Name of the Plugin
	 */
	String getName();

	/**
	 * called when the Plugin is disabled
	 */
	void onDisable();

	/**
	 * called when the Plugin is enabled
	 */
	void onEnable();

}
