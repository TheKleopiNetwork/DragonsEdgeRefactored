package net.kleopi.Client.GUI;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;

public class GUI extends Thread implements TKNListenerAdapter {
	public final static int RESOLUTION_WIDTH = 1920;
	public final static int RESOLUTION_HEIGTH = 1080;
	private static final int numBuffers = 16;

	private static DisplayMode[] BEST_DISPLAY_MODES = new DisplayMode[] { new DisplayMode(1920, 1080, 32, 0),
			new DisplayMode(1920, 1080, 16, 0), new DisplayMode(1920, 1080, 8, 0), new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 16, 0), new DisplayMode(640, 480, 8, 0) };

	private static void chooseBestDisplayMode(GraphicsDevice device) {
		DisplayMode best = getBestDisplayMode(device);
		if (best != null) {
			device.setDisplayMode(best);
		}
	}

	private static DisplayMode getBestDisplayMode(GraphicsDevice device) {
		for (int x = 0; x < BEST_DISPLAY_MODES.length; x++) {
			DisplayMode[] modes = device.getDisplayModes();
			for (int i = 0; i < modes.length; i++) {
				if (modes[i].getWidth() == BEST_DISPLAY_MODES[x].getWidth()
						&& modes[i].getHeight() == BEST_DISPLAY_MODES[x].getHeight()
						&& modes[i].getBitDepth() == BEST_DISPLAY_MODES[x].getBitDepth()) {
					return BEST_DISPLAY_MODES[x];
				}
			}
		}
		return null;
	}

	// private JPanel surface;

	private JFrame frame;

	private GraphicsDevice device;
	private BufferStrategy bufferStrategy;
	private double fps = 0;
	private int fpsct = 0;

	public GUI() {
		new LoginPanel(frame);
		ClientMain.getClient().getEventManager().addListener(this);
		try {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			device = env.getDefaultScreenDevice();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Point getMousePoint() {

		return (frame.getMousePosition());
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
			System.out.println("Object wurde nicht als Map erkannt");
		}
	}

	@Override
	public void onTick(TickEvent e) {
		fps = fps / 2 + fpsct * 30;
		fpsct = 0;
	}

	/**
	 * Redraws every few milliseconds
	 */
	@Override
	public void run() {
		// renderingLoop
		while (true) {
			Graphics g = bufferStrategy.getDrawGraphics();
			if (!bufferStrategy.contentsLost()) {
				preDraw(g);
				ClientMain.getClient().getEventManager().getListeners().forEach(e -> e.onDraw(new DrawEvent(g)));
				g.setColor(Color.BLACK);
				g.drawString(String.valueOf(fps), 20, 20);
				bufferStrategy.show();
				fpsct += 1;
			}
		}
	};

	private void preDraw(Graphics graphics) {
		// graphics.clearRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
	}

	// TODO: react to all events to draw properly
	private void setupGameFrame() {

		try {
			GraphicsConfiguration gc = device.getDefaultConfiguration();

			frame = new JFrame(gc);
			frame.setUndecorated(true);
			frame.setIgnoreRepaint(true);
			device.setFullScreenWindow(frame);
			if (device.isDisplayChangeSupported()) {
				chooseBestDisplayMode(device);
			}
			frame.createBufferStrategy(numBuffers);
			bufferStrategy = frame.getBufferStrategy();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// surface = new JPanel();
		// frame.add(surface);
		// frame.setSize(RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
		// frame.setVisible(true);
		// frame.setLayout(null);
		// frame.setFocusable(true);
		// frame.setResizable(false);
		start();
	}

}
