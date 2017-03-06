package net.kleopi.Engine.Enums;

public enum Tiletype {

	WATER("basic water\\1.jpg", "W"), DIRT("basic dirt\\1.jpg", "D"), STONE("basic stone\\1.jpg", "S"), NOTILE("",
			"0"), ERRORTILE("", "E"), DIRT1("dirtoverlay\\sides\\u.png", "D1"), DIRT3("dirtoverlay\\sides\\l.png",
					"D3"), DIRT4("dirtoverlay\\sides\\r.png", "D4"), DIRT6("dirtoverlay\\sides\\d.png", "D6"), STONE1(
							"rockoverlay\\sides\\u.png", "S1"), STONE3("rockoverlay\\sides\\l.png", "S3"), STONE4(
									"rockoverlay\\sides\\r.png", "S4"), STONE6("rockoverlay\\sides\\d.png", "S6")

	, DIRT0("dirtoverlay\\corners\\lu.png", "D0"), DIRT2("dirtoverlay\\corners\\ru.png", "D2"), DIRT5("dirtoverlay\\corners\\ld.png", "D5"), DIRT7("dirtoverlay\\corners\\rd.png", "D7"), STONE0("rockoverlay\\corners\\lu.png", "S0"), STONE2("rockoverlay\\corners\\ru.png", "S2"), STONE5("rockoverlay\\corners\\ld.png", "S5"), STONE7("rockoverlay\\corners\\rd.png", "S7");
	public static Tiletype getedge(String type, int side) {

		return getTiletypeOfShortcut(type + side);
	}

	public static Tiletype getside(String type, int side) {

		return getTiletypeOfShortcut(type + side);
	}

	// remeber: use a double backslash instead of slash or single backslash in
	// filepaths -.-
	/**
	 *
	 * @param shortcut
	 * @return Tiletype with this shortcut
	 */
	public static Tiletype getTiletypeOfShortcut(String shortcut) {

		for (Tiletype t : Tiletype.values()) {
			if (t.getShortcut().equals(shortcut)) {
				return t;
			}
		}
		// TODO: remove/replace by Messager
		System.out.println("Unknown Shortcut: " + shortcut);
		return ERRORTILE;
	}

	private String path;

	private String shortcut;

	Tiletype(String path, String shortcut) {
		this.setPath(path);
		this.setShortcut(shortcut);
	}

	/**
	 *
	 * @return Path where the sprite is saved at
	 */
	public String getPath() {

		return path;
	}

	/**
	 * @return shortcut for serialization. TODO: remove as not needed
	 */
	public String getShortcut() {

		return shortcut;
	}

	private void setPath(String path) {

		this.path = path;
	}

	private void setShortcut(String shortcut) {

		this.shortcut = shortcut;
	}
}
