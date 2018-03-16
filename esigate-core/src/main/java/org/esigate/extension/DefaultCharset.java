package org.esigate.extension;

import java.util.Collection;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.esigate.Driver;
import org.esigate.Parameters;
import org.esigate.events.Event;
import org.esigate.events.EventDefinition;
import org.esigate.events.EventManager;
import org.esigate.events.IEventListener;
import org.esigate.events.impl.FetchEvent;
import org.esigate.util.Parameter;
import org.esigate.util.ParameterString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This extension adds a default charset to responses which lack the charset attribute in Content-Type header. Only
 * parsable MIME types are modified :
 * 
 * <pre>
 * Content-Type: text/html
 * </pre>
 * 
 * becomes
 * 
 * <pre>
 * Content-Type:text/html; charset=utf-8
 * </pre>
 * 
 * <p>
 * Default charset can be set in esigate.properties using
 * 
 * <pre>
 * driverid.defaultCharset = utf - 8
 * </pre>
 * 
 * @author Nicolas Richeton
 * 
 */
public class DefaultCharset implements Extension, IEventListener {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCharset.class);
    /** default charset. */
    public static final Parameter<String> PARAM_DEFAULT_CHARSET = new ParameterString("defaultCharset", "ISO-8859-1");

    private Collection<String> parsableContentTypes;
    private String defaultCharset;

    @Override
    public void init(Driver driver, Properties properties) {
        driver.getEventManager().register(EventManager.EVENT_FETCH_POST, this);

        parsableContentTypes = Parameters.PARSABLE_CONTENT_TYPES.getValue(properties);
        defaultCharset = PARAM_DEFAULT_CHARSET.getValue(properties);

        LOG.info("Will use " + defaultCharset + " as default charset for " + parsableContentTypes.toString());

    }

    @Override
    public boolean event(EventDefinition arg0, Event arg1) {
        FetchEvent fe = (FetchEvent) arg1;

        Header contentTypeHeader = fe.getHttpResponse().getFirstHeader("Content-Type");

        // No content type, there is nothing we can do
        if (contentTypeHeader == null) {
            return true;
        }

        String contentType = contentTypeHeader.getValue();

        // Charset is present -> OK
        if (StringUtils.containsIgnoreCase(contentType, "charset")) {
            return true;
        }

        // Is document parsable
        boolean parsable = false;
        for (String parseableContentType : parsableContentTypes) {
            if (StringUtils.containsIgnoreCase(contentType, parseableContentType)) {
                parsable = true;
                break;
            }
        }

        // Add default charset
        if (parsable) {
            fe.getHttpResponse().setHeader("Content-Type", contentType + "; charset=" + defaultCharset);
        }

        return true;
    }

}
