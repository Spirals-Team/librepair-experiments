/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.monitor;

import com.microsoft.azure.management.monitor.implementation.LocalizableStringInner;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The result data of a query.
 */
public class Metric {
    /**
     * the metric Id.
     */
    @JsonProperty(value = "id", required = true)
    private String id;

    /**
     * the resource type of the metric resource.
     */
    @JsonProperty(value = "type", required = true)
    private String type;

    /**
     * the name and the display name of the metric, i.e. it is localizable
     * string.
     */
    @JsonProperty(value = "name", required = true)
    private LocalizableStringInner name;

    /**
     * the unit of the metric. Possible values include: 'Count', 'Bytes',
     * 'Seconds', 'CountPerSecond', 'BytesPerSecond', 'Percent',
     * 'MilliSeconds', 'ByteSeconds', 'Unspecified'.
     */
    @JsonProperty(value = "unit", required = true)
    private Unit unit;

    /**
     * the time series returned when a data query is performed.
     */
    @JsonProperty(value = "timeseries", required = true)
    private List<TimeSeriesElement> timeseries;

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Set the id value.
     *
     * @param id the id value to set
     * @return the Metric object itself.
     */
    public Metric withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public String type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the Metric object itself.
     */
    public Metric withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the name value.
     *
     * @return the name value
     */
    public LocalizableStringInner name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the Metric object itself.
     */
    public Metric withName(LocalizableStringInner name) {
        this.name = name;
        return this;
    }

    /**
     * Get the unit value.
     *
     * @return the unit value
     */
    public Unit unit() {
        return this.unit;
    }

    /**
     * Set the unit value.
     *
     * @param unit the unit value to set
     * @return the Metric object itself.
     */
    public Metric withUnit(Unit unit) {
        this.unit = unit;
        return this;
    }

    /**
     * Get the timeseries value.
     *
     * @return the timeseries value
     */
    public List<TimeSeriesElement> timeseries() {
        return this.timeseries;
    }

    /**
     * Set the timeseries value.
     *
     * @param timeseries the timeseries value to set
     * @return the Metric object itself.
     */
    public Metric withTimeseries(List<TimeSeriesElement> timeseries) {
        this.timeseries = timeseries;
        return this;
    }

}
