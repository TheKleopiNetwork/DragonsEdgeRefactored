package net.kleopi.Engine.Instances;

import java.awt.event.MouseEvent;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
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
	public Instance() {
		setCircle(new Circle());
		owner = new Player(null);
		target_x = 0;
		target_y = 0;
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
	 * What happens when Instance gets destroyed? TODO: also throw event
	 */
	public abstract void destroy();

	private double distanceToTarget() {

		return Math.abs(
				Math.sqrt(Math.pow(target_x - getCircle().getX(), 2) + Math.pow(target_y - getCircle().getY(), 2)));
	}

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

	public Sprite getSprite() {
		return sprite;
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

		}
	}

	public abstract void onDraw(DrawEvent e);

	/**
	 * @param e
	 * @return
	 */
	public abstract void onMouseClick(MouseEvent e);

	/**
	 * @param e
	 * @return
	 */
	public abstract void onTick(TickEvent e);

	private void path() {

		// TODO implement pathfinding system
		if (targetting) {
			if (getCircle().getX() != target_x && getCircle().getY() != target_y) {
				double angle = Math.atan2(target_y - getCircle().getY(), target_x - getCircle().getX());
				if (distanceToTarget() > speed) {
					double new_x = getCircle().getX() + speed * Math.cos(angle);
					double new_y = getCircle().getY() + speed * Math.sin(angle);
					if (ClientMain.getClient().getInstancemanager().isPassable(new_x, new_y, this)) {
						getCircle().setX(new_x);
						getCircle().setY(new_y);
					} else {
						targetting = false;
						target_x = getCircle().getX();
						target_y = getCircle().getY();
					}
				} else {
					if (ClientMain.getClient().getInstancemanager().isPassable(target_x, target_x, this)) {
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

	public void setCircle(Circle circle) {

		this.circle = circle;
	}

	/**
	 * @param i
	 */
	public void setHitboxSize(int i) {
		getCircle().setR(i);

	}

	public void setId(int id) {

		this.id = id;
	}

	/**
	 * @param owner2
	 */
	public void setOwner(Player owner) {
		this.owner = owner;

	}

	public void setPosition(int nx, int ny) {

		getCircle().setX(nx);
		getCircle().setY(ny);
		target_x = nx;
		target_y = ny;
	}

	/**
	 * @param sprite2
	 */
	public void setSprite(Sprite sprite2) {
		this.sprite = sprite2;

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

	public Instance withData(int posx, int posy, double ndirection, double nspeed, Player owner, int id,
			Sprite sprite) {

		setPosition(posx, posy);
		setHitboxSize(48);
		setVelocity(ndirection, nspeed);
		setOwner(owner);
		setId(id);
		setSprite(sprite);
		return this;
	}
}
