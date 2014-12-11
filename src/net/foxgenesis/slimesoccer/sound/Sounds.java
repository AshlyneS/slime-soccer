package net.foxgenesis.slimesoccer.sound;

import java.io.File;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

/**
 * Sounds load all game sounds
 * @author Seth
 */
public final class Sounds {

	private static HashMap<String, Sound> sounds = new HashMap<>();
	
	/**
	 * Load all game sounds
	 */
	public static void init(){
		System.out.println("loading all sounds...");
		loadFiles(new File("sounds"));
		System.out.println("sounds loaded!");
	}

	private static void loadFiles(File a){
		if(a.isDirectory()) {
			System.out.println("searching folder [" + a.getName() + "] for sounds... {");
			for(File b:a.listFiles())
				loadFiles(b);
			System.out.println("\t}");
		}
		else if(a.isFile()) {
			try{
				if(a.getName().endsWith(".ogg")) {
					System.out.println("\t  |found sound: [" + a.getName().substring(0,a.getName().lastIndexOf(".")) + "]");
					sounds.put(a.getName().substring(0,a.getName().lastIndexOf(".")), new Sound(a.getPath()));
				}
			} catch(SlickException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get a Sound for a id
	 * @param string - id of sound
	 * @return wanted sound
	 */
	public static Sound get(String string) {
		if(sounds.containsKey(string))
			return sounds.get(string);
		else
			return sounds.get("missing");
	}

	/**
	 * Check if the sound manager contains an sound
	 * @param string - id of sound
	 * @return is sound loaded
	 */
	public static boolean contains(String string) {
		return sounds.containsKey(string);
	}

	/**
	 * Get the current list of sounds loaded
	 * @return sounds loaded
	 */
	public static String[] getKeyList() {
		return sounds.keySet().toArray(new String[]{});
	}
}
