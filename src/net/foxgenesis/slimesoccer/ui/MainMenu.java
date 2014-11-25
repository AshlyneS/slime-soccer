package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.component.Button;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
/**
 * @author Seth
 */
public class MainMenu extends Scene{
	private Image background;
	private Button duelPlayer;
	public MainMenu() {
		super();
		background = Textures.get("mainBackground");
		duelPlayer = new Button("Duel");
		duelPlayer.listen(SlimeSoccer.getInput());
		duelPlayer.setToolTipText("Play a game against a human");
		duelPlayer.getLocation().y = SlimeSoccer.getHeight()/3;
		duelPlayer.setRounded(true);
		duelPlayer.setFont(Fonts.get("hiero"));
		duelPlayer.setLocation(SlimeSoccer.getWidth()/2, SlimeSoccer.getHeight()/3);
		duelPlayer.setForeground(Color.white);
		duelPlayer.setAction(new Button.Action(){
			@Override
			public void act(Input input) {
				HashMap<String, Object> params = new HashMap<>();
				params.put("player1", Slime.Type.DISCO);
				params.put("player2", Slime.Type.SPONGE);
				params.put("players", SoccerGame.DUEL);
                                params.put("background", "background1"); 
				Scene.setCurrentScene(new SoccerGame(), params);
			}
		});
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		duelPlayer.draw(g);
	}

	@Override
	public void update(GameContainer container, int i) {
		duelPlayer.update(i);
	}

	@Override
	void load(HashMap<String, Object> params) {
		
	}

	@Override
	void unload(Scene scene) {
		
	}

}
