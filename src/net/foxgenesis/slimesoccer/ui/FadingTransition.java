package net.foxgenesis.slimesoccer.ui;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class FadingTransition {
	private Image image;
	private float opacity = 1f;
	private final float SPEED;
	
	public FadingTransition(Image image) {
		this(image,0.1f);
	}
	
	public FadingTransition(Image image, float speed) {
		this.image = image;
		SPEED = speed;
	}
	
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(image,0,0,container.getWidth(),container.getHeight(),0,0,image.getWidth(),image.getHeight(), new Color(1f,1f,1f,opacity));
	}
	
	public void update(int delta) {
		if(opacity > 0f) 
			opacity-=SPEED;
	}
}
