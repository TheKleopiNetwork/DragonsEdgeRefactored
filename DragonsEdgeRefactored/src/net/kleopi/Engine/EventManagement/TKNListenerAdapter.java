package net.kleopi.Engine.EventManagement;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PingEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PlayerJoinedTheGameEvent;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;

/**
 * Interface used to Listen to some GameEvents
 *
 */
public interface TKNListenerAdapter {

	default void onDisconnect(DisconnectEvent e) {
	}

	default void onDraw(DrawEvent e) {
	}

	default void onKeyPressed(KeyEvent e) {
	}

	default void onLogin(LoginEvent e) {
	}

	default void onMouseClick(MouseEvent e) {
	}

	/**
	 * @param e
	 */
	default void onMouseWheelAction(MouseWheelEvent e) {

	};

	default void onPackageReceived(PackageReceivedEvent e) {
	}

	default void onPing(PingEvent e) {
	}

	default void onPlayerJoined(PlayerJoinedTheGameEvent e) {
	}

	default void onPreDraw(DrawEvent object) {
	}

	default void onStartUp(StartupEvent e) {
	}

	default void onSuccessfulLoginReturn(LoggedEvent object) {
	};

	default void onTick(TickEvent e) {
	}
}
