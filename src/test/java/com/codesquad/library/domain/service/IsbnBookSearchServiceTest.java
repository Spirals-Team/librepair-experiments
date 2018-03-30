package com.codesquad.library.domain.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class IsbnBookSearchServiceTest {

    private static final Logger log = LoggerFactory.getLogger(IsbnBookSearchServiceTest.class);

    @Autowired
    private IsbnBookSearchService service;

    @Test
    public void SEARCH_TEST() {
        log.debug(service.getBookInfo(9791158390839L).toString());
    }

}