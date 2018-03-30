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
 * The AS2 agreement mdn settings.
 */
public class AS2MdnSettings {
    /**
     * The value indicating whether to send or request a MDN.
     */
    @JsonProperty(value = "needMdn", required = true)
    private boolean needMdn;

    /**
     * The value indicating whether the MDN needs to be signed or not.
     */
    @JsonProperty(value = "signMdn", required = true)
    private boolean signMdn;

    /**
     * The value indicating whether to send the asynchronous MDN.
     */
    @JsonProperty(value = "sendMdnAsynchronously", required = true)
    private boolean sendMdnAsynchronously;

    /**
     * The receipt delivery URL.
     */
    @JsonProperty(value = "receiptDeliveryUrl")
    private String receiptDeliveryUrl;

    /**
     * The disposition notification to header value.
     */
    @JsonProperty(value = "dispositionNotificationTo")
    private String dispositionNotificationTo;

    /**
     * The value indicating whether to sign the outbound MDN if optional.
     */
    @JsonProperty(value = "signOutboundMdnIfOptional", required = true)
    private boolean signOutboundMdnIfOptional;

    /**
     * The MDN text.
     */
    @JsonProperty(value = "mdnText")
    private String mdnText;

    /**
     * The value indicating whether to send inbound MDN to message box.
     */
    @JsonProperty(value = "sendInboundMdnToMessageBox", required = true)
    private boolean sendInboundMdnToMessageBox;

    /**
     * The signing or hashing algorithm. Possible values include:
     * 'NotSpecified', 'None', 'MD5', 'SHA1', 'SHA2256', 'SHA2384', 'SHA2512'.
     */
    @JsonProperty(value = "micHashingAlgorithm", required = true)
    private HashingAlgorithm micHashingAlgorithm;

    /**
     * Get the needMdn value.
     *
     * @return the needMdn value
     */
    public boolean needMdn() {
        return this.needMdn;
    }

    /**
     * Set the needMdn value.
     *
     * @param needMdn the needMdn value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withNeedMdn(boolean needMdn) {
        this.needMdn = needMdn;
        return this;
    }

    /**
     * Get the signMdn value.
     *
     * @return the signMdn value
     */
    public boolean signMdn() {
        return this.signMdn;
    }

    /**
     * Set the signMdn value.
     *
     * @param signMdn the signMdn value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withSignMdn(boolean signMdn) {
        this.signMdn = signMdn;
        return this;
    }

    /**
     * Get the sendMdnAsynchronously value.
     *
     * @return the sendMdnAsynchronously value
     */
    public boolean sendMdnAsynchronously() {
        return this.sendMdnAsynchronously;
    }

    /**
     * Set the sendMdnAsynchronously value.
     *
     * @param sendMdnAsynchronously the sendMdnAsynchronously value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withSendMdnAsynchronously(boolean sendMdnAsynchronously) {
        this.sendMdnAsynchronously = sendMdnAsynchronously;
        return this;
    }

    /**
     * Get the receiptDeliveryUrl value.
     *
     * @return the receiptDeliveryUrl value
     */
    public String receiptDeliveryUrl() {
        return this.receiptDeliveryUrl;
    }

    /**
     * Set the receiptDeliveryUrl value.
     *
     * @param receiptDeliveryUrl the receiptDeliveryUrl value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withReceiptDeliveryUrl(String receiptDeliveryUrl) {
        this.receiptDeliveryUrl = receiptDeliveryUrl;
        return this;
    }

    /**
     * Get the dispositionNotificationTo value.
     *
     * @return the dispositionNotificationTo value
     */
    public String dispositionNotificationTo() {
        return this.dispositionNotificationTo;
    }

    /**
     * Set the dispositionNotificationTo value.
     *
     * @param dispositionNotificationTo the dispositionNotificationTo value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withDispositionNotificationTo(String dispositionNotificationTo) {
        this.dispositionNotificationTo = dispositionNotificationTo;
        return this;
    }

    /**
     * Get the signOutboundMdnIfOptional value.
     *
     * @return the signOutboundMdnIfOptional value
     */
    public boolean signOutboundMdnIfOptional() {
        return this.signOutboundMdnIfOptional;
    }

    /**
     * Set the signOutboundMdnIfOptional value.
     *
     * @param signOutboundMdnIfOptional the signOutboundMdnIfOptional value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withSignOutboundMdnIfOptional(boolean signOutboundMdnIfOptional) {
        this.signOutboundMdnIfOptional = signOutboundMdnIfOptional;
        return this;
    }

    /**
     * Get the mdnText value.
     *
     * @return the mdnText value
     */
    public String mdnText() {
        return this.mdnText;
    }

    /**
     * Set the mdnText value.
     *
     * @param mdnText the mdnText value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withMdnText(String mdnText) {
        this.mdnText = mdnText;
        return this;
    }

    /**
     * Get the sendInboundMdnToMessageBox value.
     *
     * @return the sendInboundMdnToMessageBox value
     */
    public boolean sendInboundMdnToMessageBox() {
        return this.sendInboundMdnToMessageBox;
    }

    /**
     * Set the sendInboundMdnToMessageBox value.
     *
     * @param sendInboundMdnToMessageBox the sendInboundMdnToMessageBox value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withSendInboundMdnToMessageBox(boolean sendInboundMdnToMessageBox) {
        this.sendInboundMdnToMessageBox = sendInboundMdnToMessageBox;
        return this;
    }

    /**
     * Get the micHashingAlgorithm value.
     *
     * @return the micHashingAlgorithm value
     */
    public HashingAlgorithm micHashingAlgorithm() {
        return this.micHashingAlgorithm;
    }

    /**
     * Set the micHashingAlgorithm value.
     *
     * @param micHashingAlgorithm the micHashingAlgorithm value to set
     * @return the AS2MdnSettings object itself.
     */
    public AS2MdnSettings withMicHashingAlgorithm(HashingAlgorithm micHashingAlgorithm) {
        this.micHashingAlgorithm = micHashingAlgorithm;
        return this;
    }

}
