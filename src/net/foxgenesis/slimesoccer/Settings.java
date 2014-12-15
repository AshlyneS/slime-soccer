package net.foxgenesis.slimesoccer;

import org.newdawn.slick.Color;
/**
 * Settings changes the game settings
 * @author Seth
 */
public final class Settings {
	public static final float MAX_SPEED = 5f;
	public static final float SPEED = 0.3f;
	public static final float JUMP_VELOCITY = -5f;
	public static final float FRICTION_RESISTANCE_FACTOR = 6;
	public static final boolean MOTION_BLUR = true;
	public static final boolean DISPLAY_BOUNDS = false;
	public static final boolean ANAGLYPH = true;
	public static final float GUI_DISTANCE = 30;
	public static final Color CYAN = new Color(Color.cyan.getRed(), Color.cyan.getGreen(), Color.cyan.getBlue(), 0.2f),
			RED = new Color(Color.red.getRed(), Color.red.getGreen(), Color.red.getBlue(), 0.2f);
}
