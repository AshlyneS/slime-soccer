package net.foxgenesis.slimesoccer.objects;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 * Bounds loads pre-made object bounds
 * @author Seth
 */
public final class Bounds {

	private static final HashMap<String, Polygon> bounds = new HashMap<>();

	/**
	 * Load all bounds files
	 */
	public static synchronized void init() {
		System.out.println("Loading bounds...");
		loadFiles(new File("bounds"));
		System.out.println("Loaded all bounds!");
	}

	private static void loadFiles(File a) {
		if(a.isDirectory()) {
			System.out.println("searching folder [" + a.getName() + "] for bounds... {");
			for(File b:a.listFiles())
				loadFiles(b);
			System.out.println("\t}");
		}
		else if(a.isFile()) {
			try{
				if(a.getName().endsWith(".bnd")) {
					System.out.println("\t  |found bounds: [" + a.getName().substring(0,a.getName().lastIndexOf(".")) + "]");
					String name = a.getName().substring(0,a.getName().lastIndexOf(".bnd"));
					String b = Files.readAllLines(a.toPath(), Charset.defaultCharset()).get(0);
					System.out.println("reading : " + b);
					bounds.put(name, parse(b));
					if(bounds.get(name) == null)
						System.err.println("WARNING: Bounds[" + name + "] returned null!");
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Get a bounds from a given key
	 * @param key - bounds key
	 * @return object bounds
	 */
	public static Polygon get(String key) {
		return bounds.get(key);
	}

	/**
	 * check if bounds is currently loaded
	 * @param key - key to check with
	 * @return if bounds is loaded
	 */
	public static boolean contains(String key) {
		return bounds.containsKey(key);
	}

	/**
	 * Used in creating bounds files. get a boolean array for an image
	 * @param image - image to create from
	 * @return boolean area used for .bnd creation
	 */
	public static boolean[][] getBoundsForImage(Image image) {
		boolean[][] points = new boolean[image.getWidth()][image.getHeight()];
		for(int i=0; i<image.getWidth(); i++)
			for(int j=0; j<image.getHeight(); j++) 
				if(closeToBlack(image.getColor(i, j)))
					points[i][j]=true;
		return points;
	}

	/**
	 * creates a .bnd representation from given boolean array
	 * @param bounds - boolean array to create from
	 * @return .bnd string
	 */
	public static String toString(boolean[][] bounds) {
		String output = "Bounds{";
		for(int i=0; i<bounds.length; i++)
			for(int j=0; j<bounds.length; j++)
				if(bounds[i][j])
					output+=i+","+j+"|";
		return output.substring(0,output.length()-1) + "}";
	}

	private static boolean closeToBlack(Color color) {
		double s = 0.2126*color.getRed() + 0.0722*color.getBlue() + 0.7152*color.getGreen()/3;
		//System.out.println(s);
		return s < 128;
	}

	/**
	 * Create a bounds from a given string
	 * @param p - string to create from
	 * @return created bounds
	 */
	public static Polygon parse(String p) {
		if(p.startsWith("Bounds{")) {
			String b = p.substring("Bounds{".length());
			b = b.substring(0, b.length()-1);
			String[] points = b.split("\\$");
			Polygon poly = new Polygon();
			poly.setClosed(true);
			for(String a:points) {
				String[] d = a.split(",");
				if(d.length != 2 || (d[0].equals("") || d[1].equals("")))
					System.err.println(a);
				int x = Integer.parseInt(d[0]),y = Integer.parseInt(d[1]);
				poly.addPoint(x,y);
			}
			return poly;
		}
		return null;
	}
}
