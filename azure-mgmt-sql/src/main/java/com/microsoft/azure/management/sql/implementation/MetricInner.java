/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.sql.implementation;

import org.joda.time.DateTime;
import com.microsoft.azure.management.sql.UnitType;
import com.microsoft.azure.management.sql.MetricName;
import java.util.List;
import com.microsoft.azure.management.sql.MetricValue;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Database metrics.
 */
public class MetricInner {
    /**
     * The start time for the metric (ISO-8601 format).
     */
    @JsonProperty(value = "startTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime startTime;

    /**
     * The end time for the metric (ISO-8601 format).
     */
    @JsonProperty(value = "endTime", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime endTime;

    /**
     * The time step to be used to summarize the metric values.
     */
    @JsonProperty(value = "timeGrain", access = JsonProperty.Access.WRITE_ONLY)
    private String timeGrain;

    /**
     * The unit of the metric. Possible values include: 'count', 'bytes',
     * 'seconds', 'percent', 'countPerSecond', 'bytesPerSecond'.
     */
    @JsonProperty(value = "unit", access = JsonProperty.Access.WRITE_ONLY)
    private UnitType unit;

    /**
     * The name information for the metric.
     */
    @JsonProperty(value = "name", access = JsonProperty.Access.WRITE_ONLY)
    private MetricName name;

    /**
     * The metric values for the specified time window and timestep.
     */
    @JsonProperty(value = "metricValues", access = JsonProperty.Access.WRITE_ONLY)
    private List<MetricValue> metricValues;

    /**
     * Get the startTime value.
     *
     * @return the startTime value
     */
    public DateTime startTime() {
        return this.startTime;
    }

    /**
     * Get the endTime value.
     *
     * @return the endTime value
     */
    public DateTime endTime() {
        return this.endTime;
    }

    /**
     * Get the timeGrain value.
     *
     * @return the timeGrain value
     */
    public String timeGrain() {
        return this.timeGrain;
    }

    /**
     * Get the unit value.
     *
     * @return the unit value
     */
    public UnitType unit() {
        return this.unit;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public MetricName name() {
        return this.name;
    }

    /**
     * Get the metricValues value.
     *
     * @return the metricValues value
     */
    public List<MetricValue> metricValues() {
        return this.metricValues;
    }

}
