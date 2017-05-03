package net.kleopi.Engine.Enums;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Utilities {
	private static BufferedImage glow;

	public static boolean between(int n1, int n2, int n3) {
		return (n1 >= n2 && n1 <= n3);
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.hypot(x2 - x1, y2 - y1);
	}

	public static void drawGlow(int x, int y, int i, Graphics2D shaderlayer) {
		if (glow == null) {
			glow = new BufferedImage(128, 128, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics2D g = glow.createGraphics();
			g.setColor(new Color(0x10FFFFFF, true));
			g.fillOval(0, 0, 128, 128);
			g.fillOval(32, 32, 64, 64);
			g.fillOval(48, 48, 32, 32);
			g.dispose();
		}
		shaderlayer.drawImage(glow, x - i / 2, y - i / 2, i, i, null);

	}
}
