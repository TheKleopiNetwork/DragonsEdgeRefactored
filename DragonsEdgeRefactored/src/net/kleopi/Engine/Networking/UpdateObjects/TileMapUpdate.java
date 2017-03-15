package net.kleopi.Engine.Networking.UpdateObjects;

@SuppressWarnings("serial")
public class TileMapUpdate extends UpdateObject {
	private char[][] compressedTileMap;

	public TileMapUpdate() {
	}

	public char[][] getCompressedTilemap() {
		return compressedTileMap;
	}

	public TileMapUpdate withCompressedTilemap(char[][] compressedmap) {
		this.compressedTileMap=compressedmap;
		return this;
	}
}
