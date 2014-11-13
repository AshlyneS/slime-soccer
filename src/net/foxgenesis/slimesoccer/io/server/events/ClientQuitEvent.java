package net.foxgenesis.slimesoccer.io.server.events;


public class ClientQuitEvent extends ConnectionEvent
{
	public ClientQuitEvent(int clientID) {
		super(clientID);
	}

	@Override
	public boolean isConnectionBreaking()  {
		return true;
	}
}
