package com.meteoradesigner.repository.datajpauserrepository;

import com.meteoradesigner.model.User;
import com.meteoradesigner.repository.DataJpaUserRepository;
import com.meteoradesigner.repository.config.DataJpaRepositoryTestConfiguration;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import static data.DataJpaUserRepositoryTestData.USER_REPOSITORY_CUSTOM;
import static data.DataJpaUserRepositoryTestData
        .USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME;
import static data.DataJpaUserRepositoryTestData.USER_REPOSITORY_FIND_ALL_COMMON;
import static data.DataJpaUserRepositoryTestData.USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL;
import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

/**
 * This class @code{DataJpaUserRepositoryCustomTest} runs custom tests
 * for @code{DataJpaUserRepository}'s custom methods.
 */
//TODO documentation.
public class DataJpaUserRepositoryCustomTest extends DataJpaRepositoryTestConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            DataJpaUserRepositoryCustomTest.class);

    @Autowired
    private DataJpaUserRepository dataJpaUserRepository;

    /**
     * Tests @code{findByEmail} method.
     */
    @Test
    public void findByEmail() {
        LOGGER.info(String.format("Mail=%s", USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL),
                    USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL);
        User actual = dataJpaUserRepository.findByEmail(
                USER_REPOSITORY_MAIL_TO_FIND_BY_MAIL);
        LOGGER.info(String.format("Actual=%s", actual), actual);
        assertEquals(
                String.format("FindByMail saveOneTest failed:" + lineSeparator() + " expected=%s" +
                                      lineSeparator() + " actual= %s",
                              USER_REPOSITORY_FIND_ALL_COMMON, actual),
                USER_REPOSITORY_CUSTOM,
                actual);
    }

    /**
     * Tests @code{findByDisplayName} method.
     */
    @Test
    public void findByDisplayName() {
        LOGGER.info(String.format("DisplayName=%s",
                                  USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME),
                    USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME);
        User actual = dataJpaUserRepository.findByDisplayName(
                USER_REPOSITORY_DISPLAY_NAME_TO_FIND_BY_DISPLAY_NAME);
        LOGGER.info(String.format("Actual=%s", actual), actual);
        assertEquals(
                String.format(
                        "FindByDisplayName saveOneTest failed:" + lineSeparator() + " expected=%s" +
                                lineSeparator() + " actual= %s",
                        USER_REPOSITORY_CUSTOM, actual),
                USER_REPOSITORY_CUSTOM,
                actual);
    }
}