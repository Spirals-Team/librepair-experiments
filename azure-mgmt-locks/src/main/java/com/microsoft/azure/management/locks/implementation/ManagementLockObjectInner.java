/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.locks.implementation;

import com.microsoft.azure.management.locks.LockLevel;
import java.util.List;
import com.microsoft.azure.management.locks.ManagementLockOwner;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The lock information.
 */
@JsonFlatten
public class ManagementLockObjectInner {
    /**
     * The level of the lock. Possible values are: NotSpecified, CanNotDelete,
     * ReadOnly. CanNotDelete means authorized users are able to read and
     * modify the resources, but not delete. ReadOnly means authorized users
     * can only read from a resource, but they can't modify or delete it.
     * Possible values include: 'NotSpecified', 'CanNotDelete', 'ReadOnly'.
     */
    @JsonProperty(value = "properties.level", required = true)
    private LockLevel level;

    /**
     * Notes about the lock. Maximum of 512 characters.
     */
    @JsonProperty(value = "properties.notes")
    private String notes;

    /**
     * The owners of the lock.
     */
    @JsonProperty(value = "properties.owners")
    private List<ManagementLockOwner> owners;

    /**
     * The resource ID of the lock.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /**
     * The resource type of the lock - Microsoft.Authorization/locks.
     */
    @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
    private String type;

    /**
     * The name of the lock.
     */
    @JsonProperty(value = "name")
    private String name;

    /**
     * Get the level value.
     *
     * @return the level value
     */
    public LockLevel level() {
        return this.level;
    }

    /**
     * Set the level value.
     *
     * @param level the level value to set
     * @return the ManagementLockObjectInner object itself.
     */
    public ManagementLockObjectInner withLevel(LockLevel level) {
        this.level = level;
        return this;
    }

    /**
     * Get the notes value.
     *
     * @return the notes value
     */
    public String notes() {
        return this.notes;
    }

    /**
     * Set the notes value.
     *
     * @param notes the notes value to set
     * @return the ManagementLockObjectInner object itself.
     */
    public ManagementLockObjectInner withNotes(String notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Get the owners value.
     *
     * @return the owners value
     */
    public List<ManagementLockOwner> owners() {
        return this.owners;
    }

    /**
     * Set the owners value.
     *
     * @param owners the owners value to set
     * @return the ManagementLockObjectInner object itself.
     */
    public ManagementLockObjectInner withOwners(List<ManagementLockOwner> owners) {
        this.owners = owners;
        return this;
    }

    /**
     * Get the id value.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
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
     * Get the name value.
     *
     * @return the name value
     */
    public String name() {
        return this.name;
    }

    /**
     * Set the name value.
     *
     * @param name the name value to set
     * @return the ManagementLockObjectInner object itself.
     */
    public ManagementLockObjectInner withName(String name) {
        this.name = name;
        return this;
    }

}
