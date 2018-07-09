/**
 * A Simple MultiThreaded Port Scanner
 * Created on 22 September 2004, 12:55
 */
package coaching.net;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PortScan Class.
 */
public class PortScan extends Thread {

	private static final Logger log = LoggerFactory.getLogger(PortScan.class);
	private static int loadFactor = 100;
	private static Properties properties = new Properties();
	private String ip = null;
	private int port = 0;
	private final boolean reportClosedPort = false;

	/**
	 * main method.
	 *
	 * arguments
	 */
	public static void main(final String[] args) {
		PortScan.log.trace(System.getProperties().toString());
		PortScan.log.debug("args[]={}", Arrays.toString(args));

		// * port number
		try {
			PortScan.log.info("PortScan IP : {}", args[0]);
			PortScan.properties.load(new FileInputStream(new File("ports.properties")));

			for (int port = 1; port < 64 * 1024;) {
				if (Thread.activeCount() > PortScan.loadFactor) {
					Thread.yield();
				} else {
					final PortScan portScan = new PortScan(args[0], port);
					portScan.start();
					port++;
				}
			}
		} catch (final Exception exception) {
			PortScan.log.info("Command line must include IP address");
		}
	}

	/**
	 * Instantiates a new port scan.
	 *
	 * ip
	 * port
	 */
	public PortScan(final String ip, final int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	/**
	 * Look up port.
	 *
	 * port
	 * string
	 */
	public String lookUpPort(final int port) {
		return PortScan.properties.getProperty(Integer.toString(port), "unknown");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			final java.net.Socket socket = new java.net.Socket(this.ip, this.port);

			// report open port & try looking it up
			PortScan.log.info("portscan = {} : {} ", this.ip, this.port);
			PortScan.log.info("scanning = {} ", lookUpPort(this.port));

			Thread.yield();

			socket.close();
		} catch (final Exception exception) {
			if (this.reportClosedPort == true) {
				PortScan.log.info("portscan = {} : {} ", this.ip, this.port);
				log.error(exception.toString());
			}
		}
	}
}
