package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.GameObject;
import net.foxgenesis.slimesoccer.objects.Slime;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class TestGame extends Scene {

	private GameObject[] objects = new GameObject[3];

	@Override
	public void draw(GameContainer container, Graphics g) {
		g.setColor(Color.cyan);
		g.fillRect(0, 0, SlimeSoccer.getWidth(), SlimeSoccer.getHeight());
		for(GameObject a: objects)
			if(a != null)
				a.render(g);;
	}

	@Override
	public void update(GameContainer container, int i) {
		for(GameObject a:objects) {
			if(a != null) {
				a.updatePosition(objects);
				a.update(i);
			}
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		objects[0] = new Slime(Slime.Type.valueOf((String) params.get("player1")), true);
		objects[1] = new Slime(Slime.Type.valueOf((String) params.get("player2")), false);
		objects[2] = new Ball();
		objects[1].getLocation().x = SlimeSoccer.getWidth() - objects[1].getWidth()-10;
		objects[0].getLocation().x = objects[0].getLocation().y = objects[0].getWidth()*2;
	}

	@Override
	void unload(Scene scene) {

	}

}
