package io.swagger.v3.jaxrs2.petstore.operations;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Resource With a Default Operation without Annotation
 * Created by rafaellopez on 11/26/17.
 */
public class DefaultOperationResource {
    @Path("/users")
    @GET
    public String getUser() {
        return new String();
    }
}
