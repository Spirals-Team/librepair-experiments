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
package org.apache.dubbo.remoting.zookeeper.zkclient;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.remoting.zookeeper.ZookeeperClient;
import org.apache.curator.test.TestingServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class ZkclientZookeeperTransporterTest {
    private TestingServer zkServer;
    private ZookeeperClient zookeeperClient;

    @Before
    public void setUp() throws Exception {
        int zkServerPort = NetUtils.getAvailablePort();
        zkServer = new TestingServer(zkServerPort, true);
        zookeeperClient = new ZkclientZookeeperTransporter().connect(URL.valueOf("zookeeper://127.0.0.1:" +
                zkServerPort + "/service"));
    }

    @Test
    public void testZookeeperClient() {
        assertThat(zookeeperClient, not(nullValue()));
        zookeeperClient.close();
    }

    @After
    public void tearDown() throws Exception {
        zkServer.stop();
    }
}