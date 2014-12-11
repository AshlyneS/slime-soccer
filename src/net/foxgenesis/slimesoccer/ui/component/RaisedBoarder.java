package net.foxgenesis.slimesoccer.ui.component;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * RaisedBoarder is a rectangle created to look like it is raised
 * @author Seth
 */
public class RaisedBoarder {
	
	private Color top,left,right,bottom;
	private float x=-1,y=-1,width=-1,height=-1;
	private Component component;
	private boolean invert = false;
	protected static Color blackFade = new Color(0f,0f,0f,0.5f), whiteFade = new Color(1f,1f,1f,0.5f);
	
	/**
	 * Create a new raisedboarder for a given component
	 * @param component - component to use
	 */
	public RaisedBoarder(Component component) {
		this(component, blackFade, whiteFade);
	}
	
	/**
	 * Create a new raisedboarder for a given component. Will also set the top and bottom color
	 * @param component - component to use
	 * @param top - top color
	 * @param bottom - bottom color
	 */
	public RaisedBoarder(Component component, Color top, Color bottom) {
		this(component, top,top,bottom,bottom);
	}
	
	/**
	 * Create a new raisedboarer for a given component. Will also set the top, left, right and bottom colors
	 * @param component - component to use
	 * @param top - top color
	 * @param left - left color
	 * @param right - right color
	 * @param bottom - bottom color
	 */
	public RaisedBoarder(Component component, Color top, Color left, Color right, Color bottom) {
		this.top = top;
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.component = component;
	}
	
	/**
	 * Set the top color of the boarder
	 * @param color - top color
	 */
	public void setTopColor(Color color) {
		this.top = color;
	}
	
	/**
	 * Get the top color of the boarder
	 * @return top color
	 */
	public Color getTopColor() {
		return top;
	}
	
	/**
	 * Sets whether the boarder should use inverted color placement
	 * @param state - inverted colors
	 */
	public void setInvertedColors(boolean state) {
		invert = state;
	}
	
	/**
	 * Gets whether the boarder will be inverted colors
	 * @return boarder will use inverted colors
	 */
	public boolean isInvertedColors() {
		return invert;
	}
	
	/**
	 * Set the left color of the boarder
	 * @param color - left color
	 */
	public void setLeftColor(Color color) {
		this.left = color;
	}
	
	/**
	 * Get the left color of the boarder
	 * @return left color
	 */
	public Color getLeftColor() {
		return left;
	}
	
	/**
	 * Set the right color of the boarder
	 * @param color - right color
	 */
	public void setRightColor(Color color) {
		this.right = color;
	}
	
	/**
	 * Get the right color of the boarder
	 * @return right color
	 */
	public Color getRightColor() {
		return right;
	}
	
	/**
	 * Set the bottom color of the boarder
	 * @param color - bottom color
	 */
	public void setBottomColor(Color color) {
		this.bottom = color;
	}
	
	/**
	 * Get the bottom color of the boarder
	 * @return bottom color
	 */
	public Color getBottomColor() {
		return bottom;
	}
	
	/**
	 * Manually set a fixed location for the boarder
	 * @param x - x location
	 * @param y - y location
	 */
	public void setFixedLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set a fixed size to use
	 * @param width - boarder width
	 * @param height - boarder height
	 */
	public void setFixedSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Draw the boarder
	 * @param g - graphics to use
	 */
	public void draw(Graphics g)  {
		float x,y,width,height;
		if(this.x != -1)
			x = this.x;
		else
			x = component.getLocation().x;
		if(this.y != -1)
			y = this.y;
		else
			y = component.getLocation().y;
		if(this.width != -1)
			width = this.width;
		else
			width = component.width;
		if(this.height != -1)
			height = this.height;
		else
			height = component.height;
		g.setColor(!invert?bottom:top);
		g.drawLine(x, y+height, x+width, y+height); //bottom
		g.setColor(!invert?right:left);
		g.drawLine(x+width, y, x+width, y+height); //right
		g.setColor(!invert?left:right);
		g.drawLine(x, y, x, y+height); //left
		g.setColor(!invert?top:bottom);
		g.drawLine(x, y, x+width, y); //top
	}
}
