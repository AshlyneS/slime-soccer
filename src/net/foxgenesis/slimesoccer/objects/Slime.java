package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.input.KeyboardInput;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Slime is the player object
 * @author Seth
 */
public class Slime extends GameObject {
	private final Image img, flipped;
	private static final float MAX_SPEED = 5f, SPEED = 0.3f, JUMP_VELOCITY = -5f;
	private final boolean controlled;
	private boolean canJump = true;
	private int direction = 0;
	
	/**
	 * Create a new Slime with a type and controlled parameters
	 * @param type -  type of slime to make
	 * @param controlled - is this player controlled
	 */
	public Slime(Type type, boolean controlled) {
		super(100, 100);
		img = Textures.get(type.img).getScaledCopy((int)getWidth(),(int)getHeight());
		this.controlled = controlled;
		this.flipped = img.getFlippedCopy(true, false);
	}

	@Override
	public void render(Graphics g) {
		switch(direction) {
		case 0:
			img.draw(location.x,location.y);
			break;
		case 1:
			flipped.draw(location.x,location.y);
			break;
		}
	}

	@Override
	public void update(int delta) {
		if(controlled) {
			if(KeyboardInput.keys[Keyboard.KEY_D]) {
				velocity.x = velocity.x + SPEED < MAX_SPEED?velocity.x+=SPEED:MAX_SPEED;
				direction = 1;
			}
			else if(KeyboardInput.keys[Keyboard.KEY_A]) {
				velocity.x = velocity.x - SPEED > -MAX_SPEED?velocity.x-=SPEED:-MAX_SPEED;
				direction = 0;
			}
			else
				if(canJump)
					if(velocity.x > 0)
						if(velocity.x - SPEED/2 < 0)
							velocity.x -= velocity.x;
						else
							velocity.x-=SPEED/2;
					else if(velocity.x < 0)
						if(velocity.x + SPEED/2 > 0)
							velocity.x -= velocity.x;
						else
							velocity.x+=SPEED/2;
			if(KeyboardInput.keys[Keyboard.KEY_SPACE] && canJump) {
				velocity.y = JUMP_VELOCITY;
				canJump = false;
			}
			if(outOfBounds(location.x,location.y + velocity.y,false,true))
				canJump = true;
		}
	}

	@Override
	public boolean isSolid() {
		return false;
	}

	/**
	 * Type contains the list of Slime types
	 * @author Seth
	 */
	public static enum Type {
		DEFAULT("svetty");
		
		private String img;
		
		/**
		 * Create a new Type with given Image path
		 * @param img - Image path
		 */
		Type(String img) {
			this.img = img;
		}
	}

}
