package net.kleopi.Engine.Instances;

import java.awt.Graphics2D;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.Networking.Player;

public abstract class Instance {

	private Player owner;
	private Circle circle;
	protected boolean selected = false;
	private boolean targetting = false;
	protected double direction;
	private double speed;
	private double target_x;
	private double target_y;
	private int id;
	private double hpmax;
	private double hp;
	private Sprite sprite;

	/**
	 *
	 * @param posx
	 * @param posy
	 * @param hitboxsize
	 * @param ndirection
	 * @param nspeed
	 * @param owner
	 * @param id
	 * @param imageid
	 *            - Sprite for drawing TODO: rework this
	 */
	public Instance(double posx, double posy, double hitboxsize, double ndirection, double nspeed, Player owner, int id,
			Sprite imageid) {
		setCircle(new Circle(posx, posy, hitboxsize));
		direction = ndirection;
		speed = nspeed;
		target_x = 0;
		target_y = 0;
		this.owner = owner;
		this.setId(id);
		this.sprite = imageid;
	}

	/**
	 * When do I die?
	 */
	abstract void checkDeath();

	/**
	 * When getting damage, what should happen?
	 *
	 * @param amount
	 *            - damage dealt
	 */
	public abstract void damage(int amount);

	/**
	 * TODO: ???
	 */
	public abstract void damageNear();

	/**
	 * What happens when Instance gets destroyed?
	 */
	public abstract void destroy();

	private double distanceToTarget() {

		return Math.abs(
				Math.sqrt(Math.pow(target_x - getCircle().getX(), 2) + Math.pow(target_y - getCircle().getY(), 2)));
	}

	@Deprecated
	public abstract void drawEvent(Graphics2D g);
	// TODO: change to Event

	/**
	 *
	 * @return Hitcircle
	 */
	public Circle getCircle() {

		return circle;
	}

	public int getId() {

		return id;
	}

	public int getScreenX() {

		return (int) (getCircle().getX() - ClientMain.getClient().getTilemanager().viewx);
	}

	public int getScreenY() {

		return (int) (getCircle().getY() - ClientMain.getClient().getTilemanager().viewy);
	}

	/**
	 * Check if Sprite is within a rectangle
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return true - when inside
	 */
	public boolean inRect(int x, int y, int width, int height) {

		return (Utilities.between((int) getCircle().getX(), x, width)
				&& Utilities.between((int) getCircle().getY(), y, height));
	}

	// TODO: to eventlistener
	@Deprecated
	public abstract void leftclickedEvent(int x, int y);

	/**
	 * Tell the sprite to move
	 */
	public void move() {

		path();
		double dx = speed * Math.cos(direction / 360 * 2 * Math.PI);
		double dy = speed * -Math.sin(direction / 360 * 2 * Math.PI);
		if (dx != 0 || dy != 0) {
			getCircle().setX(getCircle().getX() + dx);
			getCircle().setY(getCircle().getY() + dy);
			// TODO: rework
			// Main.network.updateAll(Nettype.SEND_INSTANCE_INFO,
			// Nettype.String6List(Integer.toString(id), Integer.toString((int)
			// circle.x),
			// Integer.toString((int) circle.y),
			// Integer.toString((int) direction), Integer.toString((int) speed),
			// Sprite.getValue(sprite)));
		}
	}

	private void path() {

		// TODO implement pathfinding system
		if (targetting) {
			if (getCircle().getX() != target_x && getCircle().getY() != target_y) {
				double angle = Math.atan2(target_y - getCircle().getY(), target_x - getCircle().getX());
				if (distanceToTarget() > speed) {
					double new_x = getCircle().getX() + speed * Math.cos(angle);
					double new_y = getCircle().getY() + speed * Math.sin(angle);
					if (ClientMain.getClient().getInstancemanager().passable(new_x, new_y, this)) {
						getCircle().setX(new_x);
						getCircle().setY(new_y);
					} else {
						targetting = false;
						target_x = getCircle().getX();
						target_y = getCircle().getY();
					}
				} else {
					if (ClientMain.getClient().getInstancemanager().passable(target_x, target_x, this)) {
						getCircle().setX(target_x);
						getCircle().setY(target_y);
					} else {
						targetting = false;
						target_x = getCircle().getX();
						target_y = getCircle().getY();
					}
				}
				direction = angle;
			}
		}
	}

	@Deprecated
	public abstract void rightclickedEvent(int x, int y);

	public void setCircle(Circle circle) {

		this.circle = circle;
	}

	public void setId(int id) {

		this.id = id;
	}

	public void setPosition(int nx, int ny) {

		getCircle().setX(nx);
		getCircle().setY(ny);
		target_x = nx;
		target_y = ny;
	}

	public void setTarget(Instance i) {

		target_x = i.getCircle().getX();
		target_y = i.getCircle().getY();
		targetting = true;

	}

	public void setVelocity(double ndirection, double nspeed) {

		direction = ndirection;
		speed = nspeed;

	}

	public abstract void stepEvent();
}
