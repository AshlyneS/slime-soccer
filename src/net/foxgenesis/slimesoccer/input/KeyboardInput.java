package net.foxgenesis.slimesoccer.input;

import org.lwjgl.input.Keyboard;

public final class KeyboardInput {
	/**
	 * Array of booleans containing the state of all keys
	 */
	public static final boolean[] keys = new boolean[256];
	
	/**
	 * Check for keyboard input
	 */
	public static void update() {
		for(int i=0;i<keys.length;i++)
			keys[i] = Keyboard.isKeyDown(i);
	}
}
