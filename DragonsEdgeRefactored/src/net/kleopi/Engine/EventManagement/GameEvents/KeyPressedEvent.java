/**
 *
 */
package net.kleopi.Engine.EventManagement.GameEvents;

import java.awt.event.KeyEvent;

public class KeyPressedEvent extends GameEvent {
	private KeyEvent keyevent;

	public KeyPressedEvent(KeyEvent e) {
		setKeyevent(e);
	}

	public KeyEvent getKeyevent() {
		return keyevent;
	}

	public void setKeyevent(KeyEvent keyevent) {
		this.keyevent = keyevent;
	}

}
