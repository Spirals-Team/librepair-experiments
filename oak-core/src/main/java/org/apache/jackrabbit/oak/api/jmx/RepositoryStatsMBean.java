/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.jackrabbit.oak.api.jmx;

import javax.management.openmbean.CompositeData;

/**
 * MBean for providing repository wide statistics.
 * This MBean exposes the time series provided by
 * {@link org.apache.jackrabbit.api.stats.RepositoryStatistics RepositoryStatistics}
 * through JMX as {@code CompositeData} of arrays.
 */
public interface RepositoryStatsMBean {
    String TYPE = "RepositoryStats";

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_COUNT
     */
    CompositeData getSessionCount();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_LOGIN_COUNTER
     */
    CompositeData getSessionLogin();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_READ_COUNTER
     */
    CompositeData getSessionReadCount();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_READ_DURATION
     */
    CompositeData getSessionReadDuration();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_READ_AVERAGE
     */
    CompositeData getSessionReadAverage();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_WRITE_COUNTER
     */
    CompositeData getSessionWriteCount();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_READ_DURATION
     */
    CompositeData getSessionWriteDuration();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#SESSION_WRITE_AVERAGE
     */
    CompositeData getSessionWriteAverage();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#QUERY_COUNT
     */
    CompositeData getQueryCount();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#QUERY_DURATION
     */
    CompositeData getQueryDuration();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#QUERY_AVERAGE
     */
    CompositeData getQueryAverage();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#OBSERVATION_EVENT_COUNTER
     */
    CompositeData getObservationEventCount();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#OBSERVATION_EVENT_DURATION
     */
    CompositeData getObservationEventDuration();

    /**
     * @see org.apache.jackrabbit.api.stats.RepositoryStatistics.Type#OBSERVATION_EVENT_AVERAGE
     */
    CompositeData getObservationEventAverage();

    /**
     * Maximum length of observation queue in the respective time period.
     */
    CompositeData getObservationQueueMaxLength();
}
