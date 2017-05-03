package net.kleopi.Engine.EventManagement.GameEvents;

import java.awt.Graphics2D;

public class DrawEvent extends GameEvent {
	private Graphics2D tilelayer;
	private Graphics2D spritelayer;

	private Graphics2D shaderlayer;

	private Graphics2D hudlayer;

	public DrawEvent(Graphics2D tile, Graphics2D sprite, Graphics2D shader, Graphics2D hud) {
		setTilelayer(tile);
		setSpritelayer(sprite);
		setShaderlayer(shader);
		setHudlayer(hud);
	}

	public Graphics2D getHudlayer() {
		return hudlayer;
	}

	public Graphics2D getShaderlayer() {
		return shaderlayer;
	}

	public Graphics2D getSpritelayer() {
		return spritelayer;
	}

	public Graphics2D getTilelayer() {
		return tilelayer;
	}

	public void setHudlayer(Graphics2D hudlayer) {
		this.hudlayer = hudlayer;
	}

	public void setShaderlayer(Graphics2D shaderlayer) {
		this.shaderlayer = shaderlayer;
	}

	public void setSpritelayer(Graphics2D spritelayer) {
		this.spritelayer = spritelayer;
	}

	public void setTilelayer(Graphics2D tilelayer) {
		this.tilelayer = tilelayer;
	}
}
