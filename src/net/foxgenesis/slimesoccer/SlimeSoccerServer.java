package net.foxgenesis.slimesoccer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.foxgenesis.slimesoccer.io.client.handlers.ErrorHandler;
import net.foxgenesis.slimesoccer.io.server.ChatServer;
import net.foxgenesis.slimesoccer.io.server.events.ClientConnectEvent;
import net.foxgenesis.slimesoccer.io.server.events.ClientQuitEvent;
import net.foxgenesis.slimesoccer.io.server.handlers.ConnectHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.LogHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.QuitHandler;
import net.foxgenesis.slimesoccer.io.server.handlers.ServerMessageHandler;

public final class SlimeSoccerServer {
	private static final int PORT = 25560;
	private static final String IP = "localhost";
	public static void main(String[] args) throws UnknownHostException {
		JFrame frame = new JFrame("Slime Soccer Server");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500,500);
		frame.setLayout(new BorderLayout());
		final JTextArea area = new JTextArea();
		area.setBackground(Color.white);
		frame.add(new JScrollPane(area), BorderLayout.CENTER);
		JTextField input = new JTextField();
		input.setBackground(Color.white);
		frame.add(input,BorderLayout.PAGE_END);
		final ChatServer server = new ChatServer(IP, PORT);
		server.setMaxClients(2);
		server.addMessageHandler(new ServerMessageHandler() {
			@Override
			public void handle(int id, String message) {
				area.append("<" + id + "> " + message + "\n");
				server.send(id, "id:" + server.getAmountOfClients());
			}
		});
		server.addErrorHandler(new ErrorHandler() {
			@Override
			public void handleError(String error) {
				area.append("[ERROR]*** " + error + "***\n");
			}
		});
		server.addLogHandler(new LogHandler() {
			@Override
			public void handleLog(String message) {
				area.append("[LOG]: " + message + "\n");
			}
		});
		server.addQuitHandler(new QuitHandler() {
			@Override
			public void quit(ClientQuitEvent e) {
				area.append("[QUIT]: " + e.getClientID() + " has disconnected\n");
			}
		});
		server.addConnectHandler(new ConnectHandler() {
			@Override
			public void connect(ClientConnectEvent e) {
				area.append("[CONNECT]: " + e.getClientID() + "\n");
			}
		});
		frame.setVisible(true);
	}
}
