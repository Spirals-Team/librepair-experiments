package data;

import entity.Author;
import entity.Book;
import entity.City;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.ICity;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.List;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Ignore;

@RunWith(JUnitParamsRunner.class)
public class StubUnitTest {

    private DataAccessStub DataAccessStub;

    @Before
    public void setup() {
        this.DataAccessStub = new DataAccessStub();
    }

    /*Valid*/
    @Test
    @FileParameters("src/test/java/test/resources/S1-validinput.csv")
    public void getBooksByCityTest(String city, String bookTitle, String authorFullName) throws NotFoundExceptionMapper {
        List<IBook> books = DataAccessStub.getBooksByCityName(city);
        bookTitle = bookTitle.replace("_", ",");
        assertThat(books,Matchers.<Book>hasItem(Matchers.hasProperty("bookTitle",equalTo(bookTitle))));
    }

    @Test
    @FileParameters("src/test/java/test/resources/S2-validinput.csv")
    public void getCitiesByTitleTest(String title, String name, double lat, double lon) throws NotFoundExceptionMapper {
        
        List<ICity> cities = DataAccessStub.getCitiesByBookTitle(title);
        assertThat(cities,Matchers.<City>hasItem(Matchers.hasProperty("name",equalTo(name))));
        assertThat(cities,Matchers.<City>hasItem(Matchers.hasProperty("lat",equalTo(lat))));
        assertThat(cities,Matchers.<City>hasItem(Matchers.hasProperty("lon",equalTo(lon))));
    }

    @Test
    @FileParameters("src/test/java/test/resources/S3-validinput.csv")
    public void getCitiesMentionedInBookByAuthor(String bookTitle, String authorFullName) throws NotFoundExceptionMapper {
        authorFullName = authorFullName.replace("_", ",");
        List<IBook> books = DataAccessStub.getMentionedCitiesByAuthorName(authorFullName);
        assertThat(books,Matchers.<Book>hasItem(Matchers.hasProperty("bookTitle",equalTo(bookTitle))));
    }

    @Test
    @FileParameters("src/test/java/test/resources/S4-validinput.csv")
    public void getBooksByGeolocation(double lat, double lon, String bookTitle, String authorFullName) throws NotFoundExceptionMapper {
        List<IBook> books = DataAccessStub.getBooksByGeolocation(lat, lon);
        authorFullName = authorFullName.replace("_", ",");
        assertThat(books,Matchers.<Book>hasItem(Matchers.hasProperty("bookTitle",equalTo(bookTitle))));
    }

}
