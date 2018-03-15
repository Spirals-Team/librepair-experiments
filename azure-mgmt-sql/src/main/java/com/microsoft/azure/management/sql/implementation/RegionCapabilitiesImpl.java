/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.sql.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.model.implementation.WrapperImpl;
import com.microsoft.azure.management.sql.CapabilityStatus;
import com.microsoft.azure.management.sql.RegionCapabilities;
import com.microsoft.azure.management.sql.ServerVersionCapability;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation for RegionCapabilities.
 */
@LangDefinition
public class RegionCapabilitiesImpl
    extends WrapperImpl<LocationCapabilitiesInner>
    implements RegionCapabilities {

    private Map<String, ServerVersionCapability> supportedCapabilitiesMap;

    /**
     * Creates an instance of the region capabilities object.
     *
     * @param innerObject the inner object
     */
    public RegionCapabilitiesImpl(LocationCapabilitiesInner innerObject) {
        super(innerObject);
        supportedCapabilitiesMap = new HashMap<>();
        if (this.inner().supportedServerVersions() != null) {
            for (ServerVersionCapability serverVersionCapability : this.inner().supportedServerVersions()) {
                supportedCapabilitiesMap.put(serverVersionCapability.name(), serverVersionCapability);
            }
        }
    }

    @Override
    public Region region() {
        return Region.fromName(this.inner().name());
    }

    @Override
    public CapabilityStatus status() {
        return this.inner().status();
    }

    @Override
    public Map<String, ServerVersionCapability> supportedCapabilitiesByServerVersion() {
        return Collections.unmodifiableMap(this.supportedCapabilitiesMap);
    }
}
