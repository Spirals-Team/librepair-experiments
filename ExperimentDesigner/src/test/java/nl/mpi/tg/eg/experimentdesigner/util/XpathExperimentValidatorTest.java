/*
 * Copyright (C) 2018 Max Planck Institute for Psycholinguistics, Nijmegen
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package nl.mpi.tg.eg.experimentdesigner.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @since Jun 7, 2018 5:02:22 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class XpathExperimentValidatorTest {

    public XpathExperimentValidatorTest() {
    }

    private Document getDocument(final String stringXml) throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(stringXml.getBytes("UTF-8"));
        return builder.parse(inputStream);
    }

    /**
     * Test of validateInternalName method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidateInternalName() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validateInternalName");
        String fileOkName = "generic_example";
        String fileFailName = "geNeric_example";
        Document xmlOkDocument = getDocument("<experiment appNameDisplay=\"generic_example\" appNameInternal=\"generic_example\"/>\n");
        Document xmlFailDocument = getDocument("<experiment appNameDisplay=\"generic_example\" appNameInternal=\"generic_exaMple\"/>\n");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validateInternalName(fileOkName, xmlOkDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName, xmlOkDocument));
        assertNotEquals("", instance.validateInternalName(fileOkName, xmlFailDocument));
        assertNotEquals("", instance.validateInternalName(fileFailName, xmlFailDocument));
    }

    /**
     * Test of validatePresenterNames method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidatePresenterNames() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validatePresenterNames");
        Document xmlOkDocument = getDocument("<experiment><presenter self=\"Toestemming\"></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        Document xmlFailDocument = getDocument("<experiment><presenter self=\"Informatie\"></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validatePresenterNames(xmlOkDocument));
        assertNotEquals("", instance.validatePresenterNames(xmlFailDocument));
    }

    /**
     * Test of validatePresenterLinks method, of class XpathExperimentValidator.
     *
     * @throws java.io.IOException
     * @throws javax.xml.parsers.ParserConfigurationException
     * @throws org.xml.sax.SAXException
     * @throws javax.xml.xpath.XPathExpressionException
     */
    @Test
    public void testValidatePresenterLinks() throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        System.out.println("validatePresenterLinks");
        Document xmlOkDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>\n");
        Document xmlFailNextDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Missing\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailBackDocument = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Missing\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailTarget1Document = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"missing\"/><targetButton target=\"Informatie\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        Document xmlFailTarget2Document = getDocument("<experiment><presenter self=\"Toestemming\" next=\"Informatie\" back=\"Informatie\"><eraseUsersDataButton target=\"Informatie\"/><targetButton target=\"missing\"/></presenter><presenter self=\"Informatie\"></presenter></experiment>");
        XpathExperimentValidator instance = new XpathExperimentValidator();
        assertEquals("", instance.validatePresenterLinks(xmlOkDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailNextDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailBackDocument));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailTarget1Document));
        assertNotEquals("", instance.validatePresenterLinks(xmlFailTarget2Document));
    }
}
