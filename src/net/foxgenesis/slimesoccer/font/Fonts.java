package net.foxgenesis.slimesoccer.font;

import java.io.File;
import java.util.HashMap;

import net.foxgenesis.slimesoccer.image.Textures;

import org.newdawn.slick.AngelCodeFont;

/**
 * Class created to automatically load fonts
 * @author Seth
 */
public final class Fonts {
	private static HashMap<String, AngelCodeFont> fonts = new HashMap<>();

	/**
	 * Initialize required fonts
	 */
	public static void init() {
		System.out.println("Loading all fonts...");
		File fontsFile = new File("fonts");
		System.out.println("Searching fonts file for fonts...");
		for(File a:fontsFile.listFiles())
			if(a.getName().endsWith(".fnt"))
				loadFont(a);
		System.out.println("Loaded all fonts!");
	}

	private static void loadFont(File file) {
		try {
			System.out.println("Loading font: [" + file.getName() + "]...");
			String name = file.getName().substring(0,file.getName().lastIndexOf('.'));
			if(!Textures.contains(name))
				System.err.println("WARNING: Loading font file with missing textures [" + name + "]!");
			fonts.put(name, new AngelCodeFont(file.toString(), Textures.get(name)));
			System.out.println("Font stored with key: " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}

	/**
	 * Gets a font from a given key
	 * @param key - key with stored font
	 * @return wanted font
	 */
	public static AngelCodeFont get(String key) {
		return fonts.get(key);
	}
}
