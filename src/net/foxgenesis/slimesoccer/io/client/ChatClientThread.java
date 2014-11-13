package net.foxgenesis.slimesoccer.io.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public final class ChatClientThread extends Thread
{  
	private Socket socket   = null;
	private ChatClient client   = null;
	private DataInputStream streamIn = null;

	public ChatClientThread(ChatClient client, Socket socket) {  
		this.client = client;
		this.socket = socket;
		open();  
		start();
	}

	public void open() {  
		try {  
			streamIn  = new DataInputStream(socket.getInputStream());
		} catch(IOException ioe) {  
			client.error("Error getting input stream: " + ioe);
			client.close();
		}
	}

	public void close() {  
		try {  
			if (streamIn != null) streamIn.close();
		} catch(IOException ioe) {  
			client.error("Error closing input stream: " + ioe);
		}
	}
	
	public void run() {  
		while (true) {  
			try {  
				client.handle(streamIn.readUTF());
			} catch(IOException ioe) { 
				client.error("Listening error: " + ioe.getMessage());
				client.close();
			}
		}
	}
}
