package net.kleopi.Engine.Enums;

public enum Tiletype
{

	WATER("basic water\\1.jpg", "W"), DIRT("basic dirt\\1.jpg", "D"), STONE("basic stone\\1.jpg", "S"), NOTILE("",
			"0"), ERRORTILE("", "E"), DIRT1("dirtoverlay\\sides\\u.png", "D1"), DIRT3("dirtoverlay\\sides\\l.png",
					"D3"), DIRT4("dirtoverlay\\sides\\r.png", "D4"), DIRT6("dirtoverlay\\sides\\d.png", "D6"), STONE1(
							"rockoverlay\\sides\\u.png", "S1"), STONE3("rockoverlay\\sides\\l.png", "S3"), STONE4(
									"rockoverlay\\sides\\r.png", "S4"), STONE6("rockoverlay\\sides\\d.png", "S6")

	, DIRT0("dirtoverlay\\corners\\lu.png", "D0"), DIRT2("dirtoverlay\\corners\\ru.png", "D2"), DIRT5("dirtoverlay\\corners\\ld.png", "D5"), DIRT7("dirtoverlay\\corners\\rd.png", "D7"), STONE0("rockoverlay\\corners\\lu.png", "S0"), STONE2("rockoverlay\\corners\\ru.png", "S2"), STONE5("rockoverlay\\corners\\ld.png", "S5"), STONE7("rockoverlay\\corners\\rd.png", "S7");
	public static Tiletype getedge(String type, int side)
	{

		return getTiletypeOfShortcut(type + side);
	}

	public static Tiletype getside(String type, int side)
	{

		return getTiletypeOfShortcut(type + side);
	}

	// remeber: use a double backslash instead of slash or single backslash in filepaths -.-
	public static Tiletype getTiletypeOfShortcut(String shortcut)
	{

		for (Tiletype t : Tiletype.values())
		{
			if (t.getShortcut().equals(shortcut))
			{
				return t;
			}
		}
		System.out.println("Unknown Shortcut: " + shortcut);
		return ERRORTILE;
	}

	private String	path;

	private String	shortcut;

	Tiletype(String path, String shortcut)
	{
		this.setPath(path);
		this.setShortcut(shortcut);
	}

	public String getPath()
	{

		return path;
	}

	public String getShortcut()
	{

		return shortcut;
	}

	public void setPath(String path)
	{

		this.path = path;
	}

	public void setShortcut(String shortcut)
	{

		this.shortcut = shortcut;
	}
}
