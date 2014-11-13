package net.foxgenesis.slimesoccer.io.server.handlers;

import net.foxgenesis.slimesoccer.io.server.events.ClientQuitEvent;

public interface QuitHandler  {
	public void quit(ClientQuitEvent e);
}
