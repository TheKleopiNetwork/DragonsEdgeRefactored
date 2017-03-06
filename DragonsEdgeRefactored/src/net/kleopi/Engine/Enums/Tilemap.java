package net.kleopi.Engine.Enums;

import java.io.Serializable;

public class Tilemap implements Serializable {

	private static final long serialVersionUID = -1347725125747112647L;
	private Tiletype data[][][];
	private int width, height, depth;
	private final String pri1 = "D"; // priority list = what should be drawn
										// first?
	private final String pri2 = "S";
	private final String pri3 = "W";

	public Tilemap(int width, int height, int depth) {
		this.width = width;
		this.height = height;
		this.depth = depth;
		data = new Tiletype[width][height][depth];
	}

	/**
	 * Updates all Edge/Corner Overlays of this Datamap. Resource intensive!
	 */
	public void calculateOverlays() {

		// TODO: fix bugs with corners
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				switch (getData(i, j, 0).getShortcut()) {
				case pri1: {
					setData(i, j, 1, Tiletype.getTiletypeOfShortcut("0"));
					setData(i, j, 2, Tiletype.getTiletypeOfShortcut("0"));
					setData(i, j, 3, Tiletype.getTiletypeOfShortcut("0"));
					setData(i, j, 4, Tiletype.getTiletypeOfShortcut("0"));
					setData(i, j, 5, Tiletype.getTiletypeOfShortcut("0"));
					break;
				}
				case pri2: {
					String[] stiles = new String[8];
					// 0,1,2
					// 3,X,4
					// 5,6,7

					// mapping:
					stiles[0] = getData(i - 1, j - 1, 0).getShortcut();
					stiles[1] = getData(i, j - 1, 0).getShortcut();
					stiles[2] = getData(i + 1, j - 1, 0).getShortcut();
					stiles[3] = getData(i - 1, j, 0).getShortcut();
					stiles[4] = getData(i + 1, j, 0).getShortcut();
					stiles[5] = getData(i - 1, j + 1, 0).getShortcut();
					stiles[6] = getData(i, j + 1, 0).getShortcut();
					stiles[7] = getData(i + 1, j + 1, 0).getShortcut();
					// calculating:
					int index = 0;
					boolean up = false, left = false, right = false, down = false;
					// sides
					if (stiles[1].equals(pri1)) {
						up = true;
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 1));
					}
					if (stiles[3].equals(pri1)) {
						left = true;
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 3));
					}
					if (stiles[4].equals(pri1)) {
						right = true;
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 4));
					}
					if (stiles[6].equals(pri1)) {
						down = true;
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 6));
					}
					// edges
					if (stiles[0].equals(pri1) && !left && !up) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 0));
					}
					if (stiles[2].equals(pri1) && !right && !up) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 2));
					}
					if (stiles[5].equals(pri1) && !left && !down) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 5));
					}
					if (stiles[7].equals(pri1) && !right && !down) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 7));
					}
					while (index < 8) {
						index++;
						setData(i, j, index, Tiletype.getTiletypeOfShortcut("0"));

					}
					break;

				}
				case pri3: {
					String[] stiles = new String[8];
					// 0,1,2
					// 3,X,4
					// 5,6,7

					// mapping:
					stiles[0] = getData(i - 1, j - 1, 0).getShortcut();
					stiles[1] = getData(i, j - 1, 0).getShortcut();
					stiles[2] = getData(i + 1, j - 1, 0).getShortcut();
					stiles[3] = getData(i - 1, j, 0).getShortcut();
					stiles[4] = getData(i + 1, j, 0).getShortcut();
					stiles[5] = getData(i - 1, j + 1, 0).getShortcut();
					stiles[6] = getData(i, j + 1, 0).getShortcut();
					stiles[7] = getData(i + 1, j + 1, 0).getShortcut();
					// calculating:
					int index = 0;
					// sides
					if (stiles[1].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 1));
					}
					if (stiles[3].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 3));
					}
					if (stiles[4].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 4));
					}
					if (stiles[6].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri1, 6));
					}
					// type 2
					if (stiles[1].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri2, 1));
					}
					if (stiles[3].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri2, 3));
					}
					if (stiles[4].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri2, 4));
					}
					if (stiles[6].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getside(pri2, 6));
					}
					// edges
					if (stiles[0].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 0));
					}
					if (stiles[2].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 2));
					}
					if (stiles[5].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 5));
					}
					if (stiles[7].equals(pri1)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri1, 7));
					}
					if (stiles[0].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri2, 0));
					}
					if (stiles[2].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri2, 2));
					}
					if (stiles[5].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri2, 5));
					}
					if (stiles[7].equals(pri2)) {
						index++;
						setData(i, j, index, Tiletype.getedge(pri2, 7));
					}

					while (index < 8) {
						index++;
						setData(i, j, index, Tiletype.getTiletypeOfShortcut("0"));
					}
					break;

				}
				}
			}
		}
	}

	/**
	 *
	 * @param x
	 * @param y
	 * @param layer
	 * @return Tiletype of Tile at given coordinates 'x','y' in layer 'layer'
	 */
	public Tiletype getData(int x, int y, int layer) {

		if (inRange(x, y, layer) && data[x][y][layer] != null) {
			return data[x][y][layer];
		} else {
			return Tiletype.ERRORTILE;
		}
	}

	public int getDepth() {

		return depth;
	}

	public int getHeight() {

		return height;
	}

	public int getWidth() {

		return width;
	}

	private boolean inRange(int x, int y, int z) {

		return (Utilities.between(x, 0, width - 1) && Utilities.between(y, 0, height - 1)
				&& Utilities.between(z, 0, depth - 1));
	}

	public void setData(int x, int y, int z, Tiletype s) {

		if (inRange(x, y, z)) {
			data[x][y][z] = s;
			if (z == 0) {
				// TODO: throw update event
				// Main.network.updateAll(Nettype.TILE_AT_POS,
				// Nettype.String3List(Integer.toString(x), Integer.toString(y),
				// s.getShortcut()));
			}
		}
	}

}
