package net.foxgenesis.slimesoccer.image;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * Textures loads and stores all texture files within the textures folder
 * @author Seth
 *
 */
public final class Textures {
	private static HashMap<String, Image> textures = new HashMap<>();
	public static Animation confetti;
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
			} catch(Exception e) {
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
		else {
			System.err.println("WARNING: textures \"" + string + "\" is not found!");
			return textures.get("missing");
		}
	}

	/**
	 * Check if texture manager contains an image
	 * @param string - id of image
	 * @return is texture loaded
	 */
	public static boolean contains(String string) {
		return textures.containsKey(string);
	}

	/**
	 * Gets the current keys that are loaded
	 * @return texture key list
	 */
	public static String[] getKeyList() {
		return textures.keySet().toArray(new String[]{});
	}

	/**
	 * Create an Animation from a given starting key and duration
	 * @param key - key to look for
	 * @param frameDuration - duration for each frame
	 * @return created Animation from layers
	 * @throws SlickException
	 */
	public static Animation createGif(String key, int frameDuration) throws SlickException {
		System.out.println("Creating animation from key \"" + key + "\"...");
		ArrayList<Image> imgs = new ArrayList<>();
		int count = 0;
		for(String a:getKeyList())
			if(a.contains(key))
				count++;
		System.out.println("Found " + count + " frames to make an animation from");
		int[] dur = new int[count];
		for(int i=0; i<count; i++) {
			int j = count - (i+1);
			imgs.add(removeWhiteBackground(Textures.get(key+"_"+(j>99?"0":j>9?"00":"000")+j+"_Layer " + (i+1))));
		}
		for(int i=0; i<count; i++)
			dur[i] = frameDuration;
		System.out.println("Created " + imgs.size() + " frames for animation");
		return new Animation(imgs.toArray(new Image[]{}),dur);
	}

	private static Image removeWhiteBackground(Image img) {
		try {
			Image output = new Image(img.getWidth(),img.getHeight());
			Graphics g;
			try {
				g = output.getGraphics();
				for(int i=0; i<img.getWidth(); i++)
					for(int j=0; j<img.getHeight(); j++) {
						Color c = img.getColor(i,j);
						if(c.getRed() + c.getBlue() + c.getGreen() < 600) {
							g.setColor(img.getColor(i,j));
							g.fillRect(i,j,1,1);
						}
					}
				return output;
			} catch (SlickException e) {
				e.printStackTrace();
			}
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		return img;
	}
}

