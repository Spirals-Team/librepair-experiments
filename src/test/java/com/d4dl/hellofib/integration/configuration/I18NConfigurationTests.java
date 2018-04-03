package com.d4dl.hellofib.integration.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This class tests to make sure the configured beans and Spring environment are working and can be auto wired.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class I18NConfigurationTests {


    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    /**
     * Make sure the MessageSource and LocaleResolver are autowired and
     * the local is the AcceptHeaderLocalResolver.
     */
    @Test
    public void whenContextLoadsSpecificBeansExist() {
        assertThat(messageSource).isNotNull();
        assertThat(localeResolver).isNotNull();
        assertThat(localeResolver).isInstanceOf(AcceptHeaderLocaleResolver.class);
    }
}
