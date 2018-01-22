package com.mlaf.hu.proxy;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

/**
 * Manages JMDNS/avahi registrations. JADE agents must register themselves here
 * so that ROCK agents can contact them.
 *
 * @author Satia Herfert
 */
public class JMDNSManager {

    /**
     * The JMDNS type
     */
    public static final String JMDNS_TYPE = "_fipa_service_directory._udp.local.";
    /**
     * ROCKs DateFormat
     */
    public static final DateFormat JMDNS_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HH:mm:ss:SSS000");

    public static final String JMDNS_DESCRIPTION = "Message proxy";

    /**
     * The Logger.
     */
    private static final Logger logger = Logger.getLogger(JMDNSManager.class.getName());

    private final JmDNS jmdns;
    private final InetAddress inetAddress;
    private final int jadeSocketPort;

    public static InetAddress getLocalIPv4Address() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface current = interfaces.nextElement();
            if (!current.isUp() || current.isLoopback() || current.isVirtual()) {
                continue;
            }
            // Only use eth, not wlan
            //if (!current.getName().startsWith("eth")) {
            //    continue;
            //}

            Enumeration<InetAddress> addresses = current.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress current_addr = addresses.nextElement();
                if (current_addr.isLoopbackAddress() || !(current_addr instanceof Inet4Address)) {
                    continue;
                }
                // We found our address
                return current_addr;
            }
        }

        throw new SocketException("No matching local IPv4 interface found.");
    }
    
    public InetAddress getInetAddress(){
        return inetAddress;
    }

    /**
     * @param rockProxyMTSPort the port the jadeSocketPort listens on.
     * @param listener a listener that performs JADE relevant actions when
     * services are added or removed.
     * @throws IOException if an error occurs
     */
    public JMDNSManager(int rockProxyMTSPort, ServiceListener listener) throws IOException {
        this.jadeSocketPort = rockProxyMTSPort;
        // Get the eth0 v4 address
        this.inetAddress = getLocalIPv4Address();

        jmdns = JmDNS.create(inetAddress);
        if (listener != null) {
            jmdns.addServiceListener(JMDNS_TYPE, listener);
        }
        logger.log(Level.INFO, "JMDNS created for IP {0}", inetAddress.getHostAddress());
    }

    /**
     * Registers a local JADE agent
     *
     * @param name its name
     */
    public void registerJadeAgent(String name) {
        try {
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("DESCRIPTION", JMDNS_DESCRIPTION);
            properties.put("TYPE", "mts_client");
            properties.put("LOCATOR", "tcp://" + inetAddress.getHostAddress() + ":"
                    + jadeSocketPort + " JadeProxyAgent");
            properties.put("TIMESTAMP", JMDNS_DATE_FORMAT.format(GregorianCalendar.getInstance().getTime()));

            // _fipa_service_directory._udp.local.
//            Map<Fields,String> qualifiedNameMap = new EnumMap<Fields, String>(Fields.class);
//            qualifiedNameMap.put(Fields.Application, "fipa_service_directory");
//            qualifiedNameMap.put(Fields.Domain, "local");
//            qualifiedNameMap.put(Fields.Instance, name + "@1.234:1099/JADE");
//            qualifiedNameMap.put(Fields.Protocol, "udp");
//            ServiceInfo si = ServiceInfo.create(qualifiedNameMap,
//                    jadeSocketPort, 1, 1, true, properties);
            // JMDNS cannot handle dots in the service name. They are being
            // replaced.
            ServiceInfo si = ServiceInfo.create(JMDNS_TYPE,
                    name.replaceAll("\\.", "?"),
                    jadeSocketPort, 1, 1, true, properties);

            jmdns.registerService(si);
            logger.log(Level.INFO, "JMDNS registerered {0}", si.getQualifiedName());
        } catch (IOException e) {
            logger.log(Level.WARNING, "JMDNS register failed", e);
        }
    }

    /**
     * Unregisters a local JADE agent
     *
     * @param name its name
     */
    public void unregisterJadeAgent(String name) {
        // JMDNS cannot handle dots in the service name. They are being
        // replaced.
        String name0 = name.replaceAll("\\.", "?");
        ServiceInfo si = jmdns.getServiceInfo(JMDNS_TYPE, name0);
        if (si != null) {
            jmdns.unregisterService(si);
            logger.log(Level.INFO, "JMDNS unregisterered {0}", name0);
        } else {
            logger.log(Level.INFO, "JMDNS already unregistered: {0}", name0);
        }
    }

    /**
     * Closes the JMDNSManager and unregisters all JADE agents.
     */
    public void close() {
        try {
            jmdns.close();
            logger.log(Level.INFO, "JMDNS closed");
        } catch (IOException ex) {
            logger.log(Level.WARNING, "Could not close the JMDNS", ex);
        }
    }
}
