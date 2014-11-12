package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.input.KeyboardInput;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Slime extends GameObject {
	private final Image img, flipped;
	private static final float MAX_SPEED = 5f, SPEED = 0.3f, JUMP_VELOCITY = -5f;
	private final boolean controlled;
	private boolean canJump = true;
	private int direction = 0;
	public Slime(Type type, boolean controlled) {
		super(100, 100);
		img = Textures.get("svetty").getScaledCopy((int)getWidth(),(int)getHeight());
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

	public static enum Type {
		DEFAULT
	}

}
