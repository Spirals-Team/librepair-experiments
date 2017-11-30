package io.swagger.v3.jaxrs2.petstore.tags;

import io.swagger.v3.oas.annotations.Operation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Resource with a Tag at Operation Level
 * Created by rafaellopez on 11/26/17.
 */
public class TagOperationResource {

    @GET
    @Path("/")
    @Operation(tags = {"Example Tag", "Second Tag"})
    public Response getTags() {
        return Response.ok().entity("ok").build();
    }
}
