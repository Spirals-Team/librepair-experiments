package com.rgross.parser;

import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.County;
import com.rgross.parser.common.CSVParser;
import com.rgross.repository.CountyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan_gross on 11/11/17.
 */
@Component
public class CountyParser extends CSVParser {

    @Autowired
    CountyRepository countyRepository;

    public CountyParser(CountyRepository countyRepository) {
        this.countyRepository = countyRepository;
    }


    private String cleanCountyName(String countyName) {

        return countyName.toUpperCase();
    }

    private String getPaddedFipsSection(String line, int maxLength) {
        line = line.replace(",", "");

        StringBuilder stringBuilder = new StringBuilder();

        while (stringBuilder.length() + line.length() < maxLength) {
            stringBuilder.append("0");
        }

        stringBuilder.append(line);

        return stringBuilder.toString();
    }

    private String constructFipsNumber(String stateFips, String countyFips) {
        return getPaddedFipsSection(stateFips, 2) + getPaddedFipsSection(countyFips, 3);

    }

    public County generateCounty(String line) throws InvalidLineFormatException {

        String fieldSeparatorPattern = "[0-9]{1,2}[,]|[0-9]{1,3}[,]|[A-Z][a-z][^,]*";
        Pattern pattern = Pattern.compile(fieldSeparatorPattern);
        Matcher matcher = pattern.matcher(line);
        List<String> parsedFields = new ArrayList<>();

        while (matcher.find()) {
            parsedFields.add(matcher.group());
        }

        if (parsedFields.size() != 3) {
            throw new InvalidLineFormatException();
        }

        String stateFipsSegment = parsedFields.get(0);
        String countyFipsSegment = parsedFields.get(1);

        String fipsCode = constructFipsNumber(stateFipsSegment, countyFipsSegment);
        String countyName = parsedFields.get(2);
        countyName = cleanCountyName(countyName);
        County county = new County(fipsCode, countyName);

        return county;
    }

    @Override
    public void processLine(String line) throws InvalidLineFormatException {
        County generatedCounty = generateCounty(line);
        String fipsCode = generatedCounty.getFipsNumber();

//        if (!countyRepository.containsCounty(fipsCode)) {
            countyRepository.save(generatedCounty);
//        }


    }

}
