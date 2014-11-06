package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Loading extends Scene {

	private Image background;
	private AngelCodeFont hiero;
	public Loading() {
		super();
		background = Textures.get("mainBackground");
		hiero = Fonts.get("hiero");
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		hiero.drawString(container.getWidth()/2-hiero.getWidth("Slime Soccer")/2,
				container.getHeight()/2-hiero.getHeight("Slime Soccer")/2, "Slime Soccer");
	}

	@Override
	public void update(GameContainer container, int i) {

	}

	@Override
	void load(HashMap<String, Object> params) {
		
	}

	@Override
	void unload(Scene scene) {

	}

}
