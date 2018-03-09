package ru.aserdyuchenko.JDBC;

import org.junit.Test;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.sql.SQLException;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class XmlStorageTest {
    private XmlStorage xmlStorage = new XmlStorage();
    private JdbcStorage jdbcStorage = new JdbcStorage();

//    @Test
//    public void whenSaveAndReadNumbersFromXml() throws ParserConfigurationException, TransformerConfigurationException, SQLException {
//        xmlStorage.saveToXML(jdbcStorage.get());
//        assertThat(xmlStorage.readXML(), is(Arrays.asList("1", "2", "3")));
//    }

//    @Test
//    public void  whenTransformatXMLAndGetSum() throws TransformerException {
//        xmlStorage.XsltTransformation();
//        assertThat(xmlStorage.getSum(), is(6));
//    }
}