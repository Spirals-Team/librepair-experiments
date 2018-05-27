package ru.job4j.sql;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class XSLTWorker {
    public void work(String sourceXML, String resultXML, String styleXSL) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Source xslt = new StreamSource(styleXSL);
            Transformer transformer = transformerFactory.newTransformer(xslt);
            Source original = new StreamSource(sourceXML);
            transformer.transform(original, new StreamResult(resultXML));
        } catch (TransformerException te) {
            te.printStackTrace();
        }
    }
}
