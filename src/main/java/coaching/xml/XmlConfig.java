
package coaching.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML Configuration Class.
 */
public class XmlConfig {

    private static final Logger LOG = LoggerFactory.getLogger(XmlConfig.class);

    /** configuration element. */
    private Element configElement = null;

    /**
     * Instantiates a new XmlConfig.
     */
    public XmlConfig() {
        super();
        final Class<? extends XmlConfig> className = this.getClass();
        final String simpleName = className.getSimpleName();
        final File configFile = new File(toXmlFilename(simpleName));
        loadXml(configFile);
    }

    /**
     * Instantiates a new XmlConfig from configuration file.
     *
     * @param configFilename
     *            the Configuration filename
     */
    public XmlConfig(final File configFilename) {
        super();
        loadXml(configFilename);
    }

    /**
     * Instantiates a new XmlConfig from configuration file name.
     *
     * @param configFilename
     *            the Configuration filename
     */
    public XmlConfig(final String configFilename) {
        super();
        loadXml(configFilename);
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
     * Load configuration filename.
     *
     * @param configFilename
     *            the Configuration filename
     */
    public void loadXml(final String configFilename) {
        loadXml(new File(configFilename));
    }

    /**
     * Load configuration file.
     *
     * @param configFile
     *            the Configuration file
     */
    public void loadXml(final File configFile) {
        try {
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            try {
                final DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                final Document document = documentBuilder.parse(configFile);
                this.configElement = document.getDocumentElement();
            } catch (final ParserConfigurationException parserConfigurationException) {
                LOG.error(parserConfigurationException.toString());
            }
        } catch (final Exception exception) {
            LOG.info(exception.toString());
        }
    }

    /**
     * attribute.
     *
     * @param attributeName
     *            the attribute name
     * @return the attribute
     */
    protected String getAttribute(final String attributeName) {
        return this.configElement.getAttribute(attributeName);
    }

    /**
     * elements by tag name.
     *
     * @param elementName
     *            the element name
     * @return the elements by tag name
     */
    protected NodeList getElementsByTagName(final String elementName) {
        return this.configElement.getElementsByTagName(elementName);
    }

    /*
     * (non-Javadoc)
     *
     * @see framework.config.ConfigInterface#getProperty(java.lang.String)
     */
    public String getProperty(final String key) {
        final NodeList propertyElements = this.configElement.getElementsByTagName("property");
        for (int i = 0; i < propertyElements.getLength(); i++) {
            final Node item = propertyElements.item(i);
            LOG.info("item = {}", item.toString());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see framework.config.ConfigInterface#getProperty(java.lang.String,
     * java.lang.String)
     */
    public String getProperty(final String key, final String defaultValue) {
        final String property = getProperty(key);
        return property == null ? defaultValue : property;
    }

    /**
     * tag name.
     *
     * @return the tag name
     */
    protected String getTagName() {
        return this.configElement.getTagName();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return null != this.configElement ? toXml(this.configElement) : "null";
    }

    /**
     * To xml string.
     *
     * @param node
     *            the node
     * @return the string
     */
    private String toXml(final Node node) {
        StringBuffer text = new StringBuffer();
        if (node != null) {
            final String value = node.getNodeValue();
            if (value != null) {
                text = new StringBuffer(value);
            }
            if (node.hasChildNodes()) {
                final NodeList children = node.getChildNodes();
                for (int i = 0; i < children.getLength(); i++) {
                    final Node item = children.item(i);
                    final String xml = toXml(item);
                    text.append(xml);
                }
            }
        }
        return text.toString();
    }

}
