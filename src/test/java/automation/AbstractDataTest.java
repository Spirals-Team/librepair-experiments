package automation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

/**
 * AbstractDataTest Class.
 */
public class AbstractDataTest {

	public class TestData extends AbstractData {
	}

	@Test
	public void testAbstractData() {
		final TestData testData = new TestData();
		assertNotNull(testData);
	}

	@Test
	public void testExtendAbstractData() {
		final TestData testData = new TestData();
		assertNotNull(testData);
	}

	@Test
	public void testLoadNull() throws Exception {
		new TestData().load(null);
	}

	@Test
	public void testLoadString() throws Exception {
		final Class<? extends AbstractDataTest> thisClass = this.getClass();
		final InputStream inStream = thisClass.getResourceAsStream("./Data.csv");
		new TestData().load(inStream);
	}

	@Test
	public void testLoadFromXML() throws Exception {
		final Class<? extends AbstractDataTest> thisClass = this.getClass();
		final InputStream inStream = thisClass.getResourceAsStream("./Data.csv");
		new TestData().loadFromXML(inStream);
	}

	@Test
	public void testStoreOutputStream() throws Exception {
		final OutputStream out = new FileOutputStream("./target/testOut.properties");
		new TestData().store(out);
	}

	@Test
	public void testStoreOutputStreamString() throws Exception {
		final OutputStream out = new FileOutputStream("./target/testOut.properties");
		new TestData().store(out, "");
	}

	@Test
	public void testSize() {
		new TestData().size();
	}

	@Test
	public void testSetGetProperty() {
		final TestData testData = new TestData();
		final String key = "key";
		final String value = "value";
		testData.setProperty(key, value);
		final String actual = testData.getProperty(key);
		assertEquals(value, actual);
	}

	@Test
	public void testGetPropertyStringString() {
		final TestData testData = new TestData();
		final String key = "key";
		final String defaultValue = "value";
		final String actual = testData.getProperty(key, defaultValue);
		assertEquals(defaultValue, actual);
	}

}
