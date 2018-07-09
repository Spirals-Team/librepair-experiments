package coaching.net;

import java.net.Socket;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PicoProxy Class.
 */
public class PicoProxy {

	/**
	 * PicoProxyDaemon Class.
	 */
	public class PicoProxyDaemon extends Thread {

		/**
		 * Instantiates a new pico proxy daemon.
		 */
		public PicoProxyDaemon() {
			boolean exit = false;

			// ServerSocket serverSocket = new ServerSocket( 8888 ) ;

			while (!exit) {
				// * client and to the
				// server.
				final Socket client = null, server = null;
				exit = true;
			}
		}
	}

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(PicoProxy.class);

	/**
	 * main method.
	 *
	 * arguments
	 */
	public static void main(final String[] args) {
		PicoProxy.log.trace(System.getProperties().toString());
		PicoProxy.log.debug("args[]={}", Arrays.toString(args));

		new PicoProxy();
	}

	/**
	 * Instantiates a new pico proxy.
	 */
	public PicoProxy() {
		new PicoProxyDaemon();
	}

}
