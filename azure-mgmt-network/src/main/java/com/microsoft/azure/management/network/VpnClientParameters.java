/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Vpn Client Parameters for package generation.
 */
public class VpnClientParameters {
    /**
     * VPN client Processor Architecture. Possible values are: 'AMD64' and
     * 'X86'. Possible values include: 'Amd64', 'X86'.
     */
    @JsonProperty(value = "processorArchitecture")
    private ProcessorArchitecture processorArchitecture;

    /**
     * VPN client Authentication Method. Possible values are: 'EAPTLS' and
     * 'EAPMSCHAPv2'. Possible values include: 'EAPTLS', 'EAPMSCHAPv2'.
     */
    @JsonProperty(value = "authenticationMethod")
    private AuthenticationMethod authenticationMethod;

    /**
     * The public certificate data for the radius server authentication
     * certificate as a Base-64 encoded string. Required only if external
     * radius authentication has been configured with EAPTLS authentication.
     */
    @JsonProperty(value = "radiusServerAuthCertificate")
    private String radiusServerAuthCertificate;

    /**
     * A list of client root certificates public certificate data encoded as
     * Base-64 strings. Optional parameter for external radius based
     * authentication with EAPTLS.
     */
    @JsonProperty(value = "clientRootCertificates")
    private List<String> clientRootCertificates;

    /**
     * Get the processorArchitecture value.
     *
     * @return the processorArchitecture value
     */
    public ProcessorArchitecture processorArchitecture() {
        return this.processorArchitecture;
    }

    /**
     * Set the processorArchitecture value.
     *
     * @param processorArchitecture the processorArchitecture value to set
     * @return the VpnClientParameters object itself.
     */
    public VpnClientParameters withProcessorArchitecture(ProcessorArchitecture processorArchitecture) {
        this.processorArchitecture = processorArchitecture;
        return this;
    }

    /**
     * Get the authenticationMethod value.
     *
     * @return the authenticationMethod value
     */
    public AuthenticationMethod authenticationMethod() {
        return this.authenticationMethod;
    }

    /**
     * Set the authenticationMethod value.
     *
     * @param authenticationMethod the authenticationMethod value to set
     * @return the VpnClientParameters object itself.
     */
    public VpnClientParameters withAuthenticationMethod(AuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
        return this;
    }

    /**
     * Get the radiusServerAuthCertificate value.
     *
     * @return the radiusServerAuthCertificate value
     */
    public String radiusServerAuthCertificate() {
        return this.radiusServerAuthCertificate;
    }

    /**
     * Set the radiusServerAuthCertificate value.
     *
     * @param radiusServerAuthCertificate the radiusServerAuthCertificate value to set
     * @return the VpnClientParameters object itself.
     */
    public VpnClientParameters withRadiusServerAuthCertificate(String radiusServerAuthCertificate) {
        this.radiusServerAuthCertificate = radiusServerAuthCertificate;
        return this;
    }

    /**
     * Get the clientRootCertificates value.
     *
     * @return the clientRootCertificates value
     */
    public List<String> clientRootCertificates() {
        return this.clientRootCertificates;
    }

    /**
     * Set the clientRootCertificates value.
     *
     * @param clientRootCertificates the clientRootCertificates value to set
     * @return the VpnClientParameters object itself.
     */
    public VpnClientParameters withClientRootCertificates(List<String> clientRootCertificates) {
        this.clientRootCertificates = clientRootCertificates;
        return this;
    }

}
