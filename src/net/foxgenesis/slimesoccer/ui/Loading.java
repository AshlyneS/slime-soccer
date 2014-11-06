package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.component.ProgressBar;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Loading extends Scene {

	private Image background,ball;
	private AngelCodeFont hiero;
	private ProgressBar bar;
	private int update = 0,update2 = 0,speed=1;
	public Loading() {
		super();
		background = Textures.get("mainBackground");
		ball = Textures.get("soccerball").getScaledCopy(50,50);
		hiero = Fonts.get("hiero");
		bar = new ProgressBar();
		bar.setVisible(true);
		bar.setAction(new Runnable() {
			@Override
			public void run() {
				bar.setVisible(false);
			}
		});
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		g.drawImage(ball,container.getWidth()/2 - ball.getWidth()/2
				- (float)(hiero.getHeight("Slime Soccer")*2 * Math.sin(0.05 * update2)),50);
		g.pushTransform();
		g.translate(0, -80);
		hiero.drawString(container.getWidth()/2-hiero.getWidth("Slime Soccer")/2,
				container.getHeight()/2-hiero.getHeight("Slime Soccer")/2, "Slime Soccer");
		if(bar.isVisible())
			bar.draw(g);
		else
			hiero.drawString(container.getWidth()/2-hiero.getWidth("PRESS SPACE")/2,container.getHeight()-100, "PRESS SPACE", update > 15/2?Color.red:Color.orange);
		g.popTransform();
	}

	@Override
	public void update(GameContainer gc, int i) {
		if(++update >= 15)
			update = 0;
		if((update2 += speed) >= 360)
			speed = -speed;
		bar.setSize(gc.getWidth()/3*2,20);
		bar.setLocation(gc.getWidth()/2-bar.getWidth()/2,gc.getHeight()-100);
		bar.update(i);
		if(bar.isVisible())
			bar.setValue(bar.getValue()+1);
		ball.rotate((float)(hiero.getHeight("Slime Soccer")/8 * Math.sin(0.05 * update2)));
	}

	@Override
	void load(HashMap<String, Object> params) {

	}

	@Override
	void unload(Scene scene) {

	}

}
