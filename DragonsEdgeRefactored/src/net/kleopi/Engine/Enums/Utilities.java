package net.kleopi.Engine.Enums;

/**
 * Several Math utilities
 *
 *
 */
public class Utilities {
	/**
	 *
	 * @param n1
	 * @param n2
	 * @param n3
	 * @return true if n1 is between n2 and n3, inclusive.
	 */
	public static boolean between(int n1, int n2, int n3) {
		return (n1 >= n2 && n1 <= n3);
	}

	/**
	 *
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return Euclidical distance between Point A(x1|y1) and (x2|y2)
	 */
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.hypot(x2 - x1, y2 - y1);
	}

}
