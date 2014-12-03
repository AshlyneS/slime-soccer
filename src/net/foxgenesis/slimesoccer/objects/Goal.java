package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Goal extends GameObject {
	private final int side;
	private Image img;

	public Goal(int side) {
		super(Textures.get("goal").getWidth(), Textures.get("goal").getHeight());
		this.img = Textures.get("goalBounds").getScaledCopy((int)width,(int)height);
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

	@Override
	public void render(Graphics g) {
		img.draw(location.x,location.y,width,height);
	}
}
