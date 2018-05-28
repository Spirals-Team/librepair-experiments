package facades;

import httpErrors.InvalidInputExceptionMapper;
import httpErrors.NotFoundExceptionMapper;
import interfaces.IDataAccessFactory;
import org.junit.Test;
import interfaces.ICity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import interfaces.IDataAccessor;
import org.junit.Before;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CityFacadeTest {

    @Mock
    IDataAccessor iDataAcessMock;
    @Mock
    IDataAccessFactory iDataAcessFactoryMock;

    @Before
    public void setUp() throws Exception {
        this.iDataAcessMock = mock(IDataAccessor.class);
        this.iDataAcessFactoryMock = mock(IDataAccessFactory.class);
    }

    @Test
    public void getCitiesByBookTitle() throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        List<ICity> cities = new ArrayList<ICity>();
        try{
        String title = "Harry Potter 2";
        when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
        when(iDataAcessMock.getCitiesByBookTitle((eq(title)))).thenReturn(cities);
        CityFacade cityFacade = new CityFacade(iDataAcessFactoryMock, "stub");
        assertTrue(cityFacade.getCitiesByBookTitle(title).equals(cities));
        verify(iDataAcessFactoryMock, times(1)).getDataAccessor("stub");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void getCitiesByBookTitleTestDatabaseType() throws NotFoundExceptionMapper {
        List<ICity> cities = new ArrayList<ICity>();
        when(iDataAcessMock.getCitiesByBookTitle((eq("Harry Potter 2")))).thenReturn(cities);
        when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
        CityFacade cityFacade = new CityFacade(iDataAcessFactoryMock, "stub");
        assertTrue(cityFacade.getDatabase().equals("stub"));
    }

    @Test(expected = InvalidInputExceptionMapper.class)
    public void getCitiesByBookTitle2() throws NotFoundExceptionMapper, InvalidInputExceptionMapper {
        List<ICity> cities = new ArrayList<ICity>();
        when(iDataAcessMock.getCitiesByBookTitle((eq("")))).thenReturn(cities);
        when(iDataAcessFactoryMock.getDataAccessor((eq("stub")))).thenReturn(iDataAcessMock);
        CityFacade cityFacade = new CityFacade(iDataAcessFactoryMock, "stub");
        assertTrue(cityFacade.getCitiesByBookTitle("").equals(cities));
    }
}
