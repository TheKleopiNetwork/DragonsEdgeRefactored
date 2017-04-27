package net.kleopi.Api;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import net.kleopi.Engine.Enums.Messager;

public class PluginManager {

	private static final String pluginpath = "plugins";

	List<DragonsEdgePlugin> plugins = new ArrayList<>();

	public PluginManager() {
		Messager.info("Enabling Plugins");
		try {
			searchAndEnablePlugins();
			Messager.info("Plugins loaded");
		} catch (Exception e) {
			Messager.error("Error while loading plugins: " + e);
		}

	}

	private void disableAllPlugins() {

	}

	@SuppressWarnings("rawtypes")
	private void searchAndEnablePlugins() {
		try {
			// list files
			DirectoryStream<Path> directories = Files.newDirectoryStream(Paths.get(pluginpath));
			List<Path> jarfiles = new ArrayList<>();
			// check if .jar
			for (Path p : directories) {
				if (p.toString().endsWith(".jar")) {
					jarfiles.add(p);
				}
			}
			// Extract all Files which implement the DragonsEdgePlugin-Interface
			// into the plugin list
			if (!jarfiles.isEmpty()) {
				for (Path p : jarfiles) {
					JarFile jar = new JarFile(new File(p.toUri()));
					for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements();) {
						JarEntry entry = entries.nextElement();
						String name = entry.getName();
						if (name.endsWith(".class") == false) {
							continue;
						}
						String className = name.replaceAll("/", ".");
						className = className.substring(0, className.length() - ".class".length());

						URLClassLoader loader = URLClassLoader.newInstance(new URL[] { new File(p.toUri()).toURL() });
						Class<?> c = loader.loadClass(className);
						List<Class> interfaces = Arrays.asList(c.getInterfaces());
						if (interfaces.contains(DragonsEdgePlugin.class)) {
							Messager.info("Plugin found:");
							DragonsEdgePlugin plugin = (DragonsEdgePlugin) c.newInstance();
							Messager.info("Loading plugin " + plugin.getName());
							plugins.add(plugin);
						}

					}
					jar.close();
				}
				// enable all plugins
				for (DragonsEdgePlugin plugin : plugins) {
					plugin.onEnable();
				}
			} else {
				Messager.info("No plugins in file");
			}
		} catch (Exception e) {
			Messager.error("Plugin was not loaded");
		}
	}

}
