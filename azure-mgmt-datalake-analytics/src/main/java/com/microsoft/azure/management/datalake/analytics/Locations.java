/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.datalake.analytics;

import com.microsoft.azure.CloudException;
import com.microsoft.azure.management.datalake.analytics.models.CapabilityInformation;
import com.microsoft.rest.ServiceCallback;
import com.microsoft.rest.ServiceFuture;
import com.microsoft.rest.ServiceResponse;
import rx.Observable;

/**
 * An instance of this class provides access to all the operations defined
 * in Locations.
 */
public interface Locations {
    /**
     * Gets subscription-level properties and limits for Data Lake Analytics specified by resource location.
     *
     * @param location The resource location without whitespace.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @throws CloudException thrown if the request is rejected by server
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent
     * @return the CapabilityInformation object if successful.
     */
    CapabilityInformation getCapability(String location);

    /**
     * Gets subscription-level properties and limits for Data Lake Analytics specified by resource location.
     *
     * @param location The resource location without whitespace.
     * @param serviceCallback the async ServiceCallback to handle successful and failed responses.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the {@link ServiceFuture} object
     */
    ServiceFuture<CapabilityInformation> getCapabilityAsync(String location, final ServiceCallback<CapabilityInformation> serviceCallback);

    /**
     * Gets subscription-level properties and limits for Data Lake Analytics specified by resource location.
     *
     * @param location The resource location without whitespace.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CapabilityInformation object
     */
    Observable<CapabilityInformation> getCapabilityAsync(String location);

    /**
     * Gets subscription-level properties and limits for Data Lake Analytics specified by resource location.
     *
     * @param location The resource location without whitespace.
     * @throws IllegalArgumentException thrown if parameters fail the validation
     * @return the observable to the CapabilityInformation object
     */
    Observable<ServiceResponse<CapabilityInformation>> getCapabilityWithServiceResponseAsync(String location);

}
