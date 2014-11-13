package net.foxgenesis.slimesoccer.io.server.handlers;

import net.foxgenesis.slimesoccer.io.server.events.ClientConnectEvent;

public interface ConnectHandler  {
	public void connect(ClientConnectEvent e);
}
