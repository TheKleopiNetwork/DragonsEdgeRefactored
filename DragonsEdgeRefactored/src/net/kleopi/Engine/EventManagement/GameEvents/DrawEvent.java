package net.kleopi.Engine.EventManagement.GameEvents;

import java.awt.Graphics;

public class DrawEvent extends GameEvent {
	private Graphics g;

	public DrawEvent(Graphics g) {
		setGraphics(g);
	}

	public Graphics getGraphics() {
		return g;
	}

	public void setGraphics(Graphics g) {
		this.g = g;
	}

}
