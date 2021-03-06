package net.kleopi.Client.GUI;

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import net.kleopi.Client.Input.InputReceiver;
import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoggedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.PackageReceivedEvent;
import net.kleopi.Engine.EventManagement.GameEvents.TickEvent;
import net.kleopi.Engine.Networking.UpdateObjects.TileMapUpdate;

public class GUI extends Thread implements TKNListenerAdapter {
	public final static int RESOLUTION_WIDTH = 1920;
	public final static int RESOLUTION_HEIGTH = 1080;
	private static final int numBuffers = 4;
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
		for (DisplayMode element : BEST_DISPLAY_MODES) {
			DisplayMode[] modes = device.getDisplayModes();
			for (DisplayMode mode : modes) {
				if (mode.getWidth() == element.getWidth() && mode.getHeight() == element.getHeight()
						&& mode.getBitDepth() == element.getBitDepth()) {
					return element;
				}
			}
		}
		return null;
	}

	BufferedImage tileLayer = new BufferedImage(RESOLUTION_WIDTH, RESOLUTION_HEIGTH, BufferedImage.TYPE_3BYTE_BGR);

	BufferedImage spritelayer = new BufferedImage(RESOLUTION_WIDTH, RESOLUTION_HEIGTH, BufferedImage.TYPE_4BYTE_ABGR);

	BufferedImage shaderlayer = new BufferedImage(RESOLUTION_WIDTH, RESOLUTION_HEIGTH, BufferedImage.TYPE_4BYTE_ABGR);

	BufferedImage hudlayer = new BufferedImage(RESOLUTION_WIDTH, RESOLUTION_HEIGTH, BufferedImage.TYPE_4BYTE_ABGR);

	private JFrame frame;

	// private JPanel surface;

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

	@Override
	public void onDraw(DrawEvent e) {
		e.getHudlayer().setColor(Color.black);
		e.getHudlayer().drawString(String.valueOf((int) fps), 20, 20);
	}

	@Override
	public void onPackageReceived(PackageReceivedEvent e) {
		if (e.getUpdateObject() instanceof TileMapUpdate) {
			Messager.info("Sucessfully downloaded the Map...");
			ClientMain.getClient().getTilemanager()
					.setCompressedDatamap(((TileMapUpdate) e.getUpdateObject()).getCompressedTilemap());
		}
	}

	@Override
	public void onPreDraw(DrawEvent e) {
		e.getShaderlayer().setBackground(new Color(0x88000000, true));
		e.getShaderlayer().clearRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
		// general darkness

		e.getSpritelayer().setBackground(new Color(0x00FFFFFF, true));
		e.getSpritelayer().clearRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
		e.getHudlayer().setBackground(new Color(0x00FFFFFF, true));
		e.getHudlayer().clearRect(0, 0, RESOLUTION_WIDTH, RESOLUTION_HEIGTH);
	}

	/**
	 * TODO: react on Logged Event
	 */
	@Override
	public void onSuccessfulLoginReturn(LoggedEvent e) {
		setupGameFrame();
	}

	@Override
	public void onTick(TickEvent e) {
		fps = fps / 2 + fpsct * 30;
		fpsct = 0;
	};

	/**
	 * Redraws every few milliseconds
	 */
	@Override
	public void run() {
		// renderingLoop
		while (true) {
			Graphics g = bufferStrategy.getDrawGraphics();
			if (!bufferStrategy.contentsLost()) {

				ClientMain.getClient().getEventManager().fire(new DrawEvent(tileLayer.createGraphics(),
						spritelayer.createGraphics(), shaderlayer.createGraphics(), hudlayer.createGraphics()));
				g.drawImage(tileLayer, 0, 0, null);
				g.drawImage(spritelayer, 0, 0, null);
				g.drawImage(shaderlayer, 0, 0, null);
				g.drawImage(hudlayer, 0, 0, null);
				bufferStrategy.show();
				fpsct += 1;
				g.dispose();
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}

	// TODO: react to all events to draw properly
	private void setupGameFrame() {

		try {
			GraphicsConfiguration gc = device.getDefaultConfiguration();
			InputReceiver input = new InputReceiver();
			frame = new JFrame(gc);
			frame.setUndecorated(true);
			frame.setIgnoreRepaint(true);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame.addMouseListener(input);
			frame.addKeyListener(input);
			frame.addMouseWheelListener(input);
			device.setFullScreenWindow(frame);
			if (device.isDisplayChangeSupported()) {
				chooseBestDisplayMode(device);
			}
			frame.createBufferStrategy(numBuffers);
			bufferStrategy = frame.getBufferStrategy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		start();
	}

}
