/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.client;

import com.google.inject.Inject;
import com.sun.jersey.spi.container.ResourceFilters;
import io.druid.server.http.security.StateResourceFilter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Collection of http endpoits to introspect state of HttpServerInventoryView instance for debugging.
 */
@Path("/druid-internal/v1/httpServerInventoryView")
@ResourceFilters(StateResourceFilter.class)
public class HttpServerInventoryViewResource
{
  private final HttpServerInventoryView httpServerInventoryView;

  @Inject
  public HttpServerInventoryViewResource(ServerInventoryView serverInventoryView)
  {
    if (serverInventoryView instanceof HttpServerInventoryView) {
      httpServerInventoryView = (HttpServerInventoryView) serverInventoryView;
    } else {
      this.httpServerInventoryView = null;
    }
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response getDebugInfo()
  {
    if (httpServerInventoryView == null) {
      return Response.status(Response.Status.FORBIDDEN).entity("HttpServerInventoryView is NULL.").build();
    }

    return Response.ok().entity(httpServerInventoryView.getDebugInfo()).build();
  }
}
