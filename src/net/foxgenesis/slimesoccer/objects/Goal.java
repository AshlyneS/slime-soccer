package net.foxgenesis.slimesoccer.objects;

import net.foxgenesis.slimesoccer.image.Textures;
import net.foxgenesis.slimesoccer.ui.SoccerGame;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class Goal extends GameObject {
	private final int side;
	private Image img;
	private final Slime slime;

	public Goal(int side, Slime slime) {
		super(Textures.get("goal").getWidth(), Textures.get("goal").getHeight());
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
		slime.setGoalCount(slime.getGoalCount()+1);
	}

	@Override
	public void render(Graphics g) {
		img.draw(location.x,location.y,width,height);
	}
}
