package net.kleopi.Server;

import java.util.Random;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Tilemap;
import net.kleopi.Engine.Enums.Tiletype;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;

public class TileManagerServer implements TKNListenerAdapter {
	private static final int tilexsize = 1000;
	private static final int tileysize = 1000;
	private static final int overlaydepth = 10;

	// TODO: add Events and register the Listener!
	/**
	 * DOES NOT SET THE MAP. ONLY RETURNS DEFAULT MAP!
	 *
	 * @return Default map, filled with random Tiles TODO: better generation?
	 *         TODO: Maybe also SET the map in Tilemanager >.>
	 */
	public static Tilemap getDefaultMap() {

		Tilemap map = new Tilemap(tilexsize, tileysize, 3);
		// main layer
		for (int x = 0; x < tilexsize; x++) {
			for (int y = 0; y < tileysize; y++) {
				if (Math.random() > 0.66) {
					map.setData(x, y, 0, Tiletype.WATER);
				} else if (Math.random() > 0.33) {
					map.setData(x, y, 0, Tiletype.STONE);
				} else {
					map.setData(x, y, 0, Tiletype.DIRT);
				}
			}
		}
		// make the map a bit chunkier and cleaner
		Random r = new Random();
		for (int a = 0; a < 25000000; a++) {
			int x = r.nextInt(tilexsize);
			int y = r.nextInt(tileysize);
			refineTile(map, x, y);
		}
		return map;
	}

	/**
	 * @param x
	 * @param y
	 */
	private static void refineTile(Tilemap map, int x, int y) {
		int chunksize = 3;
		int stonearound = 0;
		int wateraround = 0;
		int dirtaround = 1;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (map.getData(x + i, y + j, 0) == Tiletype.DIRT) {
					dirtaround++;
					continue;
				}
				if (map.getData(x + i, y + j, 0) == Tiletype.STONE) {
					stonearound++;
					continue;
				}
				if (map.getData(x + i, y + j, 0) == Tiletype.WATER) {
					wateraround++;
					continue;
				}
			}
		}
		if (stonearound >= wateraround && stonearound >= dirtaround && stonearound >= chunksize) {
			map.setData(x, y, 0, Tiletype.STONE);
		}
		if (dirtaround >= wateraround && dirtaround >= stonearound && dirtaround >= chunksize) {
			map.setData(x, y, 0, Tiletype.DIRT);
		}
		if (wateraround >= stonearound && wateraround >= dirtaround && wateraround >= chunksize) {
			map.setData(x, y, 0, Tiletype.WATER);
		}

	}

	public boolean hasMap = false;

	private Tilemap datamap;

	public TileManagerServer() {

		try {
			ClientMain.getClient().getEventManager().addListener(this);
		} catch (NullPointerException e) {
		}
		try {
			ServerMain.getServer().getEventManager().addListener(this);
		} catch (NullPointerException e) {
		}
		setDatamap(getDefaultMap());

	}

	public char[][] getCompressedDataMap() {
		char[][] compressedMap = new char[tilexsize][tileysize];
		// TODO: fill Array
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				compressedMap[i][j] = datamap.getData(i, j, 0).getShortcut().charAt(0);
			}
		}
		return compressedMap;
	}

	/**
	 *
	 * @return current Datamap
	 */
	public Tilemap getDatamap() {

		return datamap;
	};

	public void setCompressedDatamap(char[][] compressedTilemap) {
		Tilemap newmap = new Tilemap(tilexsize, tileysize, overlaydepth);
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				newmap.setData(i, j, 0, Tiletype.getTiletypeOfShortcut("" + compressedTilemap[i][j]));
			}
		}
	}

	/**
	 * Sets a new Datamap and recalculates its overlays. Info: very
	 * ressourceintensive. Use with caution!
	 *
	 * @param new_datamap
	 *            - Tilemap to set as map
	 */
	public void setDatamap(Tilemap new_datamap) {

		datamap = new_datamap;
		datamap.calculateOverlays();
		hasMap = true;
	}

	/**
	 * Changes tile at a specific position. TODO: update Overlays
	 *
	 * @param x
	 * @param y
	 * @param shortcut
	 *            - shortcut of Tile TODO: use Tiletype enum here
	 */
	public void updateTile(int x, int y, String shortcut) {

		getDatamap().setData(x, y, 0, Tiletype.getTiletypeOfShortcut(shortcut));
	}

}
