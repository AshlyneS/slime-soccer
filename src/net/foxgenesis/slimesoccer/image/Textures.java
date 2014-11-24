package net.foxgenesis.slimesoccer.image;

import java.io.File;
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
		loadFiles(new File("textures"));
		System.out.println("textures loaded!");
	}

	private static void loadFiles(File a){
		if(a.isDirectory()) {
			System.out.println("searching folder [" + a.getName() + "] for textures... {");
			for(File b:a.listFiles())
				loadFiles(b);
			System.out.println("\t}");
		}
		else if(a.isFile()) {
			try{
				if(a.getName().endsWith(".png") || a.getName().endsWith(".jpg")) {
					System.out.println("\t  |found texture: [" + a.getName().substring(0,a.getName().lastIndexOf(".")) + "]");
					textures.put(a.getName().substring(0,a.getName().lastIndexOf(".")), new Image(a.toString()));
				}
			} catch(SlickException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get an Image for a id
	 * @param string - id of image
	 * @return Image
	 */
	public static Image get(String string) {
		if(textures.containsKey(string))
			return textures.get(string);
		else
			return textures.get("missing");
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

