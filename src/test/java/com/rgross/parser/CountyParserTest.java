package com.rgross.parser;

import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.County;
import com.rgross.parser.CountyParser;
import com.rgross.repository.CountyRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by Ryan on 1/8/2018.
 */
public class CountyParserTest {

    CountyParser parser;
    CountyRepository repository;
    String validCountyExample = "AK,2,290,Yukon-Koyukuk Census Area,H5";
    String inValidCountyExample = "AK,,,Yukon-Koyukuk Census Area,H5";
    County county;

    @Before
    public void setup() throws InvalidLineFormatException {

        repository = Mockito.mock(CountyRepository.class);
        parser = new CountyParser(repository);
        county = parser.generateCounty(validCountyExample);

    }

    @Test
    public void generateCounty_Successful() {

        Assert.assertTrue(county.getFipsNumber().equals("02290"));
        Assert.assertTrue(county.getCountyName().equals("YUKON-KOYUKUK CENSUS AREA"));
    }

    @Test(expected = InvalidLineFormatException.class)
    public void generateCounty_Failure() throws InvalidLineFormatException{
        County invalidCounty = parser.generateCounty(inValidCountyExample);
    }

    @Test
    public void processLine_ExistingCountyFound() throws InvalidLineFormatException {
//        Mockito.when(repository.containsCounty(Mockito.anyString())).thenReturn(true);

        parser.processLine(validCountyExample);

        Mockito.verify(repository, Mockito.times(0)).save(Mockito.any(County.class));
    }

    @Test
    public void processLine_NoExistingCountyFound() throws InvalidLineFormatException {
        // Need to mock the save method.
//        Mockito.when(repository.containsCounty(Mockito.anyString())).thenReturn(false);

        parser.processLine(validCountyExample);

        Mockito.verify(repository).save(Mockito.any(County.class));
    }


}
