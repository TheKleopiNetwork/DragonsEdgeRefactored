package net.kleopi.Engine.Networking.UpdateObjects;

import java.util.List;

import net.kleopi.Engine.Instances.Instance;

@SuppressWarnings("serial")
public class InstanceListUpdate extends UpdateObject {
	public List<Instance> instancelist;

	public InstanceListUpdate(List<Instance> instances) {
		instancelist = instances;
	}
}
