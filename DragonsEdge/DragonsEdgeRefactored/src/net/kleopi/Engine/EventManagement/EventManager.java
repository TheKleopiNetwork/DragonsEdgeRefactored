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

	public EventManager() {
		start();
	}

	public void addListener(TKNListenerAdapter listener) {
		listeners.add(listener);
	}

	private void dispatch(Object e) throws UnregisteredEventException {
		// TODO add rest of events
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

	public void dispatchNextEvent() throws UnregisteredEventException {
		while (!eventqueue.isEmpty()) {
			dispatch(eventqueue.remove());
		}
	}

	public void fire(GameEvent e) {
		queue(e);
	}

	public List<TKNListenerAdapter> getListeners() {
		return listeners;
	}

	private void queue(GameEvent e) {
		eventqueue.add(e);
	}

	public void removeListener(TKNListenerAdapter listener) {
		listeners.remove(listener);
	}

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
