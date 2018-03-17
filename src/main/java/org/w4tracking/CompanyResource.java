package org.w4tracking;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.w4tracking.models.CompanyModel;
import org.w4tracking.models.CompanyProvider;
import org.w4tracking.models.utils.ModelToRepresentation;
import org.w4tracking.representations.idm.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Stateless
@Path("/companies")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Company Resource", description = "Company REST API", consumes = "application/json")
public class CompanyResource {

    @Inject
    private CompanyProvider companyProvider;

    @Context
    private UriInfo uriInfo;

    @POST
    @ApiOperation(value = "Create Company")
    public Response createCompany(ItemRepresentation<CompanyAttributesRepresentation> rep) {
        CompanyAttributesRepresentation attributes = rep.getData().getAttributes();
        CompanyModel company = companyProvider.addCompany(attributes.getName());

        URI self = uriInfo.getBaseUriBuilder()
                .path(CompanyResource.class, "getCompany")
                .build(company.getId());
        LinksRepresentation links = new LinksRepresentation.Builder().withSelf(self).build();
        CompanyAttributesRepresentation companyAttributes = ModelToRepresentation.toRepresentation(company, true);
        DataRepresentation<AttributesRepresentation> data = ResourceUtils.buildData(company, companyAttributes, links);
        return Response.created(self).entity(data).build();
    }

    @PUT
    @ApiOperation(value = "Update Company")
    public void updateCompany(ItemRepresentation<CompanyAttributesRepresentation> rep) {
        throw new UnsupportedOperationException();
    }

    @GET
    @ApiOperation(value = "Company list")
    public CollectionRepresentation<CompanyAttributesRepresentation> getCompanies() {
        throw new UnsupportedOperationException();
    }

    @GET
    @Path("/{companyId}")
    @ApiOperation(value = "Get Company")
    public ItemRepresentation<CompanyAttributesRepresentation> getCompany(String companyId) {
        throw new UnsupportedOperationException();
    }

}
