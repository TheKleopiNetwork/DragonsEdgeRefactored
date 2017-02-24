package net.kleopi.Client.Networking;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.Networking.UpdateObjects.DataMapUpdate;
import net.kleopi.Engine.Networking.UpdateObjects.UpdateObject;

public class NetWorkerClient extends Thread implements TKNListenerAdapter {

	public NetWorkerClient() {
		start();
		ClientMain.getClient().getEventManager().addListener(this);
	}

	@Override
	public void onPackageReceived(PackageReceivedEvent e) {
		performTask((UpdateObject) e.getUpdateObject());
	}

	private void performTask(UpdateObject updateObject) {
		if (updateObject instanceof DataMapUpdate) {
			System.out.println("Got a datamap");
		}
	}

	@Override
	public void run() {

	}

}
