package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.SlimeSoccer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class GameObject 
{
	public static final float GRAVITY_FACTOR = 0.198f;
	public static final int X_AXIS = 0, Y_AXIS = 1;
	protected Vector2f location = new Vector2f(), velocity = new Vector2f();
	protected float width,height;

	public GameObject(float width, float height) {
		this.width = width;
		this.height = height;
	}

	public abstract void render(Graphics g);
	public abstract void update(int delta);
	public void onCollide(GameObject a, int axis){}

	public Vector2f getLocation() {
		return location;
	}

	public boolean isSolid() {
		return true;
	}

	public boolean isEnviormentControlled() {
		return false;
	}

	public Vector2f getVelocity() {
		return velocity;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public void updatePosition(GameObject[] objects) {
		if(!isSolid()) {
			GameObject x = updateX(objects), y = updateY(objects);
			if(x != null)
				onCollide(x,0);
			if(y != null)
				onCollide(y,1);
		}
	}

	private GameObject updateX(GameObject[] objects) {
		for(GameObject a: objects)
			if(a != null && a != this)
				if(a.contains(location.x + velocity.x, location.y))
					return a;
		if(!outOfBounds(location.x + velocity.x,location.y,true,false))
			location.x += velocity.x;
		else
			velocity.x = isEnviormentControlled()?-velocity.x/2:0f;
		return null;
	}

	private GameObject updateY(GameObject[] objects) {
		for(GameObject a: objects)
			if(a != null && a != this)
				if(a.contains(location.x, location.y + velocity.y))
					return a;
		if(!outOfBounds(location.x,location.y + velocity.y)) {
			velocity.y += GRAVITY_FACTOR;
			location.y += velocity.y;
		}
		else
			velocity.y = isEnviormentControlled()?-velocity.y/2:0f;
		return null;
	}

	public boolean contains(float x, float y) {
		if(x >= location.x && x <= location.x + width)
			if(y >= location.y && y <= location.y + height) 
				return true;
		return false;
	}

	protected boolean outOfBounds(float x, float y) {
		return outOfBounds(x,y,true,true);
	}

	protected boolean outOfBounds(float x, float y, boolean testX, boolean testY) {
		if(testX)
			if(x < 0 || x+width > SlimeSoccer.getWidth())
				return true;
		if(testY)
			if(y-height/2 < 0 || y+height > SlimeSoccer.getHeight())
				return true;
		return false;
	}
}
