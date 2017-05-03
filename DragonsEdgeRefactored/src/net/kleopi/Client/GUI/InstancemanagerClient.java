package net.kleopi.Client.GUI;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Instances.Instance;
import net.kleopi.Engine.Instances.Rect;
import net.kleopi.Engine.Networking.UpdateObjects.InstanceListUpdate;

public class InstancemanagerClient implements TKNListenerAdapter {
	private ArrayList<Instance> instances = new ArrayList<>();

	int idcount = 0;

	public InstancemanagerClient() {
		ClientMain.getClient().getEventManager().addListener(this);
	}

	public ArrayList<Instance> getInstances() {

		return instances;
	}

	// TODO: might not be needed for client
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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onDraw(net.kleopi.
	 * Engine.EventManagement.GameEvents.DrawEvent)
	 */
	@Override
	public void onDraw(DrawEvent e) {
		instances.forEach(i -> i.onDraw(e));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onMouseClick(java.
	 * awt.event.MouseEvent)
	 */
	@Override
	public void onMouseClick(MouseEvent e) {
		instances.forEach(i -> i.onMouseClick(e));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onPackageReceived(
	 * net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent)
	 */
	@Override
	public void onPackageReceived(PackageReceivedEvent e) {
		if (e.getUpdateObject() instanceof InstanceListUpdate) {
			setInstances(((InstanceListUpdate) e.getUpdateObject()).instancelist);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * net.kleopi.Engine.EventManagement.TKNListenerAdapter#onTick(net.kleopi.
	 * Engine.EventManagement.GameEvents.TickEvent)
	 */
	@Override
	public void onTick(TickEvent e) {
		instances.forEach(i -> i.onTick(e));
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
