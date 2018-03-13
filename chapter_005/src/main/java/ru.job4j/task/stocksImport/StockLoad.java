package ru.job4j.task.stocksImport;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * TODO: comment
 * @author parsentev
 * @since 24.10.2015
 */
public class StockLoad {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        InputStream xmlInput  = new FileInputStream("../orders.xml");
        SAXParser saxParser = factory.newSAXParser();
        StockHandler handler = new StockHandler();
        saxParser.parse(xmlInput, handler);
//		OrderMather orderMather = new OrderMather(handler.list);
//		orderMather.match();
        System.out.println(String.format("time : %s s", (System.currentTimeMillis() - start)));
    }

    public boolean parse() {

        try {
            long start = System.currentTimeMillis();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            InputStream xmlInput  = new FileInputStream("C:\\projects\\orders.xml");//"../orders.xml");
            SAXParser saxParser = factory.newSAXParser();
            StockHandler handler = new StockHandler();
            saxParser.parse(xmlInput, handler);
            System.out.println(String.format("time : %s s", (System.currentTimeMillis() - start)));

            return true;

        } catch (Error e) {

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return false;

    }
}
