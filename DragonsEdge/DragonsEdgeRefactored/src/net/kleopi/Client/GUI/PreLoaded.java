package net.kleopi.Client.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

public class PreLoaded {
	private Hashtable<String, BufferedImage> tiles = new Hashtable<>();
	private Hashtable<String, BufferedImage> characters = new Hashtable<>();
	private Hashtable<String, BufferedImage> othersprites = new Hashtable<>();

	public PreLoaded() {
		load_folder("tiles", tiles, "sprites\\tiles\\");
		load_folder("characters", characters, "sprites\\characters\\");
		load_folder("other", othersprites, "sprites\\other\\");
	}

	public BufferedImage getCharacter(String filename) {

		return characters.get(filename);
	}

	public BufferedImage getOtherSprite(String filename) {

		return othersprites.get(filename);
	}

	public BufferedImage getTile(String filename) {

		return tiles.get(filename);
	}

	private void load_folder(String folder_path, Hashtable<String, BufferedImage> hashtable, String skey) {

		File[] folder = new File("sprites\\" + folder_path + "\\").listFiles();
		if (folder != null) {
			for (File file : folder) {
				try {
					if (file.isDirectory()) {
						load_folder(folder_path + "\\" + file.getName(), hashtable, skey);
					} else {
						String key = file.getPath();
						key = key.replace(skey, "");
						hashtable.put(key, ImageIO.read(file));
					}
				} catch (IOException e) {
				}
			}
		}
	}
}
