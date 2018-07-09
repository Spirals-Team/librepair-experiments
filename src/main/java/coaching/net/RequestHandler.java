package coaching.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RequestHandler implements Runnable {
	private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);
	private volatile boolean keepRunning = true;
	private static int connectionsCount;
	private int connectionId = 0;
	private final Socket clientSocket;

	public RequestHandler(final Socket clientSocket) {
		this.connectionId = connectionsCount++;
		LOG.info("handling connection, #" + this.connectionId);
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;
		try {
			printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
			bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			String inputLine, outputLine;
			while (this.keepRunning) {
				inputLine = bufferedReader.readLine();
				LOG.info("received : {}", inputLine);

				outputLine = inputLine;
				printWriter.write(outputLine + "\n");
				printWriter.flush();

				if (outputLine != null) {
					if (outputLine.contains("exit")) {
						this.keepRunning = false;
					}
				}
			}
		} catch (final Exception e) {
			LOG.info(e.toString());
		} finally {
			if (printWriter != null) {
				printWriter.close();
				try {
					if (bufferedReader != null) {
						bufferedReader.close();
						if (this.clientSocket != null) {
							this.clientSocket.close();
							LOG.info("closing connection : {}", this.connectionId);
						}
					}
				} catch (final IOException e) {
					LOG.info(e.toString());
				}
			}
		}
	}
}
