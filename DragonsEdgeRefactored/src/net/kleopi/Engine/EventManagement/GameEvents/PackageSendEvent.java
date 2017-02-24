package net.kleopi.Engine.EventManagement.GameEvents;

import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class PackageSendEvent extends GameEvent {
	private UpdateObject updateObject;

	public PackageSendEvent(UpdateObject pkg) {
		updateObject = pkg;
	}
}
