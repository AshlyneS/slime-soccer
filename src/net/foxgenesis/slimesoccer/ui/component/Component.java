package net.foxgenesis.slimesoccer.ui.component;

import static net.foxgenesis.slimesoccer.Settings.CYAN;
import static net.foxgenesis.slimesoccer.Settings.GUI_DISTANCE;
import static net.foxgenesis.slimesoccer.Settings.RED;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

/**
 * Component is a base framework for all UI components
 * @author Seth
 */
public abstract class Component {

	private Vector2f location,sLocation,sVelocity;
	protected float width,height;
	private boolean visible=true,smoothMoving = true;

	public Component() {
		location = new Vector2f();
		sLocation = new Vector2f();
		sVelocity = new Vector2f();
	}
	/**
	 * Get the width of the component
	 * @return component width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Checks whether the component moves over time
	 * @return is smooth moving
	 */
	public boolean isSmoothMoving() {
		return smoothMoving;
	}

	/**
	 * Sets whether the object should move over time
	 * @param state - should move over time
	 */
	public void setSmoothMoving(boolean state) {
		smoothMoving = state;
	}

	/**
	 * Get the height of the component
	 * @return component height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Get the location of the component
	 * @return component location
	 */
	public Vector2f getLocation() {
		return location;
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
	 * @param f - x coord
	 * @param g - y coord
	 */
	public void setLocation(float f, float g) {
		setLocation(f,g,1000);
	}
	
	/**
	 * Sets the location for the component
	 * @param x - x coord
	 * @param y - y coord
	 * @param time - time to move (Only applies for smooth moving)
	 */
	public void setLocation(float x, float y, long time) {
		if(smoothMoving) {
			sLocation.x = x;
			sLocation.y = y;
			float xD = Math.abs(location.x - x),yD = Math.abs(location.y - y);
			sVelocity.x = xD/(time/100);
			sVelocity.y = yD/(time/100);
		}
		else {
			location.x = x;
			location.y = y;
		}
	}

	/**
	 * Sets the component's size
	 * @param width - width of component
	 * @param height - height of component
	 */
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Checks if a point is inside of the component
	 * @param x - x coord
	 * @param y - y coord
	 * @return if point is inside component
	 */
	public boolean contains(float x, float y) {
		if(x<location.x || x>location.x+width)
			return false;
		else if(y<location.y || y>location.y+height)
			return false;
		return true;
	}

	/**
	 * Called on frame update
	 * @param delta - delay from last frame
	 */
	public void update(int delta){
		if(smoothMoving) {
			if(location.x < sLocation.x)
				if(location.x + sVelocity.x > sLocation.x)
					location.x = sLocation.x;
				else
					location.x += sVelocity.x;
			else if(location.x > sLocation.x)
				if(location.x - sVelocity.x < sLocation.x)
					location.x = sLocation.x;
				else
					location.x -= sVelocity.x;
			if(location.y < sLocation.y)
				if(location.y + sVelocity.y > sLocation.y)
					location.y = sLocation.y;
				else
					location.y += sVelocity.y;
			else if(location.y > sLocation.y)
				if(location.y - sVelocity.y < sLocation.y)
					location.y = sLocation.y;
				else
					location.y -= sVelocity.y;
		}
	}

	/**
	 * Draws the component with given graphics
	 * @param g - graphics to draw with
	 */
	public void draw(Graphics g, Color filter) {
		if(!visible)
			return;
	}
	
	public void draw3D(Graphics g) {
		g.pushTransform();
		g.translate(-GUI_DISTANCE, 0);
		draw(g, RED);
		g.popTransform();
		draw(g, null);
		g.pushTransform();
		g.translate(GUI_DISTANCE, 0);
		draw(g, CYAN);
		g.popTransform();
		g.pushTransform();
		g.translate(-GUI_DISTANCE, 0);
		draw(g, RED);
		g.popTransform();
		draw(g, null);
		g.pushTransform();
		g.translate(GUI_DISTANCE, 0);
		draw(g, CYAN);
		g.popTransform();
	}
}
