package net.foxgenesis.slimesoccer.io.server;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import net.foxgenesis.slimesoccer.io.server.handlers.ServerMessageHandler;

public final class ChatServerThread extends Thread
{  
	private ChatServer server = null;
	private Socket socket = null;
	private int ID = -1;
	private DataInputStream  streamIn  =  null;
	private DataOutputStream streamOut = null;
	
	public ChatServerThread(ChatServer server, Socket socket) {  
		super();
		this.server = server;
		this.socket = socket;
		this.ID = socket.getPort();
	}
	
	@SuppressWarnings("deprecation")
	public void send(String msg) {   
		try {  
			streamOut.writeUTF(msg);
			streamOut.flush();
		} catch(IOException ioe) {   
			server.remove(ID);
			stop();
		}
	}

	public int getID() { 
		return ID;
	}
	
	private void handle(int id, String msg) {
		for(ServerMessageHandler a:server.getMessageHandlers())
			a.handle(id, msg);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {  
		while (true) {  
			try {  
				handle(ID, streamIn.readUTF());
			} catch(IOException ioe) {  
				server.remove(ID);
				stop();
			}
		}
	}

	public void open() throws IOException {  
		streamIn = new DataInputStream(new 
				BufferedInputStream(socket.getInputStream()));
		streamOut = new DataOutputStream(new
				BufferedOutputStream(socket.getOutputStream()));
	}

	public void close() throws IOException {  
		if (socket != null)    
			socket.close();
		if (streamIn != null)  
			streamIn.close();
		if (streamOut != null) 
			streamOut.close();
	}
}