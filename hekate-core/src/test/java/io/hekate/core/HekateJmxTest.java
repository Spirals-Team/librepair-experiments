/*
 * Copyright 2018 The Hekate Project
 *
 * The Hekate Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.hekate.core;

import io.hekate.HekateNodeTestBase;
import io.hekate.core.internal.HekateTestNode;
import io.hekate.core.jmx.JmxService;
import io.hekate.core.jmx.JmxServiceFactory;
import javax.management.ObjectName;
import org.junit.Assert;
import org.junit.Test;

import static io.hekate.core.jmx.JmxTestUtils.jmxAttribute;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HekateJmxTest extends HekateNodeTestBase {
    @Test
    public void test() throws Exception {
        HekateTestNode node = createNode(boot -> boot.withService(JmxServiceFactory.class)).join();

        // Sleep for a while to give some to initialize time-related JMX attributes (f.e. UpTime).
        sleep(100);

        ObjectName name = node.get(JmxService.class).nameFor(HekateJmx.class);

        Assert.assertEquals(HekateVersion.fullVersion(), jmxAttribute(name, "Version", node));
        assertEquals(node.cluster().clusterName(), jmxAttribute(name, "ClusterName", node));
        assertEquals(node.localNode().name(), jmxAttribute(name, "NodeName", node));
        assertEquals(node.localNode().id().toString(), jmxAttribute(name, "NodeId", node));
        assertEquals(node.localNode().address().socket().getHostString(), jmxAttribute(name, "Host", node));
        assertEquals((Integer)node.localNode().address().socket().getPort(), jmxAttribute(name, "Port", node));
        assertEquals(node.localNode().address().socket().toString(), jmxAttribute(name, "SocketAddress", node));
        Assert.assertEquals(Hekate.State.UP.name(), jmxAttribute(name, "State", node));
        assertTrue((Long)jmxAttribute(name, "UpTimeMillis", node) > 0);
        assertTrue(jmxAttribute(name, "UpTime", node).toString().startsWith("PT"));
    }
}
