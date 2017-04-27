package net.kleopi.Api;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;

public class ExamplePlugin implements DragonsEdgePlugin, TKNListenerAdapter {
	// The Name of the Class is not necessary. Important is, that you let your
	// class implement the DragonsEdgePlugin, else the PluginManager ignores
	// this class. Therefore subclasses of this Plugin should NOT implement this
	// interface, or they will get treated as another plugin! The
	// TKNListenerAdapter is optional and also allowed in subclasses. It
	// provides the ability to listen to GameEvents. Dont forget to register
	// your Listener in every class and subclass!

	@Override
	public String getName() {
		return "ExamplePlugin";
		// Returns the Name of this Plugin. Used for Error logs etc. I recommend
		// changing it to a fitting name
	}

	@Override
	public void onDisable() {
		System.out.println("Ciao!");
		// Prints a message when the Client disables this plugin. This Event is
		// commonly used for saving data before a crash or when shutting the
		// program down in any other way
	}

	@Override
	public void onDraw(DrawEvent e) {
		System.out.println("I am drawing now!");
		// Event reaction: prints "I am drawing now" everytime the Client draws.
		// I recommend not to do this as the Client draws 30 times a second.
		//
		// This Method is provided by the Event manager which needs
		// "TKNListenerAdapter" implemented in your Plugin and additionally has
		// to be registered to the Manager in "onEnable" (or any other method
		// which
		// gets called BEFORE the event is needed)

	}

	@Override
	public void onEnable() {
		System.out.println("Hello World!");
		// You should know the "Hello World" program. It gets printed when the
		// Client activates the Plugin
		System.out.println(ClientMain.getClient().getTilemanager().getDatamap().getData(1, 1, 0).getShortcut());
		// Prints the shortcut of the Tile at position 1|1 on the ground (layer
		// 0)
		// Example: Prints "S" when Tile 1|1 is a Stonetile
		ClientMain.getClient().getEventManager().addListener(this);
		// Registers to the EventManager so you can react on "GameEvents", like
		// the "DrawEvent". Events dont have to be added, not added Events just
		// don't do anything. (The in this ExamplePlugin unused onTick() called
		// 30 times a second, is fully ignored e.g)

	}
	// To activate your plugin, export it as JAR File (NOT runnable jar) and put
	// the jarfile into the plugins folder -> done

}
