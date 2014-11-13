package net.foxgenesis.slimesoccer.io.client;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import net.foxgenesis.slimesoccer.io.client.handlers.ErrorHandler;
import net.foxgenesis.slimesoccer.io.client.handlers.MessageHandler;

public final class ChatClient
{  
	private Socket socket = null;
	private DataOutputStream streamOut = null;
	private ChatClientThread client = null;
	private String serverName = "localhost";
	private int serverPort = -1;
	private ArrayList<MessageHandler> messageHandlers;
	private ArrayList<ErrorHandler> errorHandlers;
	/**
	 * Create a new ChatClient that connects to a given server and receives UTF-8 encoded messages.
	 * This instance will auto-connect when created
	 * @param chatReciever receiver to handle the connection
	 */
	public ChatClient(String ip, int port) { 
		messageHandlers = new ArrayList<>();
		errorHandlers = new ArrayList<>();
		serverPort = port;
		serverName = ip;
		connect(serverName, serverPort);
	}

	/**
	 * Create a new ChatClient that connects to a given server and receives UTF-8 encoded messages.
	 */
	public ChatClient() {
		messageHandlers = new ArrayList<>();
		errorHandlers = new ArrayList<>();
		new ArrayList<>();
	}

	protected void error(String msg) {
		for(ErrorHandler a:errorHandlers)
			a.handleError(msg);
	}
	
	protected void handle(String msg) {
		for(MessageHandler a:messageHandlers)
			a.handle(msg);
	}

	/**
	 * Connects to the given server
	 * @param output receiver to handle the connection
	 */
	public void connect(String ip, int port) {
		serverName = ip;
		serverPort = port;
		try {  
			socket = new Socket(serverName, serverPort);
			open();
		} catch(UnknownHostException uhe) {  
			System.err.println("Host unknown: " + uhe.getMessage()); 
		} catch(IOException ioe) {  
			System.err.println("Unexpected exception: " + ioe.getMessage()); 
		} 
	}

	/**
	 * Sends a message to the server through bytes in UTF-8 encoding 
	 * @param msg message to send to the server
	 */
	public void send(String msg) {  
		if(isConnected())
			try {
				streamOut.writeUTF(msg);
				streamOut.flush();
			} catch (IOException e) {
				error(e.getMessage());
			} 
		else
			System.err.println("client is not connected!");
	}

	private void open() throws IOException {   
		streamOut = new DataOutputStream(socket.getOutputStream());
		client = new ChatClientThread(this, socket); 
	}

	/**
	 * Closes the socket
	 */
	@SuppressWarnings("deprecation")
	public void close() {  
		if(client != null)
			client.stop();
		client = null;

		try  {
			if (streamOut != null)
				streamOut.close();
			if (socket    != null)  
				socket.close(); 
		} catch (IOException e) {
			error("Failed to close");
		}
	}

	/**
	 * checks if the ChatClient is currently connected to the server
	 * @return Boolean is connected
	 */
	public boolean isConnected() {
		return client != null && streamOut != null;
	}

	public ArrayList<MessageHandler> getMessageHandlers() {
		return messageHandlers;
	}

	public ArrayList<ErrorHandler> getErrorHandlers() {
		return errorHandlers;
	}

	public void addMessageHandler(MessageHandler handler) {
		messageHandlers.add(handler);
	}

	public void addErrorHandler(ErrorHandler handler) {
		errorHandlers.add(handler);
	}

	public void removeMessageHandler(MessageHandler handler) {
		messageHandlers.remove(handler);
	}

	public void removeErrorHandler(ErrorHandler handler) {
		errorHandlers.remove(handler);
	}
}