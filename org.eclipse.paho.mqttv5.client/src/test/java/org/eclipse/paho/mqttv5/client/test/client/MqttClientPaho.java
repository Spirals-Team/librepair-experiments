/*******************************************************************************
 * Copyright (c) 2009, 2014 IBM Corp.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v1.0 which accompany this distribution. 
 *
 * The Eclipse Public License is available at 
 *    http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 *   http://www.eclipse.org/org/documents/edl-v10.php.
 *
 *******************************************************************************/

package org.eclipse.paho.mqttv5.client.test.client;

import org.eclipse.paho.mqttv5.client.MqttClientPersistence;
import org.eclipse.paho.mqttv5.client.MqttLegacyBlockingClient;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;


/**
 *
 */
public class MqttClientPaho extends MqttLegacyBlockingClient {

  /**
   * @param serverURI
   * @param clientId
   * @throws MqttException
   */
  public MqttClientPaho(String serverURI, String clientId) throws MqttException {
    super(serverURI, clientId, new MemoryPersistence());
  }

  /**
   * @param serverURI
   * @param clientId
   * @param persistence
   * @throws MqttException 
   */
  public MqttClientPaho(String serverURI, String clientId, MqttClientPersistence persistence) throws MqttException {
    super(serverURI, clientId, persistence);
  }

  /**
   * @throws Exception 
   */
  public void startTrace() throws Exception {
    // not implemented
  }

  /**
   * @throws Exception 
   */
  public void stopTrace() throws Exception {
    // not implemented
  }

  /**
   * @return trace buffer
   * @throws Exception 
   */
  public String getTraceLog() throws Exception {
    return null;
  }
}
