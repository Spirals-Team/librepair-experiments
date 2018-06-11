package org.eol.globi.taxon;

import com.Ostermiller.util.LabeledCSVParser;
import org.apache.commons.lang3.StringUtils;
import org.eol.globi.data.CharsetConstant;
import org.eol.globi.service.Initializing;
import org.eol.globi.service.NameSuggester;
import org.eol.globi.util.CSVTSVUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ManualSuggester implements NameSuggester, Initializing {
    private Map<String, String> corrections;

    @Override
    public String suggest(final String name) {
        if (!isInitialized()) {
            doInit();
        }
        String suggestedReplacement = corrections.get(StringUtils.lowerCase(name));
        return StringUtils.isBlank(suggestedReplacement) ? name : suggestedReplacement;
    }

    private void doInit() {
        InputStream resourceAsStream = getClass().getResourceAsStream("/org/eol/globi/service/taxon-name-mapping.csv");
        try {
            init(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException("failed to initialize taxon name normalizer", e);
        }
    }

    @Override
    public void init(InputStream resourceAsStream) throws IOException {
        BufferedReader is = org.eol.globi.data.FileUtils.getUncompressedBufferedReader(resourceAsStream, CharsetConstant.UTF8);
        LabeledCSVParser labeledCSVParser = CSVTSVUtil.createLabeledCSVParser(is);
        String[] line;

        corrections = new HashMap<>();
        while ((line = labeledCSVParser.getLine()) != null) {
            if (line.length > 1) {
                String original = line[0];
                String correction = line[1];
                if (StringUtils.isBlank(correction) || correction.trim().length() < 2) {
                    throw new RuntimeException("found invalid blank or single character conversion for [" + original + "], on line [" + labeledCSVParser.lastLineNumber() + 1 + "]");
                }

                String existingCorrection = corrections.get(StringUtils.lowerCase(original));
                if (StringUtils.isNotBlank(existingCorrection)) {
                    if (!StringUtils.equalsIgnoreCase(existingCorrection, correction)) {
                        throw new RuntimeException("term [" + original + "] already mapped. Please revisit line [" + (labeledCSVParser.lastLineNumber() + 1) + "]");
                    }
                }
                corrections.put(StringUtils.lowerCase(original), correction);
            }
        }
    }

    private boolean isInitialized() {
        return corrections != null;
    }
}
