package ru.job4j.examples;


import java.io.File;
        import java.util.logging.Level;
        import java.util.logging.Logger;
        import javax.xml.bind.JAXBContext;
        import javax.xml.bind.JAXBException;
        import javax.xml.bind.Unmarshaller;

public class JAXBExampleUnmarshal {

    public static void main(String[] args) {
        try {
            File file = new File(System.getProperty("user.dir")
                    + File.separator + "customer.xml");
            JAXBContext context = JAXBContext.newInstance(Customer.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Customer customer = (Customer) unmarshaller.unmarshal(file);
            System.out.println(customer);
        } catch (JAXBException ex) {
            Logger.getLogger(JAXBExample.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}