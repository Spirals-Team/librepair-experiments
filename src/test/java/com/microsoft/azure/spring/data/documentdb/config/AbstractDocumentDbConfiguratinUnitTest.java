/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.azure.spring.data.documentdb.config;

import com.microsoft.azure.documentdb.DocumentClient;
import com.microsoft.azure.spring.data.documentdb.DocumentDbFactory;
import com.microsoft.azure.spring.data.documentdb.TestConstants;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;


public class AbstractDocumentDbConfiguratinUnitTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void containsDocumentDbFactory() throws ClassNotFoundException {
        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(
                TestDocumentDbConfiguration.class);

        Assertions.assertThat(context.getBean(DocumentDbFactory.class)).isNotNull();
    }

    @Configuration
    static class TestDocumentDbConfiguration extends AbstractDocumentDbConfiguration {
        @MockBean
        private DocumentClient mockClient;

        @Override
        public String getDatabase() {
            return TestConstants.DB_NAME;
        }

        @Override
        public DocumentClient documentClient() {
            return mockClient;
        }
    }
}
