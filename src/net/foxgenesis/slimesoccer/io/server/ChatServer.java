package net.foxgenesis.slimesoccer.io.server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import net.foxgenesis.slimesoccer.io.client.handlers.ErrorHandler;
import net.foxgenesis.slimesoccer.io.server.events.ClientConnectEvent;
import net.foxgenesis.slimesoccer.io.server.events.ClientQuitEvent;
import net.foxgenesis.slimesoccer.io.server.handlers.ConnectHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.LogHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.QuitHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.ServerMessageHandler;

public final class ChatServer implements Runnable 
{  
	private ArrayList<ChatServerThread> clients;
	private ServerSocket server = null;
	private Thread       thread = null;
	private String ip;
	private int port;
	private int clientCount = 0;
	private int maxClients = -1;
	private ArrayList<ConnectHandler> connectHandlers;
	private ArrayList<QuitHandler> quitHandlers;
	private ArrayList<ServerMessageHandler> messageHandlers;
	private ArrayList<ErrorHandler> errorHandlers;
	private ArrayList<LogHandler> logHandlers;

	/**
	 * creates a new ChatServer for connection between clients
	 * @param a ServerReciever implementing class
	 */
	public ChatServer(String ip, int port) {  
		clients = new ArrayList<>();
		connectHandlers = new ArrayList<>();
		quitHandlers = new ArrayList<>();
		messageHandlers = new ArrayList<>();
		errorHandlers = new ArrayList<>();
		logHandlers = new ArrayList<>();	
		try {  
			server = new ServerSocket();
			server.bind(new InetSocketAddress(ip, port));
			System.out.println(server.toString());
			this.ip = ip;
			this.port = port;
			start();
		} catch(IOException ioe) {  
			System.err.println("Can not bind to port " + port + ": " + ioe.getMessage()); 
		}
	}
	
	/**
	 * Get the local ip of the server
	 * @return String localip
	 */
	public String getIP() {
		return ip;
	}
	
	/**
	 * Get the port of the server
	 * @return int port of the server
	 */
	public int getPort() {
		return port;
	}
	
	protected void log(String msg) {
		for(LogHandler a:logHandlers)
			a.handleLog(msg);
	}
	
	protected void error(String msg) {
		for(ErrorHandler a:errorHandlers)
			a.handleError(msg);
	}

	/**
	 * send a message to a specific client
	 * @param ID ID of client
	 * @param msg
	 */
	public void send(int ID, String msg) {
		clients.get(findClient(ID)).send(msg);
	}
	
	/**
	 * Set the max clients that the server can hold
	 * NOTE: -1 means no limit
	 * @param amount amount of clients
	 */
	public void setMaxClients(int amount) {
		maxClients = amount;
	}


	/**
	 * Called by runnable
	 */
	public void run() { 
		while (thread != null) {  
			try {  	
				if(clientCount != maxClients){
					log("Waiting for a client ..."); 
					addThread(server.accept()); 
				}
			} catch(IOException ioe) {  
				error("Server accept error: " + ioe); 
				stop(); 
			}
		}
	}

	private void start() { 
		if (thread == null) {  
			thread = new Thread(this); 
			thread.start();
		}
	}

	/**
	 * Stops the server 
	 */
	@SuppressWarnings("deprecation")
	public void stop() { 
		if (thread != null) { 
			thread.stop(); 
			thread = null;
		}
	}

	private int findClient(int ID) {  
		for (int i = 0; i < clientCount; i++)
			if (clients.get(i).getID() == ID)
				return i;
		return -1;
	}

	/**
	 * sends a message to all clients
	 * @param input message to send
	 */
	public void sendAll(String input) {
		for (int i = 0; i < clientCount; i++)
			clients.get(i).send(input); 
	}

	/**
	 * remove a selected ID
	 * @param ID ID of client
	 */
	@SuppressWarnings("deprecation")
	public synchronized void remove(int ID) {  
		int pos = findClient(ID);
		if (pos >= 0) {  
			ChatServerThread toTerminate = clients.get(pos);
			log("Removing client thread " + ID + " at " + pos);
			clients.remove(pos);
			clientCount--;
			ClientQuitEvent e = new ClientQuitEvent(toTerminate.getID());
			for(QuitHandler a: quitHandlers)
				a.quit(e);
			try {  
				toTerminate.close(); 
			} catch(IOException ioe) {  
				error("Error closing thread: " + ioe); 
			}
			toTerminate.stop(); 
		}
	}

	private void addThread(Socket socket) {  
		if (clientCount < maxClients) { 
			log("Client accepted: " + socket);
			clients.add(new ChatServerThread(this, socket));
			try { 
				clients.get(clientCount).open(); 
				clients.get(clientCount).start();  
				ClientConnectEvent e = new ClientConnectEvent(clients.get(clientCount),clients.get(clientCount).getID());
				clientCount++; 
				for(ConnectHandler a: connectHandlers)
					a.connect(e);
			} catch(IOException ioe) {  
				error("Error opening thread: " + ioe); 
			}
		}
		else
			error("Client refused: maximum " + clients.size() + " reached.");
	}

	@Override
	public String toString() {
		return "ChatServer{;host=" + ip + ";port=" + port + ";clients=" + clientCount + ";maxClients = " + maxClients + ";}";
	}

	/**
	 * Gets the current amount of clients currently connected
	 * @return int amount of clients
	 */
	public int getAmountOfClients() {
		return clientCount;
	}

	public void addConnectHandler(ConnectHandler handler) {
		connectHandlers.add(handler);
	}
	
	public void addQuitHandler(QuitHandler quitHandler) {
		quitHandlers.add(quitHandler);
	}
	
	public void addMessageHandler(ServerMessageHandler handler) {
		messageHandlers.add(handler);
	}
	
	public void addLogHandler(LogHandler handler) {
		logHandlers.add(handler);
	}
	
	public void addErrorHandler(ErrorHandler handler) {
		errorHandlers.add(handler);
	}
	
	public void removeConnectHandler(ConnectHandler handler) {
		connectHandlers.remove(handler);
	}
	
	public void removeQuitHandler(QuitHandler quitHandler)  {
		quitHandlers.remove(quitHandler);
	}
	
	public void removeMessageHandler(ServerMessageHandler handler) {
		messageHandlers.remove(handler);
	}
	
	public void removeLogHandler(LogHandler handler) {
		logHandlers.remove(handler);
	}
	
	public void removeErrorHandler(ErrorHandler handler) {
		errorHandlers.remove(handler);
	}
	
	public ConnectHandler[] getConnectHandlers() {
		return connectHandlers.toArray(new ConnectHandler[]{});
	}
	
	public QuitHandler[] getQuitHandlers() {
		return quitHandlers.toArray(new QuitHandler[]{});
	}
	
	public ServerMessageHandler[] getMessageHandlers() {
		return messageHandlers.toArray(new ServerMessageHandler[]{});
	}
	
	public LogHandler[] getLogHandlers() {
		return logHandlers.toArray(new LogHandler[]{});
	}
	
	public ErrorHandler[] getErrorHandlers() {
		return errorHandlers.toArray(new ErrorHandler[]{});
	}
}