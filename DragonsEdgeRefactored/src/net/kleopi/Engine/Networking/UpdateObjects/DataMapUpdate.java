package net.kleopi.Engine.Networking.UpdateObjects;

import net.kleopi.Engine.Enums.Tilemap;

@SuppressWarnings("serial")
public class DataMapUpdate extends UpdateObject {
	private Tilemap tilemap;

	public DataMapUpdate(Tilemap map) {
		setDatamap(map);
	}

	public Tilemap getDatamap() {
		return tilemap;
	}

	public void setDatamap(Tilemap tilemap) {
		this.tilemap = tilemap;
	}
}
