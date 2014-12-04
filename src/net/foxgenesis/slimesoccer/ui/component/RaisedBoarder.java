package net.foxgenesis.slimesoccer.ui.component;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class RaisedBoarder {
	
	private Color top,left,right,bottom;
	private float x=-1,y=-1,width=-1,height=-1;
	private Component component;
	private boolean invert = false;
	
	public RaisedBoarder(Component component, Color top, Color bottom) {
		this(component, top,top,bottom,bottom);
	}
	
	public RaisedBoarder(Component component, Color top, Color left, Color right, Color bottom) {
		this.top = top;
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.component = component;
	}
	
	public void setTopColor(Color color) {
		this.top = color;
	}
	
	public Color getTopColor() {
		return top;
	}
	
	public void setInvertedColors(boolean state) {
		invert = state;
	}
	
	public boolean isInvertedColors() {
		return invert;
	}
	
	public void setLeftColor(Color color) {
		this.left = color;
	}
	
	public Color getLeftColor() {
		return left;
	}
	
	public void setRightColor(Color color) {
		this.right = color;
	}
	
	public Color getRightColor() {
		return right;
	}
	
	public void setBottomColor(Color color) {
		this.bottom = color;
	}
	
	public Color getBottomColor() {
		return bottom;
	}
	
	public void setFixedLocation(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setFixedSize(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
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
		g.drawLine(x+width, y-height, x+width, y+height); //right
		g.setColor(!invert?left:right);
		g.drawLine(x, y-height, x, y+height); //left
		g.setColor(!invert?top:bottom);
		g.drawLine(x-width, y, x+width, y); //top
	}
}
