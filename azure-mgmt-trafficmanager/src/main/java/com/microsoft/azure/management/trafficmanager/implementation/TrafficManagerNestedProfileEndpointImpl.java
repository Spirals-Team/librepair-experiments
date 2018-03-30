/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */
package com.microsoft.azure.management.trafficmanager.implementation;

import com.microsoft.azure.management.apigeneration.LangDefinition;
import com.microsoft.azure.management.resources.fluentcore.arm.Region;
import com.microsoft.azure.management.resources.fluentcore.utils.Utils;
import com.microsoft.azure.management.trafficmanager.TrafficManagerNestedProfileEndpoint;

/**
 * Implementation for {@link TrafficManagerNestedProfileEndpoint}.
 */
@LangDefinition
class TrafficManagerNestedProfileEndpointImpl extends TrafficManagerEndpointImpl
    implements TrafficManagerNestedProfileEndpoint {
    TrafficManagerNestedProfileEndpointImpl(String name,
                                            TrafficManagerProfileImpl parent,
                                            EndpointInner inner,
                                            EndpointsInner client) {
        super(name, parent, inner, client);
    }

    @Override
    public String nestedProfileId() {
        return inner().targetResourceId();
    }

    @Override
    public long minimumChildEndpointCount() {
        return Utils.toPrimitiveLong(inner().minChildEndpoints());
    }

    @Override
    public Region sourceTrafficLocation() {
        return Region.fromName((inner().endpointLocation()));
    }
}
