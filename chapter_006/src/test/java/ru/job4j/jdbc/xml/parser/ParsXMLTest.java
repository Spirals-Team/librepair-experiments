package ru.job4j.jdbc.xml.parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.jdbc.xml.convert.ConvertXSQT;
import ru.job4j.jdbc.xml.load.Config;
import ru.job4j.jdbc.xml.store.StoreSQL;
import ru.job4j.jdbc.xml.store.StoreXML;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

/**
 * @author Yury Matskevich
 */
public class ParsXMLTest {
	private final PrintStream stdout = System.out;
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
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

	private void setUp() {
		new StoreSQL(config).generate(5);
		new StoreXML(config, file).createXML();
		new ConvertXSQT().convert(file, dest, scheme);
	}

	@Before
	public void loadOutput() {
		setUp();
		System.setOut(new PrintStream(out));
	}

	@After
	public void backOutput() {
		System.setOut(stdout);
	}

	@Test
	public void testParser() {
		new ParsXML(dest).parse();
		assertEquals(
				"1 + 2 + 3 + 4 + 5 = 15",
				"15",
				new String(out.toByteArray())
		);
	}
}