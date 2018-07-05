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

import com.ljd.hackajob.phonebook.datastore.ContactManager;
import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

@Path("/contacts")
@RolesAllowed("user")
public class ContactsAPI {
    private static final String CLASS = ContactsAPI.class.getName();
    private static final Logger LOG = Logger.getLogger(CLASS);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContact(
            @Context UriInfo info,
            @Valid Contact contact) {
        final String METHOD = "createContact";

        LOG.logp(Level.INFO, CLASS, METHOD, "Request: {0}", contact.toString());
        ContactManager mgr = ServiceUtils.getPhonebookService().getContactManager();
        Contact created = mgr.createContact(contact);

        LOG.logp(Level.INFO, CLASS, METHOD,"Created: {0}", created.toString());
        return Response.created(URI.create(info.getRequestUri() + "/" + created.getId().toString())).entity(created).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllContacts(
            @Min(value=0, message="{VER0200}") @QueryParam("_offset") Integer offset,
            @Min(value=1, message="{VER0201}") @Max(value=100, message="{VER0201}") @QueryParam("_limit") Integer limit) {
        if (offset == null) {
            offset = 0;
        }

        if (limit == null) {
            limit = 25;
        }

        ContactManager mgr = ServiceUtils.getPhonebookService().getContactManager();
        Page<Contact> page = mgr.getContacts(offset, limit);
        return Response.ok(page).build();
    }

    @GET
    @Path("/{contactId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getContact(@PathParam("contactId") UUID contactId) throws PhonebookException {
        final String METHOD = "getContact";
        LOG.logp(Level.INFO, CLASS, METHOD, "Get contact {0}", contactId);
        return Response.ok(ServiceUtils.getPhonebookService().getContactManager().getContact(contactId)).build();
    }

    @PUT
    @Path("/{contactId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateContact(
            @PathParam("contactId") UUID contactId,
            @Valid @NotNull Contact updates) throws PhonebookException {
        ContactManager mgr = ServiceUtils.getPhonebookService().getContactManager();
        Contact updated = mgr.updateContact(contactId, updates);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{contactId}")
    public Response deleteContact(@PathParam("contactId") UUID contactId) throws PhonebookException {
        ContactManager mgr = ServiceUtils.getPhonebookService().getContactManager();
        mgr.deleteContact(contactId);
        return Response.noContent().build();
    }
}
