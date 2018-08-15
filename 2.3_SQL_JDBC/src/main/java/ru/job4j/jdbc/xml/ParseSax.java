package ru.job4j.jdbc.xml;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class ParseSax.
 */
public class ParseSax extends DefaultHandler {
    /**
     * @param ENTRY xml attribute
     */
    private static final String ENTRY = "entry";
    /**
     * @param result result
     */
    private int result = 0;

    /**
     * Method startElement.
     *
     * @param uri        uri
     * @param localName  localname
     * @param qName      qname
     * @param attributes attributes
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (ENTRY.equals(qName)) {
            result = result + Integer.valueOf(attributes.getValue(0));
        }
    }

    /**
     * Getter for result.
     *
     * @return result
     */
    public int getResult() {
        return result;
    }
}
