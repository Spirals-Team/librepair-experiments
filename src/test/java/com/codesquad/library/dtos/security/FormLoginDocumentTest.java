package com.codesquad.library.dtos.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FormLoginDocumentTest {

    private static final Logger log = LoggerFactory.getLogger(FormLoginDocumentTest.class);

    private FormLoginDocument document;

    @Before
    public void setUp() {
        this.document = new FormLoginDocument("fuck@that.shit", "1234");
    }

    @Test
    public void JSONIFY_TEST() throws Exception {
        log.debug(new ObjectMapper().writeValueAsString(this.document));
    }
}
