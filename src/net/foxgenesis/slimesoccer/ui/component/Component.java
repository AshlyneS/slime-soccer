package net.foxgenesis.slimesoccer.ui.component;

import org.newdawn.slick.Graphics;

/**
 * Component is a base framework for all UI components
 * @author Seth
 */
public class Component {
	
	private int x,y,width,height;
	private boolean visible=true;

	/**
	 * Get the width of the component
	 * @return component width
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get the height of the component
	 * @return component height
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Get the x location
	 * @return x coord
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the y location
	 * @return y coord
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Checks if the component is visible
	 * @return
	 */
	public boolean isVisible() {
		return visible;
	}
	
	/**
	 * Set the display state for the component
	 * @param state - display state
	 */
	public void setVisible(boolean state) {
		visible = state;
	}
	
	/**
	 * Sets the location for the component
	 * @param x - x coord
	 * @param y - y coord
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Sets the component's size
	 * @param width - width of component
	 * @param height - height of component
	 */
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Checks if a point is inside of the component
	 * @param x - x coord
	 * @param y - y coord
	 * @return if point is inside component
	 */
	public boolean contains(int x, int y) {
		if(x<this.x || x>this.x+width)
			return false;
		else if(y<this.y || y>this.y+height)
			return false;
		return true;
	}
	
	/**
	 * Called on frame update
	 * @param delta - delay from last frame
	 */
	public void update(int delta){}
	
	/**
	 * Draws the component with given graphics
	 * @param g - graphics to draw with
	 */
	public void draw(Graphics g) {
		if(!visible)
			return;
	}
}
