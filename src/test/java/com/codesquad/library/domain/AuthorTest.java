package com.codesquad.library.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validator;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorTest {

    private static final Logger log = LoggerFactory.getLogger(AuthorTest.class);

    @Autowired
    private Validator validator;

    private Author author;

    @Before
    public void setUp() {

        log.info("is logger working?");
        this.author = Author.builder()
                .name("정호영")
                .build();
    }

    @Test
    public void BASIC_MODEL_FEATURE_TEST1() {
        assertThat(this.author.getName(), is("정호영"));
    }

    @Test
    public void BEAN_VALIDATION_TEST() {
        Author author = Author.builder()
                .name(null)
                .build();
        log.debug(validator.validate(author).stream().findFirst().get().toString());

        assertTrue(validator.validate(author).stream().findFirst().isPresent());

    }
}