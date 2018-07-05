package com.ljd.hackajob.phonebook.api;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ljd.hackajob.phonebook.datastore.PhoneNumberManager;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.PhoneNumber;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

@Path("/contacts/{contactId}/phonenumbers")
@RolesAllowed("user")
public class PhoneNumbersAPI {
    private static final String CLASS = PhoneNumbersAPI.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPhoneNumberToContact(
            @Context UriInfo info,
            @PathParam("contactId") UUID contactId,
            @Valid PhoneNumber number) throws PhonebookException {
        final String METHOD = "addPhoneNumberToContact";

        LOG.logp(Level.INFO, CLASS, METHOD, "Request: {0}", number.toString());
        PhoneNumberManager mgr = ServiceUtils.getPhonebookService().getPhoneNumberManager();
        PhoneNumber created = mgr.addPhoneNumberToContact(contactId, number);

        LOG.logp(Level.INFO, CLASS, METHOD,"Created: {0}", created.toString());
        return Response.created(URI.create(info.getRequestUri() + "/" + created.getType())).entity(created).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPhoneNumbersForContact(
            @PathParam("contactId") UUID contactId,
            @Min(value=0, message="{VER0200}") @QueryParam("_offset") Integer offset,
            @Min(value=1, message="{VER0201}") @Max(value=100, message="{VER0201}") @QueryParam("_limit") Integer limit) throws PhonebookException {
        if (offset == null) {
            offset = 0;
        }

        if (limit == null) {
            limit = 25;
        }

        PhoneNumberManager mgr = ServiceUtils.getPhonebookService().getPhoneNumberManager();
        Page<PhoneNumber> page = mgr.getPhoneNumbersForContact(contactId, offset, limit);
        return Response.ok(page).build();
    }

    @GET
    @Path("/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPhoneNumberForContact(@PathParam("contactId") UUID contactId, @PathParam("type") String type) throws PhonebookException {
        final String METHOD = "getPhoneNumberForContact";
        LOG.logp(Level.INFO, CLASS, METHOD, "Get contact {0}", contactId);
        return Response.ok(ServiceUtils.getPhonebookService().getPhoneNumberManager().getPhoneNumerForContact(contactId, type)).build();
    }

    @PUT
    @Path("/{type}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(
            @PathParam("contactId") UUID contactId,
            @PathParam("type") String type,
            @Valid @NotNull PhoneNumber updates) throws PhonebookException {
        PhoneNumberManager mgr = ServiceUtils.getPhonebookService().getPhoneNumberManager();
        PhoneNumber updated = mgr.updatePhoneNumberForContact(contactId, type, updates);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{type}")
    public Response deleteContact(@PathParam("contactId") UUID contactId, @PathParam("type") String type) throws PhonebookException {
        PhoneNumberManager mgr = ServiceUtils.getPhonebookService().getPhoneNumberManager();
        mgr.deletePhoneNumberForContact(contactId, type);
        return Response.noContent().build();
    }
}
