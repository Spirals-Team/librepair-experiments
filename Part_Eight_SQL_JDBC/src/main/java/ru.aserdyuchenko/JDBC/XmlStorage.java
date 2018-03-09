package ru.aserdyuchenko.JDBC;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class XmlStorage {
    /**
     * Save to 1.xml
     * @param list - number for save.
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     */
    public void saveToXML(List<Integer> list) throws ParserConfigurationException, TransformerConfigurationException {
        Iterator<Integer> iterator = list.iterator();
        Document dom;
        Element e = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.newDocument();
            Element rootEle = dom.createElement("entries");
            while (iterator.hasNext()) {
                e = dom.createElement("entry");
                Element child = dom.createElement("field");
                child.setTextContent(iterator.next().toString());
                e.appendChild(child);
                rootEle.appendChild(e);
            }

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                tr.transform(new DOMSource(dom),
                        new StreamResult(new FileOutputStream("./src/main/java/ru.aserdyuchenko/JDBC/1.xml")));

            } catch (TransformerException | IOException te) {
                System.out.println(te.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    /**
     * Read from 1.xml
     * @return list with numbers.
     */
    public List<String> readXML() {
        List<String> list = new ArrayList<>();
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("./src/main/java/ru.aserdyuchenko/JDBC/1.xml");

            NodeList doc = dom.getElementsByTagName("field");
            for (int i = 0; i < doc.getLength(); i++){
                String number = doc.item(i).getTextContent();
                list.add(number);
            }

            return list;
        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return null;
    }

    public void XsltTransformation() throws TransformerException {
        String xmlFile = "./src/main/java/ru.aserdyuchenko/JDBC/1.xml";
        String xslFile = "./src/main/java/ru.aserdyuchenko/JDBC/file.xml";

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File(xslFile));
        Transformer transformer = factory.newTransformer(xslt);

        Source text = new StreamSource(new File(xmlFile));
        transformer.transform(text, new StreamResult(new File("./src/main/java/ru.aserdyuchenko/JDBC/2.xml")));
    }

    public int getSum() {
        int sum = 0;
        Document dom;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            dom = db.parse("./src/main/java/ru.aserdyuchenko/JDBC/2.xml");
            NodeList doc = dom.getElementsByTagName("entry");

            for (int i = 0; i < doc.getLength(); i++){
                String number = doc.item(i).getAttributes().getNamedItem("field").getNodeValue();
                sum += Integer.parseInt(number);
            }
            return sum;

        } catch (ParserConfigurationException | SAXException pce) {
            System.out.println(pce.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        return -1;
    }
}
