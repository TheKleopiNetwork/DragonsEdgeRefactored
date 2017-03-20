package net.kleopi.Client.GUI;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;

public class GUI extends Thread implements TKNListenerAdapter {
	public final static int RESOLUTION_WIDTH = 1920;
	public final static int RESOLUTION_HEIGTH = 1080;

	private JPanel surface;

	private JFrame frame;
	private LoginPanel loginpanel;

	public GUI() {
		loginpanel = new LoginPanel(frame);
		ClientMain.getClient().getEventManager().addListener(this);

	}

	public Point getMousePoint() {

		return (surface.getMousePosition());
	}

	@Override
	public void onDraw(DrawEvent e) {
	}

	/**
	 * TODO: react on Logged Event
	 */
	@Override
	public void onLogin(LoginEvent e) {
		setupGameFrame();
	}

	@Override
	public void onPackageReceived(PackageReceivedEvent e) {
		if (e.getUpdateObject() instanceof TileMapUpdate) {
			Messager.info("Sucessfully downloaded the Map...");
			ClientMain.getClient().getTilemanager()
					.setCompressedDatamap(((TileMapUpdate) e.getUpdateObject()).getCompressedTilemap());
		} else {
		}
	}

	/**
	 * Redraws every few milliseconds
	 */
	@Override
	public void run() {
		while (true) {
			preDraw(surface.getGraphics());
			ClientMain.getClient().getEventManager().fire(new DrawEvent(surface.getGraphics()));
			try {
				sleep(30);
			} catch (InterruptedException e) {
			}
		}
	}

	private void preDraw(Graphics graphics) {
		//graphics.clearRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGTH);		
	}

	// TODO: react to all events to draw properly
	private void setupGameFrame() {

		frame = new JFrame();
		surface = new JPanel();
		frame.add(surface);
		frame.setSize(RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setFocusable(true);
		frame.setResizable(false);
		start();
	}

}
