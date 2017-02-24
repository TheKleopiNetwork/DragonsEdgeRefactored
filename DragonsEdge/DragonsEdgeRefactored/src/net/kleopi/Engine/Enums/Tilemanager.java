package net.kleopi.Engine.Enums;

import java.awt.Graphics;

import net.kleopi.Client.GUI.GUI;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.PackageSendEvent;
import net.kleopi.Engine.Networking.UpdateObjects.DataMapUpdate;

public class Tilemanager implements TKNListenerAdapter {
	private static final int tilexsize = 1000;
	private static final int tileysize = 1000;
	private static final int camspeed = 32;

	public static Tilemap setDefaultMap() {

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
		System.out.println("Initialized blank map");
		return map;
	}

	public boolean hasMap = false;
	private Tilemap datamap;
	private int tilesize = 32;
	public int viewx, viewy;

	public void drawEvent(Graphics g) {

		for (int i = 0; i < 9; i++) {
			if (hasMap) {
				drawTiles(i, g);
			}
		}

	}

	public void drawTileAtPos(double x, double y, String tileID, Graphics g) {

		if (!tileID.equals("") && ClientMain.getClient().getPreloaded().getTile(tileID) != null) {
			g.drawImage(ClientMain.getClient().getPreloaded().getTile(tileID), (int) x, (int) y, getTilesize(),
					getTilesize(), null);
		}
	}

	public void drawTiles(int layer, Graphics g) {

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

	}

	public Tilemap getDatamap() {

		return datamap;
	}

	public Tiletype getTileAtPosition(double x, double y, int layer) {

		return getDatamap().getData((int) (x / getTilesize()), (int) (y / getTilesize()), layer);
	}

	public int getTilesize() {

		return tilesize;
	}

	public void moveDown() {

		viewy += camspeed;
		fixview();

	}

	public void moveLeft() {

		viewx -= camspeed;
		fixview();
	}

	public void moveRight() {

		viewx += camspeed;
		fixview();
	}

	public void moveUp() {

		viewy -= camspeed;
		fixview();
	}

	public void sendMap(String id) {
		ClientMain.getClient().getEventManager().fire(new PackageSendEvent(new DataMapUpdate(datamap)));
	}

	public void setDatamap(Tilemap new_datamap) {

		datamap = new_datamap;
		datamap.calculateOverlays();
		hasMap = true;
	}

	public void setTilesize(int tilesize) {

		this.tilesize = tilesize;
	}

	public void stepEvent() {

	}

	public void updateTile(int x, int y, String shortcut) {

		getDatamap().setData(x, y, 0, Tiletype.getTiletypeOfShortcut(shortcut));
	}

	public void zoom(int movement) {

		// TODO: fix bug: camera does not match the zoom
		setTilesize(getTilesize() + movement);
		if (getTilesize() > 128) {
			setTilesize(128);
		} else if (getTilesize() < 16) {
			setTilesize(16);
		}
		System.out.println("New Tilesize: " + getTilesize());
	}

}
