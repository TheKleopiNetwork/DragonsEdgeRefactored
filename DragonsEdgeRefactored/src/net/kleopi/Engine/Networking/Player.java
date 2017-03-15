package net.kleopi.Engine.Networking;

import java.io.Serializable;

import com.esotericsoftware.kryonet.Connection;

@SuppressWarnings("serial")
public class Player implements Serializable {

	private Connection connection;

	public Player(Connection c) {
		this.connection = c;
	}

	public Connection getConnection() {
		return connection;
		
	}
}
