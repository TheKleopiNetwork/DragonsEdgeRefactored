package net.kleopi.Engine.Instances;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import net.kleopi.Client.GUI.GUI;
import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.Networking.Player;

public class Instancemanager {
	private List<Instance> instances = new ArrayList<>();

	int idcount = 0;

	private boolean selecting;

	private int select_x;

	private int select_y;

	public void createCharacter(int x, int y, int direction, int speed, Player owner, Sprite sprite) {

		int id = getNextId();
		Character c = new Character(x, y, direction, speed, owner, id, sprite);
		registerInstance(c);
		// TODO: rework
		// Main.network.updateAll(Nettype.CREATE_NEW_SPRITE,
		// Nettype.String6List(new Integer(x).toString(), new
		// Integer(y).toString(),
		// new Integer(direction).toString(), new Integer(speed).toString(),
		// Sprite.getValue(sprite),
		// Integer.toString(c.id)));
	}

	public void drawEvent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		if (!getInstances().isEmpty()) {
			for (Instance c : getInstances()) {
				c.drawEvent(g2d);
			}
		}

		// TODO: fix selectionbug
		if (selecting) {
			int x, y, width, height;
			Point p = GUI.getMousePoint();
			if (p != null) {
				if (select_x < p.x) {
					x = select_x;
					width = p.x - select_x;
				} else {
					x = p.x;
					width = select_x - p.x;
				}
				if (select_y < p.y) {
					y = select_y;
					height = p.y - select_y;
				} else {
					y = p.y;
					height = select_y - p.y;
				}
				g2d.setPaint(Color.green);
				g2d.drawRect(x, y, width, height);
			}
		}
	}

	public List<Instance> getInstances() {

		return instances;
	}

	private List<Instance> getInstancesInRect(Rect rect) {

		List<Instance> a = new ArrayList<>();
		for (Instance i : getInstances()) {
			if (rect.inRange((int) i.getCircle().getX(), (int) i.getCircle().getY())) {
				a.add(i);
			}
		}
		return a;
	}

	private int getNextId() {

		int nid = idcount;
		idcount++;
		return nid;

	}

	public List<Instance> getSelectedInstances(int clientid) {

		List<Instance> ins = new ArrayList<>();
		for (Instance i : getInstances()) {
			if (i.getId() == clientid && i.selected) {
				ins.add(i);
			}
		}
		return ins;
	}

	private Instance getTargetInstanceAtPosition(int x, int y) {

		Circle clickcircle = new Circle(x, y, 0);
		Instance ins = new Targetpoint(x, y);
		double dist = -1;
		for (Instance i : getInstances()) {
			if (i.getCircle().collision(clickcircle)) {
				double ndist = Utilities.distance(i.getCircle().getX(), i.getCircle().getY(), clickcircle.getX(),
						clickcircle.getY());
				if (dist == -1 || dist > ndist) {
					dist = ndist;
				}
				ins = i;
			}
		}
		return ins;
	}

	public void leftclickedEvent(int x, int y) {

		if (selecting == false) {
			for (Instance ins : getInstances()) {
				ins.leftclickedEvent(x, y);

			}
			selecting = true;
			select_x = x;
			select_y = y;
		}
	}

	public void leftreleasedEvent(int x, int y) {

		selecting = false;
		Rect rect = new Rect(0, 0, 0, 0);

		rect.x = Math.min(x, select_x) + ClientMain.getClient().getTilemanager().viewx;
		rect.y = Math.min(y, select_y) + ClientMain.getClient().getTilemanager().viewy;
		rect.width = Math.abs(x - select_x);
		rect.height = Math.abs(y - select_y);
		// TODO send update
		// Settings.network.sendUpdate(Nettype.RECTANGLE,
		// Nettype.String4List(new Integer(rect.x).toString(), new
		// Integer(rect.y).toString(),
		// new Integer(rect.x + rect.width).toString(), new Integer(rect.y +
		// rect.height).toString()));
	}

	public void moveselectedsprites(int clientid, int x, int y) {

		Instance target = getTargetInstanceAtPosition(x, y);

		for (Instance i : getSelectedInstances(clientid)) {
			i.setTarget(target);
		}

	}

	public boolean passable(double x, double y, Instance i) {

		for (Instance ins : getInstances()) {
			if (ins.equals(i)) {
				continue;
			}
			if (Utilities.distance(x, y, ins.getCircle().getX(), ins.getCircle().getY()) <= ins.getCircle().getR()
					+ i.getCircle().getR()) {
				return false;
			}
		}
		return true;
	}

	public void registerInstance(Instance ins) {

		getInstances().add(ins);
	}

	public void rightclickedEvent(int x, int y) {

		for (Instance ins : getInstances()) {
			ins.rightclickedEvent(x, y);
		}
	}

	public void selectAllOwned(int clientid, int x1, int y1, int x2, int y2) {
		// TODO: rework
		// Main.network.sendUpdate(Nettype.SELECT_A_SPRITE,
		// Nettype.String2List(new Integer(ins.id).toString(), "true"),
		// clientid);
	}

	public void sendCharacters(int id) {

		for (Instance i : getInstances()) {
			Character c = (Character) i;
			// TODO: rework
			// Main.network.sendUpdate(Nettype.CREATE_NEW_SPRITE,
			// Nettype.String6List(new Integer((int) c.circle.x).toString(),
			// new Integer((int) c.circle.y).toString(),
			// new Integer((int) c.direction).toString(), new Integer((int)
			// c.speed).toString(),
			// c.sprite.getPath(),
			// Integer.toString(i.id)),
			// id);
		}
	}

	public void setInstances(List<Instance> instances) {

		this.instances = instances;
	}

	public void stepEvent() {

		for (Instance ins : getInstances()) {
			ins.stepEvent();
		}
	}

	public void unregisterInstance(Instance ins) {

		getInstances().remove(ins);
	}

}