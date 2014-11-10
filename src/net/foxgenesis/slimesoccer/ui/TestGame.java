package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.GameObject;
import net.foxgenesis.slimesoccer.objects.Slime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class TestGame extends Scene {

	private GameObject[] objects = new GameObject[3];

	@Override
	public void draw(GameContainer container, Graphics g) {
		for(GameObject a: objects)
			a.render(g);;
	}

	@Override
	public void update(GameContainer container, int i) {
		for(GameObject a:objects) {
			a.update(i);
			a.updatePosition(objects);
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		objects[0] = new Slime(Slime.Type.valueOf((String) params.get("player1")));
		objects[1] = new Slime(Slime.Type.valueOf((String) params.get("player2")));
		objects[2] = new Ball();
	}

	@Override
	void unload(Scene scene) {

	}

}
