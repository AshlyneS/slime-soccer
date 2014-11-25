package net.foxgenesis.slimesoccer.objects;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

public final class Bounds {

	private static final HashMap<String, Polygon> bounds = new HashMap<>();

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
					bounds.put(a.getName().substring(0,a.getName().lastIndexOf(".")), parse(Files.readAllLines(a.toPath(), Charset.defaultCharset()).get(0)));
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}


	public static Polygon get(String key) {
		return bounds.get(key);
	}

	public static boolean[][] getBoundsForImage(Image image) {
		boolean[][] points = new boolean[image.getWidth()][image.getHeight()];
		for(int i=0; i<image.getWidth(); i++)
			for(int j=0; j<image.getHeight(); j++) 
				if(closeToBlack(image.getColor(i, j)))
					points[i][j]=true;
		return points;
	}

	public static String toString(boolean[][] bounds) {
		String output = "Bounds{";
		for(int i=0; i<bounds.length; i++)
			for(int j=0; j<bounds.length; j++)
				if(bounds[i][j])
					output+=i+","+j+"|";
		return output.substring(0,output.length()-1) + "}";
	}

	private static boolean closeToBlack(Color color) {
		int s = color.getRed() + color.getBlue() + color.getGreen()/3;
		return s <= 255 && s > 76;
	}

	public static Polygon parse(String p) {
		if(p.startsWith("Polygon{")) {
			p = p.substring("Polygon{".length()).substring(0, p.length()-1);
			String[] points = p.split("|");
			Polygon poly = new Polygon();
			poly.setClosed(true);
			for(String a:points) {
				String[] d = a.split(",");
				int x = Integer.parseInt(d[0]),y = Integer.parseInt(d[1]);
				poly.addPoint(x,y);
			}
			return poly;
		}
		else
			return null;
	}
}
