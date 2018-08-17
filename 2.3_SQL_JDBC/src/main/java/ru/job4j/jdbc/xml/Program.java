package ru.job4j.jdbc.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * main class Program.
 */
public class Program {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);
    /**
     * @param properties properties
     */
    private final Properties properties = new Properties();
    /**
     * @param xml create first xml
     */
    private StoreXML xml;
    /**
     * @param xsqt convert xml
     */
    private ConvertXSQT xsqt = new ConvertXSQT();
    /**
     * @param parse parse xml
     */
    private ParseSax parse = new ParseSax();

    /**
     * constructor.
     */
    public Program() {
        initProperties();
        File file = new File(properties.getProperty("source.xml"));
        xml = new StoreXML(file);
    }

    /**
     * method init.
     * @param n value for adding in db
     */
    public void init(int n) {
        try (StoreSQL db = new StoreSQL(properties)) {
            db.generate(n);
            xml.save(db.getAll());
            convertXml();
            parseXml();
        }
    }

    /**
     * Method initproperties.
     */
    private void initProperties() {
        File file = new File(this.getClass().getClassLoader().getResource("xml.properties").getFile());
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Method for convert xml.
     */
    private void convertXml() {
        File source = new File(properties.getProperty("source.xml"));
        File dest = new File(properties.getProperty("dest.xml"));
        File scheme = new File(properties.getProperty("xslt"));
        xsqt.convert(source, dest, scheme);
    }

    /**
     * Method for parse xml.
     */
    private void parseXml() {
        XMLReader parser = null;
        try {
            parser = XMLReaderFactory.createXMLReader();
            parser.setContentHandler(parse);
            parser.parse(properties.getProperty("dest.xml"));
            System.out.print(parse.getResult());
        } catch (SAXException | IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
