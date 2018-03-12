package ru.job4j.jdbc;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Optimization {
    private ConnectionSQL connectionSQL;
    private Connection connection;
    private int element;

    public Optimization(int element) {
        this.element = element;
        this.connectionSQL = new ConnectionSQL("jdbc:postgresql://localhost:5432/java_a_from_z",
                "postgres", "postgres");
        this.connection = connectionSQL.getConnection();
    }


    public static void main(String[] args) {
        Optimization optimization = new Optimization(10);
        optimization.dropAndCreateTable();
        optimization.insertTable();
        optimization.createJAXB();
        optimization.createXSLB();
    }

    public void dropAndCreateTable() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS test");
            statement.executeUpdate("CREATE TABLE test (field INT)");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertTable() {
        try {
            connection.setAutoCommit(false);
            PreparedStatement ps = connection.prepareStatement("INSERT INTO test (field) VALUES (?)");
            for (int i = 1; i < element + 1; i++) {
                ps.setInt(1, i);
                ps.addBatch();
            }
            ps.executeBatch();
            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    public void createJAXB() {
        Entries entries = new Entries();
        List<EntryXML> listEntryXML = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM test");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listEntryXML.add(new EntryXML(rs.getInt("field")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        entries.setEntries(listEntryXML);
        try {
            JAXBContext context = JAXBContext.newInstance(Entries.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(entries, new File("1.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void createXSLB() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File("template.xsl")));
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new StreamSource(new File("1.xml")), new StreamResult(new File("2.xml")));
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public int parsing() {


        return 0;
    }
}
