package net.kleopi.Engine.Instances;

import java.awt.event.MouseEvent;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;

public class Character extends Instance {

	public Character() {
		super();
	}

	@Override
	public void damage(int amount) {

		// TODO Auto-generated method stub

	}

	@Override
	public void damageNear() {

		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {

		// give points xp etc, let player respawn,...
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(DrawEvent e) {
		int x = getScreenX();
		int y = getScreenY();
		Utilities.drawGlow(x, y, 128, e.getShaderlayer());
		e.getSpritelayer().drawImage(getSprite().getImage(), x, y,
				ClientMain.getClient().getTilemanager().getTilesize(),
				ClientMain.getClient().getTilemanager().getTilesize(), null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see net.kleopi.Engine.Instances.Instance#onMouseAction(java.awt.event.
	 * MouseEvent)
	 */
	@Override
	public void onMouseClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTick(TickEvent e) {
		move();
	}

	@Override
	void checkDeath() {

		// TODO Auto-generated method stub

	}

}
