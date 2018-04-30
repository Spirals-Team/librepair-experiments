package org.jboss.resteasy.test.rx.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public interface TransformationResource {

   @GET
   @Path("get/string")
   @Produces(MediaType.TEXT_PLAIN)
   public String get() throws InterruptedException;

   @GET
   @Path("get/thing")
   @Produces(MediaType.APPLICATION_JSON)
   public Thing getThing() throws InterruptedException;

   @GET
   @Path("get/thing/list")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> getThingList() throws InterruptedException;

   @PUT
   @Path("put/string")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.TEXT_PLAIN)
   public String put(String s) throws InterruptedException;

   @PUT
   @Path("put/thing")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.APPLICATION_JSON)
   public Thing putThing(String s) throws InterruptedException;

   @PUT
   @Path("put/thing/list")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> putThingList(String s) throws InterruptedException;

   @POST
   @Path("post/string")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.TEXT_PLAIN)
   public String post(String s) throws InterruptedException;

   @POST
   @Path("post/thing")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.APPLICATION_JSON)
   public Thing postThing(String s) throws InterruptedException;

   @POST
   @Path("post/thing/list")
   @Consumes(MediaType.TEXT_PLAIN)
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> postThingList(String s) throws InterruptedException;

   @DELETE
   @Path("delete/string")
   @Produces(MediaType.TEXT_PLAIN)
   public String delete() throws InterruptedException;

   @DELETE
   @Path("delete/thing")
   @Produces(MediaType.APPLICATION_JSON)
   public Thing deleteThing() throws InterruptedException;

   @DELETE
   @Path("delete/thing/list")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> deleteThingList() throws InterruptedException;

   @HEAD
   @Path("head/string")
   @Produces(MediaType.TEXT_PLAIN)
   public String head() throws InterruptedException;

   @OPTIONS
   @Path("options/string")
   @Produces(MediaType.TEXT_PLAIN)
   public String options() throws InterruptedException;

   @OPTIONS
   @Path("options/thing")
   @Produces(MediaType.APPLICATION_JSON)
   public Thing optionsThing() throws InterruptedException;

   @OPTIONS
   @Path("options/thing/list")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> optionsThingList() throws InterruptedException;

   @TRACE
   @Path("trace/string")
   @Produces(MediaType.TEXT_PLAIN)
   public String trace() throws InterruptedException;

   @TRACE
   @Path("trace/thing")
   @Produces(MediaType.APPLICATION_JSON)
   public Thing traceThing() throws InterruptedException;

   @TRACE
   @Path("trace/thing/list")
   @Produces(MediaType.APPLICATION_JSON)
   public List<Thing> traceThingList() throws InterruptedException;

   @GET
   @Path("exception/unhandled")
   public Thing exceptionUnhandled() throws Exception;

   @GET
   @Path("exception/handled")
   public Thing exceptionHandled() throws Exception;
}
