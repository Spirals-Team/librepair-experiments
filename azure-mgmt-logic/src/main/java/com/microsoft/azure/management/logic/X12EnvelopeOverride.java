/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The X12 envelope override settings.
 */
public class X12EnvelopeOverride {
    /**
     * The target namespace on which this envelope settings has to be applied.
     */
    @JsonProperty(value = "targetNamespace", required = true)
    private String targetNamespace;

    /**
     * The protocol version on which this envelope settings has to be applied.
     */
    @JsonProperty(value = "protocolVersion", required = true)
    private String protocolVersion;

    /**
     * The message id on which this envelope settings has to be applied.
     */
    @JsonProperty(value = "messageId", required = true)
    private String messageId;

    /**
     * The responsible agency code.
     */
    @JsonProperty(value = "responsibleAgencyCode", required = true)
    private String responsibleAgencyCode;

    /**
     * The header version.
     */
    @JsonProperty(value = "headerVersion", required = true)
    private String headerVersion;

    /**
     * The sender application id.
     */
    @JsonProperty(value = "senderApplicationId", required = true)
    private String senderApplicationId;

    /**
     * The receiver application id.
     */
    @JsonProperty(value = "receiverApplicationId", required = true)
    private String receiverApplicationId;

    /**
     * The functional identifier code.
     */
    @JsonProperty(value = "functionalIdentifierCode")
    private String functionalIdentifierCode;

    /**
     * The date format. Possible values include: 'NotSpecified', 'CCYYMMDD',
     * 'YYMMDD'.
     */
    @JsonProperty(value = "dateFormat", required = true)
    private X12DateFormat dateFormat;

    /**
     * The time format. Possible values include: 'NotSpecified', 'HHMM',
     * 'HHMMSS', 'HHMMSSdd', 'HHMMSSd'.
     */
    @JsonProperty(value = "timeFormat", required = true)
    private X12TimeFormat timeFormat;

    /**
     * Get the targetNamespace value.
     *
     * @return the targetNamespace value
     */
    public String targetNamespace() {
        return this.targetNamespace;
    }

    /**
     * Set the targetNamespace value.
     *
     * @param targetNamespace the targetNamespace value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withTargetNamespace(String targetNamespace) {
        this.targetNamespace = targetNamespace;
        return this;
    }

    /**
     * Get the protocolVersion value.
     *
     * @return the protocolVersion value
     */
    public String protocolVersion() {
        return this.protocolVersion;
    }

    /**
     * Set the protocolVersion value.
     *
     * @param protocolVersion the protocolVersion value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
        return this;
    }

    /**
     * Get the messageId value.
     *
     * @return the messageId value
     */
    public String messageId() {
        return this.messageId;
    }

    /**
     * Set the messageId value.
     *
     * @param messageId the messageId value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    /**
     * Get the responsibleAgencyCode value.
     *
     * @return the responsibleAgencyCode value
     */
    public String responsibleAgencyCode() {
        return this.responsibleAgencyCode;
    }

    /**
     * Set the responsibleAgencyCode value.
     *
     * @param responsibleAgencyCode the responsibleAgencyCode value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withResponsibleAgencyCode(String responsibleAgencyCode) {
        this.responsibleAgencyCode = responsibleAgencyCode;
        return this;
    }

    /**
     * Get the headerVersion value.
     *
     * @return the headerVersion value
     */
    public String headerVersion() {
        return this.headerVersion;
    }

    /**
     * Set the headerVersion value.
     *
     * @param headerVersion the headerVersion value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withHeaderVersion(String headerVersion) {
        this.headerVersion = headerVersion;
        return this;
    }

    /**
     * Get the senderApplicationId value.
     *
     * @return the senderApplicationId value
     */
    public String senderApplicationId() {
        return this.senderApplicationId;
    }

    /**
     * Set the senderApplicationId value.
     *
     * @param senderApplicationId the senderApplicationId value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withSenderApplicationId(String senderApplicationId) {
        this.senderApplicationId = senderApplicationId;
        return this;
    }

    /**
     * Get the receiverApplicationId value.
     *
     * @return the receiverApplicationId value
     */
    public String receiverApplicationId() {
        return this.receiverApplicationId;
    }

    /**
     * Set the receiverApplicationId value.
     *
     * @param receiverApplicationId the receiverApplicationId value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withReceiverApplicationId(String receiverApplicationId) {
        this.receiverApplicationId = receiverApplicationId;
        return this;
    }

    /**
     * Get the functionalIdentifierCode value.
     *
     * @return the functionalIdentifierCode value
     */
    public String functionalIdentifierCode() {
        return this.functionalIdentifierCode;
    }

    /**
     * Set the functionalIdentifierCode value.
     *
     * @param functionalIdentifierCode the functionalIdentifierCode value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withFunctionalIdentifierCode(String functionalIdentifierCode) {
        this.functionalIdentifierCode = functionalIdentifierCode;
        return this;
    }

    /**
     * Get the dateFormat value.
     *
     * @return the dateFormat value
     */
    public X12DateFormat dateFormat() {
        return this.dateFormat;
    }

    /**
     * Set the dateFormat value.
     *
     * @param dateFormat the dateFormat value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withDateFormat(X12DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    /**
     * Get the timeFormat value.
     *
     * @return the timeFormat value
     */
    public X12TimeFormat timeFormat() {
        return this.timeFormat;
    }

    /**
     * Set the timeFormat value.
     *
     * @param timeFormat the timeFormat value to set
     * @return the X12EnvelopeOverride object itself.
     */
    public X12EnvelopeOverride withTimeFormat(X12TimeFormat timeFormat) {
        this.timeFormat = timeFormat;
        return this;
    }

}
