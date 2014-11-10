package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.image.Textures;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Ball extends GameObject {

	private Image ball;
	
	public Ball() {
		super(20,20);
		ball = Textures.get("soccerball").getScaledCopy((int)getWidth(),(int)getHeight());
		location.x = SlimeSoccer.getWidth()/2;
		location.y = SlimeSoccer.getHeight()/2;
	}

	@Override
	public void render(Graphics g) {
		ball.draw(location.x, location.y);
	}

	@Override
	public void update(int delta) {
		
	}

	@Override
	public boolean isSolid() {
		return false;
	}
	
	@Override
	public boolean isEnviormentControlled() {
		return true;
	}
}
