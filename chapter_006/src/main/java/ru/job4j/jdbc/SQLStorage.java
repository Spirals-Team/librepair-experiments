package ru.job4j.jdbc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SQLStorage {
    private Connection connection = null;
    private String URL = null;
    private int n;

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test(field INTEGER)");
            statement.executeUpdate("DELETE FROM test");
            for (int i = 1; i <= n; i++) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO test VALUES(?)");
                preparedStatement.setInt(1, i);
                preparedStatement.executeUpdate();
            }
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("field"));
            }
            System.out.println("Connection established!");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void parse() throws SQLException {
        System.out.println("Creating 1.xml...");
        try {
            ArrayList<Integer> list = new ArrayList<>();
            connection = DriverManager.getConnection(URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM test");
            while (resultSet.next()) {
                list.add(resultSet.getInt("field"));
            }

            Entries entries = new Entries();
            for (Integer elem : list) {
                entries.getList().add(elem);
            }

            System.out.println("Parsing...");
            JAXBContext context = JAXBContext.newInstance(entries.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(entries, new File("1.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void xsl(String inFilename, String outFilename, String xslFilename) {
        System.out.println("Creating 2.xml...");
        try {
            StreamSource xslcode = new StreamSource(new File(xslFilename));
            StreamSource input = new StreamSource(new File(inFilename));
            StreamResult output = new StreamResult(new File(outFilename));

            TransformerFactory factory = TransformerFactory.newInstance();

            Transformer transformer;

            try {
                transformer = factory.newTransformer(xslcode);
                transformer.transform(input, output);
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int parseAndFindAverage(String Filename) {
        int result = 0;

        try {
            File fXmlFile = new File("2.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("entry");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    result += Integer.parseInt(eElement.getAttribute("field"));
                }
            }
            result /= nList.getLength();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
