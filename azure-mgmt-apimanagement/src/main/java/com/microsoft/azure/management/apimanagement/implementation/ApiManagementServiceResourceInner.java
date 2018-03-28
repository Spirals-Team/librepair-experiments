/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement.implementation;

import org.joda.time.DateTime;
import java.util.List;
import com.microsoft.azure.management.apimanagement.HostnameConfiguration;
import com.microsoft.azure.management.apimanagement.VirtualNetworkConfiguration;
import com.microsoft.azure.management.apimanagement.AdditionalLocation;
import java.util.Map;
import com.microsoft.azure.management.apimanagement.CertificateConfiguration;
import com.microsoft.azure.management.apimanagement.VirtualNetworkType;
import com.microsoft.azure.management.apimanagement.ApiManagementServiceSkuProperties;
import com.microsoft.azure.management.apimanagement.ApiManagementServiceIdentity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.apimanagement.ApimResource;

/**
 * A single API Management service resource in List or Get response.
 */
@JsonFlatten
public class ApiManagementServiceResourceInner extends ApimResource {
    /**
     * Email address from which the notification will be sent.
     */
    @JsonProperty(value = "properties.notificationSenderEmail")
    private String notificationSenderEmail;

    /**
     * The current provisioning state of the API Management service which can
     * be one of the following:
     * Created/Activating/Succeeded/Updating/Failed/Stopped/Terminating/TerminationFailed/Deleted.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String provisioningState;

    /**
     * The provisioning state of the API Management service, which is targeted
     * by the long running operation started on the service.
     */
    @JsonProperty(value = "properties.targetProvisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private String targetProvisioningState;

    /**
     * Creation UTC date of the API Management service.The date conforms to the
     * following format: `yyyy-MM-ddTHH:mm:ssZ` as specified by the ISO 8601
     * standard.
     */
    @JsonProperty(value = "properties.createdAtUtc", access = JsonProperty.Access.WRITE_ONLY)
    private DateTime createdAtUtc;

    /**
     * Gateway URL of the API Management service.
     */
    @JsonProperty(value = "properties.gatewayUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String gatewayUrl;

    /**
     * Gateway URL of the API Management service in the Default Region.
     */
    @JsonProperty(value = "properties.gatewayRegionalUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String gatewayRegionalUrl;

    /**
     * Publisher portal endpoint Url of the API Management service.
     */
    @JsonProperty(value = "properties.portalUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String portalUrl;

    /**
     * Management API endpoint URL of the API Management service.
     */
    @JsonProperty(value = "properties.managementApiUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String managementApiUrl;

    /**
     * SCM endpoint URL of the API Management service.
     */
    @JsonProperty(value = "properties.scmUrl", access = JsonProperty.Access.WRITE_ONLY)
    private String scmUrl;

    /**
     * Custom hostname configuration of the API Management service.
     */
    @JsonProperty(value = "properties.hostnameConfigurations")
    private List<HostnameConfiguration> hostnameConfigurations;

    /**
     * Public Static Load Balanced IP addresses of the API Management service.
     * Available only for Basic, Standard and Premium SKU.
     */
    @JsonProperty(value = "properties.publicIPAddresses", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> publicIPAddresses;

    /**
     * Private Static Load Balanced IP addresses of the API Management service
     * which is deployed in an Internal Virtual Network. Available only for
     * Basic, Standard and Premium SKU.
     */
    @JsonProperty(value = "properties.privateIPAddresses", access = JsonProperty.Access.WRITE_ONLY)
    private List<String> privateIPAddresses;

    /**
     * Virtual network configuration of the API Management service.
     */
    @JsonProperty(value = "properties.virtualNetworkConfiguration")
    private VirtualNetworkConfiguration virtualNetworkConfiguration;

    /**
     * Additional datacenter locations of the API Management service.
     */
    @JsonProperty(value = "properties.additionalLocations")
    private List<AdditionalLocation> additionalLocations;

    /**
     * Custom properties of the API Management service. Setting
     * `Microsoft.WindowsAzure.ApiManagement.Gateway.Security.Ciphers.TripleDes168`
     * will disable the cipher TLS_RSA_WITH_3DES_EDE_CBC_SHA for all TLS(1.0,
     * 1.1 and 1.2). Setting
     * `Microsoft.WindowsAzure.ApiManagement.Gateway.Security.Protocols.Tls11`
     * can be used to disable just TLS 1.1 and setting
     * `Microsoft.WindowsAzure.ApiManagement.Gateway.Security.Protocols.Tls10`
     * can be used to disable TLS 1.0 on an API Management service.
     */
    @JsonProperty(value = "properties.customProperties")
    private Map<String, String> customProperties;

    /**
     * List of Certificates that need to be installed in the API Management
     * service. Max supported certificates that can be installed is 10.
     */
    @JsonProperty(value = "properties.certificates")
    private List<CertificateConfiguration> certificates;

    /**
     * The type of VPN in which API Managemet service needs to be configured
     * in. None (Default Value) means the API Management service is not part of
     * any Virtual Network, External means the API Management deployment is set
     * up inside a Virtual Network having an Internet Facing Endpoint, and
     * Internal means that API Management deployment is setup inside a Virtual
     * Network having an Intranet Facing Endpoint only. Possible values
     * include: 'None', 'External', 'Internal'.
     */
    @JsonProperty(value = "properties.virtualNetworkType")
    private VirtualNetworkType virtualNetworkType;

    /**
     * Publisher email.
     */
    @JsonProperty(value = "properties.publisherEmail", required = true)
    private String publisherEmail;

    /**
     * Publisher name.
     */
    @JsonProperty(value = "properties.publisherName", required = true)
    private String publisherName;

    /**
     * SKU properties of the API Management service.
     */
    @JsonProperty(value = "sku", required = true)
    private ApiManagementServiceSkuProperties sku;

    /**
     * Managed service identity of the Api Management service.
     */
    @JsonProperty(value = "identity")
    private ApiManagementServiceIdentity identity;

    /**
     * Resource location.
     */
    @JsonProperty(value = "location", required = true)
    private String location;

    /**
     * ETag of the resource.
     */
    @JsonProperty(value = "etag", access = JsonProperty.Access.WRITE_ONLY)
    private String etag;

    /**
     * Get the notificationSenderEmail value.
     *
     * @return the notificationSenderEmail value
     */
    public String notificationSenderEmail() {
        return this.notificationSenderEmail;
    }

    /**
     * Set the notificationSenderEmail value.
     *
     * @param notificationSenderEmail the notificationSenderEmail value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withNotificationSenderEmail(String notificationSenderEmail) {
        this.notificationSenderEmail = notificationSenderEmail;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public String provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the targetProvisioningState value.
     *
     * @return the targetProvisioningState value
     */
    public String targetProvisioningState() {
        return this.targetProvisioningState;
    }

    /**
     * Get the createdAtUtc value.
     *
     * @return the createdAtUtc value
     */
    public DateTime createdAtUtc() {
        return this.createdAtUtc;
    }

    /**
     * Get the gatewayUrl value.
     *
     * @return the gatewayUrl value
     */
    public String gatewayUrl() {
        return this.gatewayUrl;
    }

    /**
     * Get the gatewayRegionalUrl value.
     *
     * @return the gatewayRegionalUrl value
     */
    public String gatewayRegionalUrl() {
        return this.gatewayRegionalUrl;
    }

    /**
     * Get the portalUrl value.
     *
     * @return the portalUrl value
     */
    public String portalUrl() {
        return this.portalUrl;
    }

    /**
     * Get the managementApiUrl value.
     *
     * @return the managementApiUrl value
     */
    public String managementApiUrl() {
        return this.managementApiUrl;
    }

    /**
     * Get the scmUrl value.
     *
     * @return the scmUrl value
     */
    public String scmUrl() {
        return this.scmUrl;
    }

    /**
     * Get the hostnameConfigurations value.
     *
     * @return the hostnameConfigurations value
     */
    public List<HostnameConfiguration> hostnameConfigurations() {
        return this.hostnameConfigurations;
    }

    /**
     * Set the hostnameConfigurations value.
     *
     * @param hostnameConfigurations the hostnameConfigurations value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withHostnameConfigurations(List<HostnameConfiguration> hostnameConfigurations) {
        this.hostnameConfigurations = hostnameConfigurations;
        return this;
    }

    /**
     * Get the publicIPAddresses value.
     *
     * @return the publicIPAddresses value
     */
    public List<String> publicIPAddresses() {
        return this.publicIPAddresses;
    }

    /**
     * Get the privateIPAddresses value.
     *
     * @return the privateIPAddresses value
     */
    public List<String> privateIPAddresses() {
        return this.privateIPAddresses;
    }

    /**
     * Get the virtualNetworkConfiguration value.
     *
     * @return the virtualNetworkConfiguration value
     */
    public VirtualNetworkConfiguration virtualNetworkConfiguration() {
        return this.virtualNetworkConfiguration;
    }

    /**
     * Set the virtualNetworkConfiguration value.
     *
     * @param virtualNetworkConfiguration the virtualNetworkConfiguration value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withVirtualNetworkConfiguration(VirtualNetworkConfiguration virtualNetworkConfiguration) {
        this.virtualNetworkConfiguration = virtualNetworkConfiguration;
        return this;
    }

    /**
     * Get the additionalLocations value.
     *
     * @return the additionalLocations value
     */
    public List<AdditionalLocation> additionalLocations() {
        return this.additionalLocations;
    }

    /**
     * Set the additionalLocations value.
     *
     * @param additionalLocations the additionalLocations value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withAdditionalLocations(List<AdditionalLocation> additionalLocations) {
        this.additionalLocations = additionalLocations;
        return this;
    }

    /**
     * Get the customProperties value.
     *
     * @return the customProperties value
     */
    public Map<String, String> customProperties() {
        return this.customProperties;
    }

    /**
     * Set the customProperties value.
     *
     * @param customProperties the customProperties value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withCustomProperties(Map<String, String> customProperties) {
        this.customProperties = customProperties;
        return this;
    }

    /**
     * Get the certificates value.
     *
     * @return the certificates value
     */
    public List<CertificateConfiguration> certificates() {
        return this.certificates;
    }

    /**
     * Set the certificates value.
     *
     * @param certificates the certificates value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withCertificates(List<CertificateConfiguration> certificates) {
        this.certificates = certificates;
        return this;
    }

    /**
     * Get the virtualNetworkType value.
     *
     * @return the virtualNetworkType value
     */
    public VirtualNetworkType virtualNetworkType() {
        return this.virtualNetworkType;
    }

    /**
     * Set the virtualNetworkType value.
     *
     * @param virtualNetworkType the virtualNetworkType value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withVirtualNetworkType(VirtualNetworkType virtualNetworkType) {
        this.virtualNetworkType = virtualNetworkType;
        return this;
    }

    /**
     * Get the publisherEmail value.
     *
     * @return the publisherEmail value
     */
    public String publisherEmail() {
        return this.publisherEmail;
    }

    /**
     * Set the publisherEmail value.
     *
     * @param publisherEmail the publisherEmail value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withPublisherEmail(String publisherEmail) {
        this.publisherEmail = publisherEmail;
        return this;
    }

    /**
     * Get the publisherName value.
     *
     * @return the publisherName value
     */
    public String publisherName() {
        return this.publisherName;
    }

    /**
     * Set the publisherName value.
     *
     * @param publisherName the publisherName value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withPublisherName(String publisherName) {
        this.publisherName = publisherName;
        return this;
    }

    /**
     * Get the sku value.
     *
     * @return the sku value
     */
    public ApiManagementServiceSkuProperties sku() {
        return this.sku;
    }

    /**
     * Set the sku value.
     *
     * @param sku the sku value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withSku(ApiManagementServiceSkuProperties sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get the identity value.
     *
     * @return the identity value
     */
    public ApiManagementServiceIdentity identity() {
        return this.identity;
    }

    /**
     * Set the identity value.
     *
     * @param identity the identity value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withIdentity(ApiManagementServiceIdentity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Get the location value.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set the location value.
     *
     * @param location the location value to set
     * @return the ApiManagementServiceResourceInner object itself.
     */
    public ApiManagementServiceResourceInner withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the etag value.
     *
     * @return the etag value
     */
    public String etag() {
        return this.etag;
    }

}
