package net.kleopi.Engine.EventManagement.GameEvents;

import net.kleopi.Engine.Networking.Player;

public class PlayerJoinedTheGameEvent extends GameEvent {
	private Player player;

	public PlayerJoinedTheGameEvent(Player p) {
		setPlayer(p);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
