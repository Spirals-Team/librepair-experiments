/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 * Changes may cause incorrect behavior and will be lost if the code is
 * regenerated.
 */

package com.microsoft.azure.management.devices;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The routing related properties of the IoT hub. See:
 * https://docs.microsoft.com/azure/iot-hub/iot-hub-devguide-messaging.
 */
public class RoutingProperties {
    /**
     * The endpoints property.
     */
    @JsonProperty(value = "endpoints")
    private RoutingEndpoints endpoints;

    /**
     * The list of user-provided routing rules that the IoT hub uses to route
     * messages to built-in and custom endpoints. A maximum of 100 routing
     * rules are allowed for paid hubs and a maximum of 5 routing rules are
     * allowed for free hubs.
     */
    @JsonProperty(value = "routes")
    private List<RouteProperties> routes;

    /**
     * The properties of the route that is used as a fall-back route when none
     * of the conditions specified in the 'routes' section are met. This is an
     * optional parameter. When this property is not set, the messages which do
     * not meet any of the conditions specified in the 'routes' section get
     * routed to the built-in eventhub endpoint.
     */
    @JsonProperty(value = "fallbackRoute")
    private FallbackRouteProperties fallbackRoute;

    /**
     * Get the endpoints value.
     *
     * @return the endpoints value
     */
    public RoutingEndpoints endpoints() {
        return this.endpoints;
    }

    /**
     * Set the endpoints value.
     *
     * @param endpoints the endpoints value to set
     * @return the RoutingProperties object itself.
     */
    public RoutingProperties withEndpoints(RoutingEndpoints endpoints) {
        this.endpoints = endpoints;
        return this;
    }

    /**
     * Get the routes value.
     *
     * @return the routes value
     */
    public List<RouteProperties> routes() {
        return this.routes;
    }

    /**
     * Set the routes value.
     *
     * @param routes the routes value to set
     * @return the RoutingProperties object itself.
     */
    public RoutingProperties withRoutes(List<RouteProperties> routes) {
        this.routes = routes;
        return this;
    }

    /**
     * Get the fallbackRoute value.
     *
     * @return the fallbackRoute value
     */
    public FallbackRouteProperties fallbackRoute() {
        return this.fallbackRoute;
    }

    /**
     * Set the fallbackRoute value.
     *
     * @param fallbackRoute the fallbackRoute value to set
     * @return the RoutingProperties object itself.
     */
    public RoutingProperties withFallbackRoute(FallbackRouteProperties fallbackRoute) {
        this.fallbackRoute = fallbackRoute;
        return this;
    }

}
