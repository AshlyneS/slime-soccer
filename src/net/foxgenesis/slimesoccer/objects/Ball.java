package net.foxgenesis.slimesoccer.objects;

import java.util.Timer;
import java.util.TimerTask;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.image.Textures;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Ball is the ball
 * @author Seth
 */
public class Ball extends GameObject {

	private Image ball;
	private final float FRICTION_RESISTANCE_FACTOR = 6;
	private final boolean MOTION_BLUR = true;
	private final float radius;
	private final Timer timer;
	private boolean paused = false;
	/**
	 * Create a new ball
	 */
	public Ball() {
		this(20);
	}

	/**
	 * Create a new ball with a given radius
	 * @param radius
	 */
	public Ball(float radius) {
		super(radius,radius);
		this.radius = radius;
		timer = new Timer();
		ball = Textures.get("soccerball").getScaledCopy((int)width,(int)height);
		location.x = SlimeSoccer.getWidth()/2-radius/2;
		location.y = SlimeSoccer.getHeight()/2-radius/2;
		velocity.x = velocity.y = 0f;
	}

	@Override
	public void render(Graphics g) {
		if(MOTION_BLUR) {
			ball.draw(location.x-(velocity.x*3), location.y-(velocity.y*3), new Color(1f,1f,1f,0.3f));
			ball.draw(location.x-(velocity.x*2), location.y-(velocity.y*2), new Color(1f,1f,1f,0.4f));
			ball.draw(location.x-(velocity.x), location.y-(velocity.y*2), new Color(1f,1f,1f,0.5f));
		}
		ball.draw(location.x, location.y);
	}

	@Override
	public void update(int delta) {
		super.update(delta);
		ball.rotate(velocity.x/2 * width);
		if(location.y + height >= SlimeSoccer.getHeight())
			if(velocity.x > 0)
				if(velocity.x - GameObject.GRAVITY_FACTOR/FRICTION_RESISTANCE_FACTOR < 0)
					velocity.x -= velocity.x;
				else
					velocity.x-=GameObject.GRAVITY_FACTOR/FRICTION_RESISTANCE_FACTOR;
			else if(velocity.x < 0)
				if(velocity.x + GameObject.GRAVITY_FACTOR/FRICTION_RESISTANCE_FACTOR > 0)
					velocity.x -= velocity.x;
				else
					velocity.x+=GameObject.GRAVITY_FACTOR/FRICTION_RESISTANCE_FACTOR;
	}

	@Override
	public boolean isSolid() {
		return paused;
	}

	@Override
	public boolean isEnviormentControlled() {
		return true;
	}

	@Override
	public void onCollide(GameObject a, int axis) {
		if(a instanceof Slime) {
			if(axis == -1){
				velocity.x = a.getVelocity().x*2;
				velocity.y = a.getVelocity().y*2;
			}
			else if(axis == GameObject.X_AXIS)
				velocity.x = a.getVelocity().x*2;
			else if(axis == GameObject.Y_AXIS)
				velocity.y = a.getVelocity().y*2;
		}
		else if(a instanceof Goal) {
			final Goal g = (Goal)a;
			if(axis == GameObject.X_AXIS)
				if(g.contains(location.x, location.y,radius)) {
					location.x = SlimeSoccer.getWidth()/2-radius/2;
					location.y = SlimeSoccer.getHeight()/2-radius/2;
					velocity.x = velocity.y = 0f;
					Slime.paused = paused = true;
					timer.schedule(new TimerTask() {
						@Override
						public void run() {
							Slime.paused = paused = false;
						}
					}, 2000);
					g.addGoal();
				}
				else;
			else {
				velocity.y = -velocity.y/2;
			}
		}
	}

	@Override
	public boolean contains(float x, float y) {
		return Math.pow(x - location.x+width/2,2) + Math.pow(y - location.y+height/2,2)
				<= Math.pow(getWidth()/2, 2);
	}
}
