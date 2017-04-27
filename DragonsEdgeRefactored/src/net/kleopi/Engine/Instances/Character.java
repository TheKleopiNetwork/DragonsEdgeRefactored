package net.kleopi.Engine.Instances;

import java.awt.Color;
import java.awt.image.BufferedImage;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Utilities;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.Networking.Player;

public class Character extends Instance {

	public Character(double posx, double posy, double ndirection, double nspeed, Player owner, int id, Sprite sprite) {
		super(posx, posy, 48, ndirection, nspeed, owner, id, sprite);
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
	public void leftclickedEvent(int x, int y) {

		selected = (Utilities.distance(x, y, getScreenX(), getScreenY() - getCircle().getR()) <= getCircle().getR());
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

	@Override
	public void rightclickedEvent(int x, int y) {
	}

	@Override
	public void stepEvent() {
		move();
	}

	@Override
	void checkDeath() {

		// TODO Auto-generated method stub

	}

}
