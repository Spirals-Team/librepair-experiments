package ru.job4j.jdbc.xml.convert;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import ru.job4j.jdbc.xml.load.Config;
import ru.job4j.jdbc.xml.store.StoreSQL;
import ru.job4j.jdbc.xml.store.StoreXML;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Yury Matskevich
 */
public class ConvertXSQTTest {
	//configuration file of connection
	private Config config = new Config("/configLite.properties");
	private File file = Paths.get(
			"src",
			"test",
			"resources",
			"file1.xml"
	).toFile();
	private File dest = Paths.get(
			"src",
			"test",
			"resources",
			"file2.xml"
	).toFile();
	private File scheme = Paths.get(
			"src",
			"test",
			"resources",
			"scheme.xsl"
	).toFile();

	@Before
	public void setUp() {
		new StoreSQL(config).generate(2);
		new StoreXML(config, file).createXML();
	}

	@Test
	public void testConverFromXmlToXml() throws IOException, SAXException {
		new ConvertXSQT().convert(file, dest, scheme);
		String expectedXml =
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
						+ "<entries>"
						+ "	<entry field=\"1\"/>"
						+ "	<entry field=\"2\"/>"
						+ "</entries>";
		XMLUnit.setIgnoreWhitespace(true);
		String actualXml = new String(Files.readAllBytes(dest.toPath()), "UTF-8");
		XMLAssert.assertXMLEqual(expectedXml, actualXml);
	}
}