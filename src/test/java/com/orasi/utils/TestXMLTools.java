package com.orasi.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.junit.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.orasi.AutomationException;
import com.orasi.utils.exception.XPathInvalidExpression;
import com.orasi.utils.exception.XPathNotFoundException;
import com.orasi.web.WebBaseTest;

import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;
import ru.yandex.qatools.allure.annotations.Title;

public class TestXMLTools extends WebBaseTest {
    @BeforeTest
    public void setup() {
        setReportToMustard(false);
    }

    @Override
    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult testResults) {
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithFile")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void makeXMLDocumentWithSOAPMessage() throws IOException, SOAPException {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        InputStream in = new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset()));
        SOAPMessage request = messageFactory.createMessage(new MimeHeaders(), in);
        Document doc = XMLTools.makeXMLDocument(request);
        Assert.assertNotNull(doc);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithFile")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = AutomationException.class)
    public void makeXMLDocumentWithSOAPMessageNegativeInvalidXML() throws IOException, SOAPException {
        String xml = "<catalog><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
        InputStream in = new ByteArrayInputStream(xml.getBytes(Charset.defaultCharset()));
        SOAPMessage request = messageFactory.createMessage(new MimeHeaders(), in);
        Document doc = XMLTools.makeXMLDocument(request);
        Assert.assertNotNull(doc);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithFile")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void makeXMLDocumentWithFile() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        Assert.assertNotNull(doc);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithFileNegativeNoFileFound")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = AutomationException.class)
    public void makeXMLDocumentWithFileNegativeNoFileFound() {
        XMLTools.makeXMLDocument(new File(""));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithFileNegativeInvalidXML")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = AutomationException.class)
    public void makeXMLDocumentWithFileNegativeInvalidXML() {
        XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books_invalid_xml.xml").getPath()));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithString")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void makeXMLDocumentWithString() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        Assert.assertNotNull(doc);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithStringNegativeNullString")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = AutomationException.class)
    public void makeXMLDocumentWithStringNegativeNullString() {
        String xml = "";
        XMLTools.makeXMLDocument(xml);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("makeXMLDocumentWithStringNegativeInvalidXML")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = AutomationException.class)
    public void makeXMLDocumentWithStringNegativeInvalidXML() {
        String xml = "<book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        XMLTools.makeXMLDocument(xml);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("transformXmlToString")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void transformXmlToString() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        Assert.assertTrue(XMLTools.transformXmlToString(doc).replaceAll("\\r|\\n|\\t| ", "").contains(xml.replaceAll(" ", "")));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getValueByXpathNode")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void getValueByXpathNode() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        Assert.assertTrue(XMLTools.getValueByXpath(doc, "/catalog/book/author").equals("Gambardella, Matthew"));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getValueByXpathNodeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void getValueByXpathNodeNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.getValueByXpath(doc, "/catalog/book/autr");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getValueByXpathNodeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void getValueByXpathNodeNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.getValueByXpath(doc, "/catalog/book/author\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getValueByXpathAttribute")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void getValueByXpathAttribute() {
        String xml = "<catalog><book id=\"101\"><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        Assert.assertTrue(XMLTools.getValueByXpath(doc, "/catalog/book/@id").equals("101"));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttribute")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void addAttribute() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addAttribute(doc, "id", "/catalog/book");
        Assert.assertTrue(XMLTools.getValueByXpath(doc, "/catalog/book/@id").equals(""));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttributeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void addAttributeNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addAttribute(doc, "id", "/catalog/bo\'");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttributeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void addAttributeNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addAttribute(doc, "id", "/catalog/bo");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttribute")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void addNamespace() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addNamespace(doc, "xmlns:h,http://www.w3.org/TR/html4/", "/catalog");
        Assert.assertTrue(XMLTools.transformXmlToString(doc).contains("http://www.w3.org/TR/html4/"));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttributeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void addNamespaceNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addNamespace(doc, "xmlns:h,http://www.w3.org/TR/html4/", "/catag");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addAttributeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void addNamespaceNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addNamespace(doc, "xmlns:h,http://www.w3.org/TR/html4/", "/catag\'");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addNode")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void addNode() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addNode(doc, "description", "/catalog/book");
        Assert.assertTrue(XMLTools.getValueByXpath(doc, "/catalog/book/description").equals(""));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addNodeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void addNodeNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addNode(doc, "description", "/catalog/bo");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("addNodeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void addNodeNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        XMLTools.addNode(doc, "description", "/catalog/bo\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeAttribute")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addAttribute", expectedExceptions = XPathNotFoundException.class)
    public void removeAttribute() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addAttribute(doc, "id", "/catalog/book");
        doc = XMLTools.removeAttribute(doc, "id", "/catalog/book");
        XMLTools.getValueByXpath(doc, "/catalog/book/@id");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeAttributeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addAttribute", expectedExceptions = XPathNotFoundException.class)
    public void removeAttributeNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addAttribute(doc, "id", "/catalog/book");
        XMLTools.removeAttribute(doc, "id", "/catalog/bo");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeAttributeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addAttribute", expectedExceptions = XPathInvalidExpression.class)
    public void removeAttributeNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addAttribute(doc, "id", "/catalog/book");
        XMLTools.removeAttribute(doc, "id", "/catalog/bo\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeNode")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addNode", expectedExceptions = XPathNotFoundException.class)
    public void removeNode() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addNode(doc, "description", "/catalog/book");
        doc = XMLTools.removeNode(doc, "/catalog/book/description");
        XMLTools.getValueByXpath(doc, "/catalog/book/description");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeNodeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addNode", expectedExceptions = XPathNotFoundException.class)
    public void removeNodeNegativeInvalidXpath() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addNode(doc, "description", "/catalog/book");
        XMLTools.removeNode(doc, "/catalog/book/descron");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeNodeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, dependsOnMethods = "addNode", expectedExceptions = XPathInvalidExpression.class)
    public void removeNodeNegativeInvalidXpathExpression() {
        String xml = "<catalog><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = XMLTools.addNode(doc, "description", "/catalog/book");
        XMLTools.removeNode(doc, "/catalog/book/descron\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeComments")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void removeComments() {
        String xml = "<catalog><!--Comment block--><book><author>Gambardella, Matthew</author><title>XML Developers Guide</title><genre>Computer</genre><price>44.95</price></book></catalog>";
        Document doc = XMLTools.makeXMLDocument(xml);
        doc = (Document) XMLTools.removeComments(doc);
        Assert.assertFalse(XMLTools.transformXmlToString(doc).contains("<!--Comment block-->"));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("removeWhitespace")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void removeWhitespace() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        doc = XMLTools.removeWhiteSpace(doc);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocument")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void getNodeListByDocument() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        XMLTools.getNodeList(doc, "/catalog/book");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocumentNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void getNodeListByDocumentNegativeInvalidXpath() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        XMLTools.getNodeList(doc, "/catalog/bo");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocumentNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void getNodeListByDocumentNegativeInvalidXpathExpression() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        XMLTools.getNodeList(doc, "/catalog/bo\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocument")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void getNodeListByNodeList() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        NodeList list = XMLTools.getNodeList(doc, "/catalog/book");
        Assert.assertTrue(XMLTools.getNodeList(list.item(0), "/*").getLength() == 1);
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocumentNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void getNodeListByNodeListNegativeInvalidXpath() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        NodeList list = XMLTools.getNodeList(doc, "/catalog/book");
        XMLTools.getNodeList(list.item(0), "/title");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeListByDocumentNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void getNodeListByNodeListNegativeInvalidXpathExpression() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        NodeList list = XMLTools.getNodeList(doc, "/catalog/book");
        XMLTools.getNodeList(list.item(0), "/tit\"");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNode")
    @Test(groups = { "regression", "utils", "XMLTools" })
    public void getNode() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        Assert.assertTrue(XMLTools.getNode(doc, "/catalog/book/title").getTextContent().equals("XML Developer's Guide"));
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeNegativeInvalidXpath")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathNotFoundException.class)
    public void getNodeNegativeInvalidXpath() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        XMLTools.getNode(doc, "/catalog/book/ti");
    }

    @Features("Utilities")
    @Stories("XMLTools")
    @Title("getNodeNegativeInvalidXpathExpression")
    @Test(groups = { "regression", "utils", "XMLTools" }, expectedExceptions = XPathInvalidExpression.class)
    public void getNodeNegativeInvalidXpathExpression() {
        Document doc = XMLTools.makeXMLDocument(new File(this.getClass().getResource("/xmls/books.xml").getPath()));
        XMLTools.getNode(doc, "/catalog/book/ti\"");
    }
}
