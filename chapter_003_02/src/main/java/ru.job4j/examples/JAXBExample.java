package ru.job4j.examples;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

//public class JAXBExample {
//
//    public static void main(String[] args) {
//        Customer customer = new Customer();
//        customer.setField(1);
//
//
//        try {
//            File file = new File(System.getProperty("user.dir")
//                    + File.separator + "customer.xml");
//            JAXBContext context = JAXBContext.newInstance(Customer.class);regex
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//            marshaller.marshal(customer, file);
//            marshaller.marshal(customer, System.out);
//        } catch (JAXBException ex) {
//            Logger.getLogger(JAXBExample.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
//    }
//}


public class JAXBExample {

    public static void main(String[] args) {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Иван Иванов");
        customer.setAge(28);

        try {
            File file = new File(System.getProperty("user.dir")
                    + File.separator + "customer.xml");
            JAXBContext context = JAXBContext.newInstance(Customer.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(customer, file);
            marshaller.marshal(customer, System.out);
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBExample.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}