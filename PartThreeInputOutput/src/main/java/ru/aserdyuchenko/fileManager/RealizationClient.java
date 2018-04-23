package ru.aserdyuchenko.fileManager;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
/**
 * @author Anton Serdyuchenko.
 */
public class RealizationClient {
/**
 * @param socket - socket.
 */
	private final Socket socket;
/**
 * @param port - port.
 */
	private static int port;
/**
 * @param ip - ip.
 */
	private static String ip;
/**
 * @param socket - client socket.
 */
	 public RealizationClient(Socket socket) {
		this.socket = socket;
	}
/**
 * @param args - args.
 * @throws IOException - exception.
 */
	public static void main(String[] args) throws IOException {
		port = Integer.parseInt(readProperties("number.port"));
		ip = readProperties("number.ip");
		try (final Socket socket = new Socket(ip, port)) {
			new RealizationClient(socket);
		}
	}
/**
 * Start client.
 * @throws IOException - IOException.
 */
	public void startClient() throws IOException {
		PrintWriter out = new PrintWriter(this.socket.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		String str = null;
		do {
			System.out.print("Enter string for file manager: ");
			str = in.readLine();
			System.out.println(str);
			out.println(str);
		} while (!("Exit".equals(str)));
	}
/**
 * @param needValue - what value you need.
 * @return value - value.
 * @throws IOException - IOException.
 */
	public static String readProperties(String needValue) throws IOException {
		RealizationSettings settings = new RealizationSettings();
		ClassLoader loader = RealizationSettings.class.getClassLoader();
		try (InputStream io = loader.getResourceAsStream("app.properties")) {
			settings.load(io);
		}
		String value = settings.getValue(needValue);
		return value;
	}
}
