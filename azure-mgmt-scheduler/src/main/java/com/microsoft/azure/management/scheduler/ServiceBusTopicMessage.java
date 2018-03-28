/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.scheduler;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The ServiceBusTopicMessage model.
 */
public class ServiceBusTopicMessage extends ServiceBusMessage {
    /**
     * Gets or sets the topic path.
     */
    @JsonProperty(value = "topicPath")
    private String topicPath;

    /**
     * Get the topicPath value.
     *
     * @return the topicPath value
     */
    public String topicPath() {
        return this.topicPath;
    }

    /**
     * Set the topicPath value.
     *
     * @param topicPath the topicPath value to set
     * @return the ServiceBusTopicMessage object itself.
     */
    public ServiceBusTopicMessage withTopicPath(String topicPath) {
        this.topicPath = topicPath;
        return this;
    }

}
