package net.kleopi.Client.GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import net.kleopi.Engine.Enums.Messager;

public class PreLoaded {
	private final Hashtable<String, BufferedImage> tiles = new Hashtable<>();
	private final Hashtable<String, BufferedImage> characters = new Hashtable<>();
	private final Hashtable<String, BufferedImage> othersprites = new Hashtable<>();

	public PreLoaded() {
		Messager.info("PreLoading Sprites");
		load_folder("tiles", tiles, "sprites\\tiles\\");
		load_folder("characters", characters, "sprites\\characters\\");
		load_folder("other", othersprites, "sprites\\other\\");
		Messager.info("Sprites had been successfully loaded");
	}

	public BufferedImage getCharacter(String filename) {

		return characters.get(filename);
	}

	public BufferedImage getOtherSprite(String filename) {

		return othersprites.get(filename);
	}

	/**
	 * @param sprite
	 * @return
	 */
	public BufferedImage getSprite(Sprite sprite) {
		BufferedImage image = characters.get(sprite.getPath());
		if (image == null) {
			image = othersprites.get(sprite.getPath());
		}
		if (image == null) {
			Messager.error("Sprite not found: " + sprite.getPath());
		}
		return image;

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
						if (file.getName().endsWith(".png") || file.getName().endsWith(".jpg")) {
							String key = file.getPath();
							key = key.replace(skey, "");
							hashtable.put(key, ImageIO.read(file));
						} else {
							Messager.error("Found an unusual data in the sprite folder: " + file.getName()
									+ " could not be loaded!");
						}
					}
				} catch (IOException e) {
					Messager.error(e.toString());
				}
			}
		} else {
			Messager.error("File is null. This might be a bug");
		}
	}
}
