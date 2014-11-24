package net.foxgenesis.slimesoccer.image;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Textures loads and stores all texture files within the textures folder
 * @author Seth
 *
 */
public final class Textures {
	private static HashMap<String, Image> textures = new HashMap<>();
	public static void init(){
		System.out.println("loading all textures...");
		try { 
			loadFiles(new File("textures"));
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("textures loaded!");
	}

	private static void loadFiles(File a) throws SlickException, IOException {
		if(a.isDirectory()) {
			System.out.println("searching folder [" + a.getName() + "] for textures... {");
			for(File b:a.listFiles())
				loadFiles(b);
			System.out.println("\t}");
		}
		else if(a.isFile()) {
			if(a.getName().endsWith(".png") || a.getName().endsWith(".jpg")) {
				System.out.println("\t  |found texture: [" + a.getName().substring(0,a.getName().lastIndexOf(".")) + "]");
				textures.put(a.getName().substring(0,a.getName().lastIndexOf(".")), new Image(a.toString()));
			}
		}
	}

	/**
	 * Get an Image for a id
	 * @param string - id of image
	 * @return Image
	 */
	public static Image get(String string) {
		return textures.get(string);
	}

	/**
	 * Check if texture manager contains an image
	 * @param string - id of image
	 * @return is texture loaded
	 */
	public static boolean contains(String string) {
		return textures.containsKey(string);
	}
}

