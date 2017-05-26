package net.kleopi.Engine.Networking;

import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

@SuppressWarnings("serial")
public class Player implements Serializable {

	public static Player getNeutralPlayer() {
		return new Player(null);
	}

	private Connection connection;

	public Player() {
		// kryonet only - use the other constructor or use withData
	}

	public Player(Connection c) {
		this.connection = c;
	}

	public Connection getConnection() {
		return connection;

	}

	public void withData(Connection c) {
		connection = c;
	}
}
