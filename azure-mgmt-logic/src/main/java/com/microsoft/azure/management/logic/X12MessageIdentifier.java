/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The X12 message identifier.
 */
public class X12MessageIdentifier {
    /**
     * The message id.
     */
    @JsonProperty(value = "messageId", required = true)
    private String messageId;

    /**
     * Get the messageId value.
     *
     * @return the messageId value
     */
    public String messageId() {
        return this.messageId;
    }

    /**
     * Set the messageId value.
     *
     * @param messageId the messageId value to set
     * @return the X12MessageIdentifier object itself.
     */
    public X12MessageIdentifier withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

}
