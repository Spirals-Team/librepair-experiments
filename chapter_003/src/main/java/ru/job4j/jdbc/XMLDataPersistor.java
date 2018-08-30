package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class perform manipulation with xml.
 */
public class XMLDataPersistor {

    private static final Logger LOG = LoggerFactory.getLogger(XMLDataPersistor.class);

    private String defaultStyle = "./ext_src/transform.xsl";

    /**
     * Create xml
     *
     * @param entries  entries
     * @param filename filename
     */
    public void createXml(final Entries entries, final String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(entries, new FileWriter(filename));
            LOG.info("Created simple xml.");
        } catch (JAXBException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Transform xml
     *
     * @param sourceFile source
     * @param targetFile target
     */
    public void transformXml(final String sourceFile, String targetFile) {
        try {
            TransformerFactory t = TransformerFactory.newInstance();
            StreamSource style = new StreamSource(new FileReader(this.defaultStyle));
            Transformer transformer = t.newTransformer(style);
            Source source = new StreamSource(new FileReader(sourceFile));
            StreamResult target = new StreamResult(new FileWriter(targetFile));
            transformer.transform(source, target);
            LOG.info("Transformed simple xml to xml with attributes.");
        } catch (IOException | TransformerException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Get dataset from xml
     *
     * @param filename filename
     * @return dataset
     */
    public Entries parseEntries(final String filename) {
        Set<Entry> entries = new HashSet<>();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(new FileReader(filename));
            while (parser.hasNext()) {
                if (isChildNode(parser, parser.next())) {
                    String field = parser.getAttributeValue(null, "field");
                    entries.add(new Entry(Integer.parseInt(field)));
                }
            }
            parser.close();
        } catch (XMLStreamException | FileNotFoundException e) {
            if (parser != null) {
                try {
                    parser.close();
                } catch (XMLStreamException e1) {
                    LOG.error(e1.getMessage(), e1);
                }
            }
            LOG.error(e.getMessage(), e);
        }
        return new Entries(entries);
    }

    private boolean isChildNode(final XMLStreamReader parser, final int event) {
        return event == XMLStreamConstants.START_ELEMENT
            && parser.getLocalName().equalsIgnoreCase("entry");
    }

    public String getStyleFilename() {
        return defaultStyle;
    }

    public void setStyleFilename(String filename) {
        this.defaultStyle = filename;
    }
}
