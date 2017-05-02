/**
 *
 */
package net.kleopi.Client.Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.GameEvents.KeyPressedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.MouseClickedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.MouseWheelMovedEvent;

public class InputReceiver implements MouseListener, MouseWheelListener, KeyListener {

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		ClientMain.getClient().getEventManager().fire(new KeyPressedEvent(e));

	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		ClientMain.getClient().getEventManager().fire(new MouseClickedEvent(e));
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// im soooo excited!!!
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		ClientMain.getClient().getEventManager().fire(new MouseWheelMovedEvent(e));
	}

}
