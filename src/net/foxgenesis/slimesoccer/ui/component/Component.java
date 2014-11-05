package net.foxgenesis.slimesoccer.ui.component;

import org.newdawn.slick.Graphics;

public class Component {
	private int x,y,width,height;
	private boolean visible=true;

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean state) {
		visible = state;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public boolean contains(int x, int y) {
		if(x<this.x || x>this.x+width)
			return false;
		else if(y<this.y || y>this.y+height)
			return false;
		return true;
	}
	
	public void update(int delta){}
	public void draw(Graphics g) {
		if(!visible)
			return;
	}
}
