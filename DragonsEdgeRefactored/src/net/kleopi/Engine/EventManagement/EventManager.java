package net.kleopi.Engine.EventManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.GameEvent;
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
	 * @param e
	 *            - Object to Fire //TODO: rename
	 * @throws UnregisteredEventException
	 *             - when Object is not a valid Object, or Event was not added
	 *             to the Manager yet
	 */
	private void dispatch(Object e) throws UnregisteredEventException {
		// TODO add rest of events + check for UpdateObject first
		if (e instanceof TickEvent) {
			listeners.forEach(l -> l.onTick((TickEvent) e));
			System.out.println("Dispatched TickEvent");
		}

		else if (e instanceof PingEvent) {
			listeners.forEach(l -> l.onPing((PingEvent) e));
			System.out.println("Dispatched PingEvent");
		} else if (e instanceof StartupEvent) {
			listeners.forEach(l -> l.onStartUp((StartupEvent) e));
			System.out.println("Dispatched StartupEvent");
		} else if (e instanceof LoginEvent) {
			listeners.forEach(l -> l.onLogin((LoginEvent) e));
			System.out.println("Dispatched LoginEvent");
		} else if (e instanceof TickEvent) {
			listeners.forEach(l -> l.onTick((TickEvent) e));
			System.out.println("Dispatched TickEvent");
		} else if (e instanceof DrawEvent) {
			listeners.forEach(l -> l.onDraw((DrawEvent) e));
			System.out.println("Dispatched DrawEvent");
		} else if (e instanceof DisconnectEvent) {
			listeners.forEach(l -> l.onDisconnect((DisconnectEvent) e));
			System.out.println("Dispatched DisconnectEvent");
		} else {
			System.err.println("Received an Unknown Event - Not added to the EventManager?");
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
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
