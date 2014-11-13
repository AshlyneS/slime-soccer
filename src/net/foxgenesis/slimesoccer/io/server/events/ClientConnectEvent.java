package net.foxgenesis.slimesoccer.io.server.events;

import net.foxgenesis.slimesoccer.io.server.ChatServerThread;


public class ClientConnectEvent extends ConnectionEvent
{
	private final ChatServerThread client;
	
	public ClientConnectEvent(ChatServerThread client, int clientID) {
		super(clientID);
		this.client = client;
	}
	
	public ChatServerThread getClient() {
		return client;
	}

	@Override
	public boolean isConnectionBreaking()  {
		return false;
	}
}
