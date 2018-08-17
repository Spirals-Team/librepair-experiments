package ru.job4j.jdbc.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * Class ConvertXSQT.
 */
public class ConvertXSQT {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConvertXSQT.class);

    /**
     * Method for converting xml data
     * @param source convert from
     * @param dest convert to
     * @param scheme scheme for converting
     */
    public void convert(File source, File dest, File scheme) {
        try {
            Source sScheme = new StreamSource(new FileInputStream(scheme));
            Source sSource = new StreamSource(new FileInputStream(source));
            Result rDest = new StreamResult(new FileOutputStream(dest));
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(sScheme);
            transformer.transform(sSource, rDest);
        } catch (FileNotFoundException | TransformerException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
