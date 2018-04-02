/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice;

import java.util.Collection;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.microsoft.rest.ExpandableStringEnum;

/**
 * Defines values for SkuName.
 */
public final class SkuName extends ExpandableStringEnum<SkuName> {
    /** Static value Free for SkuName. */
    public static final SkuName FREE = fromString("Free");

    /** Static value Shared for SkuName. */
    public static final SkuName SHARED = fromString("Shared");

    /** Static value Basic for SkuName. */
    public static final SkuName BASIC = fromString("Basic");

    /** Static value Standard for SkuName. */
    public static final SkuName STANDARD = fromString("Standard");

    /** Static value Premium for SkuName. */
    public static final SkuName PREMIUM = fromString("Premium");

    /** Static value PremiumV2 for SkuName. */
    public static final SkuName PREMIUM_V2 = fromString("PremiumV2");

    /** Static value Dynamic for SkuName. */
    public static final SkuName DYNAMIC = fromString("Dynamic");

    /** Static value Isolated for SkuName. */
    public static final SkuName ISOLATED = fromString("Isolated");

    /**
     * Creates or finds a SkuName from its string representation.
     * @param name a name to look for
     * @return the corresponding SkuName
     */
    @JsonCreator
    public static SkuName fromString(String name) {
        return fromString(name, SkuName.class);
    }

    /**
     * @return known SkuName values
     */
    public static Collection<SkuName> values() {
        return values(SkuName.class);
    }
}
