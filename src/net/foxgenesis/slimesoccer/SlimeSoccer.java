package net.foxgenesis.slimesoccer;

import java.awt.DisplayMode;
import java.awt.GraphicsEnvironment;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.input.KeyboardInput;
import net.foxgenesis.slimesoccer.ui.Loading;
import net.foxgenesis.slimesoccer.ui.Scene;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlimeSoccer extends BasicGame
{
	public static void main(String[] args) {
		try {
			//create v-sync with monitor and start game
			int refresh = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getRefreshRate();
			AppGameContainer appgc = new AppGameContainer(new SlimeSoccer("Slime Soccer"));
			appgc.setDisplayMode(640, 480, false);
			if(refresh != DisplayMode.REFRESH_RATE_UNKNOWN)
				appgc.setTargetFrameRate(refresh-1);
			appgc.setUpdateOnlyWhenVisible(true);
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(SlimeSoccer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static int width, height;

	public SlimeSoccer(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		System.out.println("Loading game...");
		SlimeSoccer.width = gc.getWidth();
		SlimeSoccer.height = gc.getHeight();
		Textures.init();
		Fonts.init();
		System.out.println("Game loaded!");
		Scene.setCurrentScene(new Loading(), null);
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		KeyboardInput.update();
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().update(gc, i);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setAntiAlias(true);
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().draw(gc, g);
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
}

