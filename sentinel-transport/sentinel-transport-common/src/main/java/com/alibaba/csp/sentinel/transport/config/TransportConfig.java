/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.transport.config;

import com.alibaba.csp.sentinel.config.SentinelConfig;

/**
 * @author leyou
 */
public class TransportConfig {

    public static final String CONSOLE_SERVER = "csp.sentinel.dashboard.server";
    public static final String SERVER_PORT = "csp.sentinel.api.port";
    public static final String HEARTBEAT_INTERVAL_MS = "csp.sentinel.heartbeat.interval.ms";

    private static int runtimePort = -1;

    public static Long getHeartbeatIntervalMs() {
        String interval = SentinelConfig.getConfig(HEARTBEAT_INTERVAL_MS);
        return interval == null ? null : Long.parseLong(interval);
    }

    /**
     * Get ip:port of Sentinel Dashboard.
     *
     * @return console server ip:port, maybe null if not configured
     */
    public static String getConsoleServer() {
        return SentinelConfig.getConfig(CONSOLE_SERVER);
    }

    public static int getRuntimePort() {
        return runtimePort;
    }

    /**
     * Get Server port of this HTTP server.
     *
     * @return the port, maybe null if not configured.
     */
    public static String getPort() {
        if (runtimePort > 0) {
            return String.valueOf(runtimePort);
        }
        return SentinelConfig.getConfig(SERVER_PORT);
    }

    /**
     * Set real port this HTTP server uses.
     *
     * @param port real port.
     */
    public static void setRuntimePort(int port) {
        runtimePort = port;
    }
}
