package net.foxgenesis.slimesoccer.ui;

import java.util.HashMap;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.io.KeyboardInput;
import net.foxgenesis.slimesoccer.objects.Bounds;
import net.foxgenesis.slimesoccer.ui.component.ProgressBar;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.SharedDrawable;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.loading.LoadingList;

/**
 * Loading is the loading screen at the start of the game
 * @author Seth
 */
public class Loading extends Scene {

	private Image background,ball,image;
	private AngelCodeFont hiero;
	private ProgressBar bar;
	private int update = 0,update2 = 0,speed=1;
	private boolean getImage = false;
	private int step = 0;
	private String updateString = "Loading...";
	private Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {

				LoadingList.setDeferredLoading(false);
				SharedDrawable sharedDrawable = new SharedDrawable(GameContainer.getSharedContext());
				sharedDrawable.makeCurrent();

				updateString = "Loading textures...";
				Textures.init();
				step = (int)bar.getMaximumValue()/4;

				updateString = "Loading fonts...";
				Fonts.init();
				step = (int)bar.getMaximumValue()/2;

				updateString = "Loading music...";
				SlimeSoccer.music = new Music("music/cactus.ogg");

				step = (int)bar.getMaximumValue()/4*3;

				updateString = "Loading pixel collision bounds...";
				Bounds.init();

				step = (int) bar.getMaximumValue();

				SlimeSoccer.music.loop();
				sharedDrawable.releaseContext();
				sharedDrawable.destroy();
			} catch( LWJGLException | SlickException e ) {
				e.printStackTrace();
			}
		}
	});

	/**
	 * Create a new loading screen instance
	 * @throws SlickException 
	 */
	public Loading() throws SlickException {
		super();
		background = new Image("textures/background/mainBackground.jpg");
		ball = new Image("textures/soccerball.png").getScaledCopy(50,50);
		hiero = new AngelCodeFont("fonts/hiero.fnt", new Image("textures/hiero.png"));
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
		String title = "Slime Soccer", input = "PRESS RETURN";
		g.drawImage(background, 0, 0, container.getWidth(), 
				container.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		ball.rotate((float) (Math.cos(0.05 * update2)/2 * ball.getWidth()/2));
		g.drawImage(ball,container.getWidth()/2 - ball.getWidth()/2
				- (float)(hiero.getHeight(title)*2 * Math.sin(0.05 * update2)),50);
		g.pushTransform();
		g.translate(0, -80);
		hiero.drawString(container.getWidth()/2-hiero.getWidth(title)/2,
				container.getHeight()/2-hiero.getHeight(title)/2, title);
		if(bar.isVisible())
			bar.draw(g);
		else
			hiero.drawString(container.getWidth()/2-hiero.getWidth(input)/2+10,container.getHeight()-100, input, update > 15/2?Color.red:Color.orange);
		g.popTransform();
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
	public void update(GameContainer gc, int i) {
		if(++update >= 15)
			update = 0;
		if((update2 += speed) >= 360)
			speed = -speed;
		if(bar.isVisible())
			if(SlimeSoccer.multiThreadLoading)
				bar.setValue(step);
			else
				bar.setValue(bar.getValue()+1);
		bar.setSize(gc.getWidth()/3*2,20);
		bar.setText(updateString);
		bar.setLocation(gc.getWidth()/2-bar.getWidth()/2,gc.getHeight()-100);
		bar.update(i);
		ball.rotate((float)(hiero.getHeight("Slime Soccer")/8 * Math.sin(0.05 * update2)));
		if(!bar.isVisible() && KeyboardInput.keys[Keyboard.KEY_RETURN])
			getImage = true;
		if(image != null) {
			HashMap<String, Object> params = new HashMap<>();
			params.put("image",image);
			Scene.setCurrentScene(Scene.getScene("mainMenu"), params);
		}
	}

	@Override
	void load(HashMap<String, Object> params) {
		if(SlimeSoccer.multiThreadLoading)
			thread.start();
	}

	@Override
	void unload(Scene scene) {

	}

}
