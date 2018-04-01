package org.apache.nifi.xml;

import org.apache.nifi.reporting.InitializationException;
import org.apache.nifi.schema.access.SchemaAccessUtils;
import org.apache.nifi.serialization.MalformedRecordException;
import org.apache.nifi.util.MockFlowFile;
import org.apache.nifi.util.TestRunner;
import org.apache.nifi.util.TestRunners;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestXMLReader {

    private TestRunner runner;
    private XMLReader reader;

    private final String ROOT_TAG_NAME = "root_tag";
    private final String RECORD_TAG_NAME = "record_tag";
    private final String ATTRIBUTE_PREFIX = "attribute_prefix";

    @Before
    public void setup() throws InitializationException, IOException {

        runner = TestRunners.newTestRunner(TestXMLReaderProcessor.class);
        reader = new XMLReader();
        runner.addControllerService("xml_reader", reader);
        runner.setProperty(TestXMLReaderProcessor.XML_READER, "xml_reader");

        final String outputSchemaText = new String(Files.readAllBytes(Paths.get("src/test/resources/xml/testschema")));
        runner.setProperty(reader, SchemaAccessUtils.SCHEMA_ACCESS_STRATEGY, SchemaAccessUtils.SCHEMA_TEXT_PROPERTY);
        runner.setProperty(reader, SchemaAccessUtils.SCHEMA_TEXT, outputSchemaText);
    }

    @Test
    public void testNoValidation() throws FileNotFoundException {
        runner.enableControllerService(reader);

        InputStream is = new FileInputStream("src/test/resources/xml/people3.xml");

        runner.enqueue(is);
        runner.run();
        List<MockFlowFile> flowFile = runner.getFlowFilesForRelationship(TestXMLReaderProcessor.SUCCESS);
        List<String> records = Arrays.asList((new String(runner.getContentAsByteArray(flowFile.get(0)))).split("\n"));

        assertEquals(3, records.size());
        assertEquals("MapRecord[{COUNTRY=USA, ID=1, NAME=Cleve Butler, AGE=42}]", records.get(0));
        assertEquals("MapRecord[{COUNTRY=UK, ID=2, NAME=Ainslie Fletcher, AGE=33}]", records.get(1));
        assertEquals("MapRecord[{COUNTRY=UK, ID=3, NAME=Ainslie Fletcher, AGE=33}]", records.get(2));
    }

    @Test
    public void testRootValidation() throws FileNotFoundException {
        runner.setProperty(reader, XMLReader.VALIDATE_ROOT_TAG, "${" + ROOT_TAG_NAME + "}");
        runner.enableControllerService(reader);

        InputStream is = new FileInputStream("src/test/resources/xml/people3.xml");
        runner.enqueue(is, new HashMap<String,String>() {{
            put(ROOT_TAG_NAME, "PEOPLE");
        }});
        runner.run();

        List<MockFlowFile> flowFile = runner.getFlowFilesForRelationship(TestXMLReaderProcessor.SUCCESS);
        List<String> records = Arrays.asList((new String(runner.getContentAsByteArray(flowFile.get(0)))).split("\n"));

        assertEquals(3, records.size());
        assertEquals("MapRecord[{COUNTRY=USA, ID=1, NAME=Cleve Butler, AGE=42}]", records.get(0));
        assertEquals("MapRecord[{COUNTRY=UK, ID=2, NAME=Ainslie Fletcher, AGE=33}]", records.get(1));
        assertEquals("MapRecord[{COUNTRY=UK, ID=3, NAME=Ainslie Fletcher, AGE=33}]", records.get(2));
    }

    @Test
    public void testInvalidRoot() throws FileNotFoundException {
        runner.setProperty(reader, XMLReader.VALIDATE_ROOT_TAG, "${" + ROOT_TAG_NAME + "}");
        runner.enableControllerService(reader);

        InputStream is = new FileInputStream("src/test/resources/xml/people3.xml");
        runner.enqueue(is, new HashMap<String,String>() {{
            put(ROOT_TAG_NAME, "WRONG_ROOT");
        }});
        try {
            runner.run();
        } catch (Exception e){
            assertEquals("Name of root tag \"PEOPLE\" does not match root tag validation \"WRONG_ROOT\".", e.getMessage());
            assert(e instanceof MalformedRecordException);
        }
    }

    @Test
    public void testRecordValidation() throws FileNotFoundException {
        runner.setProperty(reader, XMLReader.VALIDATE_ROOT_TAG, "${" + ROOT_TAG_NAME + "}");
        runner.setProperty(reader, XMLReader.VALIDATE_RECORD_TAG, "${" + RECORD_TAG_NAME + "}");
        runner.enableControllerService(reader);

        InputStream is = new FileInputStream("src/test/resources/xml/people3.xml");
        runner.enqueue(is, new HashMap<String,String>() {{
            put(ROOT_TAG_NAME, "PEOPLE");
            put(RECORD_TAG_NAME, "PERSON");
        }});
        runner.run();

        List<MockFlowFile> flowFile = runner.getFlowFilesForRelationship(TestXMLReaderProcessor.SUCCESS);
        List<String> records = Arrays.asList((new String(runner.getContentAsByteArray(flowFile.get(0)))).split("\n"));

        assertEquals(2, records.size());
        assertEquals("MapRecord[{COUNTRY=USA, ID=1, NAME=Cleve Butler, AGE=42}]", records.get(0));
        assertEquals("MapRecord[{COUNTRY=UK, ID=2, NAME=Ainslie Fletcher, AGE=33}]", records.get(1));
    }

    @Test
    public void testAttributePrefix() throws FileNotFoundException {
        runner.setProperty(reader, XMLReader.ATTRIBUTE_PREFIX, "${" + ATTRIBUTE_PREFIX + "}");
        runner.enableControllerService(reader);

        InputStream is = new FileInputStream("src/test/resources/xml/people3.xml");
        runner.enqueue(is, new HashMap<String,String>() {{
            put(ATTRIBUTE_PREFIX, "ATTR_");
        }});
        runner.run();

        List<MockFlowFile> flowFile = runner.getFlowFilesForRelationship(TestXMLReaderProcessor.SUCCESS);
        List<String> records = Arrays.asList((new String(runner.getContentAsByteArray(flowFile.get(0)))).split("\n"));

        assertEquals(3, records.size());
        assertEquals("MapRecord[{COUNTRY=USA, ATTR_ID=1, NAME=Cleve Butler, AGE=42}]", records.get(0));
        assertEquals("MapRecord[{COUNTRY=UK, ATTR_ID=2, NAME=Ainslie Fletcher, AGE=33}]", records.get(1));
        assertEquals("MapRecord[{COUNTRY=UK, ATTR_ID=3, NAME=Ainslie Fletcher, AGE=33}]", records.get(2));
    }
}
