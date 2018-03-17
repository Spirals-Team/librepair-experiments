package ru.job4j.jdbc;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 03.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class OptimizatorTest {
    private OptimizationXML optimizator;
    String url;
    private final String xml1 = "1.xml";
    private final String xml2 = "2.xml";
    /**
     * Путь к xsl файлу.
     */
    private final String xsl = "converter.xsl";
    @Before
    @Ignore
    public void init() {
        optimizator = new OptimizationXML(new ConnectionSqLite(), 1000);
        if (System.getProperty("os.name").equals("Linux")) {
            this.url = "jdbc:sqlite:/home/andrei/java.db";
        } else {
            this.url =  "jdbc:sqlite:D:/sqlite/java.db";
        }
    }

    /**
     * Тест проверяет создание таблицы в бд и заполняет ее двумя значениями 1 и 2
     * затем получает их из бд и сравнивает с ожидаемым результатом.
     * @throws Exception ex.
     */
    @Test
    @Ignore
    public void whenWasAddTwoElementsInTestTable() throws Exception {
        optimizator.createTestTable();
        Connection con = DriverManager.getConnection(url);
        Statement st = con .createStatement();
        ResultSet rt = st.executeQuery("SELECT * FROM TEST");
        int[]result = new int[optimizator.getElement()];
        int count = 0;
        while (rt.next()) {
            result[count++] = rt.getInt("FIELD");
        }
        int[]ex = new int[optimizator.getElement()];
        int count2 = 0;
        for (int i = 0; i < ex.length; i++) {
            ex[count2++] = count2;
        }
        assertThat(result, is(ex));
    }

    /**
     * Метод для запуска программы .
     * @throws Exception ex.
     */
    @Test
    @Ignore
    public void startProgramWithDOM() throws Exception {
        long time = System.currentTimeMillis();
        optimizator.startProgramDOM();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println(String.format("Время работы программы : %d сек.", time));
    }

    @Test
    @Ignore
    public void startProgramWithSAX() {
        long time = System.currentTimeMillis();
        optimizator.startProgramWithSax();
        time = (System.currentTimeMillis() - time) / 1000;
        System.out.println(String.format("Время работы программы : %d сек.", time));
    }

    /**
     * Тест проверяет если колличество элементов равно 2 то в 1.xml
     * будут два узла со значениями 1 и 2 соответственно.
     * @throws Exception ex.
     */
    @Test
    @Ignore
    public void whenAddNewTwoElementsThenDocumentHasThisElements() throws Exception {
        optimizator.createTestTable();
        Document document = optimizator.createFirstXmlWithDom(new File(xml1), DocumentBuilderFactory.newInstance());
        NodeList list = document.getElementsByTagName("entry");
        String[]elements = new String[optimizator.getElement()];
        int count = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                elements[count++] = e.getElementsByTagName("field").item(0).getTextContent();
            }
        }
        String[]ex = new String[optimizator.getElement()];
        int count2 = 0;
        for (int i = 0; i < ex.length; i++) {
            ex[count2++] = String.valueOf(count2);
        }
        assertThat(elements, is(ex));
    }

    /**
     * Тест проверяет то что после конвертирования 1.xml
     * значение узлов стало их атрибутами.
     * @throws Exception ex.
     */
    @Test
    @Ignore
    public void whenNodeHasValueThenNOdeHasAttribute() throws Exception {
        optimizator.createTestTable();
        optimizator.convert(new File(xml1), new File(xsl), new File(xml2));
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("2.xml"));
        NodeList list = document.getElementsByTagName("entry");
        String[]elements = new String[optimizator.getElement()];
        int count = 0;
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;
                elements[count++] = e.getAttribute("field");
            }
        }
        String[]ex = new String[optimizator.getElement()];
        int count2 = 0;
        for (int i = 0; i < ex.length; i++) {
            ex[count2++] = String.valueOf(count2);
        }
        assertThat(elements, is(ex));
    }
}