package ru.job4j.jdbc.xml.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;

/**
 * @author Yury Matskevich
 */
public class ParsXML {
	private static final Logger LOG = LoggerFactory
			.getLogger(ParsXML.class);
	private File file;

	public ParsXML(File file) {
		this.file = file;
	}

	/**
	 * Parses file
	 */
	public void parse() {
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = spf.newSAXParser();
			saxParser.parse(file, new Handler());
		} catch (ParserConfigurationException
				| SAXException
				| IOException e) {
			LOG.error(e.getMessage(), e);
		}
	}

	private class Handler extends DefaultHandler {
		private int number = 0;

		@Override
		public void startElement(
				String uri,
				String localName,
				String qName,
				Attributes attributes) {
			if (attributes.getLength() != 0) {
				number += Integer.parseInt(
						attributes.getValue("field")
				);
			}
		}

		@Override
		public void endDocument() {
			System.out.print(number);
		}
	}
}
