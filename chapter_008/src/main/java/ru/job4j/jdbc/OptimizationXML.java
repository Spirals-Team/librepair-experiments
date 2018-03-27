package ru.job4j.jdbc;


import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.*;

/**
 * .
 * @author Hincu Andrei (andreih1981@gmail.com) by 03.12.17;
 * @version $Id$
 * @since 0.1
 */
public class OptimizationXML {

    public int getElement() {
        return element;
    }

    /**
     * колличество элементов.
     */
    private int element;
    /**
     * Соединение с бд.
     */
    private Connection connection;

    /**
     * Путь к первому xml файлу.
     */
    private final String xml1 = "1.xml";
    /**
     * Путь к второму xml файлу.
     */
    private final String xml2 = "2.xml";
    /**
     * Путь к xsl файлу.
     */
    private final String xsl = "converter.xsl";

    public OptimizationXML(ConnectionSqLite connectionSqLite, int element) {
        this.connection = connectionSqLite.getConnection();
        this.element = element;
    }

    /**
     * Запуск c DOM.
     */
    public void startProgramDOM() {
        createTestTable();
        Document document = createFirstXmlWithDom(new File(xml1), DocumentBuilderFactory.newInstance());
        writeDocumentDOM(document, xml1);
        convert(new File(xml1), new File(xsl), new File(xml2));
        parsing(SAXParserFactory.newInstance(), new Handler(), new File(xml2));
    }

    /**
     * Запук с SAX.
     */
    public void startProgramWithSax() {
        createTestTable();
        createFirstXmlWithSAX(new File(xml1), XMLOutputFactory.newInstance());
        convert(new File(xml1), new File(xsl), new File(xml2));
        parsing(SAXParserFactory.newInstance(), new Handler(), new File(xml2));
    }

    /**
     * Метод создает Новую таблицу TEST в бд.
     */
    public void createTestTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS TEST");
            statement.executeUpdate("CREATE TABLE TEST(FIELD int)");
            insert(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Медод подключается к бд и заполняет ее значениями в соответствии
     * с требованиями к задаче.
     * @param con соединение с бд.
     */
    private void insert(Connection con) {
        try {
            con.setAutoCommit(false);
            PreparedStatement statement = con.prepareStatement(
                    "INSERT INTO TEST (FIELD) VALUES (?)");
            for (int i = 1; i < element + 1; i++) {
                statement.setInt(1, i);
                statement.addBatch();
            }
            statement.executeBatch();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * Метод читает информацию из бд формирует дерево DOM.
     * и возвращает его к записи.
     */
    public Document createFirstXmlWithDom(File input, DocumentBuilderFactory factory) {
        createFile(input.getPath());
        DocumentBuilder documentBuilder;
        Document doc = null;
        try (Statement statement = connection.createStatement()) {
            factory.setNamespaceAware(true);
            factory.setIgnoringElementContentWhitespace(true);
            documentBuilder = factory.newDocumentBuilder();
            doc = documentBuilder.newDocument();

            Element root = doc.createElement("entries");
            doc.appendChild(root);

            ResultSet rs = statement.executeQuery("SELECT * FROM TEST");
            while (rs.next()) {
                Element entry = doc.createElement("entry");
                root.appendChild(entry);

                Element field = doc.createElement("field");
                field.setTextContent(rs.getString("FIELD"));
                entry.appendChild(field);
            }
            //  removeWhitespaceNodes(doc.getDocumentElement());
        } catch (ParserConfigurationException | SQLException e) {
            e.printStackTrace();
        }
        return doc;
    }

    /**
     *Создание и запись первого XML файла.
     * @param input путь к файлу.
     * @param factory фабрика.
     */
    public void createFirstXmlWithSAX(File input, XMLOutputFactory factory) {
        createFile(input.getPath());
        factory.setProperty(XMLOutputFactory.IS_REPAIRING_NAMESPACES, false);
        try (Statement statement = connection.createStatement()) {
            XMLStreamWriter writer = factory.createXMLStreamWriter(new BufferedWriter(new FileWriter(xml1)));
            writer.writeStartDocument("UTF-8", "1.1");
            writer.writeStartElement("entries");
            ResultSet rs = statement.executeQuery("SELECT * FROM TEST");
            while (rs.next()) {
                writer.writeStartElement("field");
                writer.writeStartElement("entry");
                writer.writeCharacters(rs.getString("FIELD"));
                writer.writeEndElement();
                writer.writeEndElement();
            }
            writer.writeEndElement();
            writer.writeEndDocument();
            writer.flush();
            writer.close();
        } catch (XMLStreamException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * Метод создает файл или проверяет его наличие в системе.
     * @param path путь к файлу.
     */
    public void createFile(String path) {
        File xml = new File(path);
        try {
            boolean created = xml.createNewFile();
            if (created) {
                System.out.println(String.format("Файл %s создан.", path));
            } else {
                System.out.println(String.format("Файл %s уже существует и будет перезаписан.", path));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод сохраняет документ в файл.
     * @param document документ.
     * @param path путь к файлу.
     */
    public void writeDocumentDOM(Document document, String path) {
        Transformer transformer;
        DOMSource domSource;
        FileOutputStream fos;
        try {
            transformer = TransformerFactory.newInstance().newTransformer();
            domSource = new DOMSource(document);
            fos = new FileOutputStream(path);
            StreamResult streamResult = new StreamResult(fos);
            //две строки которые преобразуют хмл к выводу в столбик, а не в строчку
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            //делает отступ от края страницы
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(domSource, streamResult);
        } catch (FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод с поьмощью xsl трансформирует 1xml в 2xml в соответствии с требованиями.
     * Сохроняет результат в файл 2.xml.
     */
    public void convert(File inputFile, File xsl, File outputFile) {
        Source xmlInput = new StreamSource(inputFile);
        Source xslFile = new StreamSource(xsl);
        createFile(outputFile.getPath());
        Result xmlOutput = new StreamResult(outputFile);
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer(xslFile);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * метод удаляет все пробелы из дерева.
     * @param e корень дерева.
     */
    public  void removeWhitespaceNodes(Element e) {
        NodeList children = e.getChildNodes();
        for (int i = children.getLength() - 1; i >= 0; i--) {
            Node child = children.item(i);
            if (child instanceof Text && ((Text) child).getData().trim().length() == 0) {
                e.removeChild(child);
            } else if (child instanceof Element) {
                removeWhitespaceNodes((Element) child);
            }
        }
    }

    /**
     *Метод парсит 2.xml и выводит полученный результат.
     */
    public void parsing(SAXParserFactory factory, Handler handler, File input) {
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            parser.parse(input, handler);
            System.out.println(String.format("Сумма элементов : %d", handler.getCount()));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

}