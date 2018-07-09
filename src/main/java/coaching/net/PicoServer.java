package coaching.net;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PicoServer Class.
 */
public class PicoServer {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(PicoServer.class);

	/**
	 * main method.
	 *
	 * arguments
	 */
	public static void main(final String[] args) {
		PicoServer.log.trace(System.getProperties().toString());
		PicoServer.log.debug("args[]={}", Arrays.toString(args));

		final PicoDaemon picoDaemon = new PicoDaemon();

		final Thread thread = new Thread(picoDaemon, "picoDaemon");
		thread.start();

	}

}