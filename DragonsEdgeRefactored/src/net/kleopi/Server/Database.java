package net.kleopi.Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import net.kleopi.Engine.Enums.Messager;

/**
 * Database to save User data. TODO: change encryption(!)
 *
 *
 */
public class Database {
	/**
	 * Decrypts a String
	 *
	 * @param text
	 *            - The text to decrypt
	 * @return Decrypted String
	 */
	private static String decrypt(String text) {

		byte[] bytes = text.getBytes();

		bytes = negateBytes(bytes, 2);
		bytes = negateBytes(bytes, 5);
		bytes = shuffle(bytes);
		return new String(bytes);
	}

	/**
	 * Encrypts a String
	 *
	 * @param text
	 *            - The text to encrypt
	 * @return Encrypted String
	 */

	private static String encrypt(String text) {

		byte[] bytes = text.getBytes();
		bytes = shuffle(bytes);
		bytes = negateBytes(bytes, 5);
		bytes = negateBytes(bytes, 2);
		return new String(bytes);
	}

	/**
	 * Get a saved value of a specific user
	 *
	 * @param username
	 *            - name of the user
	 * @param key
	 *            - the key to find a value
	 * @return the value of 'key' for user 'username'. null when not found.
	 *         "error" when file could not be found.
	 */
	public static String getValue(String username, String key) {

		File file = new File("database\\" + username + ".userdata");
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String parts[] = line.split(":");
				if (key.equals(parts[0])) {
					return decrypt(parts[1]);
				}
			}
		} catch (FileNotFoundException e) {
			{
				return "error";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	private static byte negateBit(byte b, int bitPosition) {

		return (b ^= 1 << bitPosition);
	}

	private static byte[] negateBytes(byte[] bytes, int i) {

		for (int j = 0; j < bytes.length; j++) {
			bytes[j] = negateBit(bytes[j], i);
		}
		return bytes;
	}

	/**
	 * Get a saved value of a specific user
	 *
	 * @param username
	 *            - name of the user
	 * @param key
	 *            - the key to find a value
	 * @param value
	 *            - the value to set
	 */
	public static void setValue(String username, String key, String value) {

		Path path = Paths.get("database\\" + username + ".userdata");
		File file = new File("database\\" + username + ".userdata");
		if (!file.exists() && !file.isDirectory()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		boolean replaced = false;

		List<String> fileContent;
		try {
			fileContent = new ArrayList<>(Files.readAllLines(path));

			for (int i = 0; i < fileContent.size(); i++) {
				if (fileContent.get(i).contains(key)) {
					fileContent.set(i, key + ":" + encrypt(value));
					replaced = true;
					break;
				}
			}
			if (!replaced) {
				fileContent.add(key + ":" + encrypt(value));
			}
			Files.write(path, fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static byte[] shuffle(byte[] bytes) {

		for (int i = 0; i + 2 <= bytes.length; i += 2) {
			byte save = bytes[i];
			bytes[i] = bytes[i + 1];
			bytes[i + 1] = save;
		}
		return bytes;
	}

	/**
	 * Tries to find a user in the database. Returns true if user and password
	 * could be found. Also writes status to log, to detect hacking etc.
	 *
	 * @param username
	 * @param password
	 * @return true when username and password could be found in the database.
	 *         false otherwise.
	 */

	public static boolean tryLogin(String username, String password) {

		System.out.println(username);
		System.out.println(password);
		if ((getValue(username, "password").equals(password)) && (password != "null")) {
			Messager.info("User: " + username + " could be verified");
			return true;
		} else {
			Messager.info("User: " + username + " could not be logged in. Username or Password seems to be wrong.");
			return false;
		}
	}
}
