package ru.job4j.jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class Entity {
//    private static final String xml1 = "C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\1.xml";
//    private static final String xml2 = "C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\2.xml";
//    private static final String conUrl = "jdbc:sqlite:C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\sqlite.db";
    private Connection con;
    private int n;

    /**
     * Сэттер для n
     * @param n - число записей в бд.
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Метод устанавливает соединение с бд, записывает в нее n записей и из записей формирует
     * файл 1.xml
     */
    public void setCon() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection con = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\sqlite.db")) {
                con.setAutoCommit(false);
                Statement statement = con.createStatement();
                statement.addBatch("create table if not exists 'test'('field' integer primary key);");
                statement.addBatch("delete from 'test'");
                for (int i = 1; i <= n; i++) {
                    statement.addBatch("insert into 'test'('field') values(" + i + ");");
                }
                statement.executeBatch();
                con.commit();
                ResultSet rs = statement.executeQuery("select * from 'test'");
                List<Entry> list = new ArrayList<>();
                while (rs.next()) {
                    int index = rs.getInt(1);
                    Entry en = new Entry(index);
                    list.add(en);
                }
                Entries entr = new Entries();
                entr.setEntries(list);
                try {
                    File file = new File("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\1.xml");
                    JAXBContext context = JAXBContext.newInstance(Entries.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                    marshaller.marshal(entr, file);
                } catch (JAXBException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                if (con != null) {
                    try {
                        con.rollback();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод преобразует файл 1.xml в 2.xml по заданному шаблону
     */
    public void converting() {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer(new StreamSource(new File("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\template.xslt")));
            StreamSource streamSource = new StreamSource(new File("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\1.xml"));
            StreamResult streamResult = new StreamResult(new File("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\2.xml"));
            tf.transform(streamSource, streamResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возращает сумму полей field в файде 2.xml
     * @return sum типа long, т.к. при n=1_000_000 не укладывается в диапазон типа int.
     */
    public long getSum() {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        long sum = 0;
        try {
            XMLStreamReader parser = factory.createXMLStreamReader(new FileInputStream("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\2.xml"));
            while (parser.hasNext()) {
                int event = parser.next();
                if (event == XMLStreamConstants.START_ELEMENT) {
                    if (parser.getAttributeValue(0) != null) {
                        sum += Long.parseLong(parser.getAttributeValue(0));
                    }
                }
            }
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return sum;
    }
}
