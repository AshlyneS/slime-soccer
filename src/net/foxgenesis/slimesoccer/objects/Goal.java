package net.foxgenesis.slimesoccer.objects;

import java.util.Random;

import net.foxgenesis.slimesoccer.SlimeSoccer;
import net.foxgenesis.slimesoccer.font.Fonts;
import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.sound.Sounds;
import net.foxgenesis.slimesoccer.ui.SoccerGame;
import net.foxgenesis.slimesoccer.util.TextBounce;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;

public class Goal extends GameObject {
	private final int side;
	private Image img;
	private static Animation conf;
	protected final Slime slime;
	private static final Sound cheer = Sounds.get("cheer"), app = Sounds.get("app");
	private static final Random rand = new Random();
	private static float opacity = 0f;
	private boolean goal = false;
	private static TextBounce text;

	public Goal(int side, Slime slime) {
		super(Textures.get("goal").getWidth(), Textures.get("goal").getHeight());
		if(conf == null)
			conf = Textures.confetti;
		if(text == null)
			try {
				text = new TextBounce("GOAL!", SlimeSoccer.getWidth()/2-Fonts.get("hiero").getWidth("GOAL!")/2, SlimeSoccer.getHeight()/2, Fonts.get("hiero"), 1f, 5);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		this.slime = slime;
		this.img = Textures.get("goal").getScaledCopy((int)width,(int)height);
		if(side == SoccerGame.GOAL_RIGHT) {
			img = img.getFlippedCopy(true, false);
		}
		if(Bounds.contains("goal") == false)
			System.err.println("failed to get bounds!");
		else
			bounds = Bounds.get("goal");
		this.side = side;
	}

	public int getSide() {
		return side;
	}

	public void addGoal() {
		if(rand.nextInt(10) > 5) {
			app.stop();
			cheer.play();
		}
		else {
			cheer.stop();
			app.play();
		}
		opacity = 1f;
		slime.setGoalCount(slime.getGoalCount()+1);
		goal = true;
	}

	@Override
	public boolean contains(float x, float y) {
		if(x > location.x && x < location.x + width)
			if(y > location.y && y < location.y + height)
				return true;
		return false;
	}

	public boolean contains(float x, float y, float radius) {
		if(x + radius > location.x && x - radius < location.x + width)
			if(y + radius > location.y && y - radius < location.y + height)
				return true;
		return false;
	}

	@Override
	public void update(int i) {
		super.update(i);
		if(opacity > 0f)
			opacity-=0.002f;
		if(opacity > 0f) {
			slime.getLocation().x = slime.secondary?SlimeSoccer.getWidth()/4*3-slime.getWidth()/2:SlimeSoccer.getWidth()/4-slime.getWidth()/2;
			slime.getVelocity().x = slime.getVelocity().y = 0f;
		}
		conf.update(i);
		if(goal && opacity <= 0)
			goal = false;
		if(goal)
			text.update(i);
	}

	@Override
	public void render(Graphics g) {
		img.draw(location.x,location.y,width,height);
		if(opacity > 0f)
			conf.draw(0, 0, SlimeSoccer.getWidth(),SlimeSoccer.getHeight());
		if(goal)
			text.render(g);
	}
}
