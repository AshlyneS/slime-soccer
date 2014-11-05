package net.foxgenesis.slimesoccer;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.Scene;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class SlimeSoccer extends BasicGame
{
	public SlimeSoccer(String gamename) {
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Textures.init();
	}

	@Override
	public void update(GameContainer gc, int i) throws SlickException {
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().update(gc, i);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		if(Scene.getCurrentScene() != null)
			Scene.getCurrentScene().draw(gc, g);
	}

	public static void main(String[] args)
	{
		try
		{
			AppGameContainer appgc;
			appgc = new AppGameContainer(new SlimeSoccer("Simple Slick Game"));
			appgc.setDisplayMode(640, 480, false);
			appgc.start();
		}
		catch (SlickException ex)
		{
			Logger.getLogger(SlimeSoccer.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}

