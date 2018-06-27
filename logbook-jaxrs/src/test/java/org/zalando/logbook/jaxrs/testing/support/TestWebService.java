/*
 * Copyright (C) 2018 HERE Global B.V. with its affiliate(s).
 * All rights reserved.
 *
 * This software with other materials contain proprietary information
 * controlled by HERE with are protected by applicable copyright legislation.
 * Any use with utilization of this software with other materials with
 * disclosure to any third parties is conditional upon having a separate
 * agreement with HERE for the access, use, utilization or disclosure of this
 * software. In the absence of such agreement, the use of the software is not
 * allowed.
 */

package org.zalando.logbook.jaxrs.testing.support;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.glassfish.jersey.media.multipart.FormDataParam;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("testws")
public class TestWebService {

  @GET
  @Path("testGet/{param1}/textPlain")
  @Produces(TEXT_PLAIN)
  public String testGet(
      @PathParam("param1") String param1,
      @QueryParam("param2") String param2
  ) {
    return "param1=" + param1 + " param2=" + param2;
  }

  @POST
  @Path("testPostForm")
  @Produces(TEXT_PLAIN)
  public String testFormPost(
      @FormDataParam("testFileFormField") String testFileFormField,
      @FormDataParam("name") String name,
      @FormDataParam("age") int age
  ) {
    return "name was " + name + " age was " + age + " file was " + testFileFormField;
  }

  @PUT
  @Path("testPutJson")
  @Produces(APPLICATION_JSON)
  public TestModel getJsonPut(
      TestModel testModel
  ) {
    return testModel;
  }
}
