package net.foxgenesis.slimesoccer.ui;

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
	 */
	public static void setCurrentScene(Scene scene) {
		if(current != null)
			current.unload(scene);
		current = scene;
		scene.load();
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
	abstract void load();
	
	/**
	 * Unloads the scene
	 * @param scene - Scene that is going to be displayed
	 */
	abstract void unload(Scene scene);
}
