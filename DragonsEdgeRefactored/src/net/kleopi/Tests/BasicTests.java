package net.kleopi.Tests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.kleopi.Engine.Enums.Messager;
import net.kleopi.Engine.EventManagement.EventManager;
import net.kleopi.Engine.EventManagement.TKNListenerAdapter;
import net.kleopi.Engine.EventManagement.GameEvents.PingEvent;
import net.kleopi.Engine.Exceptions.UnregisteredEventException;

/**
 * Several tests to test some features
 *
 *
 */
public class BasicTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private EventManager man;

	@Before
	public void setUp() throws Exception {
		man = new EventManager();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListenerCount() throws Exception {
		TKNListenerAdapter lis = new TKNListenerAdapter() {
		};
		man.addListener(lis);
		assertTrue(man.getListeners().size() == 1);
		man.removeListener(lis);
		assertTrue(man.getListeners().size() == 0);
	}

	@Test
	public void testPingEvent() {

		TKNListenerAdapter lis = new TKNListenerAdapter() {
			@Override
			public void onPing(PingEvent e) {
				Messager.info("[S] Ping was received");
				assertTrue(1 == 1);
				return;
			}
		};
		man.addListener(lis);
		man.fire(new PingEvent());
		try {
			man.dispatchNextEvent();
		} catch (UnregisteredEventException e1) {
			e1.printStackTrace();
		}
	}

}
