package com.codesquad.library.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class BookCategoriesTest {

    private static final Logger log = LoggerFactory.getLogger(BookCategoriesTest.class);
    private final BookCategories categories = BookCategories.BUSINESS;

    @Test
    public void JSON_GENERATION_TEST() throws Exception {
        log.debug(new ObjectMapper().writeValueAsString(categories));
    }

}