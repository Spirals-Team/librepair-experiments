/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.mqtt.examples;

import java.util.Random;

import org.apache.activemq.broker.BrokerService;
import org.apache.storm.mqtt.MqttLogger;
import org.fusesource.mqtt.client.BlockingConnection;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A MQTT example using a Storm topology.
 * @author richter
 */
public final class MqttBrokerPublisher {
    /**
     * The logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(
            MqttBrokerPublisher.class);
    /**
     * The broker.
     */
    private static BrokerService broker;
    /**
     * The connection reference.
     */
    private static BlockingConnection connection;
    /**
     * The maximum temperature.
     */
    private static final int TEMPERATUR_MAX = 100;
    /**
     * The maximum humidity.
     */
    private static final int HUMIDITY_MAX = 100;
    /**
     * The default wait in milliseconds.
     */
    private static final int WAIT_MILLIS_DEFAULT = 500;

    /**
     * Initializes {@code broker} and starts it.
     * @throws Exception if an exception during adding a connector occurs
     */
    public static void startBroker() throws Exception {
        LOG.info("Starting broker...");
        broker = new BrokerService();
        broker.addConnector("mqtt://localhost:1883");
        broker.setDataDirectory("target");
        broker.start();
        LOG.info("MQTT broker started");
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LOG.info("Shutting down MQTT broker...");
                    broker.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Initializes {@code connection}.
     * @throws Exception if an exception during connecting to connector occurs
     */
    public static void startPublisher() throws Exception {
        MQTT client = new MQTT();
        client.setTracer(new MqttLogger());
        client.setHost("tcp://localhost:1883");
        client.setClientId("MqttBrokerPublisher");
        connection = client.blockingConnection();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    LOG.info("Shutting down MQTT client...");
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        connection.connect();
    }

    /**
     * Publishes topics on connection.
     * @throws Exception if an exception during publishing occurs
     */
    public static void publish() throws Exception {
        String topic = "/users/tgoetz/office/1234";
        Random rand = new Random();
        LOG.info("Publishing to topic {}", topic);
        LOG.info("Cntrl+C to exit.");

        while (true) {
            int temp = rand.nextInt(TEMPERATUR_MAX);
            int hum = rand.nextInt(HUMIDITY_MAX);
            String payload = temp + "/" + hum;

            connection.publish(topic,
                    payload.getBytes(),
                    QoS.AT_LEAST_ONCE,
                    false);
            Thread.sleep(WAIT_MILLIS_DEFAULT);
        }
    }

    /**
     * The main method.
     * @param args the command line arguments
     * @throws Exception if an exception during connections or transmission
     *     occurs
     */
    public static void main(final String[] args) throws Exception {
        startBroker();
        startPublisher();
        publish();
    }

    /**
     * Utility constructor.
     */
    private MqttBrokerPublisher() {
    }
}
