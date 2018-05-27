package ru.job4j.sql;

import javax.xml.parsers.*;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import java.io.IOException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ParsingWork {
    public long work(String sourceXML) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        Parser parser = null;
        try {
            SAXParser saxParser = factory.newSAXParser();
            parser = new Parser();
            saxParser.parse(sourceXML, parser);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parser.getSum();
    }

    private class Parser extends DefaultHandler {

        private long sum;

        public long getSum() {
            return sum;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (qName.equals("entry")) {
                sum += Integer.valueOf(attributes.getValue("field"));
            }
        }
    }
}
