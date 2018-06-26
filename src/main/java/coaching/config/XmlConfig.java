
package coaching.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Configuration Class.
 */
public class XmlConfig extends AbstractConfig {

    /** configuration element. */
    private Element configElement = null;

    /**
     * Instantiates a new XmlConfig.
     */
    public XmlConfig() {
        super();
        final Class<? extends XmlConfig> className = this.getClass();
        final String simpleName = className.getSimpleName();
        loadFromFilename(String.format("%s.xml", simpleName));
    }

    /**
     * Instantiates a new XmlConfig from configuration file name.
     *
     * @param configFilename
     *            the Configuration filename
     */
    public XmlConfig(final String configFilename) {
        super();
        loadFromXmlFile(inputStream(toXmlFilename(configFilename)));
    }

    /**
     * To xml filename.
     *
     * @param configFilename
     *            the Configuration filename
     * @return the string
     */
    protected String toXmlFilename(final String configFilename) {
        return String.format("%s.xml", configFilename);
    }

    /**
     * Load from xml file resource as stream.
     *
     * @param resourceAsStream
     *            the resource as stream
     */
    public void loadFromXmlFile(final InputStream resourceAsStream) {
        if (resourceAsStream != null) {
            try {
                this.properties.loadFromXML(resourceAsStream);
            } catch (final IOException e) {
                this.log.error(e.toString());
            }
        }
    }

    @Override
    public String getProperty(final String key, final String defaultValue) {
        final String property = getProperty(key);
        return property == null ? defaultValue : property;
    }
    
    @Override
    public String getProperty(final String key) {
        final NodeList propertyElements = this.configElement.getElementsByTagName("property");
        for (int i = 0; i < propertyElements.getLength(); i++) {
            final Node item = propertyElements.item(i);
            this.log.info("item={}", item.toString());
        }
        return null;
    }

}
