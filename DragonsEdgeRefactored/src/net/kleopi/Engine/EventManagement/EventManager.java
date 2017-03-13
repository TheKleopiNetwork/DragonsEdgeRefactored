package net.kleopi.Engine.EventManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import net.kleopi.Client.GUI.Messager;
import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.GameEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PingEvent;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Exceptions.UnregisteredEventException;

public class EventManager extends Thread {
	private Queue<Object> eventqueue = new LinkedBlockingQueue<>();
	private List<TKNListenerAdapter> listeners = new ArrayList<>();

	/**
	 * Starts Itself!
	 */
	public EventManager() {
		start();
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
	 * Tries to dispatch the event fitting to the provided Object
	 *
	 * @param object
	 *            - Object to Fire
	 * @throws UnregisteredEventException
	 *             - when Object is not a valid Object, or Event was not added
	 *             to the Manager yet
	 */
	private void dispatch(Object object) throws UnregisteredEventException {
		// TODO add rest of events + check for UpdateObject first
		if (object instanceof TickEvent) {
			listeners.forEach(l -> l.onTick((TickEvent) object));
			System.out.println("Dispatched TickEvent");
		}

		else if (object instanceof PingEvent) {
			listeners.forEach(l -> l.onPing((PingEvent) object));
			System.out.println("Dispatched PingEvent");
		} else if (object instanceof StartupEvent) {
			listeners.forEach(l -> l.onStartUp((StartupEvent) object));
			System.out.println("Dispatched StartupEvent");
		} else if (object instanceof LoginEvent) {
			listeners.forEach(l -> l.onLogin((LoginEvent) object));
			System.out.println("Dispatched LoginEvent");
		} else if (object instanceof TickEvent) {
			listeners.forEach(l -> l.onTick((TickEvent) object));
			System.out.println("Dispatched TickEvent");
		} else if (object instanceof DrawEvent) {
			listeners.forEach(l -> l.onDraw((DrawEvent) object));
			System.out.println("Dispatched DrawEvent");
		} else if (object instanceof DisconnectEvent) {
			listeners.forEach(l -> l.onDisconnect((DisconnectEvent) object));
			Messager.info("Dispatched DisconnectEvent");
		} else if (object instanceof LoggedEvent) {
			listeners.forEach(l -> l.onSuccessfulLoginReturn((LoggedEvent) object));
			Messager.info("Dispatched LoggedEvent");
		} else {
			Messager.error("Received an Unknown Event - Not added to the EventManager?");
			throw new UnregisteredEventException();
		}

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

	private void queue(GameEvent e) {
		Messager.info("Enqueued GameEvent");
		eventqueue.add(e);
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
			try {
				dispatchNextEvent();
			} catch (UnregisteredEventException e1) {
				e1.printStackTrace();
			}
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
