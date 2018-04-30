package org.jboss.resteasy.test.cdi.interceptors.resource;

import org.jboss.logging.Logger;
import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.jboss.resteasy.test.cdi.util.Constants;

import javax.interceptor.Interceptors;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
@Produces(Constants.MEDIA_TYPE_TEST_XML)
@Interceptors({InterceptorOne.class})
@InterceptorClassBinding
public class InterceptorBookWriter implements MessageBodyWriter<InterceptorBook> {

    private static Logger logger = Logger.getLogger(InterceptorBookReader.class);

    private static MessageBodyWriter<InterceptorBook> delegate;

    static {
        logger.info("In InterceptorBookWriter static {}");
        ResteasyProviderFactory factory = ResteasyProviderFactory.getInstance();
        delegate = factory.getMessageBodyWriter(InterceptorBook.class, null, null, Constants.MEDIA_TYPE_TEST_XML_TYPE);
    }

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        logger.info("entering InterceptorBookWriter.isWriteable()");
        boolean b = InterceptorBook.class.equals(type);
        logger.info("leaving InterceptorBookWriter.isWriteable()");
        return b;
    }

    public long getSize(InterceptorBook t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        logger.info("entering InterceptorBookWriter.getSize()");
        logger.info("leaving InterceptorBookWriter.getSize()");
        return -1;
    }

    @Override
    @Interceptors({InterceptorTwo.class})
    @InterceptorMethodBinding
    public void writeTo(InterceptorBook t, Class<?> type, Type genericType,
                        Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
        logger.info("entering InterceptorBookWriter.writeTo()");
        logger.info("InterceptorBookWriter.writeTo() writing " + t);
        delegate.writeTo(t, type, genericType, annotations, mediaType, httpHeaders, entityStream);
        logger.info("leaving InterceptorBookWriter.writeTo()");
    }
}

