package net.kleopi.Engine.Instances;

import java.awt.Graphics2D;

import net.kleopi.Client.GUI.Sprite;
import net.kleopi.Engine.Networking.Player;

public class Structure extends Instance {

	public Structure(double posx, double posy, double hitboxsize, double ndirection, double nspeed, Player owner,
			int id, Sprite imageid) {
		super(posx, posy, hitboxsize, ndirection, nspeed, owner, id, imageid);
	}

	@Override
	void checkDeath() {

		// TODO Auto-generated method stub

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

		// TODO Auto-generated method stub

	}

	@Override
	public void drawEvent(Graphics2D g) {

		// TODO Auto-generated method stub

	}

	@Override
	public void leftclickedEvent(int x, int y) {

		// TODO Auto-generated method stub

	}

	@Override
	public void rightclickedEvent(int x, int y) {

		// TODO Auto-generated method stub

	}

	@Override
	public void stepEvent() {
		// usually doesnt move :P
		// TODO implement
	}

}
