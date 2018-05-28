package org.EDM.Transformations.formats.topx;

import eu.europeana.corelib.definitions.jibx.RDF;
import org.EDM.Transformations.formats.EDM;
import org.EDM.Transformations.formats.FactoryEDM;
import org.EDM.Transformations.formats.xslt.XSLTTransformations;
import org.csuc.deserialize.JaxbUnmarshal;
import org.csuc.deserialize.JibxUnMarshall;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static java.nio.charset.StandardCharsets.*;
import nl.nationaalarchief.topx.v2.TopxType;
import static org.junit.Assert.*;

public class TOPX2EDMTest {

    private File xml;
    private File tmp;

    private EDM topx;

    @Before
    public void setUp() throws Exception {
        xml = new File(getClass().getClassLoader().getResource("topx/topx.xml").getFile());
        tmp = Files.createTempFile("topx_edm", ".xml").toFile();

        assertTrue(xml.exists());

        JaxbUnmarshal jxb = new JaxbUnmarshal(xml, new Class[] { TOPX2EDM.class });
        assertNotNull(jxb.getObject());
        assertTrue(jxb.isValidating());

        topx = FactoryEDM.createFactory(new TOPX2EDM(UUID.randomUUID().toString(), (TopxType) jxb.getObject(), properties()));
        assertNotNull(topx);

        tmp.deleteOnExit();
    }


    @Test
    public void transformation() throws Exception {
        XSLTTransformations transformations = null;
        try{
            transformations = topx.transformation(null);
            assertNull(transformations);
        }catch(Exception e){}
    }


    @Test
    public void transformation1() throws Exception {
        XSLTTransformations transformations = null;
        try{
            transformations = topx.transformation(null, null, null);
            assertNull(transformations);
        }catch(Exception e){}
    }


    @Test
    public void creation() throws Exception {
        topx.creation();
    }


    @Test
    public void creation1() throws Exception {
        StringWriter writer = new StringWriter();
        topx.creation(UTF_8, true, writer);
        assertTrue(!writer.toString().isEmpty());
    }


    @Test
    public void creation2() throws Exception {
        FileOutputStream outs = new FileOutputStream(tmp);
        topx.creation(UTF_8, true, outs);

        int b  = new FileInputStream(tmp).read();
        assertNotEquals(-1, b);
    }


    @Test
    public void validateSchema() throws Exception {
        StringWriter writer = new StringWriter();
        topx.creation(UTF_8, true, writer);

        Reader reader = new StringReader(writer.toString());
        JibxUnMarshall jibx = topx.validateSchema(reader, RDF.class);

        assertNotNull(jibx);
        assertNotNull(jibx.getElement());
        assertNull(jibx.getError());
    }


    @Test
    public void validateSchema1() throws Exception {
        StringWriter writer = new StringWriter();
        topx.creation(UTF_8, true, writer);

        Reader reader = new StringReader(writer.toString());
        JibxUnMarshall jibx = topx.validateSchema(reader, "name", RDF.class);

        assertNotNull(jibx);
        assertNotNull(jibx.getElement());
        assertNull(jibx.getError());
    }


    @Test
    public void validateSchema2() throws Exception {
        FileOutputStream outs = new FileOutputStream(tmp);
        topx.creation(UTF_8, true, outs);

        JibxUnMarshall jibx = topx.validateSchema(new FileInputStream(tmp), UTF_8, RDF.class);

        assertNotNull(jibx);
        assertNotNull(jibx.getElement());
        assertNull(jibx.getError());
    }


    @Test
    public void validateSchema3() throws Exception {
        FileOutputStream outs = new FileOutputStream(tmp);
        topx.creation(UTF_8, true, outs);

        JibxUnMarshall jibx = topx.validateSchema(new FileInputStream(tmp), "name", UTF_8, RDF.class);

        assertNotNull(jibx);
        assertNotNull(jibx.getElement());
        assertNull(jibx.getError());
    }

    @Test
    public void modify() throws Exception {

    }

    private Map<String, String> properties() {
        Map<String, String> properties = new HashMap<String, String>();
        properties.put("edmType", "IMAGE");
        properties.put("provider", "provider");
        properties.put("dataProvider", "dataProvider");
        properties.put("language", "language");
        properties.put("rights", "rights");
        properties.put("set", "set");

        return properties;
    }
}