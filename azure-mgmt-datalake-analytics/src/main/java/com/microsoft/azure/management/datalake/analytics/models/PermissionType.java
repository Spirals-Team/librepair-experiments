/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

import java.util.Collection;

/**
 * Defines values for PermissionType.
 */
public final class PermissionType extends ExpandableStringEnum<PermissionType> {
    /** Static value None for PermissionType. */
    public static final PermissionType NONE = fromString("None");

    /** Static value Use for PermissionType. */
    public static final PermissionType USE = fromString("Use");

    /** Static value Create for PermissionType. */
    public static final PermissionType CREATE = fromString("Create");

    /** Static value Drop for PermissionType. */
    public static final PermissionType DROP = fromString("Drop");

    /** Static value Alter for PermissionType. */
    public static final PermissionType ALTER = fromString("Alter");

    /** Static value Write for PermissionType. */
    public static final PermissionType WRITE = fromString("Write");

    /** Static value All for PermissionType. */
    public static final PermissionType ALL = fromString("All");

    /**
     * Creates or finds a PermissionType from its string representation.
     * @param name a name to look for
     * @return the corresponding PermissionType
     */
    @JsonCreator
    public static PermissionType fromString(String name) {
        return fromString(name, PermissionType.class);
    }

    /**
     * @return known PermissionType values
     */
    public static Collection<PermissionType> values() {
        return values(PermissionType.class);
    }
}
