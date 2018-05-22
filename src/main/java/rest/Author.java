
package rest;

import com.google.gson.Gson;
import data.DataAccessFactory;
import facades.BookFacade;
import facades.CityFacade;
import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.IBookFacade;
import interfaces.ICityFacade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Path("author")
public class Author {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBooksAndCitiesByAuthor(@QueryParam("db") String db, @QueryParam("author") String author) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        List<IBook> books = new ArrayList<>();

        IBookFacade bookFacade = new BookFacade(new DataAccessFactory(), db);
        ICityFacade cityFacade = new CityFacade(new DataAccessFactory(), db);
        books = bookFacade.getBooksByAuthorName(author);


        for(IBook book : books){
            book.setCities(cityFacade.getCitiesByBookTitle(book.getTitle()));
        }


        String json = new Gson().toJson(books);
        return json;
    }
}
