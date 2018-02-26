/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.broker.api.resources;

import com.linkedin.pinot.broker.broker.BrokerServerBuilder;
import com.linkedin.pinot.common.metrics.BrokerMeter;
import com.linkedin.pinot.common.metrics.BrokerMetrics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(tags = "Health")
@Path("/")
public class PinotBrokerHealthCheck {

  @Inject
  private BrokerServerBuilder brokerServerBuilder;

  @Inject
  private BrokerMetrics brokerMetrics;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  @Path("health")
  @ApiOperation(value = "Checking broker health")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Broker is healthy"),
      @ApiResponse(code = 503, message = "Broker is disabled")
  })
  public String getBrokerHealth() {
    if (brokerServerBuilder != null
        && this.brokerServerBuilder.getCurrentState() == BrokerServerBuilder.State.RUNNING) {
      brokerMetrics.addMeteredGlobalValue(BrokerMeter.HEALTHCHECK_OK_CALLS, 1);
      return "OK";
    } else {
      brokerMetrics.addMeteredGlobalValue(BrokerMeter.HEALTHCHECK_BAD_CALLS, 1);
      throw new WebApplicationException("Pinot broker is disabled", Response.Status.SERVICE_UNAVAILABLE);
    }
  }
}
