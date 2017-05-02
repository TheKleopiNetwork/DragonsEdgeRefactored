package net.kleopi.Engine.Instances;

import net.kleopi.Engine.Enums.Utilities;

public class Circle {
	private double x, y, r;

	public Circle() {
	}

	public boolean collision(Circle c) {

		return (Utilities.distance(getX(), getY(), c.getX(), c.getY()) < getR() + c.getR());
	}

	public double getR() {

		return r;
	}

	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

	public void setR(double r) {

		this.r = r;
	}

	public void setX(double x) {

		this.x = x;
	}

	public void setY(double y) {

		this.y = y;
	}

	public Circle withData(Circle c) {
		setX(c.getX());
		setY(c.getY());
		setR(c.getR());
		return this;
	}

	public Circle withData(double x, double y, double r) {
		this.setX(x);
		this.setY(y);
		this.setR(r);
		return this;
	}
}
