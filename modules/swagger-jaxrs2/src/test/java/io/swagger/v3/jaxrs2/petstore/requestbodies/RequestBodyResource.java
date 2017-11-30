package io.swagger.v3.jaxrs2.petstore.requestbodies;

import io.swagger.v3.jaxrs2.resources.model.Pet;
import io.swagger.v3.jaxrs2.resources.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Resource with RequestBody examples
 * Created by rafaellopez on 11/26/17.
 */
public class RequestBodyResource {
    @POST
    @Path("/user")
    @Operation(summary = "Create user",
            description = "This can only be done by the logged in user.")
    public Response methodWithRequestBodyAndTwoParameters(
            @RequestBody(description = "Created user object", required = true,
                    content = @Content(
                            schema = @Schema(implementation = User.class))) User user,
            @QueryParam("name") String name, @QueryParam("code") String code) {
        return Response.ok().entity("").build();
    }

    @PUT
    @Path("/user")
    @Operation(summary = "Modify user",
            description = "Modifying user.")
    public Response methodWithRequestBodyWithoutAnnotation(
            User user) {
        return Response.ok().entity("").build();
    }

    @DELETE
    @Path("/user")
    @Operation(summary = "Delete user",
            description = "This can only be done by the logged in user.")
    public Response methodWithoutRequestBodyAndTwoParameters(
            @QueryParam("name") String name, @QueryParam("code") String code) {
        return Response.ok().entity("").build();
    }

    @PUT
    @Path("/pet")
    @Operation(summary = "Modify pet",
            description = "Modifying pet.")
    @Consumes({"application/json", "application/xml"})
    public Response methodWithRequestBodyWithoutAnnotationAndTwoConsumes(
            User user) {
        return Response.ok().entity("").build();
    }

    @POST
    @Path("/pet")
    @Operation(summary = "Create pet",
            description = "Creating pet.")
    @Consumes({"application/json", "application/xml"})
    public Response methodWithTwoRequestBodyWithoutAnnotationAndTwoConsumes(
            Pet pet, User user) {
        return Response.ok().entity("").build();
    }

    @DELETE
    @Path("/pet")
    @Operation(summary = "Delete pet",
            description = "Deleting pet.")
    @Consumes({"application/json", "application/xml"})
    public Response methodWithOneSimpleRequestBody(int id) {
        return Response.ok().entity("").build();
    }
}
