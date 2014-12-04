package net.foxgenesis.slimesoccer;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.io.KeyboardInput;
import net.foxgenesis.slimesoccer.objects.Bounds;
import net.foxgenesis.slimesoccer.ui.Loading;
import net.foxgenesis.slimesoccer.ui.MainMenu;
import net.foxgenesis.slimesoccer.ui.Scene;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

public class SlimeSoccer extends BasicGame
{
	private static int width, height;
	private static Input input;
	public static boolean PIXEL_COLLISION = false;
	private Music music;
	public static AppGameContainer appgc;

	/**
	 * Main method
	 * @param args - paramaters for program
	 */
	public static void main(String[] args) {
		try {
			appgc = new AppGameContainer(new SlimeSoccer("Slime Soccer"));
			appgc.setTargetFrameRate(60);
			appgc.setDisplayMode(640, 480, false);
			appgc.setUpdateOnlyWhenVisible(true);
			appgc.setIcons(new String[]{"textures/icon32.png","textures/icon24.png","textures/icon16.png"});
			appgc.start();
		} catch (SlickException ex) {
			Logger.getLogger(SlimeSoccer.class.getName()).log(Level.SEVERE, null, ex);
			System.exit(1);
		}
	}

	/**
	 * Gets the width of the window
	 * @return window width
	 */
	public static int getWidth() {
		return width;
	}

	/**
	 * Gets the height of the window
	 * @return window height
	 */
	public static int getHeight() {
		return height;
	}

	public static Input getInput() {
		return input;
	}

	public SlimeSoccer(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		music = new Music("music/cactus.ogg");
		System.out.println("Loading game...");
		SlimeSoccer.width = gc.getWidth();
		SlimeSoccer.height = gc.getHeight();
		SlimeSoccer.input = gc.getInput();
		Textures.init();
		Bounds.init();
		Fonts.init();
		System.out.println("Game loaded!");
		Scene.store("mainMenu", new MainMenu());
		Scene.setCurrentScene(new Loading(), null);
		music.loop();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		KeyboardInput.update();
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().update(gc, i);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setAntiAlias(false);
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().draw(gc, g);
	}
}

