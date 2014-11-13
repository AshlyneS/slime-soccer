package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import javax.swing.JOptionPane;

import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.io.KeyboardInput;
import net.foxgenesis.slimesoccer.io.client.ChatClient;
import net.foxgenesis.slimesoccer.io.client.handlers.MessageHandler;
import net.foxgenesis.slimesoccer.objects.Slime;
import net.foxgenesis.slimesoccer.ui.component.ProgressBar;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

/**
 * Loading is the loading screen at the start of the game
 * @author Seth
 */
public class Loading extends Scene {

	private Image background,ball;
	private AngelCodeFont hiero;
	private ProgressBar bar;
	private int update = 0,update2 = 0,speed=1;

	/**
	 * Create a new loading screen instance
	 */
	public Loading() {
		super();
		background = Textures.get("mainBackground");
		ball = Textures.get("soccerball").getScaledCopy(50,50);
		hiero = Fonts.get("hiero");
		bar = new ProgressBar();
		bar.setVisible(true);
		bar.setText("This bar doesn't do anything! lawl :P");
		bar.setAction(new Runnable() {
			@Override
			public void run() {
				bar.setVisible(false);
			}
		});
	}
	@Override
	public void draw(GameContainer container, Graphics g) {
		String title = "Slime Soccer", input = "PRESS SPACE";
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		g.drawImage(ball,container.getWidth()/2 - ball.getWidth()/2
				- (float)(hiero.getHeight(title)*2 * Math.sin(0.05 * update2)),50);
		g.pushTransform();
		g.translate(0, -80);
		hiero.drawString(container.getWidth()/2-hiero.getWidth(title)/2,
				container.getHeight()/2-hiero.getHeight(title)/2, title);
		if(bar.isVisible())
			bar.draw(g);
		else
			hiero.drawString(container.getWidth()/2-hiero.getWidth(input)/2,container.getHeight()-100, input, update > 15/2?Color.red:Color.orange);
		g.popTransform();
	}

	@Override
	public void update(GameContainer gc, int i) {
		if(++update >= 15)
			update = 0;
		if((update2 += speed) >= 360)
			speed = -speed;
		bar.setSize(gc.getWidth()/3*2,20);
		bar.setLocation(gc.getWidth()/2-bar.getWidth()/2,gc.getHeight()-100);
		bar.update(i);
		ball.rotate((float)(hiero.getHeight("Slime Soccer")/8 * Math.sin(0.05 * update2)));
		if(bar.isVisible())
			bar.setValue(bar.getValue()+1);
		else if(KeyboardInput.keys[Keyboard.KEY_SPACE]) {
			HashMap<String, Object> params = new HashMap<>();
			params.put("player1", Slime.Type.DEFAULT);
			params.put("player2", Slime.Type.DEFAULT);
			Scene.setCurrentScene(new SoccerGame(), params);
		}
		else if(KeyboardInput.keys[Keyboard.KEY_LSHIFT]) {
			final HashMap<String, Object> params = new HashMap<>();
			String[] args = JOptionPane.showInputDialog(null, "Server IP required", "Please enter in server IP: ").split(":");
			System.out.println("attempting connection...");
			final ChatClient client = new ChatClient();
			client.addMessageHandler(new MessageHandler() {
				@Override
				public void handle(String message) {
					System.out.println("{SEVER}: " + message);
					if(message.startsWith("id:")) {
						String id =  message.substring(message.indexOf(":"));
						params.put("player" + id, Slime.Type.DEFAULT);
						params.put("player" + id + " control", true);
						params.put("player" + id + " keys", id.equals("2"));
						params.put("multiplayer", client);
						params.put("id handler", this);
						Scene.setCurrentScene(new SoccerGame(), params);
					}
				}
			});
			client.connect(args[0],args.length>1?Integer.parseInt(args[1].replaceAll("[^\\d.]", "")):0);
			if(client.isConnected()) {
				System.out.println("requesting id...");
				client.send("idr");
			}
		}
	}

	@Override
	void load(HashMap<String, Object> params) {

	}

	@Override
	void unload(Scene scene) {
		
	}

}
