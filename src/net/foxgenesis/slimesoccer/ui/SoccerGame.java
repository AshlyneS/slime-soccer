package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.io.client.ChatClient;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.GameObject;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.objects.Slime.Type;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * SoccerGame is a scene that contains players and a soccerball
 * @author Seth
 */
public class SoccerGame extends Scene {

	private GameObject[] objects = new GameObject[3];
	private ChatClient client;
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
		if(params.keySet().contains("multiplayer"))
			client = (ChatClient) params.get("multiplayer");
		if(params.keySet().contains("player1") && params.keySet().contains("player1 control") && params.keySet().contains("player1 keys"))
			if(params.get("player1") instanceof Type)
				objects[0] = new Slime((Type) params.get("player1"), (boolean)params.get("player1 control"), (boolean)params.get("player1 keys"));
			else
				throw new RuntimeException("argument 'player1' must be an instance of Slime.Type!");
		if(params.keySet().contains("player2") && params.keySet().contains("player2 control") && params.keySet().contains("player2 keys"))
			if(params.get("player2") instanceof Type)
				objects[1] = new Slime((Type) params.get("player2"), (boolean)params.get("player2 control"), (boolean)params.get("player2 keys"));
			else
				throw new RuntimeException("argument 'player2' must be an instance of Slime.Type!");
		if(objects[2] != null)
			objects[2] = new Ball();
		if(objects[0] != null)
			objects[0].getLocation().x = objects[0].getLocation().y = objects[0].getWidth()*2;
		if(objects[1] != null) {
			objects[1].getLocation().x =  SlimeSoccer.getWidth()-objects[1].getWidth()*2;
			objects[1].getLocation().y =  SlimeSoccer.getHeight()-objects[1].getHeight()*2;
		}
	}

	@Override
	void unload(Scene scene) {

	}

	/**
	 * Get the objects contained in the current scene
	 * @return game objects
	 */
	public GameObject[] getObjects() {
		return objects;
	}

	/**
	 * Returns if the game is a multiplayer match
	 * @return game is multiplayer
	 */
	public boolean isMultiplayer() {
		return client != null;
	}

}
