/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.remoting.transport.netty;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.utils.DubboAppender;
import com.alibaba.dubbo.common.utils.LogUtil;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.Client;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.Server;
import com.alibaba.dubbo.remoting.exchange.Exchangers;
import com.alibaba.dubbo.remoting.exchange.support.ExchangeHandlerAdapter;

import org.apache.log4j.Level;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Client reconnect test
 */
public class ClientReconnectTest {
    public static void main(String[] args) {
        System.out.println(3 % 1);
    }

    @Before
    public void clear() {
        DubboAppender.clear();
    }

    @Test
    public void testReconnect() throws RemotingException, InterruptedException {
        {
            int port = NetUtils.getAvailablePort();
            Client client = startClient(port, 200);
            Assert.assertEquals(false, client.isConnected());
            Server server = startServer(port);
            for (int i = 0; i < 100 && !client.isConnected(); i++) {
                Thread.sleep(10);
            }
            Assert.assertEquals(true, client.isConnected());
            client.close(2000);
            server.close(2000);
        }
        {
            int port = NetUtils.getAvailablePort();
            Client client = startClient(port, 20000);
            Assert.assertEquals(false, client.isConnected());
            Server server = startServer(port);
            for (int i = 0; i < 5; i++) {
                Thread.sleep(200);
            }
            Assert.assertEquals(false, client.isConnected());
            client.close(2000);
            server.close(2000);
        }
    }

    /**
     * Reconnect log check, when the time is not enough for shutdown time, there is no error log, but there must be a warn log
     */
    @Test
    public void testReconnectWarnLog() throws RemotingException, InterruptedException {
        int port = NetUtils.getAvailablePort();
        DubboAppender.doStart();
        String url = "exchange://127.0.0.2:" + port + "/client.reconnect.test?check=false&"
                + Constants.RECONNECT_KEY + "=" + 1; //1ms reconnect, ensure that there is enough frequency to reconnect
        try {
            Exchangers.connect(url);
        } catch (Exception e) {
            //do nothing
        }
        Thread.sleep(1500);
        //Time is not long enough to produce a error log
        Assert.assertEquals("no error message ", 0, LogUtil.findMessage(Level.ERROR, "client reconnect to "));
        //The first reconnection failed to have a warn log
        Assert.assertEquals("must have one warn message ", 1, LogUtil.findMessage(Level.WARN, "client reconnect to "));
        DubboAppender.doStop();
    }

    public Client startClient(int port, int reconnectPeriod) throws RemotingException {
        final String url = "exchange://127.0.0.1:" + port + "/client.reconnect.test?check=false&" + Constants.RECONNECT_KEY + "=" + reconnectPeriod;
        return Exchangers.connect(url);
    }

    public Server startServer(int port) throws RemotingException {
        final String url = "exchange://127.0.0.1:" + port + "/client.reconnect.test";
        return Exchangers.bind(url, new HandlerAdapter());
    }

    static class HandlerAdapter extends ExchangeHandlerAdapter {
        @Override
        public void connected(Channel channel) throws RemotingException {
        }

        @Override
        public void disconnected(Channel channel) throws RemotingException {
        }

        @Override
        public void caught(Channel channel, Throwable exception) throws RemotingException {
        }
    }
}
