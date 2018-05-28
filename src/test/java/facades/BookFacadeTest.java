package facades;

import data.DataAccessFactory;

import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;

import interfaces.IBook;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;



import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;


@RunWith(JUnitParamsRunner.class)
public class BookFacadeTest {

    private DataAccessFactory dataAccessFactory;
    @Before
    public void setUp() throws Exception {
        this.dataAccessFactory = new DataAccessFactory();
    }
    /*Valid*/
    @Test
    @FileParameters("src/test/java/test/resources/valid-cities.csv")
    public void getBooksByCityNameValidInput(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        List<IBook> booksByCityName = bookFacade.getBooksByCityName(city);
        assertThat(booksByCityName.size(),greaterThanOrEqualTo(1));
    }
    /*Valid*/
    @Test
    @FileParameters("src/test/java/test/resources/valid-cities.csv")
    public void getBooksByCityNameValidInputWithSpace(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        city = city + "  ";
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        List<IBook> booksByCityName = bookFacade.getBooksByCityName(city);
        assertThat(booksByCityName.size(),greaterThanOrEqualTo(1));
    }
    /*Invalid*/
    @Test(expected = InvalidInputExceptionMapper.class)
    @FileParameters("src/test/java/test/resources/invalid-input-cities.csv")
    public void getBooksByCityNameInvalidInput(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        bookFacade.getBooksByCityName(city);
    }

    /*NotFound*/
    @Test(expected = NotFoundExceptionMapper.class)
    @FileParameters("src/test/java/test/resources/notfound-cities.csv")
    public void getBooksByCityNameNotFoundInput(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        bookFacade.getBooksByCityName(city);
    }
    /*NotFound*/
    @Test(expected = NotFoundExceptionMapper.class)
    @FileParameters("src/test/java/test/resources/notfound-cities.csv")
    public void getBooksByCityNameNotFoundInputWithSpace(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        city = city + "   ";
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        bookFacade.getBooksByCityName(city);
    }
    /*NotFound*/
    @Test(expected = NotFoundExceptionMapper.class)
    @FileParameters("src/test/java/test/resources/notfound-cities.csv")
    public void getBooksByCityNameNotFoundInputWithSpaceBefore(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        city = " "+city + "   ";
        BookFacade bookFacade = new BookFacade(this.dataAccessFactory,"stub");
        bookFacade.getBooksByCityName(city);
    }


}
