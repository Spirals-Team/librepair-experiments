package io.mincongh.rest;

import io.mincongh.rest.dto.User;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User endpoint.
 *
 * @author Mincong Huang
 */
@Path("user")
public interface UserEndpoint {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  User createUser(@FormParam("name") String name, @FormParam("age") int age);

  @GET
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  User getUser(@PathParam("id") int id);
}
