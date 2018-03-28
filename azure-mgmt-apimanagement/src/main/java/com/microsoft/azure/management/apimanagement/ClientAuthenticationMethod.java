/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.apimanagement;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for ClientAuthenticationMethod.
 */
public final class ClientAuthenticationMethod extends ExpandableStringEnum<ClientAuthenticationMethod> {
    /** Static value Basic for ClientAuthenticationMethod. */
    public static final ClientAuthenticationMethod BASIC = fromString("Basic");

    /** Static value Body for ClientAuthenticationMethod. */
    public static final ClientAuthenticationMethod BODY = fromString("Body");

    /**
     * Creates or finds a ClientAuthenticationMethod from its string representation.
     * @param name a name to look for
     * @return the corresponding ClientAuthenticationMethod
     */
    @JsonCreator
    public static ClientAuthenticationMethod fromString(String name) {
        return fromString(name, ClientAuthenticationMethod.class);
    }

    /**
     * @return known ClientAuthenticationMethod values
     */
    public static Collection<ClientAuthenticationMethod> values() {
        return values(ClientAuthenticationMethod.class);
    }
}
