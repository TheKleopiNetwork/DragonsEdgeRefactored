package net.kleopi.Engine.Instances;

import net.kleopi.Engine.Enums.Utilities;

public class Rect {
	public int x, y, width, height;

	public Rect(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rect(Rect r) {
		x = r.x;
		y = r.y;
		width = r.width;
		height = r.height;
	}

	public boolean inRange(int x, int y) {

		return (Utilities.between(x, this.x, this.x + width) && Utilities.between(y, this.y, this.y + height));
	}
}
