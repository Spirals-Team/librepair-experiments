package com.rgross.parser;

import com.rgross.GeolocatorService;
import com.rgross.comparator.LocationComparator;
import com.rgross.contract.GeneralContract;
import com.rgross.exception.LocationNotFoundException;
import com.rgross.exception.PlaceOfPerformanceException;
import com.rgross.model.Location;
import com.rgross.model.NaicsCode;
import com.rgross.model.PlaceOfPerformance;
import com.rgross.model.Vendor;
import com.rgross.parser.common.XMLParser;
import com.rgross.repository.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by ryan_gross on 9/16/17.
 */
@Component
public class GeneralContractParser extends XMLParser {


    GeneralContractRepository generalContractRepository;

    VendorRepository vendorRepository;

    LocationRepository locationRepository;


    NaicsCodeRepository naicsCodeRepository;

    CyberSecurityContractRepository cyberSecurityContractRepository;

    public GeneralContractParser(GeneralContractRepository generalContractRepository, VendorRepository vendorRepository, LocationRepository locationRepository, NaicsCodeRepository naicsCodeRepository, CyberSecurityContractRepository cyberSecurityContractRepository) {
        this.generalContractRepository = generalContractRepository;
        this.vendorRepository = vendorRepository;
        this.locationRepository = locationRepository;
        this.naicsCodeRepository = naicsCodeRepository;
        this.cyberSecurityContractRepository = cyberSecurityContractRepository;
    }

    public void setGeneralContractRepository(GeneralContractRepository generalContractRepository) {
        this.generalContractRepository = generalContractRepository;
    }

    public void setVendorRepository(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public void setLocationRepository(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void setNaicsCodeRepository(NaicsCodeRepository naicsCodeRepository) {
        this.naicsCodeRepository = naicsCodeRepository;
    }

    public void setCyberSecurityContractRepository(CyberSecurityContractRepository cyberSecurityContractRepository) {
        this.cyberSecurityContractRepository = cyberSecurityContractRepository;
    }


    @Override
    public void processIndividualContract(Element individualContract) throws IOException, ParseException, PlaceOfPerformanceException, LocationNotFoundException {

        GeneralContract generalContract = new GeneralContract();
        Location placeOfPerformanceLocation = generatePlaceOfPerformanceLocation(individualContract);
        Vendor vendor = generateVendor(individualContract);
        NaicsCode naicsCode = generateNaicsCode(individualContract);

        Double obligatedAmount = Double.parseDouble(getIndividualFieldFromElement(individualContract, "obligatedAmount"));
        String dunsNumber = getIndividualFieldFromElement(individualContract,"DUNSNumber");
        Integer fiscalYear = Integer.parseInt(getIndividualFieldFromElement(individualContract, "fiscal_year"));

        generalContract.setPlaceOfPerformanceLocation(placeOfPerformanceLocation);
        generalContract.setVendor(vendor);
        generalContract.setNaicsCode(naicsCode);
        generalContract.setDollarsObligated(obligatedAmount);
        generalContract.setDunsNumber(dunsNumber);
        generalContract.setFiscalYear(fiscalYear);

        generalContractRepository.save(generalContract);
    }

    public Location pickRandomLocation(List<Location> locations) {
        Location result = null;

        if (locations.size() == 1) {
            result = locations.get(0);
            return result;
        }

        locations.sort(new LocationComparator());

        double total = 0;
        double randomNumber = Math.random();

        for (int i = 0; i < locations.size(); i++) {
            total += locations.get(i).getCountyPercentage();

            if (randomNumber <= total) {
                result = locations.get(i);
            }
        }
        return result;
    }

    public Location generatePlaceOfPerformanceLocation(Element individualContract) throws LocationNotFoundException {
        String zipCode = getIndividualFieldFromElement(individualContract, "placeOfPerformanceZIPCode");

        List<Location> locationList = locationRepository.findAllByZipCode(zipCode);
        Location location;

        if (locationList.size() == 0) {
            throw new LocationNotFoundException();
        } else if (locationList.size() == 1) {
            location = locationList.get(0);
        } else {
            location = pickRandomLocation(locationList);
        }
        return location;
    }

    public String getWebsiteUrl(int year) {

        return "https://www.usaspending.gov/fpds/fpds.php?detail=c&fiscal_year=" + String.valueOf(year) +
                "&stateCode=DE&max_records=50000";
    }

    public Vendor generateVendor(Element individualContract) throws IOException, org.json.simple.parser.ParseException {

       String vendorZipCode = getIndividualFieldFromElement(individualContract, "ZIPCode");
       String vendorAddress = getIndividualFieldFromElement(individualContract, "streetAddress");

       List<Location> listOfLocations = locationRepository.findAllByZipCode(vendorZipCode);

       Location vendorLocation = null;

       if (listOfLocations.size() > 1) {
           String fullAddress = vendorAddress + ", " + listOfLocations.get(0).getCity() + ", " +
                   listOfLocations.get(0).getStateCode() + " " + vendorZipCode;

           String countyName = GeolocatorService.getGeolocatorServiceInstance().getCountyFromMapQuest(fullAddress);

           for (Location individualLocation : listOfLocations) {

               if (individualLocation.getCounty().getCountyName().equalsIgnoreCase(countyName)) {

                   vendorLocation = individualLocation;
               }
           }
       } else {
           vendorLocation = listOfLocations.get(0);
       }

       String name = getIndividualFieldFromElement(individualContract,"vendorName");

       Vendor vendor = vendorRepository.findByVendorName(name);

       if (vendor == null) {
           vendor =
            new Vendor(
                   name,
                    getIndividualFieldFromElement(individualContract, "streetAddress"),
                   vendorLocation
           );

           if (vendor.getVendorLocation().getStateCode().equalsIgnoreCase("DE")) {
               vendor.setIsOutOfState(false);
           } else {
               vendor.setIsOutOfState(true);
           }

            vendorRepository.save(vendor);
            return vendor;
       } else {
           return vendorRepository.findByVendorName(name);
       }
    }

    public NaicsCode generateNaicsCode(Element individualContract) {
        Integer code = Integer.valueOf(getIndividualFieldFromElement(individualContract, "principalNAICSCode"));

        return null;
//                naicsCodeRepository.findByNaicsCode(code);
    }

    // Make it so it parses just the years 2012-2016.
    public void scrapeContracts(int year) throws IOException, org.json.simple.parser.ParseException, java.text.ParseException, LocationNotFoundException {
        String url = getWebsiteUrl(year);
//        Document contractXml = generateDocument(url);
        Elements contracts = generateOutputByAttribute(url);


        for (Element contract : contracts) {
            String vendorCountry = getIndividualFieldFromElement(contract, "vendorCountryCode");

            if (vendorCountry.contains("USA")) {
                GeneralContract generatedGeneralContract = new GeneralContract();
                Vendor vendor = generateVendor(contract);

                if (vendor.getVendorLocation().getCounty().equals("ERROR")) {
                    break;
                }

                Location placeOfPerformanceLocation = generatePlaceOfPerformanceLocation(contract);
                NaicsCode naicsCode = generateNaicsCode(contract);

                generatedGeneralContract.setVendor(vendor);
                generatedGeneralContract.setPlaceOfPerformanceLocation(placeOfPerformanceLocation);
                generatedGeneralContract.setNaicsCode(naicsCode);
                generalContractRepository.save(generatedGeneralContract);

            }

        }
    }

    boolean containsCyberSecurityContract(Element element) {
        return true;

    }

    public List<GeneralContract> getContractsByYear(int year) {

        return generalContractRepository.findAllByFiscalYear(year);

    }



}
