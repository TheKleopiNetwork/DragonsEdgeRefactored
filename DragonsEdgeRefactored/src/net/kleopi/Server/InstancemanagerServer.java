package net.kleopi.Server;

import java.util.ArrayList;
import java.util.List;

import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Instances.Instance;
import net.kleopi.Engine.Instances.Rect;
import net.kleopi.Engine.Networking.UpdateObjects.InstanceListUpdate;

public class InstancemanagerServer implements TKNListenerAdapter {
	private ArrayList<Instance> instances = new ArrayList<>();

	int idcount = 0;

	private boolean active = false;

	public InstancemanagerServer() {
		ServerMain.getServer().getEventManager().addListener(this);
	}

	public ArrayList<Instance> getInstances() {

		return instances;
	}

	// TODO: rework
	public boolean isPassable(double x, double y, Instance i) {

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

	@Override
	public void onStartUp(StartupEvent e) {
		active = true;
	}

	@Override
	public void onTick(TickEvent e) {
		if (active) {
			instances.forEach(i -> i.onTick(e));
			ServerMain.getServer().getNetwork().updateAll(new InstanceListUpdate().withInstanceList(getInstances()));
		}

	}

	/**
	 * Add an Instance to the List
	 *
	 * @param ins
	 *            -Instance to add
	 */
	public void registerInstance(Instance ins) {

		getInstances().add(ins);
	}

	public void setInstances(ArrayList<Instance> instances) {

		this.instances = instances;
	}

	public void unregisterInstance(Instance ins) {

		getInstances().remove(ins);
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
}
