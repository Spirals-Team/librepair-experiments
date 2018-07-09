package coaching.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PicoDaemon implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(PicoDaemon.class);
	private volatile boolean keepRunning = true;

	public PicoDaemon() {
		LOG.info("{}", this.getClass().getSimpleName());
	}

	@Override
	public void run() {
		LOG.info("{} is running.....", this.getClass().getSimpleName());
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(8888);
			serverSocket.accept();

			while (this.keepRunning) {
				final Socket accept = serverSocket.accept();
				final RequestHandler clientRequest = new RequestHandler(accept);
				final Thread clientThread = new Thread(clientRequest);
				clientThread.start();
			}
		} catch (final IOException e) {
			LOG.error(e.toString());
		} finally {
			try {
				LOG.info("closing server socket");
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (final IOException e) {
				LOG.error(e.toString());
			}
		}
		LOG.info("{} is stopping.....", this.getClass().getSimpleName());
	}

	public synchronized void stop() {
		this.keepRunning = false;
	}

}
