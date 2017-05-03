package net.kleopi.Client.GUI;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Tilemap;
import net.kleopi.Engine.Enums.Tiletype;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;

public class TileManagerClient implements TKNListenerAdapter {
	private static final int tilexsize = 1000;
	private static final int tileysize = 1000;
	private static final int overlaydepth = 10;

	private final BufferedImage tileCacheImage = new BufferedImage(GUI.RESOLUTION_WIDTH, GUI.RESOLUTION_HEIGTH,
			BufferedImage.TYPE_3BYTE_BGR);

	private boolean tilesChanged = true;
	public boolean hasMap = false;

	private Tilemap datamap;
	private int tilesize = 32;
	public int viewx = 0, viewy = 0;
	private int viewxsnap;
	private int viewysnap;

	public TileManagerClient() {

		ClientMain.getClient().getEventManager().addListener(this);
	}

	public char[][] getCompressedDataMap() {
		char[][] compressedMap = new char[tilexsize][tileysize];
		// TODO: fill Array
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				compressedMap[i][j] = getTileAtPosition(i, j, 0).getShortcut().charAt(0);
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

		Tiletype t = getDatamap().getData((int) (x / getTilesize()), (int) (y / getTilesize()), layer);
		if (t == null) {
			return Tiletype.NOTILE;
		} else {
			return t;
		}
	}

	public int getTilesize() {

		return tilesize;
	}

	public void moveView(int x, int y) {
		int vxp = viewx;
		int vyp = viewy;
		viewx += x;
		viewy += y;
		// fix script
		viewx = Math.min((tilexsize - 1) * getTilesize() - GUI.RESOLUTION_WIDTH, viewx);
		viewy = Math.min((tilexsize - 1) * (getTilesize()) - GUI.RESOLUTION_HEIGTH, viewy);
		viewx = Math.max(0, viewx);
		viewy = Math.max(0, viewy);
		if (viewx == vxp && viewy == vyp) {
		} else {
			tilesChanged = true;
		}

	}

	@Override
	public void onDraw(DrawEvent e) {
		viewxsnap = viewx;
		viewysnap = viewy;
		if (hasMap) {
			if (tilesChanged) {
				cacheMap();
				tilesChanged = false;
			}
			drawCache(e.getTilelayer());
		}
	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			moveView(-16, 0);
		}
		if (e.getKeyCode() == KeyEvent.VK_W) {
			moveView(0, -16);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			moveView(0, 16);
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			moveView(16, 0);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onMouseAction(java.
	 * awt.event.MouseEvent)
	 */
	@Override
	public void onMouseWheelAction(MouseWheelEvent e) {
		zoom((int) -e.getPreciseWheelRotation());
	}

	public void setCompressedDatamap(char[][] compressedTilemap) {
		Tilemap newmap = new Tilemap(tilexsize, tileysize, overlaydepth);
		for (int i = 0; i < tilexsize; i++) {
			for (int j = 0; j < tileysize; j++) {
				newmap.setData(i, j, 0, Tiletype.getTiletypeOfShortcut("" + compressedTilemap[i][j]));
			}
		}
		setDatamap(newmap);
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

	private void cacheMap() {
		tileCacheImage.getGraphics().clearRect(0, 0, GUI.RESOLUTION_WIDTH, GUI.RESOLUTION_HEIGTH);
		drawCacheTileLayer(0, tileCacheImage.getGraphics());
		if (tilesize > 16) {
			for (int i = 1; i < 9; i++) {
				drawCacheTileLayer(i, tileCacheImage.getGraphics());
			}
		}

	}

	private void drawCache(Graphics g) {
		g.drawImage(tileCacheImage, 0, 0, null);
	}

	private void drawCacheTileLayer(int layer, Graphics g) {
		for (double i = -(viewxsnap % getTilesize()); i <= GUI.RESOLUTION_WIDTH; i += getTilesize()) {
			for (double j = -(viewysnap % getTilesize()); j <= GUI.RESOLUTION_HEIGTH; j += getTilesize()) {
				drawTileAtPos(i, j, getTileAtPosition(i + viewxsnap, j + viewysnap, layer).getPath(), g);
			}
		}

	}

	private void drawTileAtPos(double x, double y, String tileID, Graphics g) {

		if (!tileID.equals("") && ClientMain.getClient().getPreloaded().getTile(tileID) != null) {
			g.drawImage(ClientMain.getClient().getPreloaded().getTile(tileID), (int) x, (int) y, getTilesize(),
					getTilesize(), null);
		}
	}

	private void setTilesize(int tilesize) {

		this.tilesize = tilesize;
	}

	private void zoom(int movement) {

		// TODO: fix bug: camera does not match the zoom
		int ptilesize = getTilesize();
		setTilesize(getTilesize() + movement);
		if (getTilesize() > 128) {
			setTilesize(128);
		} else if (getTilesize() < 6) {
			setTilesize(6);
		}
		if (ptilesize == getTilesize()) {

		} else {
			tilesChanged = true;
			viewx = viewx * getTilesize() / ptilesize;
			viewy = viewy * getTilesize() / ptilesize;
		}

	}

}
