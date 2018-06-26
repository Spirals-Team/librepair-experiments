
package coaching.resources;

/**
 * XML Resource Loader class.
 *
 * Loads an XML resource file by name from the classpath.
 */
public class XmlResourceLoader extends ResourceLoader {

    /**
     * Constructor.
     */
    public XmlResourceLoader() {
        super();
    }

    /**
     * Constructor.
     *
     * @param propertyFileName the property file name
     */
    public XmlResourceLoader(final String propertyFileName) {
        super(propertyFileName);
    }

}
