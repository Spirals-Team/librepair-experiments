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
package com.linkedin.pinot.controller.api.resources;

import com.linkedin.pinot.common.data.Schema;
import com.linkedin.pinot.controller.helix.core.PinotHelixResourceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Api(tags = Constants.SCHEMA_TAG)
@Path("/")
public class PinotTableSchema {
  private static final Logger LOGGER = LoggerFactory.getLogger(PinotTableSchema.class);

  @Inject
  PinotHelixResourceManager pinotHelixResourceManager;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("/tables/{tableName}/schema")
  @ApiOperation(value = "Get table schema", notes = "Read table schema")
  @ApiResponses(value = {@ApiResponse(code=200, message = "Success"),
      @ApiResponse(code = 404, message = "Table not found")})
  public String getTableSchema(
      @ApiParam(value = "Table name (without type)", required = true) @PathParam("tableName") String tableName
  ) {
    Schema schema = pinotHelixResourceManager.getTableSchema(tableName);
    if (schema != null) {
      return schema.getJSONSchema();
    }
    throw new ControllerApplicationException(LOGGER, "Schema not found for table: " + tableName,
         Response.Status.NOT_FOUND);
  }
}
