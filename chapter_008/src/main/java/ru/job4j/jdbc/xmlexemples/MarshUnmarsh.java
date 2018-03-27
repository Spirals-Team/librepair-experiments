package ru.job4j.jdbc.xmlexemples;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 15.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class MarshUnmarsh {
    public static void main(String[] args) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(EmpList.class);

        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        List<Person> l = new LinkedList<Person>();
        for (int i = 0; i < 10; i++) {
            l.add(new Person("a" + i));
        }
        EmpList el = new EmpList(l);

        StringWriter sw = new StringWriter();
        m.marshal(el, sw);

        Unmarshaller um = context.createUnmarshaller();
        String s = sw.toString();

        System.out.println(s);

        System.out.println("==============================================");
        System.out.println("==============================================");
        System.out.println("==============================================");

        EmpList el2 = (EmpList) um.unmarshal(new StreamSource(new StringReader(s)));
        System.out.println(el2);

        System.out.println("FINISH ;)");

    }

    @XmlRootElement(name = "братва")
    static class EmpList {

        public EmpList() {
        }

        public EmpList(List<Person> el) {
            dude = el;
        }

        //      @XmlElement(name="чувак")
        private List<Person> dude;

        public List<Person> getDude() {
            return dude;
        }

        @XmlElement(name = "чувак")
        public void setDude(List<Person> dude) {
            this.dude = dude;
        }

        @Override
        public String toString() {
            return "EmpList [dude=" + dude + "]";
        }

    }


    static class Person {

        public Person() {
        }

        public Person(String name) {
            this.name = name;
        }

        private String name;

        @XmlElement(name = "погоняло")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person [name=" + name + "]";
        }

    }

}
