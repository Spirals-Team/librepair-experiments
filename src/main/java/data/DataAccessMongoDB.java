package data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import entity.Book;
import entity.City;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import interfaces.IDataAccessor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.neo4j.driver.v1.StatementResult;

/**
 *
 * @author Cherry Rose Semeña
 */
public class DataAccessMongoDB implements IDataAccessor {

    private String name = "DataAccessMongoDB";
    private DBConnectorMongoDB connector = null;
    private static MongoClient con = null;
    private String dbname = "gutenberg";
    private String col = "books2";

    public DataAccessMongoDB() {
        this.connector = new DBConnectorMongoDB();
        this.con = connector.getConnection();
    }

    @Override
    public List<IBook> getBooksByCityName(String cityName) throws NotFoundExceptionMapper {
        //System.out.println("THE CITY___________" + cityName + "__________________");
        try {
            List<IBook> books = new ArrayList();
            ObjectMapper mapper = new ObjectMapper();
            FindIterable<Document> findIterable = getUserStory1Query(cityName);
            for (Document document : findIterable) {
                String jsonStr = document.toJson();
                //System.out.println("THE JSON STRING IS " + jsonStr);
                IBook b = mapper.readValue(jsonStr, Book.class);
                //printBook((Book) b);
                books.add(b);
            }
            return books;
        } catch (Exception e) {
            throw new NotFoundExceptionMapper(e.getMessage());
        }
    }

    //just a helper method
    public void printBook(Book b) {
        System.out.println("THE BOOK >>>" + b.toString());
        for (int i = 0; i < b.getCities().size(); i++) {
            System.out.println(i + ": " + b.getCities().get(i).toString());
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<ICity> getCitiesByBookTitle(String bookTitle) throws NotFoundExceptionMapper {

        try {
            List<ICity> cities = new ArrayList();
            ObjectMapper mapper = new ObjectMapper();
            FindIterable<Document> findIterable = getUserStory2Query(bookTitle);
            for (Document document : findIterable) {
                String jsonStr = document.toJson();
                //System.out.println("THE JSON STRING IS " + jsonStr);
                IBook b = mapper.readValue(jsonStr, Book.class);
                for (ICity c : b.getCities()) {
                    cities.add(c);
                }
            }
            return cities;
        } catch (Exception e) {
            System.out.println("ERROR HERE" + e.getMessage());
            throw new NotFoundExceptionMapper(e.getMessage());
        }
    }

    public List<IBook> getMentionedCitiesByAuthorName(String authorFullName) throws NotFoundExceptionMapper {
        List<IBook> list = new ArrayList();

        try {
            List<IBook> books = new ArrayList();
            ObjectMapper mapper = new ObjectMapper();
            FindIterable<Document> findIterable = getUserStory3Query(authorFullName);
            for (Document document : findIterable) {
                String jsonStr = document.toJson();
                //System.out.println("THE JSON STRING IS " + jsonStr);
                IBook b = mapper.readValue(jsonStr, Book.class);
                //printBook((Book) b);
                books.add(b);
            }
            return books;
        } catch (Exception e) {
            throw new NotFoundExceptionMapper(e.getMessage());
        }
    }

    @Override
    public List<IBook> getBooksByGeolocation(double lat, double lon) throws NotFoundExceptionMapper {
        List<IBook> list = new ArrayList();

        try {
            List<IBook> books = new ArrayList();
            ObjectMapper mapper = new ObjectMapper();
            FindIterable<Document> findIterable = getUserStory4Query(lat, lon);
            //filter object before here how..
            //FindIterable<Document> findIterable = coll.find(Filters.elemMatch("cities",in("lat",lat))); //
            for (Document document : findIterable) {
                String jsonStr = document.toJson();
                //System.out.println("THE JSON STRING IS " + jsonStr);
                IBook b = mapper.readValue(jsonStr, Book.class);
                ICity theCity = null;
                List<ICity> cityList = new ArrayList();
                for (ICity city : b.getCities()) {
                    if (city.getLat() == lat && city.getLon() == lon) {
                        theCity = city;
                    }
                }//it would be smarter if we can map the Matched city only but i dont know how
                //incredible stupid solution but it is what i can find for now
                cityList.add(theCity);
                b.setCities(cityList);
                books.add(b);
            }
            return books;
        } catch (Exception e) {
            throw new NotFoundExceptionMapper(e.getMessage());
        }
    }

    FindIterable<Document> getUserStory1Query(String cityName) {
        MongoDatabase database = con.getDatabase(dbname);
        MongoCollection coll = database.getCollection(col);
        FindIterable<Document> findIterable = coll.find(in("cities.name", cityName));
        return findIterable;
    }
     FindIterable<Document> getUserStory2Query(String bookTitle) {
        MongoDatabase database = con.getDatabase(dbname);
        MongoCollection coll = database.getCollection(col);
            FindIterable<Document> findIterable = coll.find(eq("bookTitle", bookTitle));
        return findIterable;
    }

      FindIterable<Document> getUserStory3Query(String authorFullName) {
        MongoDatabase database = con.getDatabase(dbname);
        MongoCollection coll = database.getCollection(col);
        FindIterable<Document> findIterable = coll.find(eq("author.fullName", authorFullName));
        return findIterable;
    }

       FindIterable<Document> getUserStory4Query(double lat, double lon) {
        MongoDatabase database = con.getDatabase(dbname);
        MongoCollection coll = database.getCollection(col);
        FindIterable<Document> findIterable = coll.find(in("cities.lat", lat, "cities.lon", lon));
        return findIterable;
    }


}
