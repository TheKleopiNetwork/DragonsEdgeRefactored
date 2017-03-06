package net.kleopi.Client.GUI;

public class Messager {

	// TODO: properly output it?
	public static void error(String string) {
		System.err.println(string);
		System.err.flush();

	}

	public static void info(String string) {
		System.out.println(string);
		System.out.flush();

	}

}
