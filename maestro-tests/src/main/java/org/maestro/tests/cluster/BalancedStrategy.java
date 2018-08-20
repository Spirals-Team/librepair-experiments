/*
 * Copyright 2018 Otavio R. Piske <angusyoung@gmail.com>
 *
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

package org.maestro.tests.cluster;

import org.maestro.client.Maestro;
import org.maestro.client.exchange.MaestroTopics;
import org.maestro.client.exchange.support.PeerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class BalancedStrategy implements DistributionStrategy {
    private static final Logger logger = LoggerFactory.getLogger(BalancedStrategy.class);

    private final Maestro maestro;
    private int counter = 0;

    public BalancedStrategy(final Maestro maestro) {
        this.maestro = maestro;
    }

    private void assign(final String id, final PeerInfo peerInfo) {
        final String peerTopic = MaestroTopics.peerTopic(id);

        if (counter % 2 == 0) {
            logger.info("Assigning node {}@{} as {}", peerInfo.peerName(), peerInfo.peerHost(),  "receiver");
        }
        else {
            logger.info("Assigning node {}@{} as {}", peerInfo.peerName(), peerInfo.peerHost(),  "sender");
        }
        counter++;
    }

    @Override
    public void distribute(final Map<String, PeerInfo> knownPeers) {
        knownPeers.forEach(this::assign);
    }
}
