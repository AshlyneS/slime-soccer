package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.SlimeSoccer;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Vector2f;

/**
 * GameObject is the base class for all game objects
 * @author Seth
 */
public abstract class GameObject 
{
	/**
	 * Gravity factor for all GameObjects
	 */
	public static final float GRAVITY_FACTOR = 0.098f;

	/**
	 * Finals for X_Axis and Y_Axis
	 */
	public static final int X_AXIS = 0, Y_AXIS = 1;

	protected Vector2f location = new Vector2f(), velocity = new Vector2f();
	protected float rotation = 0f;
	protected float width,height;
	protected Polygon bounds;

	/**
	 * Create a new GameObject with given width and height
	 * @param width - width of object
	 * @param height - height of object
	 */
	public GameObject(float width, float height) {
		this.width = width;
		this.height = height;
		bounds = new Polygon();
		bounds.setClosed(true);
		bounds.addPoint(location.x,location.y);
		bounds.addPoint(location.x,location.y+height);
		bounds.addPoint(location.x+width,location.y+height);
		bounds.addPoint(location.x+width,location.y);
	}

	/**
	 * Sets the rotation of the object
	 * @param rotation - object rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * Gets the rotation of the object
	 * @return object rotation
	 */
	public float getRotation() {
		return rotation;
	}
	
	/**
	 * Set the size of the game object
	 * @param width - width of the object
	 * @param height - height of the object
	 */
	public void setSize(float width, float height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * Render the object with given graphics
	 * @param g - graphics to draw with
	 */
	public abstract void render(Graphics g);

	/**
	 * Called on frame udpate
	 * @param delta - delay from last frame
	 */
	public void update(int delta) {
		bounds.setLocation(location);
	}

	/**
	 * Get the polygon bounds of the game object
	 * @return object bounds
	 */
	public Polygon getBounds() {
		return bounds;
	}

	/**
	 * Called on collide with another GameObject
	 * @param a - GameObject collided with
	 * @param axis - axis on which it collided (call twice for both axis)
	 */
	public void onCollide(GameObject a, int axis){}

	/**
	 * Gets the location of the object
	 * @return object location
	 */
	public Vector2f getLocation() {
		return location;
	}

	/**
	 * Gets whether the object can move
	 * @return can move
	 */
	public boolean isSolid() {
		return true;
	}

	/**
	 * Gets whether the object should move when pushed
	 * @return
	 */
	public boolean isEnviormentControlled() {
		return false;
	}

	/**
	 * Gets the velocity of the object
	 * @return object velocity
	 */
	public Vector2f getVelocity() {
		return velocity;
	}

	/**
	 * Gets the width of the object
	 * @return width of the object
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Gets the height of the object
	 * @return object height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Update the position of the object
	 * @param objects - objects that CAN collide with this object
	 */
	public void updatePosition(GameObject[] objects) {
		if(!isSolid()) {
			if(SlimeSoccer.PIXEL_COLLISION)
				for(GameObject a: objects)
					if(a.bounds.intersects(bounds))
						onCollide(a,-1);
					else;
			else {
				GameObject x = updateX(objects), y = updateY(objects);
				if(x != null)
					onCollide(x,0);
				if(y != null)
					onCollide(y,1);
			}
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

	/**
	 * Checks if a point is within the bounds of the object
	 * \nNOTE: Override this if object does NOT have a rectangular bounds
	 * @param x - x point
	 * @param y - y point
	 * @return if point is within bounds
	 */
	public boolean contains(float x, float y) {
		if(x >= location.x && x <= location.x + width)
			if(y >= location.y && y <= location.y + height) 
				return true;
		return false;
	}

	/**
	 * Checks if a point is outside the screen
	 * @param x - x point
	 * @param y - y point
	 * @return point is outside the screen
	 */
	protected boolean outOfBounds(float x, float y) {
		return outOfBounds(x,y,true,true);
	}

	/**
	 * Checks if a point is outside the screen. allows choice of axis
	 * @param x - x point
	 * @param y - y point
	 * @param testX - test x axis
	 * @param testY - test y axis
	 * @return point is outside of screen
	 */
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
