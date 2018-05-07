/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Defines values for DnsVerificationTestResult.
 */
public enum DnsVerificationTestResult {
    /** Enum value Passed. */
    PASSED("Passed"),

    /** Enum value Failed. */
    FAILED("Failed"),

    /** Enum value Skipped. */
    SKIPPED("Skipped");

    /** The actual serialized value for a DnsVerificationTestResult instance. */
    private String value;

    DnsVerificationTestResult(String value) {
        this.value = value;
    }

    /**
     * Parses a serialized value to a DnsVerificationTestResult instance.
     *
     * @param value the serialized value to parse.
     * @return the parsed DnsVerificationTestResult object, or null if unable to parse.
     */
    @JsonCreator
    public static DnsVerificationTestResult fromString(String value) {
        DnsVerificationTestResult[] items = DnsVerificationTestResult.values();
        for (DnsVerificationTestResult item : items) {
            if (item.toString().equalsIgnoreCase(value)) {
                return item;
            }
        }
        return null;
    }

    @JsonValue
    @Override
    public String toString() {
        return this.value;
    }
}
