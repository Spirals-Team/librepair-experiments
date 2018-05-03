/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend.api;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Stanislav
 */
@Path("car")
public class CarResource {

    @Context
    private UriInfo context;

    //TODO: TEST STUFF REMOVE LATER//
    CarRemoteFetchFacade carRemote = new CarRemoteFetchFacade();

    /**
     * Creates a new instance of CarResource
     */
    public CarResource() {
    }

    /**
     * Retrieves representation of an instance of backend.api.CarResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        System.out.println(carRemote.getAll());
        return carRemote.getAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/regno/{regno}")
    public String getThroughRegNo(@PathParam("regno")String regno) {
        return carRemote.getByRegNo(regno.toUpperCase());
    }
}
