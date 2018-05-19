/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import com.microsoft.azure.management.apimanagement.IdentityProviderType;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * Parameters supplied to update Identity Provider.
 */
@JsonFlatten
public class IdentityProviderUpdateParametersInner {
    /**
     * Identity Provider Type identifier. Possible values include: 'facebook',
     * 'google', 'microsoft', 'twitter', 'aad', 'aadB2C'.
     */
    @JsonProperty(value = "properties.type")
    private IdentityProviderType type;

    /**
     * List of Allowed Tenants when configuring Azure Active Directory login.
     */
    @JsonProperty(value = "properties.allowedTenants")
    private List<String> allowedTenants;

    /**
     * Signup Policy Name. Only applies to AAD B2C Identity Provider.
     */
    @JsonProperty(value = "properties.signupPolicyName")
    private String signupPolicyName;

    /**
     * Signin Policy Name. Only applies to AAD B2C Identity Provider.
     */
    @JsonProperty(value = "properties.signinPolicyName")
    private String signinPolicyName;

    /**
     * Profile Editing Policy Name. Only applies to AAD B2C Identity Provider.
     */
    @JsonProperty(value = "properties.profileEditingPolicyName")
    private String profileEditingPolicyName;

    /**
     * Password Reset Policy Name. Only applies to AAD B2C Identity Provider.
     */
    @JsonProperty(value = "properties.passwordResetPolicyName")
    private String passwordResetPolicyName;

    /**
     * Client Id of the Application in the external Identity Provider. It is
     * App ID for Facebook login, Client ID for Google login, App ID for
     * Microsoft.
     */
    @JsonProperty(value = "properties.clientId")
    private String clientId;

    /**
     * Client secret of the Application in external Identity Provider, used to
     * authenticate login request. For example, it is App Secret for Facebook
     * login, API Key for Google login, Public Key for Microsoft.
     */
    @JsonProperty(value = "properties.clientSecret")
    private String clientSecret;

    /**
     * Get the type value.
     *
     * @return the type value
     */
    public IdentityProviderType type() {
        return this.type;
    }

    /**
     * Set the type value.
     *
     * @param type the type value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withType(IdentityProviderType type) {
        this.type = type;
        return this;
    }

    /**
     * Get the allowedTenants value.
     *
     * @return the allowedTenants value
     */
    public List<String> allowedTenants() {
        return this.allowedTenants;
    }

    /**
     * Set the allowedTenants value.
     *
     * @param allowedTenants the allowedTenants value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withAllowedTenants(List<String> allowedTenants) {
        this.allowedTenants = allowedTenants;
        return this;
    }

    /**
     * Get the signupPolicyName value.
     *
     * @return the signupPolicyName value
     */
    public String signupPolicyName() {
        return this.signupPolicyName;
    }

    /**
     * Set the signupPolicyName value.
     *
     * @param signupPolicyName the signupPolicyName value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withSignupPolicyName(String signupPolicyName) {
        this.signupPolicyName = signupPolicyName;
        return this;
    }

    /**
     * Get the signinPolicyName value.
     *
     * @return the signinPolicyName value
     */
    public String signinPolicyName() {
        return this.signinPolicyName;
    }

    /**
     * Set the signinPolicyName value.
     *
     * @param signinPolicyName the signinPolicyName value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withSigninPolicyName(String signinPolicyName) {
        this.signinPolicyName = signinPolicyName;
        return this;
    }

    /**
     * Get the profileEditingPolicyName value.
     *
     * @return the profileEditingPolicyName value
     */
    public String profileEditingPolicyName() {
        return this.profileEditingPolicyName;
    }

    /**
     * Set the profileEditingPolicyName value.
     *
     * @param profileEditingPolicyName the profileEditingPolicyName value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withProfileEditingPolicyName(String profileEditingPolicyName) {
        this.profileEditingPolicyName = profileEditingPolicyName;
        return this;
    }

    /**
     * Get the passwordResetPolicyName value.
     *
     * @return the passwordResetPolicyName value
     */
    public String passwordResetPolicyName() {
        return this.passwordResetPolicyName;
    }

    /**
     * Set the passwordResetPolicyName value.
     *
     * @param passwordResetPolicyName the passwordResetPolicyName value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withPasswordResetPolicyName(String passwordResetPolicyName) {
        this.passwordResetPolicyName = passwordResetPolicyName;
        return this;
    }

    /**
     * Get the clientId value.
     *
     * @return the clientId value
     */
    public String clientId() {
        return this.clientId;
    }

    /**
     * Set the clientId value.
     *
     * @param clientId the clientId value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    /**
     * Get the clientSecret value.
     *
     * @return the clientSecret value
     */
    public String clientSecret() {
        return this.clientSecret;
    }

    /**
     * Set the clientSecret value.
     *
     * @param clientSecret the clientSecret value to set
     * @return the IdentityProviderUpdateParametersInner object itself.
     */
    public IdentityProviderUpdateParametersInner withClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

}
