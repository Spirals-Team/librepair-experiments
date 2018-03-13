package ru.job4j;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.*;

//WORK ON BUGS разбей на блоки.
//        1. блок чтения
//        2. запись в базу
//        3. xslt.
//WORK ON BUGS2
//РАЗБИТЬ НА КЛАССЫ
public class XMLAndJDBCOptimization implements Serializable {

    public static void main(String[] args) {

        long start = System.currentTimeMillis();


//        try(тут объявляются ресурсы){
//    ...
//    ...
//        }catch(Exception ex){
//    ...
//        }finally{
//    ...
//        }

        try {

            Connection connection = new MyConnection("jdbc:postgresql://localhost:5432/SQLite", "postgres",
                    "password").getConnection();

            new CreateAndFillBase(connection, 100000).createAndFillBase();

            new CreateFirstXML(connection).createFirstXML();

            new CreateSecondXMLFromFirstXMLByXSLConvertation().createSecondXMLFromFirstXMLByXSLConvertation();

            new GetArithmeticSummAndDeleteAllFiles().getArithmeticSummAndDeleteAllFiles();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        long finish = System.currentTimeMillis();
        long timeConsumedMillis = finish - start;
        System.out.println("Total time in sec. = " + timeConsumedMillis / 1000);
    }
}

class MyConnection {

    private String url;
    private String username;
    private String password;

    public MyConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

class CreateAndFillBase {

    private Connection conn;
    private int n;

    public CreateAndFillBase(Connection conn, int numberOfRows) {
        this.conn = conn;
        this.n = numberOfRows;
    }

    public CreateAndFillBase() {
    }

    public CreateAndFillBase(Connection conn) {
        this.conn = conn;
    }

    ResultSet readFromBase(String query) {
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs = null;
        try {
            rs = st.executeQuery("SELECT * FROM TEST");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    void writeInBase(String query) {
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            st.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int[] executeBatchInBase() {
        int[] count = null;
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= n; i++) {
            try {
                st.addBatch("insert into TEST (FIELD) values (" + i + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            count = st.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    public void createAndFillBase() {
        try {
            Statement st = conn.createStatement();
            conn.setAutoCommit(false); //55.55

            ResultSet rs = readFromBase("SELECT * FROM TEST");

            if (rs == null) {
                writeInBase("CREATE TABLE TEST (FIELD INTEGER )");
                writeInBase("insert into TEST (FIELD) values (1)");
                rs = readFromBase("SELECT * FROM TEST");
            }

            if (rs.next()) {
                writeInBase("delete  from TEST");
            }
            int[] count = executeBatchInBase(); //st.executeBatch();

            conn.commit();
            System.out.println("number of created rows: " + count.length);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class CreateSecondXMLFromFirstXMLByXSLConvertation {

    void createSecondXMLFromFirstXMLByXSLConvertation() {
        try {
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("task20459\\xslttest.xsl"), "utf-8"))) {
                writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<xsl:stylesheet xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\" version=\"1.0\">\n"
                        + "    <xsl:output method=\"xml\" indent=\"yes\"/>\n"
                        + "\n"
                        + "    <xsl:template match=\"/entries\">\n"
                        + "        <entries>\n"
                        + "            <xsl:apply-templates select=\"entry\"/>\n"
                        + "        </entries>\n"
                        + "    </xsl:template>\n"
                        + "\n"
                        + "    <xsl:template match=\"entry\">\n"
                        + "        <entry field=\"{field}\">\n"
                        + "\n"
                        + "        </entry>\n"
                        + "    </xsl:template>\n"
                        + "\n"
                        + "</xsl:stylesheet>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //-
        //+
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer tf = null;
        try {
            tf = tff.newTransformer(new StreamSource(new File("task20459\\xslttest.xsl")));
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }
        StreamSource ss = new StreamSource(new File("task20459\\1.xml"));
        StreamResult sr = new StreamResult(new File("task20459\\2.xml"));
        try {
            tf.transform(ss, sr);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        System.out.println("File2 saved!");
    }

}

class CreateFirstXML {

    Connection connection;

    public CreateFirstXML(Connection connection) {
        this.connection = connection;
    }

    private void createFolderForTask() {
        File file = new File("task20459");
        if (!file.exists()) {
            file.mkdir();
        }
    }

    void createFirstXML() {
        try {
            ResultSet rs = new CreateAndFillBase(connection).readFromBase("SELECT * FROM TEST");
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = null;
            try {
                docBuilder = docFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("entries");
            doc.appendChild(rootElement);
            try {
                while (rs.next()) {
                    Element entry = doc.createElement("entry");
                    rootElement.appendChild(entry);
                    Element field = doc.createElement("field");
                    field.appendChild(doc.createTextNode(String.valueOf(rs.getInt("FIELD"))));
                    entry.appendChild(field);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            createFolderForTask();

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = null;
            try {
                transformer = transformerFactory.newTransformer();
            } catch (TransformerConfigurationException e) {
                e.printStackTrace();
            }
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("task20459\\1.xml"));
            try {
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
            System.out.println("File saved!");
            //-

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

class GetArithmeticSummAndDeleteAllFiles {

    public static void recursiveDelete(File file) {
        // до конца рекурсивного цикла
        if (!file.exists()) {
            return;
        }
        //если это папка, то идем внутрь этой папки и вызываем рекурсивное удаление всего, что там есть
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                // рекурсивный вызов
                recursiveDelete(f);
            }
        }
        // вызываем метод delete() для удаления файлов и пустых(!) папок
        file.delete();
        System.out.println("Удаленный файл или папка: " + file.getAbsolutePath());
    }

    public void getArithmeticSummAndDeleteAllFiles() {

        //++++5. Приложение парсит "2.xml" и выводит арифметическую сумму значений всех атрибутов field в консоль.
        int arithmeticSumm = 0;
        File f = new File("task20459\\2.xml");
        try {
            FileReader r = null;
            try {
                r = new FileReader(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            BufferedReader br = new BufferedReader(r);
            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final char dm = (char) 34;
            try {
                while (null != (line = br.readLine())) {
                    String[] arraySting = line.split("ield=" + dm);
                    if (arraySting.length == 1) {
                        continue;
                    }
                    String[] arraySting2 = arraySting[1].split(dm + "/>");
                    int resultValue = Integer.valueOf(arraySting2[0]);
                    arithmeticSumm = arithmeticSumm + resultValue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            r.close(); // без этого не удавалось удалить папку
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("arithmeticSumm = [" + arithmeticSumm + "]");
        long finish = System.currentTimeMillis();
        recursiveDelete(new File("task20459"));
    }
}