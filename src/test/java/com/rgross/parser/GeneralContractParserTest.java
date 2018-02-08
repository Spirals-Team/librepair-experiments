package com.rgross.parser;

import com.rgross.contract.GeneralContract;
import com.rgross.exception.LocationNotFoundException;
import com.rgross.exception.NaicsCodeNotFoundException;
import com.rgross.exception.PlaceOfPerformanceException;
import com.rgross.exception.VendorNotFoundException;
import com.rgross.model.Location;
import com.rgross.model.NaicsCode;
import com.rgross.model.PlaceOfPerformance;
import com.rgross.model.Vendor;
import com.rgross.parser.common.XMLParser;
import com.rgross.repository.*;
import com.rgross.util.ContractParserUtil;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Ryan on 1/15/2018.
 */
public class GeneralContractParserTest {

    GeneralContractParser generalContractParser;

    GeneralContractRepository generalContractRepository;
    VendorRepository vendorRepository;
    LocationRepository locationRepository;
    NaicsCodeRepository naicsCodeRepository;
    CyberSecurityContractRepository cyberSecurityContractRepository;
    ContractParserUtil contractUtil;

    Location locationOne;
    Location locationTwo;
    PlaceOfPerformance placeOfPerformance;
    Document testDocument;
    List<Location> locationList;
    Vendor vendor;
    NaicsCode naicsCode;
    GeneralContract generalContract;

    @Before
    public void setup() throws IOException, ParseException, java.text.ParseException, PlaceOfPerformanceException {

        generalContractParser = Mockito.mock(GeneralContractParser.class);
        generalContractRepository = Mockito.mock(GeneralContractRepository.class);
        vendorRepository = Mockito.mock(VendorRepository.class);
        locationRepository = Mockito.mock(LocationRepository.class);
        naicsCodeRepository = Mockito.mock(NaicsCodeRepository.class);
        cyberSecurityContractRepository = Mockito.mock(CyberSecurityContractRepository.class);
        locationOne = new Location(100.0);
        locationTwo = new Location(100.0);
        placeOfPerformance = new PlaceOfPerformance();
        contractUtil = new ContractParserUtil();
        locationList = new ArrayList<>();
        vendor = new Vendor();
        naicsCode = new NaicsCode(1, "Test");
        testDocument = contractUtil.generateDocumentFromTestFile("TestContracts.xml");

        generalContractParser.setGeneralContractRepository(generalContractRepository);
        generalContractParser.setLocationRepository(locationRepository);
        generalContractParser.setVendorRepository(vendorRepository);
        generalContractParser.setCyberSecurityContractRepository(cyberSecurityContractRepository);
        generalContractParser.setNaicsCodeRepository(naicsCodeRepository);

        Mockito.when(generalContractParser.generateDocument(Mockito.anyString())).thenReturn(testDocument);
        Mockito.when(generalContractParser.generateOutputByAttribute(Mockito.anyString())).thenCallRealMethod();
        Mockito.when(generalContractParser.getIndividualFieldFromElement(Mockito.any(Element.class), Mockito.anyString())).thenCallRealMethod();
        Mockito.doNothing().when(generalContractRepository.save(Mockito.any(GeneralContract.class)));

//        Mockito.doCallRealMethod().when(generalContractParser).scrapeContracts(Mockito.anyInt());
        Mockito.when(generalContractParser.getWebsiteUrl(Mockito.anyInt())).thenReturn("");
        Mockito.when(generalContractParser.generateOutputByAttribute(Mockito.anyString())).thenCallRealMethod();
        Mockito.when(generalContractParser.getIndividualFieldFromElement(Mockito.any(Element.class), Mockito.anyString())).thenCallRealMethod();
//        Mockito.when(generalContractParser.generatePlaceOfPerformance(Mockito.any(Element.class))).thenCallRealMethod();
        Mockito.when(generalContractParser.generateVendor(Mockito.any(Element.class))).thenCallRealMethod();
        Mockito.when(generalContractParser.generateNaicsCode(Mockito.any(Element.class))).thenCallRealMethod();
        Mockito.when(generalContractParser.containsCyberSecurityContract(Mockito.any(Element.class))).thenCallRealMethod();
//        Mockito.when(generalContractParser.generatePlaceOfPerformance(any(Element.class))).thenCallRealMethod();
//        Mockito.when(generalContractParser.generateVendor(any(Element.class), anyString())).thenCallRealMethod();
//        Mockito.when(generalContractParser.getFieldFromContract(any(Element.class), Mockito.anyString())).thenReturn("test");
    }

    @Test(expected = LocationNotFoundException.class)
    public void parseContract_generatePOP_failure() throws IOException, ParseException, java.text.ParseException {
        Mockito.when(locationRepository.findAllByZipCode(Mockito.anyString())).thenReturn(locationList);


    }

    @Test(expected = VendorNotFoundException.class)
    public void parseContract_generateVendor_failure() throws IOException, ParseException, java.text.ParseException {
        locationList.add(locationOne);

        Mockito.when(locationRepository.findAllByZipCode(Mockito.anyString())).thenReturn(locationList);
//        Mockito.when(vendorRepository.existsByVendorNameAndStreetAddressAndVendorLocation(Mockito.anyString(), Mockito.anyString(), Mockito.any(Location.class))).thenReturn(false);
        Mockito.doNothing().when(generalContractParser.generateNaicsCode(Mockito.any(Element.class)));
//        Mockito.doNothing().when(generalContractParser.getCountyFromMapQuest(Mockito.anyString()));


    }

    @Test(expected = NaicsCodeNotFoundException.class)
    public void parseContract_generateNaicsCode_Failure() throws IOException, ParseException, java.text.ParseException {
        locationOne.setCountyPercentage(20.0);
        locationTwo.setCountyPercentage(80.0);
        locationList.add(locationOne);
        locationList.add(locationTwo);

        Mockito.when(locationRepository.findAllByZipCode(Mockito.anyString())).thenReturn(locationList);
//        Mockito.when(vendorRepository.existsByVendorNameAndStreetAddressAndVendorLocation(Mockito.anyString(), Mockito.anyString(), Mockito.any(Location.class))).thenReturn(true);
        Mockito.when(vendorRepository.findByVendorName(Mockito.anyString())).thenReturn(vendor);
//        Mockito.doNothing().when(generalContractParser.getCountyFromMapQuest(Mockito.anyString()));
//        Mockito.when(naicsCodeRepository.containsNaicsCode(Mockito.anyInt())).thenReturn(true);


        Mockito.verify(generalContractParser, Mockito.times(1)).pickRandomLocation(Mockito.any(List.class));
        Assert.assertTrue(vendor.getIsOutOfState());

    }

    @Test
    public void parseContract_Successful() throws IOException, ParseException, java.text.ParseException {
        vendor.getVendorLocation().setStateCode("DE");
        locationList.add(locationOne);

        Mockito.when(locationRepository.findAllByZipCode(Mockito.anyString())).thenReturn(locationList);
//        Mockito.when(vendorRepository.existsByVendorNameAndStreetAddressAndVendorLocation(Mockito.anyString(), Mockito.anyString(), Mockito.any(Location.class))).thenReturn(true);
        Mockito.when(vendorRepository.findByVendorName(Mockito.anyString())).thenReturn(vendor);
//        Mockito.when(naicsCodeRepository.containsNaicsCode(Mockito.anyInt())).thenReturn(true);
//        Mockito.when(naicsCodeRepository.findByNaicsCode(Mockito.anyInt())).thenReturn(naicsCode);
        Mockito.when(generalContractParser.containsCyberSecurityContract(Mockito.any(Element.class))).thenReturn(true);

        Assert.assertTrue(vendor.getIsOutOfState());
        Assert.assertTrue(generalContract.getIsCyberSecurityContract());

    }


}
