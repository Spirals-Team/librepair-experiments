package ru.job4j.jdbc.xml.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author Yury Matskevich
 */
public class ConvertXSQT {
	private static final Logger LOG = LoggerFactory
			.getLogger(ConvertXSQT.class);

	/**
	 * Converts xml from one scheme to other
	 * @param source a file of .xml which is base for converting
	 * @param dest a file of .xml which will represent different scheme
	 * of the source file
	 * @param scheme a scheme which represents form of .xml file after
	 * converting
	 */
	public void convert(File source, File dest, File scheme) {
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(scheme));
			transformer.transform(new StreamSource(source), new StreamResult(dest));
		} catch (TransformerException e) {
			LOG.error(e.getMessage(), e);
		}
	}
}
