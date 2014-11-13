package net.foxgenesis.slimesoccer.io.server.events;

public abstract class ConnectionEvent 
{
	public abstract boolean isConnectionBreaking();
	private int clientID;
	
	public ConnectionEvent(int clientID) {
		this.clientID = clientID;
	}
	
	public int getClientID() {
		return clientID;
	}
}
