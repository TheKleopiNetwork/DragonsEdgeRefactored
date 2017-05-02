package net.kleopi.Engine.Instances;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.kleopi.Client.Main.ClientMain;
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
		// TODO: Draw the right sprite + Fix Bug
		BufferedImage sprite;
		sprite = ClientMain.getClient().getPreloaded().getCharacter("dragons\\earth\\r.jpg");

		int x = getScreenX();
		int y = getScreenY();
		e.getGraphics().drawImage(sprite, x, y, 128, 128, null);
		if (selected) {
			e.getGraphics().setColor(Color.GREEN);
			e.getGraphics().drawOval(x - ((int) getCircle().getR()), y - ((int) getCircle().getR() * 2),
					(int) getCircle().getR() * 2, (int) getCircle().getR() * 2);
		}

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
