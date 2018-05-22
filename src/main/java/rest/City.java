package rest;

import com.google.gson.Gson;
import data.DataAccessFactory;
import facades.BookFacade;
import facades.CityFacade;
import httpErrors.GenericExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import interfaces.IBookFacade;
import interfaces.ICityFacade;

/**
 *
 * @author Cherry Rose Semeña & Emmely Lundberg
 */
@Path("city")
public class City {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getCities(@QueryParam("db") String db, @QueryParam("title") String title) throws NotFoundExceptionMapper, Exception {

        /**
         * todo make setting come from Http Headers?
         */
        
        try {
//            System.out.println("THE TITLE IS " + title);
            ICityFacade facade = new CityFacade(new DataAccessFactory(), db);
            List<ICity> list = facade.getCitiesByBookTitle(title);
            String json = new Gson().toJson(list);
            return json;
        } catch (Exception e) {
            throw e;
        }

    }

}
