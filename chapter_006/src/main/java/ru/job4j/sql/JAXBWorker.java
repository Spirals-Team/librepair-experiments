package ru.job4j.sql;

import javax.xml.bind.*;
import java.io.File;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class JAXBWorker {
    public void work(MyEntries myEntries, String file) {
        System.setProperty("javax.xml.bind.JAXBContextFactory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        try {
            JAXBContext context = JAXBContext.newInstance(MyEntry.class, MyEntries.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(myEntries, new File(file));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
