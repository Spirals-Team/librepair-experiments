/**
 *  @title       Guid.java
 *  @description TODO
 *	Created      28-Oct-2004
 **/
package coaching.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NetInfo Class.
 */
public class NetInfo {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(NetInfo.class);

	/**
	 * main method.
	 *
	 * arguments
	 */
	public static void main(final String[] args) {
		NetInfo.log.trace(System.getProperties().toString());
		NetInfo.log.debug("args[]={}", Arrays.toString(args));
		new NetInfo();
	}

	/**
	 * Instantiates a new net info.
	 */
	public NetInfo() {
		try {
			final Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				final NetworkInterface networkInterface = interfaces.nextElement();
				NetInfo.log.info("networkInterface.getDisplayName()={}", networkInterface.getDisplayName());
				final Enumeration<InetAddress> bounded = networkInterface.getInetAddresses();
				while (bounded.hasMoreElements()) {
					final InetAddress inetAddress = bounded.nextElement();
					NetInfo.log.info("inetAddress.getHostAddress()={}", inetAddress.getHostAddress());
					NetInfo.log.info("inetAddress.getHostName()={}", inetAddress.getHostName());
				}
			}

			final Enumeration<Object> systemProperties = System.getProperties().elements();
			while (systemProperties.hasMoreElements()) {
				NetInfo.log.info(systemProperties.nextElement().toString());
			}
		} catch (final Exception e) {
			NetInfo.log.info("{}", e);
		}
	}
}
