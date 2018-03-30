/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for PcStatus.
 */
public final class PcStatus {
    /** Static value NotStarted for PcStatus. */
    public static final PcStatus NOT_STARTED = new PcStatus("NotStarted");

    /** Static value Running for PcStatus. */
    public static final PcStatus RUNNING = new PcStatus("Running");

    /** Static value Stopped for PcStatus. */
    public static final PcStatus STOPPED = new PcStatus("Stopped");

    /** Static value Error for PcStatus. */
    public static final PcStatus ERROR = new PcStatus("Error");

    /** Static value Unknown for PcStatus. */
    public static final PcStatus UNKNOWN = new PcStatus("Unknown");

    private String value;

    /**
     * Creates a custom value for PcStatus.
     * @param value the custom value
     */
    public PcStatus(String value) {
        this.value = value;
    }

    @JsonValue
    @Override
    public String toString() {
        return value;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PcStatus)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        PcStatus rhs = (PcStatus) obj;
        if (value == null) {
            return rhs.value == null;
        } else {
            return value.equals(rhs.value);
        }
    }
}
