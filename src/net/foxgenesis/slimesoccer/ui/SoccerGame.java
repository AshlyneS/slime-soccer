package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.GameObject;
import net.foxgenesis.slimesoccer.objects.Slime;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * SoccerGame is a scene that contains players and a soccerball
 * @author Seth
 */
public class SoccerGame extends Scene {

	private GameObject[] objects = new GameObject[3];
	public static final int PLAYER1 = 0,PLAYER2 = 1, BALL = 2;

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
				a.updatePosition(a instanceof Ball?objects:new GameObject[]{});
				a.update(i);
			}
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		objects[0] = new Slime(Slime.Type.valueOf(params.get("player1").toString().toUpperCase()), true);
		objects[1] = new Slime(Slime.Type.valueOf(params.get("player2").toString().toUpperCase()), true, true);
		objects[2] = new Ball();
		objects[0].getLocation().x = objects[0].getLocation().y = objects[0].getWidth()*2;
		objects[1].getLocation().x =  SlimeSoccer.getWidth()-objects[1].getWidth()*2;
		objects[1].getLocation().y =  SlimeSoccer.getHeight()-objects[1].getHeight()*2;
	}

	@Override
	void unload(Scene scene) {

	}
	
	public GameObject[] getObjects() {
		return objects;
	}

}
