package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.objects.Ball;
import net.foxgenesis.slimesoccer.objects.GameObject;
import net.foxgenesis.slimesoccer.objects.Goal;
import net.foxgenesis.slimesoccer.objects.Slime;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * SoccerGame is a scene that contains players and a soccerball
 * @author Seth
 */
public class SoccerGame extends Scene {

	private GameObject[] objects = new GameObject[5];
	public static final int PLAYER1 = 0,PLAYER2 = 1, BALL = 2, GOAL_LEFT = 3, GOAL_RIGHT = 4, SINGLE_PLAYER=0, DUEL=1, MULTIPLAYER=2;
	private Image background;
	
	public SoccerGame() {
		super();
<<<<<<< HEAD
		//test
		this.gameType = gameType;
=======
>>>>>>> origin/master
		objects[BALL] = new Ball();
		objects[GOAL_LEFT] = new Goal(GOAL_LEFT);
		objects[GOAL_RIGHT] = new Goal(GOAL_RIGHT);
		objects[GOAL_LEFT].getLocation().y = SlimeSoccer.getHeight()-objects[GOAL_LEFT].getHeight();
		objects[GOAL_RIGHT].getLocation().y = SlimeSoccer.getHeight()-objects[GOAL_RIGHT].getHeight();
		objects[GOAL_RIGHT].getLocation().x = SlimeSoccer.getWidth()-objects[GOAL_RIGHT].getWidth();
	}
	
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		for(GameObject a: objects)
			if(a != null)
				a.render(g);;
	}

	@Override
	public void update(GameContainer container, int i) {
		for(GameObject a:objects) {
			if(a != null) {
				a.updatePosition(a instanceof Ball?objects:new GameObject[]{objects[GOAL_LEFT],objects[GOAL_RIGHT]});
				a.update(i);
			}
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		objects[PLAYER1] = new Slime((Slime.Type)params.get("player1"), true);
		objects[PLAYER2] = new Slime((Slime.Type)params.get("player1"), (int)params.get("players") == DUEL, true);
		objects[PLAYER1].getLocation().x = objects[0].getLocation().y = objects[0].getWidth()*2;
		objects[PLAYER2].getLocation().x =  SlimeSoccer.getWidth()-objects[1].getWidth()*2;
		objects[PLAYER2].getLocation().y =  SlimeSoccer.getHeight()-objects[1].getHeight()*2;
		background = Textures.get((String) params.get("background"));
	}

	@Override
	void unload(Scene scene) {

	}
	
	public GameObject[] getObjects() {
		return objects;
	}

}
