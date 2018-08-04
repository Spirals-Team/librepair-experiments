package ru.job4j.jdbc.xml.store;

import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;
import ru.job4j.jdbc.xml.load.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Yury Matskevich
 */
public class StoreXMLTest {
	//configuration file of connection
	private Config config = new Config("/configLite.properties");
	private File file = Paths.get(
			"src",
			"test",
			"resources",
			"file1.xml"
	).toFile();

	@Before
	public void fillDb() {
		new StoreSQL(config).generate(2);
	}

	@Test
	public void testConverterFromDateOfDbToXML()
			throws IOException, SAXException {
		new StoreXML(config, file).createXML();
		String expectedXml =
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
						+ "<entries>\n"
						+ "    <entry>\n"
						+ "        <field>1</field>\n"
						+ "    </entry>\n"
						+ "    <entry>\n"
						+ "        <field>2</field>\n"
						+ "    </entry>\n"
						+ "</entries>\n";
		XMLUnit.setIgnoreWhitespace(true);
		String actualXml = new String(Files.readAllBytes(file.toPath()), "UTF-8");
		XMLAssert.assertXMLEqual(expectedXml, actualXml);
	}
}