package data;

import entity.Author;
import entity.Book;
import entity.City;
import interfaces.IDataAccessor;
import interfaces.IBook;
import interfaces.ICity;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import static org.neo4j.driver.v1.Values.parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataAccessNeo4J implements IDataAccessor {

    private String name = "DataAccessNeo4J";
    private DBConnectorNeo4J dbConnectorNeo4J = new DBConnectorNeo4J();

    public DataAccessNeo4J() {
        this.name = "Neo4J";
    }

    public StatementResult getUserStory1Query(String cityName) {
        Driver driver = dbConnectorNeo4J.getDriver();

        String query = "MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]-(c:City) "
                + "WHERE LOWER(c.name) = LOWER($cityName) "
                + "RETURN a,b ORDER BY b.bookTitle";
        Session session = driver.session();
        StatementResult result = session.run(query, parameters("cityName", cityName));
        session.close();
        return result;
    }

    public StatementResult getUserStory2Query(String bookTitle) {
        Driver driver = dbConnectorNeo4J.getDriver();

        String query = "MATCH (b:Book)-[r:MENTIONS]->(c:City) "
                + "WHERE LOWER(b.bookTitle) = LOWER($bookTitle) "
                + "RETURN c ORDER BY c.name";

        Session session = driver.session();

        StatementResult result = session.run(query, parameters("bookTitle", bookTitle));
        session.close();
        return result;
    }

    public StatementResult getUserStory3Query(String authorName) {
        Driver driver = dbConnectorNeo4J.getDriver();

        String query = "MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]->(c:City) "
                + "WHERE LOWER(a.fullName) = LOWER($fullName) "
                + "RETURN a,b,collect(c) ORDER BY a.fullName";

        Session session = driver.session();

        StatementResult result = session.run(query, parameters("fullName", authorName));
        session.close();
        return result;
    }

    public StatementResult getUserStory4Query(double lat, double lon) {
        Driver driver = dbConnectorNeo4J.getDriver();
        String query = "MATCH (a:Author)-[ra:AUTHORED]->(b:Book)-[r:MENTIONS ]->(c:City)"
                + " WHERE c.lat = $lat AND c.lon = $lon "
                + "RETURN a,b,collect(c) ORDER BY a.fullName";
        

        Session session = driver.session();

        StatementResult result = session.run(query, parameters("lon", lon, "lat", lat));
        session.close();
        return result;
    }

    @Override
    public List<IBook> getBooksByCityName(String cityName) { // #Userstory 1
        List<IBook> list = new ArrayList();

        try {
            StatementResult result = getUserStory1Query(cityName);
            list = getResults(result);
        } catch (Exception e) {
            //if (DEBUG) e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<ICity> getCitiesByBookTitle(String bookTitle) { // #Userstory 2
        List<ICity> list = new ArrayList();

        try {
            StatementResult result = getUserStory2Query(bookTitle);
            list = getResultsCities(result);
        } catch (Exception e) {
             e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<IBook> getMentionedCitiesByAuthorName(String authorName) { // #Userstory 3
        List<IBook> list = new ArrayList<>();

        try {
            StatementResult result = getUserStory3Query(authorName);

            list = getBooksWithCities(result);

            /*
         * todo Clean up exception handling.
         * Failing quietly on all exceptions is no good for debugging.
             */
        } catch (Exception e) {
          e.printStackTrace();
        }
        return list;
    }

    public List<IBook> getBooksByGeolocation(double lat, double lon) { // #Userstory 4
        List<IBook> list = new ArrayList<>();

        try {
            StatementResult result = getUserStory4Query(lat, lon);
            list = getBooksWithCities(result);
            /*
         * todo Clean up exception handling.
         * Failing quietly on all exceptions is no good for debugging.
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<IBook> getResults(StatementResult result) {
        List<IBook> list = new ArrayList();
        while (result.hasNext()) {
            Record record = result.next();
            IBook b = new Book();
            b.setId(record.get("b").get("bookID").asString());
            b.setTitle(record.get("b").get("bookTitle").asString());
            Author a = new Author();
            a.setAuthorID(record.get("a").get("authorID").asString());
            a.setFullName(record.get("a").get("fullName").asString());
//            a.setSurName(record.get("a").get("surName").asString());
//            a.setFirstName(record.get("a").get("firstName").asString());
//            a.setSurName(record.get("a").get("title").asString());
            b.setAuthor(a);
            list.add(b);
        }
        return list;
    }

    private List<ICity> getResultsCities(StatementResult result) {

        List<ICity> list = new ArrayList();
        while (result.hasNext()) {
            Record record = result.next();
            ICity c = new City();
            c.setName(record.get("c").get("name").asString());
            c.setLat(record.get("c").get("lat").asDouble());
            c.setLon(record.get("c").get("lon").asDouble());
            list.add(c);
        }
        return list;
    }

    public void close() {
        Driver driver = dbConnectorNeo4J.getDriver();
        driver.close();

    }

    private List<IBook> getBooksWithCities(StatementResult result) {
        List<IBook> list = new ArrayList();
        while (result.hasNext()) {
            Record record = result.next();
            IBook b = new Book();
            b.setId(record.get("b").get("bookID").asString());
            b.setTitle(record.get("b").get("bookTitle").asString());
            Author a = new Author();
            a.setAuthorID(record.get("a").get("authorID").asString());
            a.setFullName(record.get("a").get("fullName").asString());
//            a.setSurName(record.get("a").get("surName").asString());
//            a.setFirstName(record.get("a").get("firstName").asString());
//            a.setSurName(record.get("a").get("title").asString());
            b.setAuthor(a);

            List<ICity> cityList = new ArrayList();

            for (int i = 0; i < record.get("collect(c)").size(); i++) {
                String name = record.get("collect(c)").get(i).get("name").asString();
                Double lon = record.get("collect(c)").get(i).get("lon").asDouble();
                Double lat = record.get("collect(c)").get(i).get("lat").asDouble();
                ICity city = new City(name, lat, lon);
                cityList.add(city);
            }

            b.setCities(cityList);
            list.add(b);
        }
        return list;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
