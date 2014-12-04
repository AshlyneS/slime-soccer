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

	private GameObject[] objects;
	public static final int PLAYER1 = 0,PLAYER2 = 1, BALL = 2, GOAL_LEFT = 3, GOAL_RIGHT = 4, SINGLE_PLAYER=0, DUEL=1, MULTIPLAYER=2;
	private Image background;
	private final int gameType;
	private Thread collThread;

	public SoccerGame(int gameType) {
		super();
		//test
		this.gameType = gameType;
		collThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while(true) {
					for(GameObject a:objects)
						if(a != null) {
							a.update(0);
							a.updatePosition(a instanceof Ball?objects:new GameObject[]{objects[GOAL_LEFT],objects[GOAL_RIGHT]});
						}
					try {
						Thread.sleep(1000/60); //update 60 times per second
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		},"Collision Thread");
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
		
	}

	public int getGameType() {
		return gameType;
	}

	@Override
	void load(HashMap<String, Object> params) {
		//TODO change map objects based on params
		background = Textures.get((String) params.get("background"));
		objects = new GameObject[5];
		objects[BALL] = new Ball();
		objects[PLAYER1] = new Slime((Slime.Type)params.get("player1"), true);
		objects[PLAYER2] = new Slime((Slime.Type)params.get("player2"), gameType == DUEL, true);
		objects[GOAL_LEFT] = new Goal(GOAL_LEFT, (Slime)objects[PLAYER2]);
		objects[GOAL_RIGHT] = new Goal(GOAL_RIGHT, (Slime)objects[PLAYER1]);
		objects[GOAL_LEFT].getLocation().y = SlimeSoccer.getHeight()-objects[GOAL_LEFT].getHeight();
		objects[GOAL_RIGHT].getLocation().y = SlimeSoccer.getHeight()-objects[GOAL_RIGHT].getHeight();
		objects[GOAL_RIGHT].getLocation().x = SlimeSoccer.getWidth()-objects[GOAL_RIGHT].getWidth();
		objects[PLAYER1].getLocation().x = objects[0].getLocation().y = objects[0].getWidth()*2;
		objects[PLAYER2].getLocation().x =  SlimeSoccer.getWidth()-objects[1].getWidth()*2;
		objects[PLAYER2].getLocation().y =  SlimeSoccer.getHeight()-objects[1].getHeight()*2;
		collThread.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	void unload(Scene scene) {
		collThread.stop();
	}

	public GameObject[] getObjects() {
		return objects;
	}

}
