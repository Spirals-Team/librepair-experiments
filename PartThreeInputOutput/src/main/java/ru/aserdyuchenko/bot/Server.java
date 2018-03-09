package ru.aserdyuchenko.bot;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 */
public class Server {
/**
 * @param socket				socket.
 */
	private final Socket socket;
/**
 * @param NUMBERPORT			NUMBERPORT.
 */
	public static final int NUMBERPORT = 5000;
/**
 * @param socket					server socket.
 */
	public Server(Socket socket) {
		this.socket = socket;
	}
/**
 * @param args					args.
 * @throws IOException			exception.
 */
	public static void main(String[] args) throws IOException {
		try (final Socket socket = new ServerSocket(NUMBERPORT).accept()) {
			new Server(socket);
		}
	}
/**
 * @throws IOException	 		IOException.
 */
	public void startServer() throws IOException {
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String ask = null;
		do {
   			System.out.println("wait command ...");
    		ask = in.readLine();
    		System.out.println(ask);
			if ("Hello oracle".equals(ask)) {
       			out.println("Hello, dear friend, I'm a oracle.");
        		out.println();
    		} else if (!("exit".equals(ask))) {
				out.println("I don`t understand.");
				out.println();
			}
		} while (!("exit".equals(ask)));
	}
}
