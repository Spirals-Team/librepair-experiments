/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.daemon.ui.exceptionmappers;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.thrift.TException;
import org.json.simple.JSONValue;

@Provider
public class TExceptionMapper implements ExceptionMapper<TException> {

    /**
     * getResponse.
     * @param ex ex
     * @param responseStatus responseStatus
     * @return getResponse
     */
    public static Response getResponse(Exception ex, Response.Status responseStatus) {
        Response.ResponseBuilder builder = Response.status(responseStatus);
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.toString());
        body.put("errorMessage", ex.toString());
        return builder.entity(JSONValue.toJSONString(body)).type("application/json").build();
    }

    /**
     * getResponse.
     * @param ex ex
     * @return getResponse
     */
    public static Response getResponse(AuthorizationException ex) {
        Response.ResponseBuilder builder = Response.status(Response.Status.UNAUTHORIZED);
        Map<String, String> body = new HashMap<>();
        body.put("error", ex.toString());
        body.put("errorMessage", ex.get_msg());
        return builder.entity(JSONValue.toJSONString(body)).type("application/json").build();
    }

    /**
     * getResponse.
     * @param ex ex
     * @return getResponse
     */
    public static Response getResponse(Exception ex) {
        return getResponse(ex, Response.Status.INTERNAL_SERVER_ERROR);
    }

    /**
     * toResponse.
     * @param ex ex
     * @return toResponse
     */
    public Response toResponse(TException ex) {
        return getResponse(ex);
    }
}
