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

import com.linkedin.pinot.controller.ControllerConf;
import com.linkedin.pinot.controller.helix.core.PinotHelixResourceManager;
import com.linkedin.pinot.controller.util.TableSizeReader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Api(tags = Constants.TABLE_TAG)
@Path("/")
public class TableSize {
  private static Logger LOGGER = LoggerFactory.getLogger(TableSize.class);

  @Inject
  ControllerConf _controllerConf;
  @Inject
  PinotHelixResourceManager _pinotHelixResourceManager;
  @Inject
  Executor _executor;
  @Inject
  HttpConnectionManager _connectionManager;

  @GET
  @Path("/tables/{tableName}/size")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Read table sizes",
      notes = "Get table size details. Table size is the size of untarred segments including replication")
  @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
      @ApiResponse(code = 404, message = "Table not found"),
      @ApiResponse(code = 500, message = "Internal server error")})
  public TableSizeReader.TableSizeDetails getTableSize(
      @ApiParam(value = "Table name without type", required = true, example = "myTable | myTable_OFFLINE")
          @PathParam("tableName") String tableName,
      @ApiParam(value = "Get detailed information", required = false) @DefaultValue("true")
          @QueryParam("detailed") boolean detailed
  ) {
    TableSizeReader
        tableSizeReader = new TableSizeReader(_executor, _connectionManager, _pinotHelixResourceManager);
    TableSizeReader.TableSizeDetails tableSizeDetails = null;
    try {
      tableSizeDetails = tableSizeReader.getTableSizeDetails(tableName,
          _controllerConf.getServerAdminRequestTimeoutSeconds() * 1000);
    } catch (Throwable t) {
      throw new ControllerApplicationException(LOGGER, String.format("Failed to read table size for %s", tableName),
          Response.Status.INTERNAL_SERVER_ERROR, t);
    }

    if (tableSizeDetails == null) {
      throw new ControllerApplicationException(LOGGER, "Table " + tableName + " not found",
          Response.Status.NOT_FOUND);
    }
    return tableSizeDetails;
  }
}
