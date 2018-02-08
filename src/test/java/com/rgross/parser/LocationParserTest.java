package com.rgross.parser;

import com.rgross.exception.CountyNotFoundException;
import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.County;
import com.rgross.model.Location;
import com.rgross.repository.CountyRepository;
import com.rgross.repository.LocationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Ryan on 1/8/2018.
 */
public class LocationParserTest {

    LocationParser parser;
    CountyRepository countyRepository;
    LocationRepository locationRepository;
    String locationExample = "\"01001\",\"25013\",\"Hampden MA\",\"Agawam Town, MA\",16769,1";
    String invalidLocationExample = "\"51059\",\"Fairfax VA\",\"Reston, VA\",13165,1\"";

    County hampdenCounty;
    
    @Before
    public void setup() {

        countyRepository = Mockito.mock(CountyRepository.class);
        locationRepository = Mockito.mock(LocationRepository.class);
        parser = new LocationParser(countyRepository, locationRepository);
        hampdenCounty = new County("25013", "HAMPDEN COUNTY");
    }

    @Test
    public void generateLocationTest_Successful() throws InvalidLineFormatException, CountyNotFoundException {
//        Mockito.when(countyRepository.containsCounty(Mockito.anyString())).thenReturn(true);
//        Mockito.when(countyRepository.findByfipsNumber(Mockito.anyString())).thenReturn(hampdenCounty);

        Location location = parser.generateLocation(locationExample);
        County associatedCounty = location.getCounty();

//        Mockito.verify(countyRepository).containsCounty(Mockito.anyString());
//        Mockito.verify(countyRepository).findByfipsNumber(Mockito.anyString());

        Assert.assertTrue(location.getCity().equals("AGAWAM TOWN"));
        Assert.assertTrue(location.getStateCode().equals("MA"));
        Assert.assertTrue(location.getZipCode().equals("01001"));
        Assert.assertTrue(location.getCountyPercentage() == 1.0);
        Assert.assertTrue(associatedCounty.getCountyName().equals("HAMPDEN COUNTY"));
        Assert.assertTrue(associatedCounty.getFipsNumber().equals("25013"));

    }

//    @Test(expected = CountyNotFoundException.class)
//    public void generateLocationTest_Failure_NoCountyFound() throws InvalidLineFormatException, CountyNotFoundException {
//        Mockito.when(countyRepository.containsCounty(Mockito.anyString())).thenReturn(false);
//
//        Location locationOne = parser.generateLocation(locationExample);
//    }
//
//    @Test(expected = InvalidLineFormatException.class)
//    public void generateLocationTest_Failure_InvalidLine() throws InvalidLineFormatException, CountyNotFoundException {
//
//        Location locationOne = parser.generateLocation(invalidLocationExample);
//    }
//
//    @Test
//    public void processLine_DoesNotExist() throws InvalidLineFormatException, CountyNotFoundException, Exception {
//        Mockito.when(countyRepository.containsCounty(Mockito.anyString())).thenReturn(true);
//
//        parser.processLine(locationExample);
//
//        Mockito.verify(locationRepository).save(Mockito.any(Location.class));
//    }


}
