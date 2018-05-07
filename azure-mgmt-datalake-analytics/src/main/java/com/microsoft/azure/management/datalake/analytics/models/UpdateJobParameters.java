/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * The parameters that can be used to update existing Data Lake Analytics job
 * information properties. (Only for use internally with Scope job type.).
 */
public class UpdateJobParameters {
    /**
     * the degree of parallelism used for this job. This must be greater than
     * 0, if set to less than 0 it will default to 1.
     */
    @JsonProperty(value = "degreeOfParallelism")
    private Integer degreeOfParallelism;

    /**
     * the priority value for the current job. Lower numbers have a higher
     * priority. By default, a job has a priority of 1000. This must be greater
     * than 0.
     */
    @JsonProperty(value = "priority")
    private Integer priority;

    /**
     * the key-value pairs used to add additional metadata to the job
     * information. (Only for use internally with Scope job type.).
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * Get the degreeOfParallelism value.
     *
     * @return the degreeOfParallelism value
     */
    public Integer degreeOfParallelism() {
        return this.degreeOfParallelism;
    }

    /**
     * Set the degreeOfParallelism value.
     *
     * @param degreeOfParallelism the degreeOfParallelism value to set
     * @return the UpdateJobParameters object itself.
     */
    public UpdateJobParameters withDegreeOfParallelism(Integer degreeOfParallelism) {
        this.degreeOfParallelism = degreeOfParallelism;
        return this;
    }

    /**
     * Get the priority value.
     *
     * @return the priority value
     */
    public Integer priority() {
        return this.priority;
    }

    /**
     * Set the priority value.
     *
     * @param priority the priority value to set
     * @return the UpdateJobParameters object itself.
     */
    public UpdateJobParameters withPriority(Integer priority) {
        this.priority = priority;
        return this;
    }

    /**
     * Get the tags value.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set the tags value.
     *
     * @param tags the tags value to set
     * @return the UpdateJobParameters object itself.
     */
    public UpdateJobParameters withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

}
