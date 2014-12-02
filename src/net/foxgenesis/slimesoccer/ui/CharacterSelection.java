package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.objects.Slime.Type;
import net.foxgenesis.slimesoccer.ui.component.Button;
import net.foxgenesis.slimesoccer.ui.component.Button.Action;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class CharacterSelection extends Scene {
	private Type t1,t2;
	private Image background, image;
	private boolean getImage = false;
	private final int gameType;
	private int selecting = 0,update=0;
	private final Font font;
	private FadingTransition trans;
	private Button[] buttons;
	private Button go;
	
	public CharacterSelection(int gameType) {
		super();
		background = Textures.get("mainBackground");
		t1 = t2 = Type.DEFAULT;
		this.gameType = gameType;
		font = Fonts.get("hiero");
		go = new Button("READY"){
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
		go.setRounded(true);
		go.setDrawHoverScreen(false);
		Type[] types = Type.values();
		buttons = new Button[types.length];
		for(int i=0; i<types.length; i++) {
			buttons[i] = assignButton(types[i]);
			buttons[i].getLocation().x = 120;
			buttons[i].getLocation().y = 120+(i*10);
		}
	}
	
	private Button assignButton(final Type type) {
		Button button = new Button(type.name()){
			@Override
			public void mouseEntered() {
				
			}
			@Override
			public void mouseLeft() {
				
			}
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
		button.setAction(new Action() {
			@Override
			public void act(int button, int x, int y, int clickCount) {
				if(selecting==0)
					t1 = type;
				else
					t2 = type;
			}
		});
		button.setDrawHoverScreen(false);
		button.setRounded(true);
		button.setIcon(new Animation(new Image[]{Textures.get(type.getTextureName())},new int[]{300}));
		button.listen(SlimeSoccer.getInput());
		return button;
	}
	
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		trans.draw(container, g);
		for(Button a:buttons) 
			a.draw(g);
		String title = (selecting==0?"P1":"P2")+" Selecting: ";
		font.drawString(SlimeSoccer.getWidth()/2-font.getWidth(title)/2,font.getHeight(title)/2+10,title);
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
		for(Button a:buttons)
			a.update(i);
		if(image != null) {
			HashMap<String, Object> params = new HashMap<>();
			params.put("image",image);
			params.put("player1", t1);
			params.put("player2", t2);
			params.put("players", SoccerGame.DUEL);
			params.put("background","grassField");
			Scene.setCurrentScene(new SoccerGame(gameType), params);
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		trans = new FadingTransition((Image) params.get("image"));
	}

	@Override
	void unload(Scene scene) {
		for(Button a: buttons)
			a.mute(SlimeSoccer.getInput());
	}

}
