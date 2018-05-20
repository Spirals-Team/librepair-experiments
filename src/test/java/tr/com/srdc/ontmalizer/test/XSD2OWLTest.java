/**
 *
 */
package tr.com.srdc.ontmalizer.test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tr.com.srdc.ontmalizer.XSD2OWLMapper;

/**
 * @author Mustafa
 *
 */
public class XSD2OWLTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(XSD2OWLTest.class);

    @Test
    public void createCDAOntology() {

        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/CDA/CDA.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        FileOutputStream ont;
        try {
            File f = new File("src/test/resources/output/cda-ontology.n3");
            f.getParentFile().mkdirs();
            ont = new FileOutputStream(f);
            mapping.writeOntology(ont, "N3");
            ont.close();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }

    @Test
    public void createSALUSCommonOntology() {

        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/salus-common-xsd/salus-cim.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        FileOutputStream ont;
        try {
            File f = new File("src/test/resources/output/salus-cim-ontology.n3");
            f.getParentFile().mkdirs();
            ont = new FileOutputStream(f);
            mapping.writeOntology(ont, "N3");
            ont.close();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }

    @Test
    public void createTestOntology() {

        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/test/test.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        FileOutputStream ont;
        try {
            File f = new File("src/test/resources/output/test.n3");
            f.getParentFile().mkdirs();
            ont = new FileOutputStream(f);
            mapping.writeOntology(ont, "N3");
            ont.close();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }

    @Test
    public void writerTest() {

        // This part converts XML schema to OWL ontology.
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/test/test.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();

        // This part prints the ontology to the specified file.
        try {
            File f = new File("src/test/resources/output/test.n3");
            f.getParentFile().mkdirs();
            Writer w = new FileWriter(f);
            mapping.writeOntology(w, "N3");
            w.close();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }


    @Test
    public void createDefaultTextPropertyForMixedClasses_EmptyMixedClass(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/CDA/EmptyReturn.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void convertComplex_attrGroup_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/attrgroup.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void getAbstractClassesTest(){

        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/CDA/CDA.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
        mapping.getAbstractClasses();
    }

    @Test
    public void writeOnOntology_exclusive_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/exclusive.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void writeOnOntology_lenght_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/lenght.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void writeOnOntology_digit_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/digit.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void writeOnOntology_whitespace_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/whitespace.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void convertComplexType_SimpleType_test(){
        XSD2OWLMapper mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/SimpleType.xsd"));
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    @Test
    public void parseURL_test(){
        XSD2OWLMapper mapping = null;
        try {
            mapping = new XSD2OWLMapper(new File("src/test/resources/MaxPont/basic.xsd").toURI().toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }

    /*@Test
    public void parseURL_Exception_test(){
        XSD2OWLMapper mapping = null;
        try {
            mapping = new XSD2OWLMapper(new URL("sajt"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        mapping.setObjectPropPrefix("");
        mapping.setDataTypePropPrefix("");
        mapping.convertXSD2OWL();
    }*/

    @Test
    public void parseInputStream_test(){
        XSD2OWLMapper mapping = null;
        File myFile = new File("src/test/resources/MaxPont/basic.xsd");
        try {
            InputStream targetStream = new FileInputStream(myFile);
            mapping = new XSD2OWLMapper(targetStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
