package com.utn.tp5.service;
import com.utn.tp5.model.Cabin;
import com.utn.tp5.model.Price;
import com.utn.tp5.model.Route;
import com.utn.tp5.persistence.PricePersistence;
import com.utn.tp5.persistence.RouteXCabinPersistence;
import com.utn.tp5.model.RouteXCabin;
import org.junit.Before;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RouteXCabinServiceTest {

    private RouteXCabinService routeXCabinService;
    private RouteXCabin routeXCabin;
    private RouteXCabinPersistence routeXCabinPersistence;
    private PricePersistence pricePersistence;

    @Before
    public void contextLoads() {
        this.routeXCabinPersistence = mock(RouteXCabinPersistence.class);
        this.pricePersistence = mock(PricePersistence.class);
        this.routeXCabinService = new RouteXCabinService(routeXCabinPersistence);
        this.routeXCabin = new RouteXCabin(mock(Cabin.class), mock(Route.class));
        when(routeXCabinPersistence.save(this.routeXCabin)).thenReturn(this.routeXCabin);
    }

    @Test
    public void whenARouteXCabinIsSaved() {
        Boolean res = this.routeXCabinService.saveRouteXCabin(this.routeXCabin);
        assertEquals(Boolean.TRUE, res);
    }


    @Test
    public void whenARouteXCabinIsAskedById() {
        when(routeXCabinPersistence.getOne(this.routeXCabin.getId())).thenReturn(this.routeXCabin);
        RouteXCabin c = this.routeXCabinService.getById(this.routeXCabin.getId());
        assertEquals(this.routeXCabin, c);
    }

    @Test
    public void whenTheRouteXCabinListIsAsked() {
        List<RouteXCabin> expected = new ArrayList<>();
        when(routeXCabinPersistence.findAll()).thenReturn(expected);
        List<RouteXCabin> routeXCabins = this.routeXCabinService.getAll();
        assertEquals(routeXCabins, expected);
    }

    @Test
    public void whenAnRouteXCabinIsModified() throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd-MM-yy");
        Date dateFrom = formatter.parse("10-10-2018");
        Price newPrice = new Price(1015,dateFrom);
        boolean res = this.routeXCabinService.modifyRouteXCabin(this.routeXCabin, newPrice);
        assertEquals(Boolean.TRUE, res);
    }

}
