package ru.aserdyuchenko.testTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class OrderBook {
    private List<String> bidSet = new LinkedList<String>();
    private List<String> askSet = new LinkedList<String>();

    public void delete() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("test.xml");

        NodeList nodes = doc.getElementsByTagName("Orders");
        Element root = (Element) nodes.item(0);

        NodeList nodeList = doc.getElementsByTagName("AddOrder");
        Element child = (Element) nodeList.item(0);

        root.removeChild(child);
    }

    public void print() throws IOException, SAXException, ParserConfigurationException {
        readXML();
        Iterator bidIterator = bidSet.iterator();
        Iterator askIterator = askSet.iterator();
        System.out.println("BID \t\t\t ASK");
        System.out.println("Volume@Price - Volume@Price");
        while (bidIterator.hasNext() || askIterator.hasNext()) {
            if (bidIterator.hasNext() && !askIterator.hasNext()) {
                System.out.println(bidIterator.next() + "\t - --------" );
            } else if (!bidIterator.hasNext() && askIterator.hasNext()) {
                System.out.println("--------\t - " + askIterator.next());
            } else {
                System.out.println(bidIterator.next() + "\t\t - " + askIterator.next());
            }
        }
    }

    public void saveToXML(String book, Order order) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse("test.xml");

        NodeList nodes = doc.getElementsByTagName("Orders");
        Element root = (Element) nodes.item(0);
        Element item1 = doc.createElement("AddOrder");

        item1.setAttribute("book", book);
        item1.setAttribute("operation", order.getOperation());
        item1.setAttribute("volume", String.valueOf(order.getVolume()));
        item1.setAttribute("orderID", String.valueOf(order.getOrderID()));
        item1.setAttribute("price", String.valueOf(order.getPrice()));

        root.appendChild(item1);
        File file = new File("test.xml");

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(new DOMSource(doc), new StreamResult(file));

    }

    public void readXML() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("test.xml");

        NodeList nodeList = doc.getElementsByTagName("AddOrder");

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getAttributes().item(1).getNodeValue().equals("BUY")) {
                bidSet.add(nodeList.item(i).getAttributes().item(4).getNodeValue() + "@" + nodeList.item(i).getAttributes().item(3).getNodeValue());
            } else if (nodeList.item(i).getAttributes().item(1).getNodeValue().equals("SELL")) {
                askSet.add(nodeList.item(i).getAttributes().item(4).getNodeValue() + "@" + nodeList.item(i).getAttributes().item(3).getNodeValue());
            }
        }

        Collections.sort(bidSet, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String str1 = o1.substring(o1.indexOf("@") + 1);
                String str2 = o2.substring(o1.indexOf("@") + 1);
                if (Double.parseDouble(str1) < Double.parseDouble(str2)) return -1;
                if (Double.parseDouble(str1) > Double.parseDouble(str2)) return 1;
                return 0;
            }
        });
        Collections.sort(askSet, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String str1 = o1.substring(o1.indexOf("@") + 1);
                String str2 = o2.substring(o1.indexOf("@") + 1);
                if (Double.parseDouble(str1) < Double.parseDouble(str2)) return -1;
                if (Double.parseDouble(str1) > Double.parseDouble(str2)) return 1;
                return 0;
            }
        });
    }
}
