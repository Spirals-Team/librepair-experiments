/*******************************************************************************
 * Copyright (c) 2016, 2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/

package org.eclipse.hono.adapter.mqtt;

import org.eclipse.hono.service.metric.Metrics;

/**
 * Metrics for the MQTT adapter.
 */
public interface MqttAdapterMetrics extends Metrics {

    /**
     * Increments the number of MQTT connections that have been established
     * with an authenticated device by one.
     * 
     * @param tenantId The tenant that the device belongs to.
     */
    void incrementMqttConnections(String tenantId);

    /**
     * Decrements the number of MQTT connections that have been established
     * with an authenticated device by one.
     * 
     * @param tenantId The tenant that the device belongs to.
     */
    void decrementMqttConnections(String tenantId);

    /**
     * Increments the number of MQTT connections that have been established
     * with an <em>unauthenticated</em> device by one.
     */
    void incrementUnauthenticatedMqttConnections();

    /**
     * Decrements the number of MQTT connections that have been established
     * with an <em>unauthenticated</em> device by one.
     */
    void decrementUnauthenticatedMqttConnections();
}
