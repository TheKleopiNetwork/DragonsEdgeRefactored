package net.kleopi.Client.GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.kleopi.Client.Main.ClientMain;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.DisconnectEvent;
import net.kleopi.Engine.EventManagement.GameEvents.LoginEvent;
import net.kleopi.Engine.EventManagement.GameEvents.StartupEvent;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel implements TKNListenerAdapter {

	private static JFrame loginFrame;

	private static void createAndShowGUI() {

		// Create and set up the window.
		JFrame frame = new JFrame("[TKN] Dragons Edge");
		loginFrame = frame;
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and set up the content pane.
		final LoginPanel newContentPane = new LoginPanel(frame);
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Make sure the focus goes to the right component
		// whenever the frame is initially given the focus.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {

				newContentPane.resetFocus();
			}
		});

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void createPasswordWindow() {

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(() -> {
			// Turn off metal's use of bold fonts
			UIManager.put("swing.boldMetal", Boolean.FALSE);
			createAndShowGUI();
		});
	}

	private JPasswordField passwordField;
	private JTextField textField;

	public LoginPanel(JFrame f) {
		// Create everything.
		passwordField = new JPasswordField(20);
		textField = new JTextField(10);
		passwordField.addActionListener(e -> performClickAction(e));
		textField.addActionListener(e -> performClickAction(e));

		JLabel plabel = new JLabel("Password: ");
		JLabel ulabel = new JLabel("Username: ");
		plabel.setLabelFor(passwordField);
		ulabel.setLabelFor(textField);

		JComponent buttonPane = createButtonPanel();

		// Lay out everything.
		JPanel textPane = new JPanel();
		textPane.add(ulabel);
		textPane.add(textField);
		textPane.add(plabel);
		textPane.add(passwordField);

		add(textPane);
		add(buttonPane);
		ClientMain.getClient().getEventManager().addListener(this);
	}

	protected JComponent createButtonPanel() {

		JPanel p = new JPanel(new GridLayout(0, 1));
		JButton okButton = new JButton("OK");

		okButton.addActionListener(e -> performClickAction(e));

		p.add(okButton);

		return p;
	}

	@Override
	public void onDisconnect(DisconnectEvent e) {
		createPasswordWindow();
	}

	@Override
	public void onStartUp(StartupEvent e) {
		createPasswordWindow();
	}

	public void performClickAction(ActionEvent e) {

		char[] input = passwordField.getPassword();
		String username = textField.getText();
		String password = new String(input);
		input = null;
		ClientMain.getClient().getEventManager().fire(new LoginEvent(username, password));
		loginFrame.dispose();
	}

	protected void resetFocus() {

		passwordField.requestFocusInWindow();
	}
}
