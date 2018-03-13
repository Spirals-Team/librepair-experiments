package org.w4tracking;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.w4tracking.representations.idm.CollectionRepresentation;
import org.w4tracking.representations.idm.CompanyAttributes;
import org.w4tracking.representations.idm.ItemRepresentation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Company Resource", description = "Company REST API", consumes = "application/json")
public interface CompanyResource {

    @POST
    @ApiOperation(value = "Create Company")
    Response createCompany(ItemRepresentation<CompanyAttributes> rep);

    @PUT
    @ApiOperation(value = "Update Company")
    void updateCompany(ItemRepresentation<CompanyAttributes> rep);

    @GET
    @ApiOperation(value = "Company list")
    ItemRepresentation<CompanyAttributes> getCompanies();

    @GET
    @Path("/{companyId}")
    @ApiOperation(value = "Get Company")
    CollectionRepresentation<CompanyAttributes> getCompany(@PathParam("companyId") String companyId);

}
