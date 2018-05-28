package facades;

import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IBook;
import interfaces.IDataAccessFactory;
import interfaces.IDataAccessor;
import junitparams.FileParameters;
import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.verify;

import static org.mockito.internal.verification.VerificationModeFactory.times;


@RunWith(JUnitParamsRunner.class)
public class BookFacadeParameterizedBehaviourTest {
    @Mock
    IDataAccessor iDataAcessMock;
    @Mock
    IDataAccessFactory iDataAcessFactoryMock;

    @Before
    public void setUp() throws Exception {
        this.iDataAcessMock = mock(IDataAccessor.class);
        this.iDataAcessFactoryMock = mock(IDataAccessFactory.class);
    }
    /*Valid*/
    @Test
    @FileParameters("src/test/java/test/resources/valid-cities.csv")
    public void getBooksByCityNameValidInputBehaviour1(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        List<IBook> books = new ArrayList<IBook>(); //push some book elements here
        try {
            when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
            when(iDataAcessMock.getBooksByCityName((eq(city)))).thenReturn(books);
            BookFacade bookFacade = new BookFacade(iDataAcessFactoryMock, "stub");
            assertTrue(bookFacade.getBooksByCityName(city).equals(books));
            verify(iDataAcessFactoryMock, times(1)).getDataAccessor("stub");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @FileParameters("src/test/java/test/resources/valid-cities.csv")
    public void getBooksByCityNameValidInputBehaviour2(String city) {
        List<IBook> books = new ArrayList<IBook>();
        try {
            when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
            when(iDataAcessMock.getBooksByCityName((eq(city)))).thenReturn(books);
            BookFacade bookFacade = new BookFacade(iDataAcessFactoryMock, "stub");
            assertTrue(bookFacade.getBooksByCityName(city).equals(books));
            verify(iDataAcessMock, times(1)).getBooksByCityName(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*Invalid*/
    @Test(expected = InvalidInputExceptionMapper.class)
    @FileParameters("src/test/java/test/resources/invalid-input-cities.csv")
    public void getBooksByCityNameInvalidInput(String city) throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        iDataAcessMock = mock(IDataAccessor.class);
        List<IBook> books = new ArrayList<IBook>();
        when(iDataAcessMock.getBooksByCityName((eq(city)))).thenReturn(books);
        iDataAcessFactoryMock = mock(IDataAccessFactory.class);
        when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
        BookFacade bookFacade = new BookFacade(iDataAcessFactoryMock, "stub");
        assertTrue(bookFacade.getBooksByCityName(city).equals(books));
    }




}
