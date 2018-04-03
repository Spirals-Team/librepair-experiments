package com.d4dl.hellofib.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * This is the configuration class that sets up the beans for externalized messages.
 */
@Configuration
public class I18NConfiguration {

    /**
     * Default constructor
     */
    public I18NConfiguration() {

    }

    /**
     * This bean should be Autowired into components that need to use externalized strings.
     * The messages are in resource/messages_XX.properties files
     * <pre>
     * String msg = messageSource.getMessage("msg.key", new Object[]{"var", "var2"}, localeResolver.resolveLocale(request));
     * </pre>
     * @return a message bean that can be autowired into other spring components.
     * Typical usage involves calling {@link MessageSource#getMessage(String, Object[], Locale)} where the Object
     * array replaces substitution variables in the properties files.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * This bean should be Autowired into components that need to use externalized messages along with
     * {@link MessageSource}. The locale is resolved based on the
     * <a target="_blank"
     *    href="https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Accept-Language">Accept-Language</a> header.
     * @return The LocalResolver to use to pull the Accept-Language header and return a locale. If the header
     * is not present, {@link Locale#US} is the default.
     */
    @Bean
    public LocaleResolver localeResolver(){
        AcceptHeaderLocaleResolver r = new AcceptHeaderLocaleResolver();
        r.setDefaultLocale(Locale.US);
        return r;
    }
}
