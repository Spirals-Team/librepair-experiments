/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import org.joda.time.DateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Data Lake Analytics job execution statistics.
 */
public class JobStatistics {
    /**
     * the last update time for the statistics.
     */
    @JsonProperty(value = "lastUpdateTimeUtc", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime lastUpdateTimeUtc;

    /**
     * the job finalizing start time.
     */
    @JsonProperty(value = "finalizingTimeUtc", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime finalizingTimeUtc;

    /**
     * the list of stages for the job.
     */
    @JsonProperty(value = "stages", access = JsonProperty.Access.WRITE_ONLY)
    private List<JobStatisticsVertexStage> stages;

    /**
     * Get the lastUpdateTimeUtc value.
     *
     * @return the lastUpdateTimeUtc value
     */
    public DateTime lastUpdateTimeUtc() {
        return this.lastUpdateTimeUtc;
    }

    /**
     * Get the finalizingTimeUtc value.
     *
     * @return the finalizingTimeUtc value
     */
    public DateTime finalizingTimeUtc() {
        return this.finalizingTimeUtc;
    }

    /**
     * Get the stages value.
     *
     * @return the stages value
     */
    public List<JobStatisticsVertexStage> stages() {
        return this.stages;
    }

}
