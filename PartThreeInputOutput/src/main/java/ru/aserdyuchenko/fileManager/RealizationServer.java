package ru.aserdyuchenko.fileManager;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.io.RandomAccessFile;
/**
 * @author Anton Serdyuchenko.
 */
public class RealizationServer {
/**
 * @param file - file.
 */
	private RandomAccessFile file;
/**
 * @param socket - socket.
 */
	private final Socket socket;
/**
 * @param port - port.
 */
	private static int port;
/**
 * Переменная нужна для перехода в нужный каталог.
 * @param nowPath.
 */
	private String nowPath = "./";
/**
 * Переменная нужна для перехода в нужный каталог.
 * @param homePath.
 */
	private String homePath = "./";
/**
 * @param socket - server socket.
 */
	public RealizationServer(Socket socket) {
		this.socket = socket;
	}
/**
 * @param args - args.
 * @throws IOException - exception.
 */
	public static void main(String[] args) throws IOException {
		port = Integer.parseInt(readProperties("number.port"));
		try (final Socket socket = new ServerSocket(port).accept()) {
			new RealizationServer(socket);
		}
	}
/**
 * @param newHomePath - home path.
 * @throws IOException - IOException.
 */
	public void startServer(String newHomePath) throws IOException {
		homePath = newHomePath;

		InputStream socketInStr = socket.getInputStream();
		OutputStream socketOutStr = socket.getOutputStream();
		DataInputStream in = new DataInputStream(socketInStr);
		DataOutputStream out = new DataOutputStream(socketOutStr);
		String ask = null;
		do {
   		System.out.println("wait command ...");
    	ask = in.readLine();
    	System.out.println(ask);
			if ("Go to".equals(ask)) {
				do {
					ask = in.readLine();
					out.writeBytes(Arrays.toString(seeThisPath(ask)));
					ask = in.readLine();
					out.writeBytes("\n");
				} while (!("Exit in menu".equals(ask)));
			} else if ("Go home".equals(ask)) {
				nowPath = homePath;
				out.writeBytes(Arrays.toString(seeHomePath()));
				out.writeBytes("\n");
			} else if ("Download".equals(ask)) {
				ask = in.readLine();
				out.write(downloadFile(ask));
			} else if ("Push".equals(ask)) {
				do {
					ask = in.readLine();
					if ("Create new file".equals(ask)) {
						ask = in.readLine();
						createNewFileInServer(ask);
						out.writeBytes("File create");
						out.writeBytes("\n");
					} else if ("Exit in menu".equals(ask)) {
						out.writeBytes("Write in file done");
						break;
					} else {
						pushFile(ask);
					}
				} while (!("Exit in menu".equals(ask)));
			} else if (!("Exit".equals(ask))) {
				out.writeBytes("I don`t understand.\n");
			}
		} while (!("Exit".equals(ask)));
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
/**
 * Возвращает список корневого каталога.
 * @return list - содержимое корневой папки.
 */
	public String[] seeHomePath() {
		String[] list = new File(homePath).list();
		return list;
	}
/**
 * Осуществляет переход в выбранные каталог.
 * @param thisPath - thisPath.
 * @return list - содержимое данной папки.
 */
	public String[] seeThisPath(String thisPath) {
		String[] list = new File(homePath + thisPath).list();
		return list;
	}
/**
 * Скачать файл.
 * @param nameFile - nameFile.
 * @return content - content.
 * @throws IOException - IOException.
 */
	public byte[] downloadFile(String nameFile) throws IOException {
		File file = new File(nameFile);
		byte[] content = Files.readAllBytes(file.toPath());
		return content;
	}
/**
 * Загрузить файл.
 * @param bytesForNewFile - bytesForNewFile.
 * @throws IOException - IOException.
 */
	public void pushFile(String bytesForNewFile) throws IOException {
		file.writeBytes(bytesForNewFile);
	}
/**
 * Создание нового файла.
 * @param nameNewFile - nameNewFile.
 * @throws IOException - IOException.
 */
 public void createNewFileInServer(String nameNewFile) throws IOException {
	this.file = new RandomAccessFile(nameNewFile, "rw");
 }
}
