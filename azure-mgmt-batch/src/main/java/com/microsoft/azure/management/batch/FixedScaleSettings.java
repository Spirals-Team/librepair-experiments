/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch;

import org.joda.time.Period;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Fixed scale settings for the pool.
 */
public class FixedScaleSettings {
    /**
     * The timeout for allocation of compute nodes to the pool.
     * The default value is 15 minutes. Timeout values use ISO 8601 format. For
     * example, use PT10M for 10 minutes. The minimum value is 5 minutes. If
     * you specify a value less than 5 minutes, the Batch service rejects the
     * request with an error; if you are calling the REST API directly, the
     * HTTP status code is 400 (Bad Request).
     */
    @JsonProperty(value = "resizeTimeout")
    private Period resizeTimeout;

    /**
     * The desired number of dedicated compute nodes in the pool.
     * At least one of targetDedicatedNodes, targetLowPriority nodes must be
     * set.
     */
    @JsonProperty(value = "targetDedicatedNodes")
    private Integer targetDedicatedNodes;

    /**
     * The desired number of low-priority compute nodes in the pool.
     * At least one of targetDedicatedNodes, targetLowPriority nodes must be
     * set.
     */
    @JsonProperty(value = "targetLowPriorityNodes")
    private Integer targetLowPriorityNodes;

    /**
     * Determines what to do with a node and its running task(s) if the pool
     * size is decreasing.
     * If omitted, the default value is Requeue. Possible values include:
     * 'Requeue', 'Terminate', 'TaskCompletion', 'RetainedData'.
     */
    @JsonProperty(value = "nodeDeallocationOption")
    private ComputeNodeDeallocationOption nodeDeallocationOption;

    /**
     * Get the resizeTimeout value.
     *
     * @return the resizeTimeout value
     */
    public Period resizeTimeout() {
        return this.resizeTimeout;
    }

    /**
     * Set the resizeTimeout value.
     *
     * @param resizeTimeout the resizeTimeout value to set
     * @return the FixedScaleSettings object itself.
     */
    public FixedScaleSettings withResizeTimeout(Period resizeTimeout) {
        this.resizeTimeout = resizeTimeout;
        return this;
    }

    /**
     * Get the targetDedicatedNodes value.
     *
     * @return the targetDedicatedNodes value
     */
    public Integer targetDedicatedNodes() {
        return this.targetDedicatedNodes;
    }

    /**
     * Set the targetDedicatedNodes value.
     *
     * @param targetDedicatedNodes the targetDedicatedNodes value to set
     * @return the FixedScaleSettings object itself.
     */
    public FixedScaleSettings withTargetDedicatedNodes(Integer targetDedicatedNodes) {
        this.targetDedicatedNodes = targetDedicatedNodes;
        return this;
    }

    /**
     * Get the targetLowPriorityNodes value.
     *
     * @return the targetLowPriorityNodes value
     */
    public Integer targetLowPriorityNodes() {
        return this.targetLowPriorityNodes;
    }

    /**
     * Set the targetLowPriorityNodes value.
     *
     * @param targetLowPriorityNodes the targetLowPriorityNodes value to set
     * @return the FixedScaleSettings object itself.
     */
    public FixedScaleSettings withTargetLowPriorityNodes(Integer targetLowPriorityNodes) {
        this.targetLowPriorityNodes = targetLowPriorityNodes;
        return this;
    }

    /**
     * Get the nodeDeallocationOption value.
     *
     * @return the nodeDeallocationOption value
     */
    public ComputeNodeDeallocationOption nodeDeallocationOption() {
        return this.nodeDeallocationOption;
    }

    /**
     * Set the nodeDeallocationOption value.
     *
     * @param nodeDeallocationOption the nodeDeallocationOption value to set
     * @return the FixedScaleSettings object itself.
     */
    public FixedScaleSettings withNodeDeallocationOption(ComputeNodeDeallocationOption nodeDeallocationOption) {
        this.nodeDeallocationOption = nodeDeallocationOption;
        return this;
    }

}
