package org.jboss.resteasy.annotations.providers.jaxb;

import org.jboss.resteasy.annotations.Decorator;
import org.jboss.resteasy.plugins.providers.jaxb.XmlHeaderProcessor;

import javax.xml.bind.Marshaller;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets an XML header for the returned document.
 * <p>
 * You can use replacement expressions in value string.
 *
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 * @see org.jboss.resteasy.util.StringContextReplacement
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Decorator(processor = XmlHeaderProcessor.class, target = Marshaller.class)
public @interface XmlHeader
{
   String value();
}
