package net.kleopi.Client.GUI;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DrawEvent;

public class GUI implements TKNListenerAdapter {
	public final static int RESOLUTION_WIDTH = 1920;
	public final static int RESOLUTION_HEIGTH = 1080;
	public static GUI gui;

	public static Point getMousePoint() {

		return (gui.surface.getMousePosition());
	}

	private JPanel surface;
	private final LoginPanel loginpanel;

	private JFrame frame;

	public GUI() {
		loginpanel = new LoginPanel(frame);
		ClientMain.getClient().getEventManager().addListener(this);

	}

	@Override
	public void onDraw(DrawEvent e) {
		gui.surface.repaint();
	}

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
	}

}
