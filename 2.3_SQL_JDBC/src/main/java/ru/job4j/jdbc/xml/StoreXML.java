package ru.job4j.jdbc.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Class StoreXML.
 */
public class StoreXML {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreXML.class);
    /**
     * @param target xml file to saving data
     */
    private File target;

    /**
     * Constructor.
     * @param target xml file to saving data
     */
    public StoreXML(File target) {
        this.target = target;
    }

    /**
     * Method save.
     * @param list saving data frob db in xml file
     */
    public void save(List<Field> list) {
        Entry entry = new Entry(list);
        try {
            FileOutputStream fos = new FileOutputStream(target);
            JAXBContext jaxbContext = null;
            jaxbContext = JAXBContext.newInstance(Entry.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(entry, fos);
        } catch (FileNotFoundException | JAXBException e) {
                LOGGER.error(e.getMessage(), e);
        }
    }
}
