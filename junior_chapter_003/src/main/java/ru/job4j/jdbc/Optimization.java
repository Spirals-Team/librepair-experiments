package ru.job4j.jdbc;

import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс Optimization.
 *
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 12.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class Optimization {
    /**
     * Ссылка на подключения к БД.
     */
    private Connection connection;
    /**
     * Количество элементов для операции.
     */
    private int element;

    /**
     * Конструктор класса.
     *
     * @param element       количество элементов для операции.
     * @param connectionSQL ссылка на объект подключения к БД.
     */
    public Optimization(int element, ConnectionSQL connectionSQL) {
        this.element = element;
        this.connection = connectionSQL.getConnection();
    }

    /**
     * Основная точка входа в программу.
     *
     * @param args аргументы.
     */
    public static void main(String[] args) {
        ConnectionSQL connectionSQL = new ConnectionSQL("jdbc:postgresql://localhost:5432/java_a_from_z",
                "postgres", "postgres");
        Optimization optimization = new Optimization(1000000, connectionSQL);
        optimization.dropAndCreateTable();
        optimization.insertTable();
        optimization.createJAXB();
        optimization.createXSLB();
        optimization.parsingSAX();
        connectionSQL.closeConnection();
    }

    /**
     * Метод dropAndCreateTable. Удаляем таблицу из БД если она есть.
     * Создаем новую таблицу.
     */
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

    /**
     * Добавляем необходимое количество данных в таблицу.
     */
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

    /**
     * Запрашиваем данные из БДи формируем xml с помошью JAXB.
     */
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

    /**
     * Берем файл и преобразуем по средствам XSLT в другой формат.
     */
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

    /**
     * Парсим файл и выводим арифметическую сумму в консоль.
     */
    public void parsingSAX() {
        SAXHandler saxHandler = new SAXHandler();
        try {
            SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();
            saxParser.parse(new File("2.xml"), saxHandler);
            System.out.println(String.format("Арифметическую сумму значений всех атрибутов: %s", saxHandler.getAmount()));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}