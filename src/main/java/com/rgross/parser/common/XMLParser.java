package com.rgross.parser.common;

import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Ryan on 1/6/2018.
 */
public abstract class XMLParser implements Parser {


    String attribute = "spendingCategory";

    public Document generateDocument(String urlName) throws IOException {
        Document document =  Jsoup.parse(new URL(urlName).openStream(), "UTF-8", "",org.jsoup.parser.Parser.xmlParser());

        return document;
    }

    public Elements generateOutputByAttribute(String urlName) throws IOException {
        Document document = generateDocument(urlName);

        return document.getElementsByAttribute(this.attribute);
    }

    public String getIndividualFieldFromElement(Element element, String field) {

        Elements elements = element.getElementsByTag(field);

        if (elements.size() == 0) {
            return "ERROR";
        } else {

            return elements.get(0).text();
        }

    }

    public abstract void processIndividualContract(Element element) throws Exception;

    public void parse(String file) throws Exception {

        Elements outputFromXMLByAttribute = generateOutputByAttribute(file);

        for (Element element : outputFromXMLByAttribute) {

            processIndividualContract(element);

        }

    }


}
