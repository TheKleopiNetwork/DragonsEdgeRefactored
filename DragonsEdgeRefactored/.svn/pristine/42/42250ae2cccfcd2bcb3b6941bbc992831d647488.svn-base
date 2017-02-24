package net.kleopi.Engine.Enums;

public class Utilities {
	public static boolean between(int n1, int n2, int n3) {
		return (n1 >= n2 && n1 <= n3);
	}

	public static int directionOfVector(double d, double e) {
		return (int) (Math.atan(e / d));
	}

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.hypot(x2 - x1, y2 - y1);
	}

	public static double[] getDirectionalVector(double direction, double speed) {
		double dx = Math.cos((direction / 360) * 2 * Math.PI);
		double dy = -Math.sin((direction / 360) * 2 * Math.PI);
		double[] vec = new double[2];
		vec[0] = dx * speed;
		vec[1] = dy * speed;
		System.out.println("dx: " + vec[0] + "; dy: " + vec[1]);
		return vec;
	}

}
