package net.kleopi.Engine.Networking.UpdateObjects;

import java.util.ArrayList;

import net.kleopi.Engine.Instances.Instance;

@SuppressWarnings("serial")
public class InstanceListUpdate extends UpdateObject {
	public ArrayList<Instance> instancelist;

	public InstanceListUpdate() {
	}

	public InstanceListUpdate(ArrayList<Instance> instances) {
		instancelist = instances;
	}

	/**
	 * @param instances
	 * @return
	 */
	public InstanceListUpdate withInstanceList(ArrayList<Instance> instances) {
		instancelist = instances;
		return this;
	}
}
