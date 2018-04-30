/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Active Directory group information.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonTypeName("Group")
public class ADGroupInner extends DirectoryObjectInner {
    /**
     * The display name of the group.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /**
     * Whether the group is security-enable.
     */
    @JsonProperty(value = "securityEnabled")
    private Boolean securityEnabled;

    /**
     * The primary email address of the group.
     */
    @JsonProperty(value = "mail")
    private String mail;

    /**
     * Get the displayName value.
     *
     * @return the displayName value
     */
    public String displayName() {
        return this.displayName;
    }

    /**
     * Set the displayName value.
     *
     * @param displayName the displayName value to set
     * @return the ADGroupInner object itself.
     */
    public ADGroupInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the securityEnabled value.
     *
     * @return the securityEnabled value
     */
    public Boolean securityEnabled() {
        return this.securityEnabled;
    }

    /**
     * Set the securityEnabled value.
     *
     * @param securityEnabled the securityEnabled value to set
     * @return the ADGroupInner object itself.
     */
    public ADGroupInner withSecurityEnabled(Boolean securityEnabled) {
        this.securityEnabled = securityEnabled;
        return this;
    }

    /**
     * Get the mail value.
     *
     * @return the mail value
     */
    public String mail() {
        return this.mail;
    }

    /**
     * Set the mail value.
     *
     * @param mail the mail value to set
     * @return the ADGroupInner object itself.
     */
    public ADGroupInner withMail(String mail) {
        this.mail = mail;
        return this;
    }

}
