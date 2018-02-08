package com.rgross.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ryan on 1/22/2018.
 */
public class ContractParserUtil {

    public Document generateDocumentFromTestFile(String testFileName) throws IOException {

        InputStream testFileAsStream = this.getClass().getClassLoader().getResourceAsStream(testFileName);
        Document testDocument = Jsoup.parse(testFileAsStream, "UTF-8", "", org.jsoup.parser.Parser.xmlParser());
        return testDocument;
    }
}
