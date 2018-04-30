/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.ProvisioningState;
import com.microsoft.azure.management.appservice.HostingEnvironmentStatus;
import com.microsoft.azure.management.appservice.VirtualNetworkProfile;
import com.microsoft.azure.management.appservice.InternalLoadBalancingMode;
import java.util.List;
import com.microsoft.azure.management.appservice.WorkerPool;
import com.microsoft.azure.management.appservice.VirtualIPMapping;
import com.microsoft.azure.management.appservice.NetworkAccessControlEntry;
import com.microsoft.azure.management.appservice.NameValuePair;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * ARM resource for a app service enviroment.
 */
@JsonFlatten
public class AppServiceEnvironmentPatchResourceInner extends ProxyOnlyResource {
    /**
     * Name of the App Service Environment.
     */
    @JsonProperty(value = "properties.name", required = true)
    private String appServiceEnvironmentPatchResourceName;

    /**
     * Location of the App Service Environment, e.g. "West US".
     */
    @JsonProperty(value = "properties.location", required = true)
    private String location;

    /**
     * Provisioning state of the App Service Environment. Possible values
     * include: 'Succeeded', 'Failed', 'Canceled', 'InProgress', 'Deleting'.
     */
    @JsonProperty(value = "properties.provisioningState", access = JsonProperty.Access.WRITE_ONLY)
    private ProvisioningState provisioningState;

    /**
     * Current status of the App Service Environment. Possible values include:
     * 'Preparing', 'Ready', 'Scaling', 'Deleting'.
     */
    @JsonProperty(value = "properties.status", access = JsonProperty.Access.WRITE_ONLY)
    private HostingEnvironmentStatus status;

    /**
     * Name of the Virtual Network for the App Service Environment.
     */
    @JsonProperty(value = "properties.vnetName")
    private String vnetName;

    /**
     * Resource group of the Virtual Network.
     */
    @JsonProperty(value = "properties.vnetResourceGroupName")
    private String vnetResourceGroupName;

    /**
     * Subnet of the Virtual Network.
     */
    @JsonProperty(value = "properties.vnetSubnetName")
    private String vnetSubnetName;

    /**
     * Description of the Virtual Network.
     */
    @JsonProperty(value = "properties.virtualNetwork", required = true)
    private VirtualNetworkProfile virtualNetwork;

    /**
     * Specifies which endpoints to serve internally in the Virtual Network for
     * the App Service Environment. Possible values include: 'None', 'Web',
     * 'Publishing'.
     */
    @JsonProperty(value = "properties.internalLoadBalancingMode")
    private InternalLoadBalancingMode internalLoadBalancingMode;

    /**
     * Front-end VM size, e.g. "Medium", "Large".
     */
    @JsonProperty(value = "properties.multiSize")
    private String multiSize;

    /**
     * Number of front-end instances.
     */
    @JsonProperty(value = "properties.multiRoleCount")
    private Integer multiRoleCount;

    /**
     * Description of worker pools with worker size IDs, VM sizes, and number
     * of workers in each pool.
     */
    @JsonProperty(value = "properties.workerPools", required = true)
    private List<WorkerPool> workerPools;

    /**
     * Number of IP SSL addresses reserved for the App Service Environment.
     */
    @JsonProperty(value = "properties.ipsslAddressCount")
    private Integer ipsslAddressCount;

    /**
     * Edition of the metadata database for the App Service Environment, e.g.
     * "Standard".
     */
    @JsonProperty(value = "properties.databaseEdition", access = JsonProperty.Access.WRITE_ONLY)
    private String databaseEdition;

    /**
     * Service objective of the metadata database for the App Service
     * Environment, e.g. "S0".
     */
    @JsonProperty(value = "properties.databaseServiceObjective", access = JsonProperty.Access.WRITE_ONLY)
    private String databaseServiceObjective;

    /**
     * Number of upgrade domains of the App Service Environment.
     */
    @JsonProperty(value = "properties.upgradeDomains", access = JsonProperty.Access.WRITE_ONLY)
    private Integer upgradeDomains;

    /**
     * Subscription of the App Service Environment.
     */
    @JsonProperty(value = "properties.subscriptionId", access = JsonProperty.Access.WRITE_ONLY)
    private String subscriptionId;

    /**
     * DNS suffix of the App Service Environment.
     */
    @JsonProperty(value = "properties.dnsSuffix")
    private String dnsSuffix;

    /**
     * Last deployment action on the App Service Environment.
     */
    @JsonProperty(value = "properties.lastAction", access = JsonProperty.Access.WRITE_ONLY)
    private String lastAction;

    /**
     * Result of the last deployment action on the App Service Environment.
     */
    @JsonProperty(value = "properties.lastActionResult", access = JsonProperty.Access.WRITE_ONLY)
    private String lastActionResult;

    /**
     * List of comma separated strings describing which VM sizes are allowed
     * for front-ends.
     */
    @JsonProperty(value = "properties.allowedMultiSizes", access = JsonProperty.Access.WRITE_ONLY)
    private String allowedMultiSizes;

    /**
     * List of comma separated strings describing which VM sizes are allowed
     * for workers.
     */
    @JsonProperty(value = "properties.allowedWorkerSizes", access = JsonProperty.Access.WRITE_ONLY)
    private String allowedWorkerSizes;

    /**
     * Maximum number of VMs in the App Service Environment.
     */
    @JsonProperty(value = "properties.maximumNumberOfMachines", access = JsonProperty.Access.WRITE_ONLY)
    private Integer maximumNumberOfMachines;

    /**
     * Description of IP SSL mapping for the App Service Environment.
     */
    @JsonProperty(value = "properties.vipMappings", access = JsonProperty.Access.WRITE_ONLY)
    private List<VirtualIPMapping> vipMappings;

    /**
     * Current total, used, and available worker capacities.
     */
    @JsonProperty(value = "properties.environmentCapacities", access = JsonProperty.Access.WRITE_ONLY)
    private List<StampCapacityInner> environmentCapacities;

    /**
     * Access control list for controlling traffic to the App Service
     * Environment.
     */
    @JsonProperty(value = "properties.networkAccessControlList")
    private List<NetworkAccessControlEntry> networkAccessControlList;

    /**
     * True/false indicating whether the App Service Environment is healthy.
     */
    @JsonProperty(value = "properties.environmentIsHealthy", access = JsonProperty.Access.WRITE_ONLY)
    private Boolean environmentIsHealthy;

    /**
     * Detailed message about with results of the last check of the App Service
     * Environment.
     */
    @JsonProperty(value = "properties.environmentStatus", access = JsonProperty.Access.WRITE_ONLY)
    private String environmentStatus;

    /**
     * Resource group of the App Service Environment.
     */
    @JsonProperty(value = "properties.resourceGroup", access = JsonProperty.Access.WRITE_ONLY)
    private String resourceGroup;

    /**
     * Scale factor for front-ends.
     */
    @JsonProperty(value = "properties.frontEndScaleFactor")
    private Integer frontEndScaleFactor;

    /**
     * Default Scale Factor for FrontEnds.
     */
    @JsonProperty(value = "properties.defaultFrontEndScaleFactor", access = JsonProperty.Access.WRITE_ONLY)
    private Integer defaultFrontEndScaleFactor;

    /**
     * API Management Account associated with the App Service Environment.
     */
    @JsonProperty(value = "properties.apiManagementAccountId")
    private String apiManagementAccountId;

    /**
     * &lt;code&gt;true&lt;/code&gt; if the App Service Environment is
     * suspended; otherwise, &lt;code&gt;false&lt;/code&gt;. The environment
     * can be suspended, e.g. when the management endpoint is no longer
     * available
     * (most likely because NSG blocked the incoming traffic).
     */
    @JsonProperty(value = "properties.suspended")
    private Boolean suspended;

    /**
     * True/false indicating whether the App Service Environment is suspended.
     * The environment can be suspended e.g. when the management endpoint is no
     * longer available
     * (most likely because NSG blocked the incoming traffic).
     */
    @JsonProperty(value = "properties.dynamicCacheEnabled")
    private Boolean dynamicCacheEnabled;

    /**
     * Custom settings for changing the behavior of the App Service
     * Environment.
     */
    @JsonProperty(value = "properties.clusterSettings")
    private List<NameValuePair> clusterSettings;

    /**
     * User added ip ranges to whitelist on ASE db.
     */
    @JsonProperty(value = "properties.userWhitelistedIpRanges")
    private List<String> userWhitelistedIpRanges;

    /**
     * Get the appServiceEnvironmentPatchResourceName value.
     *
     * @return the appServiceEnvironmentPatchResourceName value
     */
    public String appServiceEnvironmentPatchResourceName() {
        return this.appServiceEnvironmentPatchResourceName;
    }

    /**
     * Set the appServiceEnvironmentPatchResourceName value.
     *
     * @param appServiceEnvironmentPatchResourceName the appServiceEnvironmentPatchResourceName value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withAppServiceEnvironmentPatchResourceName(String appServiceEnvironmentPatchResourceName) {
        this.appServiceEnvironmentPatchResourceName = appServiceEnvironmentPatchResourceName;
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
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get the provisioningState value.
     *
     * @return the provisioningState value
     */
    public ProvisioningState provisioningState() {
        return this.provisioningState;
    }

    /**
     * Get the status value.
     *
     * @return the status value
     */
    public HostingEnvironmentStatus status() {
        return this.status;
    }

    /**
     * Get the vnetName value.
     *
     * @return the vnetName value
     */
    public String vnetName() {
        return this.vnetName;
    }

    /**
     * Set the vnetName value.
     *
     * @param vnetName the vnetName value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withVnetName(String vnetName) {
        this.vnetName = vnetName;
        return this;
    }

    /**
     * Get the vnetResourceGroupName value.
     *
     * @return the vnetResourceGroupName value
     */
    public String vnetResourceGroupName() {
        return this.vnetResourceGroupName;
    }

    /**
     * Set the vnetResourceGroupName value.
     *
     * @param vnetResourceGroupName the vnetResourceGroupName value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withVnetResourceGroupName(String vnetResourceGroupName) {
        this.vnetResourceGroupName = vnetResourceGroupName;
        return this;
    }

    /**
     * Get the vnetSubnetName value.
     *
     * @return the vnetSubnetName value
     */
    public String vnetSubnetName() {
        return this.vnetSubnetName;
    }

    /**
     * Set the vnetSubnetName value.
     *
     * @param vnetSubnetName the vnetSubnetName value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withVnetSubnetName(String vnetSubnetName) {
        this.vnetSubnetName = vnetSubnetName;
        return this;
    }

    /**
     * Get the virtualNetwork value.
     *
     * @return the virtualNetwork value
     */
    public VirtualNetworkProfile virtualNetwork() {
        return this.virtualNetwork;
    }

    /**
     * Set the virtualNetwork value.
     *
     * @param virtualNetwork the virtualNetwork value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withVirtualNetwork(VirtualNetworkProfile virtualNetwork) {
        this.virtualNetwork = virtualNetwork;
        return this;
    }

    /**
     * Get the internalLoadBalancingMode value.
     *
     * @return the internalLoadBalancingMode value
     */
    public InternalLoadBalancingMode internalLoadBalancingMode() {
        return this.internalLoadBalancingMode;
    }

    /**
     * Set the internalLoadBalancingMode value.
     *
     * @param internalLoadBalancingMode the internalLoadBalancingMode value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withInternalLoadBalancingMode(InternalLoadBalancingMode internalLoadBalancingMode) {
        this.internalLoadBalancingMode = internalLoadBalancingMode;
        return this;
    }

    /**
     * Get the multiSize value.
     *
     * @return the multiSize value
     */
    public String multiSize() {
        return this.multiSize;
    }

    /**
     * Set the multiSize value.
     *
     * @param multiSize the multiSize value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withMultiSize(String multiSize) {
        this.multiSize = multiSize;
        return this;
    }

    /**
     * Get the multiRoleCount value.
     *
     * @return the multiRoleCount value
     */
    public Integer multiRoleCount() {
        return this.multiRoleCount;
    }

    /**
     * Set the multiRoleCount value.
     *
     * @param multiRoleCount the multiRoleCount value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withMultiRoleCount(Integer multiRoleCount) {
        this.multiRoleCount = multiRoleCount;
        return this;
    }

    /**
     * Get the workerPools value.
     *
     * @return the workerPools value
     */
    public List<WorkerPool> workerPools() {
        return this.workerPools;
    }

    /**
     * Set the workerPools value.
     *
     * @param workerPools the workerPools value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withWorkerPools(List<WorkerPool> workerPools) {
        this.workerPools = workerPools;
        return this;
    }

    /**
     * Get the ipsslAddressCount value.
     *
     * @return the ipsslAddressCount value
     */
    public Integer ipsslAddressCount() {
        return this.ipsslAddressCount;
    }

    /**
     * Set the ipsslAddressCount value.
     *
     * @param ipsslAddressCount the ipsslAddressCount value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withIpsslAddressCount(Integer ipsslAddressCount) {
        this.ipsslAddressCount = ipsslAddressCount;
        return this;
    }

    /**
     * Get the databaseEdition value.
     *
     * @return the databaseEdition value
     */
    public String databaseEdition() {
        return this.databaseEdition;
    }

    /**
     * Get the databaseServiceObjective value.
     *
     * @return the databaseServiceObjective value
     */
    public String databaseServiceObjective() {
        return this.databaseServiceObjective;
    }

    /**
     * Get the upgradeDomains value.
     *
     * @return the upgradeDomains value
     */
    public Integer upgradeDomains() {
        return this.upgradeDomains;
    }

    /**
     * Get the subscriptionId value.
     *
     * @return the subscriptionId value
     */
    public String subscriptionId() {
        return this.subscriptionId;
    }

    /**
     * Get the dnsSuffix value.
     *
     * @return the dnsSuffix value
     */
    public String dnsSuffix() {
        return this.dnsSuffix;
    }

    /**
     * Set the dnsSuffix value.
     *
     * @param dnsSuffix the dnsSuffix value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withDnsSuffix(String dnsSuffix) {
        this.dnsSuffix = dnsSuffix;
        return this;
    }

    /**
     * Get the lastAction value.
     *
     * @return the lastAction value
     */
    public String lastAction() {
        return this.lastAction;
    }

    /**
     * Get the lastActionResult value.
     *
     * @return the lastActionResult value
     */
    public String lastActionResult() {
        return this.lastActionResult;
    }

    /**
     * Get the allowedMultiSizes value.
     *
     * @return the allowedMultiSizes value
     */
    public String allowedMultiSizes() {
        return this.allowedMultiSizes;
    }

    /**
     * Get the allowedWorkerSizes value.
     *
     * @return the allowedWorkerSizes value
     */
    public String allowedWorkerSizes() {
        return this.allowedWorkerSizes;
    }

    /**
     * Get the maximumNumberOfMachines value.
     *
     * @return the maximumNumberOfMachines value
     */
    public Integer maximumNumberOfMachines() {
        return this.maximumNumberOfMachines;
    }

    /**
     * Get the vipMappings value.
     *
     * @return the vipMappings value
     */
    public List<VirtualIPMapping> vipMappings() {
        return this.vipMappings;
    }

    /**
     * Get the environmentCapacities value.
     *
     * @return the environmentCapacities value
     */
    public List<StampCapacityInner> environmentCapacities() {
        return this.environmentCapacities;
    }

    /**
     * Get the networkAccessControlList value.
     *
     * @return the networkAccessControlList value
     */
    public List<NetworkAccessControlEntry> networkAccessControlList() {
        return this.networkAccessControlList;
    }

    /**
     * Set the networkAccessControlList value.
     *
     * @param networkAccessControlList the networkAccessControlList value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withNetworkAccessControlList(List<NetworkAccessControlEntry> networkAccessControlList) {
        this.networkAccessControlList = networkAccessControlList;
        return this;
    }

    /**
     * Get the environmentIsHealthy value.
     *
     * @return the environmentIsHealthy value
     */
    public Boolean environmentIsHealthy() {
        return this.environmentIsHealthy;
    }

    /**
     * Get the environmentStatus value.
     *
     * @return the environmentStatus value
     */
    public String environmentStatus() {
        return this.environmentStatus;
    }

    /**
     * Get the resourceGroup value.
     *
     * @return the resourceGroup value
     */
    public String resourceGroup() {
        return this.resourceGroup;
    }

    /**
     * Get the frontEndScaleFactor value.
     *
     * @return the frontEndScaleFactor value
     */
    public Integer frontEndScaleFactor() {
        return this.frontEndScaleFactor;
    }

    /**
     * Set the frontEndScaleFactor value.
     *
     * @param frontEndScaleFactor the frontEndScaleFactor value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withFrontEndScaleFactor(Integer frontEndScaleFactor) {
        this.frontEndScaleFactor = frontEndScaleFactor;
        return this;
    }

    /**
     * Get the defaultFrontEndScaleFactor value.
     *
     * @return the defaultFrontEndScaleFactor value
     */
    public Integer defaultFrontEndScaleFactor() {
        return this.defaultFrontEndScaleFactor;
    }

    /**
     * Get the apiManagementAccountId value.
     *
     * @return the apiManagementAccountId value
     */
    public String apiManagementAccountId() {
        return this.apiManagementAccountId;
    }

    /**
     * Set the apiManagementAccountId value.
     *
     * @param apiManagementAccountId the apiManagementAccountId value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withApiManagementAccountId(String apiManagementAccountId) {
        this.apiManagementAccountId = apiManagementAccountId;
        return this;
    }

    /**
     * Get the suspended value.
     *
     * @return the suspended value
     */
    public Boolean suspended() {
        return this.suspended;
    }

    /**
     * Set the suspended value.
     *
     * @param suspended the suspended value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withSuspended(Boolean suspended) {
        this.suspended = suspended;
        return this;
    }

    /**
     * Get the dynamicCacheEnabled value.
     *
     * @return the dynamicCacheEnabled value
     */
    public Boolean dynamicCacheEnabled() {
        return this.dynamicCacheEnabled;
    }

    /**
     * Set the dynamicCacheEnabled value.
     *
     * @param dynamicCacheEnabled the dynamicCacheEnabled value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withDynamicCacheEnabled(Boolean dynamicCacheEnabled) {
        this.dynamicCacheEnabled = dynamicCacheEnabled;
        return this;
    }

    /**
     * Get the clusterSettings value.
     *
     * @return the clusterSettings value
     */
    public List<NameValuePair> clusterSettings() {
        return this.clusterSettings;
    }

    /**
     * Set the clusterSettings value.
     *
     * @param clusterSettings the clusterSettings value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withClusterSettings(List<NameValuePair> clusterSettings) {
        this.clusterSettings = clusterSettings;
        return this;
    }

    /**
     * Get the userWhitelistedIpRanges value.
     *
     * @return the userWhitelistedIpRanges value
     */
    public List<String> userWhitelistedIpRanges() {
        return this.userWhitelistedIpRanges;
    }

    /**
     * Set the userWhitelistedIpRanges value.
     *
     * @param userWhitelistedIpRanges the userWhitelistedIpRanges value to set
     * @return the AppServiceEnvironmentPatchResourceInner object itself.
     */
    public AppServiceEnvironmentPatchResourceInner withUserWhitelistedIpRanges(List<String> userWhitelistedIpRanges) {
        this.userWhitelistedIpRanges = userWhitelistedIpRanges;
        return this;
    }

}
