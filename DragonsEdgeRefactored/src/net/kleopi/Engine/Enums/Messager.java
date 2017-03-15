package net.kleopi.Engine.Enums;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.esotericsoftware.minlog.Log.Logger;

public class Messager extends Logger{
	private static boolean isChecked = false;

	// TODO: properly output it?
	public static void error(String string) {
		System.err.println("[ERROR]: "+string);
		System.err.flush();
		log("[ERROR]: " + string);

	}

	public static void info(String string) {
		System.out.println("[INFO]:  " +string);
		System.out.flush();
		log("[INFO]:  " + string);

	}
	@Override
	protected void print(String s) {
		info(s);
	}

	public static void log(String string) {
		if (!isChecked) {
			prepareFile();
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		String dateString = (dtf.format(localDate));
		try {
			Path path = Paths.get("logs\\" + dateString + ".log");
			List<String> lines = Files.readAllLines(path);
			lines.add(string);
			Files.write(path, lines);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void prepareFile() {
		isChecked = true;
		System.out.println("More Information about this Test can be found in the Logs folder!");
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate localDate = LocalDate.now();
		String dateString = (dtf.format(localDate));
		try {
			Path path = Paths.get("logs\\" + dateString + ".log");
			Files.createFile(path);
		} catch (IOException e) {
		}
		log("-----------------------------------------");
	}

}
