package net.foxgenesis.slimesoccer.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * FadingTransition is a scene to scene transition effect
 * @author Seth
 *
 */
public class FadingTransition {
	private Image image;
	private float opacity = 1f;
	private final float SPEED;
	
	/**
	 * Create a new transition with a given image
	 * @param image - image to use
	 */
	public FadingTransition(Image image) {
		this(image,0.1f);
	}
	
	/**
	 * Create a new transition with a given image and speed
	 * @param image - image to use
	 * @param speed - speed of transition
	 */
	public FadingTransition(Image image, float speed) {
		this.image = image;
		SPEED = speed;
	}
	
	/**
	 * Draw the transition
	 * @param container - GameContainer to use
	 * @param g - Graphics to use
	 */
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(image,0,0,container.getWidth(),container.getHeight(),0,0,image.getWidth(),image.getHeight(), new Color(1f,1f,1f,opacity));
	}
	
	/**
	 * Update the transition
	 * @param delta - elapsed time
	 */
	public void update(int delta) {
		if(opacity > 0f) 
			opacity-=SPEED;
	}
}
