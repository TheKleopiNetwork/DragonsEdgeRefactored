/**
 *
 */
package net.kleopi.Engine.EventManagement.GameEvents;

import java.awt.event.MouseEvent;

public class MouseClickedEvent extends GameEvent {
	private MouseEvent event;

	/**
	 *
	 */
	public MouseClickedEvent(MouseEvent e) {
		setEvent(e);
	}

	public MouseEvent getEvent() {
		return event;
	}

	public void setEvent(MouseEvent event) {
		this.event = event;
	}
}
