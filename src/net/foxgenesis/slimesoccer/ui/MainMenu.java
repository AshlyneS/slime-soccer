package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.component.Button;
import net.foxgenesis.slimesoccer.ui.component.Button.Action;
import net.foxgenesis.slimesoccer.ui.component.PopUp;
import net.foxgenesis.slimesoccer.util.TextBounce;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
/**
 * @author Seth
 * Main menu for the game
 */
public class MainMenu extends Scene{
	private Image background, image;
	private FadingTransition trans;
	private Button duelPlayer, singlePlayer, multiPlayer, info;
	private TextBounce title;
	private int update = 0;
	private boolean getImage = false;
	private int selection = -1;
	private PopUp infoPop;

	/**
	 * Create the main menu
	 */
	public MainMenu() {
		super();
		background = Textures.get("gradientBackground");
		duelPlayer = createButton("Duel");
		duelPlayer.setToolTipText("Play a game against a human");
		duelPlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		duelPlayer.setLocation(SlimeSoccer.getWidth()/4, SlimeSoccer.getHeight()/4);
		duelPlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("buttonBlack")}, new int[]{300,300}));
		duelPlayer.setAction(new Action(){
			@Override
			public void act(int button, int x, int y, int clickCount) {
				if(!infoPop.isVisible()) {
					selection = SoccerGame.DUEL;
					getImage = true;
				}
			}
		});

		singlePlayer = createButton("Single Player", false);
		singlePlayer.setToolTipText("Play a game against a computer");
		singlePlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		singlePlayer.setEnabled(false);
		singlePlayer.getLocation().x = SlimeSoccer.getWidth();
		singlePlayer.setLocation(SlimeSoccer.getWidth()/4*2, SlimeSoccer.getHeight()/4*2);
		singlePlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("missing")}, new int[]{300,300}));
		singlePlayer.setAction(new Action(){
			@Override
			public void act(int button, int x, int y, int clickCount) {
				if(!infoPop.isVisible()) {
					selection = SoccerGame.SINGLE_PLAYER;
					getImage = true;
				}
			}
		});

		multiPlayer = createButton("Multiplayer", false);
		multiPlayer.setToolTipText("Play a game against a human over the internet");
		multiPlayer.getLocation().y = SlimeSoccer.getHeight()/4;
		multiPlayer.setEnabled(false);
		multiPlayer.setLocation(SlimeSoccer.getWidth()/4*3, SlimeSoccer.getHeight()/4*3);
		multiPlayer.setAnimation(new Animation(new Image[]{Textures.get("mainBackground"),Textures.get("missing")}, new int[]{300,300}));
		multiPlayer.setAction(new Action(){
			@Override
			public void act(int button, int x, int y, int clickCount) {
				if(!infoPop.isVisible()) {
					selection = SoccerGame.MULTIPLAYER;
					getImage = true;
				}
			}
		});

		
		infoPop = new PopUp("Welcome to Slime Soccer!\nCurrently only Duel gamemode is available.", PopUp.Type.DIALOG);
		infoPop.setLocation(SlimeSoccer.getWidth()/2-infoPop.getWidth()/2,SlimeSoccer.getHeight()/2-infoPop.getHeight()/2);
		infoPop.listen(SlimeSoccer.getInput());

		info = createButton("i");
		info.setToolTipText("Information");
		info.setFont(null);
		info.setForeground(Color.black);
		info.getLocation().x = SlimeSoccer.getWidth();
		info.setLocation(20, SlimeSoccer.getHeight()-15);
		info.setAction(new Action(){
			@Override
			public void act(int button, int x, int y, int clickCount) {
				if(!infoPop.isVisible()) {
					infoPop.setVisible(true);
				}
			}
		});
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
		title.render(g, Color.white);
		if(infoPop.getLocation().y != SlimeSoccer.getHeight()+30)
			infoPop.draw(g);
		if(getImage) {
			try {
				image = new Image(container.getWidth(),container.getHeight());
				g.copyArea(image, 0, 0);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update(GameContainer container, int i) {
		if(++update == 90)
			update = 0;
		trans.update(i);
		infoPop.update(i);
		duelPlayer.update(i);
		singlePlayer.update(i);
		multiPlayer.update(i);
		info.update(i);
		title.update(i);
		if(image != null && selection != -1) {
			HashMap<String, Object> params = new HashMap<>();
			params.put("image",image);
			Scene.setCurrentScene(new CharacterSelection(selection), params);
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		if(params.containsKey("image"))
			trans = new FadingTransition((Image) params.get("image"));
		infoPop.listen(SlimeSoccer.getInput());
		info.listen(SlimeSoccer.getInput());
		singlePlayer.listen(SlimeSoccer.getInput());
		multiPlayer.listen(SlimeSoccer.getInput());
		duelPlayer.listen(SlimeSoccer.getInput());
	}

	@Override
	void unload(Scene scene) {
		singlePlayer.mute(SlimeSoccer.getInput());
		multiPlayer.mute(SlimeSoccer.getInput());
		duelPlayer.mute(SlimeSoccer.getInput());
		infoPop.mute(SlimeSoccer.getInput());
		info.mute(SlimeSoccer.getInput());
		image = null;
		getImage = false;
	}

	private Button createButton(String title) {
		return createButton(title,true);
	}
	
	private Button createButton(String title, final boolean enabled) {
		Button b = new Button(title){
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
				if(!enabled) {
					float f = g.getLineWidth();
					g.setLineWidth(2f);
					g.setColor(Color.red);
					g.drawLine(getLocation().x-width/2, getLocation().y-height/2,getLocation().x+width/2, getLocation().y+height/2);
					g.drawLine(getLocation().x-width/2, getLocation().y+height/2,getLocation().x+width/2, getLocation().y-height/2);
					g.setColor(Color.white);
					g.setLineWidth(f);
				}
			}
		};
		b.setRounded(true);
		b.setDrawHoverScreen(false);
		b.setFont(Fonts.get("hiero"));
		b.setSmoothMoving(true);
		b.setForeground(Color.white);
		return b;
	}

}
