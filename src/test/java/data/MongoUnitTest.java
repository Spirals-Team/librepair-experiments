package data;

import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import java.util.List;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.hamcrest.CoreMatchers;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 *
 * @author Cherry Rose Semeña
 */
@RunWith(JUnitParamsRunner.class)
public class MongoUnitTest {

    private DataAccessMongoDB dataAccessMongoDB;
    
    @Before
    public void setup(){
        this.dataAccessMongoDB = new DataAccessMongoDB();
    }

    @Test
    @FileParameters("src/test/java/test/resources/S1-validinput-mongodb.csv")
    public void getBooksByCityTest(String city, String title, String author) throws NotFoundExceptionMapper {
        List<IBook> books = dataAccessMongoDB.getBooksByCityName(city);
        assertThat(books.get(0).getTitle(), CoreMatchers.is(equalTo(title)));
        assertThat(books.get(0).getAuthor(), CoreMatchers.is(equalTo(author)));
    }
    
    @Test
    @FileParameters("src/test/java/test/resources/S2-validinput-mongodb.csv")
    public void getCitiesByTitleTest(String title, String name, double lat, double lon) throws NotFoundExceptionMapper {
        List<ICity> cities = dataAccessMongoDB.getCitiesByBookTitle(title);
        assertThat(cities.get(0).getName(), CoreMatchers.is(equalTo(name)));
        assertThat(cities.get(0).getLat(),CoreMatchers.is(equalTo(lat)));
        assertThat(cities.get(0).getLon(),CoreMatchers.is(equalTo(lon)));
    }
    
    @Test
    @FileParameters("src/test/java/test/resources/S3-validinput-mongodb.csv")
    public void getBooksByAuthor(String author, String title) throws NotFoundExceptionMapper {
        List<IBook> books = dataAccessMongoDB.getBooksByAuthorName(author);
        assertThat(books.get(0).getTitle(), CoreMatchers.is(equalTo(title)));
        assertThat(books.get(0).getCities().size(),CoreMatchers.is(greaterThanOrEqualTo(1)));
    }
    
    @Test
    public void getNameTest(){
        String dbname = dataAccessMongoDB.getName();
        assertThat(dbname, is("DataAccessMongoDB"));
    }

}
