package ru.aserdyuchenko.bot;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Anton Serdyuchenko (anton415@gmail.com)
 */
public class Client {
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
	public Client(Socket socket) {
		this.socket = socket;
	}
/**
 * @param args					args.
 * @throws IOException			exception.
 */
	public static void main(String[] args) throws IOException {
		try (final Socket socket = new Socket("127.0.0.1", NUMBERPORT)) {
			new Client(socket);
		}
	}
/**
 * Start client.
 * @throws IOException	 		IOException.
 */
	public void startClient() throws IOException {
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String str = null;
		do {
   			System.out.println("Enter string for oracle: ");
    		str = in.readLine();
    		System.out.println(str);
        	out.println(str);
		} while (!("exit".equals(str)));
	}
}
