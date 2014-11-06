package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
/**
 * Scene allows the engine to set what should be displayed
 * @author Seth
 *
 */
public abstract class Scene 
{
	private static Scene current = null;
	private static HashMap<String, Scene> stored = new HashMap<>();

	/**
	 * Gets the current scene that is displayed
	 * @return current scene
	 */
	public static Scene getCurrentScene() {
		return current;
	}

	/**
	 * Sets the current scene to be displayed
	 * @param scene - current scene to be displayed
	 * @param params - parameters to be set with current scene
	 */
	public static void setCurrentScene(Scene scene, HashMap<String, Object> params) {
		if(current != null)
			current.unload(scene);
		current = scene;
		scene.load(params);
	}
	
	/**
	 * Gets a stored scene with a given key
	 * @param key - key of scene
	 * @return requested scene
	 */
	public static Scene getScene(String key) {
		return stored.get(key);
	}
	
	/**
	 * Stores a scene for later use
	 * @param key - key stored under
	 * @param scene - Scene to store
	 */
	public static void store(String key, Scene scene) {
		stored.put(key,scene);
	}

	/**
	 * Draws the scene
	 * @param container - GameContainer to draw with
	 * @param g - Graphics to draw with
	 */
	public abstract void draw(GameContainer container, Graphics g);
	
	/**
	 * Updates the scene
	 * @param container - GameContainer to update with
	 * @param i - delta of time
	 */
	public abstract void update(GameContainer container, int i);
	
	/**
	 * Creates the scene
	 */
	abstract void load(HashMap<String, Object> params);
	
	/**
	 * Unloads the scene
	 * @param scene - Scene that is going to be displayed
	 */
	abstract void unload(Scene scene);
}
