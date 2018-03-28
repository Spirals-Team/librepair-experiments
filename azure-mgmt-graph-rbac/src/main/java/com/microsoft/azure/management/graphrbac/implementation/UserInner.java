/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.graphrbac.implementation;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Active Directory user information.
 */
public class UserInner {
    /**
     * The object ID.
     */
    @JsonProperty(value = "objectId")
    private String objectId;

    /**
     * The object type.
     */
    @JsonProperty(value = "objectType")
    private String objectType;

    /**
     * The principal name of the user.
     */
    @JsonProperty(value = "userPrincipalName")
    private String userPrincipalName;

    /**
     * The display name of the user.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /**
     * The sign-in name of the user.
     */
    @JsonProperty(value = "signInName")
    private String signInName;

    /**
     * The primary email address of the user.
     */
    @JsonProperty(value = "mail")
    private String mail;

    /**
     * The mail alias for the user.
     */
    @JsonProperty(value = "mailNickname")
    private String mailNickname;

    /**
     * A two letter country code (ISO standard 3166). Required for users that
     * will be assigned licenses due to legal requirement to check for
     * availability of services in countries. Examples include: "US", "JP", and
     * "GB".
     */
    @JsonProperty(value = "usageLocation")
    private String usageLocation;

    /**
     * Get the objectId value.
     *
     * @return the objectId value
     */
    public String objectId() {
        return this.objectId;
    }

    /**
     * Set the objectId value.
     *
     * @param objectId the objectId value to set
     * @return the UserInner object itself.
     */
    public UserInner withObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    /**
     * Get the objectType value.
     *
     * @return the objectType value
     */
    public String objectType() {
        return this.objectType;
    }

    /**
     * Set the objectType value.
     *
     * @param objectType the objectType value to set
     * @return the UserInner object itself.
     */
    public UserInner withObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    /**
     * Get the userPrincipalName value.
     *
     * @return the userPrincipalName value
     */
    public String userPrincipalName() {
        return this.userPrincipalName;
    }

    /**
     * Set the userPrincipalName value.
     *
     * @param userPrincipalName the userPrincipalName value to set
     * @return the UserInner object itself.
     */
    public UserInner withUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
        return this;
    }

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
     * @return the UserInner object itself.
     */
    public UserInner withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the signInName value.
     *
     * @return the signInName value
     */
    public String signInName() {
        return this.signInName;
    }

    /**
     * Set the signInName value.
     *
     * @param signInName the signInName value to set
     * @return the UserInner object itself.
     */
    public UserInner withSignInName(String signInName) {
        this.signInName = signInName;
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
     * @return the UserInner object itself.
     */
    public UserInner withMail(String mail) {
        this.mail = mail;
        return this;
    }

    /**
     * Get the mailNickname value.
     *
     * @return the mailNickname value
     */
    public String mailNickname() {
        return this.mailNickname;
    }

    /**
     * Set the mailNickname value.
     *
     * @param mailNickname the mailNickname value to set
     * @return the UserInner object itself.
     */
    public UserInner withMailNickname(String mailNickname) {
        this.mailNickname = mailNickname;
        return this;
    }

    /**
     * Get the usageLocation value.
     *
     * @return the usageLocation value
     */
    public String usageLocation() {
        return this.usageLocation;
    }

    /**
     * Set the usageLocation value.
     *
     * @param usageLocation the usageLocation value to set
     * @return the UserInner object itself.
     */
    public UserInner withUsageLocation(String usageLocation) {
        this.usageLocation = usageLocation;
        return this;
    }

}
