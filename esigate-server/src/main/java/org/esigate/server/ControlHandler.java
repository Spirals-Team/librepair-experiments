/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.esigate.server;

import java.io.IOException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * Handle commands to the control port. Work in progress.
 * 
 * <p>
 * Commands
 * <ul>
 * <li>POST /shutdown</li>
 * <li>POST /status</li>
 * </ul>
 * 
 * @author Nicolas Richeton
 * 
 */
public class ControlHandler extends AbstractHandler {
    private static final String PREFIX_CONTEXT = "org.eclipse.jetty.webapp.WebAppContext.main.";
    private static final String PREFIX_THREAD_POOL = "org.eclipse.jetty.util.thread.QueuedThreadPool.esigate.";
    /**
     * Human-readable status
     */
    private static final String URL_STATUS = "/server-status";
    /**
     * Machine-readable status.
     * 
     * <p>
     * Sample :
     * 
     * <pre>
     * Total Accesses: 157678
     * Total kBytes: 176421
     * CPULoad: .0190435
     * Uptime: 2214828
     * ReqPerSec: .071192
     * BytesPerSec: 81.5662
     * BytesPerReq: 1145.72
     * BusyWorkers: 1
     * IdleWorkers: 4
     * </pre>
     */
    private final MetricRegistry registry;

    /**
     * Control handler for administration tasks.
     * 
     * @param registry
     *            metrics registry.
     */
    public ControlHandler(MetricRegistry registry) {
        this.registry = registry;
    }

    private static boolean fromControlConnection(Request serverRequest) {
        return EsigateServer.getControlPort() == serverRequest.getLocalPort();
    }

    /**
     * Perform shutdown.
     * 
     * @param port
     *            control handler port.
     */
    public static void shutdown(int port) {
        Http.doPOST("http://127.0.0.1:" + port + "/shutdown");
    }

    /**
     * Display status.
     * 
     * @param port
     *            control handler port.
     */
    public static void status(int port) {
        Http.doGET("http://127.0.0.1:" + port + URL_STATUS);
    }

    @Override
    public void handle(String target, Request serverRequest, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        if (fromControlConnection(serverRequest)) {
            serverRequest.setHandled(true);

            switch (target) {

            case "/shutdown":
                if ("POST".equals(serverRequest.getMethod())) {
                    response.setStatus(HttpServletResponse.SC_OK);
                    stopServer();
                }
                break;

            case URL_STATUS:
                if ("GET".equals(serverRequest.getMethod())) {

                    if (request.getParameter("auto") != null) {
                        response.setStatus(HttpServletResponse.SC_OK);
                        try (Writer sos = response.getWriter()) {
                            Map<String, Object> status = getServerStatus();
                            for (String key : status.keySet()) {
                                sos.append(key).append(": ").append(String.valueOf(status.get(key))).append("\n");
                            }
                        }

                    } else {
                        response.setStatus(HttpServletResponse.SC_OK);
                        try (Writer sos = response.getWriter()) {
                            sos.append("Esigate Server Status\n");
                            Map<String, Object> status = getServerStatus();
                            for (String key : status.keySet()) {
                                sos.append(key).append(": ").append(String.valueOf(status.get(key))).append("\n");
                            }
                        }
                    }

                }
                break;

            default:
                response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                break;
            }

        }

    }

    private Map<String, Object> getServerStatus() {
        Map<String, Object> result = new TreeMap<>();

        Map<String, Counter> counters = this.registry.getCounters();
        for (Entry<String, Counter> c : counters.entrySet()) {
            result.put(cleanupStatusKey(c.getKey()), String.valueOf(c.getValue().getCount()));
        }

        Map<String, Meter> meters = this.registry.getMeters();
        for (Entry<String, Meter> c : meters.entrySet()) {
            result.put(cleanupStatusKey(c.getKey()), String.valueOf(c.getValue().getCount()));
            result.put(cleanupStatusKey(c.getKey()) + "PerSec", String.valueOf(c.getValue().getOneMinuteRate()));
        }

        Map<String, Gauge> gauges = this.registry.getGauges();
        for (Entry<String, Gauge> c : gauges.entrySet()) {
            result.put(cleanupStatusKey(c.getKey()), String.valueOf(c.getValue().getValue()));
        }

        Map<String, Timer> timers = this.registry.getTimers();
        for (Entry<String, Timer> c : timers.entrySet()) {
            result.put(cleanupStatusKey(c.getKey()), String.valueOf(c.getValue().getOneMinuteRate()));
        }

        // Get total accesses
        Long accesses =
                meters.get(PREFIX_CONTEXT + "1xx-responses").getCount()
                        + meters.get(PREFIX_CONTEXT + "2xx-responses").getCount()
                        + meters.get(PREFIX_CONTEXT + "3xx-responses").getCount()
                        + meters.get(PREFIX_CONTEXT + "4xx-responses").getCount()
                        + meters.get(PREFIX_CONTEXT + "5xx-responses").getCount();
        result.put("Total Accesses", accesses);

        // Get ReqPerSec
        Double reqPerSec =
                meters.get(PREFIX_CONTEXT + "1xx-responses").getOneMinuteRate()
                        + meters.get(PREFIX_CONTEXT + "2xx-responses").getOneMinuteRate()
                        + meters.get(PREFIX_CONTEXT + "3xx-responses").getOneMinuteRate()
                        + meters.get(PREFIX_CONTEXT + "4xx-responses").getOneMinuteRate()
                        + meters.get(PREFIX_CONTEXT + "5xx-responses").getOneMinuteRate();
        result.put("ReqPerSec", reqPerSec);

        // Get uptime
        result.put("Uptime", ManagementFactory.getRuntimeMXBean().getUptime());

        // Get CPULoad
        Double cpuLoad = ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
        if (cpuLoad >= 0d) {
            result.put("CPULoad", cpuLoad);
        }

        return result;

    }

    /**
     * Remove unnecessary prefix from Metrics meters id.
     * 
     * @param s
     * @return
     */
    private static String cleanupStatusKey(String s) {
        String result = s;
        if (s.startsWith(PREFIX_CONTEXT)) {
            result = s.substring(PREFIX_CONTEXT.length());

        }

        if (s.startsWith(PREFIX_THREAD_POOL)) {
            result = s.substring(PREFIX_THREAD_POOL.length());
        }

        return result;
    }

    /**
     * Start a new thread to shutdown the server
     */
    private void stopServer() {
        // Get current server
        final Server targetServer = this.getServer();

        // Start a new thread in order to escape the destruction of this Handler
        // during the stop process.
        new Thread() {
            @Override
            public void run() {
                try {
                    targetServer.stop();
                } catch (Exception e) {
                    // ignore
                }
            }
        }.start();

    }
}
