package net.kleopi.Engine.Enums;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import net.kleopi.Client.GUI.GUI;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Server.MainServer;

public class Tilemanager implements TKNListenerAdapter {
	private static final int tilexsize = 1000;
	private static final int tileysize = 1000;
	private static final int camspeed = 8;
	private static final int overlaydepth = 10;
	private BufferedImage tileCacheImage = new BufferedImage(GUI.RESOLUTION_WIDTH, GUI.RESOLUTION_HEIGTH, 1);
	private boolean tilesChanged = true;

	
	public Tilemanager() {
		
		try {
			ClientMain.getClient().getEventManager().addListener(this);
		} catch (NullPointerException e) {
		}
		try {
			MainServer.getServer().getEventManager().addListener(this);
		}catch (NullPointerException e) {
		}

	}
	//TODO: add Events and register the Listener!
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
				if (Math.random() > 0.8) {
					map.setData(x, y, 0, Tiletype.WATER);
				} else if (Math.random() > 0.6) {
					map.setData(x, y, 0, Tiletype.STONE);
				} else {
					map.setData(x, y, 0, Tiletype.DIRT);
				}
			}
		}
		return map;
	}

	public boolean hasMap = false;
	private Tilemap datamap;
	private int tilesize = 32;
	public int viewx, viewy;

	@Override
	public void onDraw(DrawEvent e) {
		if (hasMap){
			if (tilesChanged)
				{
					cacheMap();
					tilesChanged=false;
				}
			drawCache(e.getGraphics());
		}
		//TODO: cameraTest only remove when test-build done
		moveRight();
		moveDown();
		zoom(-1);
	}

	private void drawCache(Graphics g) {
		g.drawImage(tileCacheImage, 1, 1, null);
	}
	private void cacheMap() {
		tileCacheImage.getGraphics().clearRect(0, 0, GUI.RESOLUTION_WIDTH, GUI.RESOLUTION_HEIGTH);
		for (int i = 0; i < 9; i++) {
			drawCacheTileLayer(i, tileCacheImage.getGraphics());
	}
		
	}
	private void drawTileAtPos(double x, double y, String tileID, Graphics g) {

		if (!tileID.equals("") && ClientMain.getClient().getPreloaded().getTile(tileID) != null) {
			g.drawImage(ClientMain.getClient().getPreloaded().getTile(tileID), (int) x, (int) y, getTilesize(),
					getTilesize(), null);
		}
	}

	private void drawCacheTileLayer(int layer, Graphics g) {
		for (double i = -(viewx % getTilesize()); i <= GUI.RESOLUTION_WIDTH; i += getTilesize()) {
			for (double j = -(viewy % getTilesize()); j <= GUI.RESOLUTION_HEIGTH; j += getTilesize()) {
				drawTileAtPos(i, j, getTileAtPosition(i + viewx, j + viewy, layer).getPath(), g);
			}
		}
		
	}

	private void fixview() {

		viewx = Math.min((tilexsize - 1) * getTilesize() - GUI.RESOLUTION_WIDTH, viewx);
		viewy = Math.min((tilexsize - 1) * (getTilesize()) - GUI.RESOLUTION_HEIGTH, viewy);
		viewx = Math.max(0, viewx);
		viewy = Math.max(0, viewy);
		tilesChanged=true;

	}

	/**
	 *
	 * @return current Datamap
	 */
	public Tilemap getDatamap() {

		return datamap;
	}

	/**
	 * To get the type of Tile at a specific position
	 *
	 * @param x
	 *            - xCoordinate of needed tile
	 * @param y
	 *            - yCoordinate of needed tile
	 * @param layer
	 *            - layer where tile is drawn. 0 for base tile >0 for
	 *            corner/side tile overlays
	 * @return Tiletype at given coordinates in layer
	 */
	public Tiletype getTileAtPosition(double x, double y, int layer) {

		return getDatamap().getData((int) (x / getTilesize()), (int) (y / getTilesize()), layer);
	}

	private int getTilesize() {

		return tilesize;
	}

	// TODO: on client side use listener to move view
	@Deprecated
	public void moveDown() {

		viewy += camspeed;
		fixview();

	}

	@Deprecated
	public void moveLeft() {

		viewx -= camspeed;
		fixview();
	}

	@Deprecated
	public void moveRight() {

		viewx += camspeed;
		fixview();
	}

	@Deprecated
	public void moveUp() {

		viewy -= camspeed;
		fixview();
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

	private void setTilesize(int tilesize) {

		this.tilesize = tilesize;
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

	private void zoom(int movement) {

		// TODO: fix bug: camera does not match the zoom
		setTilesize(getTilesize() + movement);
		if (getTilesize() > 128) {
			setTilesize(128);
		} else if (getTilesize() < 16) {
			setTilesize(16);
		}
	}

	public char[][] getCompressedDataMap() {
		char[][] compressedMap=new char[tilexsize][tileysize];
		//TODO: fill Array
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				compressedMap[i][j]=getTileAtPosition(i, j, 0).getShortcut().charAt(0);
			}
		}
		return compressedMap;
	}

	public void setCompressedDatamap(char[][] compressedTilemap) {
		Tilemap newmap = new Tilemap(tilexsize, tileysize, overlaydepth);
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				newmap.setData(i, j, 0,Tiletype.getTiletypeOfShortcut(""+compressedTilemap[i][j] ));
			}
		}
	}

}
