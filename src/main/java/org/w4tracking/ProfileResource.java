package org.w4tracking;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.w4tracking.models.UserModel;
import org.w4tracking.models.UserProvider;
import org.w4tracking.models.utils.ModelToRepresentation;
import org.w4tracking.representations.idm.*;
import org.w4tracking.security.SecurityContext;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Optional;

@Stateless
@Path("/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Profile Resource", description = "Profile REST API", consumes = "application/json")
public class ProfileResource {

    @Context
    private UriInfo uriInfo;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private UserProvider userProvider;

    @GET
    @Path("/")
    @ApiOperation(value = "Get Profile")
    public ItemRepresentation<UserAttributesRepresentation> getProfile() {
        String username = securityContext.getUsername();
        String identityId = securityContext.getIdentityId();
        String identityProvider = securityContext.getIdentityProviderAlias();

        Optional<UserModel> optionalUser = userProvider.getUserByIdentityId(identityId);

        UserModel user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            user = userProvider.addUser(username, identityId, identityProvider);
            user.setEmail(securityContext.getEmail());
            user.setFullName(securityContext.getFullName());
        }

        URI self = uriInfo.getBaseUriBuilder()
                .path(ProfileResource.class)
                .path(ProfileResource.class, "getProfile")
                .build();
        LinksRepresentation links = new LinksRepresentation.Builder().withSelf(self).build();
        UserAttributesRepresentation userAttributes = ModelToRepresentation.toRepresentation(user, true);
        DataRepresentation<UserAttributesRepresentation> data = ResourceUtils.buildData(user, userAttributes, links);
        return new ItemRepresentation.Builder<UserAttributesRepresentation>().withData(data).build();
    }

}
