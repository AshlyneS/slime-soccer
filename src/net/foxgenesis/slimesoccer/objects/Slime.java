package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.input.KeyboardInput;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Slime extends GameObject {
	private final Image img;
	private static final float MAX_SPEED = 2f, SPEED = 0.3f;
	private final boolean controlled;
	public Slime(Type type, boolean controlled) {
		super(100, 100);
		img = Textures.get("svetty").getScaledCopy((int)getWidth(),(int)getHeight());
		this.controlled = controlled;
	}

	@Override
	public void render(Graphics g) {
		img.draw(location.x,location.y);
	}

	@Override
	public void update(int delta) {
		if(controlled) {
			if(KeyboardInput.keys[Keyboard.KEY_D])
				velocity.x = velocity.x + SPEED < MAX_SPEED?velocity.x+=SPEED:MAX_SPEED;
			else if(KeyboardInput.keys[Keyboard.KEY_A])
				velocity.x = velocity.x - SPEED > -MAX_SPEED?velocity.x-=SPEED:MAX_SPEED;
			else
				velocity.x = 0f;
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
