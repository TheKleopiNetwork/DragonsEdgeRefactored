package net.kleopi.Client.GUI;

import java.awt.image.BufferedImage;

import net.kleopi.Client.Main.ClientMain;

public enum Sprite {
	// TODO: set enum values and implement all sprites
	WATER_CHARACTER_LEFT("picturepath"), EARTH_CHARACTER_LEFT("gg"), FIRE_CHARACTER_LEFT("gg"), AIR_CHARACTER_LEFT(
			"gg"), WATER_CHARACTER_RIGHT("gg"), EARTH_CHARACTER_RIGHT("gg"), FIRE_CHARACTER_RIGHT(
					"gg"), AIR_CHARACTER_RIGHT("gg"), WATER_CHARACTER_UP("gg"), EARTH_CHARACTER_UP(
							"gg"), FIRE_CHARACTER_UP("gg"), AIR_CHARACTER_UP("gg"), WATER_CHARACTER_DOWN(
									"gg"), EARTH_CHARACTER_DOWN("gg"), FIRE_CHARACTER_DOWN("gg"), AIR_CHARACTER_DOWN(
											"gg"), NO_SPRITE("gg");
	public static Sprite getSpriteOfPath(String path) {

		for (Sprite s : Sprite.values()) {
			if (path == s.getPath()) {
				return s;
			}
		}
		return Sprite.NO_SPRITE;
	}

	public static String getValue(Sprite sprite) {

		return sprite.getPath();
	}

	private String path;

	Sprite(String s) {
		path = s;
	}

	public BufferedImage getImage() {
		return ClientMain.getClient().getPreloaded().getSprite(this);
	}

	public String getPath() {

		return path;
	}
}
