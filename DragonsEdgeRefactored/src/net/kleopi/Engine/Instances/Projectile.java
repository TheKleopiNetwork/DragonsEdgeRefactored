package net.kleopi.Engine.Instances;

import java.awt.event.MouseEvent;

import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;

public class Projectile extends Instance {

	public Projectile() {
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

		// just well...destroy yourself
		// TODO Auto-generated method stub

	}

	@Override
	public void onDraw(DrawEvent e) {
		// TODO Auto-generated method stub

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

		damageNear();
		move();
		// TODO implement

	}

	@Override
	void checkDeath() {

		// TODO Auto-generated method stub

	}

}
