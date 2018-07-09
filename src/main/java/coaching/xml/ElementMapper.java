
package coaching.xml;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * DOM Element Mapper class.
 */
public class ElementMapper {

    /** provides logging. */
    private static final Logger LOG = LoggerFactory.getLogger(ElementMapper.class);

    /** The index name. */
    private String indexName = "id";

    /** The element map. */
    private final Map<String, Element> elementMap = new ConcurrentHashMap<>();

    /**
     * Instantiates a new element mapper.
     */
    public ElementMapper() {
        super();
    }

    /**
     * Instantiates a new element mapper.
     *
     * @param document
     *            the document
     */
    public ElementMapper(final Document document) {
        initialisation(document);
    }

    /**
     * Initialisation.
     *
     * @param document
     *            the document
     */
    public void initialisation(final Document document) {
        final Element documentElement = document.getDocumentElement();
        final NodeList childNodes = documentElement.getChildNodes();
        initialisation(childNodes);
    }

    /**
     * Initialisation.
     *
     * @param nodeList
     *            the node list
     */
    public void initialisation(final NodeList nodeList) {
        for (int index = 0; index < nodeList.getLength(); index++) {
            final Element element = (Element) nodeList.item(index);
            final String key = element.getAttribute(indexName);
            elementMap.put(key, element);
            ElementMapper.LOG.info("{}={}", indexName, element);
        }
    }

    /**
     * text.
     *
     * @param node
     *            the node
     * @return the text
     */
    public static String getText(final Node node) {
        // * text from elements, entity
        // references, CDATA sections, and text nodes; but not
        // comments or processing instructions
        final int type = node.getNodeType();
        if ((type == Node.COMMENT_NODE) || (type == Node.PROCESSING_INSTRUCTION_NODE)) {
            return "";
        }

        final StringBuilder text = new StringBuilder();

        final String value = node.getNodeValue();
        if (value != null) {
            text.append(value);
        }

        if (node.hasChildNodes()) {
            final NodeList children = node.getChildNodes();
            for (int index = 0; index < children.getLength(); index++) {
                final Node child = children.item(index);
                text.append(getText(child));
            }
        }
        return text.toString();
    }

    /**
     * Find element.
     *
     * @param attributeName
     *            the attribute name
     * @return the element
     */
    public Element findElement(final String attributeName) {
        return elementMap.get(attributeName);
    }

    /**
     * Find element text.
     *
     * @param attributeName
     *            the attribute name
     * @return the string
     */
    public String findElementText(final String attributeName) {
        final Node node = elementMap.get(attributeName);
        return getText(node);
    }

    /**
     * Index name.
     *
     * @param indexAttribute
     *            the index attribute
     * @return the string
     */
    public String getIndexName(final String indexAttribute) {
        if (indexAttribute != null) {
            indexName = indexAttribute;
        }
        return indexName;
    }
}
