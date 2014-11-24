package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.component.Button;
import net.foxgenesis.slimesoccer.util.TextBounce;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
/**
 * @author Seth
 * Main menu for the game
 */
public class MainMenu extends Scene{
	private Image background;
	private FadingTransition trans;
	private Button duelPlayer, singlePlayer, multiPlayer, info;
	private TextBounce title;
	private int update = 0;

	/**
	 * Create the main menu
	 */
	public MainMenu() {
		super();
		background = Textures.get("gradientBackground");
		duelPlayer = createButton("Duel");
		duelPlayer.listen(SlimeSoccer.getInput());
		duelPlayer.setToolTipText("Play a game against a human");
		duelPlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		duelPlayer.setRounded(true);
		duelPlayer.setDrawHoverScreen(false);
		duelPlayer.setFont(Fonts.get("hiero"));
		duelPlayer.setLocation(SlimeSoccer.getWidth()/4, SlimeSoccer.getHeight()/4);
		duelPlayer.setForeground(Color.white);
		duelPlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("buttonBlack")}, new int[]{300,300}));
		duelPlayer.setAction(new Button.Action(){
			@Override
			public void act(Input input) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("player1", Slime.Type.DEFAULT);
				params.put("player2", Slime.Type.DEFAULT);
				params.put("players", SoccerGame.DUEL);
				params.put("background","grassField");
				Scene.setCurrentScene(new SoccerGame(), params);
			}
		});

		singlePlayer = createButton("Single Player");
		singlePlayer.listen(SlimeSoccer.getInput());
		singlePlayer.setToolTipText("Play a game against a computer");
		singlePlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		singlePlayer.getLocation().x = SlimeSoccer.getWidth();
		singlePlayer.setRounded(true);
		singlePlayer.setDrawHoverScreen(false);
		singlePlayer.setFont(Fonts.get("hiero"));
		singlePlayer.setLocation(SlimeSoccer.getWidth()/4*2, SlimeSoccer.getHeight()/4*2);
		singlePlayer.setForeground(Color.white);
		singlePlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("missing")}, new int[]{300,300}));
		singlePlayer.setAction(new Button.Action(){
			@Override
			public void act(Input input) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("player1", Slime.Type.DEFAULT);
				params.put("player2", Slime.Type.DEFAULT);
				params.put("players", SoccerGame.DUEL);
				params.put("background","grassField");
				Scene.setCurrentScene(new SoccerGame(), params);
			}
		});

		multiPlayer = createButton("Multiplayer");
		multiPlayer.listen(SlimeSoccer.getInput());
		multiPlayer.setToolTipText("Play a game against a human over the internet");
		multiPlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		multiPlayer.setRounded(true);
		multiPlayer.setFont(Fonts.get("hiero"));
		multiPlayer.setLocation(SlimeSoccer.getWidth()/4*3, SlimeSoccer.getHeight()/4*3);
		multiPlayer.setForeground(Color.white);
		multiPlayer.setDrawHoverScreen(false);
		multiPlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("missing")}, new int[]{300,300}));
		multiPlayer.setAction(new Button.Action(){
			@Override
			public void act(Input input) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("player1", Slime.Type.DEFAULT);
				params.put("player2", Slime.Type.DEFAULT);
				params.put("players", SoccerGame.DUEL);
				params.put("background","grassField");
				Scene.setCurrentScene(new SoccerGame(), params);
			}
		});

		info = createButton("i");
		info.listen(SlimeSoccer.getInput());
		info.setToolTipText("Information");
		info.setRounded(true);
		info.setSmoothMoving(true);
		info.setForeground(Color.black);
		info.getLocation().x = SlimeSoccer.getWidth();
		info.setLocation(20, SlimeSoccer.getHeight()-15);

		try {
			title = new TextBounce("Slime Soccer", SlimeSoccer.getWidth()/2-Fonts.get("hiero").getWidth("Slime Soccer")/2-20, 50, Fonts.get("hiero"), 0.09f, 2);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		trans.draw(container, g);
		duelPlayer.draw(g);
		singlePlayer.draw(g);
		multiPlayer.draw(g);
		info.draw(g);
		title.render(g);
	}

	@Override
	public void update(GameContainer container, int i) {
		if(++update == 90)
			update = 0;
		trans.update(i);
		duelPlayer.update(i);
		singlePlayer.update(i);
		multiPlayer.update(i);
		info.update(i);
		title.update(i);
	}

	@Override
	void load(HashMap<String, Object> params) {
		if(params.containsKey("image"))
			trans = new FadingTransition((Image) params.get("image"));
	}

	@Override
	void unload(Scene scene) {

	}
	
	private Button createButton(String title) {
		return new Button(title){
			@Override
			public void draw(Graphics g) {
				float exp = (float)Math.sin(Math.toRadians(update));
				if(cursorEntered) {
					float f = g.getLineWidth();
					g.setLineWidth(2f);
					g.setColor(new Color(1f,1f,1f,1f-exp));
					g.drawRoundRect(getLocation().x-width/2-(exp*5), getLocation().y-height/2-(exp*5), width+(exp*10), height+(exp*10), isRounded()?getRoundedArc():0);
					g.setColor(new Color(0.9f,0.9f,0.9f,1f-exp));
					g.drawRoundRect(getLocation().x-width/2-(exp*4), getLocation().y-height/2-(exp*4), width+(exp*8), height+(exp*8), isRounded()?getRoundedArc():0);
					g.setColor(new Color(0.8f,0.8f,0.8f,1f-exp));
					g.drawRoundRect(getLocation().x-width/2-(exp*3), getLocation().y-height/2-(exp*3), width+(exp*6), height+(exp*6), isRounded()?getRoundedArc():0);
					g.setLineWidth(f);
					g.setColor(Color.white);
				}
				super.draw(g);
			}
		};
	}

}
