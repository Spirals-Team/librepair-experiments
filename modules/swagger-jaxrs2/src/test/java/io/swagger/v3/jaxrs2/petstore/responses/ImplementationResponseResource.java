package io.swagger.v3.jaxrs2.petstore.responses;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Resource with a Response at Method Level
 * Created by rafaellopez on 11/27/17.
 */
public class ImplementationResponseResource {
    @GET
    @Path("/")
    @Operation(
            summary = "Simple get operation",
            description = "Defines a simple get operation with no inputs and a complex output object",
            operationId = "getWithPayloadResponse",
            deprecated = true,
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "voila!",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ImplementationResponseResource.SampleResponseSchema.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "default",
                            description = "boo",
                            content = @Content(
                                    mediaType = "*/*",
                                    schema = @Schema(implementation = ImplementationResponseResource.GenericError.class)
                            )
                    )
            }
    )

    public void getResponses() {
    }

    static class SampleResponseSchema {
        @Schema(description = "the user id")
        private String id;
    }

    static class GenericError {
        private int code;
        private String message;
    }
}
