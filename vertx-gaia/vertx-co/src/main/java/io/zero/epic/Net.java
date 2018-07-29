package io.zero.epic;

import io.zero.epic.fn.Fn;
import io.zero.epic.net.IPHost;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

class Net {

    /**
     * Check whether host:port is ok to connect
     *
     * @param host
     * @param port
     * @return
     */
    static boolean isReach(final String host, final int port) {
        return isReach(host, port, 3000);
    }

    static boolean isReach(final String host, final int port, final Integer timeOut) {
        return Fn.getJvm(() -> {
            // 1.Check whether host is reachalbe
            final Boolean hostOk =
                    Fn.getJvm(() -> InetAddress.getByName(host).isReachable(timeOut), host, timeOut);
            // 2.Check whether host/port could be connected.
            return hostOk ? (Fn.getJvm(Boolean.FALSE, () -> {
                final Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port));
                final boolean reached = socket.isConnected();
                socket.close();
                return reached;
            })) : hostOk;
        }, host, port);
    }

    /**
     * @return
     */
    static String getIPv4() {
        return IPHost.getInstance().getExtranetIPv4Address();
    }

    static String getHostName() {
        return Fn.getJvm(() -> (InetAddress.getLocalHost()).getHostName(), true);
    }

    /**
     * @return
     */
    static String getIPv6() {
        return IPHost.getInstance().getExtranetIPv6Address();
    }

    /**
     * @return
     */
    static String getIP() {
        return IPHost.getInstance().getExtranetIPAddress();
    }

    static String netUri(final String url) {
        if (null == url) {
            return null;
        }
        if (url.contains("?")) {
            return url.split("\\?")[0];
        } else {
            return url;
        }
    }
}
