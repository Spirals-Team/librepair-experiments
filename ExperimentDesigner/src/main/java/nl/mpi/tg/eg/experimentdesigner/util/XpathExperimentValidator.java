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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @since Jun 7, 2018 4:21:45 PM (creation date)
 * @author Peter Withers <peter.withers@mpi.nl>
 */
public class XpathExperimentValidator {

    public void validateDocument(File xmlFile) throws IllegalArgumentException, IOException, ParserConfigurationException, SAXException, XPathExpressionException, XpathExperimentException {
        FileInputStream fileInputStream = new FileInputStream(xmlFile);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document xmlDocument = builder.parse(fileInputStream);
        String fileName = xmlFile.getName();
        String result = "";
        result += validateInternalName(fileName, xmlDocument);
        result += validatePresenterNames(xmlDocument);
        result += validatePresenterLinks(xmlDocument);
        result += validateStimuliTags(xmlDocument);
        if (!result.isEmpty()) {
            throw new XpathExperimentException(result);
        }
    }

    protected String validateInternalName(String fileName, Document xmlDocument) throws XPathExpressionException {
        // check that the name is lower case and validate that the file name matches the internal name of the experiment
        XPath validationXPath = XPathFactory.newInstance().newXPath();
        String appNameInternal = (String) validationXPath.compile("/experiment/@appNameInternal").evaluate(xmlDocument, XPathConstants.STRING);
        if (!fileName.equals(fileName.toLowerCase())) {
            return "The experiment file name must be lowercase: '" + fileName + "'.\n";
        }
        if (!appNameInternal.equals(appNameInternal.toLowerCase())) {
            return "The experiment appNameInternal must be lowercase: '" + appNameInternal + "'.\n";
        }
        if (!appNameInternal.equals(fileName.replaceFirst("\\.xml$", ""))) {
            return "The experiment appNameInternal must match the XML file name: '" + appNameInternal + "'.\n";
        }
        return "";
    }

    protected String validatePresenterNames(Document xmlDocument) throws XPathExpressionException {
        String returnMessage = "";
        final ArrayList<String> presenterNames = new ArrayList<>();
        XPath validationXPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList = (NodeList) validationXPath.compile("/experiment/presenter/@self").evaluate(xmlDocument, XPathConstants.NODESET);
        for (int index = 0; index < nodeList.getLength(); index++) {
            final String presenterName = nodeList.item(index).getTextContent();
            if (presenterNames.contains(presenterName)) {
                returnMessage += "Each presenter name must be unique, but '" + presenterName + "' is used on another presenter.\n";
                System.out.println(returnMessage);
            } else {
                presenterNames.add(presenterName);
            }
        }
        return returnMessage;
    }

    protected String validatePresenterLinks(Document xmlDocument) throws XPathExpressionException {
        String returnMessage = "";
        final ArrayList<String> presenterNames = new ArrayList<>();
        XPath validationXPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList1 = (NodeList) validationXPath.compile("/experiment/presenter/@self").evaluate(xmlDocument, XPathConstants.NODESET);
        for (int index = 0; index < nodeList1.getLength(); index++) {
            final String presenterName = nodeList1.item(index).getTextContent();
            presenterNames.add(presenterName);
//            System.out.println("presenterName: " + presenterName);
        }
        for (String testType : new String[]{"target", "back", "next"}) {
            NodeList nodeList2 = (NodeList) validationXPath.compile("//@" + testType).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int index = 0; index < nodeList2.getLength(); index++) {
                final String targetName = nodeList2.item(index).getTextContent();
//                System.out.println("targetName: " + targetName);
                if (!presenterNames.contains(targetName)) {
                    returnMessage += "Each '" + testType + "' attribute must reference a valid presenter, but '" + targetName + "' is not the self name of any presenter.\n";
                    System.out.println(returnMessage);
                }
            }
        }
        return returnMessage;
    }

    protected String validateStimuliTags(Document xmlDocument) throws XPathExpressionException {
        String returnMessage = "";
        final ArrayList<String> tagNames = new ArrayList<>();
        XPath validationXPath = XPathFactory.newInstance().newXPath();
        NodeList nodeList1 = (NodeList) validationXPath.compile("/experiment/stimuli/stimulus/@tags").evaluate(xmlDocument, XPathConstants.NODESET);
        for (int index = 0; index < nodeList1.getLength(); index++) {
            final String tagsString = nodeList1.item(index).getTextContent();
            for (String tagString : tagsString.split(" ")) {
                if (!tagNames.contains(tagString)) {
                    tagNames.add(tagString);
//                    System.out.println("tag: " + tagString);
                }
            }
        }
        for (String testType : new String[]{"tag", "@tags"}) {
            NodeList nodeList2 = (NodeList) validationXPath.compile("/experiment/presenter//" + testType).evaluate(xmlDocument, XPathConstants.NODESET);
            for (int index = 0; index < nodeList2.getLength(); index++) {
                final String targetName = nodeList2.item(index).getTextContent();
                if (targetName.contains(" ") && !testType.endsWith("s")) {
                    returnMessage += "The attribute '" + testType + "' cannot contain more than one tag, but '" + targetName + "' was found.\n";
                }
                for (String targetTag : targetName.split(" ")) {
//                    System.out.println("targetTag: " + targetTag);
                    if (!tagNames.contains(targetTag)) {
                        returnMessage += "Each '" + testType + "' attribute must reference a valid stimuli tag, but '" + targetTag + "' is not specified any stimuli.\n";
                        System.out.println(returnMessage);
                    }
                }
            }
        }
        return returnMessage;
    }
}
