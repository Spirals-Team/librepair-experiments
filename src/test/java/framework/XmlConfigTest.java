
package framework;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

import coaching.xml.XmlConfig;

/**
 * XmlConfigTest Class.
 */
public class XmlConfigTest {

    /**
     * Test xml config.
     */
    @Test
    public void testXmlConfig() {
        final XmlConfig xmlConfig = new XmlConfig();
        assertNotNull(xmlConfig);
    }

    /**
     * Test xml config string.
     */
    @Test
    public void testXmlConfigString() {
        final XmlConfig xmlConfig = new XmlConfig("XmlConfig");
        assertNotNull(xmlConfig);
    }

    /**
     * Test load string.
     */
    @Test
    public void testLoadString() {
        final XmlConfig xmlConfig = new XmlConfig();
        assertNotNull(xmlConfig);
        xmlConfig.loadXml("XmlConfig");
    }

}
