package net.kleopi.Engine.Instances;

import java.awt.event.MouseEvent;

import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;

public class Minion extends Instance {

	public Minion() {
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
		// -> character
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

		targetOwner();
		move();
		// TODO add?

	}

	@Override
	void checkDeath() {

		// TODO Auto-generated method stub

	}

	private void targetOwner() {
		// TODO implement
	}

}
