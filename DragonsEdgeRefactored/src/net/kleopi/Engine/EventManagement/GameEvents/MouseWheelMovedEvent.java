/**
 *
 */
package net.kleopi.Engine.EventManagement.GameEvents;

import java.awt.event.MouseWheelEvent;

public class MouseWheelMovedEvent extends GameEvent {
	private MouseWheelEvent event;

	/**
	 *
	 */
	public MouseWheelMovedEvent(MouseWheelEvent e) {
		setEvent(e);
	}

	public MouseWheelEvent getEvent() {
		return event;
	}

	public void setEvent(MouseWheelEvent event) {
		this.event = event;
	}
}
