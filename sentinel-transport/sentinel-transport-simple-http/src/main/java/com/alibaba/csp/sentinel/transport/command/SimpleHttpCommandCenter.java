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
package com.alibaba.csp.sentinel.transport.command;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.csp.sentinel.command.CommandHandler;
import com.alibaba.csp.sentinel.command.CommandHandlerProvider;
import com.alibaba.csp.sentinel.concurrent.NamedThreadFactory;
import com.alibaba.csp.sentinel.log.CommandCenterLog;
import com.alibaba.csp.sentinel.transport.CommandCenter;
import com.alibaba.csp.sentinel.transport.command.http.HttpEventTask;
import com.alibaba.csp.sentinel.transport.config.TransportConfig;
import com.alibaba.csp.sentinel.util.StringUtil;

/***
 * The simple command center provides service to exchange information.
 *
 * @author youji.zj
 */
public class SimpleHttpCommandCenter implements CommandCenter {

    private static final int PORT_UNINITIALIZED = -1;

    private static final int DEFAULT_SERVER_SO_TIMEOUT = 3000;
    private static final int DEFAULT_PORT = 8719;

    private static final Map<String, CommandHandler> handlerMap = new HashMap<String, CommandHandler>();

    private ExecutorService executor = Executors.newSingleThreadExecutor(
        new NamedThreadFactory("sentinel-command-center-executor"));
    private ExecutorService bizExecutor;

    private ServerSocket socketReference;

    @Override
    public void beforeStart() throws Exception {
        // Register handlers
        Map<String, CommandHandler> handlers = CommandHandlerProvider.getInstance().namedHandlers();
        registerCommands(handlers);
    }

    @Override
    public void start() throws Exception {
        int nThreads = Runtime.getRuntime().availableProcessors();
        this.bizExecutor = new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(10),
            new NamedThreadFactory("sentinel-command-center-service-executor"),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    CommandCenterLog.info("EventTask rejected");
                    throw new RejectedExecutionException();
                }
            });

        Runnable serverInitTask = new Runnable() {
            int port;

            {
                try {
                    port = Integer.parseInt(TransportConfig.getPort());
                } catch (Exception e) {
                    port = DEFAULT_PORT;
                }
            }

            @Override
            public void run() {
                int repeat = 0;

                int tmpPort = port;
                boolean success = false;
                while (true) {
                    ServerSocket serverSocket = null;
                    try {
                        serverSocket = new ServerSocket(tmpPort);
                    } catch (IOException ex) {
                        CommandCenterLog.info(
                            String.format("IO error occurs, port: %d, repeat times: %d", tmpPort, repeat), ex);

                        tmpPort = adjustPort(repeat);
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e1) {
                            break;
                        }
                        repeat++;
                    }

                    if (serverSocket != null) {
                        CommandCenterLog.info("[CommandCenter] Begin listening at port " + serverSocket.getLocalPort());
                        socketReference = serverSocket;
                        executor.submit(new ServerThread(serverSocket));
                        success = true;
                        break;
                    }
                }

                if (!success) {
                    tmpPort = PORT_UNINITIALIZED;
                }
                TransportConfig.setRuntimePort(tmpPort);
                executor.shutdown();
            }

            /**
             * Adjust the port to settle down.
             */
            private int adjustPort(int repeat) {
                int mod = repeat / 5;
                return port + mod;
            }
        };

        new Thread(serverInitTask).start();
    }

    @Override
    public void stop() throws Exception {
        if (socketReference != null) {
            try {
                socketReference.close();
            } catch (IOException e) {
                CommandCenterLog.warn("Error when releasing the server socket", e);
            }
        }
        bizExecutor.shutdownNow();
        executor.shutdownNow();
        TransportConfig.setRuntimePort(PORT_UNINITIALIZED);
        handlerMap.clear();
    }

    /**
     * Get the name set of all registered commands.
     */
    public static Set<String> getCommands() {
        return handlerMap.keySet();
    }

    class ServerThread extends Thread {

        private ServerSocket serverSocket;

        ServerThread(ServerSocket s) {
            this.serverSocket = s;
            setName("sentinel-courier-server-accept-thread");
        }

        @Override
        public void run() {
            while (true) {
                Socket socket = null;
                try {
                    socket = this.serverSocket.accept();
                    setSocketSoTimeout(socket);
                    HttpEventTask eventTask = new HttpEventTask(socket);
                    bizExecutor.submit(eventTask);
                } catch (Exception e) {
                    CommandCenterLog.info("Server error", e);
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (Exception e1) {
                            CommandCenterLog.info("Error when closing an opened socket", e1);
                        }
                    }
                    try {
                        // In case of infinite log.
                        Thread.sleep(10);
                    } catch (InterruptedException e1) {
                        // Indicates the task should stop.
                        break;
                    }
                }
            }
        }
    }

    public static CommandHandler getHandler(String commandName) {
        return handlerMap.get(commandName);
    }

    public static void registerCommands(Map<String, CommandHandler> handlerMap) {
        if (handlerMap != null) {
            for (Entry<String, CommandHandler> e : handlerMap.entrySet()) {
                registerCommand(e.getKey(), e.getValue());
            }
        }
    }

    public static void registerCommand(String commandName, CommandHandler handler) {
        if (StringUtil.isEmpty(commandName)) {
            return;
        }

        if (handlerMap.containsKey(commandName)) {
            CommandCenterLog.info("Register failed (duplicate command): " + commandName);
            return;
        }

        handlerMap.put(commandName, handler);
    }

    /**
     * Avoid server thread hang, 3 seconds timeout by default.
     */
    private void setSocketSoTimeout(Socket socket) throws SocketException {
        if (socket != null) {
            socket.setSoTimeout(DEFAULT_SERVER_SO_TIMEOUT);
        }
    }
}
