package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.component.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class MainMenu extends Scene{
	private Image background;
	private Button singlePlayer;
	public MainMenu() {
		super();
		background = Textures.get("mainBackground");
		singlePlayer = new Button("Single Player");
		singlePlayer.listen(SlimeSoccer.getInput());
		singlePlayer.setToolTipText("Play a game against a computer");
		singlePlayer.getLocation().y = SlimeSoccer.getHeight()/3;
		singlePlayer.setRounded(true);
		singlePlayer.setFont(Fonts.get("hiero"));
		singlePlayer.setLocation(SlimeSoccer.getWidth()/2, SlimeSoccer.getHeight()/3);
		singlePlayer.setForeground(Color.white);
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		singlePlayer.draw(g);
	}

	@Override
	public void update(GameContainer container, int i) {
		singlePlayer.update(i);
	}

	@Override
	void load(HashMap<String, Object> params) {
		
	}

	@Override
	void unload(Scene scene) {
		
	}

}
