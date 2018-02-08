package com.rgross.parser;

import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.NaicsCode;
import com.rgross.parser.common.CSVParser;
import com.rgross.repository.NaicsCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan_gross on 11/18/17.
 */
@Component
public class NaicsCodeParser extends CSVParser {

    @Autowired
    public NaicsCodeRepository naicsCodeRepository;

    public NaicsCodeParser(NaicsCodeRepository naicsCodeRepository) {
        this.naicsCodeRepository = naicsCodeRepository;
    }

    public void setNaicsCodeRepository(NaicsCodeRepository naicsCodeRepository) {
        this.naicsCodeRepository = naicsCodeRepository;
    }

    public String cleanNaicsDescription(String description) {

        if (description.startsWith("\"")) {
            description = description.substring(1, description.length() - 2);
        }

        return description.trim();
    }

    public NaicsCode generateNaicsCode(String line) throws InvalidLineFormatException {
        String fieldSeparatorPattern = "[0-9]{6}|[A-Z][^,]*|[\"].*[\"]";
        Pattern pattern = Pattern.compile(fieldSeparatorPattern);
        Matcher matcher = pattern.matcher(line);
        List<String> parsedFields = new ArrayList<>();

        while (matcher.find()) {
            parsedFields.add(matcher.group());
        }

        if (parsedFields.size() != 2) {
            throw new InvalidLineFormatException();
        }

        Integer code = Integer.valueOf(parsedFields.get(0));
        String description = parsedFields.get(1);
        description = cleanNaicsDescription(description);

        NaicsCode naicsCode = new NaicsCode(code, description);

        return naicsCode;
    }


    @Override
    public void processLine(String line) throws InvalidLineFormatException {
        NaicsCode generatedCode = generateNaicsCode(line);
        Integer code = generatedCode.getNaicsCode();

//        if (!naicsCodeRepository.containsNaicsCode(code)) {
            naicsCodeRepository.save(generatedCode);
//        }
    }
}
