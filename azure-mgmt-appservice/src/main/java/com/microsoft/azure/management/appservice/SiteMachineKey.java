/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * MachineKey of an app.
 */
public class SiteMachineKey {
    /**
     * MachineKey validation.
     */
    @JsonProperty(value = "validation")
    private String validation;

    /**
     * Validation key.
     */
    @JsonProperty(value = "validationKey")
    private String validationKey;

    /**
     * Algorithm used for decryption.
     */
    @JsonProperty(value = "decryption")
    private String decryption;

    /**
     * Decryption key.
     */
    @JsonProperty(value = "decryptionKey")
    private String decryptionKey;

    /**
     * Get the validation value.
     *
     * @return the validation value
     */
    public String validation() {
        return this.validation;
    }

    /**
     * Set the validation value.
     *
     * @param validation the validation value to set
     * @return the SiteMachineKey object itself.
     */
    public SiteMachineKey withValidation(String validation) {
        this.validation = validation;
        return this;
    }

    /**
     * Get the validationKey value.
     *
     * @return the validationKey value
     */
    public String validationKey() {
        return this.validationKey;
    }

    /**
     * Set the validationKey value.
     *
     * @param validationKey the validationKey value to set
     * @return the SiteMachineKey object itself.
     */
    public SiteMachineKey withValidationKey(String validationKey) {
        this.validationKey = validationKey;
        return this;
    }

    /**
     * Get the decryption value.
     *
     * @return the decryption value
     */
    public String decryption() {
        return this.decryption;
    }

    /**
     * Set the decryption value.
     *
     * @param decryption the decryption value to set
     * @return the SiteMachineKey object itself.
     */
    public SiteMachineKey withDecryption(String decryption) {
        this.decryption = decryption;
        return this;
    }

    /**
     * Get the decryptionKey value.
     *
     * @return the decryptionKey value
     */
    public String decryptionKey() {
        return this.decryptionKey;
    }

    /**
     * Set the decryptionKey value.
     *
     * @param decryptionKey the decryptionKey value to set
     * @return the SiteMachineKey object itself.
     */
    public SiteMachineKey withDecryptionKey(String decryptionKey) {
        this.decryptionKey = decryptionKey;
        return this;
    }

}
