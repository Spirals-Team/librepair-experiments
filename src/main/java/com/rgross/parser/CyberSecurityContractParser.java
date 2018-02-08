package com.rgross.parser;

import com.rgross.contract.CyberSecurityContract;
import com.rgross.exception.InvalidLineFormatException;
import com.rgross.exception.OutOfStateException;
import com.rgross.model.NaicsCode;
import com.rgross.parser.common.CSVParser;
import com.rgross.repository.CyberSecurityContractRepository;
import com.rgross.repository.NaicsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan_gross on 12/26/17.
 */
@Component
public class CyberSecurityContractParser extends CSVParser {


    CyberSecurityContractRepository cyberSecurityContractRepository;

    NaicsCodeRepository naicsCodeRepository;

    public CyberSecurityContractParser(CyberSecurityContractRepository cyberSecurityContractRepository, NaicsCodeRepository naicsCodeRepository) {

        this.cyberSecurityContractRepository = cyberSecurityContractRepository;
        this.naicsCodeRepository = naicsCodeRepository;
    }

    protected CyberSecurityContract generateCyberSecurityContract(String line) throws OutOfStateException, InvalidLineFormatException {
//        String quotes = "([\"]{1}[^\"]*[\"]{1}|[,][2][0][1][2-6][,]|[,][0-9]{7,9}[,])";
        String quotes = "[\"][^\"]*[\"][,]|\\S+[-]\\S{4}[-]\\S{4}[-]\\S{4}[-][^,]+|[-]?\\d+[.]\\d{2}|Y,|N,|\\d{1,2}\\W\\d{2}\\W201[2-6]|201[1-6]|\\d+[,]";

        Pattern pattern = Pattern.compile(quotes);
        Matcher matcher = pattern.matcher(line);
        List<String> results = new ArrayList<>();

        while (matcher.find()) {
            results.add(matcher.group());
        }

        if (results.size() != 225) {
            throw new InvalidLineFormatException();
        }

//        for (int i = 0; i < results.size(); i++) {
//            if (results.get(i).contains("334290")) {
//                System.out.print(results.get(i)) ;
//            }
//        }

        String placeOfPerformanceStateCode = results.get(74);
        String dunsNumber = results.get(64).replaceAll("(\"|,)", "");
        String dollarsObligatedAsString = results.get(2);

        String naicsCodeFormatted = results.get(83).replaceAll("\"|,", "");
        int naicsCodeAsInteger = Integer.valueOf(naicsCodeFormatted);
        int fiscalYear = Integer.valueOf(results.get(98).replaceAll("\"", ""));

        NaicsCode naicsCode = null;

        if (naicsCodeRepository.findByNaicsCode(naicsCodeAsInteger) != null) {
            naicsCode = naicsCodeRepository.findByNaicsCode(naicsCodeAsInteger);
        }

        if (!placeOfPerformanceStateCode.contains("DE")) {
           throw new OutOfStateException();
        }

        double dollarsObligated = Double.valueOf(dollarsObligatedAsString);

        return new CyberSecurityContract(dunsNumber, dollarsObligated, fiscalYear, naicsCode);
    }

    @Override
    public void processLine(String line) throws OutOfStateException, InvalidLineFormatException {

        CyberSecurityContract cyberSecurityContract;

        try {
            cyberSecurityContract = generateCyberSecurityContract(line);
            cyberSecurityContractRepository.save(cyberSecurityContract);

        } catch (Exception e) {

        }

    }

}
