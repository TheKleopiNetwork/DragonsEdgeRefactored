package net.kleopi.Engine.EventManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.GameEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PingEvent;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Exceptions.UnregisteredEventException;

public class EventManager extends Thread {
	private final Queue<Object> eventqueue = new LinkedBlockingQueue<>();
	private final List<TKNListenerAdapter> listeners = new ArrayList<>();

	/**
	 * Starts Itself!
	 */

	public EventManager() {
		start();
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				try {
					dispatch(new TickEvent());
				} catch (UnregisteredEventException e) {
				}

			}
		}, 0, 33);
	}

	/**
	 * Register your Listener here
	 *
	 * @param listener
	 *            - the Listener to add
	 */
	public void addListener(TKNListenerAdapter listener) {
		listeners.add(listener);
	}

	/**
	 * Dispatches next queued Event
	 *
	 * @throws UnregisteredEventException
	 *             -> dispatch
	 */
	public void dispatchNextEvent() throws UnregisteredEventException {
		while (!eventqueue.isEmpty()) {
			dispatch(eventqueue.remove());
		}
	}

	/**
	 * Enqueue provided GameEvent to fire when possible
	 *
	 * @param e
	 *            - Event to enqueue
	 */
	public void fire(GameEvent e) {
		queue(e);
	}

	/**
	 *
	 * @return List of all registered Listeners
	 */
	public List<TKNListenerAdapter> getListeners() {
		return listeners;
	}

	/**
	 * Unregisters provided Listener
	 *
	 * @param listener
	 *            - Listener to remove
	 */
	public void removeListener(TKNListenerAdapter listener) {
		listeners.remove(listener);
	}

	/**
	 * Try to dispatch the events
	 */
	@Override
	public void run() {
		while (true) {
			if (!eventqueue.isEmpty()) {
				try {
					dispatchNextEvent();
				} catch (UnregisteredEventException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					sleep(10);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * Tries to dispatch the event fitting to the provided Object
	 *
	 * @param object
	 *            - Object to Fire
	 * @throws UnregisteredEventException
	 *             - when Object is not a valid Object, or Event was not added
	 *             to the Manager yet
	 */
	private synchronized void dispatch(Object object) throws UnregisteredEventException {
		if (object instanceof GameEvent) {
			if (object instanceof TickEvent) {
				listeners.forEach(l -> l.onTick((TickEvent) object));
				// Messager.info("Dispatched TickEvent");
			} else if (object instanceof PingEvent) {
				listeners.forEach(l -> l.onPing((PingEvent) object));
				Messager.info("Dispatched PingEvent");
			} else if (object instanceof StartupEvent) {
				listeners.forEach(l -> l.onStartUp((StartupEvent) object));
				Messager.info("Dispatched StartupEvent");
			} else if (object instanceof LoginEvent) {
				listeners.forEach(l -> l.onLogin((LoginEvent) object));
				Messager.info("Dispatched LoginEvent");
			} else if (object instanceof DrawEvent) {
				listeners.forEach(l -> l.onDraw((DrawEvent) object));
				Messager.info("Dispatched DrawEvent");
				((DrawEvent) object).getGraphics().dispose();
			} else if (object instanceof DisconnectEvent) {
				listeners.forEach(l -> l.onDisconnect((DisconnectEvent) object));
				Messager.info("Dispatched DisconnectEvent");
			} else if (object instanceof LoggedEvent) {
				listeners.forEach(l -> l.onSuccessfulLoginReturn((LoggedEvent) object));
				Messager.info("Dispatched LoggedEvent");
			} else if (object instanceof PackageReceivedEvent) {
				listeners.forEach(l -> l.onPackageReceived((PackageReceivedEvent) object));
				Messager.info("Dispatched PackageReceivedEvent");
			} else {
				Messager.error("Received an Unknown Event - Not added to the EventManager?");
				throw new UnregisteredEventException();
			}
		} else {
			Messager.error("Received an Object wihich could not be identified as GameEvent.");
		}

	}

	private void queue(GameEvent e) {
		Messager.info("Enqueued GameEvent");
		eventqueue.add(e);
	}
}
